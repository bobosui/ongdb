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
package org.neo4j.causalclustering.catchup.storecopy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;

import org.neo4j.causalclustering.catchup.ResponseMessageType;

public class StoreFileStreamingProtocol
{
    /**
     * This sends operations on the outgoing pipeline or the file, including
     * chunking {@link org.neo4j.causalclustering.catchup.storecopy.FileSender} handlers.
     * <p>
     * Note that we do not block here.
     */
    void stream( ChannelHandlerContext ctx, StoreResource resource )
    {
        ctx.write( ResponseMessageType.FILE );
        ctx.write( new FileHeader( resource.path(), resource.recordSize() ) );
        ctx.write( new FileSender( resource ) );
    }

    Future<Void> end( ChannelHandlerContext ctx, StoreCopyFinishedResponse.Status status )
    {
        ctx.write( ResponseMessageType.STORE_COPY_FINISHED );
        return ctx.writeAndFlush( new StoreCopyFinishedResponse( status ) );
    }
}
