package be.ucm.cas.nasca.web.test.signaletique;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SignaletiqueTest extends SignaletiqueTestBase {

    private static List<String> listNiss = new ArrayList<>();

    @Test
    public void changementAdresseIdentitePP() {
        initTest("Changement Adresse", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        if (listNiss.isEmpty()) {
            listNiss = DaoFunctionality.getNascaDao().getNissforSignaletique();
        }
        String niss = listNiss.get(0);
        listNiss.remove(0);

        SignaletiqueTestBase.doChangeAdresse(niss, "Belgique", "5060", "rue de la loi", "45", "1");

        finishTestExecution();
    }

    @Test
    public void suppressionAjoutModificationTelephoneIdentitePP() {
        initTest("Suppression, ajout et modification Telephone", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        if (listNiss.isEmpty()) {
            listNiss = DaoFunctionality.getNascaDao().getNissforSignaletique();
        }
        String niss = listNiss.get(0);
        listNiss.remove(0);

        SignaletiqueTestBase.doChangeTel(niss, "068319719", true);

        SignaletiqueTestBase.doChangeTel(niss, "071743246", false);

        finishTestExecution();
    }

    @Test
    public void suppressionAjoutModificationEmailIdentitePP() {
        initTest("Suppression, ajout et modification Email", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        if (listNiss.isEmpty()) {
            listNiss = DaoFunctionality.getNascaDao().getNissforSignaletique();
        }
        String niss = listNiss.get(0);
        listNiss.remove(0);

        SignaletiqueTestBase.doChangeEmail(niss, "test@test.be", true);

        SignaletiqueTestBase.doChangeEmail(niss, "testmodif@test.be", false);

        finishTestExecution();
    }

    @Test
    public void suppressionAjoutModificationCompteBancaireIdentitePP() {
        initTest("Suppression, ajout et modification Compte Bancaire", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        if (listNiss.isEmpty()) {
            listNiss = DaoFunctionality.getNascaDao().getNissforSignaletique();
        }
        String niss = listNiss.get(0);
        listNiss.remove(0);

        SignaletiqueTestBase.doChangeCompteBancaire(niss, "BE77001610786242", true);

        SignaletiqueTestBase.doChangeCompteBancaire(niss, "BE77001610786242", false);

        finishTestExecution();
    }

    @Test
    public void suppressionAjoutModificationContactIdentitePP() {
        initTest("Suppression, ajout et modification Contact", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        if (listNiss.isEmpty()) {
            listNiss = DaoFunctionality.getNascaDao().getNissforSignaletique();
        }
        String niss = listNiss.get(0);
        listNiss.remove(0);

        SignaletiqueTestBase.doChangeContact(niss, "Représentant légal", true);

        SignaletiqueTestBase.doChangeContact(niss, "Tuteur", false);

        finishTestExecution();
    }

    @Test
    public void suppressionAjoutLienCohabitationLegaleSansRaisonIdentitePP() {
        initTest("Suppression et ajout Cohabitation Legale sans raison", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER_SIGNA);

        if (listNiss.isEmpty()) {
            listNiss = DaoFunctionality.getNascaDao().getNissforSignaletique();
        }
        String niss = listNiss.get(0);
        listNiss.remove(0);

        SignaletiqueTestBase.doChangeCohabitationLegaleSansRaison(niss, TestData.NISS_NUMBER_SIGNA);

        finishTestExecution();
    }

    @Test
    public void suppressionAjoutLienCohabitationLegaleAvecRaisonIdentitePP() {
        initTest("Suppression et ajout Cohabitation Legale avec raison", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER_SIGNA);

        if (listNiss.isEmpty()) {
            listNiss = DaoFunctionality.getNascaDao().getNissforSignaletique();
        }
        String niss = listNiss.get(0);
        listNiss.remove(0);

        Calendar cal = Calendar.getInstance();

        String dateFin = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, cal.getTime());

        cal.add(Calendar.YEAR, -1);

        String dateDebut = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, cal.getTime());

        SignaletiqueTestBase.doChangeCohabitationLegaleAvecRaison(niss, dateDebut, dateFin, "Commun accord", TestData.NISS_NUMBER_SIGNA);

        finishTestExecution();
    }

    @Test
    public void suppressionAjoutModificationLienEtatCivilSansDateIdentitePP() {
        initTest("Suppression, ajout et modification Lien Etat Civil sans date", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        if (listNiss.isEmpty()) {
            listNiss = DaoFunctionality.getNascaDao().getNissforSignaletique();
        }
        String niss = listNiss.get(0);
        listNiss.remove(0);

        SignaletiqueTestBase.doChangeEtatCivilSansDate(niss, "Célibataire", true);

        SignaletiqueTestBase.doChangeEtatCivilSansDate(niss, "Célibataire", false);

        finishTestExecution();
    }

    @Test
    public void suppressionAjoutLienEtatCivilDebutEtFinIdentitePP() {
        initTest("Suppression et ajout Lien Etat avec debut et fin", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER_SIGNA);

        if (listNiss.isEmpty()) {
            listNiss = DaoFunctionality.getNascaDao().getNissforSignaletique();
        }
        String niss = listNiss.get(0);
        listNiss.remove(0);

        SignaletiqueTestBase.doChangeEtatCivilAvecDate(niss, TestData.NISS_NUMBER_SIGNA, "Marié", true);

        SignaletiqueTestBase.doChangeEtatCivilAvecDate(niss, TestData.NISS_NUMBER_SIGNA, "Divorcé", false);

        finishTestExecution();
    }

    @Test
    public void creationSignaletiquePP() {
        initTest("Creation Personne Physique", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER_SIGNA);

        SignaletiqueTestBase.creationPP(TestData.NISS_NUMBER_SIGNA);

        DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER_SIGNA);

        finishTestExecution();
    }

    @Test
    public void creationSignaletiquePM() {
        initTest("Creation Personne Morale", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(TestData.BCE_NUMBER_SIGNA);

        SignaletiqueTestBase.creationPM(TestData.BCE_NUMBER_SIGNA);

        DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER_SIGNA);

        finishTestExecution();
    }
}