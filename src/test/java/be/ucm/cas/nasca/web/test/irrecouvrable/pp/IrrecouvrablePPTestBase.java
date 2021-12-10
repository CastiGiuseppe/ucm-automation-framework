package be.ucm.cas.nasca.web.test.irrecouvrable.pp;

import be.ucm.cas.nasca.web.test.support.*;

import java.text.ParseException;

public class IrrecouvrablePPTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    static void doIrrecouvrable(String niss, String action, String provOuDef, String motif, String modifDatePrescription,
                                String commentaire, String envoiLettre, String periode, String simulationAnnulation, String annulationPartielleOuTotale,
                                String redebitionMajorations) {
        try {
            loadNissOrBce(niss);
            switch (action) {
                case "Ajout":
                    ajoutIrrecouvrable(niss, action, provOuDef, motif, modifDatePrescription, commentaire, envoiLettre, periode);
                    break;
                case "Modification":
                    modificationIrrecouvrable(niss, action, provOuDef, motif, modifDatePrescription, commentaire, periode);
                    break;
                case "Annulation":
                    annulationIrrecouvrable(niss, action, provOuDef, motif, periode, simulationAnnulation, annulationPartielleOuTotale, redebitionMajorations);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void annulationIrrecouvrable(String niss, String action, String provOuDef, String motif, String periode, String simulationAnnulation, String annulationPartielleOuTotale, String redebitionMajorations) throws ParseException {
        gestionClientPage.clickMenuIrrecouvrablesSuivi();
        if (Boolean.valueOf(simulationAnnulation)) {
            gestionClientPage.clickBtnSimulationIrrecouvrable();
        }
        if ("Totale".equals(annulationPartielleOuTotale)) {
            gestionClientPage.checkEntitesComptablesSuiviIrrecouvrable(periode);
            gestionClientPage.clickBtnAnnulationIrrecouvrable();
            if (Boolean.valueOf(redebitionMajorations)) {
                gestionClientPage.clickRedebitionMajorationsOuiIrrecouvrable();
                gestionClientPage.fillDateRedebitionMajorationsIrrecouvrable(DateUtils.getDateFutur(1, TestData.DATE_FORMAT_SIMPLE));
            } else {
                gestionClientPage.clickRedebitionMajorationsNonIrrecouvrable();
            }
            gestionClientPage.clickBtnEnregistrerRedebitionIrrecouvrable();
            if (TableUtils.isTableVide("entitesComptablesTable")) {
                logSuccess(action + " irrecouvrable " + annulationPartielleOuTotale + " pour " + niss + " OK");
            } else {
                logFailed(action + " irrecouvrable pour " + niss + " KO");
            }
        } else {
            TableUtils.clickElementColumnIsPresent("entitesComptablesTable", periode, "11", "1");
            gestionClientPage.fillMontantAAnnulerIrrecouvrable();
            gestionClientPage.clickBtnEnregistrerSuppressionPartielleIrrecouvrable();
            if (gestionClientPage.isPresentTableSuiviIrrecouvrable(periode, provOuDef, motif)) {
                logSuccess(action + " irrecouvrable pour " + niss + " OK");
            } else {
                logFailed(action + " irrecouvrable pour " + niss + " KO");
            }
        }
    }

    private static void modificationIrrecouvrable(String niss, String action, String provOuDef, String motif, String modifDatePrescription, String commentaire, String periode) throws ParseException {
        gestionClientPage.clickMenuIrrecouvrablesSuivi();
        gestionClientPage.checkEntitesComptablesSuiviIrrecouvrable(periode);
        gestionClientPage.clickBtnModificationIrrecouvrable();
        if ("Définitif".equals(provOuDef)) {
            gestionClientPage.clickDefinitifIrrecouvrable();
        } else {
            gestionClientPage.clickProvisoireIrrecouvrable();
        }
        gestionClientPage.selectMotifModificationIrrecouvrable(motif);
        if (Boolean.valueOf(modifDatePrescription)) {
            gestionClientPage.fillDatePrescriptionModificationIrrecouvrable(DateUtils.getDateFutur(1000, TestData.DATE_FORMAT_SIMPLE));
        }
        gestionClientPage.fillCommentaireModificationIrrecouvrable(commentaire);
        gestionClientPage.clickBtnEnregistrerModificationIrrecouvrable();
        if (gestionClientPage.isPresentTableSuiviIrrecouvrable(periode, provOuDef, motif)) {
            logSuccess(action + " irrecouvrable pour " + niss + " OK");
        } else {
            logFailed(action + " irrecouvrable pour " + niss + " KO");
        }
    }

    private static void ajoutIrrecouvrable(String niss, String action, String provOuDef, String motif, String modifDatePrescription, String commentaire, String envoiLettre, String periode) {
        gestionClientPage.clickMenuIrrecouvrablesGestion();
        if ("Définitif".equals(provOuDef)) {
            gestionClientPage.clickPasserEnIrrecouvrableDefinitif();
        } else {
            gestionClientPage.clickPasserEnIrrecouvrableProvisoire();
        }
        gestionClientPage.selectMotifIrrecouvrable(motif);
        gestionClientPage.fillCommentaireIrrecouvrable(commentaire);
        nascaPage.clickBtnSuivantWizard();
        gestionClientPage.checkEntitesComptablesIrrecouvrable(periode);
        nascaPage.clickBtnSuivantWizard();
        if (modifDatePrescription != null && "true".equals(modifDatePrescription)) {
            gestionClientPage.checkEntitesComptablesIrrecouvrable(periode);
            gestionClientPage.clickBtnEditionDatePrescriptionIrrecouvrable();
            gestionClientPage.clickBtnEnregistrerDatePrescriptionIrrecouvrable();
        }
        nascaPage.clickBtnSuivantWizard();

        if (Boolean.valueOf(envoiLettre)) {
            gestionClientPage.clickEnvoiLettreOuiIrrecouvrable();
            gestionClientPage.clickEnvoiLettreContentieuxIrrecouvrable();
        } else {
            gestionClientPage.clickEnvoiLettreNonIrrecouvrable();
        }
        nascaPage.clickBtnTerminerWizard();
        if (gestionClientPage.isPresentTableIrrecouvrable(periode, provOuDef, motif)) {
            logSuccess(action + " irrecouvrable pour " + niss + " OK");
        } else {
            logFailed(action + " irrecouvrable pour " + niss + " KO");
        }
    }
}