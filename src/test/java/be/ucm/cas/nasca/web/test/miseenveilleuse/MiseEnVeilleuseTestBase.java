package be.ucm.cas.nasca.web.test.miseenveilleuse;

import be.ucm.cas.nasca.web.test.support.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.Map.Entry;

public class MiseEnVeilleuseTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    static void doMiseEnVeilleuse(String bce, String annee, String statut, String motivation, String initiative, String sousForme) {
        try {
            loadNissOrBce(bce);
            gestionClientPage.clickMenuMiseEnVeilleuse();
            gestionClientPage.clickGestionMiseEnVeilleuse();
            gestionClientPage.fillRowMiseEnVeilleuse(1, annee, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), statut, motivation, initiative, sousForme);

            if (TestData.DIFFERE.equals(statut)) {
                gestionClientPage.selectBureauContribution();
            }

            gestionClientPage.clickBtnTerminerMev();

            if (gestionClientPage.isPresentTableMiseEnVeilleuse(annee, statut)) {
                logSuccess("Mise en veilleuse pour " + bce + " - " + annee + " (" + statut + ")");
            } else {
                logFailed("Mise en veilleuse pour " + bce + " - " + annee + " (" + statut + ")");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce, e);
        }
    }

    static void doMiseEnVeilleuseDansMemeEcran(String bce, Map<Integer, List<String>> mapDecisions) {
        try {
            loadNissOrBce(bce);
            gestionClientPage.clickMenuMiseEnVeilleuse();
            gestionClientPage.clickGestionMiseEnVeilleuse();

            int i = 1;
            for (Entry<Integer, List<String>> entry : mapDecisions.entrySet()) {
                String annee = entry.getValue().get(0);
                String statut = entry.getValue().get(1);
                String motivation = entry.getValue().get(2);
                String initiative = entry.getValue().get(3);
                String sousForme = entry.getValue().get(4);

                if (i > 1) {
                    gestionClientPage.clickBtnAjouterDemande();
                }

                gestionClientPage.fillRowMiseEnVeilleuse(i, annee, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), statut, motivation, initiative, sousForme);

                if (TestData.DIFFERE.equals(statut)) {
                    gestionClientPage.selectBureauContribution();
                }

                i++;
            }

            gestionClientPage.clickBtnTerminerMev();

            for (Entry<Integer, List<String>> entry : mapDecisions.entrySet()) {
                String annee = entry.getValue().get(0);
                String statut = entry.getValue().get(1);
                if (gestionClientPage.isPresentTableMiseEnVeilleuse(annee, statut)) {
                    logSuccess("Mise en veilleuse pour " + bce + " - " + annee + " (" + statut + ")");
                } else {
                    logFailed("Mise en veilleuse pour " + bce + " - " + annee + " (" + statut + ")");
                }
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce, e);
        }
    }

    Map<Integer, List<String>> fillDecisions(String annee1, String statutDecision1,
                                             String motivation1, String initiative, String forme, String annee2,
                                             String statutDecision2, String motivation2, String annee3,
                                             String statutDecision3, String motivation3, String annee4,
                                             String statutDecision4, String motivation4) {
        List<List<String>> listDecisions = new ArrayList<>();

        if (annee1 != null) {
            listDecisions.add(makeListDecision(annee1, statutDecision1, motivation1, initiative, forme));
        }
        if (annee2 != null) {
            listDecisions.add(makeListDecision(annee2, statutDecision2, motivation2, initiative, forme));
        }
        if (annee3 != null) {
            listDecisions.add(makeListDecision(annee3, statutDecision3, motivation3, initiative, forme));
        }
        if (annee4 != null) {
            listDecisions.add(makeListDecision(annee4, statutDecision4, motivation4, initiative, forme));
        }
        return makeMapDecision(listDecisions);
    }

    private Map<Integer, List<String>> makeMapDecision(List<List<String>> listDecision) {
        int i = 0;
        Map<Integer, List<String>> mapDecisions = new LinkedHashMap<>();
        for (List<String> list : listDecision) {
            if (list != null) {
                i++;
                mapDecisions.put(i, list);
            }
        }

        return mapDecisions;
    }

    private List<String> makeListDecision(String annee, String statutDecision,
                                          String motivation, String initiative, String forme) {
        if (annee != null && !StringUtils.isEmpty(annee.trim())) {
            List<String> listAnnee = new ArrayList<>();
            listAnnee.add(annee);
            listAnnee.add(statutDecision);
            listAnnee.add(motivation);
            listAnnee.add(initiative);
            listAnnee.add(forme);

            return listAnnee;
        }

        return new ArrayList<>();
    }

    Map<Integer, String> fillMapElementsDocument(String texte1, String texte2,
                                                 String texte3, String texte4, String texte5, String texteAnnee1,
                                                 String motifAnnee1, String texteAnnee2, String motifAnnee2,
                                                 String texteAnnee3, String motifAnnee3, String texteAnnee4,
                                                 String motifAnnee4) {
        String[] stringArray = new String[]{texte1, texte2, texte3, texte4, texte5,
                texteAnnee1, motifAnnee1, texteAnnee2, motifAnnee2,
                texteAnnee3, motifAnnee3, texteAnnee4, motifAnnee4};

        return SeleniumUtils.makeMapElementDocuments(stringArray);
    }
}