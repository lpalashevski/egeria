/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.openmetadata.openconnectors.governancedaemonconnectors.openlineageconnectors.janusconnector.maingraph;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.io.IoCore;
import org.apache.tinkerpop.gremlin.structure.io.graphson.GraphSONMapper;
import org.apache.tinkerpop.gremlin.structure.io.graphson.GraphSONWriter;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.graphdb.tinkerpop.io.graphson.JanusGraphSONModuleV2d0;
import org.odpi.openmetadata.frameworks.connectors.properties.ConnectionProperties;
import org.odpi.openmetadata.governanceservers.openlineage.ffdc.OpenLineageException;
import org.odpi.openmetadata.governanceservers.openlineage.ffdc.OpenLineageServerErrorCode;
import org.odpi.openmetadata.governanceservers.openlineage.maingraph.MainGraphConnectorBase;
import org.odpi.openmetadata.governanceservers.openlineage.model.*;
import org.odpi.openmetadata.governanceservers.openlineage.responses.LineageResponse;
import org.odpi.openmetadata.openconnectors.governancedaemonconnectors.openlineageconnectors.janusconnector.factory.GraphFactory;
import org.odpi.openmetadata.openconnectors.governancedaemonconnectors.openlineageconnectors.janusconnector.model.ffdc.JanusConnectorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import static org.odpi.openmetadata.openconnectors.governancedaemonconnectors.openlineageconnectors.janusconnector.utils.GraphConstants.*;

public class MainGraphConnector extends MainGraphConnectorBase {

    private static final Logger log = LoggerFactory.getLogger(MainGraphConnector.class);

    private JanusGraph mainGraph;
    private MainGraphConnectorHelper helper;

    /**
     * {@inheritDoc}
     */
    public void initializeGraphDB() throws OpenLineageException {
        String graphDB = connectionProperties.getConfigurationProperties().get("graphDB").toString();
        GraphFactory graphFactory = new GraphFactory();
        try {
            this.mainGraph = graphFactory.openGraph(graphDB, connectionProperties);
        } catch (JanusConnectorException error) {
            throw new OpenLineageException(500,
                    error.getReportingClassName(),
                    error.getReportingActionDescription(),
                    error.getReportedErrorMessage(),
                    error.getReportedSystemAction(),
                    error.getReportedUserAction()
            );
        }
        this.helper = new MainGraphConnectorHelper(mainGraph);
    }

    /**
     * {@inheritDoc}
     */
    public LineageResponse lineage(Scope scope, View view, String guid, String displayNameMustContain, boolean includeProcesses) throws OpenLineageException {
        String methodName = "MainGraphConnector.lineage";

        GraphTraversalSource g = mainGraph.traversal();
        try {
            g.V().has(PROPERTY_KEY_ENTITY_NODE_ID, guid).next();
        } catch (NoSuchElementException e) {
            OpenLineageServerErrorCode errorCode = OpenLineageServerErrorCode.NODE_NOT_FOUND;
            throw new OpenLineageException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorCode.getFormattedErrorMessage(),
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }
        String edgeLabel = helper.getEdgeLabel(view);
        LineageVerticesAndEdges lineageVerticesAndEdges = null;

        switch (scope) {
            case SOURCE_AND_DESTINATION:
                lineageVerticesAndEdges = helper.sourceAndDestination(edgeLabel, guid);
                break;
            case END_TO_END:
                lineageVerticesAndEdges = helper.endToEnd(edgeLabel, guid);
                break;
            case ULTIMATE_SOURCE:
                lineageVerticesAndEdges = helper.ultimateSource(edgeLabel, guid);
                break;
            case ULTIMATE_DESTINATION:
                lineageVerticesAndEdges = helper.ultimateDestination(edgeLabel, guid);
                break;
            case GLOSSARY:
                lineageVerticesAndEdges = helper.glossary(guid);
                break;
        }
        if (!includeProcesses)
            helper.filterOutProcesses(lineageVerticesAndEdges);
        if (!displayNameMustContain.isEmpty())
            helper.filterDisplayName(lineageVerticesAndEdges, displayNameMustContain);
        LineageResponse lineageResponse = new LineageResponse(lineageVerticesAndEdges);
        return lineageResponse;
    }

    /**
     * {@inheritDoc}
     */
    //TODO Remove before pentest or production
    public void dumpMainGraph() {
        try {
            mainGraph.io(IoCore.graphml()).writeGraph("mainGraph.graphml");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    public String exportMainGraph() {
        OutputStream out = new ByteArrayOutputStream();
        GraphSONMapper mapper = GraphSONMapper.build().addCustomModule(JanusGraphSONModuleV2d0.getInstance()).create();
        GraphSONWriter writer = GraphSONWriter.build().mapper(mapper).wrapAdjacencyList(true).create();
        try {
            writer.writeGraph(out, mainGraph);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return out.toString();
    }


    /**
     * {@inheritDoc}
     */
    public Object getMainGraph() {
        return mainGraph;
    }

}
