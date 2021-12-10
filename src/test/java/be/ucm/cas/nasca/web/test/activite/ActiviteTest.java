package be.ucm.cas.nasca.web.test.activite;

import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class ActiviteTest extends ActiviteTestBase {

    private static final String NISS_ENTREPRISE_PP = "83020814316";

    private static final String NISS_AUTRE = "77050707113";

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Activité.xlsx", "Activité", "Activité", "SignaletiqueActivite", null);
    }

    @Test(dataProvider = "data")
    public void activiteFromExcel(String id, String libelle, String revision,
                                  String actionTypeActivite, String typeActivite, String assujetti,
                                  String princExercee, String donneesValides, String dateDebut,
                                  String dateFin, String comment, String profession,
                                  String appportGestion, String fonction, String forme) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        boolean suppression = revision == null || StringUtils.isEmpty(revision.trim());

        if (revision == null) {
            ajoutActivite(typeActivite, profession, dateDebut, dateFin,
                    fonction, suppression,
                    assujetti, princExercee, donneesValides, comment,
                    appportGestion, forme);
        } else {
            editionActivite(typeActivite, profession, dateDebut, dateFin,
                    fonction, comment, appportGestion, actionTypeActivite);
        }

        finishTestExecution();
    }

    private void ajoutActivite(String typeActivite, String profession,
                               String dateDebut, String dateFin, String fonction,
                               boolean suppression, String assujetti, String princExercee,
                               String donneesValides, String commentaire, String apportGestion, String forme) {
        switch (typeActivite) {
            case TestData.ENTREPRISE_PP:
                ActiviteTestBase.fillActiviteEntreprisePP(NISS_ENTREPRISE_PP, typeActivite,
                        profession, dateDebut, dateFin, suppression,
                        assujetti, princExercee, donneesValides, commentaire);
                break;
            case TestData.ACT_ASSIMILEE:
                ActiviteTestBase.fillActiviteAssimilee(NISS_AUTRE, typeActivite,
                        dateDebut, dateFin, suppression, commentaire, assujetti,
                        princExercee, donneesValides);
                break;
            case TestData.AIDANT_DIRIGEANT_SOC:
                ActiviteTestBase.fillActiviteAidantDirigeantSociete(NISS_AUTRE,
                        typeActivite, "ROBERT", dateDebut, dateFin, profession,
                        suppression, commentaire, assujetti, princExercee, donneesValides);
                break;
            case TestData.AIDANT_ENTREPRISE_PP:
                ActiviteTestBase.fillActiviteAidantEntreprisePP(NISS_AUTRE, typeActivite,
                        NISS_ENTREPRISE_PP, dateDebut, dateFin, profession, suppression, commentaire, assujetti, princExercee,
                        donneesValides);
                break;
            case TestData.ASSOCIE_ACTIF_SOC:
                ActiviteTestBase.fillActiviteAssocieActifSociete(NISS_AUTRE,
                        typeActivite, "VAN", dateDebut, dateFin, profession,
                        suppression, commentaire, assujetti, princExercee, donneesValides, apportGestion);
                break;
            case TestData.AUTRE_ACTIVITE_SOC:
                ActiviteTestBase.fillActiviteAutreSociete(NISS_AUTRE, typeActivite,
                        "VAN", dateDebut, dateFin, profession, suppression,
                        commentaire, assujetti, princExercee, donneesValides);
                break;
            case TestData.MANDATAIRE_SOC:
                ActiviteTestBase.fillActiviteMandataireSociete(NISS_AUTRE, typeActivite,
                        fonction, "VAN", dateDebut, dateFin, profession,
                        suppression, commentaire, assujetti, princExercee, donneesValides, forme);
                break;
            default:
                break;
        }
    }

    private void editionActivite(String typeActivite, String profession,
                                 String dateDebut, String dateFin, String fonction, String commentaire,
                                 String apportGestion, String actionTypeActivite) {
        switch (typeActivite) {
            case TestData.ENTREPRISE_PP:
                ActiviteTestBase.fillEditionActiviteEntreprisePP(NISS_ENTREPRISE_PP,
                        dateDebut, dateFin, commentaire, actionTypeActivite, profession);
                break;
            case TestData.ACT_ASSIMILEE:
                ActiviteTestBase.fillEditionActiviteAssimilee(NISS_AUTRE, typeActivite,
                        dateDebut, dateFin, commentaire);
                break;
            case TestData.AIDANT_DIRIGEANT_SOC:
                ActiviteTestBase.fillEditionActiviteAidantDirigeantSociete(NISS_AUTRE,
                        typeActivite, dateDebut, dateFin, profession,
                        commentaire);
                break;
            case TestData.AIDANT_ENTREPRISE_PP:
                ActiviteTestBase.fillEditionActiviteAidantEntreprisePP(NISS_AUTRE,
                        dateDebut, dateFin, profession, commentaire, actionTypeActivite);
                break;
            case TestData.ASSOCIE_ACTIF_SOC:
                ActiviteTestBase.fillEditionActiviteAssocieActifSociete(NISS_AUTRE,
                        typeActivite, dateDebut, dateFin, profession,
                        commentaire, apportGestion);
                break;
            case TestData.AUTRE_ACTIVITE_SOC:
                ActiviteTestBase.fillEditionActiviteAutreSociete(NISS_AUTRE, typeActivite,
                        dateDebut, dateFin, profession, commentaire);
                break;
            case TestData.MANDATAIRE_SOC:
                ActiviteTestBase.fillEditionActiviteMandataireSociete(NISS_AUTRE,
                        fonction, dateDebut, dateFin, profession,
                        commentaire);
                break;
            default:
                break;
        }
    }
}