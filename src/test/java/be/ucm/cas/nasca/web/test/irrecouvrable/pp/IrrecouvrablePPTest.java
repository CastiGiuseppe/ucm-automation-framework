package be.ucm.cas.nasca.web.test.irrecouvrable.pp;

import be.ucm.cas.nasca.web.test.carriere.CarriereTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

public class IrrecouvrablePPTest extends IrrecouvrablePPTestBase {

    private static final Map<Integer, String> MAP_REVISION = new LinkedHashMap<>();
    private static final String NOM_DOCUMENT = "ALL - Irrécouvrable";

    private static List<String> listNiss = new ArrayList<>();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Irrécouvrable.xlsx", "Irrécouvrable", "Irrécouvrable", "Irrec", null);
    }

    @Test(dataProvider = "data")
    public void irrecouvrablePPFromExcel(String id, String libelle,
                                         String revision, String action, String provOuDef, String revisionIrrecouvrable,
                                         String simulationAnnulation, String annulationPartielleOuTotale,
                                         String redebitionMajorations, String motif, String modifDatePrescription, String commentaire,
                                         String envoiLettre, String codeActivite, String codeProvisoire) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        String periode = DateUtils.getPreviousQuarterofDate(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, DateUtils.getDateFuturOrPass(-90, new Date())));

        periode = periode.substring(0, 4) + "/" + periode.substring(4, 5);

        String niss = null;
        if (revision == null || StringUtils.isEmpty(revision.trim())) {
            if (listNiss.isEmpty()) {
                listNiss = DaoFunctionality.getNascaDao().getNissForIrrecouvrable(periode);
            }
            niss = listNiss.get(0);
            listNiss.remove(0);
        }

        if (niss != null) {
            MAP_REVISION.put(Integer.valueOf(id), niss);
        } else {
            niss = MAP_REVISION.get(Integer.valueOf(revision));
        }

        doIrrecouvrable(niss, action, provOuDef, motif,
                modifDatePrescription, commentaire, envoiLettre, periode,
                simulationAnnulation, annulationPartielleOuTotale,
                redebitionMajorations);

        if ("Annulation".equals(action)) {
            if ("Totale".equals(annulationPartielleOuTotale)) {
                EnrolementPPTestBase.checkCreanceEtTypeOperationAnnuleeTrimestrePP(niss, periode, null);
            } else {
                EnrolementPPTestBase.checkCreanceEtTypeOperationTrimestrePP(niss, periode, TestData.COTISATION, "Irrecouvrabilité (Annulation", null);
            }
        } else {
            EnrolementPPTestBase.checkCreanceEtTypeOperationTrimestrePP(niss, periode, TestData.COTISATION, "Irrecouvrabilité", null);
        }

        if (Boolean.valueOf(envoiLettre)) {
            String dateDocument = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR,
                    new Date());
            ImpressionTestBase.checkImpressionDernierDocument(niss,
                    TestData.TYPE_NISS, dateDocument, null, null,
                    TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT);
        }

        if (!StringUtils.isEmpty(codeActivite.trim())) {
            CarriereTestBase.checkCarriere(niss, "trimestre", periode.substring(5), periode.substring(0, 4), "4", codeActivite, null, codeProvisoire, periode.substring(0, 4), "false", null);
        }

        finishTestExecution();
    }
}