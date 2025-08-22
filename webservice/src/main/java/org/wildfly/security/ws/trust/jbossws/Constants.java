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
package org.wildfly.security.ws.trust.jbossws;

import org.apache.xml.security.utils.EncryptionConstants;

import javax.xml.namespace.QName;

/**
 * @author Jason T. Greene
 * @author Anil Saldhana
 */
public class Constants {

    public static final String WSSE_PREFIX = "wsse";

    public static final String WSSE_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";

    public static final String WSU_PREFIX = "wsu";

    public static final String WSU_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";

    public static final String XML_SIGNATURE_NS = org.apache.xml.security.utils.Constants.SignatureSpecNS;

    public static final String XML_ENCRYPTION_NS = EncryptionConstants.EncryptionSpecNS;

    public static final String XML_ENCRYPTION_PREFIX = "ds"; // xmlsec 1.4.2 requires this to be "ds" to correctly create
    // KeyInfo elements

    public static final String ID = "Id";

    public static final String WSU_ID = WSU_PREFIX + ":" + ID;

    public static final String WSSE_HEADER = WSSE_PREFIX + ":Security";

    public static final String XMLNS_NS = "http://www.w3.org/2000/xmlns/";

    public static final QName WSSE_HEADER_QNAME = new QName(WSSE_NS, "Security");
}
