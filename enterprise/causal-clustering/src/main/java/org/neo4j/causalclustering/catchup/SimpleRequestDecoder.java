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
package org.neo4j.causalclustering.catchup;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import org.neo4j.causalclustering.messaging.Message;
import org.neo4j.function.Factory;

/**
 * This class extends {@link MessageToMessageDecoder} because if it extended
 * {@link io.netty.handler.codec.ByteToMessageDecoder} instead the decode method would fail as no
 * bytes are consumed from the ByteBuf but an object is added in the out list.
 */
public class SimpleRequestDecoder extends MessageToMessageDecoder<ByteBuf>
{
    private Factory<? extends Message> factory;

    public SimpleRequestDecoder( Factory<? extends Message> factory )
    {
        this.factory = factory;
    }

    @Override
    protected void decode( ChannelHandlerContext ctx, ByteBuf msg, List<Object> out )
    {
        out.add( factory.newInstance() );
    }
}
