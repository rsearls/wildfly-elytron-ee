/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.picketlink.trust.jbossws.handler;

import org.picketlink.common.constants.JBossSAMLURIConstants;
import org.picketlink.common.util.StringUtil;
import org.picketlink.identity.federation.core.saml.v2.util.AssertionUtil;
import org.picketlink.identity.federation.core.wstrust.SamlCredential;
import org.picketlink.trust.jbossws.Util;

import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallerFactory;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.core.xml.config.XMLObjectProviderRegistry;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.InitializationService;
import org.opensaml.core.config.InitializationException;

import org.wildfly.security.auth.principal.NamePrincipal;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.security.auth.Subject;
import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Base class for SAML handlers implementations. A default implementation is provided by the {@link org.picketlink.identity.federation.core.saml.v2.interfaces.SAML2Handler} class.</p>
 *
 * @author <a href="mmoyses@redhat.com">Marcus Moyses</a>
 * @author <a href="alessio.soldano@jboss.com">Alessio Soldano</a>
 * @author Anil Saldhana
 * @author <a href="mailto:psilva@redhat.com">Pedro Silva</a>
 * @version $Revision: 1 $
 */
public abstract class AbstractSAML2Handler extends AbstractPicketLinkTrustHandler {

    // The system property key that can be set to determine the keys under which the roles may be in the assertion
    public static final String ROLE_KEY_SYS_PROP = "picketlink.rolekey";
    private boolean isOpenSamlInitialized = false;

    private void init() {
        XMLObjectProviderRegistry registry = new XMLObjectProviderRegistry();
        ConfigurationService.register(XMLObjectProviderRegistry.class, registry);

        try {
            InitializationService.initialize();
        } catch (InitializationException e) {
            throw new RuntimeException(e);  // TODO  should this be fatal  rls
        }

        isOpenSamlInitialized = true;
    }

    /**
     * Retrieves the SAML assertion from the SOAP payload and lets invocation go to JAAS for validation.
     */
    protected boolean handleInbound(MessageContext msgContext) {
        logger.trace("Handling Inbound Message");

        if (!isOpenSamlInitialized) {
            init();
        }

        String assertionNS = JBossSAMLURIConstants.ASSERTION_NSURI.get();
        SOAPMessageContext ctx = (SOAPMessageContext) msgContext;
        SOAPMessage soapMessage = ctx.getMessage();

        if (soapMessage == null) {
            throw logger.nullValueError("SOAP Message");
        }

        // retrieve the assertion
        Document document = soapMessage.getSOAPPart();
        Element soapHeader = Util.findOrCreateSoapHeader(document.getDocumentElement());
        Element assertionElement = Util.findElement(soapHeader, new QName(assertionNS, "Assertion"));

        if (assertionElement != null) {
            // Get the UnmarshallerFactory and then the specific Unmarshaller for Assertion
            UnmarshallerFactory unmarshallerFactory = XMLObjectProviderRegistrySupport.getUnmarshallerFactory();
            Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(assertionElement);

            Assertion assertion = null;
            try {
                // Unmarshall the Element into an Assertion object
                assertion = (Assertion) unmarshaller.unmarshall(assertionElement);
                if (AssertionUtil.hasExpired(assertion)) {
                    throw new RuntimeException(logger.samlAssertionExpiredError());
                }
            } catch (Exception e) {
                logger.samlAssertionPasingFailed(e);
            }

            SamlCredential credential = new SamlCredential(assertionElement);
            if (logger.isTraceEnabled()) {
                logger.trace("Assertion included in SOAP payload: " + credential.getAssertionAsString());
            }

            String username = assertion.getSubject().getNameID().getValue();

            Subject theSubject = new Subject();
            NamePrincipal principal = new NamePrincipal(username);

            if (assertion != null) {
                List<String> roleKeys = new ArrayList<String>();
                String roleKey = SecurityActions.getSystemProperty(ROLE_KEY_SYS_PROP, "Role");
                if (StringUtil.isNotNull(roleKey)) {
                    roleKeys.addAll(StringUtil.tokenize(roleKey));
                }

                logger.trace("Rolekeys to extract roles from the assertion: " + roleKeys);

                List<String> roles = AssertionUtil.getRoles(assertion, roleKeys);
                if (roles.size() > 0) {
                    logger.trace("Roles in the assertion: " + roles);

                    for (String role : roles) {
                        theSubject.getPrincipals().add(new NamePrincipal(role));
                    }
                } else {
                    logger.trace("Did not find roles in the assertion");
                }
            }
        } else {
            logger.trace("We did not find any assertion");
        }
        return true;
    }

    /**
     * <p>Subclasses can override this method to customize how the security context is created.</p>
     *
     * @param credential
     * @param theSubject
     * @param principal
     */
    protected void createSecurityContext(SamlCredential credential, Subject theSubject, Principal principal) {
        // rls  This is the object that gets created by the SecurityContextFactory and
        //      registered with the SecurityContext.  TBD how to make it accessible when
        //      needed
        // org.jboss.security.SubjectInfo subjectInfo = new SubjectInfo(principal, credential, theSubject);
    }

    /**
     * It expects a {@link Element} assertion as the value of the {@link SAML2Constants#SAML2_ASSERTION_PROPERTY} property. This
     * assertion is then included in the SOAP payload.
     */
    protected boolean handleOutbound(MessageContext msgContext) {
        logger.trace("Handling Outbound Message");

        if (!isOpenSamlInitialized) {
            init();
        }

        SOAPMessageContext ctx = (SOAPMessageContext) msgContext;
        SOAPMessage soapMessage = ctx.getMessage();

        // retrieve assertion first from the message context
        String SAML2_ASSERTION_PROPERTY = "org.picketlink.trust.saml.assertion";  // rls TODO replace this
        Element assertion = (Element) ctx.get(SAML2_ASSERTION_PROPERTY);

        // Assertion can also be obtained from the JAAS subject
        if (assertion == null) {
            assertion = getAssertionFromSubject();
        }

        if (assertion == null) {
            logger.trace("We did not find any assertion");
            return true;
        }

        // add wsse header
        Document document = soapMessage.getSOAPPart();
        Element soapHeader = Util.findOrCreateSoapHeader(document.getDocumentElement());
        try {
            Element wsse = getSecurityHeaderElement(document);
            wsse.setAttributeNS(soapHeader.getNamespaceURI(), soapHeader.getPrefix() + ":mustUnderstand", "1");
            if (assertion != null) {
                // add the assertion as a child of the wsse header
                // check if the assertion element comes from the same document, otherwise import the node
                if (document != assertion.getOwnerDocument()) {
                    wsse.appendChild(document.importNode(assertion, true));
                } else {
                    wsse.appendChild(assertion);
                }
            }
            soapHeader.insertBefore(wsse, soapHeader.getFirstChild());
        } catch (Exception e) {
            logger.error(e);
            return false;
        }

        return true;
    }
}