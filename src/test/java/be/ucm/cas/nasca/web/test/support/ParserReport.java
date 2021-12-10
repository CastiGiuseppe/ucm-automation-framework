package be.ucm.cas.nasca.web.test.support;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

final class ParserReport {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    public ParserReport() {
    }

    public static void parserAndWriteFile(Date dateDebutTest, Date dateFinTest) {
        try {
            Calendar calJour = Calendar.getInstance();
            String dateFormat = DateUtils.getDateTodayNoFomat();

            if (calJour.get(Calendar.AM_PM) == 0 && calJour.get(Calendar.HOUR) < 11) {
                calJour.add(Calendar.DAY_OF_MONTH, -1);
                dateFormat = DateUtils.doFormat(TestData.DATE_NOFORMAT, calJour.getTime());
            }

            String pathRepertoireParent = TestData.RECAPITULATIF_RAPPORT_TA;
            String pathRepertoireEnfant = TestData.RECAPITULATIF_RAPPORT_TA + dateFormat;
            String pathRepertoireRapportHtml = pathRepertoireEnfant + TestData.RAPPORT_HTML_TA;

            // Création des répertoires
            if (!new File(pathRepertoireParent).exists() && new File(pathRepertoireParent).mkdir()) {
                LOGGER.info("répertoire parent créé");
            }

            if (!new File(pathRepertoireEnfant).exists()) {
                if (new File(pathRepertoireEnfant).mkdir()) {
                    LOGGER.info("répertoire enfant créé");
                }
                if (new File(pathRepertoireRapportHtml).mkdir()) {
                    LOGGER.info("répertoire rapport html créé");
                }
            }

            Document doc = Jsoup.parse(new File(TestData.REPORT_LOCATON + TestData.FILE_REPORT_HTML), "UTF-8", "");
            LOGGER.info("Parsing Rapport html");
            Element element = doc.getElementById("cat-collection").getElementsByTag("li").get(0);

            double pass = 0.0;
            double fail = 0.0;
            double error = 0.0;
            double unknown = 0.0;
            double skip = 0.0;
            double totalTest = 0.0;

            String categorie = element.getElementsByClass("category-name").get(0).text();

            // Copie du répertoire target
            String[] cat = categorie.split("-");
            pathRepertoireRapportHtml += cat[1].replaceAll(".java", "").trim() + "/";
            LOGGER.info("Répertoire rapport html :" + pathRepertoireRapportHtml);
            copieRepertoireTarget(pathRepertoireRapportHtml);

            if (!new File(pathRepertoireRapportHtml).exists()) {
                SeleniumUtils.waitForActionCommon();
                copieRepertoireTarget(pathRepertoireRapportHtml);
            } else {
                LOGGER.info("Répertoire rapport html :" + pathRepertoireRapportHtml + " OK");
            }

            for (Element test : doc.getElementsByClass("wrapper").get(0).getElementsByTag("li")) {
                Elements resultTest = test.getElementsByClass("capitalize");
                switch (resultTest.get(0).text()) {
                    case "pass":
                        pass++;
                        break;
                    case "fail":
                        fail++;
                        break;
                    case "error":
                        error++;
                        break;
                    case "skip":
                        skip++;
                        break;
                    case "unknown":
                        unknown++;
                        break;
                    default:
                        break;
                }
                totalTest++;
            }
            double duree = (dateFinTest.getTime() - dateDebutTest.getTime()) / 60000.00;
            generateFichierTexte(categorie, duree, pass, fail, error, unknown, skip, totalTest, pathRepertoireRapportHtml, dateDebutTest, dateFinTest, pathRepertoireEnfant);
        } catch (Exception exception) {
            LOGGER.info("Exception 3 : " + exception);
        }
    }

    private static void copieRepertoireTarget(String pathRepertoireRapportHtml) {
        try {
            if (new File(TestData.REPORT_LOCATON).exists()) {
                FileUtils.copyDirectory(new File(TestData.REPORT_LOCATON), new File(pathRepertoireRapportHtml));
            } else {
                LOGGER.info(TestData.REPORT_LOCATON + " pas présent");
            }
        } catch (Exception e) {
            LOGGER.info("Exception : " + e);
        }
    }

    private static void generateFichierTexte(String categorie, double duree, double pass, double fail, double error, double unknown, double skip, double totalTest, String pathRepertoireRapportHtml, Date dateDebut, Date dateFin, String path) {
        String[] cat = categorie.split("-");
        String environnement = cat[0].trim();
        String test = cat[1].replaceAll(".java", "").trim();

        File fichierPlat = new File(path + "/Rapport/" + test + ".txt");
        try {
            if (fichierPlat.createNewFile()) {
                LOGGER.info("Fichier plat créé " + fichierPlat.getPath());
            }
            FileWriter ffw = new FileWriter(fichierPlat);
            ffw.write(environnement + ";" + duree + ";" + totalTest + ";" + pass + ";" + fail + ";" + error + ";" + unknown + ";" + skip + ";" + pathRepertoireRapportHtml + "TestReport.html" + ";" + DateUtils.doFormat(TestData.DATE_FORMAT_XML_WITH_HOUR, dateDebut) + ";" + DateUtils.doFormat(TestData.DATE_FORMAT_XML_WITH_HOUR, dateFin) + ";");
            ffw.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
