package be.ucm.cas.nasca.web.test.carriere.consultation;

import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.TableUtils;
import be.ucm.cas.nasca.web.test.support.TestBase;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static be.ucm.cas.nasca.web.test.support.EnumTypeTest.AUTRE;

/**
 * Created by sylvie.degraef on 7/06/2017.
 */
public class ConsultationTestBase extends TestBase {



    @Override
    public EnumTypeTest getTypeTest() {
        return AUTRE;
    }



    static void consultationcarriereenactivite(String niss) {

        String annee = null;
        String trimestre = null;
        String anneeTrimestre = null;
        boolean isLignes = false;

        try {
            initTest("CarriereConsultation", Thread.currentThread().getStackTrace()[1].getFileName());
            loginNasca();
            loadNissOrBce(niss);
            gestionClientPage.clickMenuConsultationCarriere();
            // alimentation annee et trimestre du tableau de carrière
            annee = gestionClientPage.clickSearchAnneeCarriere(annee);
            trimestre = gestionClientPage.clickSearchTrimestreCarriere(trimestre);
            anneeTrimestre = annee.concat(trimestre);
            // affichage des périodes transmises à l'INASTI
            gestionClientPage.clickOuvrirAccordeonCritereRechercheConsultationCarriere();
            gestionClientPage.checkSelectionTypeAffichageCarriere("1");
            gestionClientPage.clickBtnRechercheConsultationCarriere();

            // affichage des périodes valables pour la pension transmises à l'INASTI
            gestionClientPage.clickOuvrirAccordeonCritereRechercheConsultationCarriere();
            gestionClientPage.checkSelectionTypeAffichageCarriere("2");
            gestionClientPage.clickBtnRechercheConsultationCarriere();

            // affichage des périodes non valables pour la pension transmises à l'INASTI
            gestionClientPage.clickOuvrirAccordeonCritereRechercheConsultationCarriere();
            gestionClientPage.checkSelectionTypeAffichageCarriere("3");
            gestionClientPage.clickBtnRechercheConsultationCarriere();


            // affichage des lignes de carrière en attente
            gestionClientPage.clickOuvrirAccordeonCritereRechercheConsultationCarriere();
            gestionClientPage.checkSelectionTypeAffichageCarriere("4");
            gestionClientPage.clickBtnRechercheConsultationCarriere();

            // affichage de tous les mouvements de carrière
            gestionClientPage.clickOuvrirAccordeonCritereRechercheConsultationCarriere();
            gestionClientPage.checkSelectionTypeAffichageCarriere("5");
            gestionClientPage.clickBtnRechercheConsultationCarriere();

            // affichage d'une année de carrière
            gestionClientPage.clickOuvrirAccordeonCritereRechercheConsultationCarriere();
            gestionClientPage.checkSelectionCarriere("annee");
            gestionClientPage.selectAnneeRechercheCarriere(annee);
            gestionClientPage.checkSelectionTypeAffichageCarriere("5");
            gestionClientPage.clickBtnRechercheConsultationCarriere();
            if (gestionClientPage.isNbrLignesCarriereOK(4,isLignes)) {
                logSuccess("Check operation Lignes année carrière  OK");
            } else {
                logFailed("Check operation Lignes année carrière KO");
            }

            // affichage d'une année/trimestre de carrière
            gestionClientPage.clickOuvrirAccordeonCritereRechercheConsultationCarriere();
            gestionClientPage.checkSelectionCarriere("trimestre");
            gestionClientPage.selectAnneeTrimRechercheCarriere(anneeTrimestre);
            gestionClientPage.checkSelectionTypeAffichageCarriere("5");
            gestionClientPage.clickBtnRechercheConsultationCarriere();
            if (gestionClientPage.isNbrLignesCarriereOK(1,isLignes)) {
                logSuccess("Check operation Lignes année/trimestre carrière  OK");
            } else {
                logFailed("Check operation Lignes année/trimestrel carrière KO");
            }

            // réinitialiser la page
            gestionClientPage.clickOuvrirAccordeonCritereRechercheConsultationCarriere();
            gestionClientPage.clickBtnReinitialiser();



        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void consultationcarrieresuite() {


        try {

            // cliquer sur Profil
            gestionClientPage.clickOuvrirAccordeonRechercheProfilConsultationCarriere();

            // cliquer sur Element 2 du tableau
            gestionClientPage.clickElement2tableauConsultationCarriere();

            // cliquer sur Dernier élément du tableau
            gestionClientPage.clickDernierElementtableauConsultationCarriere();

            // cliquer sur  élément précédent du tableau
            gestionClientPage.clickElementPrecedenttableauConsultationCarriere();

            // cliquer sur élément suivant du tableau
            gestionClientPage.clickElementSuivanttableauConsultationCarriere();

            // cliquer sur Premier élément du tableau
            gestionClientPage.clickPremierElementtableauConsultationCarriere();

            // cliquer sur Afficher "25" éléments
            gestionClientPage.select25Elements();


        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }




}
