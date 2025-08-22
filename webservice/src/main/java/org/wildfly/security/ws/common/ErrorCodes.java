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
package org.wildfly.security.ws.common;

/**
 * Error Codes for PicketLink https://docs.jboss.org/author/display/PLINK/PicketLink+Error+Codes
 *
 * @author Anil.Saldhana@redhat.com
 * @since Aug 4, 2011
 */
public interface ErrorCodes {
    String EXPIRED_ASSERTION = "Assertion has expired:";
    String EXPECTED_TEXT_VALUE = "Parser: Expected text value:";
    String NOT_EQUAL = "Not equal:";
    String NULL_VALUE = "Null Value:";
    String PARSING_ERROR = "Parsing Error:";
    String PROCESSING_EXCEPTION = "Processing Exception:";
    String RESOURCE_NOT_FOUND = "Resource not found:";
    String SYSTEM_PROPERTY_MISSING = "System Property missing:";
    String UNKNOWN_END_ELEMENT = "Parser: Unknown End Element:";
    String UNKNOWN_OBJECT_TYPE = "Unknown Object Type:";
    String WRONG_TYPE = "Wrong type:";
}