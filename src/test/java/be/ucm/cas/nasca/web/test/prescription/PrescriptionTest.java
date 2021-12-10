package be.ucm.cas.nasca.web.test.prescription;

import be.ucm.cas.nasca.web.test.carriere.CarriereTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

public class PrescriptionTest extends PrescriptionTestBase {

    private static final Map<Integer, String> MAP_REVISION = new LinkedHashMap<>();

    private static List<String> LIST_NISS = new ArrayList<>();

    private static List<String> LIST_BCE = new ArrayList<>();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Prescription.xlsx", "Prescription", "Prescription", "Presc", null);
    }

    @Test(dataProvider = "data")
    public void prescriptionFromExcel(String id, String libelle,
                                      String revision, String action, String menu, String typePrescription,
                                      String partielleOuTotale, String typeAffilie, String modifDatePrescription,
                                      String codeActivite, String codeProvisoire) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        String periode = DateUtils.getPreviousQuarterofDate(DateUtils.getDateToday());
        periode = periode.substring(0, 4) + "/" + periode.substring(4, 5);
        if (TestData.TYPE_PM.equals(typeAffilie)) {
            Calendar calJour = Calendar.getInstance();
            calJour.add(Calendar.YEAR, -1);
            periode = String.valueOf(calJour.get(Calendar.YEAR));
        }

        String nissOrBce = null;
        if (revision == null || StringUtils.isEmpty(revision.trim())) {
            if (TestData.TYPE_PP.equals(typeAffilie)) {
                if (LIST_NISS.isEmpty()) {
                    LIST_NISS = DaoFunctionality.getNascaDao().getNissForPrescriptionPP(periode);
                }
                nissOrBce = LIST_NISS.get(0);
                LIST_NISS.remove(0);
            } else {
                if (LIST_BCE.isEmpty()) {
                    LIST_BCE = DaoFunctionality.getNascaDao().getBceForPrescriptionPM(periode);
                }
                nissOrBce = LIST_BCE.get(0);
                LIST_BCE.remove(0);
            }
        }

        if (nissOrBce != null) {
            MAP_REVISION.put(Integer.valueOf(id), nissOrBce);
        } else {
            nissOrBce = MAP_REVISION.get(Integer.valueOf(revision));
        }

        if (!doPrescription(nissOrBce, action, menu, typePrescription, partielleOuTotale, modifDatePrescription, periode, typeAffilie)) {
            if (!"Actes interruptifs".equals(menu)) {
                if ("Annulation".equals(action)) {
                    EnrolementPPTestBase.checkCreanceEtTypeOperationTrimestrePP(nissOrBce, periode, TestData.COTISATION, TestData.PRESCRIPTION_ANNULATION, null);
                } else {
                    EnrolementPPTestBase.checkCreanceEtTypeOperationTrimestrePP(nissOrBce, periode, TestData.COTISATION, TestData.PRESCRIPTION, null);
                }
            }
        }

        if (!StringUtils.isEmpty(codeActivite.trim())) {
            CarriereTestBase.checkCarriere(nissOrBce, "trimestre", periode.substring(5), periode.substring(0, 4), "4", codeActivite, null, codeProvisoire, periode.substring(0, 4), "false", null);
        }

        finishTestExecution();
    }
}