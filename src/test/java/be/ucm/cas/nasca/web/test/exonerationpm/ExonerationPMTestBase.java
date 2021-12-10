package be.ucm.cas.nasca.web.test.exonerationpm;

import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.TestBase;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExonerationPMTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    static void doExonerationPMDate(String bce,String dateSocieteCommercial, String dateExoneration, Map<Integer, List<Boolean>> decisions, String statut, String explicationStatut) {
        try {
            loadNissOrBce(bce);
            gestionClientPage.clickMenuExoneration();
            gestionClientPage.clickAddExoneration();
            if(!"NULL".equals(dateSocieteCommercial))
            {
                gestionClientPage.checkImmatriculeeEnTantQueSocieteCommercialeExoneration("true",dateSocieteCommercial);
            }
            gestionClientPage.fillDateSignatureExoneration(dateExoneration);
            gestionClientPage.clickTableDecisionExoneration(decisions);
            logInfo("Exoneration pm pour " + bce, "Situation avant sauvegarde");

            nascaPage.clickBtnTerminerWizard();
            checkStatut(bce, statut, explicationStatut);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce, e);
        }
    }

    static void doRevisionExonerationPMDate(String bce, String dateExoneration, Map<Integer, List<Boolean>> decisions, String attention,String dateSocieteCommercial, String statut, String explicationStatut) {
        try {
            loadNissOrBce(bce);
            gestionClientPage.clickMenuExoneration();
            gestionClientPage.clickAddExoneration();
            gestionClientPage.clickAttention(attention);
            if(!"NULL".equals(dateSocieteCommercial))
            {
                gestionClientPage.checkImmatriculeeEnTantQueSocieteCommercialeExoneration("true",dateSocieteCommercial);
            }
            if (TestData.EXO_REVISION.equals(attention)) {
                gestionClientPage.fillDateSignatureExoneration(dateExoneration);
                gestionClientPage.clickTableDecisionExoneration(decisions);
            }
            logInfo("Exoneration pm pour " + bce, "Situation avant sauvegarde");
            nascaPage.clickBtnTerminerWizard();
            checkStatut(bce, statut, explicationStatut);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce, e);
        }
    }

    static void doExonerationPMAutreCaisse(String bce,String dateSocieteCommercial, String statut, String explicationStatut) {
        try {
            loadNissOrBce(bce);
            gestionClientPage.clickMenuExoneration();
            gestionClientPage.clickAddExoneration();
            if(!"NULL".equals(dateSocieteCommercial))
            {
                gestionClientPage.checkImmatriculeeEnTantQueSocieteCommercialeExoneration("true",dateSocieteCommercial);
            }
            logInfo("Exoneration pm pour " + bce, "Situation avant sauvegarde");
            nascaPage.fermerNotification();
            nascaPage.clickBtnTerminerWizard();
            checkStatut(bce, statut, explicationStatut);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce, e);
        }
    }
    static void doExonerationPMcheckDate(String bce,String dateSocieteCommercial, String dateExoneration,String dateReception, Map<Integer, List<Boolean>> decisions, String statut, String explicationStatut) {
        try {
            loadNissOrBce(bce);
            gestionClientPage.clickMenuExoneration();
            gestionClientPage.clickAddExoneration();
            if(!"NULL".equals(dateSocieteCommercial))
            {
                gestionClientPage.checkImmatriculeeEnTantQueSocieteCommercialeExoneration("true",dateSocieteCommercial);
            }
            gestionClientPage.fillDateSignatureExoneration(dateExoneration);
            gestionClientPage.fillDateReceptionExoneration(dateReception);
            gestionClientPage.clickTableDecisionExoneration(decisions);
            logInfo("Exoneration pm pour " + bce, "Situation avant sauvegarde");
            nascaPage.clickBtnTerminerWizard();
            checkStatut(bce, statut, explicationStatut);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce, e);
        }
    }

    private static void checkStatut(String bce, String statut, String explicationStatut) {
        if (gestionClientPage.isPresentTableExoPm(statut, explicationStatut)) {
            logSuccess("Exoneration pm pour " + bce + " - " + statut);
        }
        else
        {
            if(statut.equals("erreur"))
            {
                logSuccess("Exoneration pm pour " + bce + " - " + statut);
            }
            else
            {
                logFailed("Exoneration pm pour " + bce + " - " + statut);
            }
        }
    }

//	Map<Integer, String> fillMapElementsDocument(String texte1, String texte2,
//												 String texte3, String texte4, String texte5, String texte6,
//												 String texteAnnee1, String texteAnnee2) {
//
//		String[] arrayString = new String[]{ texte1, texte2, texte3, texte4, texte5, texte6, texteAnnee1, texteAnnee2 };
//
//		return SeleniumUtils.makeMapElementDocuments(arrayString);
//	}

    Map<Integer, List<Boolean>> fillMapDecisions(String spAnnee1, String bceAnnee1,
                                                 String maaAnnee1, String spAnnee2, String bceAnnee2,
                                                 String maaAnnee2, String spAnnee3, String bceAnnee3,
                                                 String maaAnnee3) {
        List<List<Boolean>> listDecisions = new ArrayList<>();

        listDecisions.add(makeListDecision(spAnnee1, spAnnee2, spAnnee3));
        listDecisions.add(makeListDecision(bceAnnee1, bceAnnee2, bceAnnee3));
        listDecisions.add(makeListDecision(maaAnnee1, maaAnnee2, maaAnnee3));

        return makeMapDecision(listDecisions);
    }

    private Map<Integer, List<Boolean>> makeMapDecision(List<List<Boolean>> listDecision) {
        int i = 0;
        Map<Integer, List<Boolean>> mapDecisions = new LinkedHashMap<>();
        for (List<Boolean> list : listDecision) {
            if (list != null) {
                i++;
                mapDecisions.put(i, list);
            }
        }
        return mapDecisions;
    }

    private List<Boolean> makeListDecision(String infoAnnee1, String infoAnnee2, String infoAnnee3) {
        if (infoAnnee1 != null && !StringUtils.isEmpty(infoAnnee1)) {
            List<Boolean> listAnnee = new ArrayList<>();
            listAnnee.add(Boolean.valueOf(infoAnnee1));
            listAnnee.add(Boolean.valueOf(infoAnnee2));
            listAnnee.add(Boolean.valueOf(infoAnnee3));

            return listAnnee;
        }
        return new ArrayList<>();
    }
}