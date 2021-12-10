package be.ucm.cas.nasca.web.test.transfertin.pp;

import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Iterator;

public class AffiliationPPTransfertInTest extends AffiliationPPTransfertInTestBase {

    private static final String NOM_DOCUMENT = "PP - Transfert IN";
    private static final String NISS = "92120425193";

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Affiliation.xlsx", "TransfertIn", "TransfertIn", TestData.TYPE_PP, null);
    }

    @Test(dataProvider = "data")
    public void affiliationPpTransfertTest(String id, String libelle, String nature, String periode, String reponse, String revenu,
                                                String dateAssu, String templateDemande, String templateDecision) {
        initTest("Sc." + id + " - " + libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(NISS);
        DaoFunctionality.cleanAllFluxByIdentite(NISS);

        String dateDocument = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date());

        chargementTransfertInFromFlatFile(NISS, templateDemande);
        traitementDemandeTransfertInEnAttente(NISS);

        if ("accepté".equals(reponse)) {
            ImpressionTestBase.checkAucunImpressionAImprimer(NISS, TestData.TYPE_NISS, dateDocument, null, null, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT);

            chargementTransfertInFromFlatFile(NISS, templateDecision);
            traitementDemandeTransfertDecision(NISS);

            checkIfClient(NISS);
            ImpressionTestBase.checkImpression(NISS, TestData.TYPE_NISS, dateDocument, null, null, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT, null);
            EnrolementPPTestBase.checkCotisationAnneeTrimestrePP(NISS, DateUtils.getQuarterofDate(DateUtils.getDateToday()));
        } else {
            ImpressionTestBase.checkAucunImpressionAImprimer(NISS, TestData.TYPE_NISS, dateDocument, null, null, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT);

            chargementTransfertInFromFlatFile(NISS, templateDecision);
            traitementDemandeTransfertDecision(NISS);

            checkIfNotClient(NISS);
            ImpressionTestBase.checkImpression(NISS, TestData.TYPE_NISS, dateDocument, null, null, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT, null);
            EnrolementPPTestBase.checkCotisationAnneeTrimestrePP(NISS, DateUtils.getQuarterofDate(DateUtils.getDateToday()));
        }

        finishTestExecution();
    }
}