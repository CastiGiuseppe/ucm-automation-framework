package be.ucm.cas.nasca.web.test.baremes.pm;

import org.testng.annotations.Test;

public class BaremesPMTest extends BaremesPmAbstract {

    private final String fileName = Thread.currentThread().getStackTrace()[1].getFileName();

    @Test
    public void ajoutCotisationAnnuelleFuturBaremePM() {
        initTest("Baremes PM (Cotisation annuelle) - Ajout Cotisation annuelle annee futur", fileName, 1);

        BaremesPMTestBase.ajoutCotisationAnnuelleBaremesPM(getDateDebut(), getDateFin());

        finishTestExecution();
    }

    @Test
    public void ajoutCotisationAnnuellePresentBaremePM() {
        initTest("Baremes PM (Cotisation annuelle) - Ajout Cotisation annuelle annee present", fileName, 0);

        BaremesPMTestBase.ajoutCotisationAnnuelleBaremesPM(getDateDebut(), getDateFin());

        finishTestExecution();
    }

    @Test
    public void ajoutCotisationAnnuellePasseBaremePM() {
        initTest("Baremes PM (Cotisation annuelle) - Ajout Cotisation annuelle annee passe", fileName, -3);

        BaremesPMTestBase.ajoutCotisationAnnuelleBaremesPM(getDateDebut(), getDateFin());

        finishTestExecution();
    }

    @Test
    public void ajoutEcheanceEnrolementAnnuelBaremePM() {
        initTest("Baremes PM (Echeance - Enrôlement annuel) - Ajout Echeance - Enrôlement annuel", fileName, null);

        BaremesPMTestBase.ajoutEcheanceBaremesPM(getDateDebut(), getDateFin(), "Enrôlement annuel");

        finishTestExecution();
    }

    @Test
    public void ajoutEcheanceAffiliationDurant1erTrimestreBaremePM() {
        initTest("Baremes PM (Echeance - Affiliation durant 1er trimestre) - Ajout Echeance - Affiliation durant 1er trimestre", fileName, null);

        BaremesPMTestBase.ajoutEcheanceBaremesPM(getDateDebut(), getDateFin(), "Affiliation durant 1er trimestre");

        finishTestExecution();
    }

    @Test
    public void ajoutEcheanceAffiliationApres1erTrimestreBaremePM() {
        initTest("Baremes PM (Echeance - Affiliation apres 1er trimestre) - Ajout Echeance - Affiliation apres 1er trimestre", fileName, null);

        BaremesPMTestBase.ajoutEcheanceBaremesPM(getDateDebut(), getDateFin(), "Affiliation après 1er trimestre");

        finishTestExecution();
    }

    @Test
    public void ajoutFraisGestionBaremePM() {
        initTest("Baremes PM (Frais de gestion) - Ajout Frais de gestion", fileName, null);

        BaremesPMTestBase.ajoutFraisBaremesPM(getDateDebut(), getDateFin(), "Frais de gestion");

        finishTestExecution();
    }

    @Test
    public void ajoutRappelRecommandeBaremePM() {
        initTest("Baremes PM (Rappel par recommande) - Ajout Rappel par recommande", fileName, null);

        BaremesPMTestBase.ajoutFraisBaremesPM(getDateDebut(), getDateFin(), "Rappel par recommande");

        finishTestExecution();
    }

    @Test
    public void ajoutMajorationBaremePM() {
        initTest("Baremes PM (Majoration) - Ajout Majoration", fileName, null);

        BaremesPMTestBase.ajoutMajorationBaremesPM(getDateDebut(), getDateFin());

        finishTestExecution();
    }
}
