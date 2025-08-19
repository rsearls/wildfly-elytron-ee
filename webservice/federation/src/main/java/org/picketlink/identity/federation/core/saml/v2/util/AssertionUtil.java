/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.picketlink.identity.federation.core.saml.v2.util;

import org.picketlink.common.PicketLinkLogger;
import org.picketlink.common.PicketLinkLoggerFactory;
import org.picketlink.common.exceptions.ConfigurationException;

import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.Statement;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSString;

import org.w3c.dom.Node;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility to deal with assertions
 *
 * @author Anil.Saldhana@redhat.com
 * @since Jun 3, 2009
 */
public class AssertionUtil {

    private static final PicketLinkLogger logger = PicketLinkLoggerFactory.getLogger();

    public static boolean hasExpired(Assertion assertion) throws ConfigurationException {
        boolean expiry = false;

        // Check for validity of assertion
        Conditions conditionsType = assertion.getConditions();
        if (conditionsType != null) {
            Instant now = XMLTimeUtil.getIssueInstant(XMLTimeUtil.getCurrentZoneID());
            Instant notBefore = conditionsType.getNotBefore();
            Instant notOnOrAfter = conditionsType.getNotOnOrAfter();

            if (notBefore != null) {
                logger.trace("Assertion: " + assertion.getID() + " ::Now=" + now.toString() + " ::notBefore=" + notBefore.toString());
            }

            if (notOnOrAfter != null) {
                logger.trace("Assertion: " + assertion.getID() + " ::Now=" + now.toString() + " ::notOnOrAfter=" + notOnOrAfter.toString());
            }

            expiry = !XMLTimeUtil.isValid(now, notBefore, notOnOrAfter);

            if (expiry) {
                logger.samlAssertionExpired(assertion.getID());
            }
        }

        // TODO: if conditions do not exist, assume the assertion to be everlasting?
        return expiry;
    }

    public static List<String> getRoles(Assertion assertion, List<String> roleKeys) {
        List<String> roles = new ArrayList<String>();
        List<Statement> statementList = assertion.getStatements();
        for (Statement statement : statementList) {
            if (statement instanceof AttributeStatement) {
                AttributeStatement attributeStatement = (AttributeStatement) statement;
                List<Attribute> attList = attributeStatement.getAttributes();
                for (Attribute attr : attList) {
                    if (roleKeys != null && roleKeys.size() > 0) {
                        if (!roleKeys.contains(attr.getName()))
                            continue;
                    }
                    List<XMLObject> attributeValues = attr.getAttributeValues();
                    if (attributeValues != null) {
                        for (XMLObject attrValue : attributeValues) {
                            if (attrValue instanceof XSString) {
                                roles.add(((XSString) attrValue).getValue());
                            } else if (attrValue instanceof Node) {
                                Node roleNode = (Node) attrValue;
                                roles.add(roleNode.getFirstChild().getNodeValue());
                            } else
                                throw logger.unknownObjectType(attrValue);
                        }
                    }
                }
            }
        }
        return roles;
    }
}