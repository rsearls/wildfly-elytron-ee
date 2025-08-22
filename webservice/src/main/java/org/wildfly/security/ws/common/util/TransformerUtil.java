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
package org.wildfly.security.ws.common.util;

import static org.wildfly.security.ws.common.ElytronMessages.log;
import org.wildfly.security.ws.common.constants.GeneralConstants;
import org.wildfly.security.ws.common.exceptions.ConfigurationException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

/**
 * Utility to deal with JAXP Transformer
 *
 * @author Anil.Saldhana@redhat.com
 * @since Oct 22, 2010
 */
public class TransformerUtil {

    private static TransformerFactory transformerFactory;

    /**
     * Get the Default Transformer
     *
     * @return
     *
     * @throws ConfigurationException
     */
    public static Transformer getTransformer() throws ConfigurationException {
        Transformer transformer;
        try {
            transformer = getTransformerFactory().newTransformer();
        } catch (TransformerConfigurationException e) {
            throw log.configurationError(e);
        } catch (TransformerFactoryConfigurationError e) {
            throw log.configurationError(e);
        }

        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");

        return transformer;
    }

    /**
     * <p>Creates a {@link TransformerFactory}. The returned instance is cached and shared between different
     * threads.</p>
     *
     * @return
     *
     * @throws TransformerFactoryConfigurationError
     */
    public static TransformerFactory getTransformerFactory() throws TransformerFactoryConfigurationError {
        if (transformerFactory == null) {
            boolean tccl_jaxp = SystemPropertiesUtil.getSystemProperty(GeneralConstants.TCCL_JAXP, "false")
                    .equalsIgnoreCase("true");
            ClassLoader prevTCCL = SecurityActions.getTCCL();
            try {
                if (tccl_jaxp) {
                    SecurityActions.setTCCL(TransformerUtil.class.getClassLoader());
                }
                transformerFactory = TransformerFactory.newInstance();
            } finally {
                if (tccl_jaxp) {
                    SecurityActions.setTCCL(prevTCCL);
                }
            }
        }

        return transformerFactory;
    }
}