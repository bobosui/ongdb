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
package org.neo4j.causalclustering.catchup;

import org.neo4j.causalclustering.messaging.Message;

import static java.lang.String.format;

public enum RequestMessageType implements Message
{
    TX_PULL_REQUEST( (byte) 1 ),
    STORE( (byte) 2 ),
    CORE_SNAPSHOT( (byte) 3 ),
    STORE_ID( (byte) 4 ),
    PREPARE_STORE_COPY( (byte) 5 ),
    STORE_FILE( (byte) 6 ),
    INDEX_SNAPSHOT( (byte) 7 ),
    UNKNOWN( (byte) 404 );

    private byte messageType;

    RequestMessageType( byte messageType )
    {
        this.messageType = messageType;
    }

    public static RequestMessageType from( byte b )
    {
        for ( RequestMessageType responseMessageType : values() )
        {
            if ( responseMessageType.messageType == b )
            {
                return responseMessageType;
            }
        }
        return UNKNOWN;
    }

    public byte messageType()
    {
        return messageType;
    }

    @Override
    public String toString()
    {
        return format( "RequestMessageType{messageType=%s}", messageType );
    }
}
