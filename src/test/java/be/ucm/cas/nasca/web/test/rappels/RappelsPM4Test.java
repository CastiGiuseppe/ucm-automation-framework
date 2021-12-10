package be.ucm.cas.nasca.web.test.rappels;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.Iterator;

public class RappelsPM4Test extends RappelsAbstract {

    private String date = null;

    private static final String TRIMESTRE = "4";

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Recouvrement.xlsx", "Rappels", "Rappels", "Rappels PM" + TRIMESTRE, null);
    }

    @BeforeTest
    public void launchRappelPM() {
        date = doTimeMachineChange(TRIMESTRE, -1);
    }

    @Test(dataProvider = "data")
    public void doTest(String id, String libelle, String batch, String requete, String decede,
                       String cotisationOrd, String cotisationRegularisation, String majoTrimestrielle,
                       String majoAnnuelle, String majoMensuelle, String bonification, String frais,
                       String solidaire, String echu, String prescrit, String suspendu,
                       String stadeRecouvrement, String seuil1Depasse,
                       String seuil2Depasse, String maxiStatutAv1956, String credit,
                       String procedureRecouvrement, String procedureRecouvrement1,
                       String procedureRecouvrement2, String procedureMajoEtCourrier,
                       String envoiCourrier, String envoiRECC, String notification, String checkRecouvrement) throws ParseException {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        rappelsPMFromExcel(batch, requete,
                solidaire, suspendu, stadeRecouvrement, seuil1Depasse,
                seuil2Depasse, procedureRecouvrement, procedureRecouvrement1, procedureRecouvrement2,
                procedureMajoEtCourrier, envoiCourrier, envoiRECC, notification, checkRecouvrement, date);

        finishTestExecution();
    }
}