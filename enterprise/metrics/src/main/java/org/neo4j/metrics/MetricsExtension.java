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
package org.neo4j.metrics;

import com.codahale.metrics.MetricRegistry;

import org.neo4j.io.fs.FileSystemAbstraction;
import org.neo4j.kernel.configuration.Config;
import org.neo4j.kernel.impl.spi.KernelContext;
import org.neo4j.kernel.lifecycle.LifeSupport;
import org.neo4j.kernel.lifecycle.Lifecycle;
import org.neo4j.logging.Log;
import org.neo4j.logging.internal.LogService;
import org.neo4j.metrics.output.CompositeEventReporter;
import org.neo4j.metrics.output.EventReporterBuilder;
import org.neo4j.metrics.source.Neo4jMetricsBuilder;
import org.neo4j.scheduler.JobScheduler;

public class MetricsExtension implements Lifecycle
{
    private final LifeSupport life = new LifeSupport();
    private Log logger;
    private CompositeEventReporter reporter;
    private boolean metricsBuilt;

    MetricsExtension( KernelContext kernelContext, MetricsKernelExtensionFactory.Dependencies dependencies )
    {
        LogService logService = dependencies.logService();
        Config configuration = dependencies.configuration();
        FileSystemAbstraction fileSystem = dependencies.fileSystemAbstraction();
        JobScheduler scheduler = dependencies.scheduler();
        logger = logService.getUserLog( getClass() );

        MetricRegistry registry = new MetricRegistry();
        reporter = new EventReporterBuilder( configuration, registry, logger, kernelContext, life, fileSystem,
                scheduler, dependencies.portRegister() ).build();
        metricsBuilt = new Neo4jMetricsBuilder( registry, reporter, configuration, logService, kernelContext,
                                                dependencies, life ).build();
    }

    @Override
    public void init()
    {
        logger.info( "Initiating metrics..." );
        if ( metricsBuilt && reporter.isEmpty() )
        {
            logger.warn( "Several metrics were enabled but no exporting option was configured to report values to. " +
                         "Disabling kernel metrics extension." );
            life.clear();
        }

        if ( !reporter.isEmpty() && !metricsBuilt )
        {
            logger.warn( "Exporting tool have been configured to report values to but no metrics were enabled. " +
                         "Disabling kernel metrics extension." );
            life.clear();
        }

        life.init();
    }

    @Override
    public void start()
    {
        life.start();
    }

    @Override
    public void stop()
    {
        life.stop();
    }

    @Override
    public void shutdown()
    {
        life.shutdown();
    }
}
