/*
 *JBoss, Home of Professional Open Source
 *
 *Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */

package org.wildfly.security.ws.common;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.ValidIdRange;
import org.jboss.logging.annotations.ValidIdRanges;

import org.wildfly.security.ws.common.exceptions.ConfigurationException;
import org.wildfly.security.ws.common.exceptions.ParsingException;

import java.security.GeneralSecurityException;

@MessageLogger(projectCode = "ELY", length = 5)
@ValidIdRanges({
    @ValidIdRange(min = 1200, max = 1250)
})
public interface ElytronMessages extends BasicLogger {
    ElytronMessages log = Logger.getMessageLogger(ElytronMessages.class, "org.wildfly.security.ws");

    @Message(id = 1200, value = "Null Parameter: [%s]")
    IllegalArgumentException nullArgumentError(String argument);

    @Message(id = 1201, value = "Processing Exception ")
    GeneralSecurityException processingError(@Cause Throwable t);

    @Message(id = 1202, value = "Null Parameter: [%s]")
    RuntimeException nullValueError(String nullValue);

    @Message(id = 1203, value = "Parsing Error: [%s]")
    ParsingException parserError(String s, @Cause Throwable t);

    @Message(id = 1204, value = "Assertion has expired")
    GeneralSecurityException samlAssertionExpiredError();

    @Message(id = 1205, value = "System Property missing: [%s]")
    RuntimeException systemPropertyMissingError(String property);

    @Message(id = 1206, value = "Unknown Object Type: %s")
    RuntimeException unknownObjectType(Object attrValue);

    @Message(id = 1207, value = "")
    ConfigurationException configurationError(@Cause Throwable t);

    @Message(id = 1208, value = "%s")
    IllegalArgumentException invalidArgumentError(String message);

    @Message(id = 1209, value = "Not equal: [%s] and [%s]")
    RuntimeException notEqualError(String first, String second);

    @Message(id = 1210, value = "Resource not found: unable to load jboss-wsse.xml")
    RuntimeException jbossWSUnableToLoadJBossWSSEConfigError();

    @Message(id = 1211, value = "Cache duration could not be created using %s . This value must be an ISO-8601 period or a numeric value representing the duration in milliseconds.")
    IllegalArgumentException samlMetaDataFailedToCreateCacheDuration(String timeValue);

    @Message(id = 1212, value = "The security domain name could not be found. Check your jboss-web.xml.")
    ConfigurationException securityDomainNotFound();

    @Message(id = 1213, value = "Parser feature %s not supported.")
    RuntimeException parserFeatureNotSupported(String feature);

}