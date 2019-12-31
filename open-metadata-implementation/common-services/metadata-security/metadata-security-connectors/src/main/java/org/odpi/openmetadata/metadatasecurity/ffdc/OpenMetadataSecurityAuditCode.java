/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.openmetadata.metadatasecurity.ffdc;

import org.odpi.openmetadata.repositoryservices.auditlog.OMRSAuditLogRecordSeverity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * The OpenMetadataSecurityAuditCode is used to define the message content for the OMRS Audit Log.
 *
 * The 5 fields in the enum are:
 * <ul>
 *     <li>Log Message Id - to uniquely identify the message</li>
 *     <li>Severity - is this an event, decision, action, error or exception</li>
 *     <li>Log Message Text - includes placeholder to allow additional values to be captured</li>
 *     <li>Additional Information - further parameters and data relating to the audit message (optional)</li>
 *     <li>SystemAction - describes the result of the situation</li>
 *     <li>UserAction - describes how a user should correct the situation</li>
 * </ul>
 */
public enum OpenMetadataSecurityAuditCode
{
    PLATFORM_INITIALIZING("OPEN-METADATA-SECURITY-0001",
                         OMRSAuditLogRecordSeverity.INFO,
                         "The Open Metadata Security Service {0} for OMAG Server Platform {1} is initializing",
                         "The local server has started up a new instance of the Open Metadata Platform Security Service Connector.",
                         "No action is required.  This is part of the normal operation of the service."),

    PLATFORM_SHUTDOWN("OPEN-METADATA-SECURITY-0002",
                     OMRSAuditLogRecordSeverity.INFO,
                     "The Open Metadata Security Service {0} for OMAG Server Platform {1} is shutting down",
                     "The local administrator has requested shut down of the Open Metadata Platform Security Service Connector.",
                     "No action is required.  This is part of the normal operation of the service."),


    SERVICE_INITIALIZING("OPEN-METADATA-SECURITY-0003",
             OMRSAuditLogRecordSeverity.INFO,
             "The Open Metadata Security Service {0} for server {1} is initializing",
             "The local server has started up a new instance of the Open Metadata Server Security Service Connector.",
             "No action is required.  This is part of the normal operation of the service."),

    SERVICE_SHUTDOWN("OPEN-METADATA-SECURITY-0004",
             OMRSAuditLogRecordSeverity.INFO,
             "The Open Metadata Security Service {0} for server {1} is shutting down",
             "The local administrator has requested shut down of the Open Metadata Server Security Service Connector.",
             "No action is required.  This is part of the normal operation of the service."),

    UNAUTHORIZED_PLATFORM_ACCESS("OPEN-METADATA-SECURITY-0005",
             OMRSAuditLogRecordSeverity.SECURITY,
             "User {0} is not authorized to issue a request to this OMAG Server Platform",
             "The security service detected an unauthorized access to an OMAG Server Platform.",
             "Review the security policies and settings to determine if this access should be allowed or not." +
                                        "  Take action to either change the security sessions or determine the reason for the unauthorized request."),

    UNAUTHORIZED_SERVER_ACCESS("OPEN-METADATA-SECURITY-0006",
                               OMRSAuditLogRecordSeverity.SECURITY,
                               "User {0} is not authorized to issue a request to server {1}",
                               "The security service detected an unauthorized access to a service.",
                               "Review the security policies and settings to determine if this access should be allowed or not." +
                                       "  Take action to either change the security sessions or determine the reason for the unauthorized request."),


    UNAUTHORIZED_SERVICE_ACCESS("OPEN-METADATA-SECURITY-0007",
                                OMRSAuditLogRecordSeverity.SECURITY,
                                "User {0} is not authorized to issue {1} requests for service {2} on server {3}",
                                "The security service detected an unauthorized access to a service.",
                                "Review the security policies and settings to determine if this access should be allowed or not." +
                                        "  Take action to either change the security sessions or determine the reason for the unauthorized request."),

