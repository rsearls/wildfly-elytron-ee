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
package org.wildfly.security.ws.federation.core.saml.v2.util;

import static org.wildfly.security.ws.common.ElytronMessages.log;
import org.wildfly.security.ws.common.constants.GeneralConstants;
import org.wildfly.security.ws.common.exceptions.ConfigurationException;
import org.wildfly.security.ws.common.exceptions.ParsingException;
import org.wildfly.security.ws.common.util.SystemPropertiesUtil;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneId;

/**
 * Util class dealing with xml based time
 *
 * @author Anil.Saldhana@redhat.com
 * @since Jan 6, 2009
 */
public class XMLTimeUtil {

    /**
     * Add additional time in miliseconds
     *
     * @param value calendar whose value needs to be updated
     * @param milis
     *
     * @return calendar value with the addition
     *
     * @throws ConfigurationException
     */
    public static XMLGregorianCalendar add(XMLGregorianCalendar value, long milis) throws ConfigurationException {
        XMLGregorianCalendar newVal = (XMLGregorianCalendar) value.clone();

        Duration duration;
        try {
            duration = newDatatypeFactory().newDuration(milis);
        } catch (DatatypeConfigurationException e) {
            throw log.configurationError(e);
        }
        newVal.add(duration);
        return newVal;
    }

    /**
     * Subtract some miliseconds from the time value
     *
     * @param value
     * @param milis miliseconds entered in a positive value
     *
     * @return
     *
     * @throws ConfigurationException
     */
    public static XMLGregorianCalendar subtract(XMLGregorianCalendar value, long milis) throws ConfigurationException {
        if (milis < 0)
            throw log.invalidArgumentError("milis should be a positive value");
        return add(value, -1 * milis);
    }

    /**
     * Returns a XMLGregorianCalendar in the timezone specified. If the timezone is not valid, then the timezone falls
     * back to
     * "GMT"
     *
     * @param timezone
     *
     * @return
     *
     * @throws ConfigurationException
     */
    public static XMLGregorianCalendar getIssueInstant(String timezone) throws ConfigurationException {
        TimeZone tz = TimeZone.getTimeZone(timezone);
        DatatypeFactory dtf;
        try {
            dtf = newDatatypeFactory();
        } catch (DatatypeConfigurationException e) {
            throw log.configurationError(e);
        }

        GregorianCalendar gc = new GregorianCalendar(tz);
        XMLGregorianCalendar xgc = dtf.newXMLGregorianCalendar(gc);

        return xgc;
    }

    public static Instant getIssueInstant(ZoneId timezone) throws ConfigurationException {
        return ZonedDateTime.now(timezone).toInstant();
    }

    public static String getCurrentTimeZoneID() {
        String timezonePropertyValue = SecurityActions.getSystemProperty(GeneralConstants.TIMEZONE, "GMT");

        TimeZone timezone;
        if (GeneralConstants.TIMEZONE_DEFAULT.equals(timezonePropertyValue)) {
            timezone = TimeZone.getDefault();
        } else {
            timezone = TimeZone.getTimeZone(timezonePropertyValue);
        }

        return timezone.getID();
    }

public static ZoneId getCurrentZoneID() {
    String timezonePropertyValue = SecurityActions.getSystemProperty(GeneralConstants.TIMEZONE, "GMT");

    ZoneId timezone;
    if (GeneralConstants.TIMEZONE_DEFAULT.equals(timezonePropertyValue)) {
        timezone = ZoneId.systemDefault();
    } else {
        timezone = ZoneId.of(timezonePropertyValue);
    }

    return timezone;
}
    /**
     * Convert the minutes into miliseconds
     *
     * @param valueInMins
     *
     * @return
     */
    public static long inMilis(int valueInMins) {
        return valueInMins * 60 * 1000;
    }

    public static boolean isValid(Instant now, Instant notbefore, Instant notOnOrAfter) {
        int val = 0;

        if (notbefore != null) {
            val = notbefore.compareTo(now);

            if (val > 0)
                return false;
        }

        if (notOnOrAfter != null) {
            val = notOnOrAfter.compareTo(now);

            if (val <= 0)
                return false;
        }

        return true;
    }


    /**
     * Given a string, get the Duration object. The string can be an ISO 8601 period representation (Eg.: P10M) or a
     * numeric
     * value. If a ISO 8601 period, the duration will reflect the defined format. If a numeric (Eg.: 1000) the duration
     * will
     * be calculated in milliseconds.
     *
     * @param timeValue
     *
     * @return
     *
     * @throws ParsingException
     */
    public static Duration parseAsDuration(String timeValue) throws ParsingException {
        if (timeValue == null) {
            throw log.nullArgumentError("duration time");
        }

        DatatypeFactory factory = null;

        try {
            factory = newDatatypeFactory();
        } catch (DatatypeConfigurationException e) {
            throw log.parserError(e.getMessage(), e);
        }

        try {
            // checks if it is a ISO 8601 period. If not it must be a numeric value.
            if (timeValue.startsWith("P")) {
                return factory.newDuration(timeValue);
            } else {
                return factory.newDuration(Long.valueOf(timeValue));
            }
        } catch (Exception e) {
            throw log.samlMetaDataFailedToCreateCacheDuration(timeValue);
        }
    }

    /**
     * Given a string representing xml time, parse into {@code XMLGregorianCalendar}
     *
     * @param timeString
     *
     * @return
     *
     * @throws ParsingException
     */
    public static XMLGregorianCalendar parse(String timeString) throws ParsingException {
        DatatypeFactory factory = null;
        try {
            factory = newDatatypeFactory();
        } catch (DatatypeConfigurationException e) {
            throw log.parserError(e.getMessage(), e);
        }
        return factory.newXMLGregorianCalendar(timeString);
    }


    /**
     * Create a new {@link DatatypeFactory}
     *
     * @return
     *
     * @throws DatatypeConfigurationException
     */
    public static DatatypeFactory newDatatypeFactory() throws DatatypeConfigurationException {
        boolean tccl_jaxp = SystemPropertiesUtil.getSystemProperty(GeneralConstants.TCCL_JAXP, "false")
                .equalsIgnoreCase("true");
        ClassLoader prevTCCL = SecurityActions.getTCCL();
        try {
            if (tccl_jaxp) {
                SecurityActions.setTCCL(XMLTimeUtil.class.getClassLoader());
            }
            return DatatypeFactory.newInstance();
        } finally {
            if (tccl_jaxp) {
                SecurityActions.setTCCL(prevTCCL);
            }
        }
    }
}