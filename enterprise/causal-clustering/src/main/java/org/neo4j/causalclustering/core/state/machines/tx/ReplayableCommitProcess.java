/*
 * Copyright (c) 2018-2020 "Graph Foundation"
 * Graph Foundation, Inc. [https://graphfoundation.org]
 *
 * Copyright (c) 2002-2018 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of ONgDB Enterprise Edition. The included source
 * code can be redistributed and/or modified under the terms of the
 * GNU AFFERO GENERAL PUBLIC LICENSE Version 3
 * (http://www.fsf.org/licensing/licenses/agpl-3.0.html) as found
 * in the associated LICENSE.txt file.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 */
package org.neo4j.causalclustering.core.state.machines.tx;

import java.util.concurrent.atomic.AtomicLong;

import org.neo4j.internal.kernel.api.exceptions.TransactionFailureException;
import org.neo4j.kernel.impl.api.TransactionCommitProcess;
import org.neo4j.kernel.impl.api.TransactionToApply;
import org.neo4j.kernel.impl.transaction.tracing.CommitEvent;
import org.neo4j.storageengine.api.TransactionApplicationMode;

/**
 * Counts transactions, and only applies new transactions once it has already seen enough transactions to reproduce
 * the current state of the store.
 */
class ReplayableCommitProcess implements TransactionCommitProcess
{
    private final AtomicLong lastLocalTxId = new AtomicLong( 1 );
    private final TransactionCommitProcess localCommitProcess;
    private final TransactionCounter transactionCounter;

    ReplayableCommitProcess( TransactionCommitProcess localCommitProcess, TransactionCounter transactionCounter )
    {
        this.localCommitProcess = localCommitProcess;
        this.transactionCounter = transactionCounter;
    }

    @Override
    public long commit( TransactionToApply batch,
                        CommitEvent commitEvent,
                        TransactionApplicationMode mode ) throws TransactionFailureException
    {
        long txId = lastLocalTxId.incrementAndGet();
        if ( txId > transactionCounter.lastCommittedTransactionId() )
        {
            return localCommitProcess.commit( batch, commitEvent, mode );
        }
        return txId;
    }
}
