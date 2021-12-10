package be.ucm.cas.nasca.web.test.miseenveilleuse;


import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class MiseEnVeilleuseAbstract extends MiseEnVeilleuseTestBase {

    private static final String NOM_DOCUMENT = "PM - Mise en veilleuse";

    void miseEnVeilleuseFromExcel(String dateDebutPeriode, String compteSolde, String compteBancaire, String memeEcran,
                                  String annee1, String statutDecision1, String motivation1,
                                  String initiative, String forme1, String annee2,
                                  String statutDecision2, String motivation2, String annee3,
                                  String statutDecision3, String motivation3, String annee4,
                                  String statutDecision4, String motivation4,
                                  List<String> listBceSoldeNul,
                                  List<String> listBceSoldeNegatif, List<String> listBceSoldePositif) {

        loginNasca();

        String bce = null;

        switch (compteSolde) {
            case "nul":
                bce = selectionDossierSoldeNul(dateDebutPeriode, annee1, listBceSoldeNul);
                break;
            case "positif":
                bce = selectionDossierSoldePositif(dateDebutPeriode, annee1, listBceSoldePositif);
                break;
            case "négatif":
                bce = selectionSoldeNegatif(dateDebutPeriode, annee1, listBceSoldeNegatif);
                break;
            default:
                break;
        }

        if (!Boolean.valueOf(compteBancaire)) {
            DaoFunctionality.getNascaDao().deleteCompteBancaireMiseEnVeilleuse(bce);
        }

        if (bce != null) {
            Map<Integer, List<String>> mapDecisions = fillDecisions(annee1, statutDecision1, motivation1, initiative, forme1,
                    annee2, statutDecision2, motivation2, annee3, statutDecision3,
                    motivation3, annee4, statutDecision4, motivation4);

//            Map<Integer, String> mapElementsDocument = fillMapElementsDocument(texte1, texte2, texte3, texte4, texte5,
//                    texteAnnee1, motifAnnee1, texteAnnee2, motifAnnee2,
//                    texteAnnee3, motifAnnee3, texteAnnee4, motifAnnee4);

            boolean demandeMemeEcran = !(StringUtils.isEmpty(memeEcran.trim()) || "false".equals(memeEcran));

            if (!demandeMemeEcran) {
                for (Map.Entry<Integer, List<String>> entry : mapDecisions.entrySet()) {
                    MiseEnVeilleuseTestBase.doMiseEnVeilleuse(bce, entry.getValue().get(0),
                            entry.getValue().get(1), entry.getValue().get(2), entry.getValue().get(3),
                            entry.getValue().get(4));
                }
            } else {
                MiseEnVeilleuseTestBase.doMiseEnVeilleuseDansMemeEcran(bce, mapDecisions);
            }

            String dateDocument = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date());
            ImpressionTestBase.checkImpressionDernierDocument(bce, TestData.TYPE_BCE, dateDocument, null, null, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT);
        } else {
            logSkip(Thread.currentThread().getStackTrace()[1].getFileName(), TestData.PAS_CAS_TROUVE_DB);
        }
    }

    private String selectionSoldeNegatif(String dateDebutPeriode, String annee1, List<String> listBceSoldeNegatif) {
        String bce = null;
        if (listBceSoldeNegatif.isEmpty()) {
            listBceSoldeNegatif = DaoFunctionality.getNascaDao().getBceForMiseEnVeilleuseSoldeNegatif(dateDebutPeriode, annee1);
        }
        if (!listBceSoldeNegatif.isEmpty()) {
            bce = listBceSoldeNegatif.get(0);
            listBceSoldeNegatif.remove(0);
        }
        return bce;
    }

    private String selectionDossierSoldePositif(String dateDebutPeriode, String annee1, List<String> listBceSoldePositif) {
        String bce = null;
        if (listBceSoldePositif.isEmpty()) {
            listBceSoldePositif = DaoFunctionality.getNascaDao().getBceForMiseEnVeilleuseSoldePositif(dateDebutPeriode, annee1);
        }
        if (!listBceSoldePositif.isEmpty()) {
            bce = listBceSoldePositif.get(0);
            listBceSoldePositif.remove(0);
        }
        return bce;
    }

    private String selectionDossierSoldeNul(String dateDebutPeriode, String annee1, List<String> listBceSoldeNul) {
        String bce = null;
        if (listBceSoldeNul.isEmpty()) {
            listBceSoldeNul = DaoFunctionality.getNascaDao().getBceForMiseEnVeilleuseSoldeNul(dateDebutPeriode, annee1);
        }
        if (!listBceSoldeNul.isEmpty()) {
            bce = listBceSoldeNul.get(0);
            listBceSoldeNul.remove(0);
        }
        return bce;
    }
}