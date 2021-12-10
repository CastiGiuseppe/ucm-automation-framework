package be.ucm.cas.nasca.web.test.support;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public final class ParserXmlUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    public ParserXmlUtils() {
    }

    public static boolean parserXmlFile(File fichierXml, String balise, String valeur) {
        try {
            Document doc = Jsoup.parse(fichierXml, "UTF-8", "");
            LOGGER.info("Parsing XML File");
            Elements elements = doc.getElementsByTag(balise);

            for (Element element : elements) {
                if (element.text().equals(valeur)) {
                    return true;
                }
            }
        } catch (Exception exception) {
            LOGGER.info("Parser Xml File : " + exception);
        }
        return false;
    }
}