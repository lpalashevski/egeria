/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */

package org.odpi.openmetadata.adapters.connectors.datastore.datafolder;

import org.odpi.openmetadata.adapters.connectors.datastore.basicfile.BasicFileStore;
import org.odpi.openmetadata.adapters.connectors.datastore.basicfile.ffdc.exception.FileException;
import org.odpi.openmetadata.adapters.connectors.datastore.datafolder.ffdc.DataFolderConnectorErrorCode;
import org.odpi.openmetadata.frameworks.connectors.ConnectorBase;
import org.odpi.openmetadata.frameworks.connectors.properties.ConnectionProperties;
import org.odpi.openmetadata.frameworks.connectors.properties.EndpointProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


/**
 * BasicFileStoreConnector works with files to retrieve simple objects.
 */
public class DataFolderConnector extends ConnectorBase implements BasicFileStore
{
    protected String dataFolderName = null;

    /*
     * Variables used for logging and debug.
     */
    private static final Logger log = LoggerFactory.getLogger(DataFolderConnector.class);


    /**
     * Initialize the connector.
     *
     * @param connectorInstanceId - unique id for the connector instance - useful for messages etc
     * @param connectionProperties - POJO for the configuration used to create the connector.
     */
    @Override
    public void initialize(String connectorInstanceId, ConnectionProperties connectionProperties)
    {
        super.initialize(connectorInstanceId, connectionProperties);

        EndpointProperties  endpoint = connectionProperties.getEndpoint();

        if (endpoint != null)
        {
            dataFolderName = endpoint.getAddress();
        }
        else
        {
            log.error("Null endpoint");
        }

    }


    /**
     * Throw a standard exception based on the supplied error code.
     *
     * @param errorCode error code describing the problem
     * @param methodName calling method
     * @param fileStoreName name of file
     * @throws FileException exception that is generated
     */
    private void throwException(DataFolderConnectorErrorCode errorCode,
                                String                       methodName,
                                String                       fileStoreName,
                                Throwable                    caughtException) throws FileException
    {
        String  errorMessage;

        if (fileStoreName == null)
        {
            errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(super.connectionBean.getQualifiedName());
        }
        else
        {
            errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(fileStoreName,
                                                                                              super.connectionBean.getQualifiedName());
        }

        if (caughtException == null)
        {
            throw new FileException(errorCode.getHTTPErrorCode(),
                                    this.getClass().getName(),
                                    methodName,
                                    errorMessage,
                                    errorCode.getSystemAction(),
                                    errorCode.getUserAction(),
                                    fileStoreName);
        }
        else
        {
            throw new FileException(errorCode.getHTTPErrorCode(),
                                    this.getClass().getName(),
                                    methodName,
                                    errorMessage,
                                    errorCode.getSystemAction(),
                                    errorCode.getUserAction(),
                                    caughtException,
                                    fileStoreName);
        }
    }


    /**
     * Return the Java File object that provides access to the file.
     *
     * @return File object
     * @throws FileException problem accessing the file
     */
    public File  getFile() throws FileException
    {
        final String  methodName = "getFile";

        return this.getFile(methodName);
    }


    /**
     * Return the Java File object that provides access to the file.
     *
     * @param methodName, calling method
     * @return File object
     * @throws FileException problem accessing the file
     */
    protected File  getFile(String methodName) throws FileException
    {
        try
        {
            if (dataFolderName == null)
            {
                this.throwException(DataFolderConnectorErrorCode.FOLDER_NOT_SPECIFIED, methodName, null, null);
            }

            File  dataFolder = new File(dataFolderName);

            if (! dataFolder.exists())
            {
                this.throwException(DataFolderConnectorErrorCode.FOLDER_NOT_FOUND, methodName, dataFolderName, null);
            }

            if (! dataFolder.isDirectory())
            {
                this.throwException(DataFolderConnectorErrorCode.FILE_NOT_DIRECTORY, methodName, dataFolderName, null);
            }

            if (! dataFolder.canRead())
            {
                this.throwException(DataFolderConnectorErrorCode.FOLDER_NOT_READABLE, methodName, dataFolderName, null);
            }

            return dataFolder;
        }
        catch (FileException  error)
        {
            log.debug("Throwing error " + error.getClass().getSimpleName() + " with message " + error.getMessage());
            throw error;
        }
        catch (SecurityException  error)
        {
            this.throwException(DataFolderConnectorErrorCode.UNEXPECTED_SECURITY_EXCEPTION, methodName, dataFolderName, error);
        }
        catch (Throwable error)
        {
            this.throwException(DataFolderConnectorErrorCode.UNEXPECTED_IO_EXCEPTION, methodName, dataFolderName, error);
        }

        return null;
    }


    /**
     * Close the file
     */
    public void disconnect()
    {
        try
        {
            super.disconnect();
        }
        catch (Throwable  exec)
        {
            log.debug("Ignoring unexpected exception " + exec.getClass().getSimpleName() + " with message " + exec.getMessage());
        }

        log.debug("Closing File");
    }
}
