/*
 *JBoss, Home of Professional Open Source.
 *Copyright 2012, Red Hat, Inc., and individual contributors
 *as indicated by the @author tags. See the copyright.txt file in the
 *distribution for a full listing of individual contributors.
 *
 *This is free software; you can redistribute it and/or modify it
 *under the terms of the GNU Lesser General Public License as
 *published by the Free Software Foundation; either version 2.1 of
 *the License, or (at your option) any later version.
 *
 *This software is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *Lesser General Public License for more details.
 *
 *You should have received a copy of the GNU Lesser General Public
 *License along with this software; if not, write to the Free
 *Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 *02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.picketlink.common;

import org.jboss.logging.Logger;
import org.picketlink.common.exceptions.ConfigurationException;
import org.picketlink.common.exceptions.ParsingException;

import javax.xml.stream.Location;
import static org.picketlink.common.ErrorCodes.EXPECTED_TAG;
import static org.picketlink.common.ErrorCodes.REQD_ATTRIBUTE;
import static org.picketlink.common.ErrorCodes.UNKNOWN_TAG;
import java.security.GeneralSecurityException;

/**
 *@author <a href="mailto:psilva@redhat.com">Pedro Silva</a>
 *
 */

/**@author <a href="mailto:psilva@redhat.com">Pedro Silva</a> */
public class DefaultPicketLinkLogger implements PicketLinkLogger {

    private Logger logger = Logger.getLogger(PicketLinkLogger.class.getPackage().getName());

