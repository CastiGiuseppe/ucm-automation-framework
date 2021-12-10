package be.ucm.cas.nasca.web.test.exonerationpm;

import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class ExonerationPMAbstract extends ExonerationPMTestBase {

    void exonerationPmFromExcel(String id,
                                String dateDebutPeriode, String caisseOrigine, String revision,
                                String dateSocieteCommercial, String dateSignature, String dateReception,
                                String attention, String spAnnee1, String bceAnnee1,
                                String maaAnnee1, String spAnnee2, String bceAnnee2,
                                String maaAnnee2, String spAnnee3, String bceAnnee3,
                                String maaAnnee3, String statut, String explicationStatut,
                                Map<Integer, String> mapRevision, List<String> listBceAvecCO,
                                List<String> listBceSansCO) {
        loginNasca();

        String bce = null;
        if (revision == null || StringUtils.isEmpty(revision.trim())) {
            if ("NULL".equals(caisseOrigine)) {
                if (listBceSansCO.isEmpty()) {
                    listBceSansCO = DaoFunctionality.getNascaDao().getBceforExonerationPMSansCaisseOrigine(dateDebutPeriode);
                }
                bce = doRemoveBce(listBceSansCO, 0);
            } else {
                if (listBceAvecCO.isEmpty()) {
                    listBceAvecCO = DaoFunctionality.getNascaDao().getBceforExonerationPMAvecCaisseOrigine(dateDebutPeriode);
                }
                bce = doRemoveBce(listBceAvecCO, 0);
            }
        }

        Map<Integer, List<Boolean>> mapDecisions = fillMapDecisions(spAnnee1, bceAnnee1, maaAnnee1, spAnnee2,
                bceAnnee2, maaAnnee2, spAnnee3, bceAnnee3, maaAnnee3);

//        Map<Integer, String> mapElementsDocument = fillMapElementsDocument(texte1, texte2, texte3, texte4, texte5, texte6,
//                texteAnnee1, texteAnnee2);
//
        if (bce == null && StringUtils.isEmpty(revision.trim())) {
            logSkip(Thread.currentThread().getStackTrace()[1].getFileName(), TestData.PAS_CAS_TROUVE_DB);
        } else {
            if (bce != null) {
                if ("NULL".equals(caisseOrigine)) {
                    mapRevision.put(Integer.valueOf(id), bce);
                    if ("NULL".equals(dateReception) && "NULL".equals(dateSignature)) {
                        ExonerationPMTestBase.doExonerationPMDate(bce, dateSocieteCommercial, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), mapDecisions, statut, explicationStatut.trim());
                    } else {
                        ExonerationPMTestBase.doExonerationPMcheckDate(bce, dateSocieteCommercial, dateSignature, dateReception, mapDecisions, statut, explicationStatut.trim());
                    }
                } else {
                    ExonerationPMTestBase.doExonerationPMAutreCaisse(bce, dateSocieteCommercial, statut, explicationStatut.trim());
                }
            } else {
                String bceRevision = mapRevision.get(Integer.valueOf(revision));
                bce = bceRevision;
                ExonerationPMTestBase.doRevisionExonerationPMDate(bceRevision, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), mapDecisions, attention, dateSocieteCommercial, statut, explicationStatut.trim());
            }

            if (!statut.equals("erreur")) {
                String dateDocument = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date());
                ImpressionTestBase.checkImpressionDernierDocument(bce, TestData.TYPE_BCE, dateDocument, null, null, TestData.ETAT_A_IMPRIMER, "PM - Exonération");
            }
        }
        finishTestExecution();
    }

    private static String doRemoveBce(List<String> listBce, int position) {
        if (!listBce.isEmpty()){
            String bce = listBce.get(position);
            listBce.remove(position);
            return bce;
        }
        else
        {
            return null;

        }

    }
}