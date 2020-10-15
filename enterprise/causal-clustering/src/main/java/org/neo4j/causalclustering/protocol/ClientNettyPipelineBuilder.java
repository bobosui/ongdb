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
package org.neo4j.causalclustering.protocol;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import org.neo4j.logging.Log;

public class ClientNettyPipelineBuilder extends NettyPipelineBuilder<ProtocolInstaller.Orientation.Client, ClientNettyPipelineBuilder>
{
    private static final int LENGTH_FIELD_BYTES = 4;

    ClientNettyPipelineBuilder( ChannelPipeline pipeline, Log log )
    {
        super( pipeline, log );
    }

    @Override
    public ClientNettyPipelineBuilder addFraming()
    {
        add( "frame_encoder", new LengthFieldPrepender( LENGTH_FIELD_BYTES ) );
        add( "frame_decoder", new LengthFieldBasedFrameDecoder( Integer.MAX_VALUE, 0, LENGTH_FIELD_BYTES, 0, LENGTH_FIELD_BYTES ) );
        return this;
    }
}
