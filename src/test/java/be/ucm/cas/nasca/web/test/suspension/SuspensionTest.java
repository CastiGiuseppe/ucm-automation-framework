package be.ucm.cas.nasca.web.test.suspension;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by jb.delvaux on 3/07/2017.
 */



public class SuspensionTest extends SuspensionTestBase {

    //private static final String NISS_ENTREPRISE_PP = "960214129";
    //private static final String BCE_ENTREPRISE_PM = "0469618273";


    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_SuspensionRecouvrement.xlsx", "Suspension", "Suspension", "SignalétiqueSuspension", 69);
    }

    @Test(dataProvider = "data")
    public void suspensionFromExcel(String id, String typeTest, String libellé, String soldes, String cause, String application, String justification, String aRevoir, String date, String suspensionFuture, String type, String debPeriode, String finPeriode
    ){
        initTest(libellé, Thread.currentThread().getStackTrace()[1].getFileName());


        logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "Login nasca");
        loginNasca();
        logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "Query");
        String NumClient;
        String periodeEC = null;
        switch (typeTest) {
            case "PM Compte0":  //NASCARCVT 1996
                NumClient = DaoFunctionality.getNascaDao().getBCESuspensionSolde0();
                break;
            case "PM ComptePos":  //NASCARCVT 1996
                NumClient = DaoFunctionality.getNascaDao().getBCESuspensionCotEnCompte();
                break;
            case "PP CotRégul": //NASCARCVT 1952
                NumClient = DaoFunctionality.getNascaDao().getNISSSuspensionCotRegul();
                break;
            case "PP MultCotRégul":  //NASCARCVT 1952
                NumClient = DaoFunctionality.getNascaDao().getNISSSuspensionMultCotRegul();
                break;
            case "PP TypeMult":  //NASCARCVT 1953
                NumClient = DaoFunctionality.getNascaDao().getNISSSuspensionTypeMult();
                break;
            case "PP TypeMultMaj":  //NASCARCVT 1954
                NumClient = DaoFunctionality.getNascaDao().getNISSSuspensionTypeMultMaj();
                break;
            case "PP SingleMaj":  //NASCARCVT 1954
                NumClient = DaoFunctionality.getNascaDao().getNISSSuspensionMaj();
                break;
            case "PP MultCot":  //NASCARCVT 1978
                NumClient = DaoFunctionality.getNascaDao().getNISSSuspensionMultipleCotisation();
                break;
            case "PM MultCot":  //NASCARCVT 1978
                NumClient = DaoFunctionality.getNascaDao().getBCESuspensionMultipleCotisation();
                break;

            case "PP Princ":  //NASCARCVT 1983
            case "PP PrincSingle":  //NASCARCVT 1951
                NumClient = DaoFunctionality.getNascaDao().getNISSCotPrincipal();
                break;
            case "PP Comp":  //NASCARCVT 1983
            case "PP CompSingle":  //NASCARCVT 1951
                NumClient = DaoFunctionality.getNascaDao().getNISSCotComp();
                break;
            case "PP ConjAidSingle": //NASCARCVT 1983
            case "PP ConjAid":  //NASCARCVT 1951
                NumClient = DaoFunctionality.getNascaDao().getNISSCotConjAid();
                break;

            case "PP AvecCodebit": //NASCARCVT 1959
            case "PP NoCodebit":  //NASCARCVT 1956
                NumClient = DaoFunctionality.getNascaDao().getNISSSuspensionCodebit();
                break;

            case "PP ECPartielle":  //NASCARCVT 1977
                Map<String, Object> retour = DaoFunctionality.getNascaDao().getNISSSuspensionECPartielle();
                NumClient = retour.get("NUM_IDENTIFIANT").toString();
                periodeEC = retour.get("PERIODE").toString();
                //NumClient = "89061147143";
                logInfo("test","test");
                break;

            case "PM ECPartielle":  //NASCARCVT 1977
                Map<String, Object> retourPM = DaoFunctionality.getNascaDao().getBCESuspensionECPartielle();
                NumClient = retourPM.get("NUM_IDENTIFIANT").toString();
                periodeEC = retourPM.get("PERIODE").toString();
                break;
            case "PP MultCotMultCheckAuto": //NASCARCVT 1986
                NumClient =DaoFunctionality.getNascaDao().getNISSSuspensionPlanApurement();
                break;
            case "PM MultCotMultCheckAuto": //NASCARCVT 1986
                NumClient =DaoFunctionality.getNascaDao().getBCESuspensionPlanApurement();
                break;

            case "PP FutCotConcerne": //NASCARCVT 1995
                NumClient =DaoFunctionality.getNascaDao().getNISSSuspensionCotEnCompte();
            break;


            case "PP ModifSuspension":  //NASCARCVT 1982
            case "PP ACloturer":  //NASCARCVT 1968
            case "PP MultCotMultCheck":  //NASCARCVT 1986
            case "PP LeverSuspension":  //NASCARCVT 1976
            case "PP CheckAllBox": //NASCARCVT 1981
            case "PP Observation": //NASCARCVT 1973
            case "PP Quitter":  //NASCARCVT 1972
            case "PP DateCohérence": //NASCARCVT 1971
            case "PP ARevoirCohérence": //NASCARCVT 1979
            case "PP ARevoirDateCohérence": //NASCARCVT 1980
            case "PP ARevoir": //NASCARCVT 1967
            case "PP FutOrd": //NASCARCVT 1962
            case "PP FutRegul": //NASCARCVT 1963
            case "PP FutOrdRegul": //NASCARCVT 1961
            case "PP": //NASCARCVT 1955
            case "PP Single":  //NASCARCVT 1953
                NumClient =DaoFunctionality.getNascaDao().getNISSSuspensionCotEnCompte();
                break;

            case "PP FutOrdRegulA0": //NASCARCVT 1964
            case "PP FutOrdA0": //NASCARCVT 1965
            case "PP FutRegulA0": //NASCARCVT 1966
                NumClient =DaoFunctionality.getNascaDao().getNISSSuspensionCompteA0();
                break;

            case "PM AvecCodebit": //NASCARCVT 1959
            case "PM NoCodebit":  //NASCARCVT 1956
                NumClient = DaoFunctionality.getNascaDao().getBCESuspensionCodebit();
                break;


            case "PM ModifSuspension": //NASCARCVT 1982
            case "PM ACloturer": //NASCARCVT 1968
            case "PM MultCotMultCheck": //NASCARCVT 1986
            case "PM LeverSuspension": //NASCARCVT 1976
            case "PM CheckAllBox": //NASCARCVT 1981
            case "PM DateCohérence": //NASCARCVT 1971
            case "PM ARevoirCohérence": //NASCARCVT 1979
            case "PM ARevoirDateCohérence": //NASCARCVT 1980
            case "PM Quitter": //NASCARCVT 1972
            case "PM Observation": //NASCARCVT 1973
            case "PM": //NASCARCVT 1955
            default:
                NumClient = DaoFunctionality.getNascaDao().getBCESuspensionCotEnCompte();
                break;
        }
        logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "Ajout suspension");
        ajoutSuspension(NumClient, typeTest, cause, application, justification, aRevoir, date, suspensionFuture, type, debPeriode, finPeriode, soldes, periodeEC);

        logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "Verification suspension");
        verificationSuspension(NumClient, typeTest, cause, application, justification, aRevoir, date, suspensionFuture, type, debPeriode, finPeriode, soldes, periodeEC);


        finishTestExecution();
    }

    private void ajoutSuspension(String bce, String typeTest, String cause, String application, String justification, String aRevoir, String date, String suspensionFuture, String type, String debPeriode, String finPeriode, String soldes, String periodeEC){
        SuspensionTestBase.fillSuspensionRecouvrement(bce, typeTest, cause, application, justification, aRevoir, date, suspensionFuture, type, debPeriode, finPeriode, soldes, periodeEC);


    }

    private void verificationSuspension(String bce, String typeTest, String cause, String application, String justification, String aRevoir, String date, String suspensionFuture, String type, String debPeriode, String finPeriode, String soldes, String periodeEC){
        checkExistenceSuspension(bce, typeTest, cause, application, justification, aRevoir, date, suspensionFuture, type, debPeriode, finPeriode, soldes, periodeEC);



    }

}
