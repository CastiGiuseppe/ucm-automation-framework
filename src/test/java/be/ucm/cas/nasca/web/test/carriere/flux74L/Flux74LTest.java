package be.ucm.cas.nasca.web.test.carriere.flux74L;


import be.ucm.cas.nasca.web.test.support.BatchUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import be.ucm.cas.nasca.web.test.taches.TachesTestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class Flux74LTest extends Flux74LTestBase {

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Carriere.xlsx", "Signal 74L", "FLUX 74L", "Flux74L", null);
    }

    @Test(dataProvider = "data")
    public void flux74LFromExcel (String id, String S74L,
                                  String typeNiss, String niss,
                                  String requestId, String pensionKind,
                                  String debutPension, String family, String spouse,
                                  String dateDebut, String activity, String epouse,
                                  String debut, String activite, String notification) throws Exception {
        initTest(S74L + " - Sc. " + id, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();


        doFlux74L(S74L,
                typeNiss, niss,
                requestId, pensionKind,
                debutPension, family, spouse,
                dateDebut, activity, epouse, debut, activite, notification);


        loadNissOrBce(niss);
        gestionClientPage.clickMenuSignaux74LInasti();
        gestionClientPage.clickSearchResultSignaux74LInastiATraiter();
        BatchUtils.doSendResponseSignal74L(niss, "/data/nasca/NascaInastiMock/nisseCarriereId-SignalChange");
        gestionClientPage.clickBtnTerminerSignaux74LInasti();
        TachesTestBase.searchAndFinishNotification(niss, notification);


        finishTestExecution();
    }




}