    DefaultPicketLinkLogger() {

    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#info(java.lang.String)
     */
    @Override
    public void info(String message) {
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#debug(java.lang.String)
     */
    @Override
    public void debug(String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#trace(java.lang.String)
     */
    @Override
    public void trace(String message) {
        if (logger.isTraceEnabled()) {
            logger.trace(message);
        }
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#error(java.lang.Throwable)
     */
    @Override
    public void error(Throwable t) {
        logger.error("Unexpected error", t);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#nullArgument(java.lang.String)
     */

    @Override
    public IllegalArgumentException nullArgumentError(String argument) {
        return new IllegalArgumentException(ErrorCodes.NULL_ARGUMENT + argument);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#processingError(java.lang.Throwable)
     */
    @Override
    public GeneralSecurityException processingError(Throwable t) {
        return new GeneralSecurityException(ErrorCodes.PROCESSING_EXCEPTION, t);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#nullValue(java.lang.String)
     */
    @Override
    public RuntimeException nullValueError(String nullValue) {
        return new RuntimeException(ErrorCodes.NULL_VALUE + nullValue);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#parserUnknownEndElement(java.lang.String)
     */
    @Override
    public RuntimeException parserUnknownEndElement(String endElementName) {
        return new RuntimeException(ErrorCodes.UNKNOWN_END_ELEMENT + endElementName);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#parseUnknownTag(java.lang.String, javax.xml.stream.Location)
     */
    @Override
    public RuntimeException parserUnknownTag(String tag, Location location) {
        return new RuntimeException(UNKNOWN_TAG + tag + "::location=" + location);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#parseRequiredAttribute(java.lang.String)
     */
    @Override
    public ParsingException parserRequiredAttribute(String string) {
        return new ParsingException(REQD_ATTRIBUTE + string);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#parserException(java.lang.Exception)
     */
    @Override
    public ParsingException parserException(Throwable t) {
        return new ParsingException(t);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#parserExpectedTextValue(java.lang.String)
     */
    @Override
    public ParsingException parserExpectedTextValue(String string) {
        return new ParsingException(ErrorCodes.EXPECTED_TEXT_VALUE + "SigningAlias");
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#parserExpectedXSI(java.lang.String)
     */
    @Override
    public RuntimeException parserExpectedXSI(String expectedXsi) {
        return new RuntimeException(expectedXsi);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#parserExpectedTag(java.lang.String, java.lang.String)
     */
    @Override
    public RuntimeException parserExpectedTag(String tag, String foundElementTag) {
        return new RuntimeException(EXPECTED_TAG + tag + ">.  Found <" + foundElementTag + ">");
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#parserError(java.lang.Exception)
     */
    @Override
    public ParsingException parserError(Throwable t) {
        return new ParsingException(ErrorCodes.PARSING_ERROR + t.getMessage(), t);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#assertionExpiredError()
     */
    @Override
    public GeneralSecurityException samlAssertionExpiredError() {
        return new GeneralSecurityException(ErrorCodes.EXPIRED_ASSERTION);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#systemPropertyMissingError(java.lang.String)
     */
    @Override
    public RuntimeException systemPropertyMissingError(String property) {
        return new RuntimeException(ErrorCodes.SYSTEM_PROPERTY_MISSING + property);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#assertionExpired(java.lang.String)
     */
    @Override
    public void samlAssertionExpired(String id) {
        this.info("Assertion has expired with id=" + id);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#unknownObjectType(java.lang.Object)
     */
    @Override
    public RuntimeException unknownObjectType(Object attrValue) {
        return new RuntimeException(ErrorCodes.UNKNOWN_OBJECT_TYPE + attrValue);
    }

    /*
     *(non-Javadoc)
     *
     *@see
     *org.picketlink.identity.federation.PicketLinkLogger#configurationError(javax.xml.parsers.ParserConfigurationException)
     */
    @Override
    public ConfigurationException configurationError(Throwable t) {
        return new ConfigurationException(t);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#invalidArgumentError(java.lang.String)
     */
    @Override
    public IllegalArgumentException invalidArgumentError(String message) {
        return new IllegalArgumentException(message);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#trustKeyCreationError()
     */
    @Override
    public void trustKeyManagerCreationError(Throwable t) {
        logger.error("Exception creating TrustKeyManager:", t);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#error(java.lang.String)
     */
    @Override
    public void error(String message) {
        logger.error(message);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#isTraceEnabled()
     */
    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#isDebugEnabled()
     */
    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#notEqualError(java.lang.String, java.lang.String)
     */
    @Override
    public RuntimeException notEqualError(String first, String second) {
        return new RuntimeException(ErrorCodes.NOT_EQUAL + first + " and " + second);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#wrongTypeError(java.lang.String)
     */
    @Override
    public IllegalArgumentException wrongTypeError(String message) {
        return new IllegalArgumentException(ErrorCodes.WRONG_TYPE + "xmlSource should be a stax source");
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#wsTrustValidationStatusCodeMissing()
     */
    @Override
    public GeneralSecurityException wsTrustValidationStatusCodeMissing() {
        return new GeneralSecurityException(ErrorCodes.NULL_VALUE + "Validation status code is missing");
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#authSAMLAssertionPasingFailed(java.lang.Throwable)
     */
    @Override
    public void samlAssertionPasingFailed(Throwable t) {
        logger.error("SAML Assertion parsing failed", t);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#jbossWSUnableToLoadJBossWSSEConfigError()
     */
    @Override
    public RuntimeException jbossWSUnableToLoadJBossWSSEConfigError() {
        return new RuntimeException(ErrorCodes.RESOURCE_NOT_FOUND + "unable to load jboss-wsse.xml");
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#jbossWSErrorGettingOperationName(java.lang.Throwable)
     */
    @Override
    public void jbossWSErrorGettingOperationName(Throwable t) {
        logger.error("Exception using backup method to get op name=", t);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#warn(java.lang.String)
     */
    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    /*
     *(non-Javadoc)
     *
     *@see org.picketlink.identity.federation.PicketLinkLogger#usingLoggerImplementation(java.lang.String)
     */
    @Override
    public void usingLoggerImplementation(String className) {
        logger.debugf("Using logger implementation: " + className);
    }

    /*(non-Javadoc)
     *@see org.picketlink.identity.federation.PicketLinkLogger#samlMetaDataFailedToCreateCacheDuration(java.lang.String)
     */
    @Override
    public IllegalArgumentException samlMetaDataFailedToCreateCacheDuration(String timeValue) {
        return new IllegalArgumentException("Cache duration could not be created using '" + timeValue
                + "'. This value must be an ISO-8601 period or a numeric value representing the duration in milliseconds.");
    }

    /*(non-Javadoc)
     *@see org.picketlink.identity.federation.PicketLinkLogger#securityDomainNotFound()
     */
    @Override
    public ConfigurationException securityDomainNotFound() {
        return new ConfigurationException("The security domain name could not be found. Check your jboss-web.xml.");
    }

    private void error(String msg, ConfigurationException e) {
        logger.error(msg, e);
    }

    public IllegalStateException jbdcInitializationError(Throwable throwable) {
        return new IllegalStateException(throwable);
    }

    public RuntimeException errorUnmarshallingToken(Throwable e) {
        return new RuntimeException(e);
    }

    public RuntimeException runtimeException(String msg, Throwable e) {
        return new RuntimeException(msg, e);
    }

    public IllegalStateException datasourceIsNull() {
        return new IllegalStateException();
    }

    @Override
    public RuntimeException parserFeatureNotSupported(String feature) {
        return new RuntimeException("Parser feature " + feature + " not supported.");
    }
}
