package be.ucm.cas.nasca.web.test.support.dao;

import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.nasca.web.test.support.SqlData;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.math.BigDecimal;
import java.util.*;

public class NascaDao extends JdbcDaoSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    public String getNissWithSolde(String trimestre) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_NISS_WITH_SOLDE, new Object[]{trimestre}, String.class);
    }

    public String getReferenceFromPrintQueue(String sujet) {
        try {
            return getJdbcTemplate().queryForObject(SqlData.SELECT_REFERENCE_PRINT_QUEUE, new Object[]{sujet}, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void deleteCinStatistique(String date) {
        getJdbcTemplate().update(SqlData.DELETE_CIN_STATISTIQUE_COURRIER);
        getJdbcTemplate().update(SqlData.DELETE_CIN_STATISTIQUE_OBJET);
        getJdbcTemplate().update(SqlData.DELETE_CIN_COURRIER_WTIH_DATE, date);
    }

    public String getMontantCibleRegularisation(String dossierId, String periode, String dateCreation) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_MONTANT_REGULARISATION_SUITE_REVENU, new Object[]{dossierId, periode, dateCreation}, String.class);
    }

    public String getNissAvecRevenuRDE(String nature, String dateDebut, String type, String statut,
                                       String annee, String revenuDepart, String dateCommunication) {
        try {
            return getJdbcTemplate().queryForObject(SqlData.SELECT_NISS_AVEC_REVENU_RDE, new Object[]{nature, dateDebut, type, statut,
                    annee, revenuDepart, dateCommunication, annee}, String.class);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public String getNissAvecRevenuRDA(String nature, String dateDebut, String type, String statut,
                                       String annee, String revenuDepart, String dateCommunication) {
        try {
            return getJdbcTemplate().queryForObject(SqlData.SELECT_NISS_AVEC_REVENU_RDA, new Object[]{nature, dateDebut, type, statut,
                    annee, revenuDepart, dateCommunication, annee}, String.class);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public String getNissSansRevenuRDE(String nature, String dateDebut, String annee) {
        try {
            return getJdbcTemplate().queryForObject(SqlData.SELECT_NISS_SANS_REVENU_RDE, new Object[]{nature, dateDebut, annee}, String.class);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public String getNissSansRevenuRDA(String nature, String dateDebut, String annee) {
        try {
            return getJdbcTemplate().queryForObject(SqlData.SELECT_NISS_SANS_REVENU_RDA, new Object[]{nature, dateDebut, annee}, String.class);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public String getDocumentImpression() {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_DOCUMENT_IMPRESSION, new Object[]{}, String.class);
    }

    public String getNomDocumentImpression(String documentId) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_NOM_DOCUMENT_IMPRESSION, new Object[]{documentId}, String.class);
    }

    public void updateTypeDocumentImpression(String documentId) {
        getJdbcTemplate().update(SqlData.UPDATE_TYPE_DOCUMENT_IMPRESSION, documentId);
    }

    public String getDocumentImpressionIdentifiant(String documentId, String type) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_DOCUMENT_IMPRESSION_IDENTIFIANT, new Object[]{documentId, type}, String.class);
    }

    public void updateTypeSortieDocumentImpression(String typeSortie, String documentId) {
        getJdbcTemplate().update(SqlData.UPDATE_TYPE_SORTIE_DOCUMENT_IMPRESSION, typeSortie, documentId);
    }

    public void updateEtatDocumentImpression(String typeSortie, String documentId) {
        getJdbcTemplate().update(SqlData.UPDATE_ETAT_DOCUMENT_IMPRESSION, typeSortie, documentId);
    }

    public void updateAnnulationDocumentImpression(String date, String annulateur, String documentId) {
        getJdbcTemplate().update(SqlData.UPDATE_ANNULATION_DOCUMENT_IMPRESSION, date, annulateur, documentId);
    }

    public void updateNullAnnulationDocumentImpression(String documentId) {
        getJdbcTemplate().update(SqlData.UPDATE_NULL_ANNULATION_DOCUMENT_IMPRESSION, documentId);
    }

    public void updateAllTypeDocumentImpression() {
        getJdbcTemplate().update(SqlData.UPDATE_ALL_TYPE_DOCUMENT_IMPRESSION);
    }

    public List<Map<String, Object>> getNissForMedPP_1(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_MED_PP_QUERY_1, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getNissForMedPP_2(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_MED_PP_QUERY_2, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getNissForMedPP_3(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_MED_PP_QUERY_3, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getNissForMedPP_4(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_MED_PP_QUERY_4, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getNissForMedPP_5(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_MED_PP_QUERY_5, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getNissForMedPP_6(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_MED_PP_QUERY_6, stade, solidaire, suspendu, date);
    }

    public List<Map<String, Object>> getNissForMedPP_7(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_MED_PP_QUERY_7, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getBceForMedPM_1(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_MED_PM_QUERY_1, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getBceForMedPM_2(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_MED_PM_QUERY_2, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getBceForMedPM_3(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_MED_PM_QUERY_3, stade, solidaire, suspendu, date);
    }

    public List<Map<String, Object>> getBceForMedPM_4(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_MED_PM_QUERY_4, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getNissForRappelsPP_1(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_RAPPELS_PP_QUERY_1, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getNissForRappelsPP_2(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_RAPPELS_PP_QUERY_2, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getNissForRappelsPP_3(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_RAPPELS_PP_QUERY_3, stade, solidaire, suspendu, date);
    }

    public List<Map<String, Object>> getNissForRappelsPP_4(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_RAPPELS_PP_QUERY_4, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getNissForRappelsPP_5(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_RAPPELS_PP_QUERY_5, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getNissForRappelsPP_6(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_RAPPELS_PP_QUERY_6, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getNissForRappelsPP_7(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_RAPPELS_PP_QUERY_7, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getNissForRappelsPP_8(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_RAPPELS_PP_QUERY_8, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getNissForRappelsPP_9(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_RAPPELS_PP_QUERY_9, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getNissForRappelsPP_10(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_RAPPELS_PP_QUERY_10, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getNissForRappelsPP_11(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_RAPPELS_PP_QUERY_11, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getBceForRappelsPM_1(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_RAPPELS_PM_QUERY_1, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getBceForRappelsPM_2(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_RAPPELS_PM_QUERY_2, stade, solidaire, suspendu, date);
    }

    public List<Map<String, Object>> getBceForRappelsPM_3(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_RAPPELS_PM_QUERY_3, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getBceForRappelsPM_4(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_RAPPELS_PM_QUERY_4, stade, solidaire, suspendu, date, date);
    }

    public List<Map<String, Object>> getBceForRappelsPM_5(String stade, String solidaire, String suspendu, String date) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_RAPPELS_PM_QUERY_5, stade, solidaire, suspendu, date, date);
    }

    public List<String> getNissObservations() {
        return getJdbcTemplate().queryForList(SqlData.SELECT_OBSERVATION, new Object[]{}, String.class);
    }

    public List<String> getNissforMiseAJourNationalite() {
        return getJdbcTemplate().queryForList(SqlData.SELECT_MISE_A_JOUR_NATIONALITE, new Object[]{}, String.class);
    }

    public List<String> getBceForMiseEnVeilleuseSoldeNul(String dateDebutPeriode, String annee) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_MISE_EN_VEILLEUSE_SOLDE_NUL_AVEC_CPTE_BANCAIRE, new Object[]{dateDebutPeriode, annee}, String.class);
    }

    public List<String> getBceForMiseEnVeilleuseSoldePositif(String dateDebutPeriode, String annee) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_MISE_EN_VEILLEUSE_SOLDE_POSITIF_AVEC_CPTE_BANCAIRE, new Object[]{dateDebutPeriode, annee}, String.class);
    }

    public List<String> getBceForMiseEnVeilleuseSoldeNegatif(String dateDebutPeriode, String annee) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_MISE_EN_VEILLEUSE_SOLDE_NEGATIF_AVEC_CPTE_BANCAIRE, new Object[]{dateDebutPeriode, annee}, String.class);
    }

    public void deleteCompteBancaireMiseEnVeilleuse(String numIdentifiant) {
        getJdbcTemplate().update(SqlData.DELETE_CPT_BANCAIRE_MISE_EN_VEILLEUSE, numIdentifiant);
    }

    public void updateDemandeEnEncodage() {
        getJdbcTemplate().update(SqlData.UPDATE_DEMANDE_EN_ENCODAGE);
    }

    public boolean checkIfBaremeEmpty(String annee) {
        try {
            return getJdbcTemplate().queryForObject(SqlData.SELECT_BAREME_BY_YEAR, new Object[]{annee}, String.class).isEmpty();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return true;
        }
    }

    public boolean checkIfEnroEmpty(int annee, String done) {
        try {
            return getJdbcTemplate().queryForObject(SqlData.SELECT_ENRO_BY_YEAR, new Object[]{annee, done}, String.class).isEmpty();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return true;
        }
    }

    public String getNissChangementNatureProfilCessation(String dateEvenement1) {
        String dateEvenement2 = DateUtils.getDatePivotforProfilChange(dateEvenement1, 9);
        String numero;

        try {
            numero = getJdbcTemplate().queryForObject(SqlData.SELECT_PROFIL_CESSATION, new Object[]{dateEvenement1, dateEvenement2}, String.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
        if (numero != null) {
            return getNissFromNumero(numero);
        } else {
            return null;
        }
    }

    public String getNissChangementNatureProfil(String nature,
                                                String dateEvenement, String regime, String typeReduction) {
        List<String> listNumero;

        String mois = dateEvenement.substring(5, 7);

        String datePivot = dateEvenement;

        if ("01".equals(mois)) {
            datePivot = DateUtils.getDatePivotforProfilChange(dateEvenement, 9);
        } else {
            if (!"04".equals(mois)) {
                datePivot = DateUtils.getDatePivotforProfilChange(dateEvenement, 3);
            }
        }

        if (regime == null) {
            regime = "RDA";
        }
        if (typeReduction == null || StringUtils.isBlank(typeReduction)) {
            listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PROFIL_CHANGE_NATURE, new Object[]{nature, datePivot, dateEvenement, regime}, String.class);
        } else {
            listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PROFIL_CHANGE_NATURE_REDUC, new Object[]{nature, datePivot, dateEvenement, typeReduction, regime}, String.class);

        }
        if (listNumero.isEmpty()) {
            if (typeReduction == null || StringUtils.isBlank(typeReduction)) {
                listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PROFIL_CHANGE_NATURE_REVENU, new Object[]{nature, datePivot, dateEvenement, regime}, String.class);
            } else {
                if ("EXO".equals(typeReduction)) {
                    listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PROFIL_CHANGE_NATURE_REDUC_REVENU, new Object[]{nature, datePivot, dateEvenement, typeReduction, regime}, String.class);
                } else {
                    listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PROFIL_CHANGE_NATURE_REDUC_RED_REVENU, new Object[]{nature, datePivot, dateEvenement, typeReduction, regime}, String.class);
                }
            }
        }
        return doGetNissFromNumero(listNumero);
    }

    private String doGetNissFromNumero(List<String> listNumero) {
        if (listNumero.isEmpty()) {
            return null;
        }
        if (listNumero.size() == 1) {
            return getNissFromNumero(listNumero.get(0));
        } else {
            return getNissFromNumero(listNumero.get(SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1)));
        }
    }

    public String getNissSuppressionNatureProfil(String typeEvenement1, String nature1, String exo1, String dateDebut1,
                                                 String typeEvenement2, String nature2, String exo2, String dateDebut2) {
        List<String> listNumero;

        String datePivot1 = DateUtils.getDatePivotforProfilChange(dateDebut1, 9);
        String datePivot2 = DateUtils.getDatePivotforProfilChange(dateDebut2, 9);

        if (!StringUtils.isEmpty(nature2.trim())) {
            if (StringUtils.isEmpty(exo1.trim()) && StringUtils.isEmpty(exo2.trim())) {
                listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PROFIL_SUPPRESSION_NATURE_SANS_REDUC, new Object[]{typeEvenement2, nature2, dateDebut2, dateDebut1, typeEvenement1, nature1, dateDebut1, datePivot1}, String.class);
            } else if (!StringUtils.isEmpty(exo1.trim()) && !StringUtils.isEmpty(exo2.trim())) {
                listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PROFIL_SUPPRESSION_NATURE_EV1_EV2_REDUC, new Object[]{typeEvenement2, nature2, dateDebut2, dateDebut1, exo2, typeEvenement1, nature1, dateDebut1, datePivot1, exo1}, String.class);
            } else if (!StringUtils.isEmpty(exo1.trim()) && StringUtils.isEmpty(exo2.trim())) {
                listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PROFIL_SUPPRESSION_NATURE_EV1_REDUC, new Object[]{typeEvenement2, nature2, dateDebut2, dateDebut1, typeEvenement1, nature1, dateDebut1, datePivot1, exo1}, String.class);
            } else {
                listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PROFIL_SUPPRESSION_NATURE_EV2_REDUC, new Object[]{typeEvenement2, nature2, dateDebut2, dateDebut1, exo2, typeEvenement1, nature1, dateDebut1, datePivot1}, String.class);
            }
        } else {
            if (StringUtils.isEmpty(exo1.trim())) {
                listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PROFIL_SUPPRESSION_NATURE_SANS_REDUC_EVT_FIN, new Object[]{typeEvenement2, datePivot2, dateDebut2, typeEvenement1, nature1, dateDebut1, datePivot1, dateDebut1}, String.class);
            } else {
                listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PROFIL_SUPPRESSION_NATURE_EV1_REDUC_EVT_FIN, new Object[]{typeEvenement2, datePivot2, dateDebut2, typeEvenement1, nature1, dateDebut1, datePivot1, exo1, dateDebut1}, String.class);
            }
        }
        return doGetNissFromNumero(listNumero);
    }

    public String getNissMaxiStatutDataSet(String regime, String typeReduction, String date) {
        List<String> listNumero;

        if (regime == null) {
            regime = "RDA";
        }
        if (StringUtils.isBlank(typeReduction)) {
            listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PROFIL_MAXI_STATUT, new Object[]{date, regime}, String.class);
        } else {
            listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PROFIL_MAXI_STATUT_REDUC, new Object[]{date, typeReduction, regime}, String.class);
        }
        if (listNumero.isEmpty()) {
            return null;
        }
        return getNissFromNumero(listNumero.get(SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1)));
    }

    public String getTacheIkFromNiss(String niss) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_TACHE_IK_FROM_NISS, new Object[]{niss}, String.class);
    }

    public void updateTache(String urgent, String statut, String notification, String tacheIk) {
        getJdbcTemplate().update(SqlData.UPDATE_TACHE, urgent, statut, notification, tacheIk);
    }

    public String getNissforClotureDossierCjtAidant(String dateDebut) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_NISS_CLOTURE_DOSSIER_CJT_AIDANT, new Object[]{dateDebut}, String.class);
    }

    public String getNissforClotureDossierAvecCjtAidantSansExo(String dateDebut) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_NISS_CLOTURE_DOSSIER_AVEC_CJT_AIDANT_SANS_EXO, new Object[]{dateDebut}, String.class);
    }

    public String getNissforClotureDossierAvecCjtAidantAvecExo(String dateDebut) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_NISS_CLOTURE_DOSSIER_AVEC_CJT_AIDANT_AVEC_EXO, new Object[]{dateDebut}, String.class);
    }

    public String getNissForClotureDossier(String dateDebut, String nature) {
        if (StringUtils.isEmpty(nature)) {
            nature = "1";
        }
        if ("5".equals(nature)) {
            return getJdbcTemplate().queryForObject(SqlData.SELECT_NISS_CLOTURE_DOSSIER_CJT_AIDANT, new Object[]{dateDebut}, String.class);
        } else {
            return getJdbcTemplate().queryForObject(SqlData.SELECT_NISS_CLOTURE_DOSSIER_SANS_CJT_AIDANT, new Object[]{nature, dateDebut}, String.class);
        }
    }

    public String getNissNouveauContratPLC(String natureCotisante, String dateDebut, String regime, String nationalite,
                                           String contratEnCours, String contratDormant, String contratCloture, String dateFirstDayOfYear) {
        List<Object> list = new ArrayList<>();

        String query = "SELECT " +
                "NUM_IDENTIFIANT " +
                "FROM NASCA.IDENTIFIANT id " +
                "WHERE TYPE_IDENTIFIANT = 'NISS' " +
                "AND IDENTITE_FK IN " +
                "( " +
                "   SELECT " +
                "   a.identite_ik " +
                "   FROM NASCA.IDENTITE a " +
                "   WHERE a.identite_ik IN " +
                "   ( " +
                "      SELECT " +
                "      IDENTITE_FK " +
                "      FROM NASCA.DOSSIER " +
                "      WHERE DOSSIER_IK IN " +
                "      ( " +
                "         SELECT " +
                "         DOSSIER_FK " +
                "         FROM NASCA.EVENEMENT " +
                "         WHERE DOSSIER_FK IN " +
                "         ( " +
                "            SELECT " +
                "            DOSSIER_FK " +
                "            FROM NASCA.EVENEMENT " +
                "            WHERE NATURE_COTISANTE_FK = ? " +
                "            AND DT_EVENEMENT " + ("RDA".equals(regime) ? " >= ? " : " < ? ") +
                "            AND DT_EVENEMENT < ? " +
                "            AND DT_FIN_PERIODE IS NULL ";

        list.add(natureCotisante);
        list.add(dateDebut);
        list.add(dateFirstDayOfYear);

        query += "            AND TYPE_REDUC_COTISATION IS NULL " +
                "            AND TYPE_EVENEMENT IN('DAC', 'CPR') " +
                "            AND HIS_STATUT_RECORD = 'A' " +
                "            AND STATUT_EVENEMENT = 'ACT' " +
                "         ) " +
                "      ) " +
                "   ) ";

        if (!Boolean.valueOf(contratEnCours) &&
                !Boolean.valueOf(contratDormant) &&
                !Boolean.valueOf(contratCloture)) {
            query += "   AND a.IDENTITE_IK NOT IN " +
                    "   ( " +
                    "      SELECT " +
                    "      IDENTITE_FK " +
                    "      FROM NASCA.DOSSIER " +
                    "      WHERE TYPE = 'PLC' " +
                    "   ) ";
        } else {
            if (Boolean.valueOf(contratEnCours)) {
                query += "   AND a.IDENTITE_IK IN " +
                        "   ( " +
                        "      SELECT " +
                        "      IDENTITE_FK " +
                        "      FROM NASCA.DOSSIER " +
                        "      WHERE TYPE = 'PLC' " +
                        "      AND DOSSIER_IK IN " +
                        "      ( " +
                        "         SELECT " +
                        "         DOSSIER_FK " +
                        "         FROM NASCA.PLC_CONTRAT " +
                        "         WHERE EXISTS " +
                        "         ( " +
                        "         	SELECT * " +
                        "            	FROM NASCA.PLC_CONTRAT " +
                        "            	WHERE DOSSIER_FK = DOSSIER_IK AND STATUT IN('EN_COURS', 'NOUVEAU') " +
                        "         ) " +
                        "      ) " +
                        "   ) ";
            } else if (Boolean.valueOf(contratDormant)) {
                query += "   AND a.IDENTITE_IK IN " +
                        "   ( " +
                        "      SELECT " +
                        "      IDENTITE_FK " +
                        "      FROM NASCA.DOSSIER " +
                        "      WHERE TYPE = 'PLC' " +
                        "      AND DOSSIER_IK IN " +
                        "      ( " +
                        "         SELECT " +
                        "         DOSSIER_FK " +
                        "         FROM NASCA.PLC_CONTRAT " +
                        "         WHERE EXISTS " +
                        "         ( " +
                        "         	SELECT * " +
                        "            	FROM NASCA.PLC_CONTRAT " +
                        "            	WHERE DOSSIER_FK = DOSSIER_IK AND STATUT = 'DORMANT' " +
                        "         ) " +
                        "         AND NOT EXISTS " +
                        "         ( " +
                        "         	SELECT * " +
                        "            	FROM NASCA.PLC_CONTRAT " +
                        "            	WHERE DOSSIER_FK = DOSSIER_IK AND STATUT IN('EN_COURS', 'NOUVEAU') " +
                        "         ) " +
                        "      ) " +
                        "   ) ";
            } else {
                query += "   AND a.IDENTITE_IK IN " +
                        "   ( " +
                        "      SELECT " +
                        "      IDENTITE_FK " +
                        "      FROM NASCA.DOSSIER " +
                        "      WHERE TYPE = 'PLC' " +
                        "      AND DOSSIER_IK IN " +
                        "      ( " +
                        "         SELECT " +
                        "         DOSSIER_FK " +
                        "         FROM NASCA.PLC_CONTRAT " +
                        "         WHERE EXISTS " +
                        "         ( " +
                        "         	SELECT * " +
                        "            	FROM NASCA.PLC_CONTRAT " +
                        "            	WHERE DOSSIER_FK = DOSSIER_IK AND STATUT = 'CLOTURE' " +
                        "         ) " +
                        "         AND NOT EXISTS " +
                        "         ( " +
                        "         	SELECT * " +
                        "            	FROM NASCA.PLC_CONTRAT " +
                        "            	WHERE DOSSIER_FK = DOSSIER_IK AND STATUT IN('EN_COURS', 'NOUVEAU') " +
                        "         ) " +
                        "      ) " +
                        "   ) ";
            }
        }

        if ("RDA".equals(regime)) {
            query += " AND not exists(SELECT * FROM NASCA.REVENU WHERE PP_FK = a.IDENTITE_IK) ";
        }

        query += "   AND a.IDENTITE_IK IN " +
                "   ( " +
                "         	 SELECT pp.IDENTITE_IK " +
                "		      FROM NASCA.PERSONNE_PHYSIQUE pp, NASCAPARAM.PAYS p, NASCAPARAM.PAYS d " +
                "		      WHERE pp.PAYS_NAISS_FK = p.PAYS_IK " +
                "		      and p.CODE_INS = 150 " +
                "		      and pp.CODE_NATIONALITE_FK = d.PAYS_IK " +
                "		      and d.CODE_INS = ? " +
                "   ) " +
                ") " +
                "FETCH FIRST 60 ROWS ONLY ";

        if (nationalite != null && !StringUtils.isEmpty(nationalite.trim())) {
            list.add(nationalite);
        } else {
            list.add("150");
        }

        List<String> lissNiss = getJdbcTemplate().queryForList(query, list.toArray(), String.class);

        if (!lissNiss.isEmpty()) {
            return lissNiss.get(SeleniumUtils.getRandomNumberBetween(0, lissNiss.size() - 1));
        } else {
            return null;
        }
    }

    public List<String> getNissForIrrecouvrable(String periode) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_NISS_IRRECOUVRABLE, new Object[]{periode}, String.class);
    }

    public List<String> getNissForPrescriptionPP(String periode) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_NISS_PRESCRIPTION_PP, new Object[]{periode}, String.class);
    }

    public List<String> getBceForPrescriptionPM(String periode) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_BCE_PRESCRIPTION_PM, new Object[]{periode}, String.class);
    }

    public List<String> getNissForLeveeMajoPPAvecCotisation(String periode) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_NISS_LEVEE_MAJO_PP_AVEC_COTISATION, new Object[]{periode}, String.class);
    }

    public List<String> getNissForLeveeMajoPPSansCotisation(String periode) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_NISS_LEVEE_MAJO_PP_SANS_COTISATION, new Object[]{periode}, String.class);
    }

    public List<String> getBceForLeveeMajoPMAvecCotisation(String periode) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_BCE_LEVEE_MAJO_PM_AVEC_COTISATION, new Object[]{periode}, String.class);
    }

    public List<String> getBceForLeveeMajoPMSansCotisation(String periode) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_BCE_LEVEE_MAJO_PM_SANS_COTISATION, new Object[]{periode}, String.class);
    }

    public List<String> getNissForPlanApurement(String periode) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_NISS_PA, new Object[]{periode, periode}, String.class);
    }

    public List<String> getBceForPlanApurement(String periode) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_BCE_PA, new Object[]{periode}, String.class);
    }

    public List<String> getBceforExonerationPMSansCaisseOrigine(String dateDebutAffiliation) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_BCE_EXONERATION_SANS_CO_MEV, new Object[]{dateDebutAffiliation}, String.class);
    }

    public List<String> getBceforExonerationPMAvecCaisseOrigine(String dateDebutAffiliation) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_BCE_EXONERATION_AVEC_CO_MEV, new Object[]{dateDebutAffiliation}, String.class);
    }


    public List<String> getNissforSignaletique() {
        return getJdbcTemplate().queryForList(SqlData.SELECT_NISS_SIGNALETIQUE, new Object[]{}, String.class);
    }

    public String getNissForSolidaritePP() {
        List<String> lissNiss = getJdbcTemplate().queryForList(SqlData.SELECT_NISS_SOLIDARITE_PP, new Object[]{}, String.class);

        if (!lissNiss.isEmpty()) {
            return lissNiss.get(SeleniumUtils.getRandomNumberBetween(0, lissNiss.size() - 1));
        } else {
            return null;
        }
    }

    public String getBceForSolidaritePM() {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_BCE_SOLIDARITE_PM, new Object[]{}, String.class);
    }

    public List<String> getNissForSolidariteDebiteurCodebiteurPP() {
        return getJdbcTemplate().queryForList(SqlData.SELECT_NISS_SOLIDARITE_DEBITEUR_CODEBITEUR_PP, new Object[]{}, String.class);
    }

    public List<String> getBceForSolidariteDebiteurCodebiteurPM() {
        return getJdbcTemplate().queryForList(SqlData.SELECT_BCE_SOLIDARITE_DEBITEUR_CODEBITEUR_PM, new Object[]{}, String.class);
    }

    public void prepareAffectationComptable(String currentDate) {
        getJdbcTemplate().update(SqlData.INSERT_PAIEMENT_COMPTABLE_PORTEFEUILLE, currentDate);
    }

    public void updateAffectationComptable(String currentDate) {
        getJdbcTemplate().update(SqlData.UPDATE_PAIEMENT_COMPTABLE_PORTEFEUILLE, currentDate);
    }

    public void doControlVCSCompta(String nissorbce, String annee, String trimestre) {
        List<Map<String, Object>> numero = getNumerosfromNiss(nissorbce);

        for (Map<String, Object> featureService : numero) {

            for (Map.Entry<String, Object> entry : featureService.entrySet()) {
                int entitecomptaId = getJdbcTemplate().queryForObject(SqlData.SELECT_ENTITE_COMPTA_PERIODE, new Object[]{annee, trimestre, entry.getValue().toString()}, Integer.class);
                int vcsId = getVsId(entry.getValue().toString());
                getJdbcTemplate().update(SqlData.INSERT_VCS_ENTITECOMPTA, vcsId, entitecomptaId);
            }
        }
    }

    public void initialisationDateEnrolementMajoPM(int annee, int mois, Date dateEnrolement) {
        getJdbcTemplate().update(SqlData.DELETE_DATE_ENROLEMENT_MAJO_PM);
        getJdbcTemplate().update(SqlData.INSERT_DATE_ENROLEMENT_MAJO_PM, annee, mois, dateEnrolement);
    }

    public void updateExecuteFalseDateEnrolementMajoPPTrimestrielle(int annee, int mois) {
        getJdbcTemplate().update(SqlData.UPDATE_DATE_ENROLEMENT_MAJO_PP_TRIM, annee, mois);
    }

    public void updateExecuteFalseDateEnrolementMajoPPAnnuelle(int annee) {
        getJdbcTemplate().update(SqlData.UPDATE_DATE_ENROLEMENT_MAJO_PP_ANN, annee);
    }

    public void initialisationDatePaiement(Date date) {
        getJdbcTemplate().update(SqlData.INSERT_DATE_PAIEMENT1, DateUtils.doFormat(TestData.DATE_FORMAT_XML, DateUtils.getDateFuturOrPass(30, date)));
        getJdbcTemplate().update(SqlData.INSERT_DATE_PAIEMENT2, DateUtils.doFormat(TestData.DATE_FORMAT_XML, DateUtils.getDateFuturOrPass(60, date)));
    }

    public void initialisationDateEnrolementMajoPPTrimestrielle(int annee, int trimestre, Date dateEnrolement) {
        getJdbcTemplate().update(SqlData.DELETE_DATE_ENROLEMENT_MAJO_PP_TRIM);
        getJdbcTemplate().update(SqlData.INSERT_DATE_ENROLEMENT_MAJO_PP_TRIM, annee, trimestre, dateEnrolement);
    }

    public void initialisationDateEnrolementMajoPPTrimestrielle(int annee, int trimestre1, int trimestre2, Date dateEnrolement1, Date dateEnrolement2) {
        getJdbcTemplate().update(SqlData.DELETE_DATE_ENROLEMENT_MAJO_PP_TRIM);
        getJdbcTemplate().update(SqlData.INSERT_DATE_ENROLEMENT_MAJO_PP_TRIM, annee, trimestre1, dateEnrolement1);
        getJdbcTemplate().update(SqlData.INSERT_DATE_ENROLEMENT_MAJO_PP_TRIM, annee, trimestre2, dateEnrolement2);
    }

    public void initialisationDateEnrolementMajoPPAnnuelle(int annee, Date dateEnrolement) {
        getJdbcTemplate().update(SqlData.DELETE_DATE_ENROLEMENT_MAJO_PP_ANN);
        getJdbcTemplate().update(SqlData.INSERT_DATE_ENROLEMENT_MAJO_PP_ANN, annee, dateEnrolement);
    }

    public void initialisationDateEnrolementAnnuel(String annee, String dateEnrol) {
        getJdbcTemplate().update(SqlData.UPDATE_BAREME_PM);
        getJdbcTemplate().update(SqlData.UPDATE_ALL_ENROLEMENT_ANNUEL_EXECUTE);
        getJdbcTemplate().update(SqlData.DELETE_ENROLEMENT_ANNUEL1, annee);
        getJdbcTemplate().update(SqlData.INSERT_ENROLEMENT_ANNUEL, annee, dateEnrol);
    }

    public void updateDateFinValiditeBaremePM() {
        getJdbcTemplate().update(SqlData.UPDATE_BAREME_PM);
    }

    public void updateDateEnrolementAnnuelExecute(String annee) {
        getJdbcTemplate().update(SqlData.UPDATE_ENROLEMENT_ANNUEL_EXECUTE, annee);
    }

    public String getNissFromNumero(String niss) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_NISS_FROM_NUMERO, new Object[]{niss}, String.class);
    }

    public void deleteFichierInastiContributions() {
        getJdbcTemplate().update(SqlData.DELETE_FICHIER_INASTI_CONTRIBUTIONS);
    }

    public void updateOpenErpSyncStatusToSentByNiss(String nissNumber) {
        getJdbcTemplate().update(SqlData.UPDATE_OPENERP_SYNC_TO_SENT_BY_NISS, nissNumber);
    }

    public void updateEvenementToActive(String nissorBceNumber) {
        getJdbcTemplate().update(SqlData.UPDATE_EVENEMENT_ACTIF, SeleniumUtils.getConvertedStringFormat(nissorBceNumber));
        getJdbcTemplate().update(SqlData.UPDATE_EVENEMENT_ACTIF, nissorBceNumber);
    }

    public void deleteCarriereMouvement(String niss) {
        clean(SqlData.DELETE_CARRIERE_MVMT, getIdentiteID(SqlData.SELECT_IDENTITE_NISS, niss));
    }

    public void cleanUpDB(String nissorbcenumber) {
        try {
            List<Map<String, Object>> listMap = getWorkflowIDforAttributionNiss(nissorbcenumber);
            List<Map<String, Object>> listMap2 = getWorkflowIDforAttributionId(nissorbcenumber);
            List<Map<String, Object>> listidniss = getIdentiteID(SqlData.SELECT_IDENTITE_NISS, nissorbcenumber);
            List<Map<String, Object>> listid = getIdentiteID(SqlData.SELECT_IDENTITE_IK, SeleniumUtils.getConvertedStringFormat(nissorbcenumber));
            List<Map<String, Object>> listVcs = getVcsIdfromEntitCompta(getIdentiteID(SqlData.SELECT_IDENTITE_NISS, nissorbcenumber));
            List<Map<String, Object>> listVcs2 = getVcsIdfromEntitCompta(getIdentiteID(SqlData.SELECT_IDENTITE_NISS, SeleniumUtils.getConvertedStringFormat(nissorbcenumber)));

            LOGGER.info("Debut cleanUPDB for NISS/BCE: " + nissorbcenumber);

            clean(SqlData.UPDATE_REM, listid);
            clean(SqlData.UPDATE_REM, listidniss);

            clean(SqlData.DELETE_DONNE_METIER, listid);
            clean(SqlData.DELETE_DONNE_METIER, listidniss);

            clean(SqlData.DELETE_ELEMENT_MANQUANT, listid);
            clean(SqlData.DELETE_ELEMENT_MANQUANT, listidniss);

            clean(SqlData.DELETE_REM_COURRIER, listid);
            clean(SqlData.DELETE_REM_COURRIER, listidniss);

            clean(SqlData.DELETE_REM, listid);
            clean(SqlData.DELETE_REM, listidniss);

            clean(SqlData.DELETE_TRACKING_FIELD, listid);
            clean(SqlData.DELETE_TRACKING_FIELD, listidniss);

            clean(SqlData.DELETE_TRACKING_TABLE, listid);
            clean(SqlData.DELETE_TRACKING_TABLE, listidniss);

            clean(SqlData.DELETE_VARIABLE_TACHE, listid);
            clean(SqlData.DELETE_VARIABLE_TACHE, listidniss);

            clean(SqlData.DELETE_VARIABLE_WORKFLOW, listid);
            clean(SqlData.DELETE_VARIABLE_WORKFLOW, listidniss);

            clean(SqlData.DELETE_TEMP, listid);
            clean(SqlData.DELETE_TEMP, listidniss);

            clean(SqlData.DELETE_TEMP_FICHIER, listid);
            clean(SqlData.DELETE_TEMP_FICHIER, listidniss);

            clean(SqlData.DELETE_FICHIER_BINAIRE, listid);
            clean(SqlData.DELETE_FICHIER_BINAIRE, listidniss);

            clean(SqlData.DELETE_SUPPORT_INFO_FICH, listid);
            clean(SqlData.DELETE_SUPPORT_INFO_FICH, listidniss);

            clean(SqlData.DELETE_SUPPORT_INFO, listid);
            clean(SqlData.DELETE_SUPPORT_INFO, listidniss);

            clean(SqlData.DELETE_TACHES, listid);
            clean(SqlData.DELETE_TACHES, listidniss);

            clean(SqlData.DELETE_WORKFLOW, listid);
            clean(SqlData.DELETE_WORKFLOW, listidniss);

            clean(SqlData.DELETE_PLC_PRIME, listid);
            clean(SqlData.DELETE_PLC_PRIME, listidniss);

            clean(SqlData.DELETE_PLC_REVENU, listid);
            clean(SqlData.DELETE_PLC_REVENU, listidniss);

            clean(SqlData.DELETE_PLC_PERIODE, listid);
            clean(SqlData.DELETE_PLC_PERIODE, listidniss);

            clean(SqlData.DELETE_PLC_PROFIL, listid);
            clean(SqlData.DELETE_PLC_PROFIL, listidniss);

            clean(SqlData.DELETE_PLC_MONTANT_MAX, listid);
            clean(SqlData.DELETE_PLC_MONTANT_MAX, listidniss);

            clean(SqlData.DELETE_PLC_EVENEMENT, listid);
            clean(SqlData.DELETE_PLC_EVENEMENT, listidniss);

            clean(SqlData.DELETE_PLC_CONTRAT, listid);
            clean(SqlData.DELETE_PLC_CONTRAT, listidniss);

            clean(SqlData.DELETE_DECISION_PLC, listid);
            clean(SqlData.DELETE_DECISION_PLC, listidniss);

            clean(SqlData.DELETE_DECISION_AFFIPP, listid);
            clean(SqlData.DELETE_DECISION_AFFIPP, listidniss);

            clean(SqlData.DELETE_DECISION_AFFIPM, listid);
            clean(SqlData.DELETE_DECISION_AFFIPM, listidniss);

            clean(SqlData.DELETE_DECISION_EXONERATION, listid);
            clean(SqlData.DELETE_DECISION_EXONERATION, listidniss);

            clean(SqlData.DELETE_EVENEMENT_DECISION, listid);
            clean(SqlData.DELETE_EVENEMENT_DECISION, listidniss);

            clean(SqlData.DELETE_DECISION, listid);
            clean(SqlData.DELETE_DECISION, listidniss);

            clean(SqlData.DELETE_DECISION_AFFIPM, listid);
            clean(SqlData.DELETE_DECISION_AFFIPM, listidniss);

            clean(SqlData.DELETE_DECISION_AFFIPP, listid);
            clean(SqlData.DELETE_DECISION_AFFIPP, listidniss);

            clean(SqlData.DELETE_DEMANDE_CHGT_PROFIL, listid);
            clean(SqlData.DELETE_DEMANDE_CHGT_PROFIL, listidniss);

            clean(SqlData.DELETE_DEMANDE_REVENU, listid);
            clean(SqlData.DELETE_DEMANDE_REVENU, listidniss);

            clean(SqlData.DELETE_DEMANDE_APPORTEUR, listid);
            clean(SqlData.DELETE_DEMANDE_APPORTEUR, listidniss);

            clean(SqlData.DELETE_DEMANDE_CODEBITEUR, listid);
            clean(SqlData.DELETE_DEMANDE_CODEBITEUR, listidniss);

            clean(SqlData.DELETE_DEMANDE_PLC, listid);
            clean(SqlData.DELETE_DEMANDE_PLC, listidniss);

            clean(SqlData.DELETE_ENTITE_SUSPENSION, listid);
            clean(SqlData.DELETE_ENTITE_SUSPENSION, listidniss);

            clean(SqlData.DELETE_SUSPENSION_EXO, listid);
            clean(SqlData.DELETE_SUSPENSION_EXO, listidniss);

            clean(SqlData.DELETE_DEMANDE_EXONERATION, listid);
            clean(SqlData.DELETE_DEMANDE_EXONERATION, listidniss);

            clean(SqlData.DELETE_EVENEMENT_DEMANDE, listid);
            clean(SqlData.DELETE_EVENEMENT_DEMANDE, listidniss);

            clean(SqlData.DELETE_DEMANDE, listid);
            clean(SqlData.DELETE_DEMANDE, listidniss);

            getJdbcTemplate().update(SqlData.DELETE_INASTI_MESSAGE_HISTORY, nissorbcenumber);

            clean(SqlData.DELETE_PRINT_QUEUE, listid);
            clean(SqlData.DELETE_PRINT_QUEUE, listidniss);

            // TODO activer
            //getJdbcTemplate().update(SqlData.DELETE_PARTNER, nissorbcenumber);

            clean(SqlData.DELETE_BON_RETOUR_MUTUELLE, listid);
            clean(SqlData.DELETE_BON_RETOUR_MUTUELLE, listidniss);

            clean(SqlData.DELETE_BON_MUTUELLE, listid);
            clean(SqlData.DELETE_BON_MUTUELLE, listidniss);

            clean(SqlData.DELETE_CARRIERE_MVMT, listid);
            clean(SqlData.DELETE_CARRIERE_MVMT, listidniss);

            clean(SqlData.DELETE_ACTIVITE_PROF, listid);
            clean(SqlData.DELETE_ACTIVITE_PROF, listidniss);
            clean(SqlData.DELETE_ACTIVITE_PROF_LIEE, listid);
            clean(SqlData.DELETE_ACTIVITE_PROF_LIEE, listidniss);

            clean(SqlData.DELETE_ACTIVITE_ASSOCIE, listid);
            clean(SqlData.DELETE_ACTIVITE_ASSOCIE, listidniss);

            clean(SqlData.DELETE_ACTIVITE_MANDATAIRE, listid);
            clean(SqlData.DELETE_ACTIVITE_MANDATAIRE, listidniss);

            clean(SqlData.DELETE_ACTIVITE_MANDATAIRE_LIEE, listid);
            clean(SqlData.DELETE_ACTIVITE_MANDATAIRE_LIEE, listidniss);

            clean(SqlData.DELETE_ACTIVITE_LIEE, listid);
            clean(SqlData.DELETE_ACTIVITE_LIEE, listidniss);

            clean(SqlData.DELETE_ACTIVITE, listid);
            clean(SqlData.DELETE_ACTIVITE, listidniss);

            clean(SqlData.DELETE_OPERATION_COMPTA, listid);
            clean(SqlData.DELETE_OPERATION_COMPTA, listidniss);

            clean(SqlData.DELETE_PROCED_RECOUVR, listid);
            clean(SqlData.DELETE_PROCED_RECOUVR, listidniss);

            clean(SqlData.DELETE_COMPTA_PROCED_LIEE, listid);
            clean(SqlData.DELETE_COMPTA_PROCED_LIEE, listidniss);

            clean(SqlData.DELETE_VCS_ENTITE_COMPTABLE, listid);
            clean(SqlData.DELETE_VCS_ENTITE_COMPTABLE, listidniss);
            clean(SqlData.DELETE_VCS, listVcs);
            clean(SqlData.DELETE_VCS, listVcs2);

            clean(SqlData.DELETE_RECOUVREMENT, listid);
            clean(SqlData.DELETE_RECOUVREMENT, listidniss);

            clean(SqlData.DELETE_PROPOSITION_AFFECTATION, listid);
            clean(SqlData.DELETE_PROPOSITION_AFFECTATION, listidniss);

            clean(SqlData.DELETE_PERIODE_DETAILS, listid);
            clean(SqlData.DELETE_PERIODE_DETAILS, listidniss);

            clean(SqlData.DELETE_COMPTA_RECOUVREMENT, listid);
            clean(SqlData.DELETE_COMPTA_RECOUVREMENT, listidniss);

            clean(SqlData.DELETE_COMPTA_APPUREMENT, listid);
            clean(SqlData.DELETE_COMPTA_APPUREMENT, listidniss);

            clean(SqlData.DELETE_COTI_DISPENSE, listid);
            clean(SqlData.DELETE_COTI_DISPENSE, listidniss);

            clean(SqlData.DELETE_ENTITE_COMPTABLE_PROCEDURE1, listid);
            clean(SqlData.DELETE_ENTITE_COMPTABLE_PROCEDURE1, listidniss);

            clean(SqlData.DELETE_CTX_EC_PRINCIPAL_BLOC, listid);
            clean(SqlData.DELETE_CTX_EC_PRINCIPAL_BLOC, listidniss);

            clean(SqlData.DELETE_ENTITE_COMPTABLE_CTX_BLOC_DECISION, listid);
            clean(SqlData.DELETE_ENTITE_COMPTABLE_CTX_BLOC_DECISION, listidniss);

            clean(SqlData.DELETE_ENTITE_COMPTABLE, listid);
            clean(SqlData.DELETE_ENTITE_COMPTABLE, listidniss);

            clean(SqlData.DELETE_COMMENTAIRE_PROCEDURE1, listid);
            clean(SqlData.DELETE_COMMENTAIRE_PROCEDURE1, listidniss);

            clean(SqlData.DELETE_STATUT_PROCEDURE1, listid);
            clean(SqlData.DELETE_STATUT_PROCEDURE1, listidniss);

            clean(SqlData.DELETE_PROCEDURE1, listid);
            clean(SqlData.DELETE_PROCEDURE1, listidniss);

            clean(SqlData.DELETE_COMMENTAIRE_PROCEDURE2, listid);
            clean(SqlData.DELETE_COMMENTAIRE_PROCEDURE2, listidniss);

            clean(SqlData.DELETE_ENTITE_COMPTABLE_PROCEDURE2, listid);
            clean(SqlData.DELETE_ENTITE_COMPTABLE_PROCEDURE2, listidniss);

            clean(SqlData.DELETE_STATUT_PROCEDURE2, listid);
            clean(SqlData.DELETE_STATUT_PROCEDURE2, listidniss);

            clean(SqlData.DELETE_PROCEDURE2, listid);
            clean(SqlData.DELETE_PROCEDURE2, listidniss);

            clean(SqlData.DELETE_COMMENTAIRE_PROCEDURE3, listid);
            clean(SqlData.DELETE_COMMENTAIRE_PROCEDURE3, listidniss);

            clean(SqlData.DELETE_ENTITE_COMPTABLE_PROCEDURE3, listid);
            clean(SqlData.DELETE_ENTITE_COMPTABLE_PROCEDURE3, listidniss);

            clean(SqlData.DELETE_STATUT_PROCEDURE3, listid);
            clean(SqlData.DELETE_STATUT_PROCEDURE3, listidniss);

            clean(SqlData.DELETE_PROCEDURE3, listid);
            clean(SqlData.DELETE_PROCEDURE3, listidniss);

            clean(SqlData.DELETE_PERIODE_CORIPP, listid);
            clean(SqlData.DELETE_PERIODE_CORIPP, listidniss);

            clean(SqlData.DELETE_SOLIDARITE_VERSION, listid);
            clean(SqlData.DELETE_SOLIDARITE_VERSION, listidniss);

            clean(SqlData.DELETE_SOLIDARITE_CODEBITEUR, listid);
            clean(SqlData.DELETE_SOLIDARITE_CODEBITEUR, listidniss);

            clean(SqlData.DELETE_SOLIDARITE, listid);
            clean(SqlData.DELETE_SOLIDARITE, listidniss);

            clean(SqlData.DELETE_EXO_PM, listid);
            clean(SqlData.DELETE_EXO_PM, listidniss);

            clean(SqlData.DELETE_LIEN_DOSSIER_ADRESSE, listid);
            clean(SqlData.DELETE_LIEN_DOSSIER_ADRESSE, listidniss);

            clean(SqlData.DELETE_EVENEMENT, listid);
            clean(SqlData.DELETE_EVENEMENT, listidniss);

            clean(SqlData.DELETE_EVENEMENT_ID, listid);
            clean(SqlData.DELETE_EVENEMENT_ID, listidniss);

            clean(SqlData.DELETE_REGIME, listid);
            clean(SqlData.DELETE_REGIME, listidniss);

            clean(SqlData.DELETE_REVENU, listid);
            clean(SqlData.DELETE_REVENU, listidniss);

            clean(SqlData.DELETE_CARRIERE_COMMUNIQUEE, listid);
            clean(SqlData.DELETE_CARRIERE_COMMUNIQUEE, listidniss);

            clean(SqlData.DELETE_PP, listid);
            clean(SqlData.DELETE_PP, listidniss);

            clean(SqlData.DELETE_BILAN_SOCIETE, listid);
            clean(SqlData.DELETE_BILAN_SOCIETE, listidniss);

            clean(SqlData.DELETE_FORME_JURIDIQUE, listid);
            clean(SqlData.DELETE_FORME_JURIDIQUE, listidniss);

            clean(SqlData.DELETE_SITUATION_JURIDIQUE, listid);
            clean(SqlData.DELETE_SITUATION_JURIDIQUE, listidniss);

            clean(SqlData.DELETE_PM, listid);
            clean(SqlData.DELETE_PM, listidniss);

            clean(SqlData.DELETE_LIEN_PP, listid);
            clean(SqlData.DELETE_LIEN_PP, listidniss);

            clean(SqlData.DELETE_LIEN_PP_LIE, listid);
            clean(SqlData.DELETE_LIEN_PP_LIE, listidniss);

            clean(SqlData.DELETE_PHONEME, listid);
            clean(SqlData.DELETE_PHONEME, listidniss);

            clean(SqlData.DELETE_PERIODE_AFFILIATION, listid);
            clean(SqlData.DELETE_PERIODE_AFFILIATION, listidniss);

            clean(SqlData.DELETE_CIN_COURRIER, listid);
            clean(SqlData.DELETE_CIN_COURRIER, listidniss);

            clean(SqlData.DELETE_COMPTE, listid);
            clean(SqlData.DELETE_COMPTE, listidniss);

            clean(SqlData.DELETE_MISE_VEILLEUSE, listid);
            clean(SqlData.DELETE_MISE_VEILLEUSE, listidniss);

            clean(SqlData.DELETE_DEMANDE_MISE_VEILLEUSE, listid);
            clean(SqlData.DELETE_DEMANDE_MISE_VEILLEUSE, listidniss);

            clean(SqlData.DELETE_CONTACT, listid);
            clean(SqlData.DELETE_CONTACT, listidniss);

            clean(SqlData.DELETE_COORDONNEE, listid);
            clean(SqlData.DELETE_COORDONNEE, listidniss);

            clean(SqlData.DELETE_DATE_IMPORTANTE, listid);
            clean(SqlData.DELETE_DATE_IMPORTANTE, listidniss);

            clean(SqlData.DELETE_DEMANDE_CESSATION, listid);
            clean(SqlData.DELETE_DEMANDE_CESSATION, listidniss);

            clean(SqlData.DELETE_DEMANDE_LM, listid);
            clean(SqlData.DELETE_DEMANDE_LM, listidniss);

            clean(SqlData.DELETE_GED_DEMANDE, listid);
            clean(SqlData.DELETE_GED_DEMANDE, listidniss);

            clean(SqlData.DELETE_RET_DEMANDE_ATTE, listid);
            clean(SqlData.DELETE_RET_DEMANDE_ATTE, listidniss);

            clean(SqlData.DELETE_RET_DEMANDE_PAPIER, listid);
            clean(SqlData.DELETE_RET_DEMANDE_PAPIER, listidniss);

            clean(SqlData.DELETE_RET_HISTORIQUE_RETRAIT, listid);
            clean(SqlData.DELETE_RET_HISTORIQUE_RETRAIT, listidniss);

            clean(SqlData.DELETE_RET_LISTE_IMPRESSION, listid);
            clean(SqlData.DELETE_RET_LISTE_IMPRESSION, listidniss);

            clean(SqlData.DELETE_RET_LISTE_OCCUR, listid);
            clean(SqlData.DELETE_RET_LISTE_OCCUR, listidniss);

            clean(SqlData.DELETE_SUSPENSION_RECOUVREMENT, listid);
            clean(SqlData.DELETE_SUSPENSION_RECOUVREMENT, listidniss);

            clean(SqlData.DELETE_TRAITEMENT, listid);
            clean(SqlData.DELETE_TRAITEMENT, listidniss);

            clean(SqlData.DELETE_AFFI_PM, listid);
            clean(SqlData.DELETE_AFFI_PM, listidniss);

            clean(SqlData.DELETE_AFFI_PP, listidniss);

            clean(SqlData.DELETE_DOSSIER_CODEBITEUR, listid);
            clean(SqlData.DELETE_DOSSIER_CODEBITEUR, listidniss);

            clean(SqlData.DELETE_BLOCAGE, listid);
            clean(SqlData.DELETE_BLOCAGE, listidniss);

            clean(SqlData.DELETE_DOSSIER, listid);
            clean(SqlData.DELETE_DOSSIER, listidniss);

            clean(SqlData.DELETE_CONSULT_DOSSIER, listid);
            clean(SqlData.DELETE_CONSULT_DOSSIER, listidniss);

            clean(SqlData.UPDATE_IDENTIFIANT, listid);
            clean(SqlData.UPDATE_IDENTIFIANT, listidniss);

            clean(SqlData.DELETE_ADRESSE, listid);
            clean(SqlData.DELETE_ADRESSE, listidniss);

            clean(SqlData.DELETE_CTX_DEBITEUR, listid);
            clean(SqlData.DELETE_CTX_DEBITEUR, listidniss);

            clean(SqlData.DELETE_IDENTIFIANT, listid);
            clean(SqlData.DELETE_IDENTIFIANT, listidniss);

            clean(SqlData.DELETE_IDENTITE, listid);
            clean(SqlData.DELETE_IDENTITE, listidniss);

            clean(SqlData.DELETE_ATTRIBUTION_CANDIDATS_NONACTIVE, listMap);
            clean(SqlData.DELETE_ATTRIBUTION_CANDIDATS_NONACTIVE, listMap2);
            clean(SqlData.DELETE_ATTRIBUTION_CANDIDATS, listMap);
            clean(SqlData.DELETE_ATTRIBUTION_CANDIDATS, listMap2);

            clean(SqlData.DELETE_ATTRIBUTION_NONACTIVE, listMap);
            clean(SqlData.DELETE_ATTRIBUTION_NONACTIVE, listMap2);
            clean(SqlData.DELETE_ATTRIBUTION, listMap);
            clean(SqlData.DELETE_ATTRIBUTION, listMap2);

            LOGGER.info("Fin cleanUPDB for NISS/BCE: " + nissorbcenumber);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private List<Map<String, Object>> getVcsIdfromEntitCompta(List<Map<String, Object>> identiteID) {
        List<Map<String, Object>> listvcs = new ArrayList<>();
        for (Map<String, Object> featureService : identiteID) {
            for (Map.Entry<String, Object> entry : featureService.entrySet()) {
                listvcs.addAll(getJdbcTemplate().queryForList(SqlData.SELECT_VCS_ID_ENTIT_COMPTABLE, entry.getValue()));
            }
        }
        return listvcs;
    }

    private List<Map<String, Object>> getWorkflowIDforAttributionNiss(String dataset) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_WORKFLOW_ID_NISS, dataset);
    }

    private List<Map<String, Object>> getWorkflowIDforAttributionId(String dataset) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_WORKFLOW_ID, SeleniumUtils.getConvertedStringFormat(dataset));
    }

    private List<Map<String, Object>> getIdentiteID(String sql, String dataset) {
        return getJdbcTemplate().queryForList(sql, dataset);
    }

    public String getNumeroEnrolMajoPP(String periode) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_NUMERO_PP_ENROLMAJO, new Object[]{periode}, String.class);
    }

    public String getDossierIkFromNiss(String niss) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_DOSSIER_IK_FROM_NISS, new Object[]{niss}, String.class);
    }

    public String getDossierIkFromBce(String bce) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_DOSSIER_IK_FROM_BCE, new Object[]{bce}, String.class);
    }

    public String getIbanFromNiss(String niss) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_IBAN_FROM_NISS, new Object[]{niss}, String.class);
    }

    public String getIdentiteFromMandat(String niss) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_NOM_FROM_MANDAT, new Object[]{niss}, String.class);
    }

    public String getMandatTypeFromNiss(String niss) {
        String mandatType = getJdbcTemplate().queryForObject(SqlData.SELECT_MANDAT_TYPE_FROM_NISS, new Object[]{niss}, String.class);

        switch (mandatType) {
            case "145":
                return "B2B";
            case "146":
                return "B2C";
            default:
                return mandatType;
        }
    }

    public String getDossierNumber(String content) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_DOSSIER_NBR, new Object[]{content}, String.class);
    }

    public String getNissCjtFromNiss(String niss) {
        try {
            return getJdbcTemplate().queryForObject(SqlData.SELECT_NISS_CJT_FROM_NISS1, new Object[]{niss}, String.class);
        } catch (Exception e) {
            return getJdbcTemplate().queryForObject(SqlData.SELECT_NISS_CJT_FROM_NISS2, new Object[]{niss}, String.class);
        }
    }

    public List<Map<String, Object>> getNumerosfromNiss(String niss) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_ENROLEMENTPP_NUMERO_FROM_NISS, niss);
    }

    public String getNumeroFromNissUnique(String niss) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_ENROLEMENTPP_NUMERO_FROM_NISS_UNIQUE, new Object[]{niss}, String.class);
    }

    private void clean(String sql, List<Map<String, Object>> listmap) {
        for (Map<String, Object> featureService : listmap) {
            for (Map.Entry<String, Object> entry : featureService.entrySet()) {
                getJdbcTemplate().update(sql, entry.getValue());
            }
        }
    }

    public String getBlobAfterEnrolementFailed(String niss) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_BLOB_FROM_NUMERO_BATCHQUEUE, new Object[]{getNumeroFromNissUnique(niss)}, String.class);
    }

    public void deleteFichierInastiTransfertInPM() {
        getJdbcTemplate().update(SqlData.DELETE_FICHIER_INASTI_TRANSFERT_IN_PM);
    }

    public void deleteAllGedDemande() {
        getJdbcTemplate().update(SqlData.DELETE_ALL_GED_DEMANDE);
    }

    public void prepareFluxInInjection(String niss) {
        try {
            String fluxId = getJdbcTemplate().queryForObject(SqlData.SELECT_INASTI_FLUX_RECORD_PK, new Object[]{niss}, String.class);
            if (fluxId != null) {
                getJdbcTemplate().update(SqlData.DELETE_INASTI_FLUX_RECORD, niss);
                getJdbcTemplate().update(SqlData.DELETE_INASTI_FLUX, fluxId);
            }
        } catch (Exception e) {

        }
    }

    public String getVCScode(String nissorbce) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_VCS_CODE, new Object[]{nissorbce}, String.class);
    }

    private Integer getVsId(String nissorbce) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_VCS_ID, new Object[]{nissorbce}, Integer.class);
    }

    public String getVCSMontant(String nissorbce) {
        BigDecimal vcs = getJdbcTemplate().queryForObject(SqlData.SELECT_VCS_MONTANT, new Object[]{nissorbce}, BigDecimal.class);
        vcs = vcs.multiply(new BigDecimal(1000));
        return String.valueOf(vcs.intValueExact());
    }

    public List<String> getVcsMontantList(String nissorbce) {
        List<String> vcsmontant = new ArrayList<>();
        List<BigDecimal> montantdecimal = getJdbcTemplate().queryForList(SqlData.SELECT_VCS_MONTANT, new Object[]{nissorbce}, BigDecimal.class);
        for (BigDecimal big : montantdecimal) {
            big = big.multiply(new BigDecimal(1000));
            int vcsInt = big.intValueExact();
            String vcsStringMontant = String.valueOf(vcsInt);
            vcsmontant.add(vcsStringMontant);
        }
        return vcsmontant;
    }

    public List<String> getVCScodeList(String nissorbce) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_VCS_CODE, new Object[]{nissorbce}, String.class);
    }

    public void cleanInastiMessage(String nissorbcenumber) {
        getJdbcTemplate().update(SqlData.DELETE_INASTI_MESSAGE_HISTORY, nissorbcenumber);
    }

    public String countEntiteComptableCotisationsNonSoldees(String niss) {
        return getJdbcTemplate().queryForObject(SqlData.COUNT_ENTITE_COMPTABLE_COTISATIONS_NON_SOLDEES, new Object[]{niss}, String.class);
    }

    public String getNissRDEPaiementNulEnrolMajoPP(String periode, String nature, String dateDebut) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_PP_RDE_PAIEMENT_NUL_ENROL_MAJO, new Object[]{periode, nature, dateDebut}, String.class);
    }

    public String getNissRDEPaiementPartielEnrolMajoPP(String periode, String nature, String dateDebut) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_PP_RDE_PAIEMENT_PARTIEL_ENROL_MAJO, new Object[]{periode, nature, dateDebut}, String.class);
    }

    public String getNissRDEPaiementTotalEnrolMajoPP(String periode, String nature, String dateDebut) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_PP_RDE_PAIEMENT_TOTAL_ENROL_MAJO, new Object[]{periode, nature, dateDebut}, String.class);
    }

    public String getNissRDAPaiementNulEnrolMajoPP(String periode, String nature, String dateDebut, String dateFin) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_PP_RDA_PAIEMENT_NUL_ENROL_MAJO, new Object[]{periode, nature, dateDebut, dateFin}, String.class);
    }

    public String getNissRDAPaiementPartielEnrolMajoPP(String periode, String nature, String dateDebut, String dateFin) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_PP_RDA_PAIEMENT_PARTIEL_ENROL_MAJO, new Object[]{periode, nature, dateDebut, dateFin}, String.class);
    }

    public String getNissRDAPaiementTotalEnrolMajoPP(String periode, String nature, String dateDebut, String dateFin) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_PP_RDA_PAIEMENT_TOTAL_ENROL_MAJO, new Object[]{periode, nature, dateDebut, dateFin}, String.class);
    }

    public String getBcePaiementNulEnrolMajoPM(String periode, String type) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_PM_PAIEMENT_NUL_ENROL_MAJO, new Object[]{periode, type}, String.class);
    }

    public String getBcePaiementPartielEnrolMajoPM(String periode, String type) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_PM_PAIEMENT_PARTIEL_ENROL_MAJO, new Object[]{periode, type}, String.class);
    }

    public String getBcePaiementTotalEnrolMajoPM(String periode, String type) {
        return getJdbcTemplate().queryForObject(SqlData.SELECT_PM_PAIEMENT_TOTAL_ENROL_MAJO, new Object[]{periode, type}, String.class);
    }

    private boolean isFilledIn(String value) {
        return !StringUtils.isEmpty(value) && !StringUtils.isBlank(value);
    }

    private String queryBuilder(String ageSup60ans, String agePpSupp60, String nature,
                                String regime, String dateDebut1, String art11,
                                String exoReduction,
                                Calendar dateJour, String ageSup65ans, String agePpSupp65, String anneeRevenu, String typeRevenu, Map<String, List<String>> mapNiss) {
        List<Object> list = new ArrayList<>();

        if ("RDE".equals(regime) && !isFilledIn(typeRevenu)) {
            regime = "RDA";
            dateDebut1 = anneeRevenu + "-01-01";
        }

        String query = "SELECT num_identifiant" +
                " FROM NASCA.IDENTIFIANT i " +
                " WHERE i.TYPE_IDENTIFIANT = 'NISS' ";

        if (!mapNiss.isEmpty()) {
            query += " AND NUM_IDENTIFIANT NOT IN(";

            for (Map.Entry<String, List<String>> entry : mapNiss.entrySet()) {
                String identifiant = entry.getValue().get(0);
                query += "?,";
                list.add(identifiant);
            }

            query += "'') ";
        }

        query += " AND IDENTITE_FK IN (";

        if (!isFilledIn(ageSup60ans)) {
            query += " SELECT u.identite_fk" +
                    " FROM NASCA.dossier u " +
                    " INNER JOIN NASCA.PERSONNE_PHYSIQUE v" +
                    " ON u.IDENTITE_FK=v.IDENTITE_IK " +
                    " WHERE v.DT_NAISSANCE > ? " +
                    " AND u.dossier_ik IN (";
            list.add(agePpSupp60);
        } else {
            if (isFilledIn(ageSup65ans) && "true".equals(ageSup65ans)) {
                query += " SELECT u.identite_fk" +
                        " FROM NASCA.dossier u " +
                        " INNER JOIN NASCA.PERSONNE_PHYSIQUE v" +
                        " ON u.IDENTITE_FK=v.IDENTITE_IK " +
                        " WHERE v.DT_NAISSANCE < ? " +
                        " AND u.dossier_ik IN (";
                list.add(agePpSupp65);
            } else {
                if (isFilledIn(ageSup65ans) && "false".equals(ageSup65ans)) {
                    query += " SELECT u.identite_fk" +
                            " FROM NASCA.dossier u " +
                            " INNER JOIN NASCA.PERSONNE_PHYSIQUE v" +
                            " ON u.IDENTITE_FK=v.IDENTITE_IK " +
                            " WHERE v.DT_NAISSANCE BETWEEN ? AND ?" +
                            " AND u.dossier_ik IN (";
                    list.add(agePpSupp65);
                    list.add(agePpSupp60);
                } else {
                    query += " SELECT u.identite_fk" +
                            " FROM NASCA.dossier u " +
                            " INNER JOIN NASCA.PERSONNE_PHYSIQUE v" +
                            " ON u.IDENTITE_FK=v.IDENTITE_IK " +
                            " WHERE v.DT_NAISSANCE < ? " +
                            " AND u.dossier_ik IN (";
                    list.add(agePpSupp60);
                }
            }
        }

        if ("RDA".equals(regime)) {
            query += " SELECT b.DOSSIER_FK" +
                    " FROM NASCA.EVENEMENT b  " +
                    " WHERE u.dossier_ik=b.DOSSIER_FK AND b.TYPE_EVENEMENT IN('DAC','ACA') " +
                    " AND b.DT_DEBUT_PERIODE >= '2010-01-01' ";
        } else {
            query += " SELECT b.DOSSIER_FK" +
                    " FROM NASCA.EVENEMENT b  " +
                    " WHERE u.dossier_ik=b.DOSSIER_FK AND b.TYPE_EVENEMENT IN('DAC','RAC','CPR','ACA') " +
                    " AND b.DT_DEBUT_PERIODE >= '2010-01-01' ";
        }

        list.add(dateDebut1);
        if ("RDA".equals(regime)) {
            query += " AND b.DT_DEBUT_PERIODE >= ? ";
        } else {
            query += " AND b.DT_DEBUT_PERIODE <= ? ";
        }
        if (!"2".equals(nature)) {
            query += " AND b.DT_FIN_PERIODE IS NULL ";
        } else {
            query += " AND b.DT_FIN_PERIODE > ? ";
            list.add(DateUtils.doFormat(TestData.DATE_FORMAT_XML, dateJour.getTime()));
        }

        if (isFilledIn(nature)) {
            query += " AND b.NATURE_COTISANTE_FK = ? ";
            list.add(nature);
        }

        if (isFilledIn(art11)) {
            query += " AND ((b.TYPE_MONTANT_PLANCHER_FK = ? AND b.TYPE_MONTANT_PLAFOND_FK IS NULL) OR (b.TYPE_MONTANT_PLANCHER_FK IS NULL AND b.TYPE_MONTANT_PLAFOND_FK = ?)) ";
            list.add(art11);
            list.add(art11);
        } else {
            query += " AND (b.TYPE_MONTANT_PLANCHER_FK IS NULL AND b.TYPE_MONTANT_PLAFOND_FK IS NULL) ";
        }

        query += " AND b.HIS_STATUT_RECORD = 'A' " +
                " AND b.STATUT_EVENEMENT = 'ACT' ";
        if (isFilledIn(exoReduction)) {
            query += " AND b.TYPE_REDUC_COTISATION = ? ) ";
            list.add(exoReduction);
        } else {
            query += " AND b.TYPE_REDUC_COTISATION IS NULL ) ";
        }

        query += " AND u.DOSSIER_IK NOT IN ( " +
                " SELECT DOSSIER_FK " +
                " FROM NASCA.PLC_CONTRAT) ";

        query += " AND u.IDENTITE_FK NOT IN ( " +
                " SELECT IDENTITE_FK " +
                " FROM NASCA.DOSSIER WHERE TYPE = 'PLC' ) ";

        if ("RDE".equals(regime)) {
            query += " AND u.DOSSIER_IK IN ( " +
                    " SELECT DOSSIER_FK " +
                    " FROM NASCA.REGIME " +
                    " WHERE REGIME_ACTIVITE = 'RDE' AND DT_DEBUT_PERIODE <= ? AND DT_FIN_PERIODE IS NULL ) ";
            list.add(DateUtils.doFormat(TestData.DATE_FORMAT_XML, dateJour.getTime()));
        }

        if (isFilledIn(typeRevenu)) {
            query += " AND not exists(SELECT * FROM NASCA.REVENU WHERE PP_FK = v.IDENTITE_IK AND DT_DISTRIBUTION >= ?) ";
            list.add((dateJour.get(Calendar.YEAR) - 1) + "-12-31");
        } else {
            query += " AND not exists(SELECT * FROM NASCA.REVENU WHERE PP_FK = v.IDENTITE_IK AND (DT_DISTRIBUTION >= ? OR ANNEE = ?)) ";
            list.add((dateJour.get(Calendar.YEAR) - 1) + "-12-31");
            list.add(anneeRevenu);
        }

        query += " AND not exists(SELECT * FROM NASCA.WORKFLOW WHERE IDENTITE_FK = v.IDENTITE_IK AND statut = 'EN_COURS')) FETCH FIRST 5000 ROWS ONLY ";

        List<String> listNumero = getJdbcTemplate().queryForList(query, list.toArray(), String.class);
        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public boolean notExistBaremesPp(String date) {
        return getJdbcTemplate().queryForObject(SqlData.COUNT_RAPPORT_INDEXATION_PP, new Object[]{date}, Integer.class) == 0;
    }

    public void updateBaremesPp(String date) {
        getJdbcTemplate().update(SqlData.UPDATE_BAREME_MONTANT_PP, date);
        getJdbcTemplate().update(SqlData.UPDATE_BAREME_TAUX_PP, date);
    }

    public void insert2016BaremesPp(String date) {
        getJdbcTemplate().update(SqlData.INSERT_BAREME1_TAUX_PP, date);
        getJdbcTemplate().update(SqlData.INSERT_BAREME2_TAUX_PP, date);
        getJdbcTemplate().update(SqlData.INSERT_BAREME3_TAUX_PP, date);

        getJdbcTemplate().update(SqlData.INSERT_BAREME1_MONTANT_PP, date);
        getJdbcTemplate().update(SqlData.INSERT_BAREME2_MONTANT_PP, date);
        getJdbcTemplate().update(SqlData.INSERT_BAREME3_MONTANT_PP, date);
        getJdbcTemplate().update(SqlData.INSERT_BAREME4_MONTANT_PP, date);
        getJdbcTemplate().update(SqlData.INSERT_BAREME5_MONTANT_PP, date);
        getJdbcTemplate().update(SqlData.INSERT_BAREME6_MONTANT_PP, date);
        getJdbcTemplate().update(SqlData.INSERT_BAREME7_MONTANT_PP, date);
        getJdbcTemplate().update(SqlData.INSERT_BAREME8_MONTANT_PP, date);
        getJdbcTemplate().update(SqlData.INSERT_BAREME9_MONTANT_PP, date);
        getJdbcTemplate().update(SqlData.INSERT_BAREME10_MONTANT_PP, date);
        getJdbcTemplate().update(SqlData.INSERT_BAREME11_MONTANT_PP, date);
        getJdbcTemplate().update(SqlData.INSERT_BAREME12_MONTANT_PP, date);
        getJdbcTemplate().update(SqlData.INSERT_BAREME13_MONTANT_PP, date);
        getJdbcTemplate().update(SqlData.INSERT_BAREME14_MONTANT_PP, date);

        getJdbcTemplate().update(SqlData.INSERT_BAREME1_INDEXATION, date.substring(0, 4), Integer.valueOf(date.substring(0, 4)) - 9);
        getJdbcTemplate().update(SqlData.INSERT_BAREME2_INDEXATION, date.substring(0, 4), Integer.valueOf(date.substring(0, 4)) - 8);
        getJdbcTemplate().update(SqlData.INSERT_BAREME3_INDEXATION, date.substring(0, 4), Integer.valueOf(date.substring(0, 4)) - 7);
        getJdbcTemplate().update(SqlData.INSERT_BAREME4_INDEXATION, date.substring(0, 4), Integer.valueOf(date.substring(0, 4)) - 6);
        getJdbcTemplate().update(SqlData.INSERT_BAREME5_INDEXATION, date.substring(0, 4), Integer.valueOf(date.substring(0, 4)) - 5);
        getJdbcTemplate().update(SqlData.INSERT_BAREME6_INDEXATION, date.substring(0, 4), Integer.valueOf(date.substring(0, 4)) - 4);
        getJdbcTemplate().update(SqlData.INSERT_BAREME7_INDEXATION, date.substring(0, 4), Integer.valueOf(date.substring(0, 4)) - 3);
        getJdbcTemplate().update(SqlData.INSERT_BAREME8_INDEXATION, date.substring(0, 4), Integer.valueOf(date.substring(0, 4)) - 1);
        getJdbcTemplate().update(SqlData.INSERT_BAREME9_INDEXATION, date.substring(0, 4), date.substring(0, 4));
    }

    public void deleteEnrolementTrimestrielBlocage() {
        getJdbcTemplate().update(SqlData.DELETE_ENROLEMENT_TRIMESTRIEL_BLOCAGE);
    }

    public void deleteDateEnrolementPp(String annee, String trimestre) {
        getJdbcTemplate().update(SqlData.DELETE_DATE_ENROLEMENT_PP, annee, trimestre);
    }

    public void insertDateEnrolementPp(String annee, String trimestre, String date) {
        try {
            getJdbcTemplate().update(SqlData.INSERT_DATE_ENROLEMENT_PP_TRIM, annee, trimestre, date);
        } catch (DuplicateKeyException e) {
            getJdbcTemplate().update(SqlData.UPDATE_EXECUTE_FALSE_ENROLEMENT_TRIMESTRIEL, date, annee, trimestre);
        }
    }

    public void updateExecuteFalseEnrolementPp(String annee, String trimestre, String date) {
        getJdbcTemplate().update(SqlData.UPDATE_EXECUTE_FALSE_ENROLEMENT_TRIMESTRIEL, date, annee, trimestre);
    }

    public void updateExecuteTrueEnrolementPp(String annee, String trimestre) {
        getJdbcTemplate().update(SqlData.UPDATE_EXECUTE_TRUE_ENROLEMENT_TRIMESTRIEL, annee, trimestre);
    }

    public String getNissForEnrolementPP(String ageSup60ans, String date60ans, String nature, String regime, String dateDebut1, String exoReduction, String article11, Calendar dateJour, String ageSup65ans, String date65ans, String anneeRevenu, String typeRevenu, Map<String, List<String>> mapNiss) {
        try {
            return queryBuilder(ageSup60ans, date60ans, nature, regime, dateDebut1, article11, exoReduction, dateJour, ageSup65ans, date65ans, anneeRevenu, typeRevenu, mapNiss);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public void updateMontantPlancherEvenement(String plancher, String niss) {
        getJdbcTemplate().update(SqlData.UPDATE_MONTANT_PLANCHER_EVENEMENT, plancher, niss);
    }

    public List<Map<String, Object>> getFluxDepistage(String niss) {
        return getJdbcTemplate().queryForList(SqlData.SELECT_FLUX_DEPISTAGE, niss);
    }

    public void cleanInastiHisto() {
        getJdbcTemplate().update(SqlData.DELETE_INASTI_HISTO);
    }

    public String getNissSansCjtForProfilV3Trimestriel(String dateDebut65Ans, String dateFin65Ans, String nac) {
        List<String> listNumero;
        if ("O0".equals(nac) || "F0".equals(nac) || "E0".equals(nac)) {
            listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_NISS_SANS_CJT_AVEC_PLANCHER_PROFIL_V3_TRIMESTRIEL, new Object[]{dateDebut65Ans, dateFin65Ans, nac}, String.class);
        } else {
            listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_NISS_SANS_CJT_SANS_PLANCHER_PROFIL_V3_TRIMESTRIEL, new Object[]{dateDebut65Ans, dateFin65Ans, nac}, String.class);
        }

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getNissAvecCjtPensionneForProfilV3Trimestriel(String dateDebut65Ans, String dateFin65Ans, String nac) {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_NISS_AVEC_CJT_PENSIONNNE_PROFIL_V3_TRIMESTRIEL, new Object[]{ dateDebut65Ans, dateFin65Ans, nac }, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getNissAvecCjtNonPensionneForProfilV3Trimestriel(String dateDebut65Ans, String dateFin65Ans, String nac) {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_NISS_AVEC_CJT_NON_PENSIONNNE_PROFIL_V3_TRIMESTRIEL, new Object[]{ dateDebut65Ans, dateFin65Ans, nac }, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public void updateDateNaissanceForProfilV3(String date, String niss) {
        getJdbcTemplate().update(SqlData.UPDATE_DATE_NAISSANCE_PROFIL_V3, date, niss);
    }

    public void updateCaracteristiquesPensionForProfilV3(String regimePension, String infoCarriere, String naturePension, String detenteurPension, String enfantACharge, String niss) {
        getJdbcTemplate().update(SqlData.UPDATE_CARACTERISTIQUES_PENSION, regimePension, infoCarriere, naturePension, detenteurPension, enfantACharge, niss);
    }

    public String getNissPasAtteintAgePensionForProfilV3Annuel(String dateDebut65Ans, String nac, String regime, String nature, String enfantACharge) {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_NISS_PAS_ATTEINT_AGE_PENSION_PROFIL_V3_ANNUEL, new Object[]{ dateDebut65Ans, nac, regime, nature, enfantACharge }, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getNissAgePensionAtteintForProfilV3Annuel(String dateDebut65Ans, String nac, String regime, String nature, String enfantACharge) {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_NISS_ATTEINT_AGE_PENSION_PROFIL_V3_ANNUEL, new Object[]{ dateDebut65Ans, nac, regime, nature, enfantACharge }, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getBCESuspensionSolde0() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_COMPTE_A_0, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getNISSCotPrincipal() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PP_PRINC, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getNISSCotComp() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PP_COMP, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }


    public String getNISSSuspensionTypeMult (){
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_MULT_EC, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getNISSSuspensionTypeMultMaj (){
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_MULT_MAJ_EC, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getNISSSuspensionMaj (){
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_MAJ_EC, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getNISSSuspensionCodebit() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PP_CODEBIT, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getBCESuspensionCodebit() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PM_CODEBIT, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getNISSCotConjAid() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PP_CONJ_AID, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getBCESuspensionCotEnCompte() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PM_COT_EN_COMPTE, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getNISSSuspensionCotEnCompte() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PP_COT_EN_COMPTE, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getNISSSuspension20172() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PP_20172, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getNISSSuspensionPlanApurement() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PP_PLAN_APUREMENT, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getBCESuspensionPlanApurement() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PM_PLAN_APUREMENT, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public Map<String, Object> getNISSSuspensionECPartielle() {
        List<Map<String, Object>> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PP_EC_PARTIELLEMENT_PAYEE);

        if (listNumero.isEmpty()) {
            return null;
        }


        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public Map<String, Object> getBCESuspensionECPartielle() {
        List<Map<String, Object>> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PP_EC_PARTIELLEMENT_PAYEE);

        if (listNumero.isEmpty()) {
            return null;
        }


        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getNISSSuspensionMultipleCotisation() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PP_MULTIPLE_COTISATION, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getBCESuspensionMultipleCotisation() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PM_MULTIPLE_COTISATION, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    /*public String getBCESuspensionECPartielle() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PM_EC_PARTIELLEMENT_PAYEE, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }*/

    public String getNISSSuspensionCompteA0() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PP_COMPTE_A_0, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getNISSSuspensionCotRegul() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PP_COT_REGUL, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getNISSSuspensionMultCotRegul() {
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_SUSPENSION_PP_MULT_COT_REGUL, new Object[]{}, String.class);

        if (listNumero.isEmpty()) {
            return null;
        }
        return listNumero.get(listNumero.size() > 1 ? SeleniumUtils.getRandomNumberBetween(0, listNumero.size() - 1) : 0);
    }

    public String getListNissFaillitPPSolde(String natureCotissant,int p){
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PP_NISS_FAILLITE_SOLDE, new String[]{natureCotissant}, String.class);
        return listNumero.get(p);
    }

    public String getListBecFaillitPMSolde(int p){
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PM_BEC_FAILLITE_SOLDE,String.class);
        return listNumero.get(p);
    }

    public String getListNissFaillitPPssSolde(String natureCotissant, int p){
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PP_NISS_FAILLITE_SS_SOLDE,new String[]{natureCotissant},String.class);
        return listNumero.get(p);
    }

    public String getListBecFaillitPMssSolde(int p){
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PM_BEC_FAILLITE_SS_SOLDE,String.class);
        return listNumero.get(p);
    }

    public String getListNissRegule(String natureCotissant,int p){
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PP_REGULE,new String[]{natureCotissant},String.class);
        return listNumero.get(p);
    }

    public String getListBecRegule(int p){
        List<String> listNumero = getJdbcTemplate().queryForList(SqlData.SELECT_PM_REGULE,String.class);
        return listNumero.get(p);
    }
}