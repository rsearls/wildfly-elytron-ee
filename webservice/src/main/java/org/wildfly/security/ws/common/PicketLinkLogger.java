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


import org.wildfly.security.ws.common.exceptions.ConfigurationException;
import org.wildfly.security.ws.common.exceptions.ParsingException;

import javax.xml.stream.Location;
import java.security.GeneralSecurityException;

/**
 * <p>This interface acts as a Log Facade for PicketLink, from which exceptions and messages should be created or
 * logged.</p> <p>As PicketLink supports multiple containers and its versions, the main objective of this interface is
 * to abstract the logging aspects from the code and provide different logging implementations for each supported
 * binding/container.</p>
 *
 * @author <a href="mailto:psilva@redhat.com">Pedro Silva</a>
 * @see {@link PicketLinkLoggerFactory}
 */
public interface PicketLinkLogger {

    /**
     * <p>Creates an {@link IllegalArgumentException} for null arguments.</p>
     *
     * @param argument
     *
     * @return
     */
     IllegalArgumentException nullArgumentError(String argument);

    /**
     * <p>Creates an {@link ProcessingException} for generics processing errors.</p>
     *
     * @param message
     * @param t
     *
     * @return
     */
    GeneralSecurityException processingError(Throwable t);

    /**
     * <p>Creates a {@link RuntimeException} for null values.</p>
     *
     * @param nullValue
     *
     * @return
     */
    RuntimeException nullValueError(String nullValue);

    /**
     * <p>Creates a {@link RuntimeException} for the case where parser founds a unknown end element.</p>
     *
     * @param endElementName
     *
     * @return
     */
    RuntimeException parserUnknownEndElement(String endElementName);

    /**
     * @param tag
     * @param location
     *
     * @return
     */
    RuntimeException parserUnknownTag(String tag, Location location);

    /**
     * @param string
     *
     * @return
     */
    ParsingException parserRequiredAttribute(String string);

    /**
     * @param e
     *
     * @return
     */
    ParsingException parserException(Throwable t);

    /**
     * @param string
     *
     * @return
     */
    ParsingException parserExpectedTextValue(String string);

    /**
     * @param expectedXsi
     *
     * @return
     */
    RuntimeException parserExpectedXSI(String expectedXsi);

    /**
     * @param tag
     * @param foundElementTag
     *
     * @return
     */
    RuntimeException parserExpectedTag(String tag, String foundElementTag);

    /**
     * @param t
     *
     * @return
     */
    ParsingException parserError(Throwable t);

    /** @return  */
    GeneralSecurityException samlAssertionExpiredError();

    /**
     * @param property
     *
     * @return
     */
    RuntimeException systemPropertyMissingError(String property);

    /** @param id */
    void samlAssertionExpired(String id);

    /**
     * @param attrValue
     *
     * @return
     */
    RuntimeException unknownObjectType(Object attrValue);

    /**
     * @param e
     *
     * @return
     */
    ConfigurationException configurationError(Throwable t);

    /** @param message */
    void trace(String message);

    /**
     * @param message
     *
     * @return
     */
    IllegalArgumentException invalidArgumentError(String message);

    /** @param message */
    void debug(String message);

    /**
     *
     */
    void trustKeyManagerCreationError(Throwable t);

    /** @param message */
    void info(String message);

    /** @param string */
    void warn(String message);

    /** @param message */
    void error(String message);

    /** @return  */
    boolean isTraceEnabled();

    /** @return  */
    boolean isDebugEnabled();

    /**
     * @param first
     * @param second
     *
     * @return
     */
    RuntimeException notEqualError(String first, String second);

    /**
     * @param message
     *
     * @return
     */
    IllegalArgumentException wrongTypeError(String message);

    /** @return  */
    GeneralSecurityException wsTrustValidationStatusCodeMissing();

    /** @param t */
    void error(Throwable t);

    /** @param t */
    void samlAssertionPasingFailed(Throwable t);

    /** @return  */
    RuntimeException jbossWSUnableToLoadJBossWSSEConfigError();

    /** @param t */
    void jbossWSErrorGettingOperationName(Throwable t);

    /**
     * <p>Logs the implementation being used to log messages and exceptions.</p>
     *
     * @param name
     */
    void usingLoggerImplementation(String className);

    IllegalArgumentException samlMetaDataFailedToCreateCacheDuration(String timeValue);

    ConfigurationException securityDomainNotFound();

    RuntimeException parserFeatureNotSupported(String feature);

}