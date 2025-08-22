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
package org.wildfly.security.ws.trust.jbossws.handler;

import static org.wildfly.security.ws.common.ElytronMessages.log;

import io.undertow.security.api.SecurityContext;
import io.undertow.servlet.spec.HttpServletRequestImpl;
import io.undertow.server.HttpServerExchange;

import jakarta.xml.ws.handler.MessageContext;

/**
 * <p>Base class to perform Authentication for POJO Web Services based on the Authorize Operation on the JBossWS Native stack.</p>
 *
 * @author <a href="mailto:darran.lofthouse@jboss.com">Darran Lofthouse</a>
 * @author Anil.Saldhana@redhat.com
 * @author <a href="mailto:psilva@redhat.com">Pedro Silva</a>
 * @since Apr 11, 2011
 */
public abstract class AbstractWSAuthenticationHandler extends AbstractPicketLinkTrustHandler {

    /* (non-Javadoc)
     * @see org.wildfly.security.ws.trust.handler.jbossws.AbstractPicketLinkTrustHandler#handleInbound(javax.xml.ws.handler.MessageContext)
     */
    @Override
    protected boolean handleInbound(MessageContext msgContext) {

        log.trace("Handling Inbound Message");

        trace(msgContext);

        // Get object to authenticate user
        HttpServletRequestImpl ctx = (HttpServletRequestImpl)msgContext.get("HTTP.REQUEST");
        HttpServerExchange exch = ctx.getExchange();

        /* rls NOTE:
        // Must add module, org.wildfly.security.elytron-web.undertow-server-servlet, to
        // jboss-deployment-structure.xml in WAR file in order to have access to this class
        // ServletSecurityContextImpl servletSecurityCtx = (ServletSecurityContextImpl)exch.getSecurityContext();
        // SecurityContextImpl sContextImpl = (SecurityContextImpl)exch.getSecurityContext();
        */
        SecurityContext securityCtx = exch.getSecurityContext();

        /* TODO
            check for authentication.
         */

        return true;
    }
}