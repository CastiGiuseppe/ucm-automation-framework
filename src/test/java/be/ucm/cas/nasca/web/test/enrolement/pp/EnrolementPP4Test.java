package be.ucm.cas.nasca.web.test.enrolement.pp;

import com.jcraft.jsch.SftpException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

public class EnrolementPP4Test extends EnrolementPPAbstract {

    private static final String TRIMESTRE = "4";

    private static final Map<String, List<String>> MAP_NISS = new LinkedHashMap<>();

    @BeforeTest()
    public void launchEnrolementPP() {
        doLaunchEnrolement(TRIMESTRE, Calendar.OCTOBER);
    }

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Enrôlement.xlsx", "Enrôlement PP", "Enrôlement PP", "EnrolPP" + TRIMESTRE, null);
    }

    @Test(dataProvider = "data")
    public void selectionNiss(String id, String libelle,
                              String booleanDate60ans, String booleanDate65ans, String nature,
                              String regime, String dateDebut1, String provisoire,
                              String typeRevenu, String revenu, String article11,
                              String plancher, String anneeRevenu,
                              String exoReduction, String domiciliation, String credit,
                              String dispense, String cotisantPLC, String cotisation, String codeActivite,
                              String revenuCommunique, String revenuAdapte, String revenuCompose,
                              String codeProvisoire, String anneeReference, String revenuContribution,
                              String revenuBase,
                              String jour, String mois, String annee1, String skip) {
        doSelectionNiss(id, libelle, booleanDate60ans, booleanDate65ans, nature, regime, dateDebut1,
                article11, exoReduction,
                skip, MAP_NISS, getDate60Ans(), getDate65Ans(), Thread.currentThread().getStackTrace()[1].getFileName(), anneeRevenu, typeRevenu);
    }

    @Test(dataProvider = "data", priority = 1)
    public void preparationProfilEtRevenus(String id, String libelle,
                                           String booleanDate60ans, String booleanDate65ans, String nature,
                                           String regime, String dateDebut1, String provisoire,
                                           String typeRevenu, String revenu, String article11,
                                           String plancher, String anneeRevenu,
                                           String exoReduction, String domiciliation, String credit,
                                           String dispense, String cotisantPLC, String cotisation, String codeActivite,
                                           String revenuCommunique, String revenuAdapte, String revenuCompose,
                                           String codeProvisoire, String anneeReference, String revenuContribution,
                                           String revenuBase,
                                           String jour, String mois, String annee1, String skip) {
        doPreparationProfilEtRevenus(id, libelle,
                typeRevenu, revenu, anneeRevenu, exoReduction,
                skip, MAP_NISS, getCalJour(), provisoire, Thread.currentThread().getStackTrace()[1].getFileName(), nature, regime, cotisantPLC, article11);
    }

    @Test(priority = 2)
    public void launchBatch() {
        doLaunchBatch(MAP_NISS, getCalJour());
    }

    @Test(dataProvider = "data", priority = 3)
    public void checkEnrolement(String id, String libelle,
                                String booleanDate60ans, String booleanDate65ans, String nature,
                                String regime, String dateDebut1, String provisoire,
                                String typeRevenu, String revenu, String article11,
                                String plancher, String anneeRevenu,
                                String exoReduction, String domiciliation, String credit,
                                String dispense, String cotisantPLC, String cotisation, String codeActivite,
                                String revenuCommunique, String revenuAdapte, String revenuCompose,
                                String codeProvisoire, String anneeReference, String revenuContribution,
                                String revenuBase,
                                String jour, String mois, String annee1, String skip) {
        doCheckEnrolement(id, libelle,
                cotisation, skip, MAP_NISS, TRIMESTRE, getCalJour(),
                Thread.currentThread().getStackTrace()[1].getFileName(), codeActivite, revenuCommunique, revenuAdapte, revenuCompose, codeProvisoire, anneeReference, revenuContribution, revenuBase);
    }

    @Test(priority = 4)
    public void tearDown() throws IOException, SftpException {
        tearDownEnrolement();
    }
}