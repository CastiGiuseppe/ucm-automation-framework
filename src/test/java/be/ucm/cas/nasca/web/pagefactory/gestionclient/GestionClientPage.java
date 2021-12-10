package be.ucm.cas.nasca.web.pagefactory.gestionclient;

import be.ucm.cas.nasca.web.test.support.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GestionClientPage {

    private static final String TABLE_REVENUS_WIZARD_ID = "table-revenus-wizard";
    private static final String TABLE_REVENUS_ID = "table-revenus";
    private static final String TABLE_PROFIL_EVENEMENTS_ID = "profilEvenementsTableId";
    private static final String TABLE_OBSERVATIONS_ID = "table-observations";
    private static final String TABLE_OBSERVATIONS_ARCHIVEES_ID = "table-archives";
    private static final String TABLE_MAJORATIONS_PP_ID = "tableSelectedMajoPp";
    private static final String TABLE_MAJORATIONS_PM_ID = "tableSelectedMajoPm";
    private static final String DATE_DEMANDE_REM_ID = "dateDemandeRem";
    private static final String DATE_CESSATION_REM_ID = "dateCessationRem";
    private static final String OPERATIONS_COMPTABLES_TABLE_ID = "operationsComptablesTable";
    private static final String PROFIL_DATE_VALIDITE_ID = "profilDateValiditeId";
    private static final String DATE_DEMANDE_ID = "dateDemande";
    private static final String DATE_REELLE_CESSATION_ID = "dateReelleCessation";
    private static final String ENTITES_COMPTABLES_TABLE_ID = "entitesComptablesTable";
    private static final String TABLE_LEVEE_MAJORATION_ID = "leveeMajorationTableId";
    private static final String TABLE_CONTRATS_PLC_ID = "tableContratsPlcId";
    private static final String TABLE_ENTITE_COMPTABLES_PRESCRITES_ID = "entiteComptablesPrescritesTable";
    private static final String ACTES_TABLE_ID = "actesTable";
    private static final String DEMANDE_REFRESH_TABLE_ID = "consultDemandeRefreshTableId";
    private static final String TABLE_CARRIERE_PENSION_ID = "carPensionTable";
    private static final String HISTORIQUE_PLAN_APUREMENT_ID = "planApurementHistoriquesTableId";

    private final WebDriver driver;

    @FindBy(id = "ctxLink")
    private WebElement lienCtx;

    @FindBy(id = "irrecLink")
    private WebElement lienIrrec;

    @FindBy(id = "codebLink")
    private WebElement lienCodeb;

    @FindBy(id = "dispenseLink")
    private WebElement lienDispense;

    @FindBy(id = "prescrLink")
    private WebElement lienPrescr;

    @FindBy(id = "solidaireLink")
    private WebElement lienSolidaire;

    @FindBy(id = "remLink")
    private WebElement lienREM;

    @FindBy(id = "plcLink")
    private WebElement lienPlc;

    @FindBy(id = "planFamilleLink")
    private WebElement lienPlanFamille;

    @FindBy(id = "passLink")
    private WebElement lienPass;

    @FindBy(id = "matLink")
    private WebElement lienMat;

    @FindBy(id = "planApurementLink")
    private WebElement lienPA;

    @FindBy(xpath = "//*[contains(@title, 'Réinitialiser la configuration du menu')]")
    private WebElement btnResetMenu;

    @FindBy(xpath = "//*[@id='tabsSyn-0']/div[1]/div[2]/div[2]/a[2]")
    private WebElement lienSociete;

    @FindBy(xpath = "//*[@id='tabsSyn-0']/div[1]/div[2]/div[2]/a[3]")
    private WebElement lienConjoint;

    /**
     * Criteres de recherches
     */
    @FindBy(id = "typeIdentitePP")
    private static WebElement btnselectpersonnephysique;

    @FindBy(id = "typeIdentitePM")
    private static WebElement btnselectpersonnemoral;

    @FindBy(id = "searchBtn")
    private static WebElement btnsearch;

    @FindBy(id = "resetBtn")
    private static WebElement btnreset;

    /**
     * Fenetre Gestion Client apres recherche
     */
    @FindBy(xpath = "//a[@class='tooltipDcd nasca_link']")
    private static WebElement iconedecede;

    @FindBy(id = "CPTDiv")
    private static WebElement menuCompte;

    @FindBy(id = "CPT_COTDiv")
    private static WebElement menuCompteCotisant;

    @FindBy(id = "CPT_COT_SOLDiv")
    private static WebElement menuCompteCotisantSoldes;

    @FindBy(id = "CPT_COT_SCODiv")
    private static WebElement menuCompteCotisantSoldesCodebit;

    @FindBy(id = "CPT_COT_OPEDiv")
    private static WebElement menuCompteCotisantOperations;

    @FindBy(id = "CPT_PLCDiv")
    private static WebElement menuComptePLC;

    @FindBy(id = "CPT_PLC_SOLDiv")
    private static WebElement menuComptePLCSoldes;

    @FindBy(id = "CPT_PLC_OPEDiv")
    private static WebElement menuComptePLCOperations;

    @FindBy(xpath = ("//div[2]/div/div/div/ul/li[2]/a"))
    private static WebElement ongletDemandeRefresh;

    @FindBy(id = "COTDiv")
    private static WebElement menuCotisant;

    @FindBy(id = "COT_REVDiv")
    private static WebElement menuCotisantRevenus;

    @FindBy(id = "COT_REV_GESDiv")
    private static WebElement menuCotisantRevenusGestion;

    @FindBy(id = "COT_PRO_CHGDiv")
    private static WebElement menuCotisantProfilChangementProfil;

    @FindBy(xpath = "//div[1]/div[3]/h3[1]/a/img[3]")
    private static WebElement raccourciResumeChangementProfil;

    @FindBy(id = "COT_PRO_PENDiv")
    private static WebElement menuCotisantProfilDonneesPension;

    @FindBy(id = "COT_PRODiv")
    private static WebElement menuCotisantProfil;

    @FindBy(id = "COT_PRO_AFFDiv")
    private static WebElement menuCotisantProfilAffiliation;

    @FindBy(id = "COT_PRO_CLOTDiv")
    private static WebElement menuCotisantProfilCloture;

    @FindBy(id = "COT_PRO_CLOT_CESDiv")
    private static WebElement menuCotisantProfilClotureCessation;

    @FindBy(id = "COT_PRO_CLOT_DECDiv")
    private static WebElement menuCotisantProfilClotureDeces;

    @FindBy(id = "COT_PRO_CLOT_NASDiv")
    private static WebElement menuCotisantProfilClotureNonAssujettissement;

    @FindBy(id = "COT_PRO_CLOT_MGRDiv")
    private static WebElement menuCotisantProfilClotureMandatGratuit;

    @FindBy(id = "COT_PRO_CLOT_AMADiv")
    private static WebElement menuCotisantProfilClotureAssimilationMaladie;

    @FindBy(id = "COT_PRO_CLOT_ASCDiv")
    private static WebElement menuCotisantProfilClotureAssuranceContinuee;

    @FindBy(id = "COT_CODDiv")
    private static WebElement menuCotisantCodebiteurs;

    @FindBy(id = "COT_PRO_MEVDiv")
    private static WebElement menuCotisantProfilMiseEnVeilleuse;

    @FindBy(id = "CARDiv")
    private static WebElement menuCarriere;

    @FindBy(id = "lvl_1")
    private static WebElement menuActionsControleSpf;

    @FindBy(id="lvl_ct_1")
    private static WebElement menuActionsMajControleSpf;

    @FindBy(id = "CAR_CONDiv")
    private static WebElement menuConsultationCarriere;

    @FindBy(id = "CAR_GESDiv")
    private static WebElement menuGestionCarriere;

    @FindBy(id = "CAR_SPFDiv")
    private static WebElement menuControleSpfCarriere;

    @FindBy(id = "lvl_11")
    private static WebElement menuEncoderControleSpf;

    @FindBy(id="lvl_ct_11")
    private static WebElement menuMajSelTrimControleSpf;

    @FindBy(id = "RECDiv")
    private static WebElement menuRecouvrement;

    @FindBy(id = "REC_DPRDiv")
    private static WebElement menuRecouvrementDebiteursPrincipaux;

    @FindBy(id = "REC_ACCDiv")
    private static WebElement menuRecouvrementAccompagnement;

    @FindBy(id = "REC_ACC_PAPDiv")
    private static WebElement menuRecouvrementAccompagnementPlanApurement;

    @FindBy(id = "REC_ACC_EXODiv")
    private static WebElement menuRecouvrementAccompagnementExoneration;

    @FindBy(id = "REC_ACC_LMADiv")
    private static WebElement menuRecouvrementAccompagnementLeveeMajorations;

    @FindBy(id = "REC_SUSDiv")
    static WebElement menuRecouvrementSuspensionRecouvrement;

    @FindBy(id = "REC_RECDiv")
    private static WebElement menuRecouvrementRecouvrement;

    @FindBy(id = "REC_REC_RAPDiv")
    private static WebElement menuRecouvrementRecouvrementRappel;

    @FindBy(id = "REC_REC_MED")
    private static WebElement menuRecouvrementRecouvrementMiseEnDemeure;

    @FindBy(id = "REC_IRRDiv")
    private static WebElement menuRecouvrementIrrecouvrable;

    @FindBy(id = "REC_IRR_GESDiv")
    private static WebElement menuRecouvrementIrrecouvrableGestion;

    @FindBy(id = "REC_IRR_SUIDiv")
    private static WebElement menuRecouvrementIrrecouvrableSuivi;

    @FindBy(id = "REC_PREDiv")
    private static WebElement menuRecouvrementPrescription;

    @FindBy(id = "REC_PRE_GESDiv")
    private static WebElement menuRecouvrementPrescriptionGestion;

    @FindBy(id = "REC_PRE_SUIDiv")
    private static WebElement menuRecouvrementPrescriptionSuivi;

    @FindBy(id = "REC_PRE_ACTDiv")
    private static WebElement menuRecouvrementPrescriptionActesInterruptifs;

    @FindBy(id = "PLCDiv")
    private static WebElement menuPLC;

    @FindBy(id = "REMDiv")
    private static WebElement menuREM;

    @FindBy(id = "OBSDiv")
    private static WebElement menuObservations;

    /**
     * PLC
     */
    @FindBy(id = "lvl_1")
    private static WebElement menuActionsPLC;

    @FindBy(id = "lvl_newPlc")
    private static WebElement menuNouveauContratPLC;

    @FindBy(xpath = "//div[5]/div/ul/li/a")
    private static WebElement menuNouveauContratDisabledPLC;

    @FindBy(id = "lvl_11")
    private static WebElement menuSuspendreContratPLC;

    @FindBy(id = "lvl_12")
    private static WebElement menuReveillerContratPLC;

    @FindBy(id = "lvl_13")
    private static WebElement menuCloturerContratPLC;

    @FindBy(id = "lvl_14")
    private static WebElement menuAjoutObservationContratPLC;

    @FindBy(id = "lvl_15")
    private static WebElement menuModifierDonneesContratPLC;

    @FindBy(xpath = "//*[@class='warningMessage']")
    private static List<WebElement> messageInformatifContratPLC;

    @FindBy(id = "_recuPar")
    private static WebElement selectDemandeRecueParContratPLC;

    @FindBy(id = "_origineDemandeId")
    private static WebElement selectOrigineDemandeContratPLC;

    @FindBy(id = "_wizardCreateValidationContratFormObjet_model_sousOrigineDemandeTiers")
    private static WebElement selectOrigineDemandeTiersContratPLC;

    @FindBy(id = "_wizardCreateValidationContratFormObjet_model_sousOrigineDemandeOrganisme")
    private static WebElement selectOrigineDemandeOrganismeContratPLC;

    @FindBy(id = "numeroContratId")
    private static WebElement numeroContratPLC;

    @FindBy(id = "numeroVcsId")
    private static WebElement numeroVCSContratPLC;

    @FindBy(id = "numeroContratId")
    private static WebElement numeroModifContratPLC;

    @FindBy(id = "numeroVcsId")
    private static WebElement numeroVCSModifContratPLC;

    @FindBy(id = "_wizardCreateValidationContratFormObjet_model_natureConvention")
    private static WebElement selectNatureConventionContratPLC;

    @FindBy(id = "_wizardCreateValidationContratFormObjet_model_typeInvestissement")
    private static WebElement selectTypeInvestissementContratPLC;

    @FindBy(id = "_typeDecisionsId")
    private static WebElement selectDecisionContratPLC;

    @FindBy(id = "btnCalculerId")
    private static WebElement btnCalculerContratPLC;

    @FindBy(xpath = "//*[@class='errorMessage']")
    private static WebElement messageErreurContratPLC;

    @FindBy(id = "_wizardCreateValidationContratFormDecision_model_motifRefus")
    private static WebElement selectMotifRefusContratPLC;

    @FindBy(id = "_motifSuspensionContratId")
    private static WebElement selectMotifSuspensionOuClotureOuReveilContratPLC;

    @FindBy(xpath = "//div[2]/p[3]/input[5]")
    private static WebElement checkTrimestre1AEnrolerContratPLC;

    @FindBy(xpath = "//div[2]/p[3]/input[6]")
    private static WebElement checkTrimestre2AEnrolerContratPLC;

    @FindBy(xpath = "//div[2]/p[3]/input[7]")
    private static WebElement checkTrimestre3AEnrolerContratPLC;

    @FindBy(xpath = "//div[2]/p[3]/input[8]")
    private static WebElement checkTrimestre4AEnrolerContratPLC;

    @FindBy(xpath = "//div[6]/div[3]/div/button[2]")
    private static WebElement btnEnregistrerSuspensionOuClotureOuReveilContratPLC;

    @FindBy(id = "addObservationForm_observation_reference")
    private static WebElement referenceObservationContratPLC;

    @FindBy(id = "_obsTypeEntiteListId")
    private static WebElement selectTypeObservationContratPLC;

    @FindBy(id = "commentaireId")
    private static WebElement commentaireObservationContratPLC;

    @FindBy(id = "_obsTypeMediaListId")
    private static WebElement selectMediaObservationContratPLC;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerObservationContratPLC;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerModifierDonneesContratPLC;

    @FindBy(id = "_wizardCreateValidationContratFormDecision_model_natureProfil")
    private static WebElement selectProfilPrescripteurContratPLC;

    @FindBy(id = "exoArtId")
    private static WebElement checkExonerationContratPLC;

    @FindBy(id = "reducArtId")
    private static WebElement checkReductionContratPLC;

    @FindBy(id = "_wizardCreateValidationContratFormDecision_model_profilSouscripteur")
    private static WebElement selectRegimePrescripteurContratPLC;

    @FindBy(id = "pensionSurvieId")
    private static WebElement checkPensionSurvieContratPLC;

    @FindBy(id = "revenusSouscripteurId")
    private static WebElement revenuSouscripteurContratPLC;

    @FindBy(id = "anneeId")
    private static WebElement anneeRevenuSouscripteurContratPLC;

    /**
     * Gestion Profil --Actions --
     */
    @FindBy(id = "lvl_1")
    private static WebElement menuActionGestionProfil;

    @FindBy(xpath = ("//a[contains(text(),'Exonération')]"))
    private static WebElement menuActionExoneration;

    @FindBy(xpath = ("//a[contains(text(),'Exo-réduction art 37')]"))
    private static WebElement menuActionExoReductionArt37;

    @FindBy(xpath = ("//a[contains(text(),'Changement simple')]"))
    private static WebElement menuActionChangementdeNature;

    @FindBy(xpath = ("//a[contains(text(),'Changement multiple')]"))
    private static WebElement menuActionChangementMultiple;

    @FindBy(xpath = ("//a[contains(text(),'Principal')]"))
    private static WebElement menuActionNaturePrincipal;

    @FindBy(xpath = ("//a[contains(text(),'Complémentaire')]"))
    private static WebElement menuActionNatureComplementaire;

    @FindBy(xpath = ("//a[contains(text(),'Maxi-statut')]"))
    private static WebElement menuActionNatureMaxiStatut;

    @FindBy(xpath = ("//a[contains(text(),'Mini-statut')]"))
    private static WebElement menuActionNatureMiniStatut;

    /**
     * Gestion Profil --wizard --
     */
    @FindBy(id = "boutonCreationEvenement")
    private static WebElement btnAjouterEvenement;

    @FindBy(id = PROFIL_DATE_VALIDITE_ID)
    private static WebElement dateProfilEvenement;

    @FindBy(id = "_typeEvenementID")
    private static WebElement selectTypeEvenement;

    @FindBy(id = "_profilTypeRaisonSuppressionId")
    private static WebElement selectRaisonEvenementSuppression;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnOuiSupprimerEvenement;

    @FindBy(xpath = "//div[8]/div[3]/div/button[2]")
    private static WebElement btnEnregistrerProfil;

    @FindBy(xpath = "//div[9]/div[3]/div/button[2]")
    private static WebElement btnEnregistrerProfil2;

    @FindBy(xpath = "//div[6]/div[3]/div/button[2]")
    private static WebElement btnEnregistrerProfil3;

    @FindBy(id = "_natureCotisantesId")
    private static WebElement naturecotisanteinput;

    @FindBy(id = "_caisseDestinationId")
    private static WebElement caisseDestination;

    @FindBy(id = "_caisseOrigineId")
    private static WebElement caisseOrigine;

    @FindBy(id = "ajouterRevenuId")
    private static WebElement btnopenAjoutRevenu;

    @FindBy(id = "_printTypeCommunicationId")
    private static WebElement selectTypeCommunication;

    @FindBy(id = "_printTypeSousFormeId")
    private static WebElement selectSousForme;

    @FindBy(id = "_printTypeFileDeSortieId")
    private static WebElement selectFileSortie;

    @FindBy(id = "radioNo")
    private static WebElement btnRadioCourrierProfilChange;

    @FindBy(id = "_profilExoReductionId")
    private static WebElement selectExoReduction;

    @FindBy(xpath = "//*[@id='buttonEmpty_profilExoReductionId']/span[1]")
    private static WebElement selectEmptyExoReduction;

    @FindBy(id = "_profilJustificationId")
    private static WebElement selectJustification;

    @FindBy(xpath = "//*[@id='buttonEmpty_profilJustificationId']/span[1]")
    private static WebElement selectEmptyJustification;

    @FindBy(id = "_natureCotisantesId")
    private static WebElement selectNatureEvenement;

    /**
     * Profil Détail évènement
     */
    @FindBy(xpath = "//*[@id='detail']/div[4]/div[1]/p/span")
    private static WebElement libelleNatureProfil;

    @FindBy(xpath = "//*[@id='detail']/div[3]/div[2]/p/span")
    private static WebElement libelleSuperNatureProfil;

    @FindBy(xpath = "//*[@id='detail']/div[2]/div[2]/p/span")
    private static WebElement libelleLimiteArt11Profil;

    /**
     * Données pension
     */
    @FindBy(id = "lvl_2")
    private static WebElement menuDonneesPensionActions;

    @FindBy(id = "lvl_21")
    private static WebElement menuAjouterPension;

    @FindBy(id = "_naturePension")
    private static WebElement naturePension;

    @FindBy(id = "_regimePension")
    private static WebElement regimePension;

    @FindBy(id = "_infoCarriere")
    private static WebElement infoCarrierePension;

    @FindBy(id = "_detenteurPension")
    private static WebElement detenteurPension;

    @FindBy(id = "_enfantACharge")
    private static WebElement enfantAChargePension;

    /**
     * Revenu
     */
    @FindBy(id = "lvl_2")
    private static WebElement menuRevenuActions;

    @FindBy(id = "lvl_31")
    private static WebElement menuAjouterModifierRevenu;

    @FindBy(id = OPERATIONS_COMPTABLES_TABLE_ID)
    private static WebElement tableOperationComptable;

    @FindBy(id = "_annee")
    private static WebElement anneeRevenu;

    @FindBy(id = "montant")
    private static WebElement revenu;

    @FindBy(id = "_type")
    private static WebElement selectTypeRevenu;

    @FindBy(id = "radioDeclare")
    private static WebElement checkOrigineDeclare;

    @FindBy(id = "_statutFull")
    private static WebElement selectStatutFullRevenu;

    @FindBy(id = "buttonEmpty_statutFull")
    private static WebElement clearStatutFullRevenu;

    @FindBy(id = "_sourceFull")
    private static WebElement selectSourceFullRevenu;

    @FindBy(id = "revTardif")
    private static WebElement checkBoxRevenuAUtiliser;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerModalRevenu;

    @FindBy(id = "dateCommunication")
    private static WebElement datecommunicationRevenu;

    @FindBy(xpath = "//div[9]/div[3]/div/button[2]")
    private static WebElement btnenregistrerrevenufromprofil;

    @FindBy(id = "btnAddRevenu")
    private static WebElement btnAddRevenu;

    @FindBy(id = "trim1")
    private static WebElement checkBoxTrim1;

    @FindBy(id = "trim2")
    private static WebElement checkBoxTrim2;

    @FindBy(id = "trim3")
    private static WebElement checkBoxTrim3;

    @FindBy(id = "sortirSansRegul")
    private static WebElement checkSortirSansRegul;

    @FindBy(id = "radioRetour")
    private static WebElement btnRadioRetourRevenu;

    @FindBy(xpath = "//div[12]/div[3]/div/button[2]")
    private static WebElement btnOuiModalRevenu1;

    @FindBy(xpath = "//div[11]/div[3]/div/button[2]")
    private static WebElement btnOuiModalRevenu2;

    /**
     * Codebiteurs --Actions --Resultgrid
     */
    @FindBy(id = "lvl_2")
    private static WebElement menuActions;

    @FindBy(id = "envoiCourrierId")
    private static WebElement menuEnvoyerCourrier;

    @FindBy(id = "modifPeriodeId")
    private static WebElement menuModifPeriodeSelectionnee;

    @FindBy(id = "ajouterCodebiteurId")
    private static WebElement menuAjoutCodebiteur;

    @FindBy(id = "filtreSolidariteTout")
    private static WebElement checkToutesPeriodesCodebiteurs;

    @FindBy(id = "filtreSolidariteSolidaire")
    private static WebElement checkToutesPeriodesSolidairesCodebiteurs;

    @FindBy(id = "filtreSolidariteNonSolidaire")
    private static WebElement checkToutesPeriodesNonSolidairesCodebiteurs;

    /**
     * Codebiteurs --AJOUT CODEBITEURS SOLIDAIRES WIZARD
     */
    @FindBy(id = "_typeIdentiteListId")
    private static WebElement filtreTypeIdentite;

    @FindBy(id = "ilFilNomId")
    private static WebElement nomidentiteliee;

    @FindBy(id = "ilFilPrenomId")
    private static WebElement prenomidentiteliee;

    @FindBy(id = "ilFilDenomId")
    private static WebElement denomidentiteliee;

    @FindBy(id = "_ilFilFoJuId")
    private static WebElement formeJuridiqueIdentiteliee;

    @FindBy(xpath = "//form/div[1]/div/ul/li[2]/a")
    private static WebElement ongletAdresse;

    @FindBy(id = "btnAddAdresse")
    private static WebElement btnAjoutAdresse;

    @FindBy(id = "_localiteId")
    private static WebElement localiteAdresse;

    @FindBy(id = "_typeAdresseId2")
    private static WebElement typeAdresse;

    @FindBy(id = "_codeRueAdrId2")
    private static WebElement codeRueAdresse;

    @FindBy(id = "rueAdrId2")
    private static WebElement rueAdresse;

    @FindBy(id = "numeroAdrId2")
    private static WebElement numeroAdresse;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerAdresseCodebiteur;

    @FindBy(xpath = ("//a[@id='buttonSelect_ilFilSexeId']"))
    private static WebElement btnselectsex;

    @FindBy(xpath = ("//a[contains(text(),'Masculin')]"))
    private static WebElement sexmasculin;

    @FindBy(id = "btnCreate")
    private static WebElement btncreation;

    @FindBy(id = "btnSearch")
    private static WebElement btnsearchaddcodebiteur;

    @FindBy(id = "solidaritPeriodeAddId")
    private static WebElement btnaddperiodsolidarite;

    @FindBy(id = "isSolidaireId")
    private static WebElement periodeSolidarite;

    @FindBy(id = "_raisonNonSolidariteId")
    private static WebElement selectRaisonNonSolidarite;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnenregistrercodebiteurwizard;

    @FindBy(id = "debiteurCreationPeriode")
    private static WebElement checkDebiteurCreationPeriode;

    @FindBy(id = "codebiteurCreationPeriode")
    private static WebElement checkCodebiteurCreationPeriode;

    @FindBy(id = "_printTypeCommunicationId")
    private static WebElement listSuiteA;

    /**
     * Clôture dossier
     */
    @FindBy(id = "lvl_1")
    private static WebElement btnMenuNouveau;

    @FindBy(id = "lvl_1")
    private static WebElement btnMenuActions;

    /**
     * Clôture dossier - Cessation
     */
    @FindBy(id = "lvl_10")
    private static WebElement btnCessationAvecREM;

    @FindBy(id = "lvl_11")
    private static WebElement btnCessationSansREM;

    @FindBy(id = "lvl_12")
    private static WebElement btnCessationModifier;

    @FindBy(id = "lvl_13")
    private static WebElement btnCessationAnnuler;

    @FindBy(id = "lvl_14")
    private static WebElement btnCessationAjouterObservation;

    @FindBy(id = "_origineDemandeId")
    private static WebElement selectOrigineREMCessation;

    @FindBy(id = "_wizardCreateCessationFormRem_model_recuParRem")
    private static WebElement selectRecuParREMCessation;

    @FindBy(id = "_origineDemandeId")
    private static WebElement selectOrigineCessation;

    @FindBy(id = "_recuPar")
    private static WebElement selectRecuParCessation;

    @FindBy(id = DATE_REELLE_CESSATION_ID)
    private static WebElement dateCessationCessation;

    @FindBy(xpath = "//*[@id='accordionObjet']/div[2]/div/div[1]/p[1]/input")
    private static WebElement checkDeclarationHonneurCessation;

    @FindBy(xpath = "//*[@id='accordionObjet']/div[2]/div/div[1]/p[2]/input")
    private static WebElement checkDirigeantEntrepriseCessation;

    @FindBy(xpath = "//*[@id='accordionObjet']/div[2]/div/div[2]/p[1]/input")
    private static WebElement checkDroitsSociauxCessation;

    @FindBy(xpath = "//*[@id='accordionObjet']/div[2]/div/div[2]/p[2]/input")
    private static WebElement checkCessationConjointeCessation;

    @FindBy(id = "wizardCreateCessationFormObjet_model_annulationPension")
    private static WebElement checkAnnulationTrimestrePensionCessation;

    @FindBy(id = "wizardCreateCessationFormObjet_model_editionLettre")
    private static WebElement checkCourrierCessation;

    @FindBy(id = "addObservationForm_observation_reference")
    private static WebElement referenceObservationCessation;

    @FindBy(id = "addObservationForm_observation_commentaire")
    private static WebElement commentaireObservationCessation;

    @FindBy(id = "_obsTypeMediaList")
    private static WebElement selectTypeMediaObservationCessation;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerObservationCessation;

    @FindBy(id = "_origineDemandeId")
    private static WebElement selectOrigineModificationCessation;

    @FindBy(id = "_recuPar")
    private static WebElement selectRecuParModificationCessation;

    @FindBy(id = "wizardCancelCessationFormObjet_model_commentaire")
    private static WebElement commentaireAnnulationCessation;

    /**
     * Clôture dossier - Succession
     */
    @FindBy(id = "lvl_10")
    private static WebElement btnSuccessionIntroduireDeces;

    @FindBy(id = "lvl_13")
    private static WebElement btnSuccessionAjouterObservation;

    @FindBy(id = "radioUseDateDecesSignaletique")
    private static WebElement btnRadioDateDecesSignaletiqueSuccession;

    @FindBy(id = "radioUseDateDecesAutre")
    private static WebElement btnRadioDateDecesAutreSuccession;

    @FindBy(id = "dateDecesAutre")
    private static WebElement dateDecesAutreSuccession;

    @FindBy(id = "observationCommentaireId")
    private static WebElement commentaireObservationSuccession;

    @FindBy(id = "_observationTypeMediaId")
    private static WebElement selectTypeMediaObservationSuccession;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerObservationSuccession;

    /**
     * Clôture dossier - Non-assujettissement
     */
    @FindBy(id = "lvl_10")
    private static WebElement btnNonAssujettissementAvecREM;

    @FindBy(id = "lvl_11")
    private static WebElement btnNonAssujettissementSansREM;

    @FindBy(id = "lvl_14")
    private static WebElement btnNonAssujettissementAjouterObservation;

    @FindBy(xpath = "//*[@id='profilEvenementsTableId']/tbody/tr/td[1]")
    private static WebElement dateDebutActiviteProfil;

    @FindBy(id = "_origineDemandeId")
    private static WebElement selectOrigineREMNonAssujettissement;

    @FindBy(id = "_wizardCreateNonAssujettissementFormRem_model_recuParRem")
    private static WebElement selectRecuParREMNonAssujettissement;

    @FindBy(id = "dateAffiASupprimerRem")
    private static WebElement dateAffiliationASupprimerREMNonAssujettissement;

    @FindBy(id = "_origineDemandeId")
    private static WebElement selectOrigineNonAssujettissement;

    @FindBy(id = "_recuPar")
    private static WebElement selectRecuParNonAssujettissement;

    @FindBy(id = "dateAffiASupprimer")
    private static WebElement dateAffiliationASupprimerNonAssujettissement;

    @FindBy(xpath = "//*[@id='accordionObjet']/div[3]/div/div[1]/p/input[1]")
    private static WebElement checkPasAffiliationFictiveNonAssujettissement;

    @FindBy(xpath = "//*[@id='accordionObjet']/div[3]/div/div[2]/p/input[1]")
    private static WebElement checkRadiationApresAffiliationNonAssujettissement;

    @FindBy(xpath = "//*[@id='accordionObjet']/div[3]/div/div[3]/p/input[1]")
    private static WebElement checkNonRenvoiFormaulationAffiliationNonAssujettissement;

    @FindBy(id = "wizardCreateNonAssujettissementFormObjet_model_editionLettre")
    private static WebElement checkCourrierNonAssujettissement;

    @FindBy(id = "addObservationForm_observation_reference")
    private static WebElement referenceObservationNonAssujettissement;

    @FindBy(id = "addObservationForm_observation_commentaire")
    private static WebElement commentaireObservationNonAssujettissement;

    @FindBy(id = "_obsTypeMediaList")
    private static WebElement selectTypeMediaObservationNonAssujettissement;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerObservationNonAssujettissement;

    /**
     * Clôture dossier - Mandat gratuit
     */
    @FindBy(id = "lvl_10")
    private static WebElement btnMandatGratuitAvecREM;

    @FindBy(id = "lvl_11")
    private static WebElement btnMandatGratuitAvecCessation;

    @FindBy(id = "lvl_14")
    private static WebElement btnMandatGratuitAjouterObservation;

    @FindBy(id = "_origineDemandeId")
    private static WebElement selectOrigineREMMandatGratuit;

    @FindBy(id = "_wizardCreateMandatGratuitFormRem_model_recuParRem")
    private static WebElement selectRecuParREMMandatGratuit;

    @FindBy(id = "_origineDemandeId")
    private static WebElement selectOrigineMandatGratuit;

    @FindBy(id = "_recuPar")
    private static WebElement selectRecuParMandatGratuit;

    @FindBy(id = DATE_REELLE_CESSATION_ID)
    private static WebElement dateCessationMandatGratuit;

    @FindBy(id = "_decision")
    private static WebElement selectDecisionMandatGratuit;

    @FindBy(id = "_raisonRefus")
    private static WebElement selectRaisonRefusMandatGratuit;

    @FindBy(id = "wizardCreateMandatGratuitFormObjet_model_commentRaisonRefus")
    private static WebElement commentaireAutreRaisonRefusMandatGratuit;

    @FindBy(id = "wizardCreateMandatGratuitFormObjet_model_annulationPension")
    private static WebElement checkAnnulationTrimestrePensionMandatGratuit;

    @FindBy(id = "wizardCreateMandatGratuitFormObjet_model_editionLettre")
    private static WebElement checkCourrierMandatGratuit;

    @FindBy(id = "difficultePaiement")
    private static WebElement checkDifficultePaiementMandatGratuit;

    @FindBy(id = "annexeAttestComp")
    private static WebElement checkAttestationComplementaireMandatGratuit;

    @FindBy(id = "annexeDeclaAffil")
    private static WebElement checkDeclarationAffiliationMandatGratuit;

    @FindBy(id = "addObservationForm_observation_reference")
    private static WebElement referenceObservationMandatGratuit;

    @FindBy(id = "addObservationForm_observation_commentaire")
    private static WebElement commentaireObservationMandatGratuit;

    @FindBy(id = "_obsTypeMediaList")
    private static WebElement selectTypeMediaObservationMandatGratuit;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerObservationMandatGratuit;

    /**
     * Clôture dossier - Assimilation maladie
     */
    @FindBy(id = "lvl_11")
    private static WebElement btnAssMaladieSansREM;

    @FindBy(id = "lvl_12")
    private static WebElement btnAssMaladieReceptionDecisionInasti;

    @FindBy(id = "lvl_13")
    private static WebElement btnAssMaladieEnvoiInfosComplInasti;

    @FindBy(id = "lvl_14")
    private static WebElement btnAssMaladieAnnulationDemandeEnTraitement;

    @FindBy(id = "lvl_15")
    private static WebElement btnAssMaladieAjouterObservation;

    @FindBy(id = "_origineDemandeId")
    private static WebElement selectOrigineREMAssMaladie;

    @FindBy(id = "_wizardCreateAssimMaladieFormRem_model_recuParRem")
    private static WebElement selectRecuParREMAssMaladie;

    @FindBy(id = "dateIncapaciteTravail")
    static WebElement dateIncapaciteREMAssMaladie;

    @FindBy(id = "affilieSeulSociete")
    private static WebElement checkAffilieSeulEnSocieteREMAssMaladie;

    @FindBy(id = "pasRadierBce")
    private static WebElement checkPasRadierBceREMAssMaladie;

    @FindBy(id = "_origineDemandeId")
    private static WebElement selectOrigineAssMaladie;

    @FindBy(id = "_recuPar")
    private static WebElement selectRecuParAssMaladie;

    @FindBy(id = DATE_DEMANDE_ID)
    private static WebElement dateDemandeAssMaladie;

    @FindBy(id = DATE_REELLE_CESSATION_ID)
    private static WebElement dateReelleCessationAssMaladie;

    @FindBy(id = "wizardCreateAssimMaladieFormObjet_model_cloturerDossier")
    private static WebElement checkClotureDossierAssMaladie;

    @FindBy(id = "_wizardCreateAssimMaladieFormObjet_model_destinataireInasti")
    private static WebElement selectDestinaireAssMaladie;

    @FindBy(id = "wizardCreateAssimMaladieFormObjet_model_texteLibre1")
    private static WebElement texteLibre1AssMaladie;

    @FindBy(id = "wizardCreateAssimMaladieFormObjet_model_texteLibre2")
    private static WebElement texteLibre2AssMaladie;

    @FindBy(id = "_decision")
    private static WebElement selectDecisionAssMaladie;

    @FindBy(id = "_natureCotisante")
    private static WebElement selectNatureCotisanteAssMaladie;

    @FindBy(id = "wizardCreateAssimMaladieFormObjet_model_editionLettre")
    private static WebElement checkCourrierDemandeAssMaladie;

    @FindBy(id = "wizardDecisionAssimMaladieForm1_model_editionLettre")
    private static WebElement checkCourrierDecisionAssMaladie;

    @FindBy(xpath = "//*[@id='raisonDiv']/fieldset/p[1]/input")
    private static WebElement checkRaisonRefusPasOrdreCotisationAssMaladie;

    @FindBy(xpath = "//*[@id='raisonDiv']/fieldset/p[2]/input")
    private static WebElement checkRaisonRefusPasCessationAssMaladie;

    @FindBy(xpath = "//*[@id='raisonDiv']/fieldset/p[3]/input")
    private static WebElement checkRaisonRefusIncapaciteNonReconnueAssMaladie;

    @FindBy(xpath = "//*[@id='raisonDiv']/fieldset/p[4]/input")
    private static WebElement checkRaisonRefusPoursuiteActiviteAssMaladie;

    @FindBy(xpath = "//*[@id='raisonDiv']/fieldset/p[5]/input")
    private static WebElement checkRaisonRefusExistanceRevenusAssMaladie;

    @FindBy(xpath = "//*[@id='raisonDiv']/fieldset/p[6]/input")
    private static WebElement checkRaisonRefusGerantSocieteAssMaladie;

    @FindBy(xpath = "//*[@id='raisonDiv']/fieldset/p[7]/input")
    private static WebElement checkRaisonRefusIncapacitePasAssezLongueAssMaladie;

    @FindBy(xpath = "//*[@id='raisonDiv']/fieldset/p[8]/input")
    private static WebElement checkRaisonRefusActSalarieeAssMaladie;

    @FindBy(xpath = "//*[@id='raisonDiv']/fieldset/p[9]/input")
    private static WebElement checkRaisonRefusAutreCouvSocialeAssMaladie;

    @FindBy(xpath = "//*[@id='raisonDiv']/fieldset/p[10]/input")
    private static WebElement checkRaisonRefusPasSuiteAssMaladie;

    @FindBy(xpath = "//*[@id='raisonDiv']/fieldset/p[11]/input")
    private static WebElement checkRaisonRefusAutreAssMaladie;

    @FindBy(id = "addObservationForm_observation_reference")
    private static WebElement referenceObservationAssMaladie;

    @FindBy(id = "addObservationForm_observation_commentaire")
    private static WebElement commentaireObservationAssMaladie;

    @FindBy(id = "_obsTypeMediaList")
    private static WebElement selectTypeMediaObservationAssMaladie;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerObservationAssMaladie;

    @FindBy(id = "_origineDemandeId")
    private static WebElement selectOrigineInfosAssMaladie;

    @FindBy(id = "_recuPar")
    private static WebElement selectRecuParInfosAssMaladie;

    @FindBy(id = "_wizardInfoCompAssimMaladieForm1_model_destinataireInasti")
    private static WebElement selectDestinaireInfosAssMaladie;

    @FindBy(id = "wizardInfoCompAssimMaladieForm1_model_texteLibre1")
    private static WebElement texteLibre1InfosAssMaladie;

    @FindBy(id = "wizardInfoCompAssimMaladieForm1_model_texteLibre2")
    private static WebElement texteLibre2InfosAssMaladie;

    /**
     * Clôture dossier - Assurance continuée
     */
    @FindBy(id = "lvl_10")
    private static WebElement btnAssContinueeAvecREM;

    @FindBy(id = "lvl_11")
    private static WebElement btnAssContinueeAvecCessation;

    @FindBy(id = "lvl_13")
    private static WebElement btnAssContinueeEnvoiInfosComplInasti;

    @FindBy(id = "lvl_14")
    private static WebElement btnAssContinueeAnnulationDemandeEnTraitement;

    @FindBy(id = "lvl_16")
    private static WebElement btnAssContinueeAjouterObservation;

    @FindBy(id = "_origineDemandeId")
    private static WebElement selectOrigineREMAssContinuee;

    @FindBy(id = "_wizardCreateAssurContinueeFormRem_model_recuParRem")
    private static WebElement selectRecuParREMAssContinuee;

    @FindBy(id = "demandeTardive")
    private static WebElement checkDemandeTardiveAssContinuee;

    @FindBy(id = "commentDemandeTardive")
    private static WebElement commentaireDemandeTardiveAssContinuee;

    @FindBy(id = "_origineDemandeId")
    private static WebElement selectOrigineAssContinuee;

    @FindBy(id = "_recuPar")
    private static WebElement selectRecuParAssContinuee;

    @FindBy(id = DATE_DEMANDE_ID)
    private static WebElement dateDemandeAssContinuee;

    @FindBy(id = "_wizardCreateAssurContinueeFormObjet_model_destinataireInasti")
    private static WebElement selectDestinaireAssContinuee;

    @FindBy(id = "wizardCreateAssurContinueeFormObjet_model_texteLibre1")
    private static WebElement texteLibre1AssContinuee;

    @FindBy(id = "wizardCreateAssurContinueeFormObjet_model_texteLibre2")
    private static WebElement texteLibre2AssContinuee;

    @FindBy(id = "_decision")
    private static WebElement selectDecisionAssContinuee;

    @FindBy(id = "_raisonRefus")
    private static WebElement selectRaisonRefusAssContinuee;

    @FindBy(id = DATE_REELLE_CESSATION_ID)
    private static WebElement dateReelleCessationAssContinuee;

    @FindBy(id = "wizardCreateAssurContinueeFormObjet_model_editionLettre")
    private static WebElement checkCourrierDemandeAssCont;

    @FindBy(id = "wizardDecisionAssurContinueeForm1_model_editionLettre")
    private static WebElement checkCourrierDecisionAssCont;

    @FindBy(id = "_typeAssuranceContinuee")
    private static WebElement selectTypeAssContinuee;

    @FindBy(id = "addObservationForm_observation_reference")
    private static WebElement referenceObservationAssContinuee;

    @FindBy(id = "addObservationForm_observation_commentaire")
    private static WebElement commentaireObservationAssContinuee;

    @FindBy(id = "_obsTypeMediaList")
    private static WebElement selectTypeMediaObservationAssContinuee;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerObservationAssContinuee;

    @FindBy(id = "_origineDemandeId")
    private static WebElement selectOrigineInfosAssContinuee;

    @FindBy(id = "_recuPar")
    private static WebElement selectRecuParInfosAssContinuee;

    @FindBy(id = "_wizardInfoCompAssurContinueeForm1_model_destinataireInasti")
    private static WebElement selectDestinaireInfosAssContinuee;

    @FindBy(id = "wizardInfoCompAssurContinueeForm1_model_texteLibre1")
    private static WebElement texteLibre1InfosAssContinuee;

    @FindBy(id = "wizardInfoCompAssurContinueeForm1_model_texteLibre2")
    private static WebElement texteLibre2InfosAssContinuee;

    /**
     * Exonerations
     */
    @FindBy(id = "decisionsParAnnee")
    private static WebElement tableDecisionExo;

    @FindBy(id = "table-exo-pm")
    private static WebElement tableExoPm;

    @FindBy(id = "decisionGestionnaireForm_raisonNonRevisionREVISION")
    private static WebElement checkRevision;

    @FindBy(id = "decisionGestionnaireForm_raisonNonRevisionDEJA_TRAITEE")
    private static WebElement checkDejaTraitee;

    @FindBy(id = "decisionGestionnaireForm_raisonNonRevisionPOSSIBLE_QUE_POUR_TROIS_PREMIERES_ANNEES")
    private static WebElement check3PremieresAnnees;

    @FindBy(id = "immatriculeeEnTantQueSocieteCommerciale")
    private static WebElement immatriculeeEnTantQueSocieteCommerciale;

    /**
     * Mise en veilleuse
     */
    @FindBy(id = "lvl_1")
    private static WebElement menuActionMiseEnVeilleuse;

    @FindBy(id = "lvl_10")
    private static WebElement btnGestionDemandesMiseEnVeilleuse;

    @FindBy(id = "addDemId")
    private static WebElement btnAjouterDemande;

    @FindBy(xpath = "//*[@id='bureauTableId']/tbody/tr[1]")
    private static WebElement selectBureauContribution;

    @FindBy(xpath = "/html/body/div[6]/div[3]/div/button[2]")
    private static WebElement btnTerminerMev;

    @FindBy(id = "consultationVeilleuseTableId")
    private static WebElement tableMev;

    /**
     * Compte Client
     */
    @FindBy(id = "_typeRegroupement")
    private static WebElement selectAffichage;

    @FindBy(xpath = "//tr[2]/td[2]/select")
    private static WebElement filtreCreance;

    @FindBy(xpath = "//tr[2]/td[3]/select")
    private static WebElement filtreTypeCreance;

    @FindBy(xpath = "//tr[2]/td[1]/select")
    private static WebElement filtrePeriode;

    @FindBy(xpath = "//tr[2]/td[5]/select")
    private static WebElement filtreRecouvrementPP;

    @FindBy(xpath = "//tr[2]/td[4]/select")
    private static WebElement filtreRecouvrementPM;

    @FindBy(xpath = "//tr[2]/td[8]/select")
    private static WebElement filtreSuspensionPP;

    @FindBy(xpath = "//tr[2]/td[6]/select")
    private static WebElement filtreSuspensionPM;

    @FindBy(id = "_raisonAnnultaionobjectComptableId")
    private static WebElement selectRaisonAnnulation;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerRaisonAnnulation;

    @FindBy(id = "soldeTotal")
    private static WebElement soldeTotal;

    @FindBy(id = "soldeTotal")
    private static WebElement inputSoldeTotal;

    /**
     * Operations
     */
    @FindBy(id = OPERATIONS_COMPTABLES_TABLE_ID)
    private static WebElement tableoperationComptable;

    @FindBy(id = "operationsComptablesTable_next")
    private static WebElement btnNextOperations;

    @FindBy(id = "typeOperationId")
    private static WebElement selectTypeOpreation;

    @FindBy(id = "_operationComptaRechercherForm_natureEntiteComptable")
    private static WebElement selectCreanceOperation;

    @FindBy(id = "_typeRegroupement")
    private static WebElement selectTypeRegroupementOperation;

    @FindBy(xpath = "//*[@id='operationsComptablesTable']/tfoot/tr/th[7]")
    private static WebElement montantSoldeOperations;

    @FindBy(id = "afficherOperationsAnnulees")
    private static WebElement checkOperationsAnnulees;

    /**
     * Signaux 74L Inasti
     */

    @FindBy(id = "COT_PRO_CTLDiv")
    private static WebElement menuCotisantProfilControle;

    @FindBy(id = "COT_PRO_CTL_74LDiv")
    private static WebElement menuCotisantProfilSignaux74LInasti;

    @FindBy(xpath = "/html/body/div[6]/div[2]/div/div[2]/div[3]/button")
    private static WebElement btnTerminerSignaux74LInasti;

    @FindBy(xpath = "/html/body/div[6]/div[2]/div/div[2]/div[3]/button")
    private static WebElement selectSignaux74LATraiter;

    /**
     * Demande Refresh
     */
    @FindBy(id = "CAR_FLUDiv")
    private static WebElement menuCarriereHistorique;

    @FindBy(id = DEMANDE_REFRESH_TABLE_ID)
    private static WebElement tableDemandeRefresh;

    @FindBy(xpath = "/html/body/div[6]/div[3]/div/button[2]")
    private static WebElement btnTerminerDemandeRefresh;

    @FindBy(id = "_selectedEtat")
    private static WebElement selectPretEnvoi;

    @FindBy(id = "_etatID")
    private static WebElement selectListePretEnvoi;

    @FindBy(xpath ="html/body/div[1]/rightcontent/div[2]/div/div/div/div[2]/div/div[1]/form/div/div[2]/div[1]/input")
    private static WebElement btnRechercherDemandeRefresh;


    /**
     * Carriere
     */


    @FindBy(xpath = "//div[1]/rightcontent/div[2]/div/form/div[1]/h3/span")
    private static WebElement accordionProfilAcc;

    @FindBy(id = DEMANDE_REFRESH_TABLE_ID)
    private static WebElement tableLigneCarriere;




    /**
     * Observation
     */
    @FindBy(id = "lvl_1")
    private static WebElement menuActionObservation;

    @FindBy(id = "lvl_11")
    private static WebElement btnAjouterObservation;

    @FindBy(id = "_obsTypeMediaId")
    private static WebElement typeMediaObservation;

    @FindBy(id = "_obsTypeEntiteId")
    private static WebElement typeObservation;

    @FindBy(xpath = "//form/div[1]/p/span/input")
    private static WebElement typeListObservation;

    @FindBy(id = "obsSearchBtn")
    private static WebElement btnSearchObservations;

    @FindBy(id = "editObservationForm_observation_reference")
    private static WebElement referenceObservation;

    @FindBy(id = "editObservationForm_observation_commentaire")
    private static WebElement commentaireObservation;

    @FindBy(id = "_obsTypeMediaList")
    private static WebElement typeMediaListObservation;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerObservation;

    @FindBy(id = "accordionTableArchive")
    private static WebElement accordionTableArchive;

    @FindBy(xpath = "//html/body/div[6]/div[3]/a[1]")
    private static WebElement confirmArchivage;

    /**
     * Irrécouvrables
     */
    @FindBy(id = "lvl_1")
    private static WebElement menuActionIrrecouvrables;

    @FindBy(id = "lvl_10")
    private static WebElement passerIrrecouvrableProvisoire;

    @FindBy(id = "lvl_11")
    private static WebElement passerIrrecouvrableDefinitif;

    @FindBy(id = "_passerIrrecouvrableForm1_model_motifIrrecouvrabilite")
    private static WebElement motifIrrecouvrable;

    @FindBy(id = "passerIrrecouvrableForm1_model_commentaire")
    private static WebElement commentaireIrrecouvrable;

    @FindBy(id = ENTITES_COMPTABLES_TABLE_ID)
    private static WebElement entitesComptablesTable;

    @FindBy(id = "datePrescriptionBtn")
    private static WebElement btnDatePrescriptionIrrecouvrable;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerDatePrescriptionIrrecouvrable;

    @FindBy(id = "envoiOui")
    private static WebElement envoiLettreOuiIrrecouvrable;

    @FindBy(id = "envoiNon")
    private static WebElement envoiLettreNonIrrecouvrable;

    @FindBy(id = "annulationBtn")
    private static WebElement btnAnnulationIrrecouvrable;

    @FindBy(xpath = "//div[2]/fieldset/table/tbody/tr[1]/td[1]/input")
    private static WebElement envoiLettreContentieuxIrrecouvrable;

    @FindBy(id = "modificationBtn")
    private static WebElement btnModificationIrrecouvrable;

    @FindBy(id = "modificationBtn")
    private static WebElement btnSimulationIrrecouvrable;

    @FindBy(id = "redebitionOui")
    private static WebElement redebitionMajorationsOuiIrrecouvrable;

    @FindBy(id = "redebitionNon")
    private static WebElement redebitionMajorationsNonIrrecouvrable;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerRedebitionIrrecouvrable;

    @FindBy(id = "annulationPartielleIrrec_montantPartiel")
    private static WebElement montantAAnnulerIrrecouvrable;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerSuppressionPartielleIrrecouvrable;

    @FindBy(id = "provisoire")
    private static WebElement provisoireIrrecouvrable;

    @FindBy(id = "definitif")
    private static WebElement definitifIrrecouvrable;

    @FindBy(id = "_modificationIrrec_irrecouvrableDto_motifIrrecouvrabilite")
    private static WebElement motifModificationIrrecouvrable;

    @FindBy(id = "modificationIrrec_irrecouvrableDto_commentaire")
    private static WebElement commentaireModificationIrrecouvrable;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerModificationIrrecouvrable;

    /**
     * Prescription
     */
    @FindBy(id = "datePrescriptionBtn")
    private static WebElement btnDatePrescription;

    @FindBy(id = "prescrireBtn")
    private static WebElement btnPrescrirePrescription;

    @FindBy(id = TABLE_ENTITE_COMPTABLES_PRESCRITES_ID)
    private static WebElement entiteComptablesPrescritesTable;

    @FindBy(id = "editDatePrescription_montantPartiel")
    private static WebElement montantPartielPrescription;

    @FindBy(id = "prescrirePartiellementForm_montantPartiel")
    private static WebElement montantPartielAnnulationPrescription;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerAnnulationPrescription;

    @FindBy(id = "_editDatePrescription_sousTypeTraitement")
    private static WebElement selectTypePassagePartielPrescription;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerPassagePrescription;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerDatePrescription;

    @FindBy(id = "supprimerBtn")
    private static WebElement btnSupprimerPrescription;

    @FindBy(id = "addBtn")
    private static WebElement btnAjouterActeInterruptifPrescription;


    @FindBy(id = "_addActeInterruptifForm_typeActe")
    private static WebElement typeActeInterruptifPrescription;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerActeInterruptifPrescription;

    @FindBy(id = "_editTypePrescriptionForm_sousTypeTraitement")
    private static WebElement selectModificationTypePrescription;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerModificationPrescription;

    /**
     * Levée majorations
     */
    @FindBy(id = "lm_lvl_1")
    private static WebElement menuActionLM;

    @FindBy(id = "lm_lvl_10")
    private static WebElement menuActionAjouterLM;

    @FindBy(id = TABLE_LEVEE_MAJORATION_ID)
    private static WebElement leveeMajorationTable;

    @FindBy(id = "_wizardCreateLmForm1_model_origineDemande")
    private static WebElement selectOrigineDemandeLM;

    @FindBy(id = "_wizardCreateLmForm1_model_recuPar")
    private static WebElement selectDemandeRecueParLM;

    @FindBy(id = "_typeLeveeMajorationId")
    private static WebElement selectTypeDemandeLM;

    @FindBy(id = "wizardCreateLmForm1_model_coDebiteurSolidaire")
    private static WebElement checkCodebiteurSolidaireLM;

    @FindBy(id = "reponseTiersCheckbox")
    private static WebElement checkReponseATiersLM;

    @FindBy(id = "searchButtonTiers")
    private static WebElement btnRechercherTiersLM;

    @FindBy(id = "mediateurCheckBox")
    private static WebElement checkMediateurLM;

    @FindBy(id = "ilFilNissId")
    private static WebElement nissTiersLM;

    @FindBy(id = "btnSearch")
    private static WebElement btnRechercherIdentiteTiersLM;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerTiersLM;

    @FindBy(xpath = "//div[2]/form/div[1]/div/ul/li[2]/a")
    private static WebElement ongletAdresseTiersLM;

    @FindBy(id = "searchCotisationForm_filtre_anneeFrom")
    private static WebElement periodeAnneeFromLM;

    @FindBy(id = "searchCotisationForm_filtre_trimFrom")
    private static WebElement periodeTrimestreFromLM;

    @FindBy(id = "anneeTo")
    private static WebElement periodeAnneeToLM;

    @FindBy(id = "searchCotisationForm_filtre_anneeTo")
    private static WebElement periodeAnneeToLMPM;

    @FindBy(id = "searchCotisationForm_filtre_trimTo")
    private static WebElement periodeTrimestreToLM;

    @FindBy(id = "searchBtn")
    private static WebElement btnRechercherMajorationsLM;

    @FindBy(id = "emptyBtn")
    private static WebElement btnViderMajorationsLM;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnAjouterMajorationsLM;

    @FindBy(id = "paiementAttenteId")
    private static WebElement checkPaiementEnAttenteLM;

    @FindBy(id = "wizardCreateLmForm3_model_ps")
    private static WebElement postScriptumLM;

    @FindBy(id = "_typeDocument")
    private static WebElement selectTypeDocumentLM;

    @FindBy(id = "btnAddTempFile")
    private static WebElement btnAjouterDocumentLM;

    @FindBy(id = "fichier")
    private static WebElement selectionnerDocumentLM;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerDocumentLM;

    @FindBy(id = "wizardCreateLmForm3_model_debitionErronee")
    private static WebElement checkAnnulationDebitionErroneeLM;

    @FindBy(id = "lm_dec_lvl_1")
    private static WebElement menuActionDetailLM;

    @FindBy(id = "lm_dec_lvl_10")
    private static WebElement menuActionValiderLM;

    @FindBy(id = "lm_dec_lvl_20")
    private static WebElement menuActionModifierLM;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerModificationLM;

    @FindBy(id = "backButton")
    private static WebElement btnBackLM;


    /**
     * Plan apurement
     */
    @FindBy(id = "lvl_1")
    private static WebElement menuActionPA;

    @FindBy(id = "lvl_11")
    private static WebElement menuActionAjouterPA;

    @FindBy(id = "planApurementHistoriquesTableId")
    private static WebElement tableHistoriquePlanApurement;

    @FindBy(xpath = "//table/tbody/tr/td[1]/input[1]")
    private static WebElement checkIdentitePA;

    @FindBy(id = "paLetterMotivationId_Recu")
    private static WebElement radioLMRecuePA;

    @FindBy(id = "TablePaSoldePeriodique")
    private static WebElement tableSoldePeriodiquePA;

    @FindBy(id = "paIsConfirmationLetter")
    private static WebElement checkLettreConfirmationReportPA;

    @FindBy(id = "paClearancePlanTypeId_echelonnement")
    private static WebElement radioEchelonnementPA;

    @FindBy(id = "paIsAutomaticCalulMonthlyPayment")
    private static WebElement checkCalculAutoMensualitePA;

    @FindBy(id = "paDownPaymentId")
    private static WebElement acomptePA;

    @FindBy(id = "paMonthlyPaymentNumberId")
    private static WebElement nombreMensualitesPA;

    @FindBy(id = "_observationTypeMediaId")
    private static WebElement selectTypeObservationPA;

    @FindBy(id = "observationCommentaireId")
    private static WebElement observationPA;

    @FindBy(xpath = "//div[5]/div[1]/div/div/div[1]/p/input")
    private static WebElement totalPA;

    // Depistage
    @FindBy(id = "leverDepistageId")
    private static WebElement btnLeverDepistage;

    @FindBy(id = "_reponseDepistageCjtAidantForm1_model_situationConjoint")
    private static WebElement fieldReponseCjt;

    @FindBy(id = "SYNDiv")
    private static WebElement btnResume;

    @FindBy(xpath = "//img[@class='edit-rem']")
    private static WebElement btnEditRemDepistage;

    @FindBy(id = "editReclamationForm_recuGlobal")
    private static WebElement checkBoxRecuGlobal;

    // Recouvrement
    @FindBy(id = "lvl_1")
    private static WebElement menuActionRecouvrement;

    @FindBy(id = "lvl_10")
    private static WebElement menuActionRappelALaDemande;

    @FindBy(id = "lvl_10")
    private static WebElement menuActionMEDPrincipalALaDemande;

    @FindBy(id = "fallr-button-button1")
    private static WebElement btnOuiModal;

    // Carrière
    @FindBy(xpath = "//div[2]/div/form/div[2]/h3[1]/a")
    private static WebElement accordeonCritereRechercheConsultationCarriere;

    @FindBy(xpath = "//div[6]/div[2]/form/div[3]/div[2]/div/div/div[1]/p/span/a[1]/span[1]")
    private static WebElement accordeonObservationControleSpfCarriere;

    @FindBy(xpath = "//div[1]/rightcontent/div[2]/div/form/div[1]/h3/span")
    private static WebElement accordeonRechercheProfilConsultationCarriere;

    @FindBy(id = "sel1")
    private static WebElement checkSelectionGeneraleCarriere;

    @FindBy(id = "sel2")
    private static WebElement checkSelectionAnneeCarriere;

    @FindBy(id = "sel3")
    private static WebElement checkSelectionTrimestreCarriere;

    @FindBy(id = "selectionAnnee")
    private static WebElement selectionAnneeCarriere;

    @FindBy(id = "sousNum")
    private static WebElement selectionSousNumeroCarriere;

    @FindBy(id="editCarrierePensionFormId_carriereDto_revenuAdapte2")
    private static WebElement RevenuAdapteCarriere;

    @FindBy(id="editCarrierePensionFormId_carriereDto_revenuConstitue")
    private static WebElement RevenuComposeCarriere;

    @FindBy(id="editCarrierePensionFormId_carriereDto_revenuContribution")
    private static WebElement RevenuContributionCarriere;

    @FindBy(id="editCarrierePensionFormId_carriereDto_revenuBaseCalcul")
    private static WebElement RevenuBaseDeCalculCarriere;

    @FindBy(id="editCarrierePensionFormId_carriereDto_revenuCommunique1")
    private static WebElement RevenuCommuniqueCarriere;

    @FindBy(id="editCarrierePensionFormId_carriereDto_dateEcheance")
    private static WebElement DateEcheanceCarriere;

    @FindBy(id="editControleForm_controleSPFDto_dateControle")
    private static WebElement DateControleSpfCarriere;


    @FindBy(id="editCarrierePensionFormId_carriereDto_datePaiementTardif")
    private static WebElement DatePaiementTardifCarriere;

    @FindBy(name = "carPensionTable_length")
    private static WebElement select25ElementsCarriere;

    @FindBy(id="_observationTypeMediaId")
    private static WebElement selectControleSpfINASTI;


    @FindBy(id="_editCarrierePensionFormId_carriereDto_codeActivite")
    private static WebElement selectionCodeActiviteCarriere;

    @FindBy(id = "_selectionTrim")
    private static WebElement selectionTrimestreCarriere;

    @FindBy(id = "selectionTrim")
    private static WebElement selectionTrimestreAnneeCarriere;

    @FindBy(id = "selPer1")
    private static WebElement checkPeriodesTransmisesInastiCarriere;

    @FindBy(id = "selPer2")
    private static WebElement checkPeriodesValablesPensionCarriere;

    @FindBy(id = "selPer3")
    private static WebElement checkPeriodesNonValablesPensionCarriere;

    @FindBy(id = "selPer4")
    private static WebElement checkPeriodesEnAttenteCarriere;

    @FindBy(id = "selPer5")
    private static WebElement checkTousMouvementsCarriere;

    @FindBy(id = "searchBtn")
    private static WebElement btnSearchConsultationCarriere;

    @FindBy(id="iconDetail")
    private static WebElement btnSelectSpfCreeCarriere;

    @FindBy(id="lvl_ct_1")
    private static WebElement btnModifierTrimestreSpfCarriere;

    @FindBy(xpath = "//div[1]/rightcontent/div[2]/div/form/div/div[2]/div[1]/table/tbody/tr/td[12]")
    private static WebElement btnPencilCarriere;

    @FindBy(id = "addBtn")
    private static WebElement BtnAjouterGestionCarriere;

    @FindBy(xpath = "//div[5]/div[2]/form/div[1]/div/p/input[1]")
    private static WebElement BtnTrimestre1Carriere;

    @FindBy(xpath = "//div[5]/div[2]/form/div[1]/div/p/input[2]")
    private static WebElement BtnTrimestre2Carriere;

    @FindBy(xpath = "//div[5]/div[2]/form/div[1]/div/p/input[3]")
    private static WebElement BtnTrimestre3Carriere;

    @FindBy(xpath = "//div[5]/div[2]/form/div[1]/div/p/input[4]")
    private static WebElement BtnTrimestre4Carriere;

    @FindBy(xpath = "//div[5]/div[3]/div/button[2]")
    private static WebElement BtnEnregistrerCarriere;

    @FindBy(xpath = "//div[6]/div[3]/div/button[2]")
    private static WebElement BtnEnregistrer2Carriere;

    @FindBy(xpath = "//div[5]/div[3]/div/button[1]")
    private static WebElement BtnAnnulerCarriere;

    @FindBy(xpath = "//div[6]/div[3]/div/button[1]")
    private static WebElement BtnAnnuler2Carriere;

    @FindBy(xpath = "//div[7]/div[3]/div/button[2]")
    private static WebElement BtnEnregistrerControleSpfCarriere;

    @FindBy(name="buttonEmpty")
    private static WebElement BtnReinitialiserObservationControleSpfCarriere;

    @FindBy(id="addTrimestre")
    private static WebElement BtnPlusSpfCarriere;

    @FindBy(xpath = "//div[1]/rightcontent/div[2]/div/form/div/div[1]/div[2]/div[2]/input")
    private static WebElement BtnReinitialiserCarriere;

    @FindBy(xpath = "//div[5]/div[2]/form/div[3]/table/tbody/tr[1]/td/input")
    private static WebElement CotisationOrdinaireCarriere;

    @FindBy(xpath = "//div[5]/div[2]/form/div[3]/table/tbody/tr[2]/td[1]/input")
    private static WebElement CotisationDeRegularisationCarriere;

    @FindBy(xpath = "//div[5]/div[2]/form/div[3]/table/tbody/tr[3]/td/input")
    private static WebElement CotisationPresumeeCarriere;

    @FindBy(xpath = "//div[6]/div[2]/form/div[3]/table/tbody/tr[3]/td/input")
    private static WebElement CotisationPresumee2Carriere;

    @FindBy(id="editCarrierePensionFormId_carriereDto_provisoire")
    private static WebElement RevenuProvisoireCarriere;

    @FindBy(id="trimestresDupliques1")
    private static WebElement RevenuDuplique1Carriere;

    @FindBy(id="trimestresDupliques2")
    private static WebElement RevenuDuplique2Carriere;

    @FindBy(id="trimestresDupliques3")
    private static WebElement RevenuDuplique3Carriere;

    @FindBy(id="trimestresDupliques4")
    private static WebElement RevenuDuplique4Carriere;

    @FindBy(xpath = "//div[5]/div[2]/form/div[3]/table/tbody/tr[4]/td/input")
    private static WebElement LigneSansCalculCotiCarriere;

    @FindBy(xpath = "//div[1]/rightcontent/div[2]/div/form/div/div[2]/div[1]/table/tbody/tr[1]/td[1]/input")
    private static WebElement btnLigneGestionCarriere;

    @FindBy(xpath = "//div[1]/rightcontent/div[2]/div/form/div[2]/div[2]/div/div[4]/span/a[2]")
    private static WebElement Element2tableauConsultationCarriere;

    @FindBy(xpath = "//div[1]/rightcontent/div[2]/div/form/div[2]/div[2]/div/div[4]/a[4]")
    private static WebElement DernierElementtableauConsultationCarriere;

    @FindBy(xpath = "//div[1]/rightcontent/div[2]/div/form/div[2]/div[2]/div/div[4]/a[2]")
    private static WebElement ElementPrecedenttableauConsultationCarriere;

    @FindBy(xpath = "//div[1]/rightcontent/div[2]/div/form/div[2]/div[2]/div/div[4]/a[3]")
    private static WebElement ElementSuivanttableauConsultationCarriere;

    @FindBy(xpath = "//div[1]/rightcontent/div[2]/div/form/div[2]/div[2]/div/div[4]/a[1]")
    private static WebElement PremierElementtableauConsultationCarriere;

    @FindBy(id = "sel2")
    private static WebElement BtnRechercheGestionCarriereAnnee;

    @FindBy(id = "sel1")
    private static WebElement BtnRechercheControleSpfCarriere;

    @FindBy(id = "sel2")
    private static WebElement BtnRechercheControleSpfCarriereAnnee;

    @FindBy(id = "sel3")
    private static WebElement BtnRechercheControleSpfCarriereTrimestreAnnee;

    @FindBy(id = "sel3")
    private static WebElement BtnRechercheGestionCarriereTrimestreAnnee;

    @FindBy(id = "carPensionTable_length")
    private static WebElement selectElements;

    @FindBy(id = TABLE_CARRIERE_PENSION_ID)
    private static WebElement tableCarrierePension;

    public GestionClientPage(WebDriver drv) {
        this.driver = drv;
        PageFactory.initElements(driver, this);
    }

    public static void setSelectionSousNumeroCarriere(WebElement selectionSousNumeroCarriere) {
        GestionClientPage.selectionSousNumeroCarriere = selectionSousNumeroCarriere;
    }

    public void clickBtnResume() {
        btnResume.click();
    }

    public void clickBtnEditRemDepistage() {
        btnEditRemDepistage.click();
    }

    public void clickBtnLeverDepistage() {
        btnLeverDepistage.click();
    }

    public void clickCheckBoxRecuGlobal() {
        checkBoxRecuGlobal.click();
    }

    public void doFillReponseCjt(String reponse) {
        ActionUtils.sendInComboWithoutLoading(fieldReponseCjt, reponse);
    }

    public void clickBtnAjouterEvenementProfil() {
        ActionUtils.clickAndLoading(btnAjouterEvenement);
    }

    public void clickBtnProfilCourrierNon() {
        btnRadioCourrierProfilChange.click();
        SeleniumUtils.waitForActionCommon();
    }

    public boolean isCourrierDisplayed() {
        try {
            return btnRadioCourrierProfilChange.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickBtnAjoutRevenu() {
        ActionUtils.clickAndLoading(btnopenAjoutRevenu);
    }

    public void selectTypeRevenu(String typeRevenu) {
        ActionUtils.sendInCombo(selectTypeRevenu, typeRevenu);
        SeleniumUtils.waitForActionCommon();
    }

    public void checkOrigineDeclare() {
        checkOrigineDeclare.click();
    }

    public void deselectCheckBoxTrim1() {
        checkBoxTrim1.click();
    }

    public void deselectCheckBoxTrim2() {
        checkBoxTrim2.click();
    }

    public void deselectCheckBoxTrim3() {
        checkBoxTrim3.click();
    }

    public void selectCheckSortirSansRegul() {
        checkSortirSansRegul.click();
    }

    public void selectStatutRevenu(String statutRevenu) {
        ActionUtils.sendInCombo(selectStatutFullRevenu, statutRevenu);
    }

    public void clickClearStatutRevenu() {
        clearStatutFullRevenu.click();
    }

    public void selectSourceRevenu(String typeSourceRevenu) {
        ActionUtils.sendInCombo(selectSourceFullRevenu, typeSourceRevenu);
    }

    public void checkBoxRevenuAUtiliser() {
        checkBoxRevenuAUtiliser.click();
    }

    public void clickBtnEnregistrerModalRevenu() {
        ActionUtils.clickAndLoading(btnEnregistrerModalRevenu);
    }

    public void clickBtnEnregistrerModalRevenuAvecErreur() {
        ActionUtils.clickAndLoading(btnEnregistrerModalRevenu);
    }

    private Map<String, WebElement> makeMapFromTable(String tableId, int trindex) {
        Map<String, WebElement> map = new LinkedHashMap<>();

        map.put(TestData.ANNEE, TestBase.getDriver().findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + trindex + "]/td[2]")));
        map.put(TestData.REVENU, TestBase.getDriver().findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + trindex + "]/td[3]")));
        map.put(TestData.SOURCE, TestBase.getDriver().findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + trindex + "]/td[4]")));
        map.put(TestData.TYPE, TestBase.getDriver().findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + trindex + "]/td[5]")));
        map.put(TestData.STATUT, TestBase.getDriver().findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + trindex + "]/td[6]")));

        return map;
    }

    public Boolean isElementRevenuPresentWizard(String annee, String revenu, String source, String type, String statut) {
        try {
            String tableId = TABLE_REVENUS_WIZARD_ID;
            Elements trs = TableUtils.getParsedTable(tableId);
            int size = trs.size();

            for (int trindex = 1; trindex < size; trindex++) {
                Map<String, WebElement> tableMap = makeMapFromTable(tableId, trindex);

                if (tableMap.get(TestData.ANNEE).getText().equals(annee) &&
                        SeleniumUtils.transformStringToBigDecimal(SeleniumUtils.deleteFormat(tableMap.get(TestData.REVENU).getText())).equals(SeleniumUtils.transformStringToBigDecimal(revenu)) &&
                        checkSourceTypeStatut(source, type, statut, tableMap)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkSourceTypeStatut(String source, String type, String statut, Map<String, WebElement> tableMap) {
        return tableMap.get(TestData.SOURCE).getText().equals(source) &&
                tableMap.get(TestData.TYPE).getText().equals(type) &&
                tableMap.get(TestData.STATUT).getText().equals(statut);
    }

    public Boolean isElementRevenuPresentWizard(String annee, String type, String statut) {
        return doSearchElementRevenu(TABLE_REVENUS_WIZARD_ID, annee, statut, type);
    }

    public Boolean isElementRevenuPresent(String annee, String revenu, String source, String type) {
        try {
            String tableId = TABLE_REVENUS_ID;
            Elements trs = TableUtils.getParsedTable(tableId);
            int size = trs.size();

            for (int trindex = 1; trindex < size; trindex++) {
                Map<String, WebElement> tableMap = makeMapFromTable(tableId, trindex);

                if (tableMap.get(TestData.ANNEE).getText().equals(annee) &&
                        SeleniumUtils.transformStringToBigDecimal(SeleniumUtils.deleteFormat(tableMap.get(TestData.REVENU).getText())).equals(SeleniumUtils.transformStringToBigDecimal(revenu)) &&
                        tableMap.get(TestData.SOURCE).getText().equals(source) &&
                        tableMap.get(TestData.TYPE).getText().equals(type)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean isElementRevenuPresent(String annee, String statut, String type) {
        return doSearchElementRevenu(TABLE_REVENUS_ID, annee, statut, type);
    }

    private boolean doSearchElementRevenu(String tableId, String annee, String statut, String type) {
        try {
            Elements trs = TableUtils.getParsedTable(tableId);
            int size = trs.size();

            for (int trindex = 1; trindex < size; trindex++) {
                Map<String, WebElement> tableMap = makeMapFromTable(tableId, trindex);

                if (tableMap.get(TestData.ANNEE).getText().equals(annee) &&
                        tableMap.get(TestData.STATUT).getText().equals(statut) &&
                        tableMap.get(TestData.TYPE).getText().equals(type)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickRevenuEdition(boolean wizard, String annee, String type) {
        try {
            String tableId = TABLE_REVENUS_ID;
            if (wizard) {
                tableId = TABLE_REVENUS_WIZARD_ID;
            }

            Elements trs = TableUtils.getParsedTable(tableId);
            int size = trs.size();

            for (int trindex = 1; trindex < size; trindex++) {
                Map<String, WebElement> tableMap = makeMapFromTable(tableId, trindex);

                if (tableMap.get(TestData.ANNEE).getText().equals(annee) &&
                        tableMap.get(TestData.TYPE).getText().equals(type)) {
                    WebElement rowEdition = TestBase.getDriver().findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + trindex + "]/td[9]/a"));
                    ActionUtils.clickAndLoading(rowEdition);
                    return;
                }
            }
        } catch (Exception e) {

        }
    }

    public void clickBtnRadioRetourRevenu() {
        btnRadioRetourRevenu.click();
    }

    public void clickBtnOuiModalRevenu() {
        try {
            ActionUtils.clickAndLoading(btnOuiModalRevenu1);
        } catch (NoSuchElementException e) {
            try {
                ActionUtils.clickAndLoading(btnOuiModalRevenu2);
            } catch (NoSuchElementException e1) {

            }
        }
    }

    public void clickBtnEnregistrerProfilWizard() {
        try {
            ActionUtils.clickAndLoading(btnEnregistrerProfil);
        } catch (NoSuchElementException e) {
            try {
                ActionUtils.clickAndLoading(btnEnregistrerProfil2);
            } catch (NoSuchElementException e2) {
                try {
                    ActionUtils.clickAndLoading(btnEnregistrerProfil3);
                } catch (NoSuchElementException e3) {

                }
            }
        }
    }

    public void clickBtnEnregistrerRevenuFromProfil() {
        ActionUtils.clickAndLoading(btnenregistrerrevenufromprofil);
    }

    public void doSelectTypeCommunication() {
        ActionUtils.sendInComboWithoutLoading(selectTypeCommunication, "Communication de l’indépendant");
    }

    public void doSelectSousForme() {
        ActionUtils.sendInCombo(selectSousForme, "Téléphone");
    }

    public void fillDateCourrierDu(String date) {
        ActionUtils.doJsFill("printDateDownPaymentId", date);
    }

    public void doSelectFilSortie() {
        ActionUtils.sendInCombo(selectFileSortie, "Centrale");
    }

    public void clickAjouterModifierRevenu() {
        ActionUtils.moveToAndClickChild(menuRevenuActions, menuAjouterModifierRevenu, driver);
    }

    public void fillAnneeRevenu(String annee) {
        ActionUtils.sendInCombo(anneeRevenu, annee);
    }

    public void fillPeriodeOperationComptable(String year) {
        ActionUtils.doJsFill("periodeOpFrom", year);
        ActionUtils.doJsFill("periodeOpTo", year);
    }

    public void doSelectTypeOperation(String typeOperation) {
        if (typeOperation != null) {
            ActionUtils.selectInMultipleSelect(selectTypeOpreation, typeOperation);
        }
    }

    public void doSelectCreanceOperation(String creanceOperation) {
        ActionUtils.sendInComboWithoutLoading(selectCreanceOperation, creanceOperation);
    }

    public void doSelectTypeRegroupementOperation(String typeRegroupement) {
        ActionUtils.sendInComboWithoutLoading(selectTypeRegroupementOperation, typeRegroupement);
    }

    public void fillDateValeurComptable(String date) {
        ActionUtils.doJsFill("dateValeurFrom", date);
    }

    public void checkOperationsAnnulees() {
        checkOperationsAnnulees.click();
    }

    public String getSoldeTotal() {
        return soldeTotal.getText();
    }

    public void fillDateSignatureExoneration(String date) {
        ActionUtils.doJsFill("dateSignature", date);
    }
    public void fillDateReceptionExoneration(String date) {
        ActionUtils.doJsFill("dateReception", date);
    }

    public void checkImmatriculeeEnTantQueSocieteCommercialeExoneration(String check,String dateSocieteCommercial)
    {
        ActionUtils.doCheck(check,immatriculeeEnTantQueSocieteCommerciale);
        SeleniumUtils.waitForActionCommon();
        ActionUtils.doJsFill("dateImmatriculationSocieteCommerciale", dateSocieteCommercial);
    }

    public void fillDateCommunicationAuto() {
        ActionUtils.doJsFill("printDateDownPaymentId", DateUtils.getDateToday());
    }

    public void fillDateCommunicationRevenuAuto() {
        ActionUtils.doJsFill("dateCommunication", DateUtils.getDateToday());
    }

    public void fillDateCommunicationRevenuCustom(String date) {
        ActionUtils.doJsFill("dateCommunication", date);
    }

    public boolean isErrorDate(String type) {
        WebElement element = null;

        switch (type) {
            case "DateCommunicationRevenu":
                element = datecommunicationRevenu;
                break;
            case "DateDecesAutreSuccession":
                element = dateDecesAutreSuccession;
                break;
            case "DateCessationCessation":
                element = dateCessationCessation;
                break;
            case "DateDemandeAssContinuee":
                element = dateDemandeAssContinuee;
                break;
            case "DateDemandeAssMaladie":
                element = dateDemandeAssMaladie;
                break;
            case "DateAffiliationASupprimerNonAssujettissement":
                element = dateAffiliationASupprimerNonAssujettissement;
                break;
            case "DateAffiliationASupprimerREMNonAssujettissement":
                element = dateAffiliationASupprimerREMNonAssujettissement;
                break;
            case "DateCessationMandatGratuit":
                element = dateCessationMandatGratuit;
                break;
            default:
                break;
        }
        try {
            if (element.isDisplayed()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public void fillDateDistributionCustom(String date) {
        ActionUtils.doJsFill("dateDistribution", date);
    }

    public void fillDateDistributionRevenuAuto() {
        ActionUtils.doJsFill("dateDistribution", DateUtils.getDateToday());
    }

    public void fillDateEvenementCustom(String date) {
        ActionUtils.doJsFill(PROFIL_DATE_VALIDITE_ID, date);
    }

    public void fillTypeEvenement(String evenement) {
        selectTypeEvenement.clear();
        selectTypeEvenement.sendKeys(evenement);
        selectTypeEvenement.sendKeys(Keys.ARROW_DOWN);
        selectTypeEvenement.sendKeys(Keys.ENTER);

        if (caisseDestination.isDisplayed()) {
            selectCaisseDestination("017");
        }
        if (caisseOrigine.isDisplayed()) {
            selectCaisseOrigine("017");
        }
    }

    private void selectCaisseDestination(String caisse) {
        ActionUtils.sendInComboWithoutLoading(caisseDestination, caisse);
    }

    private void selectCaisseOrigine(String caisse) {
        ActionUtils.sendInComboWithoutLoading(caisseOrigine, caisse);
    }

    public void fillNatureCotisante(String nature) {
        if (naturecotisanteinput.isDisplayed()) {
            if (nature.contains(TestData.NATURE_PROFILE_MAXI_STATUT)) {
                naturecotisanteinput.sendKeys(TestData.NATURE_PROFILE_CONJOINT_MAXI_STATUT);
            } else {
                naturecotisanteinput.sendKeys(nature);
            }
            naturecotisanteinput.sendKeys(Keys.ARROW_DOWN);
            naturecotisanteinput.sendKeys(Keys.ENTER);
            SeleniumUtils.isLoading();
        }
    }

    public void fillDateProfilEvenement(String date) {
        ActionUtils.doJsFill(PROFIL_DATE_VALIDITE_ID, date);
        ActionUtils.refreshElementJS(dateProfilEvenement);
    }

    public void fillNumeroClient(String nbrclient) {
        ActionUtils.doJsFill("numCLientId", nbrclient);
    }

    public void fillNiss(String nbrniss) {
        ActionUtils.doJsFill("nissId", nbrniss);
    }

    public void fillBce(String nbrbce) {
        ActionUtils.doJsFill("bceId", nbrbce);
    }

    public void fillRevenu(String montantRevenu) {
        ActionUtils.sendInTextField(revenu, montantRevenu);
    }

    public void clickBtnSearch() {
        ActionUtils.clickAndLoading(btnsearch);
    }

    public void clickBtnReinitialiser() {
        ActionUtils.clickAndLoading(btnreset);
    }

    public void clickBtnSelectPersonnePhysique() {
        btnselectpersonnephysique.click();
    }

    public void clickBtnSelectPersonneMoral() {
        btnselectpersonnemoral.click();
    }

    public void clickBtnAddPeriodeSolidarite() {
        ActionUtils.clickAndLoading(btnaddperiodsolidarite);
    }

    public boolean isDecede() {
        try {
            return iconedecede.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickMenuCodebiteurs() {
        ActionUtils.clickChildMenu(menuCotisant, menuCotisantCodebiteurs, driver);
    }

    public void clickMenuExoneration() {
        ActionUtils.clickChildFromChildMenu(menuRecouvrement, menuRecouvrementAccompagnement, menuRecouvrementAccompagnementExoneration, driver);
    }

    public void clickMenuIrrecouvrablesGestion() {
        if (menuRecouvrementIrrecouvrableGestion.isDisplayed()) {
            ActionUtils.clickAndLoading(menuRecouvrementIrrecouvrableGestion);
        } else {
            ActionUtils.clickChildFromChildMenu(menuRecouvrement, menuRecouvrementIrrecouvrable, menuRecouvrementIrrecouvrableGestion, driver);
        }
    }

    public void clickMenuIrrecouvrablesSuivi() {
        if (menuRecouvrementIrrecouvrableSuivi.isDisplayed()) {
            ActionUtils.clickAndLoading(menuRecouvrementIrrecouvrableSuivi);
        } else {
            ActionUtils.clickChildFromChildMenu(menuRecouvrement, menuRecouvrementIrrecouvrable, menuRecouvrementIrrecouvrableSuivi, driver);
        }
    }

    public void clickMenuPrescriptionGestion() {
        if (menuRecouvrementPrescriptionGestion.isDisplayed()) {
            ActionUtils.clickAndLoading(menuRecouvrementPrescriptionGestion);
        } else {
            ActionUtils.clickChildFromChildMenu(menuRecouvrement, menuRecouvrementPrescription, menuRecouvrementPrescriptionGestion, driver);
        }
    }

    public void clickMenuPrescriptionSuivi() {
        if (menuRecouvrementPrescriptionSuivi.isDisplayed()) {
            ActionUtils.clickAndLoading(menuRecouvrementPrescriptionSuivi);
        } else {
            ActionUtils.clickChildFromChildMenu(menuRecouvrement, menuRecouvrementPrescription, menuRecouvrementPrescriptionSuivi, driver);
        }
    }

    public void clickMenuPrescriptionActesInterruptifs() {
        if (menuRecouvrementPrescriptionActesInterruptifs.isDisplayed()) {
            ActionUtils.clickAndLoading(menuRecouvrementPrescriptionActesInterruptifs);
        } else {
            ActionUtils.clickChildFromChildMenu(menuRecouvrement, menuRecouvrementPrescription, menuRecouvrementPrescriptionActesInterruptifs, driver);

        }
    }

    public void clickMenuRevenu() {
        ActionUtils.clickChildFromChildMenu(menuCotisant, menuCotisantRevenus, menuCotisantRevenusGestion, driver);
    }

    public void clickMenuProfil() {
        if (menuCotisantProfilChangementProfil.isDisplayed()) {
            ActionUtils.clickAndLoading(menuCotisantProfilChangementProfil);
        } else {
            ActionUtils.clickChildFromChildMenu(menuCotisant, menuCotisantProfil, menuCotisantProfilChangementProfil, driver);
        }
    }

    public void clickRaccourciResumeChangementProfil() {
        ActionUtils.clickAndLoading(raccourciResumeChangementProfil);
    }

    public void clickMenuDonneesPension() {
        if (menuCotisantProfilDonneesPension.isDisplayed()) {
            ActionUtils.clickAndLoading(menuCotisantProfilDonneesPension);
        } else {
            ActionUtils.clickChildFromChildMenu(menuCotisant, menuCotisantProfil, menuCotisantProfilDonneesPension, driver);
        }
    }

    public void clickMenuProfilAffiliation() {
        ActionUtils.clickChildFromChildMenu(menuCotisant, menuCotisantProfil, menuCotisantProfilAffiliation, driver);
    }

    public void clickMenuCompteCotisantSoldes() {
        ActionUtils.clickChildFromChildMenu(menuCompte, menuCompteCotisant, menuCompteCotisantSoldes, driver);
    }

    public void clickMenuCompteCotisantSoldesCodebit() {
        ActionUtils.clickChildFromChildMenu(menuCompte, menuCompteCotisant, menuCompteCotisantSoldesCodebit, driver);
    }

    public void clickMenuCompteCotisantSoldesCodebitOpen() {
        menuCompteCotisantSoldesCodebit.click();
        SeleniumUtils.waitForAction(1000);
    }

    public void clickMenuCompteCotisantOperations() {
        ActionUtils.clickChildFromChildMenu(menuCompte, menuCompteCotisant, menuCompteCotisantOperations, driver);
    }

    public void clickMenuComptePLCSoldes() {
        ActionUtils.clickChildFromChildMenu(menuCompte, menuComptePLC, menuComptePLCSoldes, driver);
    }

    public void clickMenuComptePLCOperations() {
        ActionUtils.clickChildFromChildMenu(menuCompte, menuComptePLC, menuComptePLCOperations, driver);
    }

    public void clickMenuClotureCessation() {
        ActionUtils.clickChildFromThirdChildMenu(menuCotisant, menuCotisantProfil, menuCotisantProfilCloture, menuCotisantProfilClotureCessation, driver);
    }

    public void clickMenuClotureDeces() {
        ActionUtils.clickChildFromThirdChildMenu(menuCotisant, menuCotisantProfil, menuCotisantProfilCloture, menuCotisantProfilClotureDeces, driver);
    }

    public void clickMenuClotureNonAssjettissement() {
        ActionUtils.clickChildFromThirdChildMenu(menuCotisant, menuCotisantProfil, menuCotisantProfilCloture, menuCotisantProfilClotureNonAssujettissement, driver);
    }

    public void clickMenuClotureMandatGratuit() {
        ActionUtils.clickChildFromThirdChildMenu(menuCotisant, menuCotisantProfil, menuCotisantProfilCloture, menuCotisantProfilClotureMandatGratuit, driver);
    }

    public void clickMenuClotureAssimilationMaladie() {
        ActionUtils.clickChildFromThirdChildMenu(menuCotisant, menuCotisantProfil, menuCotisantProfilCloture, menuCotisantProfilClotureAssimilationMaladie, driver);
    }

    public void clickMenuSignaux74LInasti() {
        ActionUtils.clickChildFromThirdChildMenu(menuCotisant, menuCotisantProfil, menuCotisantProfilControle, menuCotisantProfilSignaux74LInasti, driver);
    }

    public void clickMenuDemandeRefresh() {ActionUtils.clickChildMenu(menuCarriere, menuCarriereHistorique, driver); }

    public void selectPretEnvoi(String etat) {
        ActionUtils.sendInComboWithoutLoading(selectPretEnvoi, etat);
    }

    public void selectListePretEnvoi(String etat) {
        ActionUtils.sendInComboWithoutLoading(selectListePretEnvoi, etat);
    }

    public void clickMenuClotureAssuranceContinuee() {
        ActionUtils.clickChildFromThirdChildMenu(menuCotisant, menuCotisantProfil, menuCotisantProfilCloture, menuCotisantProfilClotureAssuranceContinuee, driver);
    }

    public void clickMenuPlanApurement() {
        ActionUtils.clickChildFromChildMenu(menuRecouvrement, menuRecouvrementAccompagnement, menuRecouvrementAccompagnementPlanApurement, driver);
    }

    public void clickMenuDebiteursPrincipaux() {
        ActionUtils.clickChildMenu(menuRecouvrement, menuRecouvrementDebiteursPrincipaux, driver);
    }

    public void clickAddExoneration() {
        ActionUtils.moveToAndClickChild(menuActionGestionProfil, menuActionExoneration, driver);
    }

    public void clickChangementdeNature(String constantNature) {
        boolean exception = false;

        while (!exception) {
            try {
                Actions action = new Actions(driver);
                action.moveToElement(menuActionGestionProfil).perform();
                SeleniumUtils.waitForActionCommon();
                action.moveToElement(menuActionChangementdeNature).perform();
                SeleniumUtils.waitForActionCommon();
                switch (constantNature) {
                    case TestData.NATURE_PROFILE_COMPLEMENTAIRE:
                        action.click(menuActionNatureComplementaire).perform();
                        break;
                    case TestData.NATURE_PROFILE_PRINCIPAL:
                        action.click(menuActionNaturePrincipal).perform();
                        break;
                    case TestData.NATURE_PROFILE_MAXI_STATUT:
                        action.click(menuActionNatureMaxiStatut).perform();
                        break;
                    case TestData.NATURE_PROFILE_MAXI:
                        action.click(menuActionNatureMaxiStatut).perform();
                        break;
                    case TestData.NATURE_PROFILE_MINI_STATUT:
                        action.click(menuActionNatureMiniStatut).perform();
                        break;
                    default:
                        break;
                }
                exception = true;
            } catch (MoveTargetOutOfBoundsException e) {
                return;
            }
        }

        SeleniumUtils.isLoading();
    }

    public void clickChangementProfilMultiple() {
        ActionUtils.moveToAndClickChild(menuActionGestionProfil, menuActionChangementMultiple, driver);
    }

    public void clickAjoutCodebiteurSolidaire() {
        ActionUtils.moveToAndClickChild(menuActions, menuAjoutCodebiteur, driver);
    }

    public void clickModifierCodebiteurSolidaire() {
        ActionUtils.moveToAndClickChild(menuActions, menuModifPeriodeSelectionnee, driver);
    }

    public void clickViewSolidarite(String niss) {
        TableUtils.clickViewElement("table-solidarite", niss);
    }

    public void clickEnvoyerCourrierCodebiteurSolidaire() {
        ActionUtils.moveToAndClickChild(menuActions, menuEnvoyerCourrier, driver);
    }

    public void clickRadioPeriodesSolidaires() {
        ActionUtils.clickAndLoading(checkToutesPeriodesSolidairesCodebiteurs);
    }

    public void clickRadioPeriodesNonSolidaires() {
        ActionUtils.clickAndLoading(checkToutesPeriodesNonSolidairesCodebiteurs);
    }

    public void clickRadioToutPeriodesSolidaires() {
        ActionUtils.clickAndLoading(checkToutesPeriodesCodebiteurs);
    }

    public void fillNissLiaisonCodebiteur(String niss) {
        ActionUtils.doJsFill("ilFilNissId", niss);
    }

    public void fillBceLiaisonCodebiteur(String bce) {
        ActionUtils.doJsFill("ilFilBceId", bce);
    }

    public void fillTypeIdentiteCodebiteur(String type) {
        ActionUtils.sendInComboWithoutLoading(filtreTypeIdentite, type);
    }

    private void fillNomCodebiteur(String nom) {
        ActionUtils.sendInTextField(nomidentiteliee, nom);
    }

    private void fillPrenomCodebiteur(String prenom) {
        ActionUtils.sendInTextField(prenomidentiteliee, prenom);
    }

    private void fillDenominationCodebiteur(String denom) {
        ActionUtils.sendInTextField(denomidentiteliee, denom);
    }

    public void clickOngletAdresseIdentiteSolidaire() {
        ongletAdresse.click();
    }

    public void createAdresseCodebiteurPP() {
        clickBtnAjoutAdresseSolidarite();

        ActionUtils.sendInTextField(localiteAdresse, "5060 - ARSIMONT");
        SeleniumUtils.isLoading();
        localiteAdresse.sendKeys(Keys.ARROW_DOWN);
        localiteAdresse.sendKeys(Keys.ENTER);
        SeleniumUtils.isLoading();

        ActionUtils.sendInTextField(codeRueAdresse, "Rue");
        SeleniumUtils.waitForActionCommon();
        codeRueAdresse.sendKeys(Keys.ARROW_DOWN);
        codeRueAdresse.sendKeys(Keys.ENTER);

        numeroAdresse.sendKeys("1");

        ActionUtils.clickAndLoading(btnEnregistrerAdresseCodebiteur);
    }

    public void createAdresseCodebiteurPM() {
        clickBtnAjoutAdresseSolidarite();

        ActionUtils.sendInComboWithoutLoading(typeAdresse, "Siège social adapté");

        ActionUtils.sendInTextField(localiteAdresse, "5060 - ARSIMONT");
        SeleniumUtils.isLoading();
        localiteAdresse.sendKeys(Keys.ARROW_DOWN);
        localiteAdresse.sendKeys(Keys.ENTER);
        SeleniumUtils.isLoading();

        rueAdresse.sendKeys("Rue Test");

        numeroAdresse.sendKeys("1");

        ActionUtils.clickAndLoading(btnEnregistrerAdresseCodebiteur);
    }

    private void clickBtnAjoutAdresseSolidarite() {
        ActionUtils.clickAndLoading(btnAjoutAdresse);
    }

    public void createCodebiteurPP(String niss) {
        fillTypeIdentiteCodebiteur("Personne physique");
        fillNissLiaisonCodebiteur(niss);
        fillNomCodebiteur(SeleniumUtils.generateRandomString());
        fillPrenomCodebiteur(SeleniumUtils.generateRandomString());
        btnselectsex.click();
        sexmasculin.click();
        clickBtnSearchCodebiteur();
        ActionUtils.clickAndLoading(btncreation);
    }

    public void createCodebiteurPM(String bce) {
        fillTypeIdentiteCodebiteur("Personne morale");
        fillBceLiaisonCodebiteur(bce);
        fillDenominationCodebiteur(SeleniumUtils.generateRandomString());
        fillFormeJuridique();
        clickBtnSearchCodebiteur();
        ActionUtils.clickAndLoading(btncreation);
    }

    private void fillFormeJuridique() {
        ActionUtils.sendInComboWithoutLoading(formeJuridiqueIdentiteliee, "SPRL");
    }

    public void clickBtnSearchCodebiteur() {
        ActionUtils.clickAndLoading(btnsearchaddcodebiteur);
    }

    public void checkPeriodeSolidarite() {
        if (!selectRaisonNonSolidarite.isDisplayed()) {
            periodeSolidarite.click();
        }
    }

    public void decheckPeriodeSolidarite() {
        if (selectRaisonNonSolidarite.isDisplayed()) {
            periodeSolidarite.click();
        }
    }

    public void selectRaisonNonSolidarite(String raison) {
        ActionUtils.sendInComboWithoutLoading(selectRaisonNonSolidarite, raison);
    }

    public void fillDateDebutPeriodeSolidarite(String trimestre) {
        ActionUtils.doJsFill("periodeDebutId", trimestre);
    }

    public void fillDateFinPeriodeSolidarite(String trimestre) {
        ActionUtils.doJsFill("periodeFinId", trimestre);
    }

    public void fillDateDebutPriseEffetSolidarite(String date) {
        ActionUtils.doJsFill("dateEffetDebutPeriodeId", date);
    }

    public void fillDateFinPriseEffetSolidarite(String date) {
        ActionUtils.doJsFill("dateEffetFinPeriodeId", date);
    }

    public boolean existModalOuiNonSolidarite() {
        try {
            return btnOuiModal.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickBtnEnregistrer() {
        ActionUtils.clickAndLoading(btnenregistrercodebiteurwizard);
    }

    public void clickCheckDebiteurCreationPeriode() {
        checkDebiteurCreationPeriode.click();
    }

    public void clickCheckCodebiteurCreationPeriode() {
        checkCodebiteurCreationPeriode.click();
    }

    public void fillSuiteASolidaire(String suite) {
        ActionUtils.sendInCombo(listSuiteA, suite);
    }

    public boolean checkSolidarite(String codebiteur, String raisonNonSolidarite) {
        return TableUtils.isElementSolidaritePresent("table-solidarite", codebiteur, raisonNonSolidarite);
    }

    public void clickBtnAjoutRevenuWizard() {
        ActionUtils.clickAndLoading(btnAddRevenu);
    }

    public void clickSearchResultCodebiteur(String contenuresult) {
        TableUtils.clickElementIsPresent("DataTables_Table_0", contenuresult);
        SeleniumUtils.isLoading();
    }

    public void clickSearchResultSignaux74LInastiATraiter() {
        TableUtils.clickElementWithClassColumnIsPresent("table-signal74L", "A traiter","Traiter le signal");
    }

    public void clickSearchResultDemandeRefreshATraiter(String raison){
        WebElement table = tableDemandeRefresh;

        String tableId = DEMANDE_REFRESH_TABLE_ID;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            boolean isAtraiter = false;
         /*   List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td")); */

            WebElement etat = driver.findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[4]"));
            if (etat.getText().contains("A traiter"))
            {
                isAtraiter = true;
            } else {
                break;
            }
            WebElement type_raison = driver.findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[5]"));
            if (isAtraiter && type_raison.getText().contains(raison)) {
                WebElement rowEdition = TestBase.getDriver().findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[6]/a"));
                ActionUtils.clickAndLoading(rowEdition);
                break;
            }

            rowIndex = rowIndex + 1;
        }
        SeleniumUtils.isLoading();
    }

    public void searchResultDemandeRefreshPretEnvoi(String raison){
        WebElement table = tableDemandeRefresh;

        String tableId = DEMANDE_REFRESH_TABLE_ID;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            boolean isPretEnvoi = false;
            WebElement etat = driver.findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[4]"));
            if (etat.getText().contains("Prèt pour l'envoi"))
            {
                isPretEnvoi = true;
            } else {
                break;
            }
            WebElement type_raison = driver.findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[5]"));
            if (isPretEnvoi && type_raison.getText().contains(raison)) {
                break;
            }

            rowIndex = rowIndex + 1;
        }
        SeleniumUtils.isLoading();
    }

    public String clickSearchAnneeCarriere (String annee) {
        WebElement table = tableCarrierePension;

        String tableId = TABLE_CARRIERE_PENSION_ID;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));

        int rowIndex = 1;

        for (WebElement rowElement : totalRowCount) {
            WebElement anneeTrimestreW = driver.findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[1]"));
            String anneeTrimestre = anneeTrimestreW.getText();
            annee = anneeTrimestre.substring(0, 4);
     //       trimestre = anneeTrimestre.substring(5);
            break;
        }
        return annee;
    }

    public String clickSearchTrimestreCarriere (String trimestre) {
        WebElement table = tableCarrierePension;

        String tableId = TABLE_CARRIERE_PENSION_ID;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));

        int rowIndex = 1;

        for (WebElement rowElement : totalRowCount) {
            WebElement anneeTrimestreW = driver.findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[1]"));
            String anneeTrimestre = anneeTrimestreW.getText();
            trimestre = anneeTrimestre.substring(5);
            break;
        }
        return trimestre;
    }

    public boolean isNbrLignesCarriereOK (int nbrLignes,boolean isLignes) {
        WebElement table = tableCarrierePension;

        String tableId = TABLE_CARRIERE_PENSION_ID;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));

        int rowIndex = 1;
        int nbrRow = 0;
        isLignes = false;

        for (WebElement rowElement : totalRowCount) {
            nbrRow = nbrRow + 1;

        }
        rowIndex = rowIndex + 1;
        if (nbrRow <= nbrLignes) {
            isLignes = true;
        }
        return isLignes;

    }

    public String getFromTableCotisationByAnneeTrimestre(String trimestre) {
        return TableUtils.getTextfromColumnTable(ENTITES_COMPTABLES_TABLE_ID, trimestre, "9", false);
    }

    public String getFromTableCotisationPLCByTrimestre(String trimestre) {
        return TableUtils.getTextfromColumnTable(ENTITES_COMPTABLES_TABLE_ID, trimestre, "8", true);
    }

    public String getFromTableCotisationByAnnee(String annee) {
        return TableUtils.getTextfromColumnTable(ENTITES_COMPTABLES_TABLE_ID, annee, "7", false);
    }

    public void clickAffichageSoldeElementsSoldesMajorationsRegroupees(String trimestrecontrole) {
        ActionUtils.doJsFill("periodeFrom", trimestrecontrole);
        ActionUtils.doJsFill("periodeTo", trimestrecontrole);
        ActionUtils.sendInComboWithoutLoading(selectAffichage, "Majorations/cotisations regroupées");
        clickBtnSearch();
    }

    public void fillPeriodeToSoldes(String periode) {
        ActionUtils.doJsFill("periodeTo", periode);
    }

    public void fillPeriodeFromSoldes(String periode) {
        ActionUtils.doJsFill("periodeFrom", periode);
    }

    public void selectTypeAffichageSolde(String type) {
        ActionUtils.sendInComboWithoutLoading(selectAffichage, type);
    }

    public BigDecimal getFromTableOperationCotisationByAnnee(String annee, String typeCreance, String codenature) {
        WebElement table = tableOperationComptable;

        String tableId = OPERATIONS_COMPTABLES_TABLE_ID;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));
        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                if (!TestData.AUCUN_ELEMENT_A_AFFICHER.equals(colElement.getText())) {
                    String colElemwthspace = SeleniumUtils.deleteFormat(colElement.getText());

                    Map<String, WebElement> mapOperation = makeMapFromOperationCotisation(rowIndex);

                    if (checkAnneeType(annee, colElemwthspace, mapOperation)
                            && ((codenature != null && checkOperationCreanceNature(typeCreance, codenature, mapOperation)) ||
                            checkOperationCreance(typeCreance, mapOperation))) {
                        return SeleniumUtils.transformStringToBigDecimal(driver.findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[8]")).getText());
                    }
                } else {
                    return null;
                }

                columnIndex = columnIndex + 1;
            }
            rowIndex = rowIndex + 1;
        }
        return null;
    }

    public BigDecimal getMontantOperationsFromFoot() {
        return SeleniumUtils.transformStringToBigDecimal(driver.findElement(By.xpath("//*[@id='" + OPERATIONS_COMPTABLES_TABLE_ID + "']/tfoot/tr/th[8]")).getText());
    }

    private boolean checkAnneeType(String annee, String colElemwthspace, Map<String, WebElement> mapOperation) {
        return colElemwthspace.contains(annee) && mapOperation.get(TestData.TYPE).getText().contains(TestData.COTISATION);
    }

    private boolean checkOperationCreanceNature(String typeCreance, String codenature, Map<String, WebElement> mapOperation) {
        return mapOperation.get(TestData.OPERATION).getText().contains(TestData.ENROLEMENT)
                && mapOperation.get(TestData.CREANCE).getText().equals(typeCreance)
                && mapOperation.get(TestData.NATURE).getText().equals(codenature);
    }

    private boolean checkOperationCreance(String typeCreance, Map<String, WebElement> mapOperation) {
        return mapOperation.get(TestData.OPERATION).getText().contains(TestData.ENROLEMENT)
                && mapOperation.get(TestData.CREANCE).getText().equals(typeCreance);
    }

    private Map<String, WebElement> makeMapFromOperationCotisation(int rowIndex) {
        Map<String, WebElement> map = new LinkedHashMap<>();

        map.put(TestData.TYPE, driver.findElement(By.xpath("//*[@id='" + OPERATIONS_COMPTABLES_TABLE_ID + "']/tbody/tr[" + rowIndex + "]/td[5]")));
        map.put(TestData.CREANCE, driver.findElement(By.xpath("//*[@id='" + OPERATIONS_COMPTABLES_TABLE_ID + "']/tbody/tr[" + rowIndex + "]/td[6]")));
        map.put(TestData.OPERATION, driver.findElement(By.xpath("//*[@id='" + OPERATIONS_COMPTABLES_TABLE_ID + "']/tbody/tr[" + rowIndex + "]/td[7]")));
        map.put(TestData.NATURE, driver.findElement(By.xpath("//*[@id='" + OPERATIONS_COMPTABLES_TABLE_ID + "']/tbody/tr[" + rowIndex + "]/td[9]")));

        return map;
    }

    public BigDecimal getFromTableMajorationsByAnnee(String annee, String typeMajoration) {
        WebElement table = tableOperationComptable;

        String tableId = OPERATIONS_COMPTABLES_TABLE_ID;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                String colElemwthspace = SeleniumUtils.deleteFormat(colElement.getText());

                WebElement type = driver.findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[5]"));

                WebElement typeMajo = driver.findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[6]"));

                WebElement operation = driver.findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[7]"));

                if (colElemwthspace.contains(annee)
                        && typeMajo.getText().contains(typeMajoration)
                        && type.getText().contains(TestData.MAJORATION)
                        && operation.getText().contains(TestData.ENROLEMENT)) {
                    WebElement montant = driver.findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[8]"));

                    return SeleniumUtils.transformStringToBigDecimal(montant.getText());
                }

                columnIndex = columnIndex + 1;
            }
            rowIndex = rowIndex + 1;
        }
        return null;
    }

    public Boolean isExistOperationTrimestre(String trimestre) {
        return TableUtils.isElementPresent(OPERATIONS_COMPTABLES_TABLE_ID, trimestre);
    }

    public Boolean isExistOperationCreanceEtType(String creance, String typeOperation, boolean operationPLC) {
        WebElement table = tableoperationComptable;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + OPERATIONS_COMPTABLES_TABLE_ID + "']/tbody/tr"));

        int indexOperation = 7;
        if (operationPLC) {
            indexOperation = 6;
        }

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            boolean isCreance = false;
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                if (columnIndex == 5) {
                    if (colElement.getText().equals(creance)) {
                        isCreance = true;
                    } else {
                        break;
                    }
                }
                if (columnIndex == indexOperation && isCreance && colElement.getText().contains(typeOperation)) {
                    return true;
                }
                columnIndex = columnIndex + 1;
            }
            rowIndex = rowIndex + 1;
        }
        return false;
    }

    public void deleteCotisationByAnneeTrimestre(String trimestre) {
        ActionUtils.sendInComboWithoutLoading(selectAffichage, TestData.PAS_DE_REGROUPEMENT);

        clickBtnSearch();

        String tableId = ENTITES_COMPTABLES_TABLE_ID;
        try {
            Elements trs = TableUtils.getParsedTable(tableId);
            int size = trs.size() - 2;

            for (int trindex = 1; trindex <= size; trindex++) {
                WebElement rowPeriode = TestBase.getDriver().findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + trindex + "]/td[1]"));

                WebElement rowCreance = TestBase.getDriver().findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + trindex + "]/td[2]"));

                if (SeleniumUtils.deleteFormat(rowPeriode.getText()).equals(SeleniumUtils.deleteFormat(trimestre)) &&
                        TestData.COTISATION.equals(rowCreance.getText())) {
                    WebElement clickable = driver.findElement(By.xpath("//table/tbody/tr[" + trindex + "]/td[10]/a[2]"));
                    clickable.click();
                    selectAndEnregistrerRaisonAnnulation();
                    size--;
                    trindex--;
                }
            }
        } catch (Exception e) {

        }
    }

    private void selectAndEnregistrerRaisonAnnulation() {
        ActionUtils.sendInComboWithoutLoading(selectRaisonAnnulation, "Erreur");
        ActionUtils.clickAndLoading(btnEnregistrerRaisonAnnulation);
    }

    public void fillNatureEvenement(String nature) {
        ActionUtils.sendInTextField(selectNatureEvenement, nature);
        selectNatureEvenement.sendKeys(Keys.ARROW_DOWN);
        if ("Bénéfice d'une pension".equals(nature)) {
            selectNatureEvenement.sendKeys(Keys.ARROW_DOWN);
        }
        selectNatureEvenement.sendKeys(Keys.ENTER);
        SeleniumUtils.isLoading();
    }

    public void clickProfilLigneEvenement(String testdataProfilNature) {
        if (testdataProfilNature != null) {
            TableUtils.clickElementWithClassColumnIsPresent(TABLE_PROFIL_EVENEMENTS_ID, getNatureForTableProfil(testdataProfilNature), "Editer");
        } else {
            TableUtils.clickElementWithClassColumnIsPresent(TABLE_PROFIL_EVENEMENTS_ID, "Editer");
        }
    }

    public void clickProfilSuppressionLigneEvenement(String testdataProfilNature) {
        TableUtils.clickElementWithClassColumnIsPresent(TABLE_PROFIL_EVENEMENTS_ID, getNatureForTableProfil(testdataProfilNature), "Supprimer");
    }

    private String getNatureForTableProfil(String testdataProfilNature) {
        String nature = testdataProfilNature;
        if ("Maxi-statut".equals(testdataProfilNature)) {
            nature = TestData.NATURE_PROFILE_CONJOINT_MAXI_STATUT;
        }
        return nature;
    }

    public void clickProfilSuppressionLigneEvenementMultiple(List<String> testdataProfil) {
        TableUtils.clickElementSuppressionEvtChgtMultiple("//form/div/div/div/div/div[1]/div/div/div[2]/div[2]/table[@id='profilEvenementsTableId']", testdataProfil, "10", "2");
    }

    public void fillRaisonSuppressionEvenement() {
        try {
            ActionUtils.sendInComboWithoutLoading(selectRaisonEvenementSuppression, "Déclaration sur l'honneur");
        } catch (NoSuchElementException e) {

        }
    }

    public void clickBtnOuiSupprimerEvenement() {
        ActionUtils.clickAndLoading(btnOuiSupprimerEvenement);
    }

    public void clickProfilSuppressionLigneEvenementFin() {
        TableUtils.clickElementWithClassColumnIsPresent(TABLE_PROFIL_EVENEMENTS_ID, "", "Supprimer");
    }

    public boolean isEvenementPresentProfil(String element) {
        return TableUtils.isElementPresent(TABLE_PROFIL_EVENEMENTS_ID, element);
    }

    public boolean isEvenementPresentProfil(String date, String element) {
        return TableUtils.isElementPresent(TABLE_PROFIL_EVENEMENTS_ID, date) && TableUtils.isElementPresent(TABLE_PROFIL_EVENEMENTS_ID, element);
    }

    public void clickMenuREM() {
        ActionUtils.clickAndLoading(menuREM);
    }

    public void clickMenuPLC() {
        ActionUtils.clickAndLoading(menuPLC);
    }

    public void fillReductionType(String reduc) {
        ActionUtils.sendInCombo(selectExoReduction, reduc);
    }

    public void fillReductionTypeChgtProfil(String reduc) {
        ActionUtils.sendInCombo(selectExoReduction, reduc);
    }

    public void fillEmptyReductionTypeChgtProfil() {
        ActionUtils.clickAndLoading(selectEmptyExoReduction);
    }

    public void fillEmptyJustificationChgtProfil() {
        ActionUtils.clickAndLoading(selectEmptyJustification);
    }

    public void fillJustificationRandom() {
        try {
            ActionUtils.sendInComboWithoutLoading(selectJustification, TestData.EST_MANDATAIRE_PUBLIC);
        } catch (ElementNotVisibleException e) {

        }
    }

    public void fillJustification(String justification) {
        try {
            ActionUtils.sendInComboWithoutLoading(selectJustification, justification);
        } catch (ElementNotVisibleException e) {

        }
    }

    public void fillJustificationChgtProfil() {
        try {
            ActionUtils.sendInComboWithoutLoading(selectJustification, TestData.EST_MANDATAIRE_PUBLIC);
        } catch (NoSuchElementException e) {

        }
    }

    public void clickExoRecutionArt37() {
        ActionUtils.moveToAndClickChild(menuActionGestionProfil, menuActionExoReductionArt37, driver);
    }

    public void fillDateDebutFinEvenement(String dateDebut, String dateFin) {
        ActionUtils.doJsFill(PROFIL_DATE_VALIDITE_ID, dateDebut);
        ActionUtils.doJsFill("profilDateFinValiditeId", dateFin);
    }

    public void filtreTableOperations(String creance, String typeCreance, String suspension, String recouvrement, String type) {
        filtreCreance.sendKeys(creance);
        filtreTypeCreance.sendKeys(typeCreance);
        if (TestData.TYPE_PP.equals(type)) {
            filtreSuspensionPP.sendKeys(suspension);
            filtreRecouvrementPP.sendKeys(recouvrement);
        } else {
            filtreSuspensionPM.sendKeys(suspension);
            filtreRecouvrementPM.sendKeys(recouvrement);
        }
        filtrePeriode.sendKeys("");
    }

    public String getInputSoldeTotal() {
        return inputSoldeTotal.getText();
    }

    public String getSoldeOperations() {
        return montantSoldeOperations.getText();
    }

    public void clickBtnNextOperation() {
        ActionUtils.clickAndLoading(btnNextOperations);
    }

    public void clickTableDecisionExoneration(Map<Integer, List<Boolean>> mapDecisions) {
        WebElement table = tableDecisionExo;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + "decisionsParAnnee" + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td/div/div"));
            int columnIndex = 1;
            List<Boolean> listDecision = mapDecisions.get(rowIndex);
            for (WebElement colElement : totalColumnCount) {
                int i = columnIndex + 1;
                boolean decision = listDecision.get(columnIndex - 1);
                String pathimg = "//table/tbody/tr[" + rowIndex + "]/td[" + i + "]/div/div";

                WebElement clickable = driver.findElement(By.xpath(pathimg));

                if (colElement.getAttribute("class").contains("icon-16x16-remove-red")) {
                    trtDecisionAttributeRed(decision, clickable);
                } else if (colElement.getAttribute("class").contains("icon-16x16-question-mark-grey")) {
                    trtDecisionAttributeGrey(decision, clickable);
                } else {
                    trtDecisionAttributeGreen(decision, clickable);
                }
                columnIndex = columnIndex + 1;
            }
            rowIndex = rowIndex + 1;
        }
        SeleniumUtils.isLoading();
    }

    private void trtDecisionAttributeGreen(boolean decision, WebElement clickable) {
        if (!decision) {
            clickable.click();
            SeleniumUtils.waitForActionCommon();
        }
    }

    private void trtDecisionAttributeGrey(boolean decision, WebElement clickable) {
        if (decision) {
            clickable.click();
            SeleniumUtils.waitForActionCommon();
        } else {
            clickable.click();
            SeleniumUtils.waitForActionCommon();
            clickable.click();
            SeleniumUtils.waitForActionCommon();
        }
    }

    private void trtDecisionAttributeRed(boolean decision, WebElement clickable) {
        if (decision) {
            clickable.click();
            SeleniumUtils.waitForActionCommon();
            clickable.click();
            SeleniumUtils.waitForActionCommon();
        }
    }

    public boolean isPresentTableExoPm(String statut, String explicationStatut) {
        WebElement table = tableExoPm;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + "table-exo-pm" + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            boolean isStatut = false;
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                if (columnIndex == 2) {
                    if (colElement.getText().equals(statut)) {
                        isStatut = true;
                    } else {
                        break;
                    }
                } else {
                    if (columnIndex == 3 && isStatut && colElement.getText().equals(explicationStatut)) {
                        return true;
                    }
                }
                columnIndex = columnIndex + 1;
            }
            rowIndex = rowIndex + 1;
        }
        return false;
    }

    public boolean isPresentTableMiseEnVeilleuse(String annee, String decision) {
        WebElement table = tableMev;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + "consultationVeilleuseTableId" + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            boolean isAnnee = false;
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                if (columnIndex == 1) {
                    if (colElement.getText().contains(annee)) {
                        isAnnee = true;
                    } else {
                        break;
                    }
                } else {
                    if (columnIndex == 3 && isAnnee && colElement.getText().contains(decision)) {
                        return true;
                    }
                }
                columnIndex = columnIndex + 1;
            }
            rowIndex = rowIndex + 1;
        }
        return false;
    }

    public void clickAttention(String attention) {
        switch (attention) {
            case TestData.EXO_REVISION:
                checkRevision.click();
                break;
            case TestData.EXO_DEJA_TRAITEE:
                checkDejaTraitee.click();
                break;
            case TestData.EXO_3_PREMIERES_ANNEES:
                check3PremieresAnnees.click();
                break;
            default:
                break;
        }
    }

    public void clickMenuMiseEnVeilleuse() {
        ActionUtils.clickChildFromChildMenu(menuCotisant, menuCotisantProfil, menuCotisantProfilMiseEnVeilleuse, driver);
    }

    public void clickGestionMiseEnVeilleuse() {
        ActionUtils.moveToAndClickChild(menuActionMiseEnVeilleuse, btnGestionDemandesMiseEnVeilleuse, driver);
    }

    public void clickBtnTerminerMev() {
        ActionUtils.clickAndLoading(btnTerminerMev);
    }

    public void clickBtnTerminerSignaux74LInasti() {
        ActionUtils.clickAndLoading(btnTerminerSignaux74LInasti);
    }

    public void clickBtnRechercherDemandeRefresh() {ActionUtils.clickAndLoading(btnRechercherDemandeRefresh);}

    public void clickBtnTerminerDemandeRefresh() {
        ActionUtils.clickAndLoading(btnTerminerDemandeRefresh);
    }

    public void clickBtnAjouterDemande() {
        ActionUtils.clickAndLoading(btnAjouterDemande);
    }

    public void selectBureauContribution() {
        try {
            ActionUtils.clickAndLoading(selectBureauContribution);
        } catch (Exception e) {

        }
    }

    public void fillRowMiseEnVeilleuse(int i, String annee, String dateReception, String statut, String motivation, String initiative, String sousForme) {
        setRowMiseEnVeilleuse(i, annee, dateReception, statut, motivation, initiative, sousForme);
    }

    private static void setRowMiseEnVeilleuse(int indice, String annee, String dateReception, String statut, String motivation, String initiative, String sousForme) {
        String tableId = "editionVeilleuseTableId";
        try {
            Elements trs = TableUtils.getParsedTable(tableId);
            int rowIndex = 0;
            for (Element tr : trs) {
                Elements tds = tr.getElementsByTag("td");
                int columnIndex = 1;
                while (columnIndex <= tds.size()) {
                    if (rowIndex == indice) {
                        String rown = "//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[" + columnIndex + "]";

                        switch (columnIndex) {
                            case 1:
                                if (TestBase.getDriver().findElement(By.xpath(rown + "/input[1]")).isEnabled()) {
                                    ActionUtils.doJsFill(TestBase.getDriver().findElement(By.xpath(rown + "/input[1]")).getAttribute("id"), annee);
                                }
                                break;
                            case 2:
                                if (TestBase.getDriver().findElement(By.xpath(rown + "/input[1]")).isEnabled()) {
                                    ActionUtils.doJsFill(TestBase.getDriver().findElement(By.xpath(rown + "/input[1]")).getAttribute("id"), dateReception);
                                }
                                break;
                            case 3:
                                ActionUtils.sendInCombo(TestBase.getDriver().findElement(By.xpath(rown + "/span[1]/input[1]")), statut);
                                break;
                            case 4:
                                if (motivation != null && !StringUtils.isEmpty(motivation.trim())) {
                                    ActionUtils.sendInComboWithoutLoading(TestBase.getDriver().findElement(By.xpath(rown + "/div[1]/div[1]/span[1]/input[1]")), motivation);
                                }
                                break;
                            case 5:
                                setALInitiativeDe(initiative, rown);
                                break;
                            case 6:
                                ActionUtils.sendInComboWithoutLoading(TestBase.getDriver().findElement(By.xpath(rown + "/div[1]/span[1]/input[1]")), sousForme);
                                break;
                            default:
                                break;
                        }
                    }
                    columnIndex++;
                }
                rowIndex++;
            }
        } catch (Exception e) {

        }
    }

    private static void setALInitiativeDe(String initiative, String rown) {
        WebElement row;
        try {
            row = TestBase.getDriver().findElement(By.xpath(rown + "/div[1]/span[1]/input[1]"));
        } catch (NoSuchElementException e) {
            row = TestBase.getDriver().findElement(By.xpath(rown + "/span[1]/input[1]"));
        }
        ActionUtils.sendInComboWithoutLoading(row, initiative);
    }

    public void clickMenuObservations() {
        ActionUtils.clickAndLoading(menuObservations);
    }

    public void clickAjoutObservation() {
        ActionUtils.clickChildMenu(menuActionObservation, btnAjouterObservation, driver);
    }

    public void selectTypeMediaObservation(String typeMedia) {
        ActionUtils.sendInComboWithoutLoading(typeMediaObservation, typeMedia);
    }

    public void selectTypeMediaListObservation(String typeMedia) {
        ActionUtils.sendInComboWithoutLoading(typeMediaListObservation, typeMedia);
    }

    public void selectTypeObservation(String type) {
        ActionUtils.sendInComboWithoutLoading(typeObservation, type);
    }

    public void selectTypeListObservation(String type) {
        ActionUtils.sendInComboWithoutLoading(typeListObservation, type);
    }

    public void selectSignaux74LInastiATraiter() {
        ActionUtils.sendInCombo(selectSignaux74LATraiter, "A traiter");
    }

    public void clickBtnSearchObservations() {
        ActionUtils.clickAndLoading(btnSearchObservations);
    }

    public void clickAccordeonProfilAcc() {
        ActionUtils.clickAndLoading(accordionProfilAcc);
    }

    public void fillReferenceObservation(String reference) {
        ActionUtils.sendInTextField(referenceObservation, reference);
    }

    public void fillCommentaireObservation(String commentaire) {
        ActionUtils.sendInTextField(commentaireObservation, commentaire);
    }

    public void clickBtnEnregistrerObservation() {
        ActionUtils.clickAndLoading(btnEnregistrerObservation);
    }

    public void clickAccordeonObservationsArchivees() {
        ActionUtils.clickAndLoading(accordionTableArchive);
    }

    public Boolean isObservationPresente(String typeMedia, String type, boolean archivee) {
        try {
            String tableId = TABLE_OBSERVATIONS_ID;
            int indexType = 3;
            if (archivee) {
                tableId = TABLE_OBSERVATIONS_ARCHIVEES_ID;
                indexType = 4;
            }
            Elements trs = TableUtils.getParsedTable(tableId);
            int trindex = 0;

            for (Element tr : trs) {
                Elements tds = tr.getElementsByTag("td");

                boolean isTypeMedia = false;
                int tdIndex = 1;
                while (tdIndex <= tds.size()) {
                    WebElement row = TestBase.getDriver().findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + trindex + "]/td[" + tdIndex + "]"));

                    if (tdIndex == 1 && row.getAttribute("original-title").equals(typeMedia)) {
                        isTypeMedia = true;
                    }
                    if (tdIndex == indexType && row.getText().equals(type) && isTypeMedia) {
                        return true;
                    }
                    tdIndex++;
                }
                trindex++;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickEditionObservationPresente() {
        try {
            WebElement row = TestBase.getDriver().findElement(By.xpath("//*[@id='" + TABLE_OBSERVATIONS_ID + "']/tbody/tr[1]/td[7]/a[2]"));
            ActionUtils.clickAndLoading(row);
        } catch (Exception e) {

        }
    }

    public void clickArchivageObservationPresente(String typeMedia, String type) {
        try {
            String tableId = TABLE_OBSERVATIONS_ID;
            Elements trs = TableUtils.getParsedTable(tableId);
            int trindex = 0;

            for (Element tr : trs) {
                Elements tds = tr.getElementsByTag("td");

                boolean isTypeMedia = false;
                int tdIndex = 1;
                while (tdIndex <= tds.size()) {
                    String rown = "//*[@id='" + tableId + "']/tbody/tr[" + trindex + "]/td[" + tdIndex + "]";

                    WebElement row = TestBase.getDriver().findElement(By.xpath(rown));

                    if (tdIndex == 1 && row.getAttribute("original-title").equals(typeMedia)) {
                        isTypeMedia = true;
                    }
                    if (tdIndex == 3 && row.getText().equals(type) && isTypeMedia) {
                        row = TestBase.getDriver().findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + trindex + "]/td[7]/a[1]"));
                        row.click();
                        return;
                    }
                    tdIndex++;
                }
                trindex++;
            }
        } catch (Exception e) {

        }
    }

    public void clickAlertConfirmArchivageObservation() {
        SeleniumUtils.isLoading();
        driver.switchTo();
        ActionUtils.clickAndLoading(confirmArchivage);
    }

    public void clickPasserEnIrrecouvrableProvisoire() {
        ActionUtils.moveToAndClickChild(menuActionIrrecouvrables, passerIrrecouvrableProvisoire, driver);
    }

    public void clickPasserEnIrrecouvrableDefinitif() {
        ActionUtils.moveToAndClickChild(menuActionIrrecouvrables, passerIrrecouvrableDefinitif, driver);
    }

    public void selectMotifIrrecouvrable(String motif) {
        ActionUtils.sendInComboWithoutLoading(motifIrrecouvrable, motif);
    }

    public void fillCommentaireIrrecouvrable(String commentaire) {
        ActionUtils.sendInTextField(commentaireIrrecouvrable, commentaire);
    }

    public void checkEntitesComptablesIrrecouvrable(String periode) {
        String tableId = "//div[2]/div[2]/table";

        List<WebElement> elemtable = TestBase.getDriver().findElements(By.xpath(tableId + "/tbody/tr"));

        WebElement row;

        int trindex = 1;

        for (int j = 0; j < elemtable.size(); j++) {
            String rown = tableId + "/tbody/tr[" + trindex + "]/td[2]";
            row = TestBase.getDriver().findElement(By.xpath(rown));
            if (row.getText().equals(periode)) {
                rown = tableId + "/tbody/tr[" + trindex + "]/td[1]/input";
                row = TestBase.getDriver().findElement(By.xpath(rown));
                row.click();
            }
            trindex++;
        }
    }

    public void checkEntitesComptablesSuiviIrrecouvrable(String periode) {
        String tableId = ENTITES_COMPTABLES_TABLE_ID;
        Elements trs = TableUtils.getParsedTable(tableId);
        iterateToTable(trs, periode, tableId);
    }

    private void iterateToTable(Elements trs, String periode, String tableId) {
        int trindex = 0;
        for (Element tr : trs) {
            Elements tds = tr.getElementsByTag("td");

            for (Element td : tds) {
                if (td.text().contains(periode)) {
                    String rown = "//*[@id='" + tableId + "']/tbody/tr[" + trindex + "]/td[1]/input";
                    WebElement row = TestBase.getDriver().findElement(By.xpath(rown));
                    row.click();
                }
            }
            trindex++;
        }
    }

    public void clickBtnEditionDatePrescriptionIrrecouvrable() {
        ActionUtils.clickAndLoading(btnDatePrescriptionIrrecouvrable);
    }

    public void clickBtnEnregistrerDatePrescriptionIrrecouvrable() {
        ActionUtils.clickAndLoading(btnEnregistrerDatePrescriptionIrrecouvrable);
    }

    public void clickEnvoiLettreOuiIrrecouvrable() {
        envoiLettreOuiIrrecouvrable.click();
    }

    public void clickEnvoiLettreNonIrrecouvrable() {
        envoiLettreNonIrrecouvrable.click();
    }

    public void clickEnvoiLettreContentieuxIrrecouvrable() {
        envoiLettreContentieuxIrrecouvrable.click();
    }

    public void clickBtnAnnulationIrrecouvrable() {
        ActionUtils.clickAndLoading(btnAnnulationIrrecouvrable);
    }

    public void clickBtnModificationIrrecouvrable() {
        ActionUtils.clickAndLoading(btnModificationIrrecouvrable);
    }

    public void clickBtnSimulationIrrecouvrable() {
        ActionUtils.clickAndLoading(btnSimulationIrrecouvrable);
    }

    public void clickRedebitionMajorationsOuiIrrecouvrable() {
        redebitionMajorationsOuiIrrecouvrable.click();
    }

    public void clickRedebitionMajorationsNonIrrecouvrable() {
        redebitionMajorationsNonIrrecouvrable.click();
    }

    public void clickBtnEnregistrerRedebitionIrrecouvrable() {
        ActionUtils.clickAndLoading(btnEnregistrerRedebitionIrrecouvrable);
    }

    public void fillMontantAAnnulerIrrecouvrable() {
        ActionUtils.sendInTextField(montantAAnnulerIrrecouvrable, "100.00");
    }

    public void clickBtnEnregistrerSuppressionPartielleIrrecouvrable() {
        ActionUtils.clickAndLoading(btnEnregistrerSuppressionPartielleIrrecouvrable);
    }

    public void fillDatePrescriptionModificationIrrecouvrable(String date) {
        ActionUtils.doJsFill("modificationIrrec_irrecouvrableDto_datePrescription", date);
    }

    public boolean isPresentTableIrrecouvrable(String periode, String type, String motif) {
        return ActionUtils.iterateToTable(entitesComptablesTable, ENTITES_COMPTABLES_TABLE_ID, 1, 5, 6, periode, type, motif);
    }

    public boolean isPresentTableSuiviIrrecouvrable(String periode, String type, String motif) {
        WebElement table = entitesComptablesTable;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + ENTITES_COMPTABLES_TABLE_ID + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            boolean isTypeIrrec = false;
            boolean isPeriode = false;
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                if (columnIndex == 2) {
                    if (colElement.getText().equals(periode)) {
                        isPeriode = true;
                    } else {
                        break;
                    }
                }
                if (columnIndex == 6 && isPeriode) {
                    if (colElement.getText().equals(type)) {
                        isTypeIrrec = true;
                    } else {
                        break;
                    }
                } else {
                    if (columnIndex == 7 && isTypeIrrec && colElement.getText().equals(motif)) {
                        return true;
                    }
                }
                columnIndex = columnIndex + 1;
            }
            rowIndex = rowIndex + 1;
        }
        return false;
    }

    public void clickProvisoireIrrecouvrable() {
        ActionUtils.clickAndLoading(provisoireIrrecouvrable);
    }

    public void clickDefinitifIrrecouvrable() {
        ActionUtils.clickAndLoading(definitifIrrecouvrable);
    }

    public void fillDateRedebitionMajorationsIrrecouvrable(String date) {
        ActionUtils.doJsFill("annulationIrrec_dateRedebition", date);
    }

    public void selectMotifModificationIrrecouvrable(String motif) {
        ActionUtils.sendInComboWithoutLoading(motifModificationIrrecouvrable, motif);
    }

    public void fillCommentaireModificationIrrecouvrable(String commentaire) {
        ActionUtils.sendInTextField(commentaireModificationIrrecouvrable, commentaire);
    }

    public void clickBtnEnregistrerModificationIrrecouvrable() {
        ActionUtils.clickAndLoading(btnEnregistrerModificationIrrecouvrable);
    }

    public void clickBtnDatePrescription() {
        ActionUtils.clickAndLoading(btnDatePrescription);
    }

    public void clickBtnPrescrirePrescription() {
        ActionUtils.clickAndLoading(btnPrescrirePrescription);
    }

    public void fillMontantPartielPrescription() {
        ActionUtils.sendInTextField(montantPartielPrescription, "100.00");
    }

    public void fillMontantPartielAnnulationPrescription() {
        ActionUtils.sendInTextField(montantPartielAnnulationPrescription, "50.00");
    }

    public void clickBtnEnregistrerAnnulationPrescription() {
        ActionUtils.clickAndLoading(btnEnregistrerAnnulationPrescription);
    }

    public void clickSupprimerEntiteComptablePrescripte(String creance) {
        TableUtils.clickElementWithClassColumnIsPresent(TABLE_ENTITE_COMPTABLES_PRESCRITES_ID, creance, "Supprimer partiellement");
    }

    public void checkEntiteComptablePrescripte(String periode, String column, String id) {
        TableUtils.clickElementColumnIsPresent(TABLE_ENTITE_COMPTABLES_PRESCRITES_ID, periode, column, id);
    }

    public void selectTypePrescription(String type) {
        ActionUtils.sendInComboWithoutLoading(selectTypePassagePartielPrescription, type);
    }

    public void clickBtnEnregistrerPassagePrescription() {
        btnEnregistrerPassagePrescription.click();
    }

    public void clickBtnEnregistrerDatePrescription() {
        ActionUtils.clickAndLoading(btnEnregistrerDatePrescription);
    }

    public void clickBtnSupprimerPrescription() {
        ActionUtils.clickAndLoading(btnSupprimerPrescription);
    }

    public void clickBtnAjouterActeInterruptifPrescription() {
        ActionUtils.clickAndLoading(btnAjouterActeInterruptifPrescription);
    }

    public void selectTypeActeInterruptifPrescription(String type) {
        ActionUtils.sendInComboWithoutLoading(typeActeInterruptifPrescription, type);
    }

    public void clickBtnEnregistrerActeInterruptifPrescription() {
        ActionUtils.clickAndLoading(btnEnregistrerActeInterruptifPrescription);
    }

    public boolean checkActeInterruptif(String typePrescription) {
        return TableUtils.isElementPresent(ACTES_TABLE_ID, typePrescription);
    }

    public void clickSuppressionActeInterruptif() {
        TableUtils.clickElementWithClassColumnIsPresent(ACTES_TABLE_ID, "Supprimer");
    }

    public boolean checkTableActeInterruptifVide() {
        return TableUtils.isTableVide(ACTES_TABLE_ID);
    }

    public void clickBtnEnregistrerModificationPrescription() {
        ActionUtils.clickAndLoading(btnEnregistrerModificationPrescription);
    }

    public void selectModificationTypePrescription(String type) {
        ActionUtils.sendInComboWithoutLoading(selectModificationTypePrescription, type);
    }

    public void fillDatePrescription(String date) {
        ActionUtils.doJsFill("datePrescription", date);
    }

    public void fillDateActeInterruptifPrescription(String date) {
        ActionUtils.doJsFill("addActeInterruptifForm_dateActe", date);
    }

    public void checkEntitesComptablesSoldeesPrescription(String periode) {
        String tableId = "entitesNonSoldeesTable";

        Elements trs = TableUtils.getParsedTable(tableId);

        iterateToTable(trs, periode, tableId);
    }

    public boolean isPresentTableSuiviPrescription(String periode, String typePrescription) {
        WebElement table = entiteComptablesPrescritesTable;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + TABLE_ENTITE_COMPTABLES_PRESCRITES_ID + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            boolean isPeriode = false;
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                if (columnIndex == 2) {
                    if (colElement.getText().equals(periode)) {
                        isPeriode = true;
                    } else {
                        break;
                    }
                }
                if ((columnIndex == 7 || columnIndex == 8) && isPeriode && colElement.getText().equals(typePrescription)) {
                    return true;
                }
                columnIndex = columnIndex + 1;
            }
            rowIndex = rowIndex + 1;
        }
        return false;
    }

    public void checkEntitesComptablesSuiviPrescription(String periode) {
        String tableId = TABLE_ENTITE_COMPTABLES_PRESCRITES_ID;
        Elements trs = TableUtils.getParsedTable(tableId);
        iterateToTable(trs, periode, tableId);
    }

    public boolean checkTableEntiteNonSoldeesVide() {
        return TableUtils.isTableVide("entitesNonSoldeesTable");
    }

    public boolean checkTableEntiteComptablePrescripteVide() {
        return TableUtils.isTableVide(TABLE_ENTITE_COMPTABLES_PRESCRITES_ID);
    }

    public void clickMenuLeveeMajorations() {
        ActionUtils.clickChildFromChildMenu(menuRecouvrement, menuRecouvrementAccompagnement, menuRecouvrementAccompagnementLeveeMajorations, driver);
    }

    public void clickAjouterDemandeLM() {
        ActionUtils.clickChildMenu(menuActionLM, menuActionAjouterLM, driver);
    }

    public void clickModifierDemandeLM() {
        ActionUtils.clickChildMenu(menuActionDetailLM, menuActionModifierLM, driver);
    }

    public void clickValiderDemandeLM() {
        ActionUtils.clickChildMenu(menuActionDetailLM, menuActionValiderLM, driver);
    }

    public void selectOrigineDemandeLM(String origineDemande) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineDemandeLM, origineDemande);
    }

    public void selectDemandeRecueParLM(String demandeRecuePar) {
        ActionUtils.sendInComboWithoutLoading(selectDemandeRecueParLM, demandeRecuePar);
    }

    public void selectTypeDemandeLM(String typeDemande) {
        ActionUtils.sendInComboWithoutLoading(selectTypeDemandeLM, typeDemande);
    }

    public void fillDateEvenementLM(String date) {
        ActionUtils.doJsFill("wizardCreateLmForm1_model_dateEvenement", date);
    }

    public void checkCodebiteurSolidaireLM() {
        checkCodebiteurSolidaireLM.click();
    }

    public void checkReponseATiersLM() {
        checkReponseATiersLM.click();
    }

    public void checkMediateurLM() {
        checkMediateurLM.click();
    }

    public void fillNissTiersLM(String nissTiers) {
        ActionUtils.sendInTextField(nissTiersLM, nissTiers);
    }

    public void clickBtnRechercherTiersLM() {
        btnRechercherTiersLM.click();
    }

    public void clickBtnRechercherIdentiteTiersLM() {
        ActionUtils.clickAndLoading(btnRechercherIdentiteTiersLM);
    }

    public void clickBtnEnregistrerTiersLM() {
        ActionUtils.clickAndLoading(btnEnregistrerTiersLM);
    }

    public void clickOngletAdresseTiersLM() {
        ongletAdresseTiersLM.click();
    }

    public void fillPeriodeAnneeFromLM(String annee) {
        ActionUtils.sendInTextField(periodeAnneeFromLM, annee);
    }

    public void fillPeriodeTrimestreFromLM(String trimestre) {
        ActionUtils.sendInTextField(periodeTrimestreFromLM, trimestre);
    }

    public void fillPeriodeAnneeToLM(String annee) {
        ActionUtils.sendInTextField(periodeAnneeToLM, annee);
    }

    public void fillPeriodeAnneeToLMPM(String annee) {
        ActionUtils.sendInTextField(periodeAnneeToLMPM, annee);
    }

    public void fillPeriodeTrimestreToLM(String trimestre) {
        ActionUtils.sendInTextField(periodeTrimestreToLM, trimestre);
    }

    public void clickBtnRechercherMajorationsLM() {
        ActionUtils.clickAndLoading(btnRechercherMajorationsLM);
    }

    public void clickBtnViderMajorationsLM() {
        ActionUtils.clickAndLoading(btnViderMajorationsLM);
    }

    public void clickBtnAjouterMajorationsLM() {
        ActionUtils.clickAndLoading(btnAjouterMajorationsLM);
    }

    public void checkPaiementEnAttenteLM() {
        checkPaiementEnAttenteLM.click();
    }

    public void fillPostScriptumLM(String ps) {
        ActionUtils.sendInTextField(postScriptumLM, ps);
    }

    public void selectTypeDocumentLM(String typeDocument) {
        ActionUtils.sendInComboWithoutLoading(selectTypeDocumentLM, typeDocument);
    }

    public void clickBtnAjouterDocumentLM() {
        ActionUtils.clickAndLoading(btnAjouterDocumentLM);
    }

    public void fillPathDocumentLM(String path) {
        selectionnerDocumentLM.sendKeys(path);
        SeleniumUtils.waitForActionCommon();
    }

    public void clickBtnEnregistrerDocumentLM() {
        ActionUtils.clickAndLoading(btnEnregistrerDocumentLM);
    }

    public void checkAnnulationDebitionErroneeLM() {
        checkAnnulationDebitionErroneeLM.click();
    }

    public Map<String, List<String>> getPeriodeMajorationsPPLM() {
        Map<String, List<String>> listMajorations = new LinkedHashMap<>();

        Elements trs = TableUtils.getParsedTable(TABLE_MAJORATIONS_PP_ID);
        try {
            for (int rowIndex = 1; rowIndex < trs.size() - 1; rowIndex++) {
                if (rowIndex < trs.size()) {
                    WebElement cellTrim = driver.findElement(By.xpath("//*[@id='" + TABLE_MAJORATIONS_PP_ID + "']/tbody/tr[" + rowIndex + "]/td[1]"));

                    WebElement cellMaj = driver.findElement(By.xpath("//*[@id='" + TABLE_MAJORATIONS_PP_ID + "']/tbody/tr[" + rowIndex + "]/td[10]"));

                    WebElement cellType = driver.findElement(By.xpath("//*[@id='" + TABLE_MAJORATIONS_PP_ID + "']/tbody/tr[" + rowIndex + "]/td[2]"));

                    List<String> majorationsImpayees = new ArrayList<>();
                    majorationsImpayees.add(cellMaj.getText());
                    majorationsImpayees.add(cellType.getText());

                    listMajorations.put(cellTrim.getText(), majorationsImpayees);
                }
            }
            return listMajorations;
        } catch (Exception e) {
            return listMajorations;
        }
    }

    public Map<String, List<String>> getPeriodeMajorationsPMLM() {
        Map<String, List<String>> listMajorations = new LinkedHashMap<>();

        Elements trs = TableUtils.getParsedTable(TABLE_MAJORATIONS_PM_ID);
        try {
            for (int rowIndex = 1; rowIndex < trs.size() - 1; rowIndex++) {
                if (rowIndex < trs.size()) {
                    WebElement cellAnnee = driver.findElement(By.xpath("//*[@id='" + TABLE_MAJORATIONS_PM_ID + "']/tbody/tr[" + rowIndex + "]/td[1]"));

                    WebElement cellMaj = driver.findElement(By.xpath("//*[@id='" + TABLE_MAJORATIONS_PM_ID + "']/tbody/tr[" + rowIndex + "]/td[6]"));

                    List<String> majorationsImpayees = new ArrayList<>();
                    majorationsImpayees.add(cellMaj.getText());

                    listMajorations.put(cellAnnee.getText(), majorationsImpayees);
                }
            }
            return listMajorations;
        } catch (Exception e) {
            return listMajorations;
        }
    }

    public boolean checkIfCotisationsImpayeesLM(String type) {
        Elements trs = TableUtils.getParsedTable(TABLE_MAJORATIONS_PP_ID);
        int indexMontant = 6;
        if (type.equals(TestData.TYPE_PM)) {
            trs = TableUtils.getParsedTable(TABLE_MAJORATIONS_PM_ID);
            indexMontant = 3;
        }
        try {
            int trindex = 0;
            for (Element tr : trs) {
                Elements tds = tr.getElementsByTag("td");
                int tdindex = 1;
                if (trindex > 0) {
                    for (Element td : tds) {
                        if (tdindex == indexMontant && !"0,00".equals(td.text().trim())) {
                            return true;
                        }
                        tdindex++;
                    }
                }

                trindex++;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEnTraitementEtExplicationLM(String typeDemande, String statut, String explication) {
        WebElement table = leveeMajorationTable;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + TABLE_LEVEE_MAJORATION_ID + "']/tbody/tr"));

        int rowIndex = 1;
        while (rowIndex <= totalRowCount.size()) {
            WebElement cellType = driver.findElement(By.xpath("//*[@id='" + TABLE_LEVEE_MAJORATION_ID + "']/tbody/tr[" + rowIndex + "]/td[1]"));

            WebElement cellStatut = driver.findElement(By.xpath("//*[@id='" + TABLE_LEVEE_MAJORATION_ID + "']/tbody/tr[" + rowIndex + "]/td[3]"));

            WebElement cellExplication = driver.findElement(By.xpath("//*[@id='" + TABLE_LEVEE_MAJORATION_ID + "']/tbody/tr[" + rowIndex + "]/td[4]"));

            if (cellType.getText().trim().equals(typeDemande) && cellStatut.getText().equals(statut) && cellExplication.getText().equals(explication)) {
                return true;
            }
            rowIndex = rowIndex + 1;
        }
        return false;
    }

    public void clickEncodageLeveeMajoration() {
        TableUtils.clickElementColumnIsPresent(TABLE_LEVEE_MAJORATION_ID, "Encodage", "9", "2");
    }

    public boolean isSuspensionPP(String suspension) {
        WebElement cellSuspension = driver.findElement(By.xpath("//*[@id='" + ENTITES_COMPTABLES_TABLE_ID + "']/tbody/tr[1]/td[8]"));
        return cellSuspension.getText().equals(suspension);
    }

    public boolean isRecouvrementPP(String recouvrement) {
        WebElement cellRecouvrement = driver.findElement(By.xpath("//*[@id='" + ENTITES_COMPTABLES_TABLE_ID + "']/tbody/tr[1]/td[5]"));
        return cellRecouvrement.getText().contains(recouvrement);
    }

    public boolean isSuspensionPM(String suspension) {
        WebElement cellSuspension = driver.findElement(By.xpath("//*[@id='" + ENTITES_COMPTABLES_TABLE_ID + "']/tbody/tr[1]/td[6]"));
        return cellSuspension.getText().equals(suspension);
    }

    public boolean isRecouvrementPM(String recouvrement) {
        WebElement cellRecouvrement = driver.findElement(By.xpath("//*[@id='" + ENTITES_COMPTABLES_TABLE_ID + "']/tbody/tr[1]/td[4]"));
        return cellRecouvrement.getText().equals(recouvrement);
    }

    public boolean isTableOperationVide() {
        WebElement row = driver.findElement(By.xpath("//*[@id='operationsComptablesTable']/tbody/tr/td"));
        return TestData.AUCUN_ELEMENT_A_AFFICHER.equals(row.getText());
    }

    public void clickBtnEnregistrerModificationLM() {
        ActionUtils.clickAndLoading(btnEnregistrerModificationLM);
    }

    public void clickBtnBackLM() {
        ActionUtils.clickAndLoading(btnBackLM);
    }

    public void clickMenuAjouterPA() {
        ActionUtils.clickChildMenu(menuActionPA, menuActionAjouterPA, driver);
    }

    public void checkIdentitePA() {
        checkIdentitePA.click();
    }

    public void clickRadioLMRecuePA() {
        radioLMRecuePA.click();
    }

    public void fillDatePaiementReportPA(String date) {
        ActionUtils.doJsFill("paDatePostponePaymentId", date);
    }

    public void clickCheckLettreConfirmationPA() {
        checkLettreConfirmationReportPA.click();
    }

    public void clickRadioEchelonnementPA() {
        radioEchelonnementPA.click();
    }

    public void clickCheckCalculAutoMensualitePA() {
        checkCalculAutoMensualitePA.click();
    }

    public void fillAcomptePA(String acompte) {
        ActionUtils.doJsFill("paDownPaymentId", acompte);
        ActionUtils.refreshElementJS(acomptePA);
    }

    public void fillDateEcheanceAcomptePA(String date) {
        ActionUtils.doJsFill("paDateDownPaymentId", date);
    }

    public void fillNombreMensualitesPA(String nombre) {
        ActionUtils.sendInTextField(nombreMensualitesPA, nombre);
    }

    public void fillDatePremiereMensualitePA(String date) {
        ActionUtils.doJsFill("paDateFirstMonthlyPaymentId", date);
    }

    public void selectTypeObservationPA(String typeObservation) {
        ActionUtils.sendInComboWithoutLoading(selectTypeObservationPA, typeObservation);
    }

    public void fillObservationsPA(String observation) {
        ActionUtils.sendInTextField(observationPA, observation);
    }

    public boolean isPresentTablePlanApurement(String type, String statut, String raison) {
        return ActionUtils.iterateToTable(tableHistoriquePlanApurement, HISTORIQUE_PLAN_APUREMENT_ID, 3, 4, 5, type, statut, raison);
    }

    public String getTotalPA() {
        return SeleniumUtils.deleteFormat(totalPA.getAttribute(TestData.VALUE));
    }

    public void checkSoldesPeriodiquesPA(String periode) {
        WebElement table = tableSoldePeriodiquePA;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + "TablePaSoldePeriodique" + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            boolean isPeriode = false;
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                if (columnIndex == 2) {
                    if (colElement.getText().equals(periode)) {
                        isPeriode = true;
                    } else {
                        break;
                    }
                }
                if (columnIndex == 3 && isPeriode) {
                    String rown = "//*[@id='" + "TablePaSoldePeriodique" + "']/tbody/tr[" + rowIndex + "]/td[1]/input";
                    WebElement row = TestBase.getDriver().findElement(By.xpath(rown));
                    row.click();
                }
                columnIndex = columnIndex + 1;
            }
            rowIndex = rowIndex + 1;
        }
    }

    public void clickMenuCessationAvecREM() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnCessationAvecREM, driver);
    }

    public void clickMenuCessationSansREM() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnCessationSansREM, driver);
    }

    public void clickMenuCessationModifier() {
        ActionUtils.moveToAndClickChild(btnMenuActions, btnCessationModifier, driver);
    }

    public void clickMenuCessationAnnuler() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnCessationAnnuler, driver);
    }

    public void clickMenuCessationAjouterObservation() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnCessationAjouterObservation, driver);
    }

    public void selectOrigineREMCessation(String origine) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineREMCessation, origine);
    }

    public void selectRecuParREMCessation(String recuPar) {
        ActionUtils.sendInComboWithoutLoading(selectRecuParREMCessation, recuPar);
    }

    public void fillDateDemandeREMCessation(String date) {
        ActionUtils.doJsFill(DATE_DEMANDE_REM_ID, date);
    }

    public void fillDateCessationREMCessation(String date) {
        ActionUtils.doJsFill(DATE_CESSATION_REM_ID, date);
    }

    public void selectOrigineModificationCessation(String origine) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineModificationCessation, origine);
    }

    public void selectRecuParModificationCessation(String recuPar) {
        ActionUtils.sendInComboWithoutLoading(selectRecuParModificationCessation, recuPar);
    }

    public void selectOrigineCessation(String origine) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineCessation, origine);
    }

    public void selectRecuParCessation(String recuPar) {
        ActionUtils.sendInComboWithoutLoading(selectRecuParCessation, recuPar);
    }

    public void fillCommentaireAnnulationCessation(String commentaire) {
        ActionUtils.sendInComboWithoutLoading(commentaireAnnulationCessation, commentaire);
    }

    public void fillDateDemandeCessation(String date) {
        ActionUtils.doJsFill(DATE_DEMANDE_ID, date);
    }

    public void fillDateCessationCessation(String date) {
        ActionUtils.doJsFill(DATE_REELLE_CESSATION_ID, date);
    }

    public void checkAnnulationTrimestrePensionCessation() {
        checkAnnulationTrimestrePensionCessation.click();
    }

    public void checkCourrierCessation() {
        checkCourrierCessation.click();
    }

    public void checkCourrierNonAssujettissement() {
        checkCourrierNonAssujettissement.click();
    }

    public void checkDeclarationHonneurCessation() {
        checkDeclarationHonneurCessation.click();
    }

    public void checkDirigeantEntrepriseCessation() {
        checkDirigeantEntrepriseCessation.click();
    }

    public void checkDroitsSociauxCessation() {
        checkDroitsSociauxCessation.click();
    }

    public void checkCessationConjointeCessation() {
        checkCessationConjointeCessation.click();
    }

    public void fillReferenceObservationCessation(String reference) {
        ActionUtils.sendInTextField(referenceObservationCessation, reference);
    }

    public void clickBtnEnregistrerObservationCessation() {
        ActionUtils.clickAndLoading(btnEnregistrerObservationCessation);
    }

    public void fillCommentaireObservationCessation(String commentaire) {
        ActionUtils.sendInTextField(commentaireObservationCessation, commentaire);
    }

    public void selectTypeMediaObservationCessation(String typeMedia) {
        ActionUtils.sendInComboWithoutLoading(selectTypeMediaObservationCessation, typeMedia);
    }

    public void clickMenuSuccessionIntroduireDeces() {
        ActionUtils.moveToAndClickChild(btnMenuActions, btnSuccessionIntroduireDeces, driver);
    }

    public void clickMenuSuccessionAjouterObservation() {
        ActionUtils.moveToAndClickChild(btnMenuActions, btnSuccessionAjouterObservation, driver);
    }

    public void clickBtnRadioDateDecesSignaletiqueSuccession() {
        btnRadioDateDecesSignaletiqueSuccession.click();
    }

    public void clickBtnRadioDateDecesAutreSuccession() {
        btnRadioDateDecesAutreSuccession.click();
    }

    public void fillDateDecesAutreSuccession(String date) {
        ActionUtils.doJsFill("dateDecesAutre", date);
    }

    public void clickBtnEnregistrerObservationSuccession() {
        ActionUtils.clickAndLoading(btnEnregistrerObservationSuccession);
    }

    public void fillCommentaireObservationSuccession(String commentaire) {
        ActionUtils.sendInTextField(commentaireObservationSuccession, commentaire);
    }

    public void selectTypeMediaObservationSuccession(String typeMedia) {
        ActionUtils.sendInComboWithoutLoading(selectTypeMediaObservationSuccession, typeMedia);
    }

    public void clickMenuNonAssujettissementAvecREM() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnNonAssujettissementAvecREM, driver);
    }

    public void clickMenuNonAssujettissementSansREM() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnNonAssujettissementSansREM, driver);
    }

    public void clickMenuNonAssujettissementAjouterObservation() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnNonAssujettissementAjouterObservation, driver);
    }

    public void selectOrigineREMNonAssujettissement(String origine) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineREMNonAssujettissement, origine);
    }

    public String getDateDebutActiviteProfil() {
        return dateDebutActiviteProfil.getText();
    }

    public void selectRecuParREMNonAssujettissement(String recuPar) {
        ActionUtils.sendInComboWithoutLoading(selectRecuParREMNonAssujettissement, recuPar);
    }

    public void fillDateDemandeREMNonAssujettissement(String date) {
        ActionUtils.doJsFill(DATE_DEMANDE_REM_ID, date);
    }

    public void fillDateAffiliationASupprimerREMNonAssujettissement(String date) {
        ActionUtils.doJsFill("dateAffiASupprimerRem", date);
    }

    public void selectOrigineNonAssujettissement(String origine) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineNonAssujettissement, origine);
    }

    public void selectRecuParNonAssujettissement(String recuPar) {
        ActionUtils.sendInComboWithoutLoading(selectRecuParNonAssujettissement, recuPar);
    }

    public void fillDateDemandeNonAssujettissement(String date) {
        ActionUtils.doJsFill(DATE_DEMANDE_ID, date);
    }

    public void fillDateAffiliationASupprimerNonAssujettissement(String date) {
        ActionUtils.doJsFill("dateAffiASupprimer", date);
    }

    public void checkPasAffiliationFictiveNonAssujettissement() {
        checkPasAffiliationFictiveNonAssujettissement.click();
    }

    public void checkRadiationApresAffiliationNonAssujettissement() {
        checkRadiationApresAffiliationNonAssujettissement.click();
    }

    public void checkNonRenvoiFormaulationAffiliationNonAssujettissement() {
        checkNonRenvoiFormaulationAffiliationNonAssujettissement.click();
    }

    public void fillReferenceObservationNonAssujettissement(String reference) {
        ActionUtils.sendInTextField(referenceObservationNonAssujettissement, reference);
    }

    public void clickBtnEnregistrerObservationNonAssujettissement() {
        ActionUtils.clickAndLoading(btnEnregistrerObservationNonAssujettissement);
    }

    public void fillCommentaireObservationNonAssujettissement(String commentaire) {
        ActionUtils.sendInTextField(commentaireObservationNonAssujettissement, commentaire);
    }

    public void selectTypeMediaObservationNonAssujettissement(String typeMedia) {
        ActionUtils.sendInComboWithoutLoading(selectTypeMediaObservationNonAssujettissement, typeMedia);
    }

    public void clickMenuMandatGratuitAvecREM() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnMandatGratuitAvecREM, driver);
    }

    public void clickMenuMandatGratuitAvecCessation() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnMandatGratuitAvecCessation, driver);
    }

    public void clickMenuMandatGratuitAjouterObservation() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnMandatGratuitAjouterObservation, driver);
    }

    public void selectOrigineREMMandatGratuit(String origine) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineREMMandatGratuit, origine);
    }

    public void selectRecuParREMMandatGratuit(String recuPar) {
        ActionUtils.sendInComboWithoutLoading(selectRecuParREMMandatGratuit, recuPar);
    }

    public void fillDateDemandeREMMandatGratuit(String date) {
        ActionUtils.doJsFill(DATE_DEMANDE_REM_ID, date);
    }

    public void fillDateMandatREMMandatGratuit(String date) {
        ActionUtils.doJsFill("dateMandatGratuitRem", date);
    }

    public void selectOrigineMandatGratuit(String origine) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineMandatGratuit, origine);
    }

    public void selectRecuParMandatGratuit(String recuPar) {
        ActionUtils.sendInComboWithoutLoading(selectRecuParMandatGratuit, recuPar);
    }

    public void fillDateDemandeMandatGratuit(String date) {
        ActionUtils.doJsFill(DATE_DEMANDE_ID, date);
    }

    public void fillDateCessationMandatGratuit(String date) {
        ActionUtils.doJsFill(DATE_REELLE_CESSATION_ID, date);
    }

    public void selectDecisionMandatGratuit(String decision) {
        ActionUtils.sendInComboWithoutLoading(selectDecisionMandatGratuit, decision);
    }

    public void selectRaisonRefusMandatGratuit(String raisonRefus) {
        ActionUtils.sendInComboWithoutLoading(selectRaisonRefusMandatGratuit, raisonRefus);
    }

    public void fillCommentaireAutreRaisonRefusMandatGratuit(String commentaire) {
        ActionUtils.sendInTextField(commentaireAutreRaisonRefusMandatGratuit, commentaire);
    }

    public void checkAnnulationTrimestrePensionMandatGratuit() {
        checkAnnulationTrimestrePensionMandatGratuit.click();
    }

    public void checkCourrierMandatGratuit() {
        checkCourrierMandatGratuit.click();
    }

    public void checkDifficultePaiementMandatGratuit() {
        checkDifficultePaiementMandatGratuit.click();
    }

    public void checkAttestationComplementaireMandatGratuit() {
        checkAttestationComplementaireMandatGratuit.click();
    }

    public void checkDeclarationAffiliationMandatGratuit() {
        checkDeclarationAffiliationMandatGratuit.click();
    }

    public void fillReferenceObservationMandatGratuit(String reference) {
        ActionUtils.sendInTextField(referenceObservationMandatGratuit, reference);
    }

    public void clickBtnEnregistrerObservationMandatGratuit() {
        ActionUtils.clickAndLoading(btnEnregistrerObservationMandatGratuit);
    }

    public void fillCommentaireObservationMandatGratuit(String commentaire) {
        ActionUtils.sendInTextField(commentaireObservationMandatGratuit, commentaire);
    }

    public void selectTypeMediaObservationMandatGratuit(String typeMedia) {
        ActionUtils.sendInComboWithoutLoading(selectTypeMediaObservationMandatGratuit, typeMedia);
    }

    public void clickMenuAssimMaladieSansRem() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnAssMaladieSansREM, driver);
    }

    public void clickMenuAssimMaladieAvecRem() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnAssContinueeAvecREM, driver);
    }

    public void clickMenuAssimMaladieReceptionDecisionInasti() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnAssMaladieReceptionDecisionInasti, driver);
    }

    public void clickMenuAssimMaladieEnvoiInfosComplInasti() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnAssMaladieEnvoiInfosComplInasti, driver);
    }

    public void clickMenuAssimMaladieAnnulerDemandeEnTraitement() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnAssMaladieAnnulationDemandeEnTraitement, driver);
    }

    public void clickMenuAssMaladieAjouterObservation() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnAssMaladieAjouterObservation, driver);
    }

    public void selectOrigineREMAssMaladie(String origine) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineREMAssMaladie, origine);
    }

    public void selectRecuParREMAssMaladie(String recuPar) {
        ActionUtils.sendInComboWithoutLoading(selectRecuParREMAssMaladie, recuPar);
    }

    public void fillDateDemandeREMAssMaladie(String date) {
        ActionUtils.doJsFill(DATE_DEMANDE_REM_ID, date);
    }

    public void fillDateCessationREMAssMaladie(String date) {
        ActionUtils.doJsFill(DATE_CESSATION_REM_ID, date);
    }

    public void fillDateIncapaciteTravailREMAssMaladie(String date) {
        ActionUtils.doJsFill("dateIncapaciteTravail", date);
    }

    public void checkAffilieSeulEnSocieteREMAssMaladie() {
        checkAffilieSeulEnSocieteREMAssMaladie.click();
    }

    public void checkPasRadierBceREMAssMaladie() {
        checkPasRadierBceREMAssMaladie.click();
    }

    public void selectOrigineAssMaladie(String origine) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineAssMaladie, origine);
    }

    public void selectRecuParAssMaladie(String recuPar) {
        ActionUtils.sendInComboWithoutLoading(selectRecuParAssMaladie, recuPar);
    }

    public void selectOrigineInfosAssMaladie(String origine) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineInfosAssMaladie, origine);
    }

    public void selectRecuParInfosAssMaladie(String recuPar) {
        ActionUtils.sendInComboWithoutLoading(selectRecuParInfosAssMaladie, recuPar);
    }

    public void fillDateDemandeAssMaladie(String date) {
        ActionUtils.doJsFill(DATE_DEMANDE_ID, date);
    }

    public void fillDateReelleCessationAssMaladie(String date) {
        ActionUtils.doJsFill(DATE_REELLE_CESSATION_ID, date);
        ActionUtils.refreshElementJS(dateReelleCessationAssMaladie);
    }

    public void checkClotureDossierAssMaladie() {
        checkClotureDossierAssMaladie.click();
    }

    public void selectDestinaireAssMaladie(String destinataire) {
        ActionUtils.sendInComboWithoutLoading(selectDestinaireAssMaladie, destinataire);
    }

    public void fillTexteLibre1AssMaladie(String texte) {
        ActionUtils.sendInTextField(texteLibre1AssMaladie, texte);
    }

    public void fillTexteLibre2AssMaladie(String texte) {
        ActionUtils.sendInTextField(texteLibre2AssMaladie, texte);
    }

    public void selectDestinaireInfosAssMaladie(String destinataire) {
        ActionUtils.sendInComboWithoutLoading(selectDestinaireInfosAssMaladie, destinataire);
    }

    public void fillTexteLibre1InfosAssMaladie(String texte) {
        ActionUtils.sendInTextField(texteLibre1InfosAssMaladie, texte);
    }

    public void fillTexteLibre2InfosAssMaladie(String texte) {
        ActionUtils.sendInTextField(texteLibre2InfosAssMaladie, texte);
    }

    public void fillDateDecisionAssMaladie(String date) {
        ActionUtils.doJsFill("dateDecision", date);
    }

    public void selectDecisionAssMaladie(String decision) {
        ActionUtils.sendInComboWithoutLoading(selectDecisionAssMaladie, decision);
    }

    public void fillDateDebutAssMaladie(String date) {
        ActionUtils.doJsFill("dateDebutAssim", date);
    }

    public void fillDateFinAssMaladie(String date) {
        ActionUtils.doJsFill("dateFinAssim", date);
    }

    public void fillDateRepriseAssMaladie(String date) {
        ActionUtils.doJsFill("dateRepriseActivite", date);
    }

    public void selectNatureCotisanteAssMaladie(String natureCotisante) {
        ActionUtils.sendInComboWithoutLoading(selectNatureCotisanteAssMaladie, natureCotisante);
    }

    public void checkCourrierDemandeAssMaladie() {
        checkCourrierDemandeAssMaladie.click();
    }

    public void checkCourrierDecisionAssMaladie() {
        checkCourrierDecisionAssMaladie.click();
    }

    public void checkRaisonRefusPasOrdreCotisationAssMaladie() {
        checkRaisonRefusPasOrdreCotisationAssMaladie.click();
    }

    public void checkRaisonRefusPasCessationAssMaladie() {
        checkRaisonRefusPasCessationAssMaladie.click();
    }

    public void checkRaisonRefusIncapaciteNonReconnueAssMaladie() {
        checkRaisonRefusIncapaciteNonReconnueAssMaladie.click();
    }

    public void checkRaisonRefusPoursuiteActiviteAssMaladie() {
        checkRaisonRefusPoursuiteActiviteAssMaladie.click();
    }

    public void checkRaisonRefusExistanceRevenusAssMaladie() {
        checkRaisonRefusExistanceRevenusAssMaladie.click();
    }

    public void checkRaisonRefusGerantSocieteAssMaladie() {
        checkRaisonRefusGerantSocieteAssMaladie.click();
    }

    public void checkRaisonRefusIncapacitePasAssezLongueAssMaladie() {
        checkRaisonRefusIncapacitePasAssezLongueAssMaladie.click();
    }

    public void checkRaisonRefusActSalarieeAssMaladie() {
        checkRaisonRefusActSalarieeAssMaladie.click();
    }

    public void checkRaisonRefusAutreCouvSocialeAssMaladie() {
        checkRaisonRefusAutreCouvSocialeAssMaladie.click();
    }

    public void checkRaisonRefusPasSuiteAssMaladie() {
        checkRaisonRefusPasSuiteAssMaladie.click();
    }

    public void checkRaisonRefusAutreAssMaladie() {
        checkRaisonRefusAutreAssMaladie.click();
    }

    public void fillReferenceObservationAssMaladie(String reference) {
        ActionUtils.sendInTextField(referenceObservationAssMaladie, reference);
    }

    public void clickBtnEnregistrerObservationAssMaladie() {
        ActionUtils.clickAndLoading(btnEnregistrerObservationAssMaladie);
    }

    public void fillCommentaireObservationAssMaladie(String commentaire) {
        ActionUtils.sendInTextField(commentaireObservationAssMaladie, commentaire);
    }

    public void selectTypeMediaObservationAssMaladie(String typeMedia) {
        ActionUtils.sendInComboWithoutLoading(selectTypeMediaObservationAssMaladie, typeMedia);
    }

    public void clickMenuAssContinueeAvecRem() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnAssContinueeAvecREM, driver);
    }

    public void clickMenuAssContinueeAvecCessation() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnAssContinueeAvecCessation, driver);
    }

    public void clickMenuAssContinueeReceptionDecisionInasti() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnAssMaladieReceptionDecisionInasti, driver);
    }

    public void clickMenuAssContinueeEnvoiInfosComplInasti() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnAssContinueeEnvoiInfosComplInasti, driver);
    }

    public void clickMenuAssContinueeAnnulerDemandeEnTraitement() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnAssContinueeAnnulationDemandeEnTraitement, driver);
    }

    public void clickMenuAssContinueeAjouterObservation() {
        ActionUtils.moveToAndClickChild(btnMenuNouveau, btnAssContinueeAjouterObservation, driver);
    }

    public void selectOrigineREMAssContinuee(String origine) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineREMAssContinuee, origine);
    }

    public void selectRecuParREMAssContinuee(String recuPar) {
        ActionUtils.sendInComboWithoutLoading(selectRecuParREMAssContinuee, recuPar);
    }

    public void fillDateDemandeREMAssContinuee(String date) {
        ActionUtils.doJsFill(DATE_DEMANDE_REM_ID, date);
    }

    public void fillDateCessationREMAssContinuee(String date) {
        ActionUtils.doJsFill(DATE_CESSATION_REM_ID, date);
    }

    public void checkDemandeTardiveAssContinuee() {
        checkDemandeTardiveAssContinuee.click();
    }

    public void checkCourrierDemandeAssCont() {
        checkCourrierDemandeAssCont.click();
    }

    public void checkCourrierDecisionAssCont() {
        checkCourrierDecisionAssCont.click();
    }

    public void fillCommentaireDemandeTardiveAssContinuee(String commentaire) {
        ActionUtils.sendInTextField(commentaireDemandeTardiveAssContinuee, commentaire);
    }

    public void selectOrigineAssContinuee(String origine) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineAssContinuee, origine);
    }

    public void selectRecuParAssContinuee(String recuPar) {
        ActionUtils.sendInComboWithoutLoading(selectRecuParAssContinuee, recuPar);
    }

    public void selectOrigineInfosAssContinuee(String origine) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineInfosAssContinuee, origine);
    }

    public void selectRecuParInfosAssContinuee(String recuPar) {
        ActionUtils.sendInComboWithoutLoading(selectRecuParInfosAssContinuee, recuPar);
    }

    public void fillDateDemandeAssContinuee(String date) {
        ActionUtils.doJsFill(DATE_DEMANDE_ID, date);
    }

    public void fillDateReelleCessationAssContinuee(String date) {
        ActionUtils.doJsFill(DATE_REELLE_CESSATION_ID, date);
        ActionUtils.refreshElementJS(dateReelleCessationAssContinuee);
    }

    public void selectDestinaireAssContinuee(String destinataire) {
        ActionUtils.sendInComboWithoutLoading(selectDestinaireAssContinuee, destinataire);
    }

    public void fillTexteLibre1AssContinuee(String texte) {
        ActionUtils.sendInTextField(texteLibre1AssContinuee, texte);
    }

    public void fillTexteLibre2AssContinuee(String texte) {
        ActionUtils.sendInTextField(texteLibre2AssContinuee, texte);
    }

    public void selectDestinaireInfosAssContinuee(String destinataire) {
        ActionUtils.sendInComboWithoutLoading(selectDestinaireInfosAssContinuee, destinataire);
    }

    public void fillTexteLibre1InfosAssContinuee(String texte) {
        ActionUtils.sendInTextField(texteLibre1InfosAssContinuee, texte);
    }

    public void fillTexteLibre2InfosAssContinuee(String texte) {
        ActionUtils.sendInTextField(texteLibre2InfosAssContinuee, texte);
    }

    public void fillDateDecisionAssContinuee(String date) {
        ActionUtils.doJsFill("dateDecision", date);
    }

    public void selectDecisionAssContinuee(String decision) {
        ActionUtils.sendInComboWithoutLoading(selectDecisionAssContinuee, decision);
    }

    public void selectRaisonRefusAssContinuee(String raisonRefus) {
        ActionUtils.sendInComboWithoutLoading(selectRaisonRefusAssContinuee, raisonRefus);
    }

    public void fillDateDebutAssContinuee(String date) {
        ActionUtils.doJsFill("dateDebutAssCont", date);
    }

    public void fillDateFinAssContinuee(String date) {
        ActionUtils.doJsFill("dateFinAssCont", date);
    }

    public void fillDateCessationAssContinuee(String date) {
        ActionUtils.doJsFill("dateCessation", date);
    }

    public void selectTypeAssContinuee(String type) {
        ActionUtils.sendInComboWithoutLoading(selectTypeAssContinuee, type);
    }

    public void fillReferenceObservationAssContinuee(String reference) {
        ActionUtils.sendInTextField(referenceObservationAssContinuee, reference);
    }

    public void clickBtnEnregistrerObservationAssContinuee() {
        ActionUtils.clickAndLoading(btnEnregistrerObservationAssContinuee);
    }

    public void fillCommentaireObservationAssContinuee(String commentaire) {
        ActionUtils.sendInTextField(commentaireObservationAssContinuee, commentaire);
    }

    public void selectTypeMediaObservationAssContinuee(String typeMedia) {
        ActionUtils.sendInComboWithoutLoading(selectTypeMediaObservationAssContinuee, typeMedia);
    }

    public void clickMenuNouveauContratPLC() {
        ActionUtils.moveToAndClickChild(menuActionsPLC, menuNouveauContratPLC, driver);
    }

    public boolean isMenuNouveauContratPLCDisabled() {
        Actions action = new Actions(driver);
        action.moveToElement(menuActionsPLC).perform();
        SeleniumUtils.waitForActionCommon();
        return menuNouveauContratDisabledPLC.isEnabled();
    }

    public void clickMenuSuspendreContratPLC() {
        ActionUtils.moveToAndClickChild(menuActionsPLC, menuSuspendreContratPLC, driver);
    }

    public void clickViewContratPLC(String statut) {
        TableUtils.clickViewElement(TABLE_CONTRATS_PLC_ID, statut);
    }

    public void clickEditContratPLC(String statut) {
        TableUtils.clickEditElement(TABLE_CONTRATS_PLC_ID, statut);
    }

    public boolean checkContratPLC(String statut) {
        return TableUtils.isElementPresent(TABLE_CONTRATS_PLC_ID, statut);
    }

    public void clickMenuReveillerContratPLC() {
        ActionUtils.moveToAndClickChild(menuActionsPLC, menuReveillerContratPLC, driver);
    }

    public void clickMenuCloturerContratPLC() {
        ActionUtils.moveToAndClickChild(menuActionsPLC, menuCloturerContratPLC, driver);
    }

    public void clickMenuAjouterObservationContratPLC() {
        ActionUtils.moveToAndClickChild(menuActionsPLC, menuAjoutObservationContratPLC, driver);
    }

    public void clickMenuModifierDonneesContratPLC() {
        ActionUtils.moveToAndClickChild(menuActionsPLC, menuModifierDonneesContratPLC, driver);
    }

    public void fillDateSuspensionOuClotureOuReveilContratPLC(String date) {
        ActionUtils.doJsFill("dateMotifSuspId", date);
    }

    public void selectMotifSuspensionOuClotureOuReveilContratPLC(String motif) {
        ActionUtils.sendInComboWithoutLoading(selectMotifSuspensionOuClotureOuReveilContratPLC, motif);
    }

    public void clickBtnEnregistrerSuspensionOuClotureOuReveilContratPLC() {
        ActionUtils.clickAndLoading(btnEnregistrerSuspensionOuClotureOuReveilContratPLC);
    }

    public void checkTrimestreAEnrolerContratPLC() {
        checkTrimestre1AEnrolerContratPLC.click();
        checkTrimestre2AEnrolerContratPLC.click();
        checkTrimestre3AEnrolerContratPLC.click();
        checkTrimestre4AEnrolerContratPLC.click();
    }

    public void fillReferenceObservationContratPLC(String reference) {
        ActionUtils.sendInTextField(referenceObservationContratPLC, reference);
    }

    public void selectTypeObservationContratPLC(String type) {
        ActionUtils.sendInComboWithoutLoading(selectTypeObservationContratPLC, type);
    }

    public void fillCommentaireObservationContratPLC(String commentaire) {
        ActionUtils.sendInTextField(commentaireObservationContratPLC, commentaire);
    }

    public void selectMediaObservationContratPLC(String media) {
        ActionUtils.sendInComboWithoutLoading(selectMediaObservationContratPLC, media);
    }

    public void clickBtnEnregistrerObservationContratPLC() {
        ActionUtils.clickAndLoading(btnEnregistrerObservationContratPLC);
    }

    public void fillNumeroContratPLC(String numeroContrat) {
        ActionUtils.sendInTextField(numeroContratPLC, numeroContrat);
    }

    public void fillNumeroModifContratPLC(String numeroContrat) {
        ActionUtils.sendInTextField(numeroModifContratPLC, numeroContrat);
    }

    public String getNumeroContratPLC() {
        return numeroContratPLC.getAttribute("value");
    }

    public void fillNumeroVCSContratPLC(String numeroVCS) {
        ActionUtils.sendInTextField(numeroVCSContratPLC, numeroVCS);
    }

    public void fillNumeroVCSModifContratPLC(String numeroVCS) {
        ActionUtils.sendInTextField(numeroVCSModifContratPLC, numeroVCS);
    }

    public String getNumeroVCSContratPLC() {
        return numeroVCSContratPLC.getAttribute("value");
    }

    public void clickBtnEnregistrerModifierDonneesContratPLC() {
        ActionUtils.clickAndLoading(btnEnregistrerModifierDonneesContratPLC);
    }

    public void selectOrigineDemandeContratPLC(String origine) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineDemandeContratPLC, origine);
    }

    public void selectOrigineDemandeTiersContratPLC(String tiers) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineDemandeTiersContratPLC, tiers);
    }

    public boolean checkMessageInformatif(String message) {
        for (WebElement el : messageInformatifContratPLC) {
            if (el.getText().equals(message)) {
                return true;
            }
        }
        return false;
    }

    public void selectOrigineDemandeOrganismeContratPLC(String organisme) {
        ActionUtils.sendInComboWithoutLoading(selectOrigineDemandeOrganismeContratPLC, organisme);
    }

    public void selectDemandeRecueParContratPLC(String recuePar) {
        ActionUtils.sendInComboWithoutLoading(selectDemandeRecueParContratPLC, recuePar);
    }

    public void selectNatureConventionContratPLC(String natureConvention) {
        ActionUtils.sendInComboWithoutLoading(selectNatureConventionContratPLC, natureConvention);
    }

    public void fillDateSouscriptionContratPLC(String date) {
        ActionUtils.doJsFill("dateSouscriptionContratId", date);
    }

    public void selectTypeInvestissementContratPLC(String typeInvestissement) {
        ActionUtils.sendInComboWithoutLoading(selectTypeInvestissementContratPLC, typeInvestissement);
    }

    public void fillDatePriseEffetCalculContratPLC(String date) {
        ActionUtils.doJsFill("datePriseEffetId", date);
    }

    public void selectDecisionContratPLC(String decision) {
        ActionUtils.sendInComboWithoutLoading(selectDecisionContratPLC, decision);
    }

    public void clickBtnCalculerContratPLC() {
        ActionUtils.clickAndLoading(btnCalculerContratPLC);
    }

    public boolean isMessageErreurContratPLC() {
        return messageErreurContratPLC.isDisplayed();
    }

    public void selectMotifRefusContratPLC(String motifRefus) {
        ActionUtils.sendInComboWithoutLoading(selectMotifRefusContratPLC, motifRefus);
    }

    public void selectProfilPrescripteurContratPLC(String profil) {
        ActionUtils.sendInComboWithoutLoading(selectProfilPrescripteurContratPLC, profil);
    }

    public void selectRegimePrescripteurContratPLC(String regime) {
        ActionUtils.sendInComboWithoutLoading(selectRegimePrescripteurContratPLC, regime);
    }

    public void checkExonerationContratPLC() {
        checkExonerationContratPLC.click();
    }

    public void checkReductionContratPLC() {
        checkReductionContratPLC.click();
    }

    public void checkPensionSurvieContratPLC() {
        checkPensionSurvieContratPLC.click();
    }

    public void fillRevenuSouscripteurContratPLC(String revenu) {
        ActionUtils.sendInTextField(revenuSouscripteurContratPLC, revenu);
    }

    public void fillAnneeRevenuSouscripteurContratPLC(String revenu) {
        ActionUtils.sendInTextField(anneeRevenuSouscripteurContratPLC, revenu);
    }

    public void clickLienCtx() {
        ActionUtils.clickAndLoading(lienCtx);
    }



    public void clickLienIrrec() {
        ActionUtils.clickAndLoading(lienIrrec);
    }

    public void clickLienCodeb() {
        ActionUtils.clickAndLoading(lienCodeb);
    }

    public void clickLienDispense() {
        ActionUtils.clickAndLoading(lienDispense);
    }

    public void clickLienPrescr() {
        ActionUtils.clickAndLoading(lienPrescr);
    }

    public void clickLienSolidaire() {
        ActionUtils.clickAndLoading(lienSolidaire);
    }

    public void clickLienRem() {
        ActionUtils.clickAndLoading(lienREM);
    }

    public void clickLienPlc() {
        ActionUtils.clickAndLoading(lienPlc);
    }

    public void clickLienPlanFamille() {
        ActionUtils.clickAndLoading(lienPlanFamille);
    }

    public void clickLienPasserelle() {
        ActionUtils.clickAndLoading(lienPass);
    }

    public void clickLienMaternite() {
        ActionUtils.clickAndLoading(lienMat);
    }

    public void clickLienPA() {
        ActionUtils.clickAndLoading(lienPA);
    }

    public void clickBtnReset() {
        ActionUtils.clickAndLoading(btnResetMenu);
    }

    public void clickLienConjoint() {
        ActionUtils.clickAndLoading(lienConjoint);
    }

    public void clickLienSociete() {
        ActionUtils.clickAndLoading(lienSociete);
    }

    public Boolean isObservationPLCPresente(String typeMedia, String type) {
        try {
            String tableId = "observationTableId";
            Elements trs = TableUtils.getParsedTable(tableId);
            int trindex = 0;

            for (Element tr : trs) {
                Elements tds = tr.getElementsByTag("td");

                boolean isTypeMedia = false;
                int tdIndex = 1;
                while (tdIndex <= tds.size()) {
                    String rown = "//*[@id='" + tableId + "']/tbody/tr[" + trindex + "]/td[" + tdIndex + "]";

                    if (tdIndex == 1) {
                        rown += "/img";
                    }

                    WebElement row = TestBase.getDriver().findElement(By.xpath(rown));

                    if (tdIndex == 1 && row.getAttribute("title").equals(typeMedia)) {
                        isTypeMedia = true;
                    }
                    if (tdIndex == 3 && row.getText().equals(type) && isTypeMedia) {
                        return true;
                    }
                    tdIndex++;
                }
                trindex++;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickMenuRecouvrementRappel() {
        if (menuRecouvrementRecouvrementRappel.isDisplayed()) {
            ActionUtils.clickAndLoading(menuRecouvrementRecouvrementRappel);
        } else {
            ActionUtils.clickChildFromChildMenu(menuRecouvrement, menuRecouvrementRecouvrement, menuRecouvrementRecouvrementRappel, driver);
        }
    }

    public void clickMenuRecouvrementMiseEnDemeure() {
        if (menuRecouvrementRecouvrementMiseEnDemeure.isDisplayed()) {
            ActionUtils.clickAndLoading(menuRecouvrementRecouvrementMiseEnDemeure);
        } else {
            ActionUtils.clickChildFromChildMenu(menuRecouvrement, menuRecouvrementRecouvrement, menuRecouvrementRecouvrementMiseEnDemeure, driver);
        }
    }

    public void clickMenuRappelALaDemande() {
        ActionUtils.moveToAndClickChild(menuActionRecouvrement, menuActionRappelALaDemande, driver);
    }

    public void clickMenuMEDPrincipalALaDemande() {
        ActionUtils.moveToAndClickChild(menuActionRecouvrement, menuActionMEDPrincipalALaDemande, driver);
    }

    public String getLibelleNatureProfil() {
        return libelleNatureProfil.getText().replaceAll("’","'");
    }

    public String getLibelleSuperNatureProfil() {
        return libelleSuperNatureProfil.getText().replaceAll("’","'");
    }

    public String getLibelleLimiteArt11Profil() {
        return libelleLimiteArt11Profil.getText().replaceAll("’","'");
    }

    public void clickMenuAjouterPension() {
        ActionUtils.moveToAndClickChild(menuDonneesPensionActions, menuAjouterPension, driver);
    }

    public void fillDateDemandePension(String date) {
        ActionUtils.doJsFill("dateDemande", date);
    }

    public void fillDatePriseCoursPension(String date) {
        ActionUtils.doJsFill("datePriseCours", date);
    }

    public void selectNaturePension(String valeur) {
        ActionUtils.sendInCombo(naturePension, valeur);
    }

    public void selectRegimePension(String valeur) {
        ActionUtils.sendInCombo(regimePension, valeur);
    }

    public void selectInfoCarrierePension(String valeur) {
        ActionUtils.sendInComboWithoutLoading(infoCarrierePension, valeur);
    }

    public void selectDetenteurPension(String valeur) {
        ActionUtils.sendInCombo(detenteurPension, valeur);
    }

    public void selectEnfantAChargePension(String valeur) {
        ActionUtils.sendInComboWithoutLoading(enfantAChargePension, valeur);
    }

    public void clickMenuConsultationCarriere() {
        if (menuConsultationCarriere.isDisplayed()) {
            ActionUtils.clickAndLoading(menuConsultationCarriere);
        } else {
            ActionUtils.clickChildMenu(menuCarriere, menuConsultationCarriere, driver);
        }
    }

    public void clickOuvrirAccordeonCritereRechercheConsultationCarriere() {
        accordeonCritereRechercheConsultationCarriere.click();
    }

    public void clickOuvrirAccordeonObservationControleSpfCarriere() {
        accordeonObservationControleSpfCarriere.click();
    }


    public void clickOuvrirAccordeonRechercheProfilConsultationCarriere() {
        accordeonRechercheProfilConsultationCarriere.click();
    }


    public void checkSelectionCarriere(String selection) {
        switch (selection) {
            case "generale" :
                checkSelectionGeneraleCarriere.click();
                break;
            case "annee" :
                checkSelectionAnneeCarriere.click();
                break;
            case "trimestre" :
                checkSelectionTrimestreCarriere.click();
                break;
        }
    }

    public void selectAnneeRechercheCarriere(String annee) {
        ActionUtils.sendInTextField(selectionAnneeCarriere, annee);
    }

    public void selectTrimestreAnneeRechercheCarriere(String anneeTrimestre) {
        ActionUtils.sendInTextField(selectionTrimestreAnneeCarriere, anneeTrimestre);
    }

    public void selectAnneeTrimestreRechercheCarriere(String annee, String trimestre) {
        ActionUtils.sendInTextField(selectionTrimestreAnneeCarriere, annee + "/" + trimestre);
    }

    public void select25Elements() {
        select25ElementsCarriere.sendKeys("25");
    }

    public void selectControleSpfINASTI() {
        selectControleSpfINASTI.sendKeys("INASTI");
    }

    public void selectControleSpfCourrier() {
        selectControleSpfINASTI.sendKeys("courrier");
    }


    public void selectAnneeTrimRechercheCarriere(String anneeTrimestre) {
 /*    ActionUtils.sendInComboWithoutLoading(selectionTrimestreCarriere, trimestre); */

 /*       ActionUtils.sendInTextField(selectionTrimestreAnneeCarriere, annee);*/
        ActionUtils.sendInTextField(selectionTrimestreAnneeCarriere, anneeTrimestre);
    }

    public void checkSelectionTypeAffichageCarriere(String type) {
        switch (type) {
            case "1" :
                checkPeriodesTransmisesInastiCarriere.click();
                break;
            case "2" :
                checkPeriodesValablesPensionCarriere.click();
                break;
            case "3" :
                checkPeriodesNonValablesPensionCarriere.click();
                break;
            case "4" :
                checkPeriodesEnAttenteCarriere.click();
                break;
            case "5" :
                checkTousMouvementsCarriere.click();
                break;
        }
    }

    public void clickBtnRechercheConsultationCarriere() {
        ActionUtils.clickAndLoading(btnSearchConsultationCarriere);
    }

    public void SelectSpfCreeCarriere() {
        ActionUtils.clickAndLoading(btnSelectSpfCreeCarriere);
    }




    public void clickPencilGestionCarriere() {
        ActionUtils.clickAndLoading(btnPencilCarriere);
    }

    public String getCodeActiviteFromCarriere() {
        return driver.findElement(By.xpath("//*[@id='" + TABLE_CARRIERE_PENSION_ID + "']/tbody/tr[1]/td[2]")).getText();
    }

    public void clickBtnAjouterGestionCarriere() {
        ActionUtils.clickAndLoading(BtnAjouterGestionCarriere);
    }

    public void clickBtnTrimestre1Carriere() {
        ActionUtils.clickAndLoading(BtnTrimestre1Carriere);
    }

    public void clickBtnTrimestre2Carriere() {
        ActionUtils.clickAndLoading(BtnTrimestre2Carriere);
    }


    public void clickBtnTrimestre3Carriere() {
        ActionUtils.clickAndLoading(BtnTrimestre3Carriere);
    }

    public void clickBtnTrimestre4Carriere() {
        ActionUtils.clickAndLoading(BtnTrimestre4Carriere);
    }

    public void clickCotisationOrdinaireCarriere (){
        ActionUtils.clickAndLoading(CotisationOrdinaireCarriere);
    }

    public void clickCotisationDeRegularisationCarriere (){
        ActionUtils.clickAndLoading(CotisationDeRegularisationCarriere);
    }

    public void selectSousNumeroCarriere (String sousNumero){
        //   ActionUtils.sendInTextField(selectionSousNumeroCarriere, sousNumero);
        ActionUtils.doJsFill("sousNum", sousNumero);
    }

    public void fillAnneeReferenceCarriere (String anneeRef){
        ActionUtils.doJsFill("editCarrierePensionFormId_carriereDto_anneeReference", anneeRef);
    }

    public void fillAnneeTrimestreControleSpfCarriere (String anneeTrimSpf){
        ActionUtils.doJsFill("trimestre", anneeTrimSpf);
    }


    public void fillRevenuCommuniqueCarriere (String mntRevenu){
        ActionUtils.sendInTextField(RevenuCommuniqueCarriere, mntRevenu);
    }

    public void fillRevenuAdapteCarriere (String mntRevenu){
        ActionUtils.sendInTextField(RevenuAdapteCarriere, mntRevenu);
    }

    public void fillRevenuComposeCarriere (String mntRevenu){
        ActionUtils.sendInTextField(RevenuComposeCarriere, mntRevenu);
    }

    public void fillRevenuContributionCarriere (String mntRevenu){
        ActionUtils.sendInTextField(RevenuContributionCarriere, mntRevenu);
    }

    public void fillRevenuBaseDeCalculCarriere (String mntRevenu){
        ActionUtils.sendInTextField(RevenuBaseDeCalculCarriere, mntRevenu);
    }

    public void fillDateEcheanceCarriere (String date){
        ActionUtils.sendInTextField(DateEcheanceCarriere, date);
    }

    public void fillDateControleSpfCarriere (String date){
        ActionUtils.sendInTextField(DateControleSpfCarriere, date);
    }

    public void fillDatePaiementTardifCarriere (String date){
        ActionUtils.sendInTextField(DatePaiementTardifCarriere, date);
    }

    public void selectCodeActiviteCarriere(String codeActivite) {
        ActionUtils.sendInComboWithoutLoading(selectionCodeActiviteCarriere, "02");
    }

    public void clickCotisationPresumeeCarriere (){
        ActionUtils.clickAndLoading(CotisationPresumeeCarriere);
    }

    public void clickCotisationPresumee2Carriere (){
        ActionUtils.clickAndLoading(CotisationPresumee2Carriere);
    }

    public void clickRevenuProvisoireCarriere (){
        ActionUtils.clickAndLoading(RevenuProvisoireCarriere);
    }

    public void clickBtnEnregistrerCarriere (){
        ActionUtils.clickAndLoading(BtnEnregistrerCarriere);
    }

    public void clickBtnEnregistrer2Carriere (){
        ActionUtils.clickAndLoading(BtnEnregistrer2Carriere);
    }

    public void clickBtnEnregistrerControleSpfCarriere (){
        ActionUtils.clickAndLoading(BtnEnregistrerControleSpfCarriere);
    }

    public void clickBtnReinitialiserObservationControleSpfCarriere (){
        ActionUtils.clickAndLoading(BtnReinitialiserObservationControleSpfCarriere);
    }

    public void clickPlusSpfCarriere (){
        ActionUtils.clickAndLoading(BtnPlusSpfCarriere);
    }

    public void clickBtnAnnulerCarriere (){
        ActionUtils.clickAndLoading(BtnAnnulerCarriere);
    }

    public void clickBtnAnnuler2Carriere (){
        ActionUtils.clickAndLoading(BtnAnnuler2Carriere);
    }


    public void clickBtnReinitialiserCarriere (){
        ActionUtils.clickAndLoading(BtnReinitialiserCarriere);
    }

    public void clickRevenuDuplique1Carriere (){
        ActionUtils.clickAndLoading(RevenuDuplique1Carriere);
    }

    public void clickRevenuDuplique2Carriere (){
        ActionUtils.clickAndLoading(RevenuDuplique2Carriere);
    }

    public void clickRevenuDuplique3Carriere (){
        ActionUtils.clickAndLoading(RevenuDuplique3Carriere);
    }

    public void clickRevenuDuplique4Carriere (){
        ActionUtils.clickAndLoading(RevenuDuplique4Carriere);
    }

    public void clickLigneSansCalculCotiCarriere (){
        ActionUtils.clickAndLoading(LigneSansCalculCotiCarriere);
    }


    public void selectLigneGestionCarriereAnnee() {
        ActionUtils.clickAndLoading(btnLigneGestionCarriere);
    }

    public void clickElement2tableauConsultationCarriere() {
        ActionUtils.clickAndLoading(Element2tableauConsultationCarriere);
    }

    public void clickDernierElementtableauConsultationCarriere() {
        ActionUtils.clickAndLoading(DernierElementtableauConsultationCarriere);
    }

    public void clickElementPrecedenttableauConsultationCarriere() {
        ActionUtils.clickAndLoading(ElementPrecedenttableauConsultationCarriere);
    }

    public void clickElementSuivanttableauConsultationCarriere() {
        ActionUtils.clickAndLoading(ElementSuivanttableauConsultationCarriere);
    }

    public void clickPremierElementtableauConsultationCarriere() {
        ActionUtils.clickAndLoading(PremierElementtableauConsultationCarriere);
    }

    public void clickMenuGestionCarriere() {
        if (menuGestionCarriere.isDisplayed()) {
            ActionUtils.clickAndLoading(menuGestionCarriere);
        } else {
            ActionUtils.clickChildMenu(menuCarriere, menuGestionCarriere, driver);
        }
    }

    public void clickMenuControleSpfCarriere() {
        if (menuControleSpfCarriere.isDisplayed()) {
            ActionUtils.clickAndLoading(menuControleSpfCarriere);
        } else {
            ActionUtils.clickChildMenu(menuCarriere, menuControleSpfCarriere, driver);
        }
    }

    public void clickEncoderControleSpf() {
        ActionUtils.moveToAndClickChild(menuActionsControleSpf, menuEncoderControleSpf, driver);
    }

    public void clickModifierTrimestresSpf() {
        ActionUtils.moveToAndClickChild(menuActionsMajControleSpf, menuMajSelTrimControleSpf, driver);
    }

    public void clickMajSelTrimControleSpf() {
        ActionUtils.moveToAndClickChild(menuActionsControleSpf, menuEncoderControleSpf, driver);
    }

    public void clickBtnRechercheGestionCarriereAnnee() {
        ActionUtils.clickAndLoading(BtnRechercheGestionCarriereAnnee);
    }

    public void clickBtnRechercheControleSpfCarriere() {
        ActionUtils.clickAndLoading(BtnRechercheControleSpfCarriere);
    }

    public void clickBtnRechercheControleSpfCarriereAnnee() {
        ActionUtils.clickAndLoading(BtnRechercheControleSpfCarriereAnnee);
    }

    public void clickBtnRechercheControleSpfCarriereTrimestreAnnee() {
        ActionUtils.clickAndLoading(BtnRechercheControleSpfCarriereTrimestreAnnee);
    }


    public void clickBtnRechercheGestionCarriereTrimestreAnnee() {
        ActionUtils.clickAndLoading(BtnRechercheGestionCarriereTrimestreAnnee);
    }

    public void selectElements() {
        ActionUtils.clickAndLoading(selectElements);
      }


    public BigDecimal getRevenuCommuniqueFromCarriere() {
        if (StringUtils.isEmpty(driver.findElement(By.xpath("//*[@id='" + TABLE_CARRIERE_PENSION_ID + "']/tbody/tr[1]/td[3]")).getText().trim())) {
            return null;
        } else {
            return SeleniumUtils.transformStringToBigDecimal(driver.findElement(By.xpath("//*[@id='" + TABLE_CARRIERE_PENSION_ID + "']/tbody/tr[1]/td[3]")).getText());
        }
    }

    public BigDecimal getRevenuAdapteFromCarriere() {
        if (StringUtils.isEmpty(driver.findElement(By.xpath("//*[@id='" + TABLE_CARRIERE_PENSION_ID + "']/tbody/tr[1]/td[4]")).getText().trim())) {
            return null;
        } else {
            return SeleniumUtils.transformStringToBigDecimal(driver.findElement(By.xpath("//*[@id='" + TABLE_CARRIERE_PENSION_ID + "']/tbody/tr[1]/td[4]")).getText());
        }
    }

    public BigDecimal getRevenuComposeFromCarriere() {
        if (StringUtils.isEmpty(driver.findElement(By.xpath("//*[@id='" + TABLE_CARRIERE_PENSION_ID + "']/tbody/tr[1]/td[5]")).getText().trim())) {
            return null;
        } else {
            return SeleniumUtils.transformStringToBigDecimal(driver.findElement(By.xpath("//*[@id='" + TABLE_CARRIERE_PENSION_ID + "']/tbody/tr[1]/td[5]")).getText());
        }
    }

    public boolean getCodeProvisoireFromCarriere() {
        try {
            if (driver.findElement(By.xpath("//*[@id='" + TABLE_CARRIERE_PENSION_ID + "']/tbody/tr[1]/td[6]/img")).isDisplayed()) {
                return true;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
        return false;
    }

    public String getAnneeReferenceFromCarriere() {
        return driver.findElement(By.xpath("//*[@id='" + TABLE_CARRIERE_PENSION_ID + "']/tbody/tr[1]/td[7]")).getText();
    }

    public BigDecimal getRevenuContributionFromCarriere() {
        if (StringUtils.isEmpty(driver.findElement(By.xpath("//*[@id='" + TABLE_CARRIERE_PENSION_ID + "']/tbody/tr[1]/td[10]")).getText().trim())) {
            return null;
        } else {
            return SeleniumUtils.transformStringToBigDecimal(driver.findElement(By.xpath("//*[@id='" + TABLE_CARRIERE_PENSION_ID + "']/tbody/tr[1]/td[10]")).getText());
        }
    }

    public BigDecimal getRevenuBaseFromCarriere() {
        if (StringUtils.isEmpty(driver.findElement(By.xpath("//*[@id='" + TABLE_CARRIERE_PENSION_ID + "']/tbody/tr[1]/td[11]")).getText().trim())) {
            return null;
        } else {
            return SeleniumUtils.transformStringToBigDecimal(driver.findElement(By.xpath("//*[@id='" + TABLE_CARRIERE_PENSION_ID + "']/tbody/tr[1]/td[11]")).getText());
        }
    }

    public void clickOngletDemandeRefresh() {
        ActionUtils.clickAndLoading(ongletDemandeRefresh);
    }
}