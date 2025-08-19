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

package org.picketlink.common;


import org.picketlink.common.exceptions.ConfigurationException;
import org.picketlink.common.exceptions.ParsingException;
//rls import org.picketlink.common.exceptions.ProcessingException;
/** rls
import org.picketlink.common.exceptions.TrustKeyConfigurationException;
import org.picketlink.common.exceptions.TrustKeyProcessingException;
import org.picketlink.common.exceptions.fed.AssertionExpiredException;
import org.picketlink.common.exceptions.fed.IssueInstantMissingException;
import org.picketlink.common.exceptions.fed.IssuerNotTrustedException;
import org.picketlink.common.exceptions.fed.SignatureValidationException;
import org.picketlink.common.exceptions.fed.WSTrustException;
import org.w3c.dom.Element;

import javax.security.auth.login.LoginException;
import javax.xml.crypto.dsig.XMLSignatureException;
**/
import javax.xml.stream.Location;
/** rls
import javax.xml.ws.WebServiceException;
import java.io.IOException;
**/
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
     * <p>Creates an {@link IllegalArgumentException} for arguments that should not be the same.</p>
     *
     * @param string
     *
     * @return
     */
    //rls IllegalArgumentException shouldNotBeTheSameError(String string);

    /**
     * <p>Creates an {@link ProcessingException} for resources that are not found.</p>
     *
     * @param resource
     *
     * @return
     */
    //rls ProcessingException resourceNotFound(String resource);

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
     * <p>Creates an {@link RuntimeException} for not supported types.</p>
     *
     * @param name
     *
     * @return
     */
    //rls RuntimeException unsupportedType(String name);

    /**
     * <p>Creates a {@link ProcessingException} for exceptions raised during signature processing.</p>
     *
     * @param e
     *
     * @return
     */
    //rls XMLSignatureException signatureError(Throwable e);

    /**
     * <p>Creates a {@link RuntimeException} for null values.</p>
     *
     * @param nullValue
     *
     * @return
     */
    RuntimeException nullValueError(String nullValue);

    /**
     * <p>Creates a {@link RuntimeException} for not implemented methods or features.</p>
     *
     * @param string
     *
     * @return
     */
    //rls RuntimeException notImplementedYet(String string);

    /**
     * <p>Creates a {@link IllegalStateException} for the case the Audit Manager is null.</p>
     *
     * @return
     */
    //rls IllegalStateException auditNullAuditManager();

    /**
     * <p>Indicates if the logging level is set to INFO.</p>
     *
     * @return
     */
    //rls boolean isInfoEnabled();

    /**
     * <p>Logs a PicketLink Audit Event.</p>
     *
     * @param auditEvent
     */
    //rls void auditEvent(String auditEvent);

    /**
     * <p>Creates a {@link RuntimeException} for missing values.</p>
     *
     * @param string
     *
     * @return
     */
    //rls  RuntimeException injectedValueMissing(String value);

    /** <p>Logs a message during the KeyStore setup.</p> */
    //rls void keyStoreSetup();

    /**
     * <p>Creates a {@link IllegalStateException} for the case where the KeyStore is null.</p>
     *
     * @return
     */
    //rls IllegalStateException keyStoreNullStore();

    /**
     * <p>Logs a message for the cases where no public key was found for a given alias.</p>
     *
     * @param alias
     */
    //rls void keyStoreNullPublicKeyForAlias(String alias);

    /**
     * <p>Creates a {@link TrustKeyConfigurationException} for exceptions raised during the KeyStore configuration.</p>
     *
     * @param t
     *
     * @return
     */
    //rls TrustKeyConfigurationException keyStoreConfigurationError(Throwable t);

    /**
     * <p>Creates a {@link TrustKeyConfigurationException} for exceptions raised during the KeyStore processing.</p>
     *
     * @param t
     *
     * @return
     */
    //rls TrustKeyProcessingException keyStoreProcessingError(Throwable t);

    /**
     * @param domain
     *
     * @return
     */
    //rls IllegalStateException keyStoreMissingDomainAlias(String domain);

    /**
     * <p>Creates a {@link RuntimeException} for the case where the signing key password is null.</p>
     *
     * @return
     */
    //rls RuntimeException keyStoreNullSigningKeyPass();

    //rls RuntimeException keyStoreNullEncryptionKeyPass();

    /**
     * <p>Creates a {@link RuntimeException} for the case where key store are not located.</p>
     *
     * @param keyStore
     *
     * @return
     */
    //rls  RuntimeException keyStoreNotLocated(String keyStore);

    /**
     * <p>Creates a {@link IllegalStateException} for the case where the alias is null.</p>
     *
     * @return
     */
    //rls IllegalStateException keyStoreNullAlias();

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
     * @param elementName
     * @param location
     *
     * @return
     */
    //rls RuntimeException parserUnknownStartElement(String elementName, Location location);

    /** @return  */
    //rls  IllegalStateException parserNullStartElement();

    /**
     * @param xsiTypeValue
     *
     * @return
     */
    //rls  ParsingException parserUnknownXSI(String xsiTypeValue);

    /**
     * @param string
     *
     * @return
     */
    //rls  ParsingException parserExpectedEndTag(String tagName);

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
     * @param elementName
     *
     * @return
     */
    //rls   RuntimeException parserFailed(String elementName);

    /** @return  */
    //rls   ParsingException parserUnableParsingNullToken();

    /**
     * @param t
     *
     * @return
     */
    ParsingException parserError(Throwable t);

    /**
     * @param e
     *
     * @return
     */
    //rls  RuntimeException xacmlPDPMessageProcessingError(Throwable t);

    /**
     * @param policyConfigFileName
     *
     * @return
     */
    //rls  IllegalStateException fileNotLocated(String policyConfigFileName);

    /**
     * @param string
     *
     * @return
     */
    //rls  IllegalStateException optionNotSet(String option);

    /**
     *
     */
    //rls   void stsTokenRegistryNotSpecified();

    /** @param tokenRegistryOption */
    //rls   void stsTokenRegistryInvalidType(String tokenRegistryOption);

    /**
     *
     */
    //rls   void stsTokenRegistryInstantiationError();

    /**
     *
     */
    //rls   void stsRevocationRegistryNotSpecified();

    /** @param registryOption */
    //rls  void stsRevocationRegistryInvalidType(String registryOption);

    /**
     *
     */
    //rls   void stsRevocationRegistryInstantiationError();

    /** @return  */
    GeneralSecurityException samlAssertionExpiredError();

    /** @return  */
    //rls  ProcessingException assertionInvalidError();

    /**
     * @param name
     *
     * @return
     */
    //rls RuntimeException writerUnknownTypeError(String name);

    /**
     * @param string
     *
     * @return
     */
    //rls  ProcessingException writerNullValueError(String value);

    /**
     * @param value
     *
     * @return
     */
    //rls  RuntimeException writerUnsupportedAttributeValueError(String value);

    /** @return  */
    //rls   IllegalArgumentException issuerInfoMissingStatusCodeError();

    /**
     * @param fqn
     *
     * @return
     */
    //rls   ProcessingException classNotLoadedError(String fqn);

    /**
     * @param fqn
     * @param e
     *
     * @return
     */
    //rls   ProcessingException couldNotCreateInstance(String fqn, Throwable t);

    /**
     * @param property
     *
     * @return
     */
    RuntimeException systemPropertyMissingError(String property);

    /** @param t */
    //rls  void samlMetaDataIdentityProviderLoadingError(Throwable t);

    /** @param t */
    //rls   void samlMetaDataServiceProviderLoadingError(Throwable t);

    /** @param t */
    //rls   void signatureAssertionValidationError(Throwable t);

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
     * @param string
     * @param t
     */
    //rls    void trace(String message, Throwable t);

    /**
     * @param algo
     *
     * @return
     */
    //rls   RuntimeException signatureUnknownAlgo(String algo);

    /**
     * @param message
     *
     * @return
     */
    IllegalArgumentException invalidArgumentError(String message);

    /**
     * @param configuration
     * @param protocolContext
     *
     * @return
     */
    //rls   ProcessingException stsNoTokenProviderError(String configuration, String protocolContext);

    /** @param message */
    void debug(String message);

    /** @param fileName */
    //rls   void stsConfigurationFileNotFoundTCL(String fileName);

    /** @param fileName */
    //rls   void stsConfigurationFileNotFoundClassLoader(String fileName);

    /** @param fileName */
    //rls   void stsUsingDefaultConfiguration(String fileName);

    /** @param fileName */
    //rls   void stsConfigurationFileLoaded(String fileName);

    /**
     * @param t
     *
     * @return
     */
    //rls   ConfigurationException stsConfigurationFileParsingError(Throwable t);

    /**
     * @param message
     *
     * @return
     */
    //rls   IOException notSerializableError(String message);

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

    /** @param t */
    //rls  void xmlCouldNotGetSchema(Throwable t);

    /** @return  */
    boolean isTraceEnabled();

    /** @return  */
    boolean isDebugEnabled();

    /**
     * @param name
     * @param t
     */
    //rls    void jceProviderCouldNotBeLoaded(String name, Throwable t);

    /** @return  */
    //rls   ProcessingException writerInvalidKeyInfoNullContentError();

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

    /**
     * @param certAlgo
     *
     * @return
     */
    //rls   RuntimeException encryptUnknownAlgoError(String certAlgo);

    /**
     * @param element
     *
     * @return
     */
    //rls  IllegalStateException domMissingDocElementError(String element);

    /**
     * @param element
     *
     * @return
     */
    //rls   IllegalStateException domMissingElementError(String element);

    /** @return  */
    //rls   WebServiceException stsWSInvalidTokenRequestError();

    /**
     * @param t
     *
     * @return
     */
    //rls    WebServiceException stsWSError(Throwable t);

    /**
     * @param t
     *
     * @return
     */
    //rls    WebServiceException stsWSConfigurationError(Throwable t);

    /**
     * @param requestType
     *
     * @return
     */
    //rls    WSTrustException stsWSInvalidRequestTypeError(String requestType);

    /**
     * @param t
     *
     * @return
     */
    //rls   WebServiceException stsWSHandlingTokenRequestError(Throwable t);

    /**
     * @param t
     *
     * @return
     */
    //rls   WebServiceException stsWSResponseWritingError(Throwable t);

    /**
     * @param t
     *
     * @return
     */
    //rls  RuntimeException stsUnableToConstructKeyManagerError(Throwable t);

    /**
     * @param serviceName
     * @param t
     *
     * @return
     */
    //rls  RuntimeException stsPublicKeyError(String serviceName, Throwable t);

    /**
     * @param t
     *
     * @return
     */
    //rls   RuntimeException stsSigningKeyPairError(Throwable t);

    /**
     * @param t
     *
     * @return
     */
    //rls  RuntimeException stsPublicKeyCertError(Throwable t);

    /**
     *
     */
    //rls   void stsTokenTimeoutNotSpecified();

    /**
     * @param t
     *
     * @return
     */
    //rls   WSTrustException wsTrustCombinedSecretKeyError(Throwable t);

    /** @return  */
    //rls    WSTrustException wsTrustClientPublicKeyError();

    /**
     * @param t
     *
     * @return
     */
    //rls   WSTrustException stsError(Throwable t);

    /**
     * @param message
     * @param t
     *
     * @return
     */
    //rls    XMLSignatureException signatureInvalidError(String message, Throwable t);

    /**
     *
     */
    //rls   void stsSecurityTokenSignatureNotVerified();

    /**
     * @param e
     *
     * @return
     */
    //rls   RuntimeException encryptProcessError(Throwable t);

    /**
     *
     */
    //rls    void stsSecurityTokenShouldBeEncrypted();

    /**
     * @param password
     *
     * @return
     */
    //rls   RuntimeException unableToDecodePasswordError(String password);

    /**
     * @param configFile
     *
     * @return
     */
    //rls  IllegalStateException couldNotLoadProperties(String configFile);

    /**
     * @param t
     *
     * @return
     */
    //rls    WSTrustException stsKeyInfoTypeCreationError(Throwable t);

    /**
     *
     */
    //rls    void stsSecretKeyNotEncrypted();

    /** @return  */
    //rls    LoginException authCouldNotIssueSAMLToken();

    /**
     * @param t
     *
     * @return
     */
    //rls   LoginException authLoginError(Throwable t);

    /**
     * @param e
     *
     * @return
     */
    //rls   IllegalStateException authCouldNotCreateWSTrustClient(Throwable t);

    /** @param id */
    //rls   void samlAssertionWithoutExpiration(String id);

    /**
     * @param token
     *
     * @return
     */
    //rls  LoginException authCouldNotValidateSAMLToken(Element token);

    /** @return  */
    //rls   LoginException authCouldNotLocateSecurityToken();

    /** @return  */
    //rls   ProcessingException wsTrustNullCancelTargetError();

    /**
     * @param t
     *
     * @return
     */
    //rls   ProcessingException samlAssertionMarshallError(Throwable t);

    /** @return  */
    //rls    ProcessingException wsTrustNullRenewTargetError();

    /**
     * @param t
     *
     * @return
     */
    //rls    ProcessingException samlAssertionUnmarshallError(Throwable t);

    /** @return  */
    //rls   ProcessingException samlAssertionRevokedCouldNotRenew(String id);

    /** @return  */
    //rls    ProcessingException wsTrustNullValidationTargetError();

    /** @param attributeProviderClassName */
    //rls    void stsWrongAttributeProviderTypeNotInstalled(String attributeProviderClassName);

    /** @param t */
    //rls    void attributeProviderInstationError(Throwable t);

    /** @param nodeAsString */
    //rls    void samlAssertion(String nodeAsString);

    /**
     * @param dce
     *
     * @return
     */
    //rls   RuntimeException wsTrustUnableToGetDataTypeFactory(Throwable t);

    /** @return  */
    GeneralSecurityException wsTrustValidationStatusCodeMissing();

    /** @param activeSessionCount */
    //rls    void samlIdentityServerActiveSessionCount(int activeSessionCount);

    /**
     * @param id
     * @param activeSessionCount
     */
    //rls   void samlIdentityServerSessionCreated(String id, int activeSessionCount);

    /**
     * @param id
     * @param activeSessionCount
     */
    //rls    void samlIdentityServerSessionDestroyed(String id, int activeSessionCount);

    /**
     * @param name
     *
     * @return
     */
    //rls   RuntimeException unknowCredentialType(String name);

    /** @param t */
    //rls   void samlHandlerRoleGeneratorSetupError(Throwable t);

    /** @return  */
    //rls   RuntimeException samlHandlerAssertionNotFound();

    /** @return  */
    //rls    ProcessingException samlHandlerAuthnRequestIsNull();

    /** @param t */
    //rls  void samlHandlerAuthenticationError(Throwable t);

    /** @return  */
    //rls  IllegalArgumentException samlHandlerNoAssertionFromIDP();

    /** @return  */
    //rls   ProcessingException samlHandlerNullEncryptedAssertion();

    /** @return  */
    //rls    SecurityException samlHandlerIDPAuthenticationFailedError();

    /**
     * @param aee
     *
     * @return
     */
    //rls   ProcessingException assertionExpiredError(AssertionExpiredException aee);

    /**
     * @param attrValue
     *
     * @return
     */
    //rls   RuntimeException unsupportedRoleType(Object attrValue);

    /**
     * @param inResponseTo
     * @param authnRequestId
     */
    //rls    void samlHandlerFailedInResponseToVerification(String inResponseTo, String authnRequestId);

    /** @return  */
    //rls   ProcessingException samlHandlerFailedInResponseToVerificarionError();

    /**
     * @param issuer
     *
     * @return
     */
    //rls    IssuerNotTrustedException samlIssuerNotTrustedError(String issuer);

    /**
     * @param e
     *
     * @return
     */
    //rls    IssuerNotTrustedException samlIssuerNotTrustedException(Throwable t);

    /** @return  */
    //rls   ConfigurationException samlHandlerTrustElementMissingError();

    /** @return  */
    //rls   ProcessingException samlHandlerIdentityServerNotFoundError();

    /** @return  */
    //rls  ProcessingException samlHandlerPrincipalNotFoundError();

    /**
     *
     */
    //rls   void samlHandlerKeyPairNotFound();

    /** @return  */
    //rls   ProcessingException samlHandlerKeyPairNotFoundError();

    /** @param t */
    //rls   void samlHandlerErrorSigningRedirectBindingMessage(Throwable t);

    /**
     * @param t
     *
     * @return
     */
    //rls   RuntimeException samlHandlerSigningRedirectBindingMessageError(Throwable t);

    /** @return  */
    //rls   SignatureValidationException samlHandlerSignatureValidationFailed();

    /** @param t */
    //rls   void samlHandlerErrorValidatingSignature(Throwable t);

    /** @return  */
    //rls   ProcessingException samlHandlerInvalidSignatureError();

    /** @return  */
    //rls   ProcessingException samlHandlerSignatureNotPresentError();

    /**
     * @param t
     *
     * @return
     */
    //rls   ProcessingException samlHandlerSignatureValidationError(Throwable t);

    /** @param t */
    void error(Throwable t);

    /**
     * @param t
     *
     * @return
     */
    //rls   RuntimeException samlHandlerChainProcessingError(Throwable t);

    /** @return  */
    //rls  TrustKeyConfigurationException trustKeyManagerMissing();

    /** @param rte */
    //rls  void samlBase64DecodingError(Throwable t);

    /** @param t */
    //rls   void samlParsingError(Throwable t);

    /** @param t */
    //rls   void trace(Throwable t);

    /**
     *
     */
    //rls   void mappingContextNull();

    /** @param t */
    //rls    void attributeManagerError(Throwable t);

    /**
     *
     */
    //rls  void couldNotObtainSecurityContext();

    /**
     * @param t
     *
     * @return
     */
    //rls   LoginException authFailedToCreatePrincipal(Throwable t);

    /**
     * @param class1
     *
     * @return
     */
    //rls  LoginException authSharedCredentialIsNotSAMLCredential(String className);

    /** @return  */
    //rls   LoginException authSTSConfigFileNotFound();

    /**
     * @param t
     *
     * @return
     */
    //rls  LoginException authErrorHandlingCallback(Throwable t);

    //rls   LoginException authInvalidSAMLAssertionBySTS();

    /**
     * @param t
     *
     * @return
     */
    //rls   LoginException authAssertionValidationError(Throwable t);

    /**
     * @param t
     *
     * @return
     */
    //rls   LoginException authFailedToParseSAMLAssertion(Throwable t);

    /** @param t */
    void samlAssertionPasingFailed(Throwable t);

    //rls   LoginException authNullKeyStoreFromSecurityDomainError(String name);

    //rls  LoginException authNullKeyStoreAliasFromSecurityDomainError(String name);

    //rls LoginException authNoCertificateFoundForAliasError(String alias, String name);

    //rls LoginException authSAMLInvalidSignatureError();

    //rls  LoginException authSAMLAssertionExpiredError();

    /** @param t */
    //rls  void authSAMLAssertionIssuingFailed(Throwable t);

    /** @param t */
    //rls  void jbossWSUnableToCreateBinaryToken(Throwable t);

    /**
     *
     */
    //rls void jbossWSUnableToCreateSecurityToken();

    //rls  void jbossWSUnableToWriteSOAPMessage(Throwable t);

    /** @return  */
    RuntimeException jbossWSUnableToLoadJBossWSSEConfigError();

    /** @return  */
    //rls   RuntimeException jbossWSAuthorizationFailed();

    /** @param t */
    void jbossWSErrorGettingOperationName(Throwable t);

    /** @return  */
    //rls  LoginException authSAMLCredentialNotAvailable();

    /**
     * @param token
     * @param t
     *
     * @return
    //rls  RuntimeException authUnableToInstantiateHandler(String token, Throwable t);

    /**
     * @param e1
     *
     * @return
    //rls   RuntimeException jbossWSUnableToCreateSSLSocketFactory(Throwable t);

    /** @return  */
    //rls   RuntimeException jbossWSUnableToFindSSLSocketFactory();

    /** @return  */
    //rls   RuntimeException authUnableToGetIdentityFromSubject();

    /** @return  */
    //rls   RuntimeException authSAMLAssertionNullOrEmpty();

    /** @return  */
    //rls ProcessingException jbossWSUncheckedAndRolesCannotBeTogether();

    /** @param t */
    //rls  void samlIDPHandlingSAML11Error(Throwable t);

    /** @return  */
    //rls   GeneralSecurityException samlIDPValidationCheckFailed();

    /** @param t */
    //rls   void samlIDPRequestProcessingError(Throwable t);

    /** @param t */
    //rls   void samlIDPUnableToSetParticipantStackUsingDefault(Throwable t);

    /** @param t */
    //rls   void samlHandlerConfigurationError(Throwable t);

    /** @param canonicalizationMethod */
    //rls   void samlIDPSettingCanonicalizationMethod(String canonicalizationMethod);

    /**
     * @param t
     *
     * @return
     */
    //rls   RuntimeException samlIDPConfigurationError(Throwable t);

    /**
     * @param configFile
     *
     * @return
     */
    //rls  RuntimeException configurationFileMissing(String configFile);

    /**
     *
     */
    //rls   void samlIDPInstallingDefaultSTSConfig();

    //rls   void samlSPFallingBackToLocalFormAuthentication();

    /**
     * @param ex
     *
     * @return
     */
    //rls   IOException unableLocalAuthentication(Throwable t);

    /**
     *
     */
    //rls  void samlSPUnableToGetIDPDescriptorFromMetadata();

    /**
     * @param t
     *
     * @return
     */
    //rls   RuntimeException samlSPConfigurationError(Throwable t);

    /** @param canonicalizationMethod */
    //rls   void samlSPSettingCanonicalizationMethod(String canonicalizationMethod);

    /** @param logOutPage */
    //rls   void samlSPCouldNotDispatchToLogoutPage(String logOutPage);

    /**
     * <p>Logs the implementation being used to log messages and exceptions.</p>
     *
     * @param name
     */
    void usingLoggerImplementation(String className);

    /**
     *
     */
    //rls   void samlResponseFromIDPParsingFailed();

    /**
     * @param t
     *
     * @return
     */
    //rls   ConfigurationException auditSecurityDomainNotFound(Throwable t);

    /**
     * @param location
     * @param t
     *
     * @return
     */
    //rls   ConfigurationException auditAuditManagerNotFound(String location, Throwable t);

    /** @return  */
    //rls   IssueInstantMissingException samlIssueInstantMissingError();

    /**
     * @param response
     *
     * @return
     */
    //rls   RuntimeException samlSPResponseNotCatalinaResponseError(Object response);

    /** @param t */
    //rls   void samlLogoutError(Throwable t);

    /** @param t */
    //rls   void samlErrorPageForwardError(String errorPage, Throwable t);

    /** @param t */
    //rls   void samlSPHandleRequestError(Throwable t);

    /**
     * @param t
     *
     * @return
     */
    //rls   IOException samlSPProcessingExceptionError(Throwable t);

    /** @return  */
    //rls    IllegalArgumentException samlInvalidProtocolBinding();

    /** @return  */
    //rls   IllegalStateException samlHandlerServiceProviderConfigNotFound();

    /**
     *
     */
    //rls  void samlSecurityTokenAlreadyPersisted(String id);

    /** @param id */
    //rls   void samlSecurityTokenNotFoundInRegistry(String id);

    IllegalArgumentException samlMetaDataFailedToCreateCacheDuration(String timeValue);

    //rls   ConfigurationException samlMetaDataNoIdentityProviderDefined();

    //rls   ConfigurationException samlMetaDataNoServiceProviderDefined();

    ConfigurationException securityDomainNotFound();

    //rls   void authenticationManagerError(ConfigurationException e);

    //rls    void authorizationManagerError(ConfigurationException e);

    //rls   IllegalStateException jbdcInitializationError(Throwable throwable);

    //rls  RuntimeException errorUnmarshallingToken(Throwable e);

    //rls  RuntimeException runtimeException(String msg, Throwable e);

    //rls   IllegalStateException datasourceIsNull();

    //rls   IllegalArgumentException cannotParseParameterValue(String parameter, Throwable e);

    //rls   RuntimeException cannotGetFreeClientPoolKey(String key);

    //rls  RuntimeException cannotGetSTSConfigByKey(String key);

    //rls  RuntimeException cannotGetUsedClientsByKey(String key);

    //rls  RuntimeException removingNonExistingClientFromUsedClientsByKey(String key);

    //rls  RuntimeException freePoolAlreadyContainsGivenKey(String key);

    //rls  RuntimeException maximumNumberOfClientsReachedforPool(String max);

    //rls  RuntimeException cannotSetMaxPoolSizeToNegative(String max);

    RuntimeException parserFeatureNotSupported(String feature);

    //rls   ProcessingException samlAssertionWrongAudience(String serviceURL);
}