    UNAUTHORIZED_ASSET_FEEDBACK("OPEN-METADATA-SECURITY-0008",
              OMRSAuditLogRecordSeverity.SECURITY,
             "User {0} is not authorized to attach feedback to asset {1}",
             "The security service detected an unauthorized change to an asset.",
             "Review the security policies and settings to determine if this access should be allowed or not." +
                                        "  Take action to either change the security sessions or determine the reason for the unauthorized request."),

    UNAUTHORIZED_ZONE_CHANGE("OPEN-METADATA-SECURITY-0009",
                             OMRSAuditLogRecordSeverity.SECURITY,
                             "User {0} is not authorized to change the zone membership of asset {1} from {2} to {3}",
                             "The security service detected an unauthorized change to an asset.",
                             "Review the security policies and settings to determine if this access should be allowed or not." +
                                     "  Take action to either change the security sessions or determine the reason for the unauthorized request."),

    UNAUTHORIZED_CONNECTION_ACCESS("OPEN-METADATA-SECURITY-0010",
             OMRSAuditLogRecordSeverity.SECURITY,
             "User {0} is not authorized to use connection {1}",
             "The security service detected an unauthorized access to an asset.",
             "Review the security policies and settings to determine if this access should be allowed or not." +
                                     "  Take action to either change the security sessions or determine the reason for the unauthorized request."),

    UNAUTHORIZED_ASSET_ACCESS("OPEN-METADATA-SECURITY-0011",
                             OMRSAuditLogRecordSeverity.SECURITY,
                             "User {0} is not authorized to access asset {1}",
                             "The security service detected an unauthorized access to an asset.",
                             "Review the security policies and settings to determine if this access should be allowed or not." +
                                     "  Take action to either change the security sessions or determine the reason for the unauthorized request."),

    UNAUTHORIZED_ASSET_CHANGE("OPEN-METADATA-SECURITY-0012",
                              OMRSAuditLogRecordSeverity.SECURITY,
                              "User {0} is not authorized to change asset {1}",
                              "The security service detected an unauthorized access to an asset.",
                              "Review the security policies and settings to determine if this access should be allowed or not." +
                                      "  Take action to either change the security sessions or determine the reason for the unauthorized request."),

    INCOMPLETE_ASSET(         "OPEN-METADATA-SECURITY-0013 ",
                              OMRSAuditLogRecordSeverity.SECURITY,
                              "User {0} is not authorized to change asset {1} because it has missing properties",
                              "The system is unable to process a request from the user because the asset is not correctly or completely filled out.",
                              "The request fails with a UserNotAuthorizedException exception."),

    UNAUTHORIZED_TYPE_ACCESS("OPEN-METADATA-SECURITY-0014",
                              OMRSAuditLogRecordSeverity.SECURITY,
                              "User {0} is not authorized to access open metadata type {1} ({2}) on server {3}",
                              "The security service detected an unauthorized access of an open metadata type.",
                              "Review the security policies and settings to determine if this access should be allowed or not." +
                                      "  Take action to either change the security sessions or determine the reason for the unauthorized request."),

    UNAUTHORIZED_TYPE_CHANGE("OPEN-METADATA-SECURITY-0015",
                             OMRSAuditLogRecordSeverity.SECURITY,
                             "User {0} is not authorized to change open metadata type {1} ({2}) on server {3}",
                             "The security service detected an unauthorized change of an open metadata type.",
                             "Review the security policies and settings to determine if this access should be allowed or not." +
                                     "  Take action to either change the security sessions or determine the reason for the unauthorized request."),

    UNAUTHORIZED_INSTANCE_CREATE("OPEN-METADATA-SECURITY-0016",
                                 OMRSAuditLogRecordSeverity.SECURITY,
                                 "User {0} is not authorized to create an open metadata instance of type {1} on server {2}",
                                 "The security service detected an unauthorized access of an open metadata type.",
                                 "Review the security policies and settings to determine if this access should be allowed or not." +
                                         "  Take action to either change the security sessions or determine the reason for the unauthorized request."),

