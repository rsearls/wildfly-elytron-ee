package org.wildfly.opensaml.parser;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallerFactory;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.core.xml.config.XMLObjectProviderRegistry;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.InitializationService;
import org.opensaml.core.config.InitializationException;
import org.opensaml.saml.saml2.core.Response;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

public class SAMLAssertionParserTest {

    @BeforeClass
    public static void init() throws InitializationException {
        XMLObjectProviderRegistry registry = new XMLObjectProviderRegistry();
        ConfigurationService.register(XMLObjectProviderRegistry.class, registry);

        InitializationService.initialize();
    }

    @Ignore
    @Test
    public void testSimpleSAML() throws Exception {
        try {
            /*** rls
            XMLObjectProviderRegistry registry = new XMLObjectProviderRegistry();
            ConfigurationService.register(XMLObjectProviderRegistry.class, registry);

            InitializationService.initialize();
        ***/
            String samlAssertionXML = "<saml:Assertion xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\" ID=\"_someID\" Version=\"2.0\" IssueInstant=\"2025-08-08T15:00:00.000Z\">" +
                "  <saml:Issuer>http://idp.example.com</saml:Issuer>" +
                "  <saml:Subject>" +
                "    <saml:NameID Format=\"urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified\">user123</saml:NameID>" +
                "    <saml:SubjectConfirmation Method=\"urn:oasis:names:tc:SAML:2.0:cm:bearer\">" +
                "      <saml:SubjectConfirmationData NotOnOrAfter=\"2025-08-08T16:00:00.000Z\" Recipient=\"http://sp.example.com/acs\"/>" +
                "    </saml:SubjectConfirmation>" +
                "  </saml:Subject>" +
                "  <saml:Conditions NotBefore=\"2025-08-08T14:50:00.000Z\" NotOnOrAfter=\"2025-08-08T16:00:00.000Z\">" +
                "    <saml:AudienceRestriction>" +
                "      <saml:Audience>http://sp.example.com</saml:Audience>" +
                "    </saml:AudienceRestriction>" +
                "  </saml:Conditions>" +
                "  <saml:AuthnStatement AuthnInstant=\"2025-08-08T15:00:00.000Z\" SessionIndex=\"_someSessionIndex\">" +
                "    <saml:AuthnContext>" +
                "      <saml:AuthnContextClassRef>urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport</saml:AuthnContextClassRef>" +
                "    </saml:AuthnContext>" +
                "  </saml:AuthnStatement>" +
                "</saml:Assertion>";

            // Convert the XML string to an InputStream
            InputStream inputStream = new ByteArrayInputStream(samlAssertionXML.getBytes());

            // Get a DocumentBuilderFactory and DocumentBuilder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true); // Important for SAML XML
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Parse the InputStream into a DOM Document
            Document doc = db.parse(inputStream);
            Element assertionElement = doc.getDocumentElement();

            // Get the UnmarshallerFactory and then the specific Unmarshaller for Assertion
            UnmarshallerFactory unmarshallerFactory = XMLObjectProviderRegistrySupport.getUnmarshallerFactory();
            Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(assertionElement);

            // Unmarshall the Element into an Assertion object
            Assertion assertion = (Assertion) unmarshaller.unmarshall(assertionElement);

            // Now you can access the assertion's properties
            System.out.println("Assertion ID: " + assertion.getID());
            System.out.println("Issuer: " + assertion.getIssuer().getValue());
            System.out.println("Subject NameID: " + assertion.getSubject().getNameID().getValue());
            System.out.println("Advice: " + (assertion.getAdvice() == null ? "NULL" : "SOME-VALUE"));
            System.out.println("Signature: " + (assertion.getSignature() == null ? "NULL" : "SOME-VALUE"));

        } catch (ParserConfigurationException | IOException | UnmarshallingException e) {
            e.printStackTrace();
        } catch (Exception e) { // Catch general OpenSAML initialization exceptions
            e.printStackTrace();
        }
    }

