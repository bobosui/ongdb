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
 * (http://www.fsf.org/licensing/licenses/agpl-3.0.html).
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 */
package org.neo4j.kernel.ha.lock.trace;

import org.neo4j.kernel.monitoring.Monitors;
import org.neo4j.kernel.monitoring.tracing.DefaultTracerFactory;
import org.neo4j.scheduler.JobScheduler;
import org.neo4j.storageengine.api.lock.LockTracer;

public class TestLocksTracerFactory extends DefaultTracerFactory
{
    public TestLocksTracerFactory()
    {
    }

    @Override
    public String getImplementationName()
    {
        return "slaveLocksTracer";
    }

    @Override
    public LockTracer createLockTracer( Monitors monitors, JobScheduler jobScheduler )
    {
        return new RecordingLockTracer();
    }
}