    UNAUTHORIZED_INSTANCE_ACCESS("OPEN-METADATA-SECURITY-0017",
                             OMRSAuditLogRecordSeverity.SECURITY,
                             "User {0} is not authorized to access open metadata instance {1} of type {2} on server {3}",
                             "The security service detected an unauthorized access of an open metadata type.",
                             "Review the security policies and settings to determine if this access should be allowed or not." +
                                     "  Take action to either change the security sessions or determine the reason for the unauthorized request."),

    UNAUTHORIZED_INSTANCE_CHANGE("OPEN-METADATA-SECURITY-0018",
                             OMRSAuditLogRecordSeverity.SECURITY,
                             "User {0} is not authorized to change open metadata type {1} of type {2} on server {3} using method {4}",
                             "The security service detected an unauthorized change of an open metadata instance.",
                             "Review the security policies and settings to determine if this access should be allowed or not." +
                                     "  Take action to either change the security sessions or determine the reason for the unauthorized request."),
    ;

    private String                     logMessageId;
    private OMRSAuditLogRecordSeverity severity;
    private String                     logMessage;
    private String                     systemAction;
    private String                     userAction;

    private static final Logger log = LoggerFactory.getLogger(OpenMetadataSecurityAuditCode.class);


    /**
     * The constructor for DiscoveryEngineAuditCode expects to be passed one of the enumeration rows defined in
     * DiscoveryEngineAuditCode above.   For example:
     *
     *     DiscoveryEngineAuditCode   auditCode = DiscoveryEngineAuditCode.SERVER_NOT_AVAILABLE;
     *
     * This will expand out to the 4 parameters shown below.
     *
     * @param messageId - unique Id for the message
     * @param severity - severity of the message
     * @param message - text for the message
     * @param systemAction - description of the action taken by the system when the condition happened
     * @param userAction - instructions for resolving the situation, if any
     */
    OpenMetadataSecurityAuditCode(String                     messageId,
                                  OMRSAuditLogRecordSeverity severity,
                                  String                     message,
                                  String                     systemAction,
                                  String                     userAction)
    {
        this.logMessageId = messageId;
        this.severity = severity;
        this.logMessage = message;
        this.systemAction = systemAction;
        this.userAction = userAction;
    }


    /**
     * Returns the unique identifier for the error message.
     *
     * @return logMessageId
     */
    public String getLogMessageId()
    {
        return logMessageId;
    }


    /**
     * Return the severity of the audit log record.
     *
     * @return OMRSAuditLogRecordSeverity enum
     */
    public OMRSAuditLogRecordSeverity getSeverity()
    {
        return severity;
    }

    /**
     * Returns the log message with the placeholders filled out with the supplied parameters.
     *
     * @param params - strings that plug into the placeholders in the logMessage
     * @return logMessage (formatted with supplied parameters)
     */
    public String getFormattedLogMessage(String... params)
    {
        if (log.isDebugEnabled())
        {
            log.debug(String.format("<== OpenMetadataSecurityAuditCode.getMessage(%s)", Arrays.toString(params)));
        }

        MessageFormat mf = new MessageFormat(logMessage);
        String result = mf.format(params);

        if (log.isDebugEnabled())
        {
            log.debug(String.format("==> OpenMetadataSecurityAuditCode.getMessage(%s): %s", Arrays.toString(params), result));
        }

        return result;
    }



    /**
     * Returns a description of the action taken by the system when the condition that caused this exception was
     * detected.
     *
     * @return systemAction String
     */
    public String getSystemAction()
    {
        return systemAction;
    }


    /**
     * Returns instructions of how to resolve the issue reported in this exception.
     *
     * @return userAction String
     */
    public String getUserAction()
    {
        return userAction;
    }
}
