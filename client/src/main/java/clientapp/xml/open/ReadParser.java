package clientapp.xml.open;


import lib.entity.StudArray;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

class ReadParser {
    StudArray parse(String path) throws ParserConfigurationException,
            SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        StudentHandler handler = new StudentHandler();
        parser.parse(new File(path), handler);

        return handler.getStudentList();
    }
}