    @Test
    public void testSAMLResponseWithSignedAssertion() throws Exception{
        try {

            String samlAssertionXML = "<samlp:Response xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\" ID=\"_8e8dc5f69a98cc4c1ff3427e5ce34606fd672f91e6\" Version=\"2.0\" IssueInstant=\"2014-07-17T01:01:48Z\" Destination=\"http://sp.example.com/demo1/index.php?acs\" InResponseTo=\"ONELOGIN_4fee3b046395c4e751011e97f8900b5273d56685\">\n" +
                "  <saml:Issuer>http://idp.example.com/metadata.php</saml:Issuer>\n" +
                "  <samlp:Status>\n" +
                "    <samlp:StatusCode Value=\"urn:oasis:names:tc:SAML:2.0:status:Success\"/>\n" +
                "  </samlp:Status>\n" +
                "  <saml:Assertion xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" ID=\"pfxf32aa02d-1fa7-f2b0-219c-5fad5cc6a259\" Version=\"2.0\" IssueInstant=\"2014-07-17T01:01:48Z\">\n" +
                "    <saml:Issuer>http://idp.example.com/metadata.php</saml:Issuer><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "  <ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>\n" +
                "    <ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/>\n" +
                "  <ds:Reference URI=\"#pfxf32aa02d-1fa7-f2b0-219c-5fad5cc6a259\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/><ds:DigestValue>7ubyoEqSzOP+BlOmsIyBHMyyXn8=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>hZfOji81Dj6hDFvFu+Bq6hhNJhG444TkQWyS2rlbX+Ow4RN9mN56v0zGmIXNTvaWfw2TA3n1eEvC6EkEO6H7WngZoZvvU4vK9nsK20ywbyzhQeTZ1FfhQxSvp0wp4OTI7hdBxrhMtsZnyvOr9CPKsWlmMDWT87BlYLWoyPRLTcw=</ds:SignatureValue>\n" +
                "<ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIICajCCAdOgAwIBAgIBADANBgkqhkiG9w0BAQ0FADBSMQswCQYDVQQGEwJ1czETMBEGA1UECAwKQ2FsaWZvcm5pYTEVMBMGA1UECgwMT25lbG9naW4gSW5jMRcwFQYDVQQDDA5zcC5leGFtcGxlLmNvbTAeFw0xNDA3MTcxNDEyNTZaFw0xNTA3MTcxNDEyNTZaMFIxCzAJBgNVBAYTAnVzMRMwEQYDVQQIDApDYWxpZm9ybmlhMRUwEwYDVQQKDAxPbmVsb2dpbiBJbmMxFzAVBgNVBAMMDnNwLmV4YW1wbGUuY29tMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZx+ON4IUoIWxgukTb1tOiX3bMYzYQiwWPUNMp+Fq82xoNogso2bykZG0yiJm5o8zv/sd6pGouayMgkx/2FSOdc36T0jGbCHuRSbtia0PEzNIRtmViMrt3AeoWBidRXmZsxCNLwgIV6dn2WpuE5Az0bHgpZnQxTKFek0BMKU/d8wIDAQABo1AwTjAdBgNVHQ4EFgQUGHxYqZYyX7cTxKVODVgZwSTdCnwwHwYDVR0jBBgwFoAUGHxYqZYyX7cTxKVODVgZwSTdCnwwDAYDVR0TBAUwAwEB/zANBgkqhkiG9w0BAQ0FAAOBgQByFOl+hMFICbd3DJfnp2Rgd/dqttsZG/tyhILWvErbio/DEe98mXpowhTkC04ENprOyXi7ZbUqiicF89uAGyt1oqgTUCD1VsLahqIcmrzgumNyTwLGWo17WDAa1/usDhetWAMhgzF/Cnf5ek0nK00m0YZGyc4LzgD0CROMASTWNg==</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature>\n" +
                "    <saml:Subject>\n" +
                "      <saml:NameID SPNameQualifier=\"http://sp.example.com/demo1/metadata.php\" Format=\"urn:oasis:names:tc:SAML:2.0:nameid-format:transient\">_ce3d2948b4cf20146dee0a0b3dd6f69b6cf86f62d7</saml:NameID>\n" +
                "      <saml:SubjectConfirmation Method=\"urn:oasis:names:tc:SAML:2.0:cm:bearer\">\n" +
                "        <saml:SubjectConfirmationData NotOnOrAfter=\"2024-01-18T06:21:48Z\" Recipient=\"http://sp.example.com/demo1/index.php?acs\" InResponseTo=\"ONELOGIN_4fee3b046395c4e751011e97f8900b5273d56685\"/>\n" +
                "      </saml:SubjectConfirmation>\n" +
                "    </saml:Subject>\n" +
                "    <saml:Conditions NotBefore=\"2014-07-17T01:01:18Z\" NotOnOrAfter=\"2024-01-18T06:21:48Z\">\n" +
                "      <saml:AudienceRestriction>\n" +
                "        <saml:Audience>http://sp.example.com/demo1/metadata.php</saml:Audience>\n" +
                "      </saml:AudienceRestriction>\n" +
                "    </saml:Conditions>\n" +
                "    <saml:AuthnStatement AuthnInstant=\"2014-07-17T01:01:48Z\" SessionNotOnOrAfter=\"2024-07-17T09:01:48Z\" SessionIndex=\"_be9967abd904ddcae3c0eb4189adbe3f71e327cf93\">\n" +
                "      <saml:AuthnContext>\n" +
                "        <saml:AuthnContextClassRef>urn:oasis:names:tc:SAML:2.0:ac:classes:Password</saml:AuthnContextClassRef>\n" +
                "      </saml:AuthnContext>\n" +
                "    </saml:AuthnStatement>\n" +
                "    <saml:AttributeStatement>\n" +
                "      <saml:Attribute Name=\"uid\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:basic\">\n" +
                "        <saml:AttributeValue xsi:type=\"xs:string\">test</saml:AttributeValue>\n" +
                "      </saml:Attribute>\n" +
                "      <saml:Attribute Name=\"mail\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:basic\">\n" +
                "        <saml:AttributeValue xsi:type=\"xs:string\">test@example.com</saml:AttributeValue>\n" +
                "      </saml:Attribute>\n" +
                "      <saml:Attribute Name=\"eduPersonAffiliation\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:basic\">\n" +
                "        <saml:AttributeValue xsi:type=\"xs:string\">users</saml:AttributeValue>\n" +
                "        <saml:AttributeValue xsi:type=\"xs:string\">examplerole1</saml:AttributeValue>\n" +
                "      </saml:Attribute>\n" +
                "    </saml:AttributeStatement>\n" +
                "  </saml:Assertion>\n" +
                "</samlp:Response>";

            // Convert the XML string to an InputStream
            InputStream inputStream = new ByteArrayInputStream(samlAssertionXML.getBytes());

            // Get a DocumentBuilderFactory and DocumentBuilder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true); // Important for SAML XML
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Parse the InputStream into a DOM Document
            Document doc = db.parse(inputStream);
            Element assertionElement = doc.getDocumentElement();

            // Get the UnmarshallerFactory and then the specific Unmarshaller for Assertion
            UnmarshallerFactory unmarshallerFactory = XMLObjectProviderRegistrySupport.getUnmarshallerFactory();
            Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(assertionElement);

            // Unmarshall the Element into an Assertion object
            Response response = (Response) unmarshaller.unmarshall(assertionElement);
            System.out.println("Response Issuer: " + response.getIssuer().getValue());
            System.out.println("Response StatusCode: " + response.getStatus().getStatusCode().getValue());

            List<Assertion> assertionList = response.getAssertions();
            for (Assertion assertion : assertionList) {
                // Now you can access the assertion's properties
                System.out.println("Assertion ID: " + assertion.getID());
                System.out.println("Issuer: " + assertion.getIssuer().getValue());
                System.out.println("Subject NameID: " + assertion.getSubject().getNameID().getValue());
                System.out.println("Advice: " + (assertion.getAdvice() == null ? "NULL" : "SOME-VALUE"));
                System.out.println("Signature: " + (assertion.getSignature() == null ? "NULL" : "SOME-VALUE"));
                System.out.println("---");
            }
        } catch (ParserConfigurationException | IOException | UnmarshallingException e) {
            e.printStackTrace();
        } catch (Exception e) { // Catch general OpenSAML initialization exceptions
            e.printStackTrace();
        }
    }

    /**
     @Test
     public void test() throws Exception{

     }
    **/
}
