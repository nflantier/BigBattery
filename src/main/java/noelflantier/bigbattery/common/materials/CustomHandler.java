package noelflantier.bigbattery.common.materials;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public interface CustomHandler {
	boolean endElement(String uri, String localName, String qName, RecipeParser parser) throws SAXException;
	boolean startElement(String uri, String localName, String qName, Attributes attributes, RecipeParser parser) throws SAXException;
	public void process();
}
