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
package org.neo4j.causalclustering.core.consensus.log.segmented;

/**
 * Collects all the state that must be recovered after a restart.
 */
public class State
{
    Segments segments;
    Terms terms;

    long prevIndex = -1;
    long prevTerm = -1;
    long appendIndex = -1;

    @Override
    public String toString()
    {
        return "State{" +
               "prevIndex=" + prevIndex +
               ", prevTerm=" + prevTerm +
               ", appendIndex=" + appendIndex +
               '}';
    }
}
