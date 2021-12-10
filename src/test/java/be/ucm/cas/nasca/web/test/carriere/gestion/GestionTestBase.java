package be.ucm.cas.nasca.web.test.carriere.gestion;

import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.TestBase;

import static be.ucm.cas.nasca.web.test.support.EnumTypeTest.AUTRE;

/**
 * Created by sylvie.degraef on 7/06/2017.
 */
public class GestionTestBase extends TestBase {



    @Override
    public EnumTypeTest getTypeTest() {
        return AUTRE;
    }



    static void gestioncarriereenactivite(String niss, String anneeTrimestre,String annee,String anneeReference,String anneeTrimestre2) {


        try {
            initTest("CarriereGestion", Thread.currentThread().getStackTrace()[1].getFileName());
            loginNasca();
            loadNissOrBce(niss);
            gestionClientPage.clickMenuGestionCarriere();

            // sélection sur le trimestre d'une année de carrière
            gestionClientPage.clickBtnRechercheGestionCarriereTrimestreAnnee();
            gestionClientPage.selectTrimestreAnneeRechercheCarriere(anneeTrimestre);
            gestionClientPage.clickBtnRechercheConsultationCarriere();

            // sélection sur l'année de Gestion de Carrière
            gestionClientPage.clickBtnRechercheGestionCarriereAnnee();
            gestionClientPage.selectAnneeRechercheCarriere(annee);
            gestionClientPage.clickBtnRechercheConsultationCarriere();

            // cliquer sur trimestre 2016/4 et faire ajouter
            gestionClientPage.selectLigneGestionCarriereAnnee();
            gestionClientPage.clickBtnAjouterGestionCarriere();

            // test panel correction d'une ligne carrière
            gestionClientPage.clickBtnTrimestre1Carriere();
            gestionClientPage.clickBtnTrimestre2Carriere();
            gestionClientPage.clickBtnTrimestre3Carriere();
            gestionClientPage.clickBtnTrimestre4Carriere();

            gestionClientPage.clickCotisationOrdinaireCarriere();
            gestionClientPage.clickCotisationDeRegularisationCarriere();
            gestionClientPage.selectSousNumeroCarriere("1");
            gestionClientPage.clickLigneSansCalculCotiCarriere();
            gestionClientPage.clickCotisationPresumeeCarriere();


            // cliquer sur Code activité et choisir 02 complémentaire
            gestionClientPage.selectCodeActiviteCarriere("02");


            // mettre des valeurs dans les zones qui suivent
            gestionClientPage.fillRevenuCommuniqueCarriere("10000");
            gestionClientPage.fillDateEcheanceCarriere(DateUtils.getDateToday());
            gestionClientPage.fillRevenuAdapteCarriere("10000");
            gestionClientPage.fillAnneeReferenceCarriere(anneeReference);
            gestionClientPage.clickRevenuProvisoireCarriere();
            gestionClientPage.fillRevenuComposeCarriere("10000");
            gestionClientPage.fillDatePaiementTardifCarriere(DateUtils.getDateToday());
            gestionClientPage.fillRevenuContributionCarriere("10000");
            gestionClientPage.fillRevenuBaseDeCalculCarriere("10000");
            gestionClientPage.clickRevenuDuplique1Carriere();
            gestionClientPage.clickRevenuDuplique2Carriere();
            gestionClientPage.clickRevenuDuplique3Carriere();
            gestionClientPage.clickBtnEnregistrerCarriere();
            gestionClientPage.clickBtnAnnulerCarriere();


            // bouton réinitialiser
            gestionClientPage.clickBtnReinitialiserCarriere();

            // sélection sur le trimestre d'une année de carrière
            gestionClientPage.clickBtnRechercheGestionCarriereTrimestreAnnee();
            gestionClientPage.selectTrimestreAnneeRechercheCarriere(anneeTrimestre2);
            gestionClientPage.clickBtnRechercheConsultationCarriere();

            gestionClientPage.selectLigneGestionCarriereAnnee();
            gestionClientPage.clickPencilGestionCarriere();

            gestionClientPage.clickRevenuDuplique1Carriere();
            gestionClientPage.clickRevenuDuplique2Carriere();
            gestionClientPage.clickRevenuDuplique4Carriere();

            gestionClientPage.clickBtnAnnuler2Carriere();

        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

}
