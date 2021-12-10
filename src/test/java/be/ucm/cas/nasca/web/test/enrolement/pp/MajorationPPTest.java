package be.ucm.cas.nasca.web.test.enrolement.pp;

import be.ucm.cas.nasca.web.test.support.*;
import com.jcraft.jsch.SftpException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;

public class MajorationPPTest extends EnrolementPPTestBase {

    private String dateOperationComptable;

    private Calendar calJour;

    @BeforeTest
    public void launchMajorationPP() {
        calJour = Calendar.getInstance();
        calJour.set(Calendar.DAY_OF_MONTH, 1);
        calJour.set(Calendar.MONTH, 0);
        calJour.add(Calendar.YEAR, 1);
        dateOperationComptable = DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, calJour.getTime());

        String batchdate = DateUtils.doFormat(TestData.DATE_FORMAT_XML, calJour.getTime());

        RunBatch.runBatchChangeDate(batchdate);

        DaoFunctionality.getNascaDao().initialisationDateEnrolementMajoPPAnnuelle(calJour.get(Calendar.YEAR), calJour.getTime());
        DaoFunctionality.getNascaDao().initialisationDateEnrolementMajoPPTrimestrielle(calJour.get(Calendar.YEAR), DateUtils.getQuarterNumber(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, calJour.getTime())), calJour.getTime());
    }

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Enrôlement.xlsx", "Majoration PP", "Majoration PP", "MajoPP", null);
    }

    @Test(dataProvider = "data")
    public void MajorationPPFromExcel(String id, String libelle,
                                      String typeMajoration, String paiement, String nature,
                                      String regime, String dateDebut, String dateFin, String echeance,
                                      String periode, String annee, String pivotdate, String resultat) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        if (TestData.TRIMESTRIELLE.equals(typeMajoration)) {
            DaoFunctionality.getNascaDao().updateExecuteFalseDateEnrolementMajoPPTrimestrielle(calJour.get(Calendar.YEAR), DateUtils.getQuarterNumber(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, calJour.getTime())));
        } else {
            DaoFunctionality.getNascaDao().updateExecuteFalseDateEnrolementMajoPPAnnuelle(calJour.get(Calendar.YEAR));
        }

        loginNasca();

        String niss = null;

        switch (regime) {
            case "RDA":
                niss = selectionDossierRDA(paiement, nature, dateDebut, dateFin, periode);
                break;
            case "RDE":
                niss = selectionDossierRDE(paiement, nature, dateDebut, periode);
                break;
            default:
                break;
        }
        if (TestData.TRIMESTRIELLE.equals(typeMajoration)) {
            doMajorationsTrimestriellesPP(niss, DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, calJour.getTime()));
        } else {
            doMajorationsAnnuellesPP(niss, DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, calJour.getTime()));
        }

        if (Boolean.valueOf(resultat)) {
            checkMajorationPP(niss, SeleniumUtils.deleteFormat(periode), dateOperationComptable, typeMajoration);
        } else {
            checkPasMajorationPP(niss, SeleniumUtils.deleteFormat(periode), dateOperationComptable, typeMajoration);
        }

        finishTestExecution();
    }

    private String selectionDossierRDE(String paiement, String nature, String dateDebut, String periode) {
        switch (paiement) {
            case "Nul":
                return DaoFunctionality.getNascaDao().getNissRDEPaiementNulEnrolMajoPP(periode, nature, dateDebut);
            case "Partiel":
                return DaoFunctionality.getNascaDao().getNissRDEPaiementPartielEnrolMajoPP(periode, nature, dateDebut);
            case "Total":
                return DaoFunctionality.getNascaDao().getNissRDEPaiementTotalEnrolMajoPP(periode, nature, dateDebut);
            default:
                return null;
        }
    }

    private String selectionDossierRDA(String paiement, String nature, String dateDebut, String dateFin, String periode) {
        switch (paiement) {
            case "Nul":
                return DaoFunctionality.getNascaDao().getNissRDAPaiementNulEnrolMajoPP(periode, nature, dateDebut, dateFin);
            case "Partiel":
                return DaoFunctionality.getNascaDao().getNissRDAPaiementPartielEnrolMajoPP(periode, nature, dateDebut, dateFin);
            case "Total":
                return DaoFunctionality.getNascaDao().getNissRDAPaiementTotalEnrolMajoPP(periode, nature, dateDebut, dateFin);
            default:
                return null;
        }
    }

    @Test(priority = 1)
    public void tearDown() throws IOException, SftpException {
        tearDownEnrolement();
    }
}