/*
 * JBoss, Home of Professional Open Source
 *
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.picketlink.common.util;

import org.picketlink.common.PicketLinkLogger;
import org.picketlink.common.PicketLinkLoggerFactory;
import org.picketlink.common.exceptions.ConfigurationException;
import org.picketlink.common.exceptions.ParsingException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.security.GeneralSecurityException;


/**
 * Utility dealing with DOM
 *
 * @author Anil.Saldhana@redhat.com
 * @since Jan 14, 2009
 */
public class DocumentUtil {

    private static final PicketLinkLogger logger = PicketLinkLoggerFactory.getLogger();

    private static DocumentBuilderFactory documentBuilderFactory;

    public static final String feature_external_general_entities = "http://xml.org/sax/features/external-general-entities";
    public static final String feature_external_parameter_entities = "http://xml.org/sax/features/external-parameter-entities";
    public static final String feature_disallow_doctype_decl = "http://apache.org/xml/features/disallow-doctype-decl";

    /**
     * Parse a document from the string
     *
     * @param docString
     *
     * @return
     *
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public static Document getDocument(String docString) throws ConfigurationException, GeneralSecurityException, GeneralSecurityException {
        return getDocument(new StringReader(docString));
    }

    /**
     * Parse a document from a reader
     *
     * @param reader
     *
     * @return
     *
     * @throws ParsingException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Document getDocument(Reader reader) throws ConfigurationException, GeneralSecurityException, GeneralSecurityException {
        try {
            DocumentBuilderFactory factory = getDocumentBuilderFactory();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(reader));
        } catch (ParserConfigurationException e) {
            throw logger.configurationError(e);
        } catch (SAXException e) {
            throw logger.parserError(e);
        } catch (IOException e) {
            throw logger.processingError(e);
        }
    }

    /**
     * Get Document from an inputstream
     *
     * @param is
     *
     * @return
     *
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Document getDocument(InputStream is) throws ConfigurationException, GeneralSecurityException, GeneralSecurityException {
        DocumentBuilderFactory factory = getDocumentBuilderFactory();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(is);
        } catch (ParserConfigurationException e) {
            throw logger.configurationError(e);
        } catch (SAXException e) {
            throw logger.parserError(e);
        } catch (IOException e) {
            throw logger.processingError(e);
        }
    }

    /**
     * <p> Get an child element from the parent element given its {@link QName} </p> <p> First an attempt to get the
     * element based on its namespace is made, failing which an element with the localpart ignoring any namespace is
     * returned. </p>
     *
     * @param doc
     * @param elementQName
     *
     * @return
     */
    public static Element getChildElement(Element doc, QName elementQName) {
        NodeList nl = doc.getElementsByTagNameNS(elementQName.getNamespaceURI(), elementQName.getLocalPart());
        if (nl.getLength() == 0) {
            nl = doc.getElementsByTagNameNS("*", elementQName.getLocalPart());
            if (nl.getLength() == 0)
                nl = doc.getElementsByTagName(elementQName.getPrefix() + ":" + elementQName.getLocalPart());
            if (nl.getLength() == 0)
                return null;
        }
        return (Element) nl.item(0);
    }

    /**
     * <p> Creates a namespace aware {@link DocumentBuilderFactory}. The returned instance is cached and shared between
     * different threads. </p>
     *
     * @return
     */
    private static DocumentBuilderFactory getDocumentBuilderFactory() {
        String TCCL_JAXP = "picketlink.jaxp.tccl";  // rls TODO replace
        // boolean tccl_jaxp = SystemPropertiesUtil.getSystemProperty(GeneralConstants.TCCL_JAXP, "false")
        boolean tccl_jaxp = SystemPropertiesUtil.getSystemProperty(TCCL_JAXP, "false")
                .equalsIgnoreCase("true");
        ClassLoader prevTCCL = SecurityActions.getTCCL();
        if (documentBuilderFactory == null) {
            try {
                if (tccl_jaxp) {
                    SecurityActions.setTCCL(DocumentUtil.class.getClassLoader());
                }
                documentBuilderFactory = DocumentBuilderFactory.newInstance();
                documentBuilderFactory.setNamespaceAware(true);
                documentBuilderFactory.setXIncludeAware(true);
                String feature = "";
                try {
                    feature = feature_disallow_doctype_decl;
                    documentBuilderFactory.setFeature(feature, true);
                    feature = feature_external_general_entities;
                    documentBuilderFactory.setFeature(feature, false);
                    feature = feature_external_parameter_entities;
                    documentBuilderFactory.setFeature(feature, false);
                } catch (ParserConfigurationException e) {
                    throw logger.parserFeatureNotSupported(feature);
                }
            } finally {
                if (tccl_jaxp) {
                    SecurityActions.setTCCL(prevTCCL);
                }
            }
        }

        return documentBuilderFactory;
    }
}