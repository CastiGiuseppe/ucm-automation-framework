package be.ucm.cas.nasca.web.pagefactory.administration;

import be.ucm.cas.nasca.web.test.support.ActionUtils;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdministrationPage {

    private static final String MONTANT_MAX = "9999.99";

    private final WebDriver driver;

    // Barèmes
    @FindBy(id = "level5Div")
    private static WebElement menuBaremes;

    @FindBy(id = "level5-1Div")
    private static WebElement menuBaremesPersonnesMorales;

    @FindBy(id = "level5-2Div")
    private static WebElement menuBaremesPersonnesPhysiques;

    @FindBy(xpath = "//rightcontent/div[2]/div/div/ul/li[1]/a")
    private static WebElement ongletMontantsPivotsBaremesPP;

    @FindBy(xpath = "//rightcontent/div[2]/div/div/ul/li[2]/a")
    private static WebElement ongletIndexBaremesPP;

    @FindBy(xpath = "//rightcontent/div[2]/div/div/ul/li[3]/a")
    private static WebElement ongletTauxBaremesPP;

    @FindBy(xpath = "//rightcontent/div[2]/div/div/ul/li[4]/a")
    private static WebElement ongletMajorationsBaremesPP;

    @FindBy(xpath = "//rightcontent/div[2]/div/div/ul/li[1]/a")
    private static WebElement ongletCotisationsAnnuellesBaremesPM;

    @FindBy(xpath = "//rightcontent/div[2]/div/div/ul/li[2]/a")
    private static WebElement ongletEcheancesBaremesPM;

    @FindBy(xpath = "//rightcontent/div[2]/div/div/ul/li[3]/a")
    private static WebElement ongletMajorationsBaremesPM;

    @FindBy(xpath = "//rightcontent/div[2]/div/div/ul/li[4]/a")
    private static WebElement ongletFraisBaremesPM;

    @FindBy(id = "_trimestreListId")
    private static WebElement trimestreMontantsPivotsBaremesPP;

    @FindBy(id = "addPlcPlfId")
    private static WebElement btnAjouterPlcPlfMontantPivotBaremesPP;

    @FindBy(id = "addPlfPensCjtId")
    private static WebElement btnAjouterPlfPensCjtMontantPivotBaremesPP;

    @FindBy(id = "editPlcPlfForm_montants_0__valeur")
    private static WebElement montantPlcG;

    @FindBy(id = "editPlfPensCjtForm_montants_0__valeur")
    private static WebElement montantPlfPensR;

    @FindBy(id = "_trimestreIndexListId")
    private static WebElement trimestreIndexRefBaremesPP;

    @FindBy(id = "addIndexId")
    private static WebElement btnAjouterIndexRefBaremesPP;

    @FindBy(id = "editIndexForm_indexes_0__numerateur")
    private static WebElement num1Index;

    @FindBy(id = "editIndexForm_indexes_1__numerateur")
    private static WebElement num2Index;

    @FindBy(id = "editIndexForm_indexes_2__numerateur")
    private static WebElement num3Index;

    @FindBy(id = "editIndexForm_indexes_3__numerateur")
    private static WebElement num4Index;

    @FindBy(id = "editIndexForm_indexes_4__numerateur")
    private static WebElement num5Index;

    @FindBy(id = "editIndexForm_indexes_5__numerateur")
    private static WebElement num6Index;

    @FindBy(id = "editIndexForm_indexes_6__numerateur")
    private static WebElement num7Index;

    @FindBy(id = "editIndexForm_indexes_0__denominateur")
    private static WebElement denom1Index;

    @FindBy(id = "editIndexForm_indexes_1__denominateur")
    private static WebElement denom2Index;

    @FindBy(id = "editIndexForm_indexes_2__denominateur")
    private static WebElement denom3Index;

    @FindBy(id = "editIndexForm_indexes_3__denominateur")
    private static WebElement denom4Index;

    @FindBy(id = "editIndexForm_indexes_4__denominateur")
    private static WebElement denom5Index;

    @FindBy(id = "editIndexForm_indexes_5__denominateur")
    private static WebElement denom6Index;

    @FindBy(id = "editIndexForm_indexes_6__denominateur")
    private static WebElement denom7Index;

    @FindBy(id = "editIndexForm_indexes_0__assuCont")
    private static WebElement checkAssContinuee1Index;

    @FindBy(id = "editIndexForm_indexes_2__assuCont")
    private static WebElement checkAssContinuee3Index;

    @FindBy(id = "editIndexForm_indexes_4__assuCont")
    private static WebElement checkAssContinuee5Index;

    @FindBy(id = "editIndexForm_indexes_6__assuCont")
    private static WebElement checkAssContinuee7Index;

    @FindBy(id = "_trimestreTauxListId")
    private static WebElement trimestreTauxValeurBaremesPP;

    @FindBy(id = "addTauxValeurId")
    private static WebElement btnAjouterTauxValeurBaremesPP;

    @FindBy(id = "editTauxValeurForm_listTaux_0__valeur")
    private static WebElement tauxFraisGestion;

    @FindBy(id = "_trimestreMajPpListId")
    private static WebElement trimestreMajorationsBaremesPP;

    @FindBy(id = "addMajPpValeurId")
    private static WebElement btnAjouterMajorationBaremesPP;

    @FindBy(id = "editMajPpValeurForm_listTaux_0__valeur")
    private static WebElement majorationTrimArt48;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerBareme;

    @FindBy(id = "searchCotBtn")
    private static WebElement btnSearchCotisationsAnnuellesBaremesPM;

    @FindBy(id = "addCot")
    private static WebElement btnAjouterCotisationAnnuelleBaremesPM;

    @FindBy(id = "montantPivotId")
    private static WebElement totalBilanPivot;

    @FindBy(id = "cotisationMinId")
    private static WebElement cotisationMinimale;

    @FindBy(id = "cotisationMaxId")
    private static WebElement cotisationMaximale;

    @FindBy(id = "_contexteId")
    private static WebElement selectContexteEcheanceBaremesPM;

    @FindBy(id = "editEcheanceForm_baremeEcheancePm_delai")
    private static WebElement delaiEcheanceBaremePM;

    @FindBy(id = "_typeEcheance")
    private static WebElement selectTypeEcheanceBaremesPM;

    @FindBy(id = "searchEchBtn")
    private static WebElement btnSearchEcheancesBaremesPM;

    @FindBy(id = "addEch")
    private static WebElement btnAjouterEcheanceBaremesPM;

    @FindBy(id = "addMaj")
    private static WebElement btnAjouterMajorationBaremesPM;

    @FindBy(xpath = "//div[2]/form/div[2]/table/tbody/tr/td[6]/a/img")
    private static WebElement linkEcheanceEnCours;

    @FindBy(xpath = "//div[2]/form/div[2]/table/tbody/tr/td[4]")
    private static WebElement dateFinValiditeEcheanceEnCours;

    @FindBy(id = "tTaux")
    private static WebElement checkTaux;

    @FindBy(id = "valeurTaux")
    private static WebElement valeurTaux;

    @FindBy(xpath = "//div[3]/div/form/div[2]/table/tbody/tr[1]/td[7]/a/img")
    private static WebElement linkMajorationEnCours;

    @FindBy(xpath = "//div[3]/div/form/div[2]/table/tbody/tr[1]/td[5]")
    private static WebElement dateFinValiditeMajorationEnCours;

    @FindBy(xpath = "//div[4]/form/div[2]/table/tbody/tr/td[8]/a/img")
    private static WebElement linkFraisEnCours;

    @FindBy(xpath = "//div[4]/form/div[2]/table/tbody/tr/td[6]")
    private static WebElement dateFinValiditeFraisEnCours;

    @FindBy(id = "_natureId")
    private static WebElement selectNatureFraisBaremesPM;

    @FindBy(id = "montantPivot")
    private static WebElement montantPivotFraisBaremesPM;

    @FindBy(id = "_typeFrais")
    private static WebElement selectTypeFraisBaremesPM;

    @FindBy(id = "searchFraBtn")
    private static WebElement btnSearchFraisBaremesPM;

    @FindBy(id = "addFra")
    private static WebElement btnAjouterFraisBaremesPM;

    // Recouvrement
    @FindBy(id = "level8Div")
    private static WebElement menuRecouvrement;

    @FindBy(id = "level8-1Div")
    private static WebElement menuRecouvrementMiseEnDemeure;

    @FindBy(id = "level8-2Div")
    private static WebElement menuRecouvrementRappel;

    @FindBy(id = "editButtonPM")
    private static WebElement btnEditerRecouvrementRappelPM;

    @FindBy(id = "editButtonPP")
    private static WebElement btnEditerRecouvrementRappelPP;

    @FindBy(xpath = "//div[2]/div/div/div[1]/div/div/div[2]/div/div[2]/div/input")
    private static WebElement btnEditerRecouvrementMEDPrincipalPM;

    @FindBy(xpath = "//div[2]/div/div/div[1]/div/div/div[1]/div/div[2]/div/input")
    private static WebElement btnEditerRecouvrementMEDPrincipalPP;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerRecouvrement;

    @FindBy(id = "paramTraitementForm_parametres_seuil")
    private static WebElement seuilMinimumRecouvrement;

    @FindBy(id = "paramTraitementForm_parametres_montantPivot")
    private static WebElement montantPivotRecouvrement;

    @FindBy(id = "level1-2Div")
    private static WebElement menuGroupeOrganisation;

    @FindBy(id = "_selectResponsable")
    private static WebElement responsableEquipe;

    @FindBy(xpath = "//div[9]/div[3]/div/button[2]")
    private static WebElement btnEnregistrerEquipe;

    public AdministrationPage(WebDriver drv) {
        this.driver = drv;
        PageFactory.initElements(driver, this);
    }

    public void clickMenuBaremesPersonnesPhysiques() {
        ActionUtils.clickChildMenu(menuBaremes, menuBaremesPersonnesPhysiques, driver);
    }

    public void clickMenuBaremesPersonnesMorales() {
        ActionUtils.clickChildMenu(menuBaremes, menuBaremesPersonnesMorales, driver);
    }

    public void clickMenuGroupeOrganisation() {
        ActionUtils.clickAndLoading(menuGroupeOrganisation);
    }

    public void editEquipeAccompagnement() {
        ActionUtils.executeJS("openModalEditEquipe('equipe=12');");
    }

    public void setResponsableEquipe(String user) {
        ActionUtils.sendInComboWithoutLoading(responsableEquipe, user);
    }

    public void clickBtnEnregistrerEquipe() {
        ActionUtils.clickAndLoading(btnEnregistrerEquipe);
    }

    public void clickOngletMontantsPivotsBaremesPersonnesPhysiques() {
        ongletMontantsPivotsBaremesPP.click();
    }

    public void clickOngletIndexBaremesPersonnesPhysiques() {
        ongletIndexBaremesPP.click();
    }

    public void clickOngletTauxBaremesPersonnesPhysiques() {
        ongletTauxBaremesPP.click();
    }

    public void clickOngletMajorationsBaremesPersonnesPhysiques() {
        ongletMajorationsBaremesPP.click();
    }

    public void fillTrimestreMontantsPivotsPersonnesPhysiques(String trimestre) {
        ActionUtils.sendInCombo(trimestreMontantsPivotsBaremesPP, trimestre);
    }

    public void clickBtnEncodagePlcPlfMontantPivotBaremePP() {
        ActionUtils.clickAndLoading(btnAjouterPlcPlfMontantPivotBaremesPP);
    }

    public void clickBtnEncodagePlfPensCjtMontantPivotBaremePP() {
        ActionUtils.clickAndLoading(btnAjouterPlfPensCjtMontantPivotBaremesPP);
    }

    public boolean isBtnEncodagePlcPlfMontantPivotBaremePPEnabled() {
        return btnAjouterPlcPlfMontantPivotBaremesPP.isEnabled();
    }

    public boolean isBtnEncodagePlfPensCjtMontantPivotBaremePPEnabled() {
        return btnAjouterPlfPensCjtMontantPivotBaremesPP.isEnabled();
    }

    public void fillMontantPlcPlfMontantPivotBaremePP() {
        ActionUtils.sendInTextField(montantPlcG, MONTANT_MAX);
    }

    public void fillMontantPlfPensCjtMontantPivotBaremePP() {
        ActionUtils.sendInTextField(montantPlfPensR, MONTANT_MAX);
    }

    public void fillTauxBaremePP() {
        ActionUtils.sendInTextField(tauxFraisGestion, "5.05");
    }

    public void fillTauxMajorationPP() {
        ActionUtils.sendInTextField(majorationTrimArt48, "4.07");
    }

    public void fillTrimestreIndexPersonnesPhysiques(String trimestre) {
        ActionUtils.sendInCombo(trimestreIndexRefBaremesPP, trimestre);
    }

    public void clickBtnEncodageIndexBaremePP() {
        ActionUtils.clickAndLoading(btnAjouterIndexRefBaremesPP);
    }

    public boolean isBtnEncodageIndexBaremePPEnabled() {
        return btnAjouterIndexRefBaremesPP.isEnabled();
    }

    public void fillTrimestreTauxPersonnesPhysiques(String trimestre) {
        ActionUtils.sendInCombo(trimestreTauxValeurBaremesPP, trimestre);
    }

    public void clickBtnEncodageTauxValeurBaremePP() {
        ActionUtils.clickAndLoading(btnAjouterTauxValeurBaremesPP);
    }

    public void fillTrimestreMajorationsPersonnesPhysiques(String trimestre) {
        ActionUtils.sendInCombo(trimestreMajorationsBaremesPP, trimestre);
    }

    public void clickBtnEncodageMajorationBaremePP() {
        ActionUtils.clickAndLoading(btnAjouterMajorationBaremesPP);
    }

    public boolean isBtnEncodageMajorationBaremePPEnabled() {
        return btnAjouterMajorationBaremesPP.isEnabled();
    }

    public void clickBtnEnregistrerBareme() {
        ActionUtils.clickAndLoading(btnEnregistrerBareme);
    }

    public void fillNumerateurIndexBaremePP() {
        num1Index.sendKeys("101");
        num2Index.sendKeys("102");
        num3Index.sendKeys("103");
        num4Index.sendKeys("104");
        num5Index.sendKeys("105");
        num6Index.sendKeys("106");
        num7Index.sendKeys("107");
    }

    public void fillDenominateurIndexBaremePP() {
        denom1Index.sendKeys("100");
        denom2Index.sendKeys("100");
        denom3Index.sendKeys("100");
        denom4Index.sendKeys("100");
        denom5Index.sendKeys("100");
        denom6Index.sendKeys("100");
        denom7Index.sendKeys("100");
    }

    public void checkAssContinueeIndexBaremePP() {
        checkAssContinuee1Index.click();
        checkAssContinuee3Index.click();
        checkAssContinuee5Index.click();
        checkAssContinuee7Index.click();
    }

    public void clickOngletCotisationsAnnuellesBaremesPersonnesMorales() {
        ongletCotisationsAnnuellesBaremesPM.click();
    }

    public void clickOngletEcheancesBaremesPersonnesMorales() {
        ongletEcheancesBaremesPM.click();
    }

    public void clickOngletMajorationsBaremesPersonnesMorales() {
        ongletMajorationsBaremesPM.click();
    }

    public void clickOngletFraisBaremesPersonnesMorales() {
        ongletFraisBaremesPM.click();
    }

    public void fillDateFromCotisationsAnnuellesBaremesPM(String date) {
        ActionUtils.doJsFill("dateFromCoti", date);
    }

    public void fillDateToCotisationsAnnuellesBaremesPM(String date) {
        ActionUtils.doJsFill("dateToCoti", date);
    }

    public void clickBtnRechercherCotisationsAnnuellesBaremesPM() {
        ActionUtils.clickAndLoading(btnSearchCotisationsAnnuellesBaremesPM);
    }

    public void clickBtnAjouterCotisationAnnuelleBaremesPM() {
        ActionUtils.clickAndLoading(btnAjouterCotisationAnnuelleBaremesPM);
    }

    public void selectTypeEcheanceBaremesPM(String typeEcheance) {
        ActionUtils.sendInComboWithoutLoading(selectTypeEcheanceBaremesPM, typeEcheance);
    }

    public void clickBtnRechercherEcheancesBaremesPM() {
        ActionUtils.clickAndLoading(btnSearchEcheancesBaremesPM);
    }

    public void clickBtnAjouterEcheanceBaremesPM() {
        ActionUtils.clickAndLoading(btnAjouterEcheanceBaremesPM);
    }

    public void clickBtnAjouterMajorationBaremesPM() {
        ActionUtils.clickAndLoading(btnAjouterMajorationBaremesPM);
    }

    public void selectTypeFraisBaremesPM(String typeFrais) {
        ActionUtils.sendInComboWithoutLoading(selectTypeFraisBaremesPM, typeFrais);
    }

    public void selectNatureFraisBaremesPM(String natureFrais) {
        ActionUtils.sendInComboWithoutLoading(selectNatureFraisBaremesPM, natureFrais);
    }

    public void clickBtnRechercherFraisBaremesPM() {
        ActionUtils.clickAndLoading(btnSearchFraisBaremesPM);
    }

    public void clickBtnAjouterFraisBaremesPM() {
        ActionUtils.clickAndLoading(btnAjouterFraisBaremesPM);
    }

    public void fillDateDebutValiditeCotisationAnnuelle(String date) {
        ActionUtils.doJsFill("dtDebutValCotId", date);
    }

    public void fillDateFinValiditeCotisationAnnuelle(String date) {
        ActionUtils.doJsFill("dtFinValCotId", date);
    }

    public void fillTotalBilanPivot() {
        ActionUtils.sendInTextField(totalBilanPivot, MONTANT_MAX);
    }

    public void fillCotisationMinimale() {
        ActionUtils.sendInTextField(cotisationMinimale, "100");
    }

    public void fillCotisationMaximale() {
        ActionUtils.sendInTextField(cotisationMaximale, "4000");
    }

    public void fillDateDebutValiditeMajoration(String date) {
        ActionUtils.doJsFill("dtDebutValMajId", date);
    }

    public void fillDateFinValiditeMajoration(String date) {
        ActionUtils.doJsFill("dtFinValMajId", date);
    }

    public void checkTaux() {
        checkTaux.click();
    }

    public void fillValeurTaux() {
        ActionUtils.sendInTextField(valeurTaux, "5.5");
    }

    public void clickEditionMajorationEnCours() {
        ActionUtils.clickAndLoading(linkMajorationEnCours);
    }

    public String getDateFinValiditeMajoration() {
        return SeleniumUtils.deleteFormat(dateFinValiditeMajorationEnCours.getText());
    }

    public void clickEditionFraisEnCours() {
        ActionUtils.clickAndLoading(linkFraisEnCours);
    }

    public String getDateFinValiditeFrais() {
        return SeleniumUtils.deleteFormat(dateFinValiditeFraisEnCours.getText());
    }

    public void fillDateDebutValiditeFrais(String date) {
        ActionUtils.doJsFill("dtDebutValFraId", date);
    }

    public void fillDateFinValiditeFrais(String date) {
        ActionUtils.doJsFill("dtFinValFraId", date);
    }

    public void fillMontantPivotFrais() {
        ActionUtils.sendInTextField(montantPivotFraisBaremesPM, "6");
    }

    public void fillDateDebutValiditeEcheance(String date) {
        ActionUtils.doJsFill("dtDebutValEchId", date);
    }

    public void fillDateFinValiditeEcheance(String date) {
        ActionUtils.doJsFill("dtDFinValEchId", date);
    }

    public void selectContexteEcheanceBaremesPM(String contexte) {
        ActionUtils.sendInComboWithoutLoading(selectContexteEcheanceBaremesPM, contexte);
    }

    public void fillDelaiEcheanceBaremesPM() {
        ActionUtils.sendInTextField(delaiEcheanceBaremePM, "10");
    }

    public void clickEditionEcheanceEnCours() {
        ActionUtils.clickAndLoading(linkEcheanceEnCours);
    }

    public String getDateFinValiditeEcheance() {
        return SeleniumUtils.deleteFormat(dateFinValiditeEcheanceEnCours.getText());
    }

    public void fillDateDerniereRecetteValideeRecouvrement(String date) {
        ActionUtils.doJsFill("paramTraitementForm_parametres_dateDerniereRecette", date);
    }

    public void fillDateAPayerPourRecouvrement(String date) {
        ActionUtils.doJsFill("paramTraitementForm_parametres_dateAPayer", date);
    }

    public void fillDateEnvoiRecouvrement(String date) {
        ActionUtils.doJsFill("paramTraitementForm_parametres_dateEnvoi", date);
    }

    public void fillSeuilMinimumRecouvrement(String montant) {
        ActionUtils.sendInTextField(seuilMinimumRecouvrement, montant);
    }

    public void fillMontantPivotRecouvrement(String montant) {
        ActionUtils.sendInTextField(montantPivotRecouvrement, montant);
    }

    public void clickBtnEditerRecouvrementRappelPP() {
        ActionUtils.clickAndLoading(btnEditerRecouvrementRappelPP);
    }

    public void clickBtnEditerRecouvrementRappelPM() {
        ActionUtils.clickAndLoading(btnEditerRecouvrementRappelPM);
    }

    public void clickBtnEditerRecouvrementMEDPrincipalPP() {
        ActionUtils.clickAndLoading(btnEditerRecouvrementMEDPrincipalPP);
    }

    public void clickBtnEditerRecouvrementMEDPrincipalPM() {
        ActionUtils.clickAndLoading(btnEditerRecouvrementMEDPrincipalPM);
    }

    public void clickBtnEnregistrerRecouvrement() {
        ActionUtils.clickAndLoading(btnEnregistrerRecouvrement);
    }

    public void clickMenuRecouvrementRappel() {
        ActionUtils.clickChildMenu(menuRecouvrement, menuRecouvrementRappel, driver);
    }

    public void clickMenuRecouvrementMiseEnDemeure() {
        ActionUtils.clickChildMenu(menuRecouvrement, menuRecouvrementMiseEnDemeure, driver);
    }
}