package se.uu.its.integration.model.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.uu.its.integration.model.events.UUEvent;

/**
 * Utility class for model related functionality.
 *
 */
public class ModelUtils {
	
	public static String TO_STRING_ERROR_MSG = "Error!";
	protected Log log = LogFactory.getLog(this.getClass());
	
	public ModelUtils() {
	}

	/**
	 * Need a simple UUEvent from a descendant? Use this method.
	 * 
	 * @param message Message to get an UUEvent from, if possible.
	 * @return Returns a simple instance of UUEvent if possible. Otherwise passed object.
	 */
	public Object convertToUUEvent(Object message) {
		
		if (message instanceof UUEvent) {
			UUEvent e = ((UUEvent) message).exportUUEvent();
			return e;
		} else {
			return message;
		}
	}
	
	/**
	 * Used for getting new event identifiers.
	 * 
	 * @return A newly generated unique event identifier.
	 */
	public String getNewEventId() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * Adds a unique event identifier to event XML.
	 * 
	 * @param xml The XML representation of an event.
	 * @return XML decorated with a unique identifier.
	 * @throws Exception
	 */
	public String addIntegrationEventIdToEvent(String xml) throws Exception {
		
		log.debug("Adding new integration event id.");
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("uid", UUID.randomUUID().toString());
		
		return xsltTransform(xml, "/se/uu/its/integration/model/transform/addIntegrationEventIdToEvent.xsl", parameters);
	}
	
	/**
	 * A simple XSLT transformation method.
	 * 
	 * @param xml The source XML that should be transformed.
	 * @param xsltResourcePath A resource path to the XML stylesheet that should be used for transformation.
	 * @return A String with transformed XML.
	 * @throws Exception
	 */
	public String xsltTransform(String xml, String xsltResourcePath) throws Exception {
		return xsltTransform(xml, xsltResourcePath, null);
	}
	
	/**
	 * A simple XSLT transformation method.
	 * 
	 * @param xml The source XML that should be transformed.
	 * @param xsltResourcePath A resource path to the XML stylesheet that should be used for transformation.
	 * @param parameters Parameters as key/value pairs.
	 * @return A String with transformed XML.
	 * @throws Exception
	 */
	public String xsltTransform(String xml, String xsltResourcePath, Map<String, String> parameters) throws Exception {
		
		// get the stylesheet as an inputstream
		InputStream stylesheet = this.getClass().getResourceAsStream(xsltResourcePath);

		if (stylesheet == null) {
			throw new Exception("No stylesheet found!");
		}		
		
		// get the transformer
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(new StreamSource(stylesheet));

		if (parameters != null)
			for (Map.Entry<String, String> parameter : parameters.entrySet()) {
				log.info("Setting parameter + '" + parameter.getKey() + "': "
						+ parameter.getValue());
				transformer.setParameter(parameter.getKey(),
						parameter.getValue());
			}
		
		// the source and the target
		StringReader reader = new StringReader(xml);
		ByteArrayOutputStream targetDocument = new ByteArrayOutputStream();
		
		// do the tranformation
		transformer.transform(new StreamSource(reader), new StreamResult(targetDocument));
		
		String transformedXml = targetDocument.toString("UTF-8");
		log.debug(transformedXml);
		
		return transformedXml;
		
	}

	/**
	 * Helper method for unmarchalling XML into objects with JAXB.
	 * 
	 * @param c Class of expected object.
	 * @param xml XML representation of the object.
	 * @return An object instance of XML representation as class c.
	 * @throws JAXBException
	 */
	public static Object getUnmarchalledObject(
			@SuppressWarnings("rawtypes") Class c, String xml) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(c);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return jaxbUnmarshaller.unmarshal(new StringReader(xml));			
	}

	/**
	 * Helper method that generates XML from an object with JAXB.
	 * 
	 * @param c Class of object o.
	 * @param o Source object of marchalling.
	 * @return XML as a string from object o.
	 * @throws JAXBException
	 */
	public static String getMarchalledObjectXml(Class c, Object o) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(c);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		OutputStream objectXmlStream = new ByteArrayOutputStream();
		jaxbMarshaller.marshal(o, objectXmlStream);
		return objectXmlStream.toString();
		
	}	
	
}
