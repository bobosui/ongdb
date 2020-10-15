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
package org.neo4j.causalclustering.core.consensus;

import org.neo4j.causalclustering.core.consensus.outcome.Outcome;

public interface LeaderListener
{
    /**
     * Allows listeners to handle a leader step down for the given term.
     * Note: actions taken as a result of a step down should typically happen *before* any
     * actions taken as a result of the leader switch which has also, implicitly, taken place.
     *
     * @param stepDownTerm the term in which the the step down event occurred.
     */
    default void onLeaderStepDown( long stepDownTerm )
    {
    }

    void onLeaderSwitch( LeaderInfo leaderInfo );

    /**
     * Standard catch-all method which delegates leader events to their appropriate handlers
     * in the appropriate order, i.e. calls step down logic (if necessary) befor leader switch
     * logic.
     *
     * @param outcome The outcome which contains details of the leader event
     */
    default void onLeaderEvent( Outcome outcome )
    {
        outcome.stepDownTerm().ifPresent( this::onLeaderStepDown );
        onLeaderSwitch( new LeaderInfo( outcome.getLeader(), outcome.getTerm() ) );
    }
}
