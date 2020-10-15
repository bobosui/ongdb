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
package org.neo4j.kernel.enterprise.builtinprocs;

import java.time.ZoneId;

import org.neo4j.helpers.SocketAddress;
import org.neo4j.kernel.api.net.TrackedNetworkConnection;

public class ListConnectionResult
{
    public final String connectionId;
    public final String connectTime;
    public final String connector;
    public final String username;
    public final String userAgent;
    public final String serverAddress;
    public final String clientAddress;

    ListConnectionResult( TrackedNetworkConnection connection, ZoneId timeZone )
    {
        connectionId = connection.id();
        connectTime = ProceduresTimeFormatHelper.formatTime( connection.connectTime(), timeZone );
        connector = connection.connector();
        username = connection.username();
        userAgent = connection.userAgent();
        serverAddress = SocketAddress.format( connection.serverAddress() );
        clientAddress = SocketAddress.format( connection.clientAddress() );
    }
}
