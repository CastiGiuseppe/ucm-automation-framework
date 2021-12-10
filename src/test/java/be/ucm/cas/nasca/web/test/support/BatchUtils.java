package be.ucm.cas.nasca.web.test.support;

import be.ucm.cas.openerp.web.test.support.CodaFileUtils;
import be.ucm.cas.openerp.web.test.support.OpenErpUtils;
import com.google.common.io.Files;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class BatchUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    private static RunBatch batch;
    private static OpenErpUtils oerp;
    private static String filetosend;
    private static String SFTPPATHEXML;

    private static Session session = null;
    private static ChannelSftp channelSftp = null;

    public BatchUtils() {
    }

    public static void initContent() {
        setBatch((RunBatch) ApplicationContext.getAppCtx().getBean(RunBatch.BEAN_NAME_BATCH));
    }

    public static void iniContentErp() {
        setOerp((OpenErpUtils) ApplicationContext.getAppCtx().getBean(OpenErpUtils.BEAN_NAME));
    }

    public static void editScriptJS(String pathScriptJs, String nameScriptJs, String search, String replace) throws SftpException {
        String filetosend = "C:/temp";

        initContent();
        RunBatch.connectMachine(RunBatch.getHostUrl(), RunBatch.getUser(), RunBatch.getPwd());
        channelSftp = batch.getSftp().getChannelSftp();

        channelSftp.get(pathScriptJs + nameScriptJs, filetosend);

        File js = new File("C:/temp/" + nameScriptJs);

        try {
            FileReader fr = new FileReader(js);
            String s;
            String totalStr = "";
            try (BufferedReader br = new BufferedReader(fr)) {

                while ((s = br.readLine()) != null) {
                    totalStr += s + "\r\n";
                }
                totalStr = totalStr.replaceAll(search, replace);
                FileWriter fw = new FileWriter(js);
                fw.write(totalStr);
                fw.close();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        channelSftp.put(filetosend + "/" + nameScriptJs, pathScriptJs);
    }

    public static File getFileFromServer(String path) throws SftpException {
        String filetosend = "C:/temp";

        String[] name = path.split("print_tmp");

        initContent();
        RunBatch.connectMachine(RunBatch.getHostUrl(), RunBatch.getUser(), RunBatch.getPwd());
        channelSftp = batch.getSftp().getChannelSftp();

        channelSftp.get(path, filetosend);

        String fileName = filetosend + "/" + name[1].substring(1);
        return new File(fileName);
    }

    public static void doSendResponseLM(String nissOrBce, String path, String type) throws Exception {
        String filename = nissOrBce + ".xml";
        File file1 = new File(TestData.TEMP_LOCATION + filename);
        String template = TestData.XML_TEMPLATE_LM_NISS;
        if (TestData.TYPE_PM.equals(type)) {
            template = TestData.XML_TEMPLATE_LM_BCE;
        }
        File filetosend = new File(TestData.FILE_TEMPLATE_PATH + template);

        doFileDeleteSftp(filename, path, file1, filetosend);
    }

    public static void doSendResponseSignal74L(String niss, String path) throws Exception {
        String filename = niss + ".xml";
        File file1 = new File(TestData.TEMP_LOCATION + filename);
        File filetosend = new File(TestData.FILE_TEMPLATE_PATH + TestData.XML_TEMPLATE_SIGNAL_74L);

        doFileDeleteSftp(filename, path, file1, filetosend);
    }

    private static void doFileDeleteSftp(String filename, String path, File file1, File filetosend) throws IOException, SftpException {
        initContent();
        RunBatch.connectMachine(RunBatch.getHostUrl(), RunBatch.getUser(), RunBatch.getPwd());
        copyFileToSftp(filename, path, file1, filetosend);
        session = batch.getSftp().getSession();
        batch.getSftp().destroySession(channelSftp, session);
        if (file1.delete()) {
            LOGGER.info(TestData.FICHIER_SUPPRIME + file1.getPath());
        }
    }

    private static void copyFileToSftp(String filename, String pathFlux, File file1, File filetosend) throws IOException, SftpException {
        try {
            channelSftp = batch.getSftp().getChannelSftp();
            channelSftp.get(pathFlux + "/" + filename, TestData.TEMP_LOCATION);
            if (file1.delete()) {
                LOGGER.info(TestData.FICHIER_SUPPRIME + file1.getPath());
            }
        } catch (Exception e) {

        }
        Files.copy(filetosend, file1);
        channelSftp.put(TestData.TEMP_LOCATION + filename, pathFlux);
    }

    public static void createAndSendXmlTransfertPm(String bcenumber, String year, String decision) {
        Document doc = new Document();
        Element root = new Element("batchmessage")
                .setAttribute("Timestamp", DateUtils.getDateTodayXmlFomat())
                .setAttribute("VersionID", "1").setAttribute("SequenceID", "1")
                .setAttribute("Process", "Contributions")
                .setAttribute("DestinationID", "019")
                .setAttribute("OriginID", "000");
        Element child1 = new Element("companies");
        Element content = new Element("company")
                .setAttribute("CompanyID", bcenumber)
                .setAttribute("StartYearOfAffiliation", year)
                .setAttribute("DecisionDate", DateUtils.getDateTodayXmlFomat())
                .setAttribute("Decision", decision)
                .setAttribute("PreviousSIFID", "003");
        child1.addContent(content);
        root.addContent(child1);
        doc.addContent(root);
        createFileAndSendToBatchServer(doc, "000_019_AffiliationDecisionNewSif_");
    }

    public static void createAndSendXmlEnrolPm(String bcenumber, String year,
                                               String datexmlformat, String balance, String cotisation) {
        Document doc = new Document();
        Element root = new Element("batchmessage")
                .setAttribute("Timestamp", DateUtils.getDateTodayXmlFomat())
                .setAttribute("VersionID", "1").setAttribute("SequenceID", "1")
                .setAttribute("Process", "Contributions")
                .setAttribute("DestinationID", "019")
                .setAttribute("OriginID", "000");
        Element child1 = new Element("companies");
        Element content = new Element("company")
                .setAttribute("CompanyID", bcenumber)
                .setAttribute("ContributionYear", year)
                .setAttribute("BalanceDate", datexmlformat)
                .setAttribute("BalanceTotal", balance)
                .setAttribute("ContributionAmount", cotisation);
        child1.addContent(content);
        root.addContent(child1);
        doc.addContent(root);
        createFileAndSendToBatchServer(doc, "000_019_Contributions_");
    }

    private static void createFileAndSendToBatchServer(Document doc, String name) {
        try {
            filetosend = TestData.TMP_FILE + "\\" + name + DateUtils.getDateTodayNoFomat() + "_0001.xml";
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(doc, new FileWriter(filetosend));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        doSendFileToBatchServer(filetosend, "/xmlBatch");
    }

    public static void createAndSendXmlViaxis(String numeroContrat, String nature,
                                              String typeInvestissement, String numeroVCS, String dateReception, String niss, String nom, String prenom) {
        String templateXml = TestData.FILE_TEMPLATE_PATH + TestData.XML_TEMPLATE_VIAXIS;

        filetosend = TestData.TMP_FILE + "\\" + "Viaxis_new_contract" + DateUtils.getDateTodayNoFomat() + ".xml";

        Map<String, String> map = new LinkedHashMap<>();

        map.put("NUMERO_CONTRAT", numeroContrat);
        map.put("NATURE", nature.toUpperCase());
        map.put("TYPE_INVESTISSEMENT", typeInvestissement.toUpperCase());
        map.put("NUMERO_VCS", numeroVCS);
        map.put("DATE_RECEPTION", dateReception);
        map.put("NISS", niss);
        map.put("NOM", nom);
        map.put("PRENOM", prenom);

        ReplaceContentInTestCaseUtils.replace(templateXml, filetosend, map);

        doSendFileToBatchServer(filetosend, "/viaxis");
    }

    public static void createAndSendXmlFluxA020(String niss) {
        String templateXml = TestData.FILE_TEMPLATE_PATH + TestData.XML_TEMPLATE_FLUX_A020;

        filetosend = TestData.TMP_FILE + "\\" + TestData.XML_TEMPLATE_FLUX_A020;

        Map<String, String> map = new LinkedHashMap<>();

        map.put("NISS", niss);

        ReplaceContentInTestCaseUtils.replace(templateXml, filetosend, map);

        doSendFileToBatchServer(filetosend, "/xmlBatch");
    }

    private static void doSendFileToBatchServer(String pathfile, String repertoire) {
        initContent();
        RunBatch.connectMachine(RunBatch.getHostUrl(), RunBatch.getUser(), RunBatch.getPwd());
        try {
            channelSftp = batch.getSftp().getChannelSftp();
            SFTPPATHEXML = TestData.REPERTOIRE_DATA + batch.getMachine() + repertoire;
            channelSftp.put(pathfile, SFTPPATHEXML);
        } catch (SftpException e) {
            LOGGER.error(e.getMessage());
        }
        session = batch.getSftp().getSession();
        batch.getSftp().destroySession(channelSftp, session);
        File filetmp = new File(pathfile);
        if (filetmp.delete()) {
            LOGGER.info(TestData.FICHIER_SUPPRIME + filetmp.getPath());
        }
    }

    public static void deleteXmlBatch(String nameFile) {
        initContent();
        RunBatch.connectMachine(RunBatch.getHostUrl(), RunBatch.getUser(), RunBatch.getPwd());
        channelSftp = batch.getSftp().getChannelSftp();
        SFTPPATHEXML = TestData.REPERTOIRE_DATA + batch.getMachine() + "/xmlBatch";

        try {
            channelSftp.cd(SFTPPATHEXML);
            channelSftp.rm("*" + nameFile + "*");
        } catch (SftpException e) {
            LOGGER.error(e.getMessage());
        }

        session = batch.getSftp().getSession();
        batch.getSftp().destroySession(channelSftp, session);
    }

    public static void doSendFlatFilePPInastiBatch(String fileNameTemplate, String niss) throws Exception {
        String filename = TestData.NOM_FICHIER_PLAT + niss;
        String pathFlux = TestData.REPERTOIRE_DATA + TestBase.getBatch().getMachine() + "/fluxin";
        File file1 = new File(TestData.TEMP_LOCATION + filename);
        File filetosend = new File(TestData.FILE_TEMPLATE_PATH + fileNameTemplate);

        doFileDeleteSftp(filename, pathFlux, file1, filetosend);
    }

    public static void doSendFlatFileL010InastiBatch(String niss) throws IOException {
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(TestData.FILE_TEMPLATE_PATH + TestData.FLAT_FILE_TEMPLATE_L010);
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        List<String> myList = new ArrayList<>();

        try {
            while ((strLine = br.readLine()) != null) {
                myList.add(strLine);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        int i = 0;
        while (i < myList.size()) {
            switch (i) {
                case 0:
                    break;
                case 1:
                    myList.set(i, creationLigneL010(niss, myList, i));
                    break;
                default:
                    break;
            }
            i++;
        }

        FileWriter writer = new FileWriter(TestData.TMP_FILE + "\\" + TestData.NOM_FICHIER_PLAT + niss);
        for (String str : myList) {
            writer.append(str);
            writer.write("\n");
        }

        writer.close();

        try {
            br.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        String filepathname = TestData.TMP_FILE + "\\" + TestData.NOM_FICHIER_PLAT + niss;
        doSendFileToBatchServer(filepathname, "/fluxin");
    }

    private static String creationLigneL010(String niss, List<String> myList, int i) {
        String ligne = CodaFileUtils.doReplaceInfoCoda(myList.get(i), DateUtils.getDateTodayNoFomat(), 52, 60);
        ligne = CodaFileUtils.doReplaceInfoCoda(ligne, niss, 41, 52);
        ligne = CodaFileUtils.doReplaceInfoCoda(ligne, DateUtils.getDateTodayNoFomat(), 77, 85);
        return ligne;
    }

    public static RunBatch getBatch() {
        return batch;
    }

    private static void setBatch(RunBatch batch) {
        BatchUtils.batch = batch;
    }

    public static OpenErpUtils getOerp() {
        return oerp;
    }

    private static void setOerp(OpenErpUtils oerp) {
        BatchUtils.oerp = oerp;
    }
}