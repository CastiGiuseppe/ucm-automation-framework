package be.ucm.cas.nasca.web.test.suspension;

import be.ucm.cas.nasca.web.pagefactory.signaletique.SignaletiquePage;
import be.ucm.cas.nasca.web.test.enrolement.pm.EnrolementPMTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.revenu.RevenuTestBase;
import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.TableUtils;
import be.ucm.cas.nasca.web.test.support.*;
import be.ucm.cas.nasca.web.test.support.TestBase;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jb.delvaux on 3/07/2017.
 */
public class SuspensionTestBase extends TestBase {


    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    static void fillSuspensionRecouvrement(String niss, String typeTest, String cause, String application, String justification, String aRevoir, String date, String suspensionFuture, String type, String debPeriode, String finPeriode, String soldes, String periodeEC){

        try{

            boolean suspension = Boolean.parseBoolean(suspensionFuture);

            //Ouverture de la session et début de la suspension
            loadNissOrBce(niss);
            logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "Chargement Niss");
            signaletiquePage.clickSuspension();
            signaletiquePage.clickAjouterSuspension();
            logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "Click ajout suspension");


            switch (typeTest) { //Choix des EC à sélectionné.
                case "false":
                    logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension check no box");
                    break;

                case "PP CotRégul": //NASCARCVT 1952
                    signaletiquePage.clickCheckboxSingleRegulSoldesPeriodique("Cotisation PP", soldes);
                    break;
                case "PP MultCotRégul": //NASCARCVT 1952
                    signaletiquePage.clickCheckboxMultRegulSoldesPeriodique("Cotisation PP", soldes);
                    break;

                case "PP Single": //NASCARCVT 1953
                    signaletiquePage.clickCheckboxSingleSoldesPeriodique("Cotisation PP");
                    break;
                case "PP TypeMultMaj": //NASCARCVT 1954
                    signaletiquePage.clickCheckboxMultMajSoldesPeriodique("Majoration PP");
                    break;
                case "PP SingleMaj": //NASCARCVT 1954
                    signaletiquePage.clickCheckboxSingleMajSoldesPeriodique("Majoration PP");
                    break;


                case "PP ECPartielle": //NASCARCVT 1977
                    signaletiquePage.clickCheckboxSingleSoldesECPartielle(periodeEC);
                    //signaletiquePage.clickCheckboxAllSoldesperiodique();
                    break;
                case "PM ECPartielle": //NASCARCVT 1977
                    signaletiquePage.clickCheckboxSingleSoldesECPartielle(periodeEC);
                    //signaletiquePage.clickCheckboxFirstSoldePeriodique();
                    break;


                case "PP CompSingle": //NASCARCVT 1951
                case "PP ConjAidSingle": //NASCARCVT 1951
                case "PP PrincSingle": //NASCARCVT 1951
                    signaletiquePage.clickCheckboxOrdinaireSingleSoldesPeriodique("Cotisation PP", soldes);
                    logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension check ordinaire box");
                    break;

                case "PP Comp": //NASCARCVT 1983
                case "PP ConjAid": //NASCARCVT 1983
                case "PP Princ": //NASCARCVT 1983
                    signaletiquePage.clickCheckboxOrdinaireSoldesPeriodique(soldes);
                    logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension check ordinaire box");
                    break;

                case "PP MultCot": //NASCARCVT 1978
                    signaletiquePage.clickCheckboxSingleSoldesPeriodique("Cotisation PP");
                    break;


                case "PM MultCot": //NASCARCVT 1978
                    signaletiquePage.clickCheckboxSingleSoldesPeriodique("Cotisation PM");
                    break;

                case "PP FutCotConcerne": //NASCARCVT 1955
                    signaletiquePage.clickCheckboxSingleFutCotConcerneSoldesPeriodique("2017/2");
                    break;

                case "PP CheckAllBox": //NASCARCVT 1981
                case "PM CheckAllBox": //NASCARCVT 1981
                    signaletiquePage.clickCheckboxAllSoldesperiodique();
                    checkStateBox();
                    break;

                case "PP TypeMult": //NASCARCVT 1953
                case "PM MultCot2": //NASCARCVT 1978
                case "PM MultCotMultCheck": //NASCARCVT 1986
                case "PM MultCotMultCheck2": //NASCARCVT 1986
                case "PP MultCot2": //NASCARCVT 1978
                case "PP MultCotMultCheck": //NASCARCVT 1986
                case "PP MultCotMultCheck2": //NASCARCVT 1986
                case "PM ComptePos": //NASCARCVT 1996
                case "PM CompteA0": //NASCARCVT 1996
                case "PP ModifSuspension"://NASCARCVT 1982
                case "PM ModifSuspension": //NASCARCVT 1982
                case "PP MultCotMultCheckAuto": //NASCARCVT 1986
                case "PM MultCotMultCheckAuto": //NASCARCVT 1986
                case "PP Observation": //NASCARCVT 1973
                case "PM Observation": //NASCARCVT 1973
                case "PP FutOrdRegulA0": //NASCARCVT 1964
                case "PP FutOrdA0": //NASCARCVT 1965
                case "PP FutRegulA0": //NASCARCVT 1966
                case "PP FutOrdRegul": //NASCARCVT 1961
                case "PP FutOrd": //NASCARCVT 1962
                case "PP FutRegul": //NASCARCVT 1963
                case "PP NoCodebit": //NASCARCVT 1956
                case "PM NoCodebit": //NASCARCVT 1956
                case "PP AvecCodebit": //NASCARCVT 1959
                case "PM AvecCodebit": //NASCARCVT 1959
                case "PP ACloturer": //NASCARCVT 1968
                case "PM ACloturer": //NASCARCVT 1969
                case "PP ARevoir": //NASCARCVT 1967
                case "PM ARevoir": //NASCARCVT 1970
                case "PP": //NASCARCVT 1955
                case "PM": //NASCARCVT 1955
                    signaletiquePage.clickCheckboxAllSoldesperiodique(); //cas le plus courant
                    logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension check all box");
                    break;
                default:
                    break;
            }

            //rentrée du reste des information pour la suspension
            logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension check boxes");
            nascaPage.clickBtnSuivantWizard();
            signaletiquePage.clickAjouterCauseSuspension(cause);
            signaletiquePage.clickAjouterJustificationSuspension(justification);
            signaletiquePage.clickRadioIdentité(application);
            signaletiquePage.clickRadioaRevoir(aRevoir);
            signaletiquePage.clickDateSuspension(date);
            signaletiquePage.clickRadioSuspensionFuture(suspension);
            //action supplémentaire en cas de suspension future
            if(suspension) {
                signaletiquePage.clickRadioTypeCotisation(type);
                signaletiquePage.clickPeriodeDebSuspension(debPeriode);
                signaletiquePage.clickPeriodeFinSuspension(finPeriode);
            }
            logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension terminer");
            nascaPage.clickBtnTerminerWizard();
            //Fin de l'ajout de la suspension. Renvoie automatiquement à l'écran de recouvrement pour commencer
            //les vérifications

        }catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }



    static void checkExistenceSuspension(String numero, String typeTest, String cause, String application, String justification, String aRevoir, String date, String suspensionFuture, String type, String debPeriode, String finPeriode, String soldes, String periodeEC) {
        try {


            //S'assure que la suspension a bien été ajouté à la liste des suspension.
            // Certains tests peuvent sauter/adapter cette étape.
            switch (typeTest) {
                case "PM MultCotMultCheck2": //NASCARCVT 1986
                case "PP MultCotMultCheck2": //NASCARCVT 1986
                    gestionClientPage.clickMenuCompteCotisantSoldes();
                    break;
                    
                case "PP DateCohérence": //NASCARCVT 1971
                case "PM DateCohérence": //NASCARCVT 1971
                        if (signaletiquePage.checkValueSuspension())
                            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Date invalide a bloqué");
                        else
                            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Date invalide est passée");

                        signaletiquePage.annulerSuspension();
                    break;

                case "PP ARevoirCohérence": //NASCARCVT 1979
                case "PM ARevoirCohérence": //NASCARCVT 1979
                    if (signaletiquePage.checkValueSuspension())
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Case non cochée a bloqué");
                    else
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Case non cochée est passée");

                    signaletiquePage.annulerSuspension();
                    break;

                case "PP ARevoirDateCohérence": //NASCARCVT 1980
                case "PM ARevoirDateCohérence": //NASCARCVT 1980
                    if (signaletiquePage.checkValueSuspension())
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Zone-non remplie a bloqué");
                    else
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "zone non remplie est passée");

                    signaletiquePage.annulerSuspension();
                    break;

                case "PP ModifSuspension": //NASCARCVT 1982
                case "PM ModifSuspension": //NASCARCVT 1982
                case "PP Observation": //NASCARCVT 1973
                case "PM Observation": //NASCARCVT 1973
                    if (TableUtils.isElementPresent("tableDemandes", justification))
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussite table suspension");
                    else
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension raté table suspension");
                    break;


                default://vérification classique. Vérifie la présence de la suspension et envoie à la page des comptes
                    if (TableUtils.isElementPresent("tableDemandes", justification))
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussite table suspension");
                    else
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension raté table suspension");


                    gestionClientPage.clickMenuCompteCotisantSoldes();


                    //Solution à un problème d'origine inconnue. Pour une raison inconnue,
                    // utiliser la chaine "Prescription (Droits pension)" comme cause ne rentre pas le (lors du remplissage
                    // de la suspension. Comme il est cependans nécessaire pour le reste de la vérification, il est modifier ici.
                    if (cause.equals("Prescription"))
                        cause = "Prescription (Droits pension)";

                break;
            }


            //Vérification de la présence de la suspension dans les comptes.
            // Certains tests peuvent sauter/adapater cette vérification.
            switch (typeTest) {
                case "PM CompteA0": //NASCARCVT 1996
                case "PP LeverSuspension": //NASCARCVT 1976
                case "PM LeverSuspension": //NASCARCVT 1976
                case "PP Observation": //NASCARCVT 1973
                case "PM Observation": //NASCARCVT 1973
                case "PP Quitter": //NASCARCVT 1972
                case "PM Quitter": //NASCARCVT 1972
                case "PP DateCohérence": //NASCARCVT 1971
                case "PM DateCohérence": //NASCARCVT 1971
                case "PM ARevoirCohérence": //NASCARCVT 1979
                case "PP ARevoirCohérence": //NASCARCVT 1979
                case "PM ARevoirDateCohérence": //NASCARCVT 1980
                case "PP ARevoirDateCohérence": //NASCARCVT 1980
                case "PP ModifSuspension": //NASCARCVT 1982
                case "PM ModifSuspension": //NASCARCVT 1982
                case "PP MultCotMultCheckAuto": //NASCARCVT 1986
                case "PM MultCotMultCheckAuto": //NASCARCVT 1986

                    break;

                case "PP FutRegulA0": //NASCARCVT 1966
                case "PP FutOrdRegulA0": //NASCARCVT 1964
                case "PP FutOrdA0": //NASCARCVT 1965

                    //comme ces test considère un tableau vide, la présence de la suspension serait une erreur
                    if (TableUtils.isElementPresent("entitesComptablesTable", cause))
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension raté comptes soldes");
                    else
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussite Comptes soldes");
                break;


                default:

                    //Vérifie l'impact de la suspension dans le tableau des comptes.
                    if (TableUtils.isElementPresent("entitesComptablesTable", cause))
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussite Comptes soldes");
                    else
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension raté comptes soldes");
                break;
            }

            // vérification unique pour chaque test.
            // Chaque test y retrouve ses propres règles. Pas de fonction par défaut.
            switch (typeTest){
                case "PP FutRegul": //NASCARCVT 1963
                case "PP FutRegulA0": //NASCARCVT 1966

                    RevenuTestBase.ajouterRevenuSansErreur(numero, "2017", "80000", "Fiscal","A régulariser","INASTI courrier", true, "13/07/2017", null);
                    // création de la date du batch sous String
                    String stringDateBatchRegul = DateUtils.getDateFutur(366, TestData.DATE_FORMAT_XML);
                    //changement de la date
                    RunBatch.runBatchChangeDate(stringDateBatchRegul);
                    SeleniumUtils.waitForBatch();

                    loginNasca();
                    loadNissOrBce(numero);
                    gestionClientPage.clickMenuCompteCotisantSoldes();
                    if (TableUtils.isElementPresent("entitesComptablesTable", cause))
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussite Comptes soldes");
                    else
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension raté comptes soldes");


                    break;

                case "PM ComptePos": //NASCARCVT 1996
                case "PM CompteA0": //NASCARCVT 1996
                case "PP FutOrdRegulA0": //NASCARCVT 19
                case "PP FutOrdA0": //NASCARCVT 1965
                case "PP FutOrdRegul": //NASCARCVT 1961
                case "PP FutOrd": //NASCARCVT 1962


                    // création de la date du batch sous String
                    String stringDateBatch = DateUtils.getDateFutur(366, TestData.DATE_FORMAT_XML);
                    SimpleDateFormat formatter = new SimpleDateFormat(TestData.DATE_FORMAT_XML);
                    //changement de la date
                    RunBatch.runBatchChangeDate(stringDateBatch);
                    SeleniumUtils.waitForBatch();

                    //transformation de la date sous format Date
                    Date dateBatch = formatter.parse(stringDateBatch);
                    String quarter = "";
                    int month = dateBatch.getMonth() + 1;
                    switch (month){
                        case 1:
                        case 2:
                        case 3: quarter = "1";
                            break;
                        case 4:
                        case 5:
                        case 6: quarter = "2";
                            break;
                        case 7:
                        case 8:
                        case 9: quarter = "3";
                            break;
                        case 10:
                        case 11:
                        case 12: quarter = "4";
                            break;

                    }

                    //intégration de la personne
                    String periode = String.valueOf(DateUtils.getNextYearOfToday() + "/" + quarter);
                    Calendar calJour = Calendar.getInstance();
                    calJour = toCalendar(dateBatch);
                    DaoFunctionality.getNascaDao().deleteDateEnrolementPp(String.valueOf(DateUtils.getNextYearOfToday()), quarter);


                    switch (typeTest) {
                        case "PM ComptePos": //NASCARCVT 1996
                        case "PM CompteA0": //NASCARCVT 1996
                            DaoFunctionality.getNascaDao().initialisationDateEnrolementAnnuel(String.valueOf(DateUtils.getNextYearOfToday()), DateUtils.doFormat(TestData.DATE_FORMAT_XML_WITH_HOUR, calJour.getTime()));
                            doLaunchBatchPM(numero, calJour);
                            break;
                        default:
                            DaoFunctionality.getNascaDao().insertDateEnrolementPp(String.valueOf(DateUtils.getNextYearOfToday()), quarter, DateUtils.doFormat(TestData.DATE_FORMAT_XML_WITH_HOUR, calJour.getTime()));
                            doLaunchBatchPP(numero, calJour);
                            break;
                    }
                    //doLaunchBatchPP(numero, calJour);
                    SeleniumUtils.waitForBatch();


                    //SeleniumUtils.waitForAction(10000);


                    loginNasca();
                    loadNissOrBce(numero);
                    gestionClientPage.clickMenuCompteCotisantSoldes();

                    if (TableUtils.searchIntable(SignaletiquePage.tableEntiteComptable, "entitesComptablesTable" , 1, 8, 1, periode, cause) != null)
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussite Comptes soldes");
                    else
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension raté comptes soldes");


                    break;
                case "PM NoCodebit": //NASCARCVT 1956
                case "PP NoCodebit": //NASCARCVT 1956
                    gestionClientPage.clickMenuCompteCotisantSoldesCodebitOpen();
                    if (TableUtils.isElementPresent("entitesComptablesTable", cause))
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension ratée Comptes soldes codébiteurs");
                    else
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussite Comptes soldes codébiteurs");
                    break;
                case "PP AvecCodebit": //NASCARCVT 1959
                case "PM AvecCodebit": //NASCARCVT 1959
                    gestionClientPage.clickMenuCompteCotisantSoldesCodebitOpen();
                    int choice = 1;
                    int length = signaletiquePage.lengthCombobox("//div[2]/div/form/div[1]/div[1]/p/select/option");
                    boolean found = false;
                    while(choice < (length+1)){
                        if (TableUtils.isElementPresent("entitesComptablesTable", cause)) {
                            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussite Comptes soldes codébiteurs");
                            found = true;
                        }

                        choice++;

                    }
                    if (found)
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussite Comptes soldes codébiteurs");
                    else
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension ratée Comptes soldes codébiteurs");
                    break;

                case "PM ARevoir": //NASCARCVT 1970
                case "PP ARevoir": //NASCARCVT 1967


                    DateTime dateBatchAClotARevoir = DateUtils.doConvertStringtoDatTime(date, TestData.DATE_FORMAT_YEAR);
                    String stringDateBatchAClotARevoir = DateUtils.doFormat(TestData.DATE_FORMAT_XML, dateBatchAClotARevoir.toDate());
                    RunBatch.runBatchChangeDate(stringDateBatchAClotARevoir);
                    SeleniumUtils.waitForBatch();
                    loginNasca();
                    loadNissOrBce(numero);
                    gestionClientPage.clickMenuCompteCotisantSoldes();
                    if (TableUtils.isElementPresent("entitesComptablesTable", cause))
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension raté comptes soldes");
                    else
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussite Comptes soldes");
                    signaletiquePage.clickSuspension();
                    if (TableUtils.isElementPresent("tableDemandes", justification))
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension raté table suspension");
                    else
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussite table suspension");

                    signaletiquePage.clickTache();
                    signaletiquePage.clickBoutonReinitialiserTache();
                    if(TableUtils.clickTraiterInTable(signaletiquePage.tableTache,"table-taches", 2, "Création suspension manuelle", 12, "*[@id='treatTask']")) {
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Quitter réussi et tâche existante");
                        signaletiquePage.annulerSuspension();
                    }
                    else
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Tâche non existante");


                    break;

                case "PM ACloturer": //NASCARCVT 1969
                case "PP ACloturer": //NASCARCVT 1998


                    DateTime dateBatchAClot = DateUtils.doConvertStringtoDatTime(date, TestData.DATE_FORMAT_YEAR);
                    String stringdateBatchAClot = DateUtils.doFormat(TestData.DATE_FORMAT_XML, dateBatchAClot.plusDays(1).toDate());
                    RunBatch.runBatchChangeDate(stringdateBatchAClot);
                    SeleniumUtils.waitForBatch();
                    loginNasca();
                    loadNissOrBce(numero);
                    gestionClientPage.clickMenuCompteCotisantSoldes();
                    if (TableUtils.isElementPresent("entitesComptablesTable", cause))
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension raté comptes soldes");
                    else
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussite Comptes soldes");
                    signaletiquePage.clickSuspension();
                    if (TableUtils.isElementPresent("tableDemandes", justification))
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension raté table suspension");
                    else
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussite table suspension");


                    break;

                case "PP Quitter": //NASCARCVT 1972
                case "PM Quitter": //NASCARCVT 1972
                        signaletiquePage.quitterSuspension();
                        SeleniumUtils.waitForActionCommon();
                        signaletiquePage.clickTache();
                        signaletiquePage.clickBoutonReinitialiserTache();
                        if(TableUtils.clickTraiterInTable(signaletiquePage.tableTache,"table-taches", 2, "Création suspension manuelle", 12, "*[@id='treatTask']")) {
                            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Quitter réussi et tâche existante");
                            signaletiquePage.annulerSuspension();
                        }
                        else
                            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Tâche non existante");
                    break;


                case "PP Observation": //NASCARCVT 1973
                case "PM Observation": //NASCARCVT 1973
                    if(TableUtils.clickTraiterInTable(SignaletiquePage.tableSuspensionManuelle,"tableDemandes", 3, cause, 10, "a")) {
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussie");
                        if(TableUtils.isElementPresent("accordion", cause) && TableUtils.isElementPresent("accordion", justification) )
                            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "menu observation rempli");
                        else
                            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "menu non rempli");

                    }
                    else
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "suspension ratée");
                    break;
                case "PP LeverSuspension": //NASCARCVT 1976
                case "PM LeverSuspension": //NASCARCVT 1976
                    if(TableUtils.clickTraiterInTable(SignaletiquePage.tableSuspensionManuelle,"tableDemandes", 3, cause, 10, "a")) {
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussie");
                        if(TableUtils.isElementPresent("accordion", cause) && TableUtils.isElementPresent("accordion", justification) ) {
                            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "menu observation rempli");
                            signaletiquePage.clickLeverSuspension();
                            signaletiquePage.clickRetourLeverSuspension();
                            if(TableUtils.isElementPresent("tableDemandes", "Suspension levée"))
                                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension levée");
                            else
                                logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension non levée");

                        }


                        else
                            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "menu non rempli");

                    }
                    else
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "suspension ratée");
                    break;

                case "PP ECPartielle": //NASCARCVT 1977
                    if(TableUtils.clickTraiterInTable(SignaletiquePage.tableEntiteComptable,"entitesComptablesTable", 1, periodeEC, 10, "*[@id='iconGoOperation']")) {
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension trouvée");
                        if(TableUtils.clickTraiterInTable(SignaletiquePage.tableOperationComptable,"operationsComptablesTable", 7, "Recette", 11, "*[@id='iconAnnuler']")) {
                            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Recette annulée");
                            signaletiquePage.clickEnregistrerRecetteOperation();
                            signaletiquePage.clickRetourRecetteOperation();

                            if(TableUtils.isElementPresent("entitesComptablesTable", cause))
                                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension toujours présente");
                            else
                                logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension disparue");

                        }


                        else
                            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "recette non trouvée");

                    }
                    else
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "annulation recette échouée");
                    break;

                case "PM ECPartielle": //NASCARCVT 1977
                    if(TableUtils.clickTraiterInTable(SignaletiquePage.tableEntiteComptable,"entitesComptablesTable", 8, cause, 10, "*[@id='iconGoOperation']")) {
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension trouvée");
                        if(TableUtils.clickTraiterInTable(SignaletiquePage.tableOperationComptable,"operationsComptablesTable", 7, "Recette", 11, "*[@id='iconAnnuler']")) {
                            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Recette annulée");
                            signaletiquePage.clickEnregistrerRecetteOperation();
                            signaletiquePage.clickRetourRecetteOperation();

                            if(TableUtils.isElementPresent("entitesComptablesTable", cause))
                                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension toujours présente");
                            else
                                logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension disparue");

                        }


                        else
                            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "recette non trouvée");

                    }
                    else
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "annulation recette échouée");
                    break;


                case "PP MultCot": //NASCARCVT 1978
                    fillSuspensionRecouvrement(numero, "PP MultCot2", cause, application, justification, aRevoir, date, suspensionFuture, type, debPeriode, finPeriode, soldes,null);
                    checkExistenceSuspension(numero, "PP MultCot2", cause, application, justification, aRevoir, date, suspensionFuture, type, debPeriode, finPeriode, soldes, null);
                    break;

                case "PM MultCot": //NASCARCVT 1978
                    fillSuspensionRecouvrement(numero, "PM MultCot2", cause, application, justification, aRevoir, date, suspensionFuture, type, debPeriode, finPeriode, soldes, null);
                    checkExistenceSuspension(numero, "PM MultCot2", cause, application, justification, aRevoir, date, suspensionFuture, type, debPeriode, finPeriode, soldes, null);

                    break;

                case "PP MultCotMultCheck": //NASCARCVT 1986
                    fillSuspensionRecouvrement(numero, "PP MultCot2", cause, application, justification, aRevoir, date, suspensionFuture, type, debPeriode, finPeriode, soldes, null);
                    checkExistenceSuspension(numero, "PP MultCot2", "MULTIPLE", application, justification, aRevoir, date, suspensionFuture, type, debPeriode, finPeriode, soldes, null);
                    break;

                case "PM MultCotMultCheck": //NASCARCVT 1986
                    fillSuspensionRecouvrement(numero, "PM MultCotMultCheck2", cause, application, justification, aRevoir, date, suspensionFuture, type, debPeriode, finPeriode, soldes, null);
                    checkExistenceSuspension(numero, "PM MultCotMultCheck2", "MULTIPLE", application, justification, aRevoir, date, suspensionFuture, type, debPeriode, finPeriode, soldes, null);

                    break;

                case "PP ModifSuspension": //NASCARCVT 1982
                case "PM ModifSuspension": //NASCARCVT 1982

                    if(TableUtils.clickTraiterInTable(SignaletiquePage.tableSuspensionManuelle,"tableDemandes", 3, cause, 10, "a")) {
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussie");
                        if(TableUtils.isElementPresent("accordion", cause) && TableUtils.isElementPresent("accordion", justification) ) {
                            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "menu observation rempli");
                            signaletiquePage.clickModifierSuspension();
                            signaletiquePage.modifierJustificationSuspension("modif");
                            signaletiquePage.clickEnregistrerRecetteOperation();
                            if(TableUtils.isElementPresent("accordion", cause) && TableUtils.isElementPresent("accordion", "modif") )
                                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension modifiée");
                            else
                                logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension inchangée");

                        }


                        else
                            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "menu non rempli");

                    }
                    else
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "suspension ratée");
                    break;

                case "PP MultCotMultCheckAuto": //NASCARCVT 1986
                case "PM MultCotMultCheckAuto": //NASCARCVT 1986
                    checkExistenceSuspension(numero, "PP MultCotMultAuto", "MULTIPLE", application, justification, aRevoir, date, suspensionFuture, type, debPeriode, finPeriode, soldes, null);
                break;

                case "PP FutCotConcerne": //NASCARCVT 1995
                    Calendar calJourCotConcerne = toCalendar(new Date());
                    //DaoFunctionality.getNascaDao().insertDateEnrolementPp(DateUtils.getYearNow(), "3", DateUtils.doFormat(TestData.DATE_FORMAT_XML_WITH_HOUR, calJourCotConcerne.getTime()));
                    DaoFunctionality.getNascaDao().insertDateEnrolementPp("2017", "3", DateUtils.doFormat(TestData.DATE_FORMAT_XML_WITH_HOUR, calJourCotConcerne.getTime()));
                    doLaunchBatchPP(numero, calJourCotConcerne);
                    SeleniumUtils.waitForBatch();


                    //SeleniumUtils.waitForAction(10000);


                    loginNasca();
                    loadNissOrBce(numero);
                    gestionClientPage.clickMenuCompteCotisantSoldes();

                    if (TableUtils.searchIntable(SignaletiquePage.tableEntiteComptable, "entitesComptablesTable" , 1, 8, 1, "2017/3", "") != null)
                        logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension réussite Comptes soldes");
                    else
                        logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Suspension raté comptes soldes");


                    break;
            }

        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + numero, e);
        }
    }


    //Fonction créée pour vérifier le statut de la checkbox "all" du tableau des sélection des EC
    static void checkStateBox(){
        Boolean found = TableUtils.stateboxsingleBoxInTable(SignaletiquePage.tableEntiteComptable, "entitesComptablesTable");
        if(found)
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "toutes cases cochées");
        else
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "toutes cases non cochées");

    }


    //Copie bête et méchante d'une fonction déjà existente de Gyuseppe.
    // Je n'ai pas très bien compris sont fonctionnement
    static void doLaunchBatchPP(String niss, Calendar calJour) {
        loginNasca();
        if (niss != null) {
            EnrolementPPTestBase.doEnrolementPPPrepare(niss, DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, calJour.getTime()));
        }
        loginNasca();
        // Enrolement
        EnrolementPPTestBase.doEnrolementPPAutomatique();


    }

    static void doLaunchBatchPM(String BCE, Calendar calJour) {
        loginNasca();
        if (BCE != null) {
            EnrolementPMTestBase.doEnrolementPM(BCE, String.valueOf(calJour.YEAR), DateUtils.doFormat(TestData.DATE_FORMAT_XML, calJour.getTime()), TestData.MONTANT_BILAN_PM, TestData.MONTANT_COTISATION_PM);
            //EnrolementPMTestBase.doEnrolementPPPrepare(BCE, DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, calJour.getTime()));
        }
        loginNasca();
        // Enrolement
        EnrolementPPTestBase.doEnrolementPPAutomatique();


    }

    //Reçois une date au format Date et la renvoie sous forme Calendar.
    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }






}