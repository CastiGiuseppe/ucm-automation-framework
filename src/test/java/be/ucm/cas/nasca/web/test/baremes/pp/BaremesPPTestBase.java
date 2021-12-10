package be.ucm.cas.nasca.web.test.baremes.pp;

import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.TableUtils;
import be.ucm.cas.nasca.web.test.support.TestBase;

public class BaremesPPTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    static void modificationPlanchersEtPlafondsMontantsPivotsBaremesPP(
            String trimestre, boolean isSuccess) {
        try {
            homeMenuPage.clickAdministration();
            administrationPage.clickMenuBaremesPersonnesPhysiques();
            administrationPage.clickOngletMontantsPivotsBaremesPersonnesPhysiques();
            administrationPage.fillTrimestreMontantsPivotsPersonnesPhysiques(trimestre);

            if (!isSuccess) {
                if (administrationPage.isBtnEncodagePlcPlfMontantPivotBaremePPEnabled()) {
                    logFailed("Barèmes PP (Montants pivots) - Modification planchers et plafonds accessible", "Barèmes PP (Montants pivots) KO");
                } else {
                    logSuccess("Barèmes PP (Montants pivots) - Modification planchers et plafonds inaccessible", "Barèmes PP (Montants pivots) OK");
                }
            } else {
                administrationPage.clickBtnEncodagePlcPlfMontantPivotBaremePP();
                administrationPage.fillMontantPlcPlfMontantPivotBaremePP();
                administrationPage.clickBtnEnregistrerBareme();

                if (TableUtils.isElementPresent("table-plc-plf-taux-spec", "9 999,99 €")) {
                    logSuccess("Barèmes PP (Montants pivots) - Modification planchers et plafonds",
                            "Modification montant pivot OK");
                } else {
                    logFailed("Barèmes PP (Montants pivots) - Modification planchers et plafonds",
                            "Modification montant pivot KO");
                }
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void modificationPlafondsPensionConjointMontantsPivotsBaremesPP(
            String trimestre, boolean isSuccess) {
        try {
            homeMenuPage.clickAdministration();
            administrationPage.clickMenuBaremesPersonnesPhysiques();
            administrationPage.clickOngletMontantsPivotsBaremesPersonnesPhysiques();
            administrationPage.fillTrimestreMontantsPivotsPersonnesPhysiques(trimestre);

            if (!isSuccess) {
                if (!administrationPage.isBtnEncodagePlfPensCjtMontantPivotBaremePPEnabled()) {
                    logSuccess("Barèmes PP (Montants pivots) - Modification plafonds pension conjoint inaccessible", "Barèmes PP (Montants pivots) OK");
                } else {
                    logFailed("Barèmes PP (Montants pivots) - Modification plafonds pension conjoint accessible", "Barèmes PP (Montants pivots) KO");
                }
            } else {
                administrationPage.clickBtnEncodagePlfPensCjtMontantPivotBaremePP();
                administrationPage.fillMontantPlfPensCjtMontantPivotBaremePP();
                administrationPage.clickBtnEnregistrerBareme();

                if (TableUtils.isElementPresent("table-plf-pens-cjt", "9 999,99 €")) {
                    logSuccess("Barèmes PP (Montants pivots) - Modification plafonds pensions conjoint",
                            "Modification montant pivot OK");
                } else {
                    logFailed("Barèmes PP (Montants pivots) - Modification plafonds pensions conjoint",
                            "Modification montant pivot KO");
                }
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void modificationIndexBaremesPP(String trimestre, boolean isSuccess) {
        try {
            homeMenuPage.clickAdministration();
            administrationPage.clickMenuBaremesPersonnesPhysiques();
            administrationPage.clickOngletIndexBaremesPersonnesPhysiques();
            administrationPage.fillTrimestreIndexPersonnesPhysiques(trimestre);

            if (!isSuccess) {
                if (!administrationPage.isBtnEncodageIndexBaremePPEnabled()) {
                    logSuccess("Barèmes PP (Index) - Modification Index inaccessible", "Barèmes PP (Index) OK");
                } else {
                    logFailed("Barèmes PP (Index) - Modification Index accessible", "Barèmes PP (Index) KO");
                }
            } else {
                administrationPage.clickBtnEncodageIndexBaremePP();
                administrationPage.fillNumerateurIndexBaremePP();
                administrationPage.fillDenominateurIndexBaremePP();
                administrationPage.checkAssContinueeIndexBaremePP();
                administrationPage.clickBtnEnregistrerBareme();

                if (TableUtils.isElementPresent("table-index-ref", "101,00")) {
                    logSuccess("Barèmes PP (Index) - Modification Index",
                            "Modification Index OK");
                } else {
                    logFailed("Barèmes PP (Index) - Modification Index",
                            "Modification Index KO");
                }
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void modificationTauxBaremesPP(String trimestre, boolean isSuccess) {
        try {
            homeMenuPage.clickAdministration();
            administrationPage.clickMenuBaremesPersonnesPhysiques();
            administrationPage.clickOngletTauxBaremesPersonnesPhysiques();
            administrationPage.fillTrimestreTauxPersonnesPhysiques(trimestre);

            if (!isSuccess) {
                if (administrationPage.isBtnEncodagePlcPlfMontantPivotBaremePPEnabled()) {
                    logFailed("Barèmes PP (Taux) - Modification taux accessible", "Barèmes PP (Taux) KO");
                } else {
                    logSuccess("Barèmes PP (Taux) - Modification taux inaccessible", "Barèmes PP (Taux) OK");
                }
            } else {
                administrationPage.clickBtnEncodageTauxValeurBaremePP();
                administrationPage.fillTauxBaremePP();
                administrationPage.clickBtnEnregistrerBareme();

                if (TableUtils.isElementPresent("table-taux-valeur", "5,05 %")) {
                    logSuccess("Barèmes PP (Taux) - Modification Taux",
                            "Modification taux OK");
                } else {
                    logFailed("Barèmes PP (Taux) - Modification Taux",
                            "Modification taux KO");
                }
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void modificationMajorationsBaremesPP(String trimestre, boolean isSuccess) {
        try {
            homeMenuPage.clickAdministration();
            administrationPage.clickMenuBaremesPersonnesPhysiques();
            administrationPage.clickOngletMajorationsBaremesPersonnesPhysiques();
            administrationPage.fillTrimestreMajorationsPersonnesPhysiques(trimestre);

            if (!isSuccess) {
                if (!administrationPage.isBtnEncodageMajorationBaremePPEnabled()) {
                    logSuccess("Barèmes PP (Majorations) - Modification Majorations inaccessible", "Barèmes PP (Majorations) OK");
                } else {
                    logFailed("Barèmes PP (Majorations) - Modification Majorations accessible", "Barèmes PP (Majorations) KO");
                }
            } else {
                administrationPage.clickBtnEncodageMajorationBaremePP();
                administrationPage.fillTauxMajorationPP();
                administrationPage.clickBtnEnregistrerBareme();

                if (TableUtils.isElementPresent("table-taux-valeur", "4,07 %")) {
                    logSuccess("Barèmes PP (Majorations) - Modification Majorations",
                            "Modification Majorations OK");
                } else {
                    logFailed("Barèmes PP (Majorations) - Modification Majorations",
                            "Modification Majorations KO");
                }
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }
}
