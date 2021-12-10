package be.ucm.cas.nasca.web.test.carriere.controleSpf;

import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.TestBase;

import static be.ucm.cas.nasca.web.test.support.EnumTypeTest.AUTRE;

/**
 * Created by sylvie.degraef on 7/06/2017.
 */
public class ControleSpfTestBase extends TestBase {



    @Override
    public EnumTypeTest getTypeTest() {
        return AUTRE;
    }



    static void controleSpfCarriere(String niss, String annee,String anneeTrimestre,String anneeTrimestre2) {


        try {
            initTest("CarriereControleSpf", Thread.currentThread().getStackTrace()[1].getFileName());
            loginNasca();
            loadNissOrBce(niss);

            // création d'une ligne en attente dans CArrière avant contrôle SPF
            gestionClientPage.clickMenuGestionCarriere();

            // sélection sur le trimestre d'une année de carrière
            gestionClientPage.clickBtnRechercheGestionCarriereTrimestreAnnee();
            gestionClientPage.selectTrimestreAnneeRechercheCarriere(anneeTrimestre);
            gestionClientPage.clickBtnRechercheConsultationCarriere();


            // cliquer sur trimestre 2016/4 et faire ajouter
            gestionClientPage.selectLigneGestionCarriereAnnee();
            gestionClientPage.clickPencilGestionCarriere();

            // cliquer sur Code activité et choisir 02 complémentaire
            //gestionClientPage.clickCotisationPresumeeCarriere();

            gestionClientPage.selectCodeActiviteCarriere("02");
            gestionClientPage.fillDateEcheanceCarriere(DateUtils.getDateToday());
            gestionClientPage.clickBtnEnregistrerCarriere();

            // bouton réinitialiser
            gestionClientPage.clickBtnReinitialiserCarriere();

            // sélection sur le trimestre d'une année de carrière
            gestionClientPage.clickBtnRechercheGestionCarriereTrimestreAnnee();
            gestionClientPage.selectTrimestreAnneeRechercheCarriere(anneeTrimestre2);
            gestionClientPage.clickBtnRechercheConsultationCarriere();


            // cliquer sur trimestre 2016/3et faire ajouter
            gestionClientPage.selectLigneGestionCarriereAnnee();
            gestionClientPage.clickPencilGestionCarriere();

            // cliquer sur Code activité et choisir 02 complémentaire

            //gestionClientPage.clickCotisationPresumee2Carriere();
            gestionClientPage.selectCodeActiviteCarriere("02");

            gestionClientPage.fillDateEcheanceCarriere(DateUtils.getDateToday());
            gestionClientPage.clickBtnEnregistrer2Carriere();



            // contrôle Spf

            gestionClientPage.clickMenuControleSpfCarriere();

            gestionClientPage.clickEncoderControleSpf();

            gestionClientPage.fillAnneeTrimestreControleSpfCarriere(anneeTrimestre);
            gestionClientPage.clickPlusSpfCarriere();
            gestionClientPage.fillDateControleSpfCarriere(DateUtils.getDateToday());

            gestionClientPage.selectControleSpfCourrier();
            gestionClientPage.clickBtnReinitialiserObservationControleSpfCarriere();

            gestionClientPage.selectControleSpfINASTI();
            gestionClientPage.clickBtnEnregistrerControleSpfCarriere();

            // sélection sur générale
            gestionClientPage.clickBtnRechercheControleSpfCarriere ();
            gestionClientPage.clickBtnRechercheConsultationCarriere();

            //sélectionner le controle spf créé
            gestionClientPage.SelectSpfCreeCarriere();
            gestionClientPage.clickModifierTrimestresSpf();

            //ajouter un autre trimestre (2016/3)
            gestionClientPage.fillAnneeTrimestreControleSpfCarriere(anneeTrimestre2);
            gestionClientPage.clickPlusSpfCarriere();

            gestionClientPage.selectControleSpfINASTI();
            gestionClientPage.clickBtnEnregistrerControleSpfCarriere();





        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

}
