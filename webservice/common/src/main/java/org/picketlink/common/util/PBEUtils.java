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

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

/**
 * Utility dealing with Password Based Encryption (Code is ripped off of the PBEUtils class in JBossSecurity/PicketBox)
 *
 * @author Scott.Stark@jboss.org
 * @author Anil.Saldhana@redhat.com
 * @since May 25, 2010
 */
public class PBEUtils {

    public static byte[] decode(byte[] secret, String cipherAlgorithm, SecretKey cipherKey, PBEParameterSpec cipherSpec)
            throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, cipherKey, cipherSpec);
        byte[] decode = cipher.doFinal(secret);
        return decode;
    }
    public static String decode64(String secret, String cipherAlgorithm, SecretKey cipherKey, PBEParameterSpec cipherSpec)
            throws GeneralSecurityException, UnsupportedEncodingException {
        byte[] encoding = Base64.decode(secret);
        byte[] decode = decode(encoding, cipherAlgorithm, cipherKey, cipherSpec);
        return new String(decode, "UTF-8");
    }
}