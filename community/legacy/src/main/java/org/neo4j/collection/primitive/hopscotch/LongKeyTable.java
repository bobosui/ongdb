/*
 * Copyright (c) 2018-2020 "Graph Foundation"
 * Graph Foundation, Inc. [https://graphfoundation.org]
 *
 * Copyright (c) 2002-2020 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.collection.primitive.hopscotch;

public class LongKeyTable<VALUE>
        extends IntArrayBasedKeyTable<VALUE>
{
    public LongKeyTable( int capacity, VALUE singleValue )
    {
        super( capacity, 3, 32, singleValue );
    }

    @Override
    public long key( int index )
    {
        return getLong( address( index ) );
    }

    @Override
    protected void internalPut( int actualIndex, long key, VALUE value )
    {
        putLong( actualIndex, key );
    }

    @Override
    protected LongKeyTable<VALUE> newInstance( int newCapacity )
    {
        return new LongKeyTable<>( newCapacity, singleValue );
    }
}
