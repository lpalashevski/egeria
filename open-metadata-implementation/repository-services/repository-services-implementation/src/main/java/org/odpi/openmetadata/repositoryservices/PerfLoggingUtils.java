package org.odpi.openmetadata.repositoryservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class PerfLoggingUtils {
    private static final Logger log = LoggerFactory.getLogger(PerfLoggingUtils.class);

    public static void logTimeElapsed(long start, String reqIdKey,String message) {
        log.debug("{} {} {}", MDC.get(reqIdKey)!=null?MDC.get(reqIdKey):"-",message,System.currentTimeMillis()-start);
    }
}