package be.ucm.cas.nasca.web.test.support;

public class SqlData {

    public SqlData() {

    }

    public static final String SELECT_NISS_WITH_SOLDE = "SELECT " +
            "NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT " +
            "WHERE TYPE_IDENTIFIANT = 'NISS' " +
            "AND IDENTITE_FK = " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER " +
            "   WHERE DOSSIER_IK = " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_FK " +
            "      FROM NASCA.ENTITE_COMPTABLE " +
            "      WHERE PERIODE = ? " +
            "      AND NATURE = 'COT_PP' " +
            "      AND ANNULE = 'F' " +
            "      AND SOLDE > 0 FETCH FIRST 1 ROWS ONLY " +
            "   ) " +
            ") ";

    public static final String SELECT_REFERENCE_PRINT_QUEUE = "SELECT REFERENCE FROM NASCA.PRINT_QUEUE WHERE SUJET = ? ORDER BY PRINT_QUEUE_IK DESC FETCH FIRST 1 ROWS ONLY";

    // Courrier
    public static final String DELETE_CIN_STATISTIQUE_COURRIER = "DELETE FROM NASCA.CIN_STATISTIQUE_COURRIER ";

    public static final String DELETE_CIN_STATISTIQUE_OBJET = "DELETE FROM NASCA.CIN_STATISTIQUE_OBJET";

    public static final String DELETE_CIN_COURRIER_WTIH_DATE = "DELETE FROM NASCA.CIN_COURRIER_ENTRANT WHERE DT_ENTREE >= ?";

    // Revenu
    public static final String SELECT_MONTANT_REGULARISATION_SUITE_REVENU = "SELECT MONTANT_CIBLE FROM NASCA.ENTITE_COMPTABLE WHERE DOSSIER_FK = ? " +
            "AND NATURE = 'COT_PP' " +
            "AND PERIODE = ? " +
            "AND TYPE IN('ORD','REG', 'PRE') " +
            "AND ANNULE = 'F' " +
            "AND DT_CREATION >= ? ";

    public static final String SELECT_NISS_AVEC_REVENU_RDE =
            "SELECT " +
                    "NUM_IDENTIFIANT " +
                    "FROM NASCA.IDENTIFIANT " +
                    "WHERE TYPE_IDENTIFIANT = 'NISS' " +
                    "AND NUM_IDENTIFIANT > '60' " +
                    "AND IDENTITE_FK IN " +
                    "( " +
                    "   SELECT " +
                    "   a.IDENTITE_IK " +
                    "   FROM NASCA.IDENTITE a " +
                    "   WHERE a.IDENTITE_IK IN " +
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
                    "            AND DT_DEBUT_PERIODE <= ? " +
                    "            AND DT_FIN_PERIODE IS NULL " +
                    "            AND TYPE_EVENEMENT IN('DAC','RAC','CPR') " +
                    "            AND HIS_STATUT_RECORD = 'A' " +
                    "            AND STATUT_EVENEMENT = 'ACT' " +
                    "            AND TYPE_REDUC_COTISATION IS NULL " +
                    "         ) " +
                    "         AND DOSSIER_FK IN " +
                    "         ( " +
                    "            SELECT " +
                    "            DOSSIER_FK " +
                    "            FROM NASCA.REGIME " +
                    "            WHERE REGIME_ACTIVITE = 'RDE' " +
                    "         ) " +
                    "      ) " +
                    "   ) " +
                    "   AND a.IDENTITE_IK IN " +
                    "   ( " +
                    "      SELECT " +
                    "      PP_FK " +
                    "      FROM NASCA.REVENU " +
                    "      WHERE TYPE = ? " +
                    "      AND STATUT = ? " +
                    "      AND ANNEE = ? " +
                    "      AND MONTANT BETWEEN 0 " +
                    "      AND ? " +
                    "      AND HIS_STATUT_RECORD IN('A') " +
                    "      AND DT_COMMUNICATION <> ? " +
                    "   ) " +
                    "   AND (SELECT count(*) FROM NASCA.REVENU rev WHERE PP_FK = IDENTITE_IK AND ANNEE = ? AND HIS_STATUT_RECORD IN('A')) = 1 " +
                    "   AND a.IDENTITE_IK NOT IN " +
                    "   ( " +
                    "   	SELECT " +
                    "  		IDENTITE_FK " +
                    "   	FROM NASCA.WORKFLOW " +
                    "   	WHERE STATUT = 'EN_COURS' " +
                    "   	AND TYPE_WORKFLOW = 'REVENUS' " +
                    "   ) " +
                    ") " +
                    "FETCH FIRST 1 ROWS ONLY ";

    public static final String SELECT_NISS_AVEC_REVENU_RDA = "SELECT " +
            "NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT " +
            "WHERE TYPE_IDENTIFIANT = 'NISS' " +
            "AND NUM_IDENTIFIANT > '60' " +
            "AND IDENTITE_FK IN " +
            "( " +
            "   SELECT " +
            "   a.IDENTITE_IK " +
            "   FROM NASCA.IDENTITE a " +
            "   WHERE a.IDENTITE_IK IN " +
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
            "            AND DT_DEBUT_PERIODE = ? " +
            "            AND DT_FIN_PERIODE IS NULL " +
            "            AND TYPE_EVENEMENT IN('DAC','RAC','CPR') " +
            "            AND HIS_STATUT_RECORD = 'A' " +
            "            AND STATUT_EVENEMENT = 'ACT' " +
            "            AND TYPE_REDUC_COTISATION IS NULL " +
            "         ) " +
            "         AND DOSSIER_FK IN " +
            "         ( " +
            "            SELECT " +
            "            DOSSIER_FK " +
            "            FROM NASCA.REGIME " +
            "            WHERE REGIME_ACTIVITE = 'RDA' " +
            "         ) " +
            "      ) " +
            "   ) " +
            "   AND a.IDENTITE_IK IN " +
            "   ( " +
            "      SELECT " +
            "      PP_FK " +
            "      FROM NASCA.REVENU " +
            "      WHERE TYPE = ? " +
            "      AND STATUT = ? " +
            "      AND ANNEE = ? " +
            "      AND MONTANT BETWEEN 0 " +
            "      AND ? " +
            "      AND HIS_STATUT_RECORD IN('A') " +
            "      AND DT_COMMUNICATION <> ? " +
            "   ) " +
            "   AND (SELECT count(*) FROM NASCA.REVENU rev WHERE PP_FK = IDENTITE_IK AND ANNEE = ? AND HIS_STATUT_RECORD IN('A')) = 1 " +
            "   AND a.IDENTITE_IK NOT IN " +
            "   ( " +
            "   	SELECT " +
            "  		IDENTITE_FK " +
            "   	FROM NASCA.WORKFLOW " +
            "   	WHERE STATUT = 'EN_COURS' " +
            "   	AND TYPE_WORKFLOW = 'REVENUS' " +
            "   ) " +
            ") " +
            "FETCH FIRST 1 ROWS ONLY ";

    public static final String SELECT_NISS_SANS_REVENU_RDE =
            "SELECT " +
                    "NUM_IDENTIFIANT " +
                    "FROM NASCA.IDENTIFIANT " +
                    "WHERE TYPE_IDENTIFIANT = 'NISS' " +
                    "AND NUM_IDENTIFIANT > '60' " +
                    "AND IDENTITE_FK IN " +
                    "( " +
                    "   SELECT " +
                    "   a.IDENTITE_IK " +
                    "   FROM NASCA.IDENTITE a " +
                    "   WHERE a.IDENTITE_IK IN " +
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
                    "            AND DT_DEBUT_PERIODE <= ? " +
                    "            AND DT_FIN_PERIODE IS NULL " +
                    "            AND TYPE_EVENEMENT IN('DAC','RAC','CPR') " +
                    "            AND HIS_STATUT_RECORD = 'A' " +
                    "            AND STATUT_EVENEMENT = 'ACT' " +
                    "            AND TYPE_REDUC_COTISATION IS NULL " +
                    "         ) " +
                    "         AND DOSSIER_FK IN " +
                    "         ( " +
                    "            SELECT " +
                    "            DOSSIER_FK " +
                    "            FROM NASCA.REGIME " +
                    "            WHERE REGIME_ACTIVITE = 'RDE' " +
                    "         ) " +
                    "      ) " +
                    "      AND NOT EXISTS " +
                    "      ( " +
                    "         SELECT " +
                    "         * " +
                    "         FROM NASCA.REVENU r " +
                    "         WHERE r.PP_FK = a.IDENTITE_IK " +
                    "         AND r.ANNEE = ? " +
                    "         AND r.HIS_STATUT_RECORD IN('A') " +
                    "      ) " +
                    "   ) " +
                    "   AND a.IDENTITE_IK NOT IN " +
                    "   ( " +
                    "   	SELECT " +
                    "  		IDENTITE_FK " +
                    "   	FROM NASCA.WORKFLOW " +
                    "   	WHERE STATUT = 'EN_COURS' " +
                    "   	AND TYPE_WORKFLOW = 'REVENUS' " +
                    "   ) " +
                    ") " +
                    "FETCH FIRST 1 ROWS ONLY ";

    public static final String SELECT_NISS_SANS_REVENU_RDA =
            "SELECT " +
                    "NUM_IDENTIFIANT " +
                    "FROM NASCA.IDENTIFIANT " +
                    "WHERE TYPE_IDENTIFIANT = 'NISS' " +
                    "AND NUM_IDENTIFIANT > '60' " +
                    "AND IDENTITE_FK IN " +
                    "( " +
                    "   SELECT " +
                    "   a.IDENTITE_IK " +
                    "   FROM NASCA.IDENTITE a " +
                    "   WHERE a.IDENTITE_IK IN " +
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
                    "            AND DT_DEBUT_PERIODE <= ? " +
                    "            AND DT_FIN_PERIODE IS NULL " +
                    "            AND TYPE_EVENEMENT IN('DAC','RAC','CPR') " +
                    "            AND HIS_STATUT_RECORD = 'A' " +
                    "            AND STATUT_EVENEMENT = 'ACT' " +
                    "            AND TYPE_REDUC_COTISATION IS NULL " +
                    "         ) " +
                    "         AND DOSSIER_FK IN " +
                    "         ( " +
                    "            SELECT " +
                    "            DOSSIER_FK " +
                    "            FROM NASCA.REGIME " +
                    "            WHERE REGIME_ACTIVITE = 'RDA' " +
                    "         ) " +
                    "      ) " +
                    "      AND NOT EXISTS " +
                    "      ( " +
                    "         SELECT " +
                    "         * " +
                    "         FROM NASCA.REVENU r " +
                    "         WHERE r.PP_FK = a.IDENTITE_IK " +
                    "         AND r.ANNEE = ? " +
                    "         AND r.HIS_STATUT_RECORD IN('A') " +
                    "      ) " +
                    "   ) " +
                    "   AND a.IDENTITE_IK NOT IN " +
                    "   ( " +
                    "   	SELECT " +
                    "  		IDENTITE_FK " +
                    "   	FROM NASCA.WORKFLOW " +
                    "   	WHERE STATUT = 'EN_COURS' " +
                    "   	AND TYPE_WORKFLOW = 'REVENUS' " +
                    "   ) " +
                    ") " +
                    "FETCH FIRST 1 ROWS ONLY ";

    // Print Mécanisme
    public static final String SELECT_DOCUMENT_IMPRESSION = "SELECT " +
            "PRINT_QUEUE_IK " +
            "FROM NASCA.PRINT_QUEUE " +
            "WHERE SUJET LIKE 'PP%' FETCH FIRST 1 ROWS ONLY ";

    public static final String SELECT_NOM_DOCUMENT_IMPRESSION = "SELECT " +
            "SUJET " +
            "FROM NASCA.PRINT_QUEUE " +
            "WHERE PRINT_QUEUE_IK = ? ";

    public static final String UPDATE_TYPE_DOCUMENT_IMPRESSION = "UPDATE NASCA.PRINT_QUEUE SET TYPE_PRINT_ORIGINE = 'INTERACTIF' " +
            "WHERE PRINT_QUEUE_IK = ? ";

    public static final String SELECT_DOCUMENT_IMPRESSION_IDENTIFIANT = "SELECT " +
            "i.NUM_IDENTIFIANT " +
            "FROM NASCA.PRINT_QUEUE p, NASCA.IDENTIFIANT i " +
            "WHERE p.PRINT_QUEUE_IK = ? " +
            "AND p.IDENTITE_FK = i.IDENTITE_FK " +
            "AND i.TYPE_IDENTIFIANT = ? ";

    public static final String UPDATE_TYPE_SORTIE_DOCUMENT_IMPRESSION = "UPDATE NASCA.PRINT_QUEUE SET FILE_SORTIE = ? " +
            "WHERE PRINT_QUEUE_IK = ? ";

    public static final String UPDATE_ETAT_DOCUMENT_IMPRESSION = "UPDATE NASCA.PRINT_QUEUE SET STATUT = ? " +
            "WHERE PRINT_QUEUE_IK = ? ";

    public static final String UPDATE_ANNULATION_DOCUMENT_IMPRESSION = "UPDATE NASCA.PRINT_QUEUE SET DT_ANNULATION = ?, ANNULATEUR_FK = ? " +
            "WHERE PRINT_QUEUE_IK = ? ";

    public static final String UPDATE_NULL_ANNULATION_DOCUMENT_IMPRESSION = "UPDATE NASCA.PRINT_QUEUE SET DT_ANNULATION = NULL, ANNULATEUR_FK = NULL " +
            "WHERE PRINT_QUEUE_IK = ? ";

    public static final String UPDATE_ALL_TYPE_DOCUMENT_IMPRESSION = "UPDATE NASCA.PRINT_QUEUE SET TYPE_PRINT_ORIGINE = 'INTERACTIF' ";

    // Mise en Demeure PP
    public static final String SELECT_MED_PP_QUERY_1 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecc.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecc, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecc.ENTITE_COMPTABLE_IK " +
            "AND ecc.SOLDE > 0 " +
            "AND ecc.SOLDE = ecc.MONTANT_CIBLE " +
            "AND ecc.NATURE = 'COT_PP' " +
            "AND ecc.DT_ECHEANCE < ? " +
            "AND ecc.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecc.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecmat WHERE ecmat.DOSSIER_FK = d.DOSSIER_IK AND ecmat.NATURE = 'MAJ_PP' AND ecmat.TYPE = 'TR' AND ecmat.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'AN' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'REG' AND ecre.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ec WHERE ec.DOSSIER_FK = d.DOSSIER_IK AND ec.SOLDE < 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC') FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_MED_PP_QUERY_2 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecc.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecc, NASCA.PERIODE_COTISATION_PP pc, NASCA.EVENEMENT evart11, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecc.ENTITE_COMPTABLE_IK " +
            "AND ecc.SOLDE > 0 " +
            "AND ecc.SOLDE = ecc.MONTANT_CIBLE " +
            "AND ecc.NATURE = 'COT_PP' " +
            "AND ecc.DT_ECHEANCE < ? " +
            "AND ecc.DT_PRESCRIPTION > ? " +
            "AND ecc.PERIODE_COTI_PP_FK = pc.PERIODE_COTISATION_PP_IK " +
            "AND evart11.EVENEMENT_IK = pc.EVENEMENT_FK " +
            "AND evart11.TYPE_MONTANT_PLANCHER_FK IS NOT NULL " +
            "AND evart11.DOSSIER_FK = d.DOSSIER_IK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecmat WHERE ecmat.DOSSIER_FK = d.DOSSIER_IK AND ecmat.NATURE = 'MAJ_PP' AND ecmat.TYPE = 'TR' AND ecmat.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'AN' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'REG' AND ecre.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ec WHERE ec.DOSSIER_FK = d.DOSSIER_IK AND ec.SOLDE < 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC')  FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_MED_PP_QUERY_3 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecc.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecc, NASCA.ENTITE_COMPTABLE ecm, NASCA.PERIODE_COTISATION_PP pc, NASCA.EVENEMENT evmx, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.PERSONNE_PHYSIQUE pp, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecc.ENTITE_COMPTABLE_IK " +
            "AND ecc.SOLDE > 0 " +
            "AND ecc.SOLDE = ecc.MONTANT_CIBLE " +
            "AND ecc.NATURE = 'COT_PP' " +
            "AND ecc.DT_ECHEANCE < ? " +
            "AND ecc.DT_PRESCRIPTION > ? " +
            "AND ecc.PERIODE_COTI_PP_FK = pc.PERIODE_COTISATION_PP_IK " +
            "AND evmx.EVENEMENT_IK = pc.EVENEMENT_FK " +
            "AND evmx.NATURE_COTISANTE_FK = 5 " +
            "AND evmx.DOSSIER_FK = d.DOSSIER_IK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND pp.IDENTITE_IK = i.IDENTITE_IK " +
            "AND pp.DT_NAISSANCE < '1956-01-01' " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecmat WHERE ecmat.DOSSIER_FK = d.DOSSIER_IK AND ecmat.NATURE = 'MAJ_PP' AND ecmat.TYPE = 'TR' AND ecmat.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'AN' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'REG' AND ecre.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ec WHERE ec.DOSSIER_FK = d.DOSSIER_IK AND ec.SOLDE < 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC')  FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_MED_PP_QUERY_4 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecc.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecc, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecc.ENTITE_COMPTABLE_IK " +
            "AND ecc.SOLDE > 0 " +
            "AND ecc.SOLDE = ecc.MONTANT_CIBLE " +
            "AND ecc.NATURE = 'COT_PP' " +
            "AND ecc.DT_ECHEANCE < ? " +
            "AND ecc.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecc.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecmat WHERE ecmat.DOSSIER_FK = d.DOSSIER_IK AND ecmat.NATURE = 'MAJ_PP' AND ecmat.TYPE = 'TR' AND ecmat.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'AN' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'REG' AND ecre.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ec WHERE ec.DOSSIER_FK = d.DOSSIER_IK AND ec.SOLDE < 0) " +
            "AND EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC') FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_MED_PP_QUERY_5 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecm.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecm, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecm.ENTITE_COMPTABLE_IK " +
            "AND ecm.SOLDE > 0 " +
            "AND ecm.NATURE = 'MAJ_PP' " +
            "AND ecm.DT_ECHEANCE < ? " +
            "AND ecm.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecm.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecc WHERE ecc.DOSSIER_FK = d.DOSSIER_IK AND ecc.NATURE = 'COT_PP' AND ecc.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ec WHERE ec.DOSSIER_FK = d.DOSSIER_IK AND ec.SOLDE < 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC') FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_MED_PP_QUERY_6 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecf.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecf, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecf.ENTITE_COMPTABLE_IK " +
            "AND ecf.SOLDE > 0 " +
            "AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') " +
            "AND ecf.DT_PRESCRIPTION < ? " +
            "AND d.DOSSIER_IK = ecf.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecmat WHERE ecmat.DOSSIER_FK = d.DOSSIER_IK AND ecmat.NATURE = 'MAJ_PP' AND ecmat.TYPE = 'TR' AND ecmat.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'AN' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'REG' AND ecre.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecc WHERE ecc.DOSSIER_FK = d.DOSSIER_IK AND ecc.NATURE = 'COT_PP' AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ec WHERE ec.DOSSIER_FK = d.DOSSIER_IK AND ec.SOLDE < 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC') FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_MED_PP_QUERY_7 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecf.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecf, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecf.ENTITE_COMPTABLE_IK " +
            "AND ecf.SOLDE > 0 " +
            "AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') " +
            "AND ecf.DT_ECHEANCE < ? " +
            "AND ecf.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecf.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecmat WHERE ecmat.DOSSIER_FK = d.DOSSIER_IK AND ecmat.NATURE = 'MAJ_PP' AND ecmat.TYPE = 'TR' AND ecmat.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'AN' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'REG' AND ecre.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecc WHERE ecc.DOSSIER_FK = d.DOSSIER_IK AND ecc.NATURE = 'COT_PP' AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ec WHERE ec.DOSSIER_FK = d.DOSSIER_IK AND ec.SOLDE < 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC') FETCH FIRST 1 ROWS ONLY";

    // Mise en Demeure PM
    public static final String SELECT_MED_PM_QUERY_1 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecc.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecc, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecc.ENTITE_COMPTABLE_IK " +
            "AND ecc.SOLDE > 0 " +
            "AND ecc.NATURE = 'COT_PM' " +
            "AND ecc.DT_ECHEANCE < ? " +
            "AND ecc.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecc.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'BCE' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.NATURE = 'MAJ_PM' AND ecma.TYPE = 'ME' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccs WHERE eccs.DOSSIER_FK = d.DOSSIER_IK AND eccs.SOLDE < 0) FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_MED_PM_QUERY_2 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecc.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecc, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecc.ENTITE_COMPTABLE_IK " +
            "AND ecc.SOLDE > 0 " +
            "AND ecc.NATURE = 'COT_PM' " +
            "AND ecc.DT_ECHEANCE < ? " +
            "AND ecc.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecc.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'BCE' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.NATURE = 'MAJ_PM' AND ecma.TYPE = 'ME' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccs WHERE eccs.DOSSIER_FK = d.DOSSIER_IK AND eccs.SOLDE < 0) FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_MED_PM_QUERY_3 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecf.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecf, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecf.ENTITE_COMPTABLE_IK " +
            "AND ecf.SOLDE > 0 " +
            "AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') " +
            "AND ecf.DT_PRESCRIPTION < ? " +
            "AND d.DOSSIER_IK = ecf.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'BCE' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.NATURE = 'MAJ_PM' AND ecma.TYPE = 'ME' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecc WHERE ecc.DOSSIER_FK = d.DOSSIER_IK AND ecc.NATURE = 'COT_PM' AND ecc.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccs WHERE eccs.DOSSIER_FK = d.DOSSIER_IK AND eccs.SOLDE < 0) FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_MED_PM_QUERY_4 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecf.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecf, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecf.ENTITE_COMPTABLE_IK " +
            "AND ecf.SOLDE > 0 " +
            "AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') " +
            "AND ecf.DT_ECHEANCE < ? " +
            "AND ecf.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecf.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'BCE' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.NATURE = 'MAJ_PM' AND ecma.TYPE = 'ME' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecc WHERE ecc.DOSSIER_FK = d.DOSSIER_IK AND ecc.NATURE = 'COT_PM' AND ecc.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccs WHERE eccs.DOSSIER_FK = d.DOSSIER_IK AND eccs.SOLDE < 0) FETCH FIRST 1 ROWS ONLY";

    // Rappels PP
    public static final String SELECT_RAPPELS_PP_QUERY_1 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecc.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecc, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecc.ENTITE_COMPTABLE_IK " +
            "AND ecc.SOLDE > 0 " +
            "AND ecc.NATURE = 'COT_PP' " +
            "AND ecc.TYPE = 'ORD' " +
            "AND ecc.DT_ECHEANCE < ? " +
            "AND ecc.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecc.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.periode = ecc.periode AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'TR' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.periode = ecc.periode AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'AN' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.periode = ecc.periode AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'REG' AND ecre.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.periode = ecc.periode AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.periode = ecc.periode AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccre WHERE eccre.DOSSIER_FK = d.DOSSIER_IK AND eccre.periode = ecc.periode AND eccre.SOLDE < 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC') FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_RAPPELS_PP_QUERY_2 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecc.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecc, NASCA.ENTITE_COMPTABLE ecm, NASCA.PERIODE_COTISATION_PP pc, NASCA.EVENEMENT evmx, NASCA.EVENEMENT ev, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.PERSONNE_PHYSIQUE pp, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecc.ENTITE_COMPTABLE_IK " +
            "AND ecc.SOLDE > 0 " +
            "AND ecc.NATURE = 'COT_PP' " +
            "AND ecc.TYPE = 'ORD' " +
            "AND ecc.DT_ECHEANCE < ? " +
            "AND ecc.DT_PRESCRIPTION > ? " +
            "AND ecc.PERIODE_COTI_PP_FK = pc.PERIODE_COTISATION_PP_IK " +
            "AND ev.EVENEMENT_IK = pc.EVENEMENT_FK " +
            "AND evmx.NATURE_COTISANTE_FK = 5 " +
            "AND evmx.DOSSIER_FK = d.DOSSIER_IK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND pp.IDENTITE_IK = i.IDENTITE_IK " +
            "AND pp.DT_NAISSANCE < '1956-01-01' " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.periode = ecc.periode AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'TR' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.COTISATION_FK = d.DOSSIER_IK AND ecma.periode = ecc.periode AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'AN' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.periode = ecc.periode AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'REG' AND ecre.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.periode = ecc.periode AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.periode = ecc.periode AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccre WHERE eccre.DOSSIER_FK = d.DOSSIER_IK AND eccre.periode = ecc.periode AND eccre.SOLDE < 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC') FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_RAPPELS_PP_QUERY_3 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecf.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecf, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecf.ENTITE_COMPTABLE_IK " +
            "AND ecf.SOLDE > 0 " +
            "AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') " +
            "AND ecf.DT_PRESCRIPTION < ? " +
            "AND d.DOSSIER_IK = ecf.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.periode = ecf.periode AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'TR' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.periode = ecf.periode AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'AN' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.periode = ecf.periode AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'REG' AND ecre.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecc WHERE ecc.DOSSIER_FK = d.DOSSIER_IK AND ecc.periode = ecf.periode AND ecc.NATURE = 'COT_PP' AND ecc.TYPE = 'ORD' AND ecc.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.periode = ecf.periode AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccre WHERE eccre.DOSSIER_FK = d.DOSSIER_IK AND eccre.periode = ecf.periode AND eccre.SOLDE < 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC') FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_RAPPELS_PP_QUERY_4 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecc.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecc, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecc.ENTITE_COMPTABLE_IK " +
            "AND ecc.SOLDE > 0 " +
            "AND ecc.NATURE = 'COT_PP' " +
            "AND ecc.TYPE = 'ORD' " +
            "AND ecc.DT_ECHEANCE < ? " +
            "AND ecc.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecc.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.periode = ecc.periode AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'TR' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.periode = ecc.periode AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'AN' AND ecma.SOLDE > 0) " +
            "AND EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.periode = ecc.periode AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'REG' AND ecre.SOLDE > 0) " +
            "AND EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.periode = ecc.periode AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.periode = ecc.periode AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccre WHERE eccre.DOSSIER_FK = d.DOSSIER_IK AND eccre.periode = ecc.periode AND eccre.SOLDE < 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC') FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_RAPPELS_PP_QUERY_5 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecmat.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecmat, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecmat.ENTITE_COMPTABLE_IK " +
            "AND ecmat.SOLDE > 0 " +
            "AND ecmat.NATURE = 'MAJ_PP' " +
            "AND ecmat.TYPE = 'TR' " +
            "AND ecmat.DT_ECHEANCE < ? " +
            "AND ecmat.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecmat.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.periode = ecmat.periode AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'AN' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.periode = ecmat.periode AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'REG' AND ecre.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecc WHERE ecc.DOSSIER_FK = d.DOSSIER_IK AND ecc.periode = ecmat.periode AND ecc.NATURE = 'COT_PP' AND ecc.TYPE = 'ORD' AND ecc.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.periode = ecmat.periode AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.periode = ecmat.periode AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccre WHERE eccre.DOSSIER_FK = d.DOSSIER_IK AND eccre.periode = ecmat.periode AND eccre.SOLDE < 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC') FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_RAPPELS_PP_QUERY_6 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecma.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecma, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecma.ENTITE_COMPTABLE_IK " +
            "AND ecma.SOLDE > 0 " +
            "AND ecma.NATURE = 'MAJ_PP' " +
            "AND ecma.TYPE = 'AN' " +
            "AND ecma.DT_ECHEANCE < ? " +
            "AND ecma.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecma.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecmat WHERE ecmat.DOSSIER_FK = d.DOSSIER_IK AND ecmat.periode = ecma.periode AND ecmat.NATURE = 'MAJ_PP' AND ecmat.TYPE = 'TR' AND ecmat.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.periode = ecma.periode AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'REG' AND ecre.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecc WHERE ecc.DOSSIER_FK = d.DOSSIER_IK AND ecc.periode = ecma.periode AND ecc.NATURE = 'COT_PP' AND ecc.TYPE = 'ORD' AND ecc.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.periode = ecma.periode AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.periode = ecma.periode AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccre WHERE eccre.DOSSIER_FK = d.DOSSIER_IK AND eccre.periode = ecma.periode AND eccre.SOLDE < 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC') FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_RAPPELS_PP_QUERY_7 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecc.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecc, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecc.ENTITE_COMPTABLE_IK " +
            "AND ecc.SOLDE > 0 " +
            "AND ecc.NATURE = 'COT_PP' " +
            "AND ecc.TYPE = 'REG' " +
            "AND ecc.DT_ECHEANCE < ? " +
            "AND ecc.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecc.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecmat WHERE ecmat.DOSSIER_FK = d.DOSSIER_IK AND ecmat.periode = ecc.periode AND ecmat.NATURE = 'MAJ_PP' AND ecmat.TYPE = 'AN' AND ecmat.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.periode = ecc.periode AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'AN' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.periode = ecc.periode AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'ORD' AND ecre.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.periode = ecc.periode AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.periode = ecc.periode AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccre WHERE eccre.DOSSIER_FK = d.DOSSIER_IK AND eccre.periode = ecc.periode AND eccre.SOLDE < 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC') FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_RAPPELS_PP_QUERY_8 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecc.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecc, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecc.ENTITE_COMPTABLE_IK " +
            "AND ecc.SOLDE > 0 " +
            "AND ecc.NATURE = 'COT_PP' " +
            "AND ecc.TYPE = 'ORD' " +
            "AND ecc.DT_ECHEANCE < ? " +
            "AND ecc.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecc.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecmat WHERE ecmat.DOSSIER_FK = d.DOSSIER_IK AND ecmat.periode = ecc.periode AND ecmat.NATURE = 'MAJ_PP' AND ecmat.TYPE = 'TR' AND ecmat.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.periode = ecc.periode AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'AN' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.periode = ecc.periode AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'REG' AND ecre.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.periode = ecc.periode AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.periode = ecc.periode AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccre WHERE eccre.DOSSIER_FK = d.DOSSIER_IK AND eccre.periode = ecc.periode AND eccre.SOLDE < 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC') FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_RAPPELS_PP_QUERY_9 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecb.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecb, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecb.ENTITE_COMPTABLE_IK " +
            "AND ecb.SOLDE > 0 " +
            "AND ecb.NATURE = 'BON' " +
            "AND ecb.DT_ECHEANCE < ? " +
            "AND ecb.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecb.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.periode = ecb.periode AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'AN' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecmat WHERE ecmat.DOSSIER_FK = d.DOSSIER_IK AND ecmat.periode = ecb.periode AND ecmat.NATURE = 'MAJ_PP' AND ecmat.TYPE = 'TR' AND ecmat.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.periode = ecb.periode AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'REG' AND ecre.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecord WHERE ecord.DOSSIER_FK = d.DOSSIER_IK AND ecord.periode = ecb.periode AND ecord.NATURE = 'COT_PP' AND ecord.TYPE = 'REG' AND ecord.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.periode = ecb.periode AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccre WHERE eccre.DOSSIER_FK = d.DOSSIER_IK AND eccre.periode = ecb.periode AND eccre.SOLDE < 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC') FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_RAPPELS_PP_QUERY_10 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecc.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecc, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecc.ENTITE_COMPTABLE_IK " +
            "AND ecc.SOLDE > 0 " +
            "AND ecc.NATURE = 'COT_PP' " +
            "AND ecc.TYPE = 'ORD' " +
            "AND ecc.DT_ECHEANCE < ? " +
            "AND ecc.DT_PRESCRIPTION > ? " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecmat WHERE ecmat.DOSSIER_FK = d.DOSSIER_IK AND ecmat.periode = ecc.periode AND ecmat.NATURE = 'MAJ_PP' AND ecmat.TYPE = 'TR' AND ecmat.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.DOSSIER_FK = d.DOSSIER_IK AND ecma.periode = ecc.periode AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'AN' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.periode = ecc.periode AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'REG' AND ecre.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.periode = ecc.periode AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.periode = ecc.periode AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccre WHERE eccre.DOSSIER_FK = d.DOSSIER_IK AND eccre.periode = ecc.periode AND eccre.PERIODE = ecc.PERIODE AND eccre.SOLDE < 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC') FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_RAPPELS_PP_QUERY_11 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecc.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecc, NASCA.DOSSIER d, NASCA.EVENEMENT ev, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecc.ENTITE_COMPTABLE_IK " +
            "AND ecc.SOLDE > 0 " +
            "AND ecc.NATURE = 'COT_PP' " +
            "AND ecc.TYPE = 'REG' " +
            "AND ecc.DT_ECHEANCE < ? " +
            "AND ecc.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecc.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND ev.DOSSIER_FK = d.DOSSIER_IK " +
            "AND ev.TYPE_EVENEMENT = 'DEC' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecmat WHERE ecmat.COTISATION_FK = d.DOSSIER_IK AND ecmat.periode = ecc.periode AND ecmat.NATURE = 'MAJ_PP' AND ecmat.TYPE = 'TR' AND ecmat.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecma WHERE ecma.COTISATION_FK = d.DOSSIER_IK AND ecma.periode = ecc.periode AND ecma.NATURE = 'MAJ_PP' AND ecma.TYPE = 'AN' AND ecma.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecre WHERE ecre.DOSSIER_FK = d.DOSSIER_IK AND ecre.periode = ecc.periode AND ecre.NATURE = 'COT_PP' AND ecre.TYPE = 'ORD' AND ecre.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecb WHERE ecb.DOSSIER_FK = d.DOSSIER_IK AND ecb.periode = ecc.periode AND ecb.NATURE = 'BON' AND ecb.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.periode = ecc.periode AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccre WHERE eccre.DOSSIER_FK = d.DOSSIER_IK AND eccre.periode = ecc.periode AND eccre.SOLDE < 0) " +
            "AND EXISTS (SELECT * FROM NASCA.EVENEMENT ev WHERE ev.DOSSIER_FK = d.DOSSIER_IK AND ev.TYPE_EVENEMENT = 'DEC') FETCH FIRST 1 ROWS ONLY";

    // Rappels PM
    public static final String SELECT_RAPPELS_PM_QUERY_1 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecc.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecc, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecc.ENTITE_COMPTABLE_IK " +
            "AND ecc.SOLDE > 0 " +
            "AND ecc.NATURE = 'COT_PM' " +
            "AND ecc.DT_ECHEANCE < ? " +
            "AND ecc.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecc.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'BCE' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecm WHERE ecm.DOSSIER_FK = d.DOSSIER_IK AND ecm.periode = ecc.periode AND ecm.NATURE = 'MAJ_PM' AND ecm.TYPE = 'ME' AND ecm.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.periode = ecc.periode AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccre WHERE eccre.DOSSIER_FK = d.DOSSIER_IK AND eccre.periode = ecc.periode AND eccre.SOLDE < 0) FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_RAPPELS_PM_QUERY_2 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecf.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecf, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecf.ENTITE_COMPTABLE_IK " +
            "AND ecf.SOLDE > 0 " +
            "AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') " +
            "AND ecf.DT_PRESCRIPTION < ? " +
            "AND d.DOSSIER_IK = ecf.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'BCE' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecm WHERE ecm.DOSSIER_FK = d.DOSSIER_IK AND ecm.periode = ecf.periode AND ecm.NATURE = 'MAJ_PM' AND ecm.TYPE = 'ME' AND ecm.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecc WHERE ecc.DOSSIER_FK = d.DOSSIER_IK AND ecc.periode = ecf.periode AND ecc.NATURE = 'COT_PM' AND ecc.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccre WHERE eccre.DOSSIER_FK = d.DOSSIER_IK AND eccre.periode = ecf.periode AND eccre.SOLDE < 0) FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_RAPPELS_PM_QUERY_3 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecc.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecc, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecc.ENTITE_COMPTABLE_IK " +
            "AND ecc.SOLDE > 0 " +
            "AND ecc.NATURE = 'COT_PM' " +
            "AND ecc.DT_ECHEANCE < ? " +
            "AND ecc.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecc.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'BCE' " +
            "AND EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecm WHERE ecm.DOSSIER_FK = d.DOSSIER_IK AND ecm.periode = ecc.periode AND ecm.NATURE = 'MAJ_PM' AND ecm.TYPE = 'ME' AND ecm.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.periode = ecc.periode AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccre WHERE eccre.DOSSIER_FK = d.DOSSIER_IK AND eccre.periode = ecc.periode AND eccre.SOLDE < 0) FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_RAPPELS_PM_QUERY_4 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecm.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecm, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecm.ENTITE_COMPTABLE_IK " +
            "AND ecm.SOLDE > 0 " +
            "AND ecm.NATURE = 'MAJ_PM' " +
            "AND ecm.DT_ECHEANCE < ? " +
            "AND ecm.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecm.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'BCE' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecc WHERE ecc.DOSSIER_FK = d.DOSSIER_IK AND ecc.periode = ecm.periode AND ecc.NATURE = 'COT_PM' AND ecc.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.periode = ecm.periode AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccre WHERE eccre.DOSSIER_FK = d.DOSSIER_IK AND eccre.periode = ecm.periode AND eccre.SOLDE < 0) FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_RAPPELS_PM_QUERY_5 = "SELECT " +
            "id.NUM_IDENTIFIANT, ecc.PERIODE " +
            "FROM NASCA.RECOUVREMENT r, NASCA.ENTITE_COMPTABLE ecc, NASCA.DOSSIER d, NASCA.IDENTITE i, NASCA.IDENTIFIANT id " +
            "WHERE r.STADE = ? " +
            "AND r.SOLIDAIRE = ? " +
            "AND r.SUSPENDU = ? " +
            "AND r.CREANCE_FK = ecc.ENTITE_COMPTABLE_IK " +
            "AND ecc.SOLDE > 0 " +
            "AND ecc.NATURE = 'COT_PM' " +
            "AND ecc.DT_ECHEANCE < ? " +
            "AND ecc.DT_PRESCRIPTION > ? " +
            "AND d.DOSSIER_IK = ecc.DOSSIER_FK " +
            "AND d.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.IDENTITE_FK = i.IDENTITE_IK " +
            "AND id.TYPE_IDENTIFIANT = 'BCE' " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecm WHERE ecm.DOSSIER_FK = d.DOSSIER_IK AND ecm.periode = ecc.periode AND ecm.NATURE = 'MAJ_PM' AND ecm.TYPE = 'ME' AND ecm.SOLDE > 0) " +
            "AND NOT EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE ecf WHERE ecf.DOSSIER_FK = d.DOSSIER_IK AND ecf.periode = ecc.periode AND ecf.NATURE IN('FRAP','FSOM','FREC','FCIT','FEXE') AND ecf.SOLDE > 0) " +
            "AND EXISTS (SELECT * FROM NASCA.ENTITE_COMPTABLE eccre WHERE eccre.DOSSIER_FK = d.DOSSIER_IK AND eccre.periode = ecc.periode AND eccre.SOLDE < 0) FETCH FIRST 1 ROWS ONLY";

    // OBSERVATIONS
    public static final String SELECT_OBSERVATION = "SELECT " +
            "a.NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b " +
            "WHERE a.IDENTITE_FK = b.IDENTITE_IK " +
            "AND a.TYPE_IDENTIFIANT = 'NISS' " +
            "AND b.IDENTITE_IK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER " +
            "   WHERE DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_FK " +
            "      FROM NASCA.PERIODE_AFFILIATION_PP " +
            "      WHERE STATUT = 'ACTIF' " +
            "   ) " +
            ") " +
            "AND b.IDENTITE_IK NOT IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.OBSERVATION " +
            ") " +
            "FETCH FIRST 25 ROWS ONLY ";

    // MISE A JOUR NATIONALITE
    public static final String SELECT_MISE_A_JOUR_NATIONALITE = "SELECT " +
            "a.NUM_IDENTIFIANT FROM nasca.IDENTIFIANT a, " +
            "nasca.IDENTITE b " +
            "WHERE a.IDENTITE_FK=b.IDENTITE_IK and  " +
            "a.TYPE_IDENTIFIANT='NISS' and b.IDENTITE_IK in( " +
            "select identite_fk from nasca.dossier where dossier_ik in(" +
            "select DOSSIER_FK from nasca.EVENEMENT where type_evenement <>'CES'))" +
            "FETCH FIRST 25 ROWS ONLY ";



    // MISE EN VEILLEUSE
    public static final String SELECT_MISE_EN_VEILLEUSE_SOLDE_POSITIF_AVEC_CPTE_BANCAIRE = "SELECT a.NUM_IDENTIFIANT "
            + "FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b "
            + "WHERE a.IDENTITE_FK = b.IDENTITE_IK AND a.TYPE_IDENTIFIANT = 'BCE' "
            + "AND b.IDENTITE_IK IN "
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.PERIODE_AFFILIATION_PM WHERE STATUT = 'ACTIF' "
            + "AND DT_DEBUT_PERIODE < ? AND DT_FIN_PERIODE IS NULL "
            + "AND DEMANDE_FK IN(SELECT DEMANDE_IK FROM NASCA.DEMANDE_AFFILIATION_PM WHERE CAISSE_ORIGINE_FK IS NULL)) "
            + "AND DOSSIER_IK IN(SELECT dossier_FK FROM NASCA.ENTITE_COMPTABLE WHERE nature = 'COT_PM' AND PERIODE = ? AND SOLDE > 0) "
            + "AND DOSSIER_IK NOT IN(SELECT DOSSIER_FK FROM NASCA.EXONERATION_PM) "
            + "AND DOSSIER_IK NOT IN(SELECT DOSSIER_FK FROM NASCA.MISE_EN_VEILLEUSE))"
            + "AND b.IDENTITE_IK IN(SELECT c.IDENTITE_FK FROM NASCA.COMPTE c WHERE c.DT_FINVAL IS NULL) FETCH FIRST 30 ROWS ONLY";

    public static final String SELECT_MISE_EN_VEILLEUSE_SOLDE_NUL_AVEC_CPTE_BANCAIRE = "SELECT a.NUM_IDENTIFIANT "
            + "FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b "
            + "WHERE a.IDENTITE_FK = b.IDENTITE_IK AND a.TYPE_IDENTIFIANT = 'BCE' "
            + "AND b.IDENTITE_IK IN "
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.PERIODE_AFFILIATION_PM WHERE STATUT = 'ACTIF' "
            + "AND DT_DEBUT_PERIODE < ? AND DT_FIN_PERIODE IS NULL "
            + "AND DEMANDE_FK IN(SELECT DEMANDE_IK FROM NASCA.DEMANDE_AFFILIATION_PM WHERE CAISSE_ORIGINE_FK IS NULL)) "
            + "AND DOSSIER_IK IN(SELECT dossier_FK FROM NASCA.ENTITE_COMPTABLE WHERE nature = 'COT_PM' AND PERIODE = ? AND SOLDE = 0) "
            + "AND DOSSIER_IK NOT IN(SELECT DOSSIER_FK FROM NASCA.EXONERATION_PM) "
            + "AND DOSSIER_IK NOT IN(SELECT DOSSIER_FK FROM NASCA.MISE_EN_VEILLEUSE))"
            + "AND b.IDENTITE_IK IN(SELECT c.IDENTITE_FK FROM NASCA.COMPTE c WHERE c.DT_FINVAL IS NULL) FETCH FIRST 30 ROWS ONLY";

    public static final String SELECT_MISE_EN_VEILLEUSE_SOLDE_NEGATIF_AVEC_CPTE_BANCAIRE =
            "SELECT a.NUM_IDENTIFIANT "
                    + "FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b "
                    + "WHERE a.IDENTITE_FK = b.IDENTITE_IK AND a.TYPE_IDENTIFIANT = 'BCE' "
                    + "AND b.IDENTITE_IK IN "
                    + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
                    + "(SELECT DOSSIER_FK FROM NASCA.PERIODE_AFFILIATION_PM WHERE STATUT = 'ACTIF' "
                    + "AND DT_DEBUT_PERIODE < ? AND DT_FIN_PERIODE IS NULL "
                    + "AND DEMANDE_FK IN(SELECT DEMANDE_IK FROM NASCA.DEMANDE_AFFILIATION_PM WHERE CAISSE_ORIGINE_FK IS NULL)) "
                    + "AND DOSSIER_IK IN(SELECT dossier_FK FROM NASCA.ENTITE_COMPTABLE WHERE nature = 'COT_PM' AND PERIODE = ? AND SOLDE < 0) "
                    + "AND DOSSIER_IK NOT IN(SELECT DOSSIER_FK FROM NASCA.EXONERATION_PM) "
                    + "AND DOSSIER_IK NOT IN(SELECT DOSSIER_FK FROM NASCA.MISE_EN_VEILLEUSE)) "
                    + "AND b.IDENTITE_IK IN(SELECT c.IDENTITE_FK FROM NASCA.COMPTE c WHERE c.DT_FINVAL IS NULL) FETCH FIRST 2 ROWS ONLY";

    public static final String DELETE_CPT_BANCAIRE_MISE_EN_VEILLEUSE = "DELETE " +
            "FROM NASCA.COMPTE c " +
            "WHERE c.IDENTITE_FK IN " +
            "( " +
            "   SELECT " +
            "   i.IDENTITE_FK " +
            "   FROM NASCA.IDENTIFIANT i " +
            "   WHERE i.NUM_IDENTIFIANT = ? " +
            ") ";

    // PROFIL
    public static final String SELECT_PROFIL_SUPPRESSION_NATURE_SANS_REDUC = "SELECT numero FROM NASCA.IDENTITE WHERE identite_ik IN"
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE (TYPE_EVENEMENT = ? "
            + "AND NATURE_COTISANTE_FK = ? "
            + "AND DT_DEBUT_PERIODE <= ? "
            + "AND DT_DEBUT_PERIODE > ? "
            + "AND TYPE_REDUC_COTISATION IS NULL "
            + "AND DT_FIN_PERIODE IS NULL "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND HIS_STATUT_RECORD = 'A')) "
            + "AND DOSSIER_IK IN"
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE (TYPE_EVENEMENT = ? "
            + "AND NATURE_COTISANTE_FK = ? "
            + "AND DT_DEBUT_PERIODE <= ? "
            + "AND DT_DEBUT_PERIODE >= ? "
            + "AND TYPE_REDUC_COTISATION IS NULL "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND HIS_STATUT_RECORD = 'A')) "
            + "AND (SELECT count(*) FROM NASCA.EVENEMENT ev WHERE DOSSIER_FK = DOSSIER_IK) = 2 "
            + "AND NOT EXISTS(SELECT * FROM NASCA.DEMANDE dem WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.STATUT = 'ENCODAGE') "
            + ") FETCH FIRST 50 ROWS ONLY";

    public static final String SELECT_PROFIL_SUPPRESSION_NATURE_EV2_REDUC = "SELECT numero FROM NASCA.IDENTITE WHERE identite_ik IN"
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE (TYPE_EVENEMENT = ? "
            + "AND NATURE_COTISANTE_FK = ? "
            + "AND DT_DEBUT_PERIODE <= ? "
            + "AND DT_DEBUT_PERIODE > ? "
            + "AND TYPE_REDUC_COTISATION = ? "
            + "AND DT_FIN_PERIODE IS NULL "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND HIS_STATUT_RECORD = 'A'))"
            + "AND DOSSIER_IK IN"
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE (TYPE_EVENEMENT = ? "
            + "AND NATURE_COTISANTE_FK = ? "
            + "AND DT_DEBUT_PERIODE <= ? "
            + "AND DT_DEBUT_PERIODE >= ? "
            + "AND TYPE_REDUC_COTISATION IS NULL "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND HIS_STATUT_RECORD = 'A')) "
            + "AND (SELECT count(*) FROM NASCA.EVENEMENT ev WHERE DOSSIER_FK = DOSSIER_IK) = 2 "
            + "AND NOT EXISTS(SELECT * FROM NASCA.DEMANDE dem WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.STATUT = 'ENCODAGE') "
            + ") FETCH FIRST 50 ROWS ONLY";

    public static final String SELECT_PROFIL_SUPPRESSION_NATURE_EV1_REDUC = "SELECT numero FROM NASCA.IDENTITE WHERE identite_ik IN"
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE (TYPE_EVENEMENT = ? "
            + "AND NATURE_COTISANTE_FK = ? "
            + "AND DT_DEBUT_PERIODE <= ? "
            + "AND DT_DEBUT_PERIODE > ? "
            + "AND TYPE_REDUC_COTISATION IS NULL "
            + "AND DT_FIN_PERIODE IS NULL "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND HIS_STATUT_RECORD = 'A')) "
            + "AND DOSSIER_IK IN"
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE (TYPE_EVENEMENT = ? "
            + "AND NATURE_COTISANTE_FK = ? "
            + "AND DT_DEBUT_PERIODE <= ? "
            + "AND DT_DEBUT_PERIODE >= ? "
            + "AND TYPE_REDUC_COTISATION = ? "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND HIS_STATUT_RECORD = 'A')) "
            + "AND (SELECT count(*) FROM NASCA.EVENEMENT ev WHERE DOSSIER_FK = DOSSIER_IK) = 2 "
            + "AND NOT EXISTS(SELECT * FROM NASCA.DEMANDE dem WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.STATUT = 'ENCODAGE') "
            + ") FETCH FIRST 50 ROWS ONLY";

    public static final String SELECT_PROFIL_SUPPRESSION_NATURE_EV1_EV2_REDUC = "SELECT numero FROM NASCA.IDENTITE WHERE identite_ik IN"
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE (TYPE_EVENEMENT = ? "
            + "AND NATURE_COTISANTE_FK = ? "
            + "AND DT_DEBUT_PERIODE <= ? "
            + "AND DT_DEBUT_PERIODE > ? "
            + "AND TYPE_REDUC_COTISATION = ? "
            + "AND DT_FIN_PERIODE IS NULL "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND HIS_STATUT_RECORD = 'A')) "
            + "AND DOSSIER_IK IN"
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE (TYPE_EVENEMENT = ? "
            + "AND NATURE_COTISANTE_FK = ? "
            + "AND DT_DEBUT_PERIODE <= ? "
            + "AND DT_DEBUT_PERIODE <= ? "
            + "AND TYPE_REDUC_COTISATION = ? "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND HIS_STATUT_RECORD = 'A')) "
            + "AND (SELECT count(*) FROM NASCA.EVENEMENT ev WHERE DOSSIER_FK = DOSSIER_IK) = 2 "
            + "AND NOT EXISTS(SELECT * FROM NASCA.DEMANDE dem WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.STATUT = 'ENCODAGE') "
            + ") FETCH FIRST 50 ROWS ONLY";

    public static final String SELECT_PROFIL_SUPPRESSION_NATURE_SANS_REDUC_EVT_FIN = "SELECT numero FROM NASCA.IDENTITE WHERE identite_ik IN"
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE (TYPE_EVENEMENT = ? "
            + "AND DT_FIN_PERIODE > ? "
            + "AND DT_FIN_PERIODE <= ? "
            + "AND TYPE_REDUC_COTISATION IS NULL "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND HIS_STATUT_RECORD = 'A')) "
            + "AND DOSSIER_IK IN"
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE (TYPE_EVENEMENT = ? "
            + "AND NATURE_COTISANTE_FK = ? "
            + "AND DT_DEBUT_PERIODE <= ? "
            + "AND DT_DEBUT_PERIODE > ? "
            + "AND TYPE_REDUC_COTISATION IS NULL "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND HIS_STATUT_RECORD = 'A')) "
            + "AND DOSSIER_IK not IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE (TYPE_EVENEMENT = 'RAC' "
            + "AND DT_DEBUT_PERIODE >= ? "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND HIS_STATUT_RECORD = 'A')) "
            + "AND IDENTITE_IK NOT IN(SELECT pp_fk FROM NASCA.revenu WHERE annee > '2012' AND type = 'FISCAL' AND statut = 'SERVI_CALC' AND montant = 0) "
            + "AND (SELECT count(*) FROM NASCA.EVENEMENT ev WHERE DOSSIER_FK = DOSSIER_IK) = 2 "
            + "AND NOT EXISTS(SELECT * FROM NASCA.DEMANDE dem WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.STATUT = 'ENCODAGE') "
            + ") FETCH FIRST 50 ROWS ONLY";

    public static final String SELECT_PROFIL_SUPPRESSION_NATURE_EV1_REDUC_EVT_FIN = "SELECT numero FROM NASCA.IDENTITE WHERE identite_ik IN"
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE (TYPE_EVENEMENT = ? "
            + "AND DT_FIN_PERIODE > ? "
            + "AND DT_FIN_PERIODE <= ? "
            + "AND TYPE_REDUC_COTISATION IS NULL "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND HIS_STATUT_RECORD = 'A')) "
            + "AND DOSSIER_IK IN"
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE (TYPE_EVENEMENT = ? "
            + "AND NATURE_COTISANTE_FK = ? "
            + "AND DT_DEBUT_PERIODE <= ? "
            + "AND DT_DEBUT_PERIODE > ? "
            + "AND TYPE_REDUC_COTISATION = ? "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND HIS_STATUT_RECORD = 'A')) "
            + "AND DOSSIER_IK not IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE (TYPE_EVENEMENT = 'RAC' "
            + "AND DT_DEBUT_PERIODE >= ? "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND HIS_STATUT_RECORD = 'A')) "
            + "AND (SELECT count(*) FROM NASCA.EVENEMENT ev WHERE DOSSIER_FK = DOSSIER_IK) = 2 "
            + "AND NOT EXISTS(SELECT * FROM NASCA.DEMANDE dem WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.STATUT = 'ENCODAGE') "
            + ") FETCH FIRST 50 ROWS ONLY";

    public static final String SELECT_PROFIL_MAXI_STATUT = "SELECT numero FROM NASCA.IDENTITE WHERE identite_ik IN"
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT GROUP BY DOSSIER_FK "
            + "HAVING COUNT(DOSSIER_FK)= 1 AND DOSSIER_FK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE NATURE_COTISANTE_FK = 5 "
            + "AND DT_DEBUT_PERIODE <= ? "
            + "AND DT_FIN_PERIODE IS NULL "
            + "AND TYPE_EVENEMENT IN('DAC','RAC') "
            + "AND TYPE_REDUC_COTISATION IS NULL "
            + "AND HIS_STATUT_RECORD = 'A' "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND dossier_fk IN "
            + "(SELECT dossier_fk FROM NASCA.regime WHERE REGIME_ACTIVITE= ? ))"
            + "AND NOT EXISTS(SELECT * FROM NASCA.DEMANDE dem WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.STATUT = 'ENCODAGE') "
            + ")) FETCH FIRST 50 ROWS ONLY";

    public static final String SELECT_PROFIL_MAXI_STATUT_REDUC = "SELECT numero FROM NASCA.IDENTITE WHERE identite_ik IN "
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN"
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT GROUP BY DOSSIER_FK "
            + "HAVING COUNT(DOSSIER_FK)= 1 AND DOSSIER_FK IN"
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + " WHERE NATURE_COTISANTE_FK = 5 "
            + "AND DT_DEBUT_PERIODE <= ? "
            + "AND DT_FIN_PERIODE IS NULL "
            + "AND TYPE_EVENEMENT IN('DAC','RAC') "
            + "AND TYPE_REDUC_COTISATION = ? "
            + "AND HIS_STATUT_RECORD = 'A' "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND dossier_fk IN "
            + "(SELECT dossier_fk FROM NASCA.regime WHERE REGIME_ACTIVITE= ?))"
            + "AND NOT EXISTS(SELECT * FROM NASCA.DEMANDE dem WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.STATUT = 'ENCODAGE') "
            + "))FETCH FIRST 50 ROWS ONLY";

    public static final String SELECT_PROFIL_CHANGE_NATURE_REDUC = "SELECT numero FROM NASCA.IDENTITE WHERE identite_ik IN "
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT GROUP BY DOSSIER_FK "
            + "HAVING COUNT(DOSSIER_FK)= 1 AND DOSSIER_FK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE NATURE_COTISANTE_FK = ? "
            + "AND DT_DEBUT_PERIODE between ? AND ? "
            + "AND DT_FIN_PERIODE IS NULL "
            + "AND TYPE_EVENEMENT IN('DAC','RAC') "
            + "AND TYPE_REDUC_COTISATION = ? "
            + "AND HIS_STATUT_RECORD = 'A' "
            + "AND STATUT_EVENEMENT = 'ACT' )"
            + "AND dossier_fk IN(SELECT dossier_fk FROM NASCA.regime WHERE REGIME_ACTIVITE= ?) "
            + "AND NOT EXISTS(SELECT * FROM NASCA.DEMANDE dem WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.STATUT = 'ENCODAGE') "
            + "AND NOT EXISTS (SELECT * FROM NASCA.REVENU WHERE PP_FK = IDENTITE_FK ))) FETCH FIRST 50 ROWS ONLY";

    public static final String SELECT_PROFIL_CHANGE_NATURE_REDUC_REVENU = "SELECT numero FROM NASCA.IDENTITE WHERE identite_ik IN "
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT GROUP BY DOSSIER_FK "
            + "HAVING COUNT(DOSSIER_FK)= 1 AND DOSSIER_FK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE NATURE_COTISANTE_FK = ? "
            + "AND DT_DEBUT_PERIODE between ? AND ? "
            + "AND DT_FIN_PERIODE IS NULL "
            + "AND TYPE_EVENEMENT IN('DAC','RAC') "
            + "AND TYPE_REDUC_COTISATION = ? "
            + "AND HIS_STATUT_RECORD = 'A' "
            + "AND STATUT_EVENEMENT = 'ACT' ) "
            + "AND dossier_fk IN(SELECT dossier_fk FROM NASCA.regime WHERE REGIME_ACTIVITE= ?)"
            + "AND NOT EXISTS(SELECT * FROM NASCA.DEMANDE dem WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.STATUT = 'ENCODAGE') "
            + "))"
            + "AND IDENTITE_IK IN(SELECT IDENTITE_IK FROM NASCA.PERSONNE_PHYSIQUE WHERE DT_NAISSANCE >= '1960-01-01') FETCH FIRST 50 ROWS ONLY";

    public static final String SELECT_PROFIL_CHANGE_NATURE_REDUC_RED_REVENU = "SELECT numero FROM NASCA.IDENTITE WHERE identite_ik IN "
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT GROUP BY DOSSIER_FK "
            + "HAVING COUNT(DOSSIER_FK)= 1 AND DOSSIER_FK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE NATURE_COTISANTE_FK = ? "
            + "AND DT_DEBUT_PERIODE between ? AND ? "
            + "AND DT_FIN_PERIODE IS NULL "
            + "AND TYPE_EVENEMENT IN('DAC','RAC') "
            + "AND TYPE_REDUC_COTISATION = ? "
            + "AND HIS_STATUT_RECORD = 'A' "
            + "AND STATUT_EVENEMENT = 'ACT' ) "
            + "AND dossier_fk IN(SELECT dossier_fk FROM NASCA.regime WHERE REGIME_ACTIVITE= ?)"
            + "AND NOT EXISTS(SELECT * FROM NASCA.DEMANDE dem WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.STATUT = 'ENCODAGE') "
            + "))"
            + "AND IDENTITE_IK IN(SELECT IDENTITE_IK FROM NASCA.PERSONNE_PHYSIQUE WHERE DT_NAISSANCE >= '1960-01-01')"
            + "AND IDENTITE_IK NOT IN(SELECT pp_fk FROM NASCA.revenu WHERE annee > '2012' AND type = 'FISCAL' AND statut = 'SERVI_CALC' AND montant = 0) FETCH FIRST 50 ROWS ONLY";

    public static final String SELECT_PROFIL_CHANGE_NATURE = "SELECT numero FROM NASCA.IDENTITE WHERE identite_ik IN"
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT GROUP BY DOSSIER_FK "
            + "HAVING COUNT(DOSSIER_FK)= 1 AND DOSSIER_FK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE NATURE_COTISANTE_FK = ? "
            + "AND DT_DEBUT_PERIODE between ? AND ? "
            + "AND DT_FIN_PERIODE IS NULL "
            + "AND TYPE_EVENEMENT IN('DAC','RAC') "
            + "AND TYPE_REDUC_COTISATION IS NULL "
            + "AND HIS_STATUT_RECORD = 'A' "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND dossier_fk IN "
            + "(SELECT dossier_fk FROM NASCA.regime WHERE REGIME_ACTIVITE= ? )"
            + "AND NOT EXISTS(SELECT * FROM NASCA.DEMANDE dem WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.STATUT = 'ENCODAGE') "
            + "AND NOT EXISTS (SELECT * FROM NASCA.REVENU WHERE PP_FK = IDENTITE_FK )))) FETCH FIRST 50 ROWS ONLY";

    public static final String SELECT_PROFIL_CHANGE_NATURE_REVENU = "SELECT numero FROM NASCA.IDENTITE WHERE identite_ik IN"
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT GROUP BY DOSSIER_FK "
            + "HAVING COUNT(DOSSIER_FK)= 1 AND DOSSIER_FK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE NATURE_COTISANTE_FK = ? "
            + "AND DT_DEBUT_PERIODE between ? AND ? "
            + "AND DT_FIN_PERIODE IS NULL "
            + "AND TYPE_EVENEMENT IN('DAC','RAC') "
            + "AND TYPE_REDUC_COTISATION IS NULL "
            + "AND HIS_STATUT_RECORD = 'A' "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND dossier_fk IN "
            + "(SELECT dossier_fk FROM NASCA.regime WHERE REGIME_ACTIVITE= ? )"
            + "AND NOT EXISTS(SELECT * FROM NASCA.DEMANDE dem WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.STATUT = 'ENCODAGE') "
            + "))) FETCH FIRST 50 ROWS ONLY";

    public static final String SELECT_PROFIL_CESSATION = "SELECT numero FROM NASCA.IDENTITE WHERE identite_ik IN( "
            + "SELECT IDENTITE_FK "
            + "FROM NASCA.DOSSIER dos "
            + "WHERE DOSSIER_IK IN( "
            + "SELECT dossier_fk FROM(SELECT EVENEMENT_IK,  DOSSIER_FK,  DT_EVENEMENT, TYPE_EVENEMENT,HIS_STATUT_RECORD,row_number() over( partition by DOSSIER_FK ORDER BY DOSSIER_FK ,DT_EVENEMENT desc) as rn FROM NASCA.EVENEMENT) tmp "
            + "WHERE TYPE_EVENEMENT IN('CES') "
            + "AND rn = 1 "
            + "AND HIS_STATUT_RECORD = 'A' "
            + "AND (DT_EVENEMENT <= ? AND DT_EVENEMENT > ?) "
            + "AND EXISTS (SELECT * FROM NASCA.regime reg WHERE tmp.DOSSIER_FK = reg.DOSSIER_FK AND reg.REGIME_ACTIVITE IN('RDA','RDE')) "
            + "AND NOT EXISTS(SELECT * FROM NASCA.DEMANDE dem WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.STATUT = 'ENCODAGE') "
            + "AND NOT EXISTS (SELECT * FROM NASCA.REVENU rev WHERE rev.PP_FK = dos.IDENTITE_FK))) "
            + "FETCH FIRST 1 ROWS ONLY";

    public static final String UPDATE_DEMANDE_EN_ENCODAGE = "UPDATE NASCA.DEMANDE SET STATUT = 'ANNULE'"
            + " WHERE TYPE_DEMANDE = 'CHGMT_PROF' AND STATUT = 'ENCODAGE'";

    public static final String SELECT_BAREME_BY_YEAR = "SELECT NASCADM.PERIODE_VALIDITE.DT_DEBVAL FROM NASCADM.PERIODE_VALIDITE " +
            "WHERE NASCADM.PERIODE_VALIDITE.CONTEXTE = 'BARPM' " +
            "AND NASCADM.PERIODE_VALIDITE.DT_FINVAL LIKE ? || '%'";

    public static final String SELECT_ENRO_BY_YEAR = "SELECT NASCAPARAM.DATE_ENROLEMENT_ANNUEL.ANNEE FROM NASCAPARAM.DATE_ENROLEMENT_ANNUEL " +
            "WHERE NASCAPARAM.DATE_ENROLEMENT_ANNUEL.ANNEE = ? " +
            "AND NASCAPARAM.DATE_ENROLEMENT_ANNUEL.EXECUTE = ?";

    // TACHES
    public static final String SELECT_TACHE_IK_FROM_NISS = "SELECT " +
            "t.TACHE_IK " +
            "FROM NASCA.TACHE t, NASCA.WORKFLOW w, NASCA.IDENTIFIANT i " +
            "WHERE t.WORKFLOW_FK = w.WORKFLOW_IK " +
            "AND w.IDENTITE_FK = i.IDENTITE_FK " +
            "AND i.NUM_IDENTIFIANT = ? ";

    public static final String UPDATE_TACHE = "update NASCA.TACHE set URGENT = ?, STATUT = ?, NOTIFICATION = ? " +
            "WHERE TACHE_IK = ? ";

    // CLOTURE DOSSIER
    public static final String SELECT_NISS_CLOTURE_DOSSIER_CJT_AIDANT = "SELECT " +
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
            "         GROUP BY DOSSIER_FK HAVING COUNT(DOSSIER_FK) = 1 " +
            "         AND DOSSIER_FK IN " +
            "         ( " +
            "            SELECT " +
            "            DOSSIER_FK " +
            "            FROM NASCA.EVENEMENT " +
            "            WHERE NATURE_COTISANTE_FK = 5 " +
            "            AND DT_DEBUT_PERIODE >= ? " +
            "            AND DT_DEBUT_PERIODE < '2015-01-01' " +
            "            AND DT_FIN_PERIODE IS NULL " +
            "            AND TYPE_EVENEMENT IN('DAC','RAC') " +
            "            AND HIS_STATUT_RECORD = 'A' " +
            "            AND STATUT_EVENEMENT = 'ACT' " +
            "         ) " +
            "         AND NOT EXISTS " +
            "         ( " +
            "            SELECT " +
            "            * " +
            "            FROM NASCA.DEMANDE dem " +
            "            WHERE dem.DOSSIER_FK = DOSSIER_IK " +
            "            AND " +
            "            ( " +
            "               dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_CESSATION " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_ASSIMILATION_MALADIE " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_ASSURANCE_CONTINUEE " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_DECES " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_MANDAT_GRATUIT " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_NON_ASSUJETTISSEMENT " +
            "               ) " +
            "            ) " +
            "         ) " +
            "      ) " +
            "   ) " +
            ") " +
            "AND NOT EXISTS (SELECT * FROM NASCA.DOSSIER WHERE IDENTITE_FK = id.IDENTITE_FK AND TYPE = 'LS') " +
            "FETCH FIRST 1 ROWS ONLY ";

    public static final String SELECT_NISS_CLOTURE_DOSSIER_AVEC_CJT_AIDANT_SANS_EXO = "SELECT " +
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
            "         GROUP BY DOSSIER_FK HAVING COUNT(DOSSIER_FK) = 1 " +
            "         AND DOSSIER_FK IN " +
            "         ( " +
            "            SELECT " +
            "            DOSSIER_FK " +
            "            FROM NASCA.EVENEMENT " +
            "            WHERE NATURE_COTISANTE_FK = 1 " +
            "            AND DT_DEBUT_PERIODE >= ? " +
            "            AND DT_DEBUT_PERIODE < '2015-01-01' " +
            "            AND DT_FIN_PERIODE IS NULL " +
            "            AND TYPE_EVENEMENT IN('DAC','RAC') " +
            "            AND HIS_STATUT_RECORD = 'A' " +
            "            AND STATUT_EVENEMENT = 'ACT' " +
            "         ) " +
            "         AND NOT EXISTS " +
            "         ( " +
            "            SELECT " +
            "            * " +
            "            FROM NASCA.DEMANDE dem " +
            "            WHERE dem.DOSSIER_FK = DOSSIER_IK " +
            "            AND " +
            "            ( " +
            "               dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_CESSATION " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_ASSIMILATION_MALADIE " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_ASSURANCE_CONTINUEE " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_DECES " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_MANDAT_GRATUIT " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_NON_ASSUJETTISSEMENT " +
            "               ) " +
            "            ) " +
            "         ) " +
            "      ) " +
            "   ) " +
            "   AND a.IDENTITE_IK IN " +
            "   ( " +
            "      SELECT " +
            "      pp.IDENTITE_LIEE_FK " +
            "      FROM NASCA.LIEN_PP pp , NASCA.EVENEMENT ev, NASCA.dossier dos " +
            "      WHERE (pp.ETAT_CIVIL = 'MARIE' OR pp.TYPE_LIEN = 'COHAB') " +
            "      AND pp.DT_FINVAL IS NULL " +
            "      AND pp.IDENTITE_LIANTE_FK = dos.IDENTITE_FK " +
            "      AND dos.DOSSIER_IK = ev.DOSSIER_FK " +
            "      AND ev.HIS_STATUT_RECORD = 'A' " +
            "      AND ev.STATUT_EVENEMENT = 'ACT' " +
            "      AND ev.DT_FIN_PERIODE IS NULL " +
            "      AND ev.TYPE_REDUC_COTISATION IS NULL " +
            "      AND ev.NATURE_COTISANTE_FK = 5 " +
            "      AND ev.DT_DEBUT_PERIODE < '2016-01-01'" +
            "   ) " +
            ") " +
            "AND NOT EXISTS (SELECT * FROM NASCA.DOSSIER WHERE IDENTITE_FK = id.IDENTITE_FK AND TYPE = 'LS') " +
            "FETCH FIRST 1 ROWS ONLY ";

    public static final String SELECT_NISS_CLOTURE_DOSSIER_AVEC_CJT_AIDANT_AVEC_EXO = "SELECT " +
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
            "         GROUP BY DOSSIER_FK HAVING COUNT(DOSSIER_FK) = 1 " +
            "         AND DOSSIER_FK IN " +
            "         ( " +
            "            SELECT " +
            "            DOSSIER_FK " +
            "            FROM NASCA.EVENEMENT " +
            "            WHERE NATURE_COTISANTE_FK = 1 " +
            "            AND DT_DEBUT_PERIODE >= ? " +
            "            AND DT_DEBUT_PERIODE < '2015-01-01' " +
            "            AND DT_FIN_PERIODE IS NULL " +
            "            AND TYPE_EVENEMENT IN('DAC','RAC') " +
            "            AND HIS_STATUT_RECORD = 'A' " +
            "            AND STATUT_EVENEMENT = 'ACT' " +
            "         ) " +
            "         AND NOT EXISTS " +
            "         ( " +
            "            SELECT " +
            "            * " +
            "            FROM NASCA.DEMANDE dem " +
            "            WHERE dem.DOSSIER_FK = DOSSIER_IK " +
            "            AND " +
            "            ( " +
            "               dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_CESSATION " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_ASSIMILATION_MALADIE " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_ASSURANCE_CONTINUEE " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_DECES " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_MANDAT_GRATUIT " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_NON_ASSUJETTISSEMENT " +
            "               ) " +
            "            ) " +
            "         ) " +
            "      ) " +
            "   ) " +
            "   AND a.IDENTITE_IK IN " +
            "   ( " +
            "      SELECT " +
            "      pp.IDENTITE_LIEE_FK " +
            "      FROM NASCA.LIEN_PP pp , NASCA.EVENEMENT ev, NASCA.dossier dos " +
            "      WHERE (pp.ETAT_CIVIL = 'MARIE' OR pp.TYPE_LIEN = 'COHAB') " +
            "      AND pp.DT_FINVAL IS NULL " +
            "      AND pp.IDENTITE_LIANTE_FK = dos.IDENTITE_FK " +
            "      AND dos.DOSSIER_IK = ev.DOSSIER_FK " +
            "      AND ev.HIS_STATUT_RECORD = 'A' " +
            "      AND ev.STATUT_EVENEMENT = 'ACT' " +
            "      AND ev.DT_FIN_PERIODE IS NULL " +
            "      AND ev.TYPE_REDUC_COTISATION = 'EXO' " +
            "      AND ev.NATURE_COTISANTE_FK = 5 " +
            "      AND ev.DT_DEBUT_PERIODE < '2016-01-01'" +
            "   ) " +
            ") " +
            "AND NOT EXISTS (SELECT * FROM NASCA.DOSSIER WHERE IDENTITE_FK = id.IDENTITE_FK AND TYPE = 'LS') " +
            "FETCH FIRST 1 ROWS ONLY ";

    public static final String SELECT_NISS_CLOTURE_DOSSIER_SANS_CJT_AIDANT = "SELECT " +
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
            "         GROUP BY DOSSIER_FK HAVING COUNT(DOSSIER_FK) = 1 " +
            "         AND DOSSIER_FK IN " +
            "         ( " +
            "            SELECT " +
            "            DOSSIER_FK " +
            "            FROM NASCA.EVENEMENT " +
            "            WHERE NATURE_COTISANTE_FK = ? " +
            "            AND DT_DEBUT_PERIODE >= ? " +
            "            AND DT_DEBUT_PERIODE < '2015-01-01' " +
            "            AND DT_FIN_PERIODE IS NULL " +
            "            AND TYPE_EVENEMENT IN('DAC','RAC') " +
            "            AND HIS_STATUT_RECORD = 'A' " +
            "            AND STATUT_EVENEMENT = 'ACT' " +
            "         ) " +
            "         AND NOT EXISTS " +
            "         ( " +
            "            SELECT " +
            "            * " +
            "            FROM NASCA.DEMANDE dem " +
            "            WHERE dem.DOSSIER_FK = DOSSIER_IK " +
            "            AND " +
            "            ( " +
            "               dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_CESSATION " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_ASSIMILATION_MALADIE " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_ASSURANCE_CONTINUEE " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_DECES " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_MANDAT_GRATUIT " +
            "               ) " +
            "               OR dem.DEMANDE_IK IN " +
            "               ( " +
            "                  SELECT " +
            "                  DEMANDE_IK " +
            "                  FROM NASCA.DEMANDE_NON_ASSUJETTISSEMENT " +
            "               ) " +
            "            ) " +
            "         ) " +
            "      ) " +
            "   ) " +
            "   AND a.IDENTITE_IK IN " +
            "   ( " +
            "      SELECT " +
            "      pp.IDENTITE_LIEE_FK " +
            "      FROM NASCA.LIEN_PP pp " +
            "      WHERE (pp.ETAT_CIVIL IN('CELIB', 'DIVORCE', 'TRANS_DIV', 'SEPARE', 'VEUF', 'INDET')) " +
            "      AND pp.DT_FINVAL IS NULL " +
            "   ) " +
            ") " +
            "AND NOT EXISTS (SELECT * FROM NASCA.DOSSIER WHERE IDENTITE_FK = id.IDENTITE_FK AND TYPE = 'LS') " +
            "FETCH FIRST 1 ROWS ONLY ";

    // IRRECOUVRABLE
    public static final String SELECT_NISS_IRRECOUVRABLE = "SELECT " +
            "a.NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b " +
            "WHERE a.IDENTITE_FK = b.IDENTITE_IK " +
            "AND a.TYPE_IDENTIFIANT = 'NISS' " +
            "AND b.IDENTITE_IK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER d " +
            "   WHERE d.DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_FK " +
            "      FROM NASCA.PERIODE_AFFILIATION_PP " +
            "      WHERE STATUT = 'ACTIF'  AND DT_FIN_PERIODE IS NULL " +
            "   ) " +
            "   AND DOSSIER_IK IN(SELECT dossier_FK FROM NASCA.ENTITE_COMPTABLE WHERE nature = 'COT_PP' AND PERIODE = ? AND MONTANT_CIBLE > 0 AND SOLDE > 200 AND ANNULE = 'F') " +
            " ) " +
            "AND b.IDENTITE_IK NOT IN(SELECT IDENTITE_FK FROM NASCA.TRAITEMENT WHERE TYPE like 'IRREC%') " +
            "FETCH FIRST 15 ROWS ONLY";

    // PRESCRIPTION
    public static final String SELECT_NISS_PRESCRIPTION_PP = "SELECT " +
            "a.NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b " +
            "WHERE a.IDENTITE_FK = b.IDENTITE_IK " +
            "AND a.TYPE_IDENTIFIANT = 'NISS' " +
            "AND b.IDENTITE_IK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER d " +
            "   WHERE d.DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_FK " +
            "      FROM NASCA.PERIODE_AFFILIATION_PP " +
            "      WHERE STATUT = 'ACTIF'  AND DT_FIN_PERIODE IS NULL " +
            "   ) " +
            "   AND DOSSIER_IK IN(SELECT dossier_FK FROM NASCA.ENTITE_COMPTABLE WHERE nature = 'COT_PP' AND PERIODE = ? AND MONTANT_CIBLE > 0 AND SOLDE > 200 AND ANNULE = 'F') " +
            " ) " +
            "AND b.IDENTITE_IK NOT IN(SELECT IDENTITE_FK FROM NASCA.TRAITEMENT WHERE TYPE like 'PRE%') " +
            "FETCH FIRST 15 ROWS ONLY";

    public static final String SELECT_BCE_PRESCRIPTION_PM = "SELECT " +
            "a.NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b " +
            "WHERE a.IDENTITE_FK = b.IDENTITE_IK " +
            "AND a.TYPE_IDENTIFIANT = 'BCE' " +
            "AND b.IDENTITE_IK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER d " +
            "   WHERE d.DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_FK " +
            "      FROM NASCA.PERIODE_AFFILIATION_PM " +
            "      WHERE STATUT = 'ACTIF' " +
            "      AND DT_FIN_PERIODE IS NULL " +
            "   ) " +
            " AND DOSSIER_IK IN(SELECT dossier_FK FROM NASCA.ENTITE_COMPTABLE WHERE nature = 'COT_PM' AND PERIODE = ? AND SOLDE > 200 AND ANNULE = 'F') " +
            ") " +
            "AND b.IDENTITE_IK NOT IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.TRAITEMENT " +
            "   WHERE TYPE like 'PRE%' " +
            ") " +
            "FETCH FIRST 15 ROWS ONLY ";

    // LEVEE MAJORATIONS
    public static final String SELECT_NISS_LEVEE_MAJO_PP_AVEC_COTISATION = "SELECT " +
            "a.NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b " +
            "WHERE a.IDENTITE_FK = b.IDENTITE_IK " +
            "AND a.TYPE_IDENTIFIANT = 'NISS' " +
            "AND b.IDENTITE_IK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER d " +
            "   WHERE d.DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_FK " +
            "      FROM NASCA.PERIODE_AFFILIATION_PP " +
            "      WHERE STATUT = 'ACTIF' " +
            "      AND DT_FIN_PERIODE IS NULL " +
            "   ) " +
            "   AND DOSSIER_IK NOT IN " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_FK " +
            "      FROM NASCA.DEMANDE " +
            "      WHERE DEMANDE_IK IN(SELECT DEMANDE_IK FROM NASCA.DEMANDE_LM) " +
            "   ) " +
            "   AND DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      dossier_FK " +
            "      FROM NASCA.ENTITE_COMPTABLE a " +
            "      WHERE a.nature = 'MAJ_PP' " +
            "      AND a.PERIODE = ? " +
            "      AND a.SOLDE > 0 " +
            "      AND EXISTS " +
            "      ( " +
            "         SELECT " +
            "         * " +
            "         FROM NASCA.ENTITE_COMPTABLE b " +
            "         WHERE b.ENTITE_COMPTABLE_IK = a.COTISATION_FK " +
            "         AND b.solde > 0 " +
            "      ) " +
            "   ) " +
            "	AND DOSSIER_IK NOT IN " +
            "	( " +
            "		SELECT " +
            "		dossier_FK " +
            "		FROM NASCA.ENTITE_COMPTABLE f " +
            "		WHERE f.NATURE like 'F%' " +
            "		AND f.SOLDE > 0 " +
            "	) " +
            ") " +
            "FETCH FIRST 50 ROWS ONLY ";

    public static final String SELECT_NISS_LEVEE_MAJO_PP_SANS_COTISATION = "SELECT " +
            "a.NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b " +
            "WHERE a.IDENTITE_FK = b.IDENTITE_IK " +
            "AND a.TYPE_IDENTIFIANT = 'NISS' " +
            "AND b.IDENTITE_IK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER d " +
            "   WHERE d.DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_FK " +
            "      FROM NASCA.PERIODE_AFFILIATION_PP " +
            "      WHERE STATUT = 'ACTIF' " +
            "      AND DT_FIN_PERIODE IS NULL " +
            "   ) " +
            "   AND DOSSIER_IK NOT IN " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_FK " +
            "      FROM NASCA.DEMANDE " +
            "      WHERE DEMANDE_IK IN(SELECT DEMANDE_IK FROM NASCA.DEMANDE_LM) " +
            "   ) " +
            "   AND DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      dossier_FK " +
            "      FROM NASCA.ENTITE_COMPTABLE a " +
            "      WHERE a.nature = 'MAJ_PP' " +
            "      AND a.PERIODE = ? " +
            "      AND a.SOLDE > 0 " +
            "      AND EXISTS " +
            "      ( " +
            "         SELECT " +
            "         * " +
            "         FROM NASCA.ENTITE_COMPTABLE b " +
            "         WHERE b.ENTITE_COMPTABLE_IK = a.COTISATION_FK " +
            "         AND b.solde = 0 " +
            "      ) " +
            "   ) " +
            "	AND DOSSIER_IK NOT IN " +
            "	( " +
            "		SELECT " +
            "		dossier_FK " +
            "		FROM NASCA.ENTITE_COMPTABLE f " +
            "		WHERE f.NATURE like 'F%' " +
            "		AND f.SOLDE > 0 " +
            "	) " +
            ") " +
            "FETCH FIRST 50 ROWS ONLY ";

    public static final String SELECT_BCE_LEVEE_MAJO_PM_AVEC_COTISATION = "SELECT " +
            "a.NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b " +
            "WHERE a.IDENTITE_FK = b.IDENTITE_IK " +
            "AND a.TYPE_IDENTIFIANT = 'BCE' " +
            "AND b.IDENTITE_IK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER d " +
            "   WHERE d.DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_FK " +
            "      FROM NASCA.PERIODE_AFFILIATION_PM " +
            "      WHERE STATUT = 'ACTIF' " +
            "      AND DT_FIN_PERIODE IS NULL " +
            "   ) " +
            "   AND DOSSIER_IK NOT IN " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_FK " +
            "      FROM NASCA.DEMANDE " +
            "      WHERE DEMANDE_IK IN(SELECT DEMANDE_IK FROM NASCA.DEMANDE_LM) " +
            "   ) " +
            "   AND DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      dossier_FK " +
            "      FROM NASCA.ENTITE_COMPTABLE a " +
            "      WHERE a.nature = 'MAJ_PM' " +
            "      AND a.SOLDE > 0 " +
            "      AND a.PERIODE = ? " +
            "      AND EXISTS " +
            "      ( " +
            "         SELECT " +
            "         * " +
            "         FROM NASCA.ENTITE_COMPTABLE b " +
            "         WHERE b.ENTITE_COMPTABLE_IK = a.COTISATION_FK " +
            "         AND b.solde > 0 " +
            "      ) " +
            "   ) " +
            "	AND DOSSIER_IK NOT IN " +
            "	( " +
            "		SELECT " +
            "		dossier_FK " +
            "		FROM NASCA.ENTITE_COMPTABLE f " +
            "		WHERE f.NATURE like 'F%' " +
            "		AND f.SOLDE > 0 " +
            "	) " +
            ") " +
            "FETCH FIRST 50 ROWS ONLY ";

    public static final String SELECT_BCE_LEVEE_MAJO_PM_SANS_COTISATION = "SELECT " +
            "a.NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b " +
            "WHERE a.IDENTITE_FK = b.IDENTITE_IK " +
            "AND a.TYPE_IDENTIFIANT = 'BCE' " +
            "AND b.IDENTITE_IK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER d " +
            "   WHERE d.DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_FK " +
            "      FROM NASCA.PERIODE_AFFILIATION_PM " +
            "      WHERE STATUT = 'ACTIF' " +
            "      AND DT_FIN_PERIODE IS NULL " +
            "   ) " +
            "   AND DOSSIER_IK NOT IN " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_FK " +
            "      FROM NASCA.DEMANDE " +
            "      WHERE DEMANDE_IK IN(SELECT DEMANDE_IK FROM NASCA.DEMANDE_LM) " +
            "   ) " +
            "   AND DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      dossier_FK " +
            "      FROM NASCA.ENTITE_COMPTABLE a " +
            "      WHERE a.nature = 'MAJ_PM' " +
            "      AND a.SOLDE > 0 " +
            "      AND a.PERIODE = ? " +
            "      AND EXISTS " +
            "      ( " +
            "         SELECT " +
            "         * " +
            "         FROM NASCA.ENTITE_COMPTABLE b " +
            "         WHERE b.ENTITE_COMPTABLE_IK = a.COTISATION_FK " +
            "         AND b.solde = 0 " +
            "      ) " +
            "	   AND NOT EXISTS " +
            "	   ( " +
            "		  SELECT " +
            "		  * " +
            "		  FROM NASCA.ENTITE_COMPTABLE s " +
            "		  WHERE s.DOSSIER_FK = a.DOSSIER_FK " +
            "		  AND s.nature = 'MAJ_PM' " +
            "		  AND s.PERIODE = a.PERIODE " +
            "		  AND s.solde < 0 " +
            "	    ) " +
            "   ) " +
            "	AND DOSSIER_IK NOT IN " +
            "	( " +
            "		SELECT " +
            "		dossier_FK " +
            "		FROM NASCA.ENTITE_COMPTABLE f " +
            "		WHERE f.NATURE like 'F%' " +
            "		AND f.SOLDE > 0 " +
            "	) " +
            ") " +
            "FETCH FIRST 50 ROWS ONLY ";

    // PLAN APUREMENT
    public static final String SELECT_NISS_PA = "SELECT " +
            "a.NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b " +
            "WHERE a.IDENTITE_FK = b.IDENTITE_IK " +
            "AND a.TYPE_IDENTIFIANT = 'NISS' " +
            "AND b.IDENTITE_IK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER d " +
            "   WHERE d.DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_FK " +
            "      FROM NASCA.PERIODE_AFFILIATION_PP " +
            "      WHERE STATUT = 'ACTIF' " +
            "      AND DT_FIN_PERIODE IS NULL " +
            "   ) " +
            "   AND NOT EXISTS " +
            "   ( " +
            "      SELECT * " +
            "      FROM NASCA.DEMANDE dem " +
            "      WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.DEMANDE_IK IN(SELECT DEMANDE_IK FROM NASCA.DEMANDE_LM) " +
            "   ) " +
            "   AND NOT EXISTS " +
            "   ( " +
            "      SELECT * " +
            "      FROM NASCA.DEMANDE dem " +
            "      WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.DEMANDE_IK IN(SELECT DEMANDE_IK FROM NASCA.DEMANDE_PLAN_APUREMENT) " +
            "   ) " +
            "   AND DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      dossier_FK " +
            "      FROM NASCA.ENTITE_COMPTABLE a " +
            "      WHERE a.nature = 'COT_PP' " +
            "      AND a.PERIODE = ? " +
            "      AND a.type = 'ORD' " +
            "      AND a.SOLDE > 50 " +
            "      AND EXISTS " +
            "      ( " +
            "         SELECT " +
            "         * " +
            "         FROM NASCA.ENTITE_COMPTABLE b " +
            "         WHERE a.ENTITE_COMPTABLE_IK = b.COTISATION_FK " +
            "         AND b.solde > 0 " +
            "      ) " +
            "      AND EXISTS " +
            "      ( " +
            "         SELECT " +
            "         * " +
            "         FROM NASCA.RECOUVREMENT r " +
            "         WHERE a.ENTITE_COMPTABLE_IK = r.CREANCE_FK " +
            "         AND r.STADE in('RA','EN') " +
            "      ) " +
            "   ) " +
            "	AND DOSSIER_IK NOT IN " +
            "	 ( " +
            "		SELECT " +
            "		dossier_FK " +
            "		FROM NASCA.ENTITE_COMPTABLE a " +
            "		WHERE a.nature = 'COT_PP' " +
            "		AND a.PERIODE = ? " +
            "		AND a.type <> 'ORD' " +
            "		AND a.SOLDE > 50 " +
            "	) " +
            ") FETCH FIRST 30 ROWS ONLY";

    public static final String SELECT_BCE_PA = "SELECT " +
            "a.NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b " +
            "WHERE a.IDENTITE_FK = b.IDENTITE_IK " +
            "AND a.TYPE_IDENTIFIANT = 'BCE' " +
            "AND b.IDENTITE_IK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER d " +
            "   WHERE d.DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_FK " +
            "      FROM NASCA.PERIODE_AFFILIATION_PM " +
            "      WHERE STATUT = 'ACTIF' " +
            "      AND DT_FIN_PERIODE IS NULL " +
            "   ) " +
            "   AND NOT EXISTS " +
            "   ( " +
            "      SELECT * " +
            "      FROM NASCA.DEMANDE dem " +
            "      WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.DEMANDE_IK IN(SELECT DEMANDE_IK FROM NASCA.DEMANDE_LM) " +
            "   ) " +
            "   AND NOT EXISTS " +
            "   ( " +
            "      SELECT * " +
            "      FROM NASCA.DEMANDE dem " +
            "      WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.DEMANDE_IK IN(SELECT DEMANDE_IK FROM NASCA.DEMANDE_PLAN_APUREMENT) " +
            "   ) " +
            "   AND NOT EXISTS " +
            "   ( " +
            "      SELECT * " +
            "      FROM NASCA.DEMANDE dem " +
            "      WHERE dem.DOSSIER_FK = DOSSIER_IK AND dem.DEMANDE_IK IN(SELECT DEMANDE_IK FROM NASCA.DEMANDE_MISE_EN_VEILLEUSE) " +
            "   ) " +
            "   AND DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      dossier_FK " +
            "      FROM NASCA.ENTITE_COMPTABLE a " +
            "      WHERE a.nature = 'COT_PM' " +
            "      AND a.SOLDE > 50 " +
            "      AND a.PERIODE = ? " +
            "      AND EXISTS " +
            "      ( " +
            "         SELECT " +
            "         * " +
            "         FROM NASCA.ENTITE_COMPTABLE b " +
            "         WHERE a.ENTITE_COMPTABLE_IK = b.COTISATION_FK " +
            "         AND b.solde > 0 " +
            "      ) " +
            "   ) " +
            ") FETCH FIRST 30 ROWS ONLY";

    // ENROLEMENT MAJORATIONS PP
    public static final String SELECT_NUMERO_PP_ENROLMAJO = "SELECT numero  FROM NASCA.IDENTITE a , NASCA.identifiant b WHERE"
            + "			a.IDENTITE_IK=b.IDENTITE_FK AND type_identite = 'PP' AND b.TYPE_IDENTIFIANT = 'NISS'"
            + "			AND a.IDENTITE_IK IN(SELECT identite_ik FROM NASCA.PERSONNE_PHYSIQUE WHERE DT_DECES IS NULL)"
            + "			AND  a.IDENTITE_ik IN(SELECT IDENTITE_FK FROM NASCA.dossier WHERE"
            + "			dossier_ik IN(SELECT DOSSIER_FK FROM NASCA.DEMANDE )"
            + "			AND DOSSIER_IK IN(SELECT DOSSIER_FK FROM NASCA.PERIODE_AFFILIATION_PP WHERE  statut = 'ACTIF' AND DT_DEBUT_PERIODE between '2003-01-01' AND '2014-01-01' AND DT_FIN_PERIODE IS NULL )"
            + "			AND dossier_ik IN(SELECT dossier_fk FROM NASCA.EVENEMENT  group by  dossier_fk having sum(dossier_fk)> 1)"
            + "			AND DOSSIER_iK IN(SELECT dossier_FK FROM NASCA.ENTITE_COMPTABLE WHERE nature = 'COT_PP' AND PERIODE = ? AND MONTANT_CIBLE > 0 AND SOLDE > 0)"
            + "			AND dossier_IK  NOT IN(SELECT dossier_fk FROM NASCA.EVENEMENT WHERE NATURE_COTISANTE_FK  IN(SELECT nature_cotisante_ik FROM NASCA.NATURE_COTISANTE WHERE CODE = 'Assmal')AND DT_FIN_PERIODE IS NULL))FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_PP_RDE_PAIEMENT_NUL_ENROL_MAJO = "SELECT "
            + "NUM_IDENTIFIANT "
            + "FROM NASCA.IDENTIFIANT "
            + "WHERE TYPE_IDENTIFIANT = 'NISS' "
            + "AND IDENTITE_FK IN "
            + "( "
            + "   SELECT "
            + "   IDENTITE_FK "
            + "   FROM NASCA.DOSSIER "
            + "   WHERE DOSSIER_IK IN "
            + "   ( "
            + "      SELECT "
            + "      d.DOSSIER_IK "
            + "      FROM NASCA.DOSSIER d "
            + "      LEFT JOIN NASCA.EVENEMENT e ON d.DOSSIER_IK = e.DOSSIER_FK "
            + "      LEFT JOIN NASCA.ENTITE_COMPTABLE a ON a.DOSSIER_FK = d.DOSSIER_IK "
            + "      WHERE a.NATURE = 'COT_PP' "
            + "      AND a.PERIODE = ? "
            + "      AND a.SOLDE = a.MONTANT_CIBLE "
            + "      AND a.MONTANT_CIBLE > 0 "
            + "      AND e.NATURE_COTISANTE_FK = ? "
            + "      AND e.DT_DEBUT_PERIODE <= ? "
            + "      AND e.DT_FIN_PERIODE IS NULL "
            + "      AND e.HIS_STATUT_RECORD = 'A' "
            + "      AND e.STATUT_EVENEMENT = 'ACT' "
            + "      AND a.ANNULE = 'F' "
            + "      AND a.TYPE = 'ORD' "
            + "      AND NOT EXISTS (SELECT * FROM NASCA.RECOUVREMENT WHERE CREANCE_FK = a.ENTITE_COMPTABLE_IK "
            + "                      AND STADE IN('CTX','MED'))"
            + "      AND NOT EXISTS (SELECT * FROM NASCA.RECOUVREMENT WHERE CREANCE_FK = a.ENTITE_COMPTABLE_IK "
            + "                      AND SUSPENDU = 'T')"
            + "   ) "
            + ") FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_PP_RDE_PAIEMENT_PARTIEL_ENROL_MAJO = "SELECT "
            + "NUM_IDENTIFIANT "
            + "FROM NASCA.IDENTIFIANT "
            + "WHERE TYPE_IDENTIFIANT = 'NISS' "
            + "AND IDENTITE_FK IN "
            + "( "
            + "   SELECT "
            + "   IDENTITE_FK "
            + "   FROM NASCA.DOSSIER "
            + "   WHERE DOSSIER_IK IN "
            + "   ( "
            + "      SELECT "
            + "      d.DOSSIER_IK "
            + "      FROM NASCA.DOSSIER d "
            + "      LEFT JOIN NASCA.EVENEMENT e ON d.DOSSIER_IK = e.DOSSIER_FK "
            + "      LEFT JOIN NASCA.ENTITE_COMPTABLE a ON a.DOSSIER_FK = d.DOSSIER_IK "
            + "      WHERE a.NATURE = 'COT_PP' "
            + "      AND a.PERIODE = ? "
            + "      AND a.SOLDE > 0 AND a.SOLDE < a.MONTANT_CIBLE "
            + "      AND a.MONTANT_CIBLE > 0 "
            + "      AND e.NATURE_COTISANTE_FK = ? "
            + "      AND e.DT_DEBUT_PERIODE <= ? "
            + "      AND e.DT_FIN_PERIODE IS NULL "
            + "      AND e.HIS_STATUT_RECORD = 'A' "
            + "      AND e.STATUT_EVENEMENT = 'ACT' "
            + "      AND a.ANNULE = 'F' "
            + "      AND a.TYPE = 'ORD' "
            + "      AND NOT EXISTS (SELECT * FROM NASCA.RECOUVREMENT WHERE CREANCE_FK = a.ENTITE_COMPTABLE_IK "
            + "                      AND STADE IN('CTX','MED'))"
            + "      AND NOT EXISTS (SELECT * FROM NASCA.RECOUVREMENT WHERE CREANCE_FK = a.ENTITE_COMPTABLE_IK "
            + "                      AND SUSPENDU = 'T')"
            + "   ) "
            + ") FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_PP_RDE_PAIEMENT_TOTAL_ENROL_MAJO = "SELECT "
            + "NUM_IDENTIFIANT "
            + "FROM NASCA.IDENTIFIANT "
            + "WHERE TYPE_IDENTIFIANT = 'NISS' "
            + "AND IDENTITE_FK IN "
            + "( "
            + "   SELECT "
            + "   IDENTITE_FK "
            + "   FROM NASCA.DOSSIER "
            + "   WHERE DOSSIER_IK IN "
            + "   ( "
            + "      SELECT "
            + "      d.DOSSIER_IK "
            + "      FROM NASCA.DOSSIER d "
            + "      LEFT JOIN NASCA.EVENEMENT e ON d.DOSSIER_IK = e.DOSSIER_FK "
            + "      LEFT JOIN NASCA.ENTITE_COMPTABLE a ON a.DOSSIER_FK = d.DOSSIER_IK "
            + "      WHERE a.NATURE = 'COT_PP' "
            + "      AND a.PERIODE = ? "
            + "      AND SOLDE = 0 AND a.MONTANT_CIBLE > 0 "
            + "      AND a.MONTANT_CIBLE > 0 "
            + "      AND e.NATURE_COTISANTE_FK = ? "
            + "      AND e.DT_DEBUT_PERIODE <= ? "
            + "      AND e.DT_FIN_PERIODE IS NULL "
            + "      AND e.HIS_STATUT_RECORD = 'A' "
            + "      AND e.STATUT_EVENEMENT = 'ACT' "
            + "      AND a.ANNULE = 'F' "
            + "      AND a.TYPE = 'ORD' "
            + "      AND NOT EXISTS (SELECT * FROM NASCA.RECOUVREMENT WHERE CREANCE_FK = a.ENTITE_COMPTABLE_IK "
            + "                      AND STADE IN('CTX','MED'))"
            + "      AND NOT EXISTS (SELECT * FROM NASCA.RECOUVREMENT WHERE CREANCE_FK = a.ENTITE_COMPTABLE_IK "
            + "                      AND SUSPENDU = 'T')"
            + "   ) "
            + ") FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_PP_RDA_PAIEMENT_NUL_ENROL_MAJO = "SELECT "
            + "NUM_IDENTIFIANT "
            + "FROM NASCA.IDENTIFIANT "
            + "WHERE TYPE_IDENTIFIANT = 'NISS' "
            + "AND IDENTITE_FK IN "
            + "( "
            + "   SELECT "
            + "   IDENTITE_FK "
            + "   FROM NASCA.DOSSIER "
            + "   WHERE DOSSIER_IK IN "
            + "   ( "
            + "      SELECT "
            + "      d.DOSSIER_IK "
            + "      FROM NASCA.DOSSIER d "
            + "      LEFT JOIN NASCA.EVENEMENT e ON d.DOSSIER_IK = e.DOSSIER_FK "
            + "      LEFT JOIN NASCA.ENTITE_COMPTABLE a ON a.DOSSIER_FK = d.DOSSIER_IK "
            + "      WHERE a.NATURE = 'COT_PP' "
            + "      AND a.PERIODE = ? "
            + "      AND a.SOLDE > 0 "
            + "      AND a.SOLDE = a.MONTANT_CIBLE "
            + "      AND a.MONTANT_CIBLE > 0 "
            + "      AND e.NATURE_COTISANTE_FK = ? "
            + "      AND e.DT_DEBUT_PERIODE BETWEEN ? AND ? "
            + "      AND e.DT_FIN_PERIODE IS NULL "
            + "      AND e.HIS_STATUT_RECORD = 'A' "
            + "      AND e.STATUT_EVENEMENT = 'ACT' "
            + "      AND a.ANNULE = 'F' "
            + "      AND a.TYPE = 'ORD' "
            + "      AND NOT EXISTS (SELECT * FROM NASCA.RECOUVREMENT WHERE CREANCE_FK = a.ENTITE_COMPTABLE_IK "
            + "                      AND STADE IN('CTX','MED'))"
            + "      AND NOT EXISTS (SELECT * FROM NASCA.RECOUVREMENT WHERE CREANCE_FK = a.ENTITE_COMPTABLE_IK "
            + "                      AND SUSPENDU = 'T')"
            + "   ) "
            + ") FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_PP_RDA_PAIEMENT_PARTIEL_ENROL_MAJO = "SELECT "
            + "NUM_IDENTIFIANT "
            + "FROM NASCA.IDENTIFIANT "
            + "WHERE TYPE_IDENTIFIANT = 'NISS' "
            + "AND IDENTITE_FK IN "
            + "( "
            + "   SELECT "
            + "   IDENTITE_FK "
            + "   FROM NASCA.DOSSIER "
            + "   WHERE DOSSIER_IK IN "
            + "   ( "
            + "      SELECT "
            + "      d.DOSSIER_IK "
            + "      FROM NASCA.DOSSIER d "
            + "      LEFT JOIN NASCA.EVENEMENT e ON d.DOSSIER_IK = e.DOSSIER_FK "
            + "      LEFT JOIN NASCA.ENTITE_COMPTABLE a ON a.DOSSIER_FK = d.DOSSIER_IK "
            + "      WHERE a.NATURE = 'COT_PP' "
            + "      AND a.PERIODE = ? "
            + "      AND a.SOLDE > 0 AND a.SOLDE < a.MONTANT_CIBLE "
            + "      AND a.MONTANT_CIBLE > 0 "
            + "      AND e.NATURE_COTISANTE_FK = ? "
            + "      AND e.DT_DEBUT_PERIODE BETWEEN ? AND ? "
            + "      AND e.DT_FIN_PERIODE IS NULL "
            + "      AND e.HIS_STATUT_RECORD = 'A' "
            + "      AND e.STATUT_EVENEMENT = 'ACT' "
            + "      AND a.ANNULE = 'F' "
            + "      AND a.TYPE = 'ORD' "
            + "      AND NOT EXISTS (SELECT * FROM NASCA.RECOUVREMENT WHERE CREANCE_FK = a.ENTITE_COMPTABLE_IK "
            + "                      AND STADE IN('CTX','MED'))"
            + "      AND NOT EXISTS (SELECT * FROM NASCA.RECOUVREMENT WHERE CREANCE_FK = a.ENTITE_COMPTABLE_IK "
            + "                      AND SUSPENDU = 'T')"
            + "   ) "
            + ") FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_PP_RDA_PAIEMENT_TOTAL_ENROL_MAJO = "SELECT "
            + "NUM_IDENTIFIANT "
            + "FROM NASCA.IDENTIFIANT "
            + "WHERE TYPE_IDENTIFIANT = 'NISS' "
            + "AND IDENTITE_FK IN "
            + "( "
            + "   SELECT "
            + "   IDENTITE_FK "
            + "   FROM NASCA.DOSSIER "
            + "   WHERE DOSSIER_IK IN "
            + "   ( "
            + "      SELECT "
            + "      d.DOSSIER_IK "
            + "      FROM NASCA.DOSSIER d "
            + "      LEFT JOIN NASCA.EVENEMENT e ON d.DOSSIER_IK = e.DOSSIER_FK "
            + "      LEFT JOIN NASCA.ENTITE_COMPTABLE a ON a.DOSSIER_FK = d.DOSSIER_IK "
            + "      WHERE a.NATURE = 'COT_PP' "
            + "      AND a.PERIODE = ? "
            + "      AND a.SOLDE = 0 AND a.MONTANT_CIBLE > 0 "
            + "      AND a.MONTANT_CIBLE > 0 "
            + "      AND e.NATURE_COTISANTE_FK = ? "
            + "      AND e.DT_DEBUT_PERIODE BETWEEN ? AND ? "
            + "      AND e.DT_FIN_PERIODE IS NULL "
            + "      AND e.HIS_STATUT_RECORD = 'A' "
            + "      AND e.STATUT_EVENEMENT = 'ACT' "
            + "      AND a.ANNULE = 'F' "
            + "      AND a.TYPE = 'ORD' "
            + "      AND NOT EXISTS (SELECT * FROM NASCA.RECOUVREMENT WHERE CREANCE_FK = a.ENTITE_COMPTABLE_IK "
            + "                      AND STADE IN('CTX','MED'))"
            + "      AND NOT EXISTS (SELECT * FROM NASCA.RECOUVREMENT WHERE CREANCE_FK = a.ENTITE_COMPTABLE_IK "
            + "                      AND SUSPENDU = 'T')"
            + "   ) "
            + ") FETCH FIRST 1 ROWS ONLY";

    // ENROLEMENT MAJORATIONS PM
    public static final String SELECT_PM_PAIEMENT_NUL_ENROL_MAJO = "SELECT " +
            "NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT " +
            "WHERE TYPE_IDENTIFIANT = 'BCE' " +
            "AND IDENTITE_FK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER " +
            "   WHERE DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      d.DOSSIER_IK " +
            "      FROM NASCA.DOSSIER d " +
            "      LEFT JOIN NASCA.EVENEMENT e ON D.DOSSIER_IK = e.DOSSIER_FK " +
            "      LEFT JOIN NASCA.ENTITE_COMPTABLE a ON A.DOSSIER_FK = d.DOSSIER_IK " +
            "      LEFT JOIN NASCA.PERIODE_AFFILIATION_PM f ON D.DOSSIER_IK= f.DOSSIER_FK " +
            "      WHERE a.NATURE = 'COT_PM' " +
            "      AND a.PERIODE = ? " +
            "      AND a.TYPE = ? " +
            "      AND a.ENTITE_COMPTABLE_IK IN " +
            "      ( " +
            "         SELECT " +
            "         a.ENTITE_COMPTABLE_IK " +
            "         FROM NASCA.ENTITE_COMPTABLE " +
            "         LEFT JOIN NASCA.OPERATION_COMPTABLE b ON a.ENTITE_COMPTABLE_IK = b.ENTITE_COMPTABLE_FK " +
            "         WHERE b.TYPE = 'ENR' " +
            "         AND a.SOLDE = b.MONTANT_GLOBAL " +
            "      ) " +
            "   ) " +
            ") FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_PM_PAIEMENT_PARTIEL_ENROL_MAJO = "SELECT " +
            "NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT " +
            "WHERE TYPE_IDENTIFIANT = 'BCE' " +
            "AND IDENTITE_FK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER " +
            "   WHERE DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      d.DOSSIER_IK " +
            "      FROM NASCA.DOSSIER d " +
            "      LEFT JOIN NASCA.EVENEMENT e ON D.DOSSIER_IK = e.DOSSIER_FK " +
            "      LEFT JOIN NASCA.ENTITE_COMPTABLE a ON A.DOSSIER_FK = d.DOSSIER_IK " +
            "      LEFT JOIN NASCA.PERIODE_AFFILIATION_PM f ON D.DOSSIER_IK= f.DOSSIER_FK " +
            "      WHERE a.NATURE = 'COT_PM' " +
            "      AND a.PERIODE = ? " +
            "      AND a.TYPE = ? " +
            "      AND a.ENTITE_COMPTABLE_IK IN " +
            "      ( " +
            "         SELECT " +
            "         a.ENTITE_COMPTABLE_IK " +
            "         FROM NASCA.ENTITE_COMPTABLE " +
            "         LEFT JOIN NASCA.OPERATION_COMPTABLE b ON a.ENTITE_COMPTABLE_IK = b.ENTITE_COMPTABLE_FK " +
            "         WHERE b.TYPE = 'ENR' " +
            " 		  AND a.SOLDE > 0 " +
            "         AND a.SOLDE < b.MONTANT_GLOBAL " +
            "      ) " +
            "   ) " +
            ") FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_PM_PAIEMENT_TOTAL_ENROL_MAJO = "SELECT " +
            "NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT " +
            "WHERE TYPE_IDENTIFIANT = 'BCE' " +
            "AND IDENTITE_FK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER " +
            "   WHERE DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      d.DOSSIER_IK " +
            "      FROM NASCA.DOSSIER d " +
            "      LEFT JOIN NASCA.EVENEMENT e ON D.DOSSIER_IK = e.DOSSIER_FK " +
            "      LEFT JOIN NASCA.ENTITE_COMPTABLE a ON A.DOSSIER_FK = d.DOSSIER_IK " +
            "      LEFT JOIN NASCA.PERIODE_AFFILIATION_PM f ON D.DOSSIER_IK= f.DOSSIER_FK " +
            "      WHERE a.NATURE = 'COT_PM' " +
            "      AND a.PERIODE = ? " +
            "      AND a.TYPE = ? " +
            "      AND a.ENTITE_COMPTABLE_IK IN " +
            "      ( " +
            "         SELECT " +
            "         a.ENTITE_COMPTABLE_IK " +
            "         FROM NASCA.ENTITE_COMPTABLE " +
            "         LEFT JOIN NASCA.OPERATION_COMPTABLE b ON a.ENTITE_COMPTABLE_IK = b.ENTITE_COMPTABLE_FK " +
            "         WHERE b.TYPE = 'ENR' " +
            " 		  AND a.SOLDE = 0 " +
            "      ) " +
            "   ) " +
            ") FETCH FIRST 1 ROWS ONLY";

    // EXONERATION PM
    public static final String SELECT_BCE_EXONERATION_SANS_CO_MEV = "SELECT a.NUM_IDENTIFIANT FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b "
            + "WHERE a.IDENTITE_FK = b.IDENTITE_IK AND a.TYPE_IDENTIFIANT = 'BCE' "
            + "AND b.IDENTITE_IK IN "
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.PERIODE_AFFILIATION_PM WHERE STATUT = 'ACTIF' "
            + "AND DT_DEBUT_PERIODE >= ?  AND DEMANDE_FK IN "
            + "(SELECT DEMANDE_IK FROM NASCA.DEMANDE_AFFILIATION_PM WHERE CAISSE_ORIGINE_FK IS NULL)) "
            + "AND DOSSIER_IK NOT IN(SELECT DOSSIER_FK FROM NASCA.DEMANDE WHERE DEMANDE_IK IN "
            + "(SELECT DEMANDE_IK FROM NASCA.DEMANDE_EXONERATION_PM)) "
            + "AND DOSSIER_IK NOT IN(SELECT DOSSIER_FK FROM NASCA.EXONERATION_PM) "
            + "AND DOSSIER_IK NOT IN(SELECT DOSSIER_FK FROM NASCA.MISE_EN_VEILLEUSE))"
            + "AND b.IDENTITE_IK NOT IN(SELECT IDENTITE_FK FROM NASCA.WORKFLOW "
            + "WHERE TYPE_WORKFLOW = 'EXO' AND STATUT = 'EN_COURS') FETCH FIRST 30 ROWS ONLY";

    public static final String SELECT_BCE_EXONERATION_AVEC_CO_MEV = "SELECT a.NUM_IDENTIFIANT FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b "
            + "WHERE a.IDENTITE_FK = b.IDENTITE_IK AND a.TYPE_IDENTIFIANT = 'BCE' "
            + "AND b.IDENTITE_IK IN "
            + "(SELECT IDENTITE_FK FROM NASCA.DOSSIER WHERE DOSSIER_IK IN "
            + "(SELECT DOSSIER_FK FROM NASCA.PERIODE_AFFILIATION_PM WHERE STATUT = 'ACTIF' "
            + "AND DT_DEBUT_PERIODE >= ?  AND DEMANDE_FK IN "
            + "(SELECT DEMANDE_IK FROM NASCA.DEMANDE_AFFILIATION_PM WHERE CAISSE_ORIGINE_FK IS NOT NULL)) "
            + "AND DOSSIER_IK NOT IN(SELECT DOSSIER_FK FROM NASCA.DEMANDE WHERE DEMANDE_IK IN "
            + "(SELECT DEMANDE_IK FROM NASCA.DEMANDE_EXONERATION_PM)) "
            + "AND DOSSIER_IK NOT IN(SELECT DOSSIER_FK FROM NASCA.EXONERATION_PM) "
            + "AND DOSSIER_IK NOT IN(SELECT DOSSIER_FK FROM NASCA.MISE_EN_VEILLEUSE))"
            + "AND b.IDENTITE_IK NOT IN(SELECT IDENTITE_FK FROM NASCA.WORKFLOW "
            + "WHERE TYPE_WORKFLOW = 'EXO' AND STATUT = 'EN_COURS') FETCH FIRST 1 ROWS ONLY";

    // SIGNALETIQUE
    public static final String SELECT_NISS_SIGNALETIQUE = "SELECT " +
            "NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT " +
            "WHERE IDENTIFIANT_IK IN " +
            "( " +
            "   SELECT " +
            "   IDENTIFIANT_VALIDE_FK " +
            "   FROM NASCA.identite " +
            "   WHERE type_identite = 'PP' " +
            "   AND IDENTITE_IK IN " +
            "   ( " +
            "      SELECT " +
            "      identite_ik " +
            "      FROM NASCA.PERSONNE_PHYSIQUE " +
            "      WHERE DT_DECES IS NULL " +
            "   ) " +
            "   AND ADRESSE_LEGALE_FK IN " +
            "   ( " +
            "      SELECT " +
            "      ADRESSE_IK " +
            "      FROM NASCA.ADRESSE " +
            "      WHERE TYPE_ADRESSE NOT IN('LEGALE_A') " +
            "   ) " +
            "   AND IDENTITE_FK in " +
            "   ( " +
            "   	SELECT " +
            "   	IDENTITE_FK " +
            "   	FROM NASCA.DOSSIER d " +
            "   	WHERE EXISTS (SELECT * FROM NASCA.EVENEMENT WHERE DOSSIER_FK = d.DOSSIER_IK AND STATUT_EVENEMENT = 'ACT') " +
            "   ) " +
            ") " +
            "FETCH FIRST 15 ROWS ONLY ";

    // SOLIDARITE
    public static final String SELECT_NISS_SOLIDARITE_PP = "SELECT NUM_IDENTIFIANT FROM NASCA.IDENTIFIANT WHERE type_IDENTIFIANT = 'NISS' "
            + "AND IDENTITE_FK NOT IN(SELECT IDENTITE_FK FROM NASCA.dossier WHERE type = 'LS' AND CODEBITEUR_FK is not null) "
            + "AND IDENTITE_FK NOT IN(SELECT CODEBITEUR_FK FROM NASCA.dossier WHERE type = 'LS' AND CODEBITEUR_FK is not null) "
            + "AND IDENTITE_FK IN(SELECT IDENTITE_FK FROM NASCA.dossier WHERE DOSSIER_IK IN(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE (TYPE_EVENEMENT = 'DAC' "
            + "AND NATURE_COTISANTE_FK = 1 "
            + "AND DT_DEBUT_PERIODE >= '2010-01-01' "
            + "AND DT_DEBUT_PERIODE <= '2013-01-01' "
            + "AND DT_FIN_PERIODE IS NULL "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND HIS_STATUT_RECORD = 'A')) "
            + "AND IDENTITE_FK NOT IN(SELECT IDENTITE_FK FROM NASCA.dossier WHERE DOSSIER_IK IN(SELECT DOSSIER_FK FROM NASCA.SOLIDARITE "
            + "WHERE STATUT = 'ACTIF'))) FETCH FIRST 100 ROWS ONLY";

    public static final String SELECT_BCE_SOLIDARITE_PM = "SELECT NUM_IDENTIFIANT FROM NASCA.IDENTIFIANT WHERE type_IDENTIFIANT = 'BCE' "
            + "AND IDENTITE_FK NOT IN(SELECT IDENTITE_FK FROM NASCA.dossier WHERE type = 'LS' AND CODEBITEUR_FK is not null) "
            + "AND IDENTITE_FK NOT IN(SELECT CODEBITEUR_FK FROM NASCA.dossier WHERE type = 'LS' AND CODEBITEUR_FK is not null) "
            + "AND IDENTITE_FK IN(SELECT IDENTITE_FK FROM NASCA.dossier WHERE DOSSIER_IK IN(SELECT DOSSIER_FK FROM NASCA.PERIODE_AFFILIATION_PM "
            + "WHERE (STATUT = 'ACTIF' AND DT_DEBUT_PERIODE >= '2010-01-01' AND DT_DEBUT_PERIODE <= '2013-01-01' AND DT_FIN_PERIODE IS NULL)) "
            + "AND IDENTITE_FK NOT IN(SELECT IDENTITE_FK FROM NASCA.dossier WHERE DOSSIER_IK IN(SELECT DOSSIER_FK FROM NASCA.SOLIDARITE "
            + "WHERE STATUT = 'ACTIF'))) FETCH FIRST 1 ROWS ONLY";

    public static final String SELECT_NISS_SOLIDARITE_DEBITEUR_CODEBITEUR_PP = "SELECT NUM_IDENTIFIANT FROM NASCA.IDENTIFIANT WHERE type_IDENTIFIANT = 'NISS' "
            + "AND IDENTITE_FK NOT IN(SELECT IDENTITE_FK FROM NASCA.dossier WHERE type = 'LS' AND CODEBITEUR_FK is not null) "
            + "AND IDENTITE_FK NOT IN(SELECT CODEBITEUR_FK FROM NASCA.dossier WHERE type = 'LS' AND CODEBITEUR_FK is not null) "
            + "AND IDENTITE_FK IN(SELECT IDENTITE_IK FROM NASCA.identite WHERE TYPE_IDENTITE = 'PP') "
            + "AND IDENTITE_FK IN(SELECT IDENTITE_FK FROM NASCA.dossier WHERE DOSSIER_IK IN(SELECT DOSSIER_FK FROM NASCA.EVENEMENT "
            + "WHERE (TYPE_EVENEMENT = 'DAC' "
            + "AND NATURE_COTISANTE_FK = 1 "
            + "AND DT_DEBUT_PERIODE >= '2010-01-01' "
            + "AND DT_DEBUT_PERIODE <= '2013-01-01' "
            + "AND DT_FIN_PERIODE IS NULL "
            + "AND STATUT_EVENEMENT = 'ACT' "
            + "AND HIS_STATUT_RECORD = 'A' ))"
            + "AND IDENTITE_FK NOT IN(SELECT IDENTITE_FK FROM NASCA.dossier WHERE DOSSIER_IK IN(SELECT DOSSIER_FK FROM NASCA.SOLIDARITE "
            + "WHERE STATUT = 'ACTIF'))) FETCH FIRST 2 ROWS ONLY";

    public static final String SELECT_BCE_SOLIDARITE_DEBITEUR_CODEBITEUR_PM = "SELECT NUM_IDENTIFIANT FROM NASCA.IDENTIFIANT WHERE type_IDENTIFIANT = 'BCE' "
            + "AND IDENTITE_FK NOT IN(SELECT IDENTITE_FK FROM NASCA.dossier WHERE type = 'LS' AND CODEBITEUR_FK is not null) "
            + "AND IDENTITE_FK NOT IN(SELECT CODEBITEUR_FK FROM NASCA.dossier WHERE type = 'LS' AND CODEBITEUR_FK is not null) "
            + "AND IDENTITE_FK IN(SELECT IDENTITE_FK FROM NASCA.dossier WHERE DOSSIER_IK IN(SELECT DOSSIER_FK FROM NASCA.PERIODE_AFFILIATION_PM "
            + "WHERE (STATUT = 'ACTIF' AND DT_DEBUT_PERIODE >= '2010-01-01' AND DT_DEBUT_PERIODE <= '2013-01-01' AND DT_FIN_PERIODE IS NULL)) "
            + "AND IDENTITE_FK NOT IN(SELECT IDENTITE_FK FROM NASCA.dossier WHERE DOSSIER_IK IN(SELECT DOSSIER_FK FROM NASCA.SOLIDARITE "
            + "WHERE STATUT = 'ACTIF'))) FETCH FIRST 2 ROWS ONLY";

    // COMPTA DOMICILIATION
    public static final String SELECT_IBAN_FROM_NISS = "SELECT IBAN FROM mANDat.debiteur a , mANDat.mANDat b  WHERE a.debiteur_ik=b.debiteur_fk AND niss= ? AND b.workflow_fk IN"
            + "(SELECT entry_id FROM mANDat.os_currentstep WHERE step_id IN('30','35','40'))";

    public static final String SELECT_MANDAT_TYPE_FROM_NISS = "SELECT type_mANDat_fk FROMmANDat.debiteur a , mANDat.mANDat b  WHERE a.debiteur_ik=b.debiteur_fk AND niss= ? AND b.workflow_fk IN"
            + "(SELECT entry_id FROM mANDat.os_currentstep WHERE step_id IN('30','35','40'))";

    public static final String SELECT_NOM_FROM_MANDAT = "SELECT titulaire FROMmANDat.debiteur a , mANDat.mANDat b  WHERE a.debiteur_ik=b.debiteur_fk AND niss= ? AND b.workflow_fk IN"
            + "(SELECT entry_id FROM mANDat.os_currentstep WHERE step_id IN('30','35','40'))";

    // COMPTA
    public static final String INSERT_PAIEMENT_COMPTABLE_PORTEFEUILLE =
            "INSERT INTO NASCA.PAIEMENT_COMPTABLE (IDENTITE_FK,DT_CREATION,MONTANT_TOTAL,MONTANT_DISPO,MONTANT_AFFECTE,MONTANT_PROPOSE,NOM_PAYEUR,IBAN_PAYEUR,COMMUNICATION,STRUCTURE,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,DT_VALEUR,BIC_PAYEUR,TYPE_OPERATION,REFERENCE_CLIENT,COMPTE_CAS_FK,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA,ADRESSE1_PAYEUR,MONTANT_LITIGIEUX,MONTANT_REMBOURSE,LITIGIEUX_FK,ADRESSE2_PAYEUR,CODE_REFUS_DOM,VISIBLE,EQUIPE_FK,MONTANT_LITIGIEUX_UTILISE,STATUT_PAIEMENT) VALUES"
                    + "(null,'"
                    + new java.sql.Timestamp(new java.util.Date().getTime())
                    + "',100000.00,100000.00,0.00,0.00,null,null,'test.selenium','F','"
                    + new java.sql.Timestamp(new java.util.Date().getTime())
                    + "',0,null,?,null,'PAIE',null,10,'"
                    + new java.sql.Timestamp(new java.util.Date().getTime())
                    + "',0,null,null,0.00,0.00,null,null,null,'T',11,0.00,'A_TRAITER')";

    public static final String UPDATE_PAIEMENT_COMPTABLE_PORTEFEUILLE = "update NASCA.PAIEMENT_COMPTABLE set DT_VALEUR = ? " +
            "WHERE COMMUNICATION = 'test.selenium' " +
            "AND MONTANT_DISPO > '5000.00' ";

    // VCS -FOR CODA
    public static final String SELECT_VCS_CODE = "SELECT content FROM NASCA.VCS WHERE destinataire_fk IN"
            + "(SELECT identite_ik FROM NASCA.IDENTITE WHERE identifiant_valide_fk IN"
            + "(SELECT identifiant_ik FROM NASCA.identifiant WHERE NUM_IDENTIFIANT = ?))";

    public static final String SELECT_VCS_MONTANT = "SELECT solde FROM NASCA.ENTITE_COMPTABLE WHERE ENTITE_COMPTABLE_IK IN "
            + "(SELECT ENTITE_COMPTABLE_FK FROM NASCA.VCS_ENTITE_COMPTABLE WHERE VCS_FK IN "
            + "(SELECT VCS_IK FROM NASCA.VCS WHERE destinataire_fk IN "
            + "(SELECT identite_ik FROM NASCA.IDENTITE WHERE identifiant_valide_fk IN "
            + "(SELECT identifiant_ik FROM NASCA.identifiant WHERE NUM_IDENTIFIANT = ?))))";

    public static final String SELECT_VCS_ID = "SELECT vcs_ik FROM NASCA.VCS WHERE destinataire_fk IN"
            + "(SELECT IDENTITE_IK FROM NASCA.identite WHERE numero = ?)";

    // ENTITE COMPTABLE PERIOD
    public static final String SELECT_ENTITE_COMPTA_PERIODE = "SELECT ENTITE_COMPTABLE_IK FROM NASCA.ENTITE_COMPTABLE  WHERE PERIODE_COTI_PP_FK IN "
            + "		(SELECT PERIODE_COTISATION_PP_IK FROM NASCA.PERIODE_COTISATION_PP WHERE annee = ? AND trimestre = ?)"
            + "		AND dossier_fk IN "
            + "		(SELECT dossier_ik FROM NASCA.dossier WHERE identite_fk IN"
            + " 	(SELECT IDENTITE_IK FROM NASCA.identite WHERE numero = ?))";

    public static final String INSERT_VCS_ENTITECOMPTA = "INSERT INTO NASCA.VCS_ENTITE_COMPTABLE (VCS_FK, ENTITE_COMPTABLE_FK) VALUES (?, ?)";

    // COUNT RECORD OF ENROLEMENT RECORD
    public static final String INSERT_DATE_ENROLEMENT_MAJO_PM = "INSERT INTO NASCAPARAM.DATE_ENROLEMENT_MAJO_PM " +
            "(ANNEE ,MOIS ,DT_ENROLEMENT    ,EXECUTE,SYS_TMSTP_CREA    ,SYS_USR_CREA_FK,SYS_MOD_CREA,SYS_TMSTP_MAJ     ,SYS_USR_MAJ_FK,SYS_MOD_MAJ) VALUES " +
            "(?     ,?    ,? ,'F'    ,CURRENT TIMESTAMP ,NULL           ,NULL        ,CURRENT TIMESTAMP ,null          ,null       ) ";

    public static final String INSERT_DATE_ENROLEMENT_MAJO_PP_TRIM = "INSERT INTO NASCAPARAM.DATE_ENROLEMENT_MAJO_PP_TRIM " +
            "(ANNEE ,TRIMESTRE ,DT_ENROLEMENT    ,EXECUTE,SYS_TMSTP_CREA    ,SYS_USR_CREA_FK,SYS_MOD_CREA,SYS_TMSTP_MAJ     ,SYS_USR_MAJ_FK,SYS_MOD_MAJ) VALUES " +
            "(?     ,?    ,? ,'F'    ,CURRENT TIMESTAMP ,NULL           ,NULL        ,CURRENT TIMESTAMP ,null          ,null       ) ";

    public static final String INSERT_DATE_PAIEMENT1 = "INSERT INTO NASCAPARAM.DATE_PAIEMENT (TYPE_PAIEMENT,DT_EXECUTION,EXECUTE,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ," +
            "SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES ('CP',?,'F',{ts '2016-06-07 10:21:42'},0,null,{ts '2016-06-07 10:21:42'},0,null)";

    public static final String INSERT_DATE_PAIEMENT2 = "INSERT INTO NASCAPARAM.DATE_PAIEMENT (TYPE_PAIEMENT,DT_EXECUTION,EXECUTE,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ," +
            "SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES ('CP',?,'F',{ts '2016-06-07 10:21:42'},0,null,{ts '2016-06-07 10:21:42'},0,null)";

    public static final String INSERT_DATE_ENROLEMENT_MAJO_PP_ANN = "INSERT INTO NASCAPARAM.DATE_ENROLEMENT_MAJO_PP_ANN " +
            "(ANNEE ,DT_ENROLEMENT    ,EXECUTE,SYS_TMSTP_CREA    ,SYS_USR_CREA_FK,SYS_MOD_CREA,SYS_TMSTP_MAJ     ,SYS_USR_MAJ_FK,SYS_MOD_MAJ) VALUES " +
            "(?     ,? ,'F'    ,CURRENT TIMESTAMP ,NULL           ,NULL        ,CURRENT TIMESTAMP ,null          ,null       ) ";

    public static final String DELETE_DATE_ENROLEMENT_MAJO_PM = "delete NASCAPARAM.DATE_ENROLEMENT_MAJO_PM";

    public static final String DELETE_DATE_ENROLEMENT_MAJO_PP_TRIM = "delete NASCAPARAM.DATE_ENROLEMENT_MAJO_PP_TRIM";

    public static final String DELETE_DATE_ENROLEMENT_MAJO_PP_ANN = "delete NASCAPARAM.DATE_ENROLEMENT_MAJO_PP_ANN";

    public static final String UPDATE_DATE_ENROLEMENT_MAJO_PP_TRIM = "update NASCAPARAM.DATE_ENROLEMENT_MAJO_PP_TRIM set EXECUTE = 'F' " +
            "WHERE ANNEE = ? AND TRIMESTRE = ?";

    public static final String UPDATE_DATE_ENROLEMENT_MAJO_PP_ANN = "update NASCAPARAM.DATE_ENROLEMENT_MAJO_PP_ANN set EXECUTE = 'F' " +
            "WHERE ANNEE = ?";

    public static final String UPDATE_EXECUTE_FALSE_ENROLEMENT_TRIMESTRIEL = "UPDATE NASCAPARAM.DATE_ENROLEMENT_TRIMESTRIEL set EXECUTE = 'F', DT_ENROLEMENT = ? WHERE annee = ? AND trimestre = ?";

    public static final String UPDATE_EXECUTE_TRUE_ENROLEMENT_TRIMESTRIEL = "UPDATE NASCAPARAM.DATE_ENROLEMENT_TRIMESTRIEL set EXECUTE = 'T' WHERE annee = ? AND trimestre = ?";

    public static final String UPDATE_ENROLEMENT_ANNUEL_EXECUTE = "UPDATE NASCAPARAM.DATE_ENROLEMENT_ANNUEL set EXECUTE = 'F'" + " WHERE annee= ?";

    public static final String UPDATE_ALL_ENROLEMENT_ANNUEL_EXECUTE = "UPDATE NASCAPARAM.DATE_ENROLEMENT_ANNUEL set EXECUTE = 'T'";

    public static final String UPDATE_BAREME_PM = "UPDATE NASCADM.PERIODE_VALIDITE SET DT_FINVAL = '2025-12-31' "
            + "WHERE PERIODE_VALIDITE_IK = (SELECT PERIODE_VALIDITE_IK FROM NASCADM.PERIODE_VALIDITE " +
            "WHERE DT_FINVAL IS NOT NULL ORDER BY DT_FINVAL DESC FETCH FIRST 1 ROWS ONLY)";

    public static final String INSERT_ENROLEMENT_ANNUEL = "INSERT INTO NASCAPARAM.DATE_ENROLEMENT_ANNUEL "
            + "(ANNEE,DT_ENROLEMENT,EXECUTE,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) "
            + "VALUES (?,?,'F',CURRENT TIMESTAMP,null,null,CURRENT TIMESTAMP,null,null)";

    public static final String SELECT_ENROLEMENTPP_NUMERO_FROM_NISS = "SELECT numero FROM NASCA.IDENTITE WHERE identifiant_valide_fk IN"
            + "(SELECT identifiant_ik FROM NASCA.identifiant WHERE NUM_IDENTIFIANT = ?)";

    public static final String SELECT_ENROLEMENTPP_NUMERO_FROM_NISS_UNIQUE = "SELECT numero FROM NASCA.IDENTITE WHERE identifiant_valide_fk IN"
            + "(SELECT identifiant_ik FROM NASCA.identifiant WHERE NUM_IDENTIFIANT= ? AND TYPE_IDENTIFIANT = 'NISS')";

    public static final String SELECT_NISS_FROM_NUMERO = "SELECT NUM_IDENTIFIANT FROM NASCA.IDENTIFIANT WHERE (TYPE_IDENTIFIANT = 'NISS'or TYPE_IDENTIFIANT = 'BIS') AND DT_FINVAL IS NULL AND IDENTITE_FK IN "
            + "(SELECT IDENTITE_IK FROM NASCA.identite WHERE numero = ?)";

    public static final String SELECT_BLOB_FROM_NUMERO_BATCHQUEUE = "SELECT message FROM NASCABATCH.BATCH_QUEUE WHERE ENTITE_ID IN "
            + "(SELECT IDENTITE_IK FROM NASCA.identite WHERE numero = ?) ";

    // to comment when moteur de calcul is activated R3
    public static final String UPDATE_EVENEMENT_ACTIF = "UPDATE NASCA.EVENEMENT set STATUT_EVENEMENT = 'ACT' WHERE dossier_fk IN"
            + " (SELECT dossier_ik FROM NASCA.dossier WHERE IDENTITE_FK IN "
            + "(SELECT identite_ik FROM NASCA.IDENTITE WHERE NUMERO like (?)))";

    // DELETE YEAR ENROLEMENT +1
    public static final String DELETE_ENROLEMENT_ANNUEL1 = "DELETE FROM NASCAPARAM.DATE_ENROLEMENT_ANNUEL WHERE annee = ?";

    // DELETE INASTI FICHIER
    public static final String DELETE_FICHIER_INASTI_CONTRIBUTIONS = "DELETE FROM NASCABATCH.FICHIER_INASTI WHERE nom like '000_019_Contributions_%'";

    public static final String DELETE_FICHIER_INASTI_TRANSFERT_IN_PM = "DELETE FROM NASCABATCH.FICHIER_INASTI WHERE nom like '000_019_AffiliationDecisionNewSif_%'";

    // INASTI FLUX/FLATFILE
    public static final String SELECT_INASTI_FLUX_RECORD_PK = "SELECT DISTINCT(INASTI_FLUX_FK) FROM NASCABATCH.INASTI_RECORD WHERE IDENTIFIANT = ?";

    public static final String DELETE_INASTI_FLUX_RECORD = "DELETE FROM NASCABATCH.INASTI_RECORD WHERE IDENTIFIANT = ?";

    public static final String DELETE_INASTI_FLUX = "DELETE FROM NASCABATCH.INASTI_FLUX WHERE inasti_flux_IK = ?";

    // SELECT DOSSIER NUMBER
    public static final String SELECT_DOSSIER_NBR = "SELECT NUMERO FROM NASCA.identite WHERE identite_ik IN"
            + " (SELECT IDENTITE_FK FROM NASCA.identifiant WHERE NUM_IDENTIFIANT IN(?))";

    public static final String SELECT_NISS_CJT_FROM_NISS1 = "SELECT NUM_IDENTIFIANT FROM NASCA.IDENTIFIANT WHERE IDENTITE_FK in " +
            "(SELECT IDENTITE_LIEE_FK FROM lien_PP WHERE IDENTITE_LIANTE_FK in (SELECT identite_fk FROM NASCA.identifiant WHERE NUM_IDENTIFIANT = ?)) " +
            "AND TYPE_IDENTIFIANT = 'NISS'";

    public static final String SELECT_NISS_CJT_FROM_NISS2 = "SELECT NUM_IDENTIFIANT FROM NASCA.IDENTIFIANT WHERE IDENTITE_FK in " +
            "(SELECT IDENTITE_LIANTE_FK FROM lien_PP WHERE IDENTITE_LIEE_FK in (SELECT identite_fk FROM NASCA.identifiant WHERE NUM_IDENTIFIANT = ?)) " +
            "AND TYPE_IDENTIFIANT = 'NISS'";

    // SELECT WORKFLOW ID FOR MAP
    public static final String SELECT_WORKFLOW_ID = "SELECT ATTRIBUTION_FK FROM NASCA.TACHE WHERE WORKFLOW_FK IN"
            + " (SELECT WORKFLOW_IK FROM NASCA.WORKFLOW  WHERE IDENTITE_FK IN"
            + "(SELECT B.IDENTITE_IK FROM NASCA.IDENTITE B WHERE numero like (?)))";

    // SELECT WORKFLOW ID FOR MAP
    public static final String SELECT_WORKFLOW_ID_NISS = "SELECT ATTRIBUTION_FK FROM NASCA.TACHE WHERE WORKFLOW_FK IN"
            + " (SELECT WORKFLOW_IK FROM NASCA.WORKFLOW  WHERE IDENTITE_FK IN"
            + "(SELECT B.IDENTITE_IK FROM NASCA.IDENTITE B WHERE IDENTIFIANT_VALIDE_FK IN"
            + "(SELECT IDENTIFIANT_IK FROM NASCA.IDENTIFIANT WHERE NUM_IDENTIFIANT = ?)))";

    public static final String SELECT_IDENTITE_IK = "SELECT IDENTITE_IK FROM NASCA.IDENTITE  WHERE numero like (?)";

    public static final String SELECT_IDENTITE_NISS = "SELECT IDENTITE_IK FROM NASCA.IDENTITE  WHERE IDENTIFIANT_VALIDE_FK IN"
            + "(SELECT IDENTIFIANT_IK FROM NASCA.IDENTIFIANT WHERE NUM_IDENTIFIANT = ? )";

    // Nettoyage TABLE NASCA après Affiliation
    public static final String UPDATE_REM = "UPDATE NASCA.RECLAMATION_ELEMENT_MANQUANT set WF_APPELANT_FK = null, WF_REDEMARRAGE_FK = null WHERE DEMANDE_FK IN"
            + "(SELECT DEMANDE_IK FROM NASCA.DEMANDE WHERE DOSSIER_FK IN"
            + "(SELECT DOSSIER_ik FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_DONNE_METIER = "DELETE FROM NASCA.DONNEE_METIER WHERE ELEMENT_MANQUANT_FK IN"
            + " (SELECT ELEMENT_MANQUANT_IK FROM NASCA.ELEMENT_MANQUANT WHERE reclamation_FK IN"
            + " (SELECT RECLAMATION_ELEMENT_MANQUANT_IK FROM NASCA.RECLAMATION_ELEMENT_MANQUANT WHERE DEMANDE_FK IN"
            + "(SELECT DEMANDE_IK FROM NASCA.DEMANDE WHERE DOSSIER_FK IN"
            + "(SELECT DOSSIER_ik FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)))))";

    public static final String DELETE_ELEMENT_MANQUANT = "DELETE FROM NASCA.ELEMENT_MANQUANT WHERE reclamation_FK IN"
            + "(SELECT RECLAMATION_ELEMENT_MANQUANT_IK FROM NASCA.RECLAMATION_ELEMENT_MANQUANT WHERE DEMANDE_FK IN"
            + "(SELECT DEMANDE_IK FROM NASCA.DEMANDE WHERE DOSSIER_FK IN"
            + "(SELECT DOSSIER_ik FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?))))";

    public static final String DELETE_REM_COURRIER = "DELETE FROM NASCA.COURRIER_ELEMENT_MANQUANT WHERE RECLAMATION_FK IN"
            + " (SELECT RECLAMATION_ELEMENT_MANQUANT_IK FROM NASCA.RECLAMATION_ELEMENT_MANQUANT WHERE DEMANDE_FK IN"
            + "(SELECT DEMANDE_IK FROM NASCA.DEMANDE WHERE DOSSIER_FK IN"
            + "(SELECT DOSSIER_ik FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?))))";

    public static final String DELETE_REM = "DELETE FROM NASCA.RECLAMATION_ELEMENT_MANQUANT WHERE DEMANDE_FK IN"
            + "(SELECT DEMANDE_IK FROM NASCA.DEMANDE WHERE DOSSIER_FK IN"
            + "(SELECT DOSSIER_ik FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_VARIABLE_TACHE = "DELETE FROM NASCA.VARIABLE_TACHE WHERE TACHE_FK IN"
            + "(SELECT TACHE_IK FROM NASCA.TACHE WHERE WORKFLOW_FK IN"
            + "(SELECT WORKFLOW_IK FROM NASCA.WORKFLOW  WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_VARIABLE_WORKFLOW = "DELETE FROM NASCA.VARIABLE_WORKFLOW WHERE WORKFLOW_FK IN"
            + "(SELECT WORKFLOW_IK FROM NASCA.WORKFLOW  WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_TEMP = "DELETE FROM NASCA.TEMP WHERE TACHE_FK IN"
            + "(SELECT TACHE_IK FROM NASCA.TACHE WHERE WORKFLOW_FK IN"
            + "(SELECT WORKFLOW_IK FROM NASCA.WORKFLOW  WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_TEMP_FICHIER = "DELETE FROM NASCA.TEMP_FICHIER WHERE FICHIER_BINAIRE_fk IN"
            + "(SELECT FICHIER_BINAIRE_fk FROM NASCA.SUPPORT_INFORMATION_FICHIER WHERE SUPPORT_INFORMATION_FK IN"
            + "(SELECT SUPPORT_INFORMATION_ik FROM NASCA.SUPPORT_INFORMATION WHERE DEMANDE_FK IN"
            + "(SELECT d.demANDe_ik FROM NASCA.DEMANDE d WHERE d.DOSSIER_FK IN"
            + "(SELECT o.DOSSIER_IK FROM NASCA.DOSSIER o WHERE o.IDENTITE_FK IN(?)))))";

    public static final String DELETE_FICHIER_BINAIRE = "DELETE FROM NASCA.FICHIER_BINAIRE WHERE FICHIER_BINAIRE_ik IN"
            + "(SELECT FICHIER_BINAIRE_fk FROM NASCA.SUPPORT_INFORMATION_FICHIER WHERE SUPPORT_INFORMATION_FK IN"
            + "(SELECT SUPPORT_INFORMATION_ik FROM NASCA.SUPPORT_INFORMATION WHERE DEMANDE_FK IN"
            + "(SELECT d.demANDe_ik FROM NASCA.DEMANDE d WHERE d.DOSSIER_FK IN"
            + "(SELECT o.DOSSIER_IK FROM NASCA.DOSSIER o WHERE o.IDENTITE_FK IN(?)))))";

    public static final String DELETE_SUPPORT_INFO_FICH = "DELETE FROM NASCA.SUPPORT_INFORMATION_FICHIER WHERE SUPPORT_INFORMATION_FK IN "
            + "(SELECT SUPPORT_INFORMATION_ik FROM NASCA.SUPPORT_INFORMATION WHERE DEMANDE_FK IN"
            + "(SELECT d.demANDe_ik FROM NASCA.DEMANDE d WHERE d.DOSSIER_FK IN "
            + "(SELECT o.DOSSIER_IK FROM NASCA.DOSSIER o WHERE o.IDENTITE_FK IN "
            + "(SELECT o.DOSSIER_IK FROM NASCA.DOSSIER o WHERE o.IDENTITE_FK IN(?)))))";

    public static final String DELETE_SUPPORT_INFO = "DELETE FROM NASCA.SUPPORT_INFORMATION WHERE DEMANDE_FK IN"
            + "(SELECT d.demANDe_ik FROM NASCA.DEMANDE d WHERE d.DOSSIER_FK IN"
            + "(SELECT o.DOSSIER_IK FROM NASCA.DOSSIER o WHERE o.IDENTITE_FK IN(?)))";

    public static final String DELETE_TACHES = "DELETE FROM NASCA.TACHE WHERE WORKFLOW_FK IN"
            + "(SELECT WORKFLOW_IK FROM NASCA.WORKFLOW  WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_WORKFLOW = "DELETE FROM NASCA.WORKFLOW WHERE IDENTITE_FK IN(?)";

    public static final String DELETE_TRACKING_TABLE = "DELETE FROM NASCA.TRACKING_TABLE WHERE IDENTITE_ID IN(?)";

    public static final String DELETE_TRACKING_FIELD = "DELETE FROM NASCA.TRACKING_FIELD WHERE TABLE_FK IN"
            + "(SELECT TRACKING_TABLE_IK FROM NASCA.TRACKING_TABLE WHERE IDENTITE_ID IN(?))";

    public static final String DELETE_DECISION_PLC = "DELETE FROM NASCA.DECISION_PLC da WHERE da.DECISION_IK IN"
            + " (SELECT i.decision_IK FROM NASCA.DECISION i WHERE i.DEMANDE_FK IN"
            + "(SELECT d.demANDe_ik FROM NASCA.DEMANDE d WHERE d.DOSSIER_FK IN"
            + "(SELECT o.DOSSIER_IK FROM NASCA.DOSSIER o WHERE o.IDENTITE_FK IN(?))))";

    public static final String DELETE_DECISION_AFFIPM = "DELETE FROM NASCA.DECISION_AFFILIATION_PM da WHERE da.DECISION_IK IN"
            + " (SELECT i.decision_IK FROM NASCA.DECISION i WHERE i.DEMANDE_FK IN"
            + "(SELECT d.demANDe_ik FROM NASCA.DEMANDE d WHERE d.DOSSIER_FK IN"
            + "(SELECT o.DOSSIER_IK FROM NASCA.DOSSIER o WHERE o.IDENTITE_FK IN(?))))";

    public static final String DELETE_DECISION_AFFIPP = "DELETE FROM NASCA.DECISION_AFFILIATION_PP da WHERE da.DECISION_IK IN"
            + "(SELECT i.decision_IK FROM NASCA.DECISION i WHERE i.DEMANDE_FK IN"
            + "(SELECT d.demANDe_ik FROM NASCA.DEMANDE d WHERE d.DOSSIER_FK IN"
            + "(SELECT o.DOSSIER_IK FROM NASCA.DOSSIER o WHERE o.IDENTITE_FK IN(?))))";

    public static final String DELETE_DECISION = "DELETE FROM NASCA.DECISION  WHERE DEMANDE_FK IN"
            + "(SELECT demANDe_ik FROM NASCA.DEMANDE WHERE DOSSIER_FK IN"
            + "(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_DEMANDE_PLC = "DELETE FROM NASCA.DEMANDE_PLC WHERE DEMANDE_IK IN "
            + "(SELECT DEMANDE_IK FROM NASCA.DEMANDE WHERE DOSSIER_FK IN(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE identite_FK IN (?)))";

    public static final String DELETE_DEMANDE_CHGT_PROFIL = "DELETE FROM NASCA.DEMANDE_CHGT_PROFIL WHERE DEMANDE_IK IN"
            + " (SELECT DEMANDE_IK FROM NASCA.DEMANDE WHERE DOSSIER_FK IN"
            + " (SELECT  DEMANDE_IK FROM NASCA.DOSSIER WHERE identite_FK IN(?)))";

    public static final String DELETE_DEMANDE_REVENU = "DELETE FROM NASCA.DEMANDE_REVENU WHERE DEMANDE_IK IN"
            + "(SELECT DEMANDE_IK FROM NASCA.DEMANDE WHERE DOSSIER_FK IN"
            + " (SELECT  DEMANDE_IK FROM NASCA.DOSSIER WHERE identite_FK  IN(?)))";

    public static final String DELETE_DEMANDE_APPORTEUR = "DELETE FROM NASCA.DEMANDE WHERE APPORTEUR_AFFAIRE_FK IN(?)";

    public static final String DELETE_DEMANDE_CODEBITEUR = "DELETE FROM NASCA.DEMANDE WHERE DOSSIER_FK IN"
            + " (SELECT dossier_ik FROM NASCA.dossier WHERE CODEBITEUR_FK IN(?)) ";

    public static final String DELETE_DEMANDE = "DELETE FROM NASCA.DEMANDE WHERE DOSSIER_FK IN"
            + " (SELECT  DOSSIER_IK FROM NASCA.DOSSIER WHERE identite_FK IN(?)) ";

    public static final String DELETE_INASTI_MESSAGE_HISTORY = "DELETE FROM NASCAHISTO.WS_INASTI_EXCHANGE WHERE IDENTIFIER IN(?)";

    public static final String DELETE_PRINT_QUEUE = "DELETE FROM NASCA.PRINT_QUEUE WHERE IDENTITE_FK IN(?)";

    public static final String DELETE_BON_MUTUELLE = "DELETE FROM NASCA.BON_MUTUELLE WHERE IDENTITE_FK IN(?)";

    public static final String DELETE_BON_RETOUR_MUTUELLE = "DELETE FROM NASCA.BON_RETOUR WHERE BON_MUTUELLE_FK IN(SELECT BON_MUTUELLE_IK FROM NASCA.BON_MUTUELLE WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_PARTNER = "DELETE FROM NASCAOE.PARTNER WHERE REF IN "
            + "(SELECT NUMERO FROM NASCA.IDENTITE WHERE IDENTIFIANT_VALIDE_FK IN"
            + "(SELECT IDENTIFIANT_IK FROM NASCA.IDENTIFIANT WHERE NUM_IDENTIFIANT IN(?)))";

    public static final String DELETE_CARRIERE_MVMT = "DELETE FROM NASCA.CARRIERE_MOUVEMENT WHERE PP_FK IN(?)";

    public static final String DELETE_ACTIVITE_PROF = "DELETE FROM NASCA.ACTIVITE_PROFESSION WHERE ACTIVITE_FK IN"
            + " (SELECT ACTIVITE_IK FROM NASCA.ACTIVITE WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_ACTIVITE_PROF_LIEE = "DELETE FROM NASCA.ACTIVITE_PROFESSION WHERE ACTIVITE_FK IN"
            + " (SELECT ACTIVITE_IK FROM NASCA.ACTIVITE WHERE IDENTITE_LIEE_FK IN(?))";

    public static final String DELETE_ACTIVITE_ASSOCIE = "DELETE FROM NASCA.ACTIVITE_ASSOCIE WHERE ACTIVITE_IK IN"
            + " (SELECT ACTIVITE_IK FROM NASCA.ACTIVITE WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_ACTIVITE_MANDATAIRE = "DELETE FROM NASCA.ACTIVITE_MANDATAIRE WHERE ACTIVITE_IK IN"
            + "(SELECT ACTIVITE_IK FROM NASCA.ACTIVITE WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_ACTIVITE_MANDATAIRE_LIEE = "DELETE FROM NASCA.ACTIVITE_MANDATAIRE WHERE ACTIVITE_IK IN"
            + "(SELECT ACTIVITE_IK FROM NASCA.ACTIVITE WHERE IDENTITE_LIEE_FK IN(?))";

    public static final String DELETE_ACTIVITE = "DELETE FROM NASCA.ACTIVITE WHERE IDENTITE_FK IN(?)";

    public static final String DELETE_ACTIVITE_LIEE = "DELETE FROM NASCA.ACTIVITE WHERE IDENTITE_LIEE_FK IN(?)";

    public static final String DELETE_OPERATION_COMPTA = "DELETE FROM NASCA.OPERATION_COMPTABLE WHERE ENTITE_COMPTABLE_FK IN "
            + "(SELECT ENTITE_COMPTABLE_IK FROM NASCA.ENTITE_COMPTABLE WHERE DOSSIER_FK IN"
            + "(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_PROCED_RECOUVR = "DELETE FROM NASCA.PROCEDURE_RECOUVREMENT WHERE PROCEDURE_IK IN"
            + " (SELECT PROCEDURE_IK FROM NASCA.PROCEDURE WHERE  DEBITEUR_FK IN(?))";

    public static final String DELETE_COMPTA_PROCED_LIEE = "DELETE FROM NASCA.OPERATION_COMPTABLE WHERE ENTITE_COMPTABLE_FK IN "
            + "(SELECT ENTITE_COMPTABLE_IK FROM NASCA.ENTITE_COMPTABLE WHERE DOSSIER_FK IN"
            + "(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_ENTITE_COMPTABLE_PROCEDURE1 = "DELETE FROM NASCA.ENTITE_COMPTABLE_PROCEDURE WHERE PROCEDURE_FK IN"
            + "(SELECT PROCEDURE_IK FROM NASCA.PROCEDURE WHERE DEBITEUR_FK IN(?))";

    public static final String DELETE_ENTITE_COMPTABLE_PROCEDURE2 = "DELETE FROM NASCA.ENTITE_COMPTABLE_PROCEDURE WHERE PROCEDURE_FK IN"
            + "(SELECT PROCEDURE_IK FROM NASCA.PROCEDURE WHERE DEBITEUR_2_FK IN(?))";

    public static final String DELETE_ENTITE_COMPTABLE_PROCEDURE3 = "DELETE FROM NASCA.ENTITE_COMPTABLE_PROCEDURE WHERE PROCEDURE_FK IN"
            + "(SELECT PROCEDURE_IK FROM NASCA.PROCEDURE WHERE COTISANT_FK IN(?))";

    public static final String DELETE_ENTITE_COMPTABLE_CTX_BLOC_DECISION = "DELETE FROM NASCA.ENTITE_COMPTABLE_CTX_BLOC_DECISION WHERE ENTITE_COMPTABLE_FK " +
            "IN(SELECT ENTITE_COMPTABLE_IK FROM NASCA.ENTITE_COMPTABLE WHERE DOSSIER_FK IN (SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_CTX_EC_PRINCIPAL_BLOC = "DELETE FROM NASCA.CTX_EC_PRINCIPAL_BLOC WHERE ENTITE_COMPTABLE_FK " +
            "IN(SELECT ENTITE_COMPTABLE_IK FROM NASCA.ENTITE_COMPTABLE WHERE DOSSIER_FK IN (SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_COMMENTAIRE_PROCEDURE1 = "DELETE FROM NASCA.COMMENTAIRE_PROCEDURE WHERE PROCEDURE_FK IN(SELECT PROCEDURE_IK FROM NASCA.PROCEDURE WHERE DEBITEUR_FK IN(?))";

    public static final String DELETE_STATUT_PROCEDURE1 = "DELETE FROM NASCA.STATUT_PROCEDURE WHERE PROCEDURE_FK IN(SELECT PROCEDURE_IK FROM NASCA.PROCEDURE WHERE DEBITEUR_FK IN(?))";

    public static final String DELETE_PROCEDURE1 = "DELETE FROM NASCA.PROCEDURE WHERE DEBITEUR_FK IN(?)";

    public static final String DELETE_COMMENTAIRE_PROCEDURE2 = "DELETE FROM NASCA.COMMENTAIRE_PROCEDURE WHERE PROCEDURE_FK IN(SELECT PROCEDURE_IK FROM NASCA.PROCEDURE WHERE DEBITEUR_2_FK IN(?))";

    public static final String DELETE_STATUT_PROCEDURE2 = "DELETE FROM NASCA.STATUT_PROCEDURE WHERE PROCEDURE_FK IN(SELECT PROCEDURE_IK FROM NASCA.PROCEDURE WHERE DEBITEUR_2_FK IN(?))";

    public static final String DELETE_PROCEDURE2 = "DELETE FROM NASCA.PROCEDURE WHERE DEBITEUR_2_FK IN(?)";

    public static final String DELETE_COMMENTAIRE_PROCEDURE3 = "DELETE FROM NASCA.COMMENTAIRE_PROCEDURE WHERE PROCEDURE_FK IN(SELECT PROCEDURE_IK FROM NASCA.PROCEDURE WHERE COTISANT_FK IN(?))";

    public static final String DELETE_STATUT_PROCEDURE3 = "DELETE FROM NASCA.STATUT_PROCEDURE WHERE PROCEDURE_FK IN(SELECT PROCEDURE_IK FROM NASCA.PROCEDURE WHERE COTISANT_FK IN(?))";

    public static final String DELETE_PROCEDURE3 = "DELETE FROM NASCA.PROCEDURE WHERE COTISANT_FK IN(?)";

    public static final String SELECT_VCS_ID_ENTIT_COMPTABLE = "SELECT VCS_FK FROM NASCA.VCS_ENTITE_COMPTABLE WHERE ENTITE_COMPTABLE_FK IN"
            + "(SELECT ENTITE_COMPTABLE_IK FROM NASCA.ENTITE_COMPTABLE WHERE dossier_fk IN"
            + " (SELECT dossier_ik FROM NASCA.dossier WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_VCS_ENTITE_COMPTABLE = "DELETE FROM NASCA.VCS_ENTITE_COMPTABLE WHERE ENTITE_COMPTABLE_FK IN "
            + "(SELECT ENTITE_COMPTABLE_IK FROM NASCA.ENTITE_COMPTABLE WHERE DOSSIER_FK IN "
            + "(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK  IN(?)))";

    public static final String DELETE_VCS = "DELETE FROM NASCA.VCS WHERE vcs_ik IN(?)";

    public static final String DELETE_RECOUVREMENT = "DELETE FROM NASCA.RECOUVREMENT WHERE CREANCE_FK IN "
            + "(SELECT ENTITE_COMPTABLE_IK FROM NASCA.ENTITE_COMPTABLE WHERE DOSSIER_FK IN "
            + "(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK  IN(?)))";

    public static final String DELETE_PROPOSITION_AFFECTATION = "DELETE FROM NASCA.PROPOSITION_AFFECTATION WHERE ENTITE_COMPTABLE_FK IN"
            + "(SELECT ENTITE_COMPTABLE_IK FROM NASCA.ENTITE_COMPTABLE WHERE DOSSIER_FK IN "
            + "(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_PERIODE_DETAILS = "DELETE FROM NASCA.PERIODE_DETAILS_LM WHERE COTISATION_FK IN "
            + "(SELECT ENTITE_COMPTABLE_IK FROM NASCA.ENTITE_COMPTABLE WHERE DOSSIER_FK IN "
            + "(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_COMPTA_RECOUVREMENT = "DELETE FROM NASCA.ENTITE_COMPTABLE_SUSPENSION_RECOUVREMENT WHERE ENTITE_COMPTABLE_FK IN"
            + "(SELECT ENTITE_COMPTABLE_IK FROM NASCA.ENTITE_COMPTABLE WHERE DOSSIER_FK IN"
            + "(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_COMPTA_APPUREMENT = "DELETE FROM NASCA.ENTITE_COMPTABLE_PLAN_APUREMENT WHERE ENTITE_COMPTABLE_FK IN"
            + " (SELECT ENTITE_COMPTABLE_IK FROM NASCA.ENTITE_COMPTABLE WHERE DOSSIER_FK IN"
            + " (SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_COTI_DISPENSE = "DELETE FROM NASCA.COTISATION_DISPENSE_LRS WHERE ENTITE_COMPTABLE_FK IN "
            + "(SELECT ENTITE_COMPTABLE_IK FROM NASCA.ENTITE_COMPTABLE WHERE DOSSIER_FK IN"
            + "(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_ENTITE_COMPTABLE = "DELETE FROM NASCA.ENTITE_COMPTABLE WHERE DOSSIER_FK IN"
            + " (SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_PERIODE_CORIPP = "DELETE FROM NASCA.PERIODE_COTISATION_PP WHERE DOSSIER_FK IN "
            + "(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_SOLIDARITE_VERSION = "DELETE FROM NASCA.SOLIDARITE_VERSION WHERE SOLIDARITE_ik IN"
            + " (SELECT SOLIDARITE_ik FROM NASCA.SOLIDARITE WHERE DOSSIER_FK IN"
            + " (SELECT dossier_ik FROM NASCA.dossier WHERE CODEBITEUR_FK IN(?)))";

    public static final String DELETE_SOLIDARITE_CODEBITEUR = "DELETE FROM NASCA.SOLIDARITE WHERE dossier_FK IN "
            + "(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE CODEBITEUR_FK IN(?))";

    public static final String DELETE_SOLIDARITE = "DELETE FROM NASCA.SOLIDARITE WHERE dossier_FK IN "
            + "(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_EXO_PM = "DELETE  FROM NASCA.exoneration_pm WHERE dossier_FK IN "
            + "(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_LIEN_DOSSIER_ADRESSE = "DELETE  FROM NASCA.LIEN_DOSSIER_ADRESSE WHERE dossier_FK IN "
            + "(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_EVENEMENT = "DELETE FROM NASCA.EVENEMENT WHERE DOSSIER_FK IN"
            + " (SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_EVENEMENT_ID = "DELETE FROM NASCA.EVENEMENT_PP WHERE identite_FK IN(?)";

    public static final String DELETE_REGIME = "DELETE FROM NASCA.REGIME WHERE DOSSIER_FK IN"
            + " (SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_PLC_CONTRAT = "DELETE FROM NASCA.PLC_CONTRAT WHERE DOSSIER_FK IN"
            + " (SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_PLC_EVENEMENT = "DELETE FROM NASCA.PLC_EVENEMENT WHERE PLC_CONTRAT_FK IN ("
            + " SELECT CONTRAT_IK FROM NASCA.PLC_CONTRAT WHERE DOSSIER_FK IN (SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_PLC_MONTANT_MAX = "DELETE FROM NASCA.PLC_MONTANT_MAX WHERE DOSSIER_FK IN"
            + " (SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_PLC_PERIODE = "DELETE FROM NASCA.PLC_PERIODE WHERE CONTRAT_FK IN ("
            + " SELECT CONTRAT_IK FROM NASCA.PLC_CONTRAT WHERE DOSSIER_FK IN (SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)))";

    public static final String DELETE_PLC_PROFIL = "DELETE FROM NASCA.PLC_PROFIL WHERE PLC_PROFIL_IK IN ("
            + " SELECT PROFIL_FK FROM NASCA.PLC_PERIODE WHERE CONTRAT_FK IN (SELECT CONTRAT_IK FROM NASCA.PLC_CONTRAT WHERE DOSSIER_FK IN (SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?))))";

    public static final String DELETE_PLC_PRIME = "DELETE FROM NASCA.PLC_PRIME WHERE PERIODE_FK IN ("
            + " SELECT PLC_PERIODE_IK FROM NASCA.PLC_PERIODE WHERE CONTRAT_FK IN (SELECT CONTRAT_IK FROM NASCA.PLC_CONTRAT WHERE DOSSIER_FK IN (SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?))))";

    public static final String DELETE_PLC_REVENU = "DELETE FROM NASCA.PLC_REVENU WHERE PLC_REVENU_IK IN ("
            + " SELECT REVENU_FK FROM NASCA.PLC_PRIME WHERE PERIODE_FK IN (SELECT PLC_PERIODE_IK FROM NASCA.PLC_PERIODE WHERE CONTRAT_FK IN( SELECT CONTRAT_IK FROM NASCA.PLC_CONTRAT WHERE DOSSIER_FK IN (SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)))))";

    public static final String DELETE_REVENU = "DELETE FROM NASCA.REVENU WHERE PP_FK IN(?)";

    public static final String DELETE_PP = "DELETE FROM NASCA.PERSONNE_PHYSIQUE WHERE IDENTITE_IK IN(?)";

    public static final String DELETE_BILAN_SOCIETE = "DELETE FROM NASCA.BILAN_SOCIETE WHERE identite_FK IN(SELECT identite_IK FROM NASCA.PERSONNE_MORALE WHERE identite_FK IN(?))";

    public static final String DELETE_FORME_JURIDIQUE = "DELETE FROM NASCA.FORME_JURIDIQUE WHERE identite_FK IN(SELECT identite_IK FROM NASCA.PERSONNE_MORALE WHERE identite_FK IN(?))";

    public static final String DELETE_SITUATION_JURIDIQUE = "DELETE FROM  NASCA.SITUATION_JURIDIQUE WHERE identite_FK IN(SELECT identite_IK FROM NASCA.PERSONNE_MORALE WHERE identite_FK IN(?))";

    public static final String DELETE_PM = "DELETE FROM NASCA.PERSONNE_MORALE WHERE IDENTITE_IK IN(?)";

    public static final String DELETE_LIEN_PP = "DELETE FROM NASCA.LIEN_PP WHERE IDENTITE_LIANTE_FK IN(?)";

    public static final String DELETE_LIEN_PP_LIE = "DELETE FROM NASCA.LIEN_PP WHERE IDENTITE_LIEE_FK IN (?)";

    public static final String DELETE_PHONEME = "DELETE FROM NASCA.PHONEME WHERE IDENTITE_FK IN(?)";

    public static final String DELETE_PERIODE_AFFILIATION = "DELETE FROM NASCA.PERIODE_AFFILIATION_PP WHERE DOSSIER_FK IN(SELECT dossier_ik FROM NASCA.DOSSIER WHERE IDENTITE_FK = (?))";

    public static final String DELETE_CIN_COURRIER = "DELETE FROM NASCA.CIN_COURRIER_ENTRANT WHERE identite_FK IN(?)";

    public static final String DELETE_COMPTE = "DELETE FROM NASCA.COMPTE WHERE identite_FK IN(?)";

    public static final String DELETE_MISE_VEILLEUSE = "DELETE FROM NASCA.MISE_EN_VEILLEUSE WHERE DEMANDE_FK IN"
            + " (SELECT DEMANDE_IK FROM NASCA.DEMANDE_MISE_EN_VEILLEUSE WHERE CONTACT_FK IN"
            + "(SELECT CONTACT_IK FROM NASCA.CONTACT WHERE IDENTITE_LIANTE_FK IN(?)))";

    public static final String DELETE_DEMANDE_MISE_VEILLEUSE = "DELETE FROM NASCA.DEMANDE_MISE_EN_VEILLEUSE WHERE CONTACT_FK "
            + "IN(SELECT CONTACT_IK FROM NASCA.CONTACT WHERE IDENTITE_LIANTE_FK IN(?))";

    public static final String DELETE_CONTACT = "DELETE FROM NASCA.CONTACT WHERE IDENTITE_LIANTE_FK IN(?)";

    public static final String DELETE_COORDONNEE = "DELETE FROM NASCA.COORDONNEE WHERE identite_FK IN(?)";

    public static final String DELETE_DATE_IMPORTANTE = "DELETE FROM NASCA.DATE_IMPORTANTE_PM WHERE identite_FK IN(?)";

    public static final String DELETE_DEMANDE_CESSATION = "DELETE FROM NASCA.DEMANDE_CESSATION WHERE TIERS_FK IN(?)";

    public static final String DELETE_DEMANDE_LM = "DELETE FROM NASCA.DEMANDE_LM WHERE TIERS_FK IN(?)";

    public static final String DELETE_GED_DEMANDE = "DELETE FROM NASCA.GED_DEMANDE WHERE identite_FK IN(?)";

    public static final String DELETE_ALL_GED_DEMANDE = "DELETE FROM NASCA.GED_DEMANDE";

    public static final String DELETE_RET_DEMANDE_ATTE = "DELETE FROM NASCA.RET_DEMANDE_ATTENTE WHERE identite_FK IN(?)";

    public static final String DELETE_RET_DEMANDE_PAPIER = "DELETE FROM NASCA.RET_DOSSIER_PAPIER WHERE identite_FK IN(?)";

    public static final String DELETE_RET_HISTORIQUE_RETRAIT = "DELETE FROM NASCA.RET_HISTORIQUE_RETRAIT WHERE identite_FK IN(?)";

    public static final String DELETE_RET_LISTE_IMPRESSION = "DELETE FROM NASCA.RET_LISTE_IMPRESSION WHERE identite_FK IN(?)";

    public static final String DELETE_RET_LISTE_OCCUR = "DELETE FROM NASCA.RET_LISTE_OCCUR WHERE identite_FK IN(?)";

    public static final String DELETE_SUSPENSION_RECOUVREMENT = "DELETE FROM NASCA.SUSPENSION_RECOUVREMENT WHERE identite_FK IN(?)";

    public static final String DELETE_TRAITEMENT = "DELETE FROM NASCA.TRAITEMENT WHERE IDENTITE_FK IN(?)";

    public static final String DELETE_AFFI_PM = "DELETE FROM NASCA.PERIODE_AFFILIATION_PM WHERE dossier_FK IN(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_AFFI_PP = "DELETE FROM NASCA.PERIODE_AFFILIATION_PP WHERE dossier_FK IN(SELECT DOSSIER_IK FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?))";

    public static final String DELETE_BLOCAGE = "DELETE FROM NASCA.BLOCAGE  WHERE dossier_fk IN"
            + "(SELECT dossier_ik FROM NASCA.DOSSIER WHERE identite_fk IN(?))";

    public static final String DELETE_DOSSIER = "DELETE FROM NASCA.DOSSIER WHERE IDENTITE_FK IN(?)";

    public static final String DELETE_DOSSIER_CODEBITEUR = "DELETE FROM NASCA.DOSSIER WHERE CODEBITEUR_FK IN(?)";

    public static final String DELETE_CONSULT_DOSSIER = "DELETE FROM NASCA.CONSULTATION_DOSSIER WHERE IDENTITE_FK IN(?)";

    public static final String DELETE_CARRIERE_COMMUNIQUEE = "DELETE FROM NASCA.CARRIERE_COMMUNIQUEE WHERE PP_FK IN(?)";

    public static final String UPDATE_IDENTIFIANT = "UPDATE NASCA.IDENTITE set IDENTIFIANT_VALIDE_FK = null ,ADRESSE_LEGALE_FK = null WHERE IDENTIFIANT_VALIDE_FK IN"
            + " (SELECT IDENTIFIANT_IK FROM NASCA.IDENTIFIANT WHERE identite_FK IN(?))";

    public static final String DELETE_ADRESSE = "DELETE FROM NASCA.ADRESSE WHERE IDENTITE_FK IN(?)";

    public static final String DELETE_CTX_DEBITEUR = "DELETE FROM NASCA.CTX_DEBITEUR WHERE DEBITEUR_FK IN(?)";

    public static final String DELETE_IDENTIFIANT = "DELETE FROM NASCA.IDENTIFIANT WHERE IDENTITE_FK IN(?)";

    public static final String DELETE_IDENTITE = "DELETE FROM NASCA.IDENTITE WHERE IDENTITE_IK IN (?)";

    public static final String DELETE_ATTRIBUTION_CANDIDATS_NONACTIVE = "DELETE FROM NASCA.ATTRIBUTION_CANDIDATS WHERE ATTRIBUTION_FK IN(SELECT ATTRIBUTION_IK FROM NASCA.ATTRIBUTION WHERE HIS_VERSION_ACTIVE_FK = ?)";

    public static final String DELETE_ATTRIBUTION_CANDIDATS = "DELETE FROM NASCA.ATTRIBUTION_CANDIDATS WHERE ATTRIBUTION_FK = ?";

    public static final String DELETE_ATTRIBUTION_NONACTIVE = "DELETE FROM NASCA.ATTRIBUTION WHERE HIS_STATUT_RECORD<>'A' AND HIS_VERSION_ACTIVE_FK = ?";

    public static final String DELETE_ATTRIBUTION = "DELETE FROM NASCA.ATTRIBUTION WHERE ATTRIBUTION_IK = ?";

    public static final String UPDATE_OPENERP_SYNC_TO_SENT_BY_NISS = "UPDATE NASCAOE.PARTNER  set SYNCHRO_STATUS = 'SENT' WHERE REF = ?";

    public static final String DELETE_INASTI_HISTO = "TRUNCATE TABLE NASCAHISTO.WS_INASTI_EXCHANGE DROP STORAGE IMMEDIATE";

    public static final String DELETE_EVENEMENT_DECISION = "DELETE FROM NASCA.EVENEMENT_DECISION " +
            "WHERE EVENEMENT_FK IN (" +
            "SELECT EVENEMENT_IK FROM NASCA.EVENEMENT " +
            "INNER JOIN NASCA.DOSSIER on NASCA.EVENEMENT.DOSSIER_FK = NASCA.DOSSIER.DOSSIER_IK " +
            "WHERE NASCA.DOSSIER.IDENTITE_FK = ?" +
            ")";

    public static final String DELETE_EVENEMENT_DEMANDE = "DELETE FROM NASCA.EVENEMENT_DEMANDE " +
            "WHERE EVENEMENT_FK IN (" +
            "SELECT EVENEMENT_IK FROM NASCA.EVENEMENT " +
            "INNER JOIN NASCA.DOSSIER ON NASCA.EVENEMENT.DOSSIER_FK = NASCA.DOSSIER.DOSSIER_IK " +
            "WHERE NASCA.DOSSIER.IDENTITE_FK = ?" +
            ")";

    public static final String DELETE_DECISION_EXONERATION = "DELETE FROM NASCA.DECISION_EXONERATION_PM " +
            "WHERE EXONERATION_PM_FK IN (" +
            "SELECT EXONERATION_PM_IK FROM NASCA.EXONERATION_PM " +
            "INNER JOIN NASCA.DOSSIER ON NASCA.EXONERATION_PM.DOSSIER_FK = NASCA.DOSSIER.DOSSIER_IK " +
            "WHERE NASCA.DOSSIER.IDENTITE_FK = ?" +
            ")";

    public static final String DELETE_DEMANDE_EXONERATION = "DELETE FROM NASCA.DECISION_EXONERATION_PM " +
            "WHERE EXONERATION_PM_FK IN (" +
            "SELECT EXONERATION_PM_IK FROM NASCA.EXONERATION_PM " +
            "INNER JOIN NASCA.DOSSIER ON NASCA.EXONERATION_PM.DOSSIER_FK = NASCA.DOSSIER.DOSSIER_IK " +
            "WHERE NASCA.DOSSIER.IDENTITE_FK = ?" +
            ")";

    public static final String DELETE_ENTITE_SUSPENSION = "DELETE FROM NASCA.ENTITE_COMPTABLE_SUSPENSION_RECOUVREMENT " +
            "WHERE NASCA.ENTITE_COMPTABLE_SUSPENSION_RECOUVREMENT.ENTITE_COMPTABLE_FK IN (" +
            "SELECT NASCA.ENTITE_COMPTABLE.ENTITE_COMPTABLE_IK " +
            "FROM NASCA.ENTITE_COMPTABLE " +
            "INNER JOIN NASCA.DOSSIER ON NASCA.ENTITE_COMPTABLE.DOSSIER_FK = NASCA.DOSSIER.DOSSIER_IK " +
            "WHERE NASCA.DOSSIER.IDENTITE_FK = ?" +
            ")";

    public static final String DELETE_SUSPENSION_EXO = "DELETE FROM NASCA.SUSPENSION_RECOUVREMENT " +
            "WHERE NASCA.SUSPENSION_RECOUVREMENT.DEMANDE_FK IN (" +
            "SELECT NASCA.DEMANDE_EXONERATION_PM.DEMANDE_IK FROM NASCA.DEMANDE_EXONERATION_PM " +
            "INNER JOIN NASCA.DEMANDE on NASCA.DEMANDE_EXONERATION_PM.DEMANDE_IK = NASCA.DEMANDE.DEMANDE_IK " +
            "INNER JOIN NASCA.DOSSIER on NASCA.DEMANDE.DOSSIER_FK = NASCA.DOSSIER.DOSSIER_IK " +
            "WHERE NASCA.DOSSIER.IDENTITE_FK = ?" +
            ")";

    public static final String COUNT_ENTITE_COMPTABLE_COTISATIONS_NON_SOLDEES = "SELECT " +
            "count(*) " +
            "FROM NASCA.ENTITE_COMPTABLE " +
            "WHERE NATURE = 'COT_PP' " +
            "AND SOLDE > 0 " +
            "AND DOSSIER_FK IN " +
            "( " +
            "   SELECT " +
            "   DOSSIER_IK " +
            "   FROM NASCA.dossier " +
            "   WHERE IDENTITE_FK IN " +
            "   ( " +
            "      SELECT " +
            "      identite_fk " +
            "      FROM NASCA.IDENTIFIANT " +
            "      WHERE NUM_IDENTIFIANT = ? " +
            "   ) " +
            ") ";

    public static final String COUNT_BAREME_MONTANT_PP = "SELECT count(*) FROM NASCA.montant WHERE DT_DEBVAL = ?";

    public static final String COUNT_RAPPORT_INDEXATION_PP = "SELECT count(*) FROM NASCA.RAPPORT_INDEXATION WHERE ANNEE_NUMERATEUR = ?";

    public static final String UPDATE_BAREME_MONTANT_PP = "update NASCA.MONTANT set DT_FINVAL = ? WHERE DT_FINVAL is null";

    public static final String UPDATE_BAREME_TAUX_PP = "update NASCA.TAUX set DT_FINVAL = ? WHERE DT_FINVAL is null AND TYPE_FK IN (4,5,24)";

    public static final String INSERT_BAREME1_MONTANT_PP = "INSERT INTO NASCA.MONTANT (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (17,?,null,null,18017.00,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant')";
    public static final String INSERT_BAREME2_MONTANT_PP = "INSERT INTO NASCA.MONTANT (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (25,?,null,null,2878.84,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant')";
    public static final String INSERT_BAREME3_MONTANT_PP = "INSERT INTO NASCA.MONTANT (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (9,?,null,null,1439.42,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant')";
    public static final String INSERT_BAREME4_MONTANT_PP = "INSERT INTO NASCA.MONTANT (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (10,?,null,null,6815.52,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant')";
    public static final String INSERT_BAREME5_MONTANT_PP = "INSERT INTO NASCA.MONTANT (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (16,?,null,null,9357.00,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant')";
    public static final String INSERT_BAREME6_MONTANT_PP = "INSERT INTO NASCA.MONTANT (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (8,?,null,null,56182.45,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant')";
    public static final String INSERT_BAREME7_MONTANT_PP = "INSERT INTO NASCA.MONTANT (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (7,?,null,null,82795.16,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant')";
    public static final String INSERT_BAREME8_MONTANT_PP = "INSERT INTO NASCA.MONTANT (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (18,?,null,null,21916.00,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant')";
    public static final String INSERT_BAREME9_MONTANT_PP = "INSERT INTO NASCA.MONTANT (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (15,?,null,null,6238.00,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant')";
    public static final String INSERT_BAREME10_MONTANT_PP = "INSERT INTO NASCA.MONTANT (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (14,?,null,null,26021.32,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant')";
    public static final String INSERT_BAREME11_MONTANT_PP = "INSERT INTO NASCA.MONTANT (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (19,?,null,null,14523.00,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant')";
    public static final String INSERT_BAREME12_MONTANT_PP = "INSERT INTO NASCA.MONTANT (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (1,?,null,null,13010.66,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant')";
    public static final String INSERT_BAREME13_MONTANT_PP = "INSERT INTO NASCA.MONTANT (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (24,?,null,null,5715.58,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant')";
    public static final String INSERT_BAREME14_MONTANT_PP = "INSERT INTO NASCA.MONTANT (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (20,?,null,null,18154.00,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Montant')";

    public static final String INSERT_BAREME1_TAUX_PP = "INSERT INTO NASCA.TAUX (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,NUMERATEUR,DENOMINATEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (5,?,null,null,21.5000,null,null,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Taux',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Taux')";
    public static final String INSERT_BAREME2_TAUX_PP = "INSERT INTO NASCA.TAUX (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,NUMERATEUR,DENOMINATEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (24,?,null,null,4.0500,null,null,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Taux',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Taux')";
    public static final String INSERT_BAREME3_TAUX_PP = "INSERT INTO NASCA.TAUX (TYPE_FK,DT_DEBVAL,DT_FINVAL,PERIODICITE,VALEUR,NUMERATEUR,DENOMINATEUR,DT_CREATION,USER_CREATION_FK,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (4,?,null,null,21.5000,null,null,{d '2016-10-22'},0,{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Taux',{ts '2016-10-22 18:30:32'},0,'REPRISE_ETL - T_BAR_Load_Taux')";

    public static final String INSERT_BAREME1_INDEXATION = "INSERT INTO NASCA.RAPPORT_INDEXATION (ANNEE_NUMERATEUR,NUMERATEUR,ANNEE_DENOMINATEUR,DENOMINATEUR,DOMAINE,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (?,506.6000,?,428.9000,'S2',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation')";
    public static final String INSERT_BAREME2_INDEXATION = "INSERT INTO NASCA.RAPPORT_INDEXATION (ANNEE_NUMERATEUR,NUMERATEUR,ANNEE_DENOMINATEUR,DENOMINATEUR,DOMAINE,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (?,506.6000,?,448.1900,'S2',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation')";
    public static final String INSERT_BAREME3_INDEXATION = "INSERT INTO NASCA.RAPPORT_INDEXATION (ANNEE_NUMERATEUR,NUMERATEUR,ANNEE_DENOMINATEUR,DENOMINATEUR,DOMAINE,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (?,506.6000,?,447.9400,'S2',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation')";
    public static final String INSERT_BAREME4_INDEXATION = "INSERT INTO NASCA.RAPPORT_INDEXATION (ANNEE_NUMERATEUR,NUMERATEUR,ANNEE_DENOMINATEUR,DENOMINATEUR,DOMAINE,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (?,506.6000,?,457.7300,'S2',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation')";
    public static final String INSERT_BAREME5_INDEXATION = "INSERT INTO NASCA.RAPPORT_INDEXATION (ANNEE_NUMERATEUR,NUMERATEUR,ANNEE_DENOMINATEUR,DENOMINATEUR,DOMAINE,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (?,506.6000,?,473.9100,'S2',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation')";
    public static final String INSERT_BAREME6_INDEXATION = "INSERT INTO NASCA.RAPPORT_INDEXATION (ANNEE_NUMERATEUR,NUMERATEUR,ANNEE_DENOMINATEUR,DENOMINATEUR,DOMAINE,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (?,506.6000,?,487.3600,'S2',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation')";
    public static final String INSERT_BAREME7_INDEXATION = "INSERT INTO NASCA.RAPPORT_INDEXATION (ANNEE_NUMERATEUR,NUMERATEUR,ANNEE_DENOMINATEUR,DENOMINATEUR,DOMAINE,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (?,506.6000,?,492.7800,'  ',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation')";
    public static final String INSERT_BAREME8_INDEXATION = "INSERT INTO NASCA.RAPPORT_INDEXATION (ANNEE_NUMERATEUR,NUMERATEUR,ANNEE_DENOMINATEUR,DENOMINATEUR,DOMAINE,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (?,506.6000,?,506.6000,'  ',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation')";
    public static final String INSERT_BAREME9_INDEXATION = "INSERT INTO NASCA.RAPPORT_INDEXATION (ANNEE_NUMERATEUR,NUMERATEUR,ANNEE_DENOMINATEUR,DENOMINATEUR,DOMAINE,SYS_TMSTP_MAJ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES (?,506.6000,?,506.6000,'  ',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation',{ts '2016-12-03 14:26:37'},0,'REPRISE_ETL - T_BAR_Load_Rapport_Indexation')";

    public static final String INSERT_DATE_ENROLEMENT_PP_TRIM = "INSERT "
            + "INTO NASCAPARAM.DATE_ENROLEMENT_TRIMESTRIEL "
            + "(ANNEE ,TRIMESTRE,DT_ENROLEMENT,EXECUTE,SYS_TMSTP_MAJ      ,SYS_USR_MAJ_FK,SYS_MOD_MAJ,SYS_TMSTP_CREA    ,SYS_USR_CREA_FK,SYS_MOD_CREA) VALUES "
            + "(?     ,?        , ?           ,'F'    , CURRENT TIMESTAMP ,null          ,null       ,CURRENT TIMESTAMP ,null           ,null        ) ";

    public static final String DELETE_DATE_ENROLEMENT_PP = "delete FROM NASCAPARAM.DATE_ENROLEMENT_TRIMESTRIEL WHERE annee = ? AND trimestre = ?";

    public static final String DELETE_ENROLEMENT_TRIMESTRIEL_BLOCAGE = "update NASCA.BLOCAGE set DATE_ENROLEMENT_TRIMESTRIEL_FK = NULL WHERE TYPE_BLOCAGE = 'AVIS_PP'";

    public static final String UPDATE_MONTANT_PLANCHER_EVENEMENT = "UPDATE NASCA.EVENEMENT SET TYPE_MONTANT_PLANCHER_FK = ? " +
            "WHERE EVENEMENT_IK = " +
            "( " +
            "   SELECT " +
            "   EVENEMENT_IK " +
            "   FROM NASCA.EVENEMENT " +
            "   WHERE DOSSIER_FK = " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_IK " +
            "      FROM NASCA.dossier " +
            "      WHERE IDENTITE_FK = " +
            "      ( " +
            "         SELECT " +
            "         IDENTITE_FK " +
            "         FROM NASCA.IDENTIFIANT " +
            "         WHERE NUM_IDENTIFIANT = ? " +
            "      ) " +
            "      AND TYPE = 'CP' " +
            "   ) " +
            "   AND HIS_STATUT_RECORD = 'A' " +
            "   order by DT_EVENEMENT desc FETCH FIRST 1 ROWS ONLY " +
            ") ";

    public static final String SELECT_DOSSIER_IK_FROM_NISS = "SELECT " +
            "DOSSIER_IK " +
            "FROM NASCA.DOSSIER d " +
            "WHERE d.TYPE = 'CP' AND d.IDENTITE_FK IN " +
            "( " +
            "   SELECT " +
            "   i.IDENTITE_FK " +
            "   FROM NASCA.IDENTIFIANT i " +
            "   WHERE i.TYPE_IDENTIFIANT = 'NISS' " +
            "   AND i.NUM_IDENTIFIANT = ? " +
            ") ";

    public static final String SELECT_DOSSIER_IK_FROM_BCE = "SELECT " +
            "DOSSIER_IK " +
            "FROM NASCA.DOSSIER d " +
            "WHERE d.TYPE = 'CS' " +
            "AND d.IDENTITE_FK IN " +
            "( " +
            "   SELECT " +
            "   i.IDENTITE_FK " +
            "   FROM NASCA.IDENTIFIANT i " +
            "   WHERE i.TYPE_IDENTIFIANT = 'BCE' " +
            "   AND i.NUM_IDENTIFIANT = ? " +
            ") ";

    public static final String SELECT_FLUX_DEPISTAGE = "SELECT TYPE, DONNEES " +
            "FROM NASCABATCH.INASTI_RECORD " +
            "WHERE NASCABATCH.INASTI_RECORD.IDENTIFIANT = ?";


    // PROFIL V3
    public static final String SELECT_NISS_SANS_CJT_SANS_PLANCHER_PROFIL_V3_TRIMESTRIEL = "SELECT " +
            "NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT " +
            "WHERE TYPE_IDENTIFIANT = 'NISS' " +
            "AND IDENTITE_FK in " +
            "( " +
            "   SELECT " +
            "   pp.IDENTITE_IK " +
            "   FROM NASCA.PERSONNE_PHYSIQUE pp " +
            "   LEFT JOIN NASCA.LIEN_PP lienpp ON lienpp.IDENTITE_LIANTE_FK = pp.IDENTITE_IK " +
            "   LEFT JOIN NASCA.DOSSIER identiteDossier ON identiteDossier.IDENTITE_FK = pp.IDENTITE_IK " +
            "   WHERE pp.DT_NAISSANCE > ? " +
            "   AND pp.DT_NAISSANCE < ? " +
            "   AND identiteDossier.DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      evt.DOSSIER_FK " +
            "      FROM NASCA.EVENEMENT evt " +
            "      LEFT JOIN PERIODE_AFFILIATION_PP periodeAffi ON periodeAffi.DOSSIER_FK = evt.DOSSIER_FK " +
            "      WHERE evt.NAC_INASTI LIKE ? " +
            "      AND evt.DT_DEBUT_PERIODE <= '2015-01-01'" +
            "      AND evt.DT_FIN_PERIODE IS NULL " +
            "      AND periodeAffi.DT_FIN_PERIODE IS NULL " +
            "      AND lienpp.ETAT_CIVIL = 'CELIB' " +
            "      AND evt.HIS_STATUT_RECORD = 'A' " +
            "      AND evt.TYPE_MONTANT_PLANCHER_FK IS NULL " +
            "   ) " +
            ") FETCH FIRST 200 ROWS ONLY ";

    public static final String SELECT_NISS_SANS_CJT_AVEC_PLANCHER_PROFIL_V3_TRIMESTRIEL = "SELECT " +
            "NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT " +
            "WHERE TYPE_IDENTIFIANT = 'NISS' " +
            "AND IDENTITE_FK in " +
            "( " +
            "   SELECT " +
            "   pp.IDENTITE_IK " +
            "   FROM NASCA.PERSONNE_PHYSIQUE pp " +
            "   LEFT JOIN NASCA.LIEN_PP lienpp ON lienpp.IDENTITE_LIANTE_FK = pp.IDENTITE_IK " +
            "   LEFT JOIN NASCA.DOSSIER identiteDossier ON identiteDossier.IDENTITE_FK = pp.IDENTITE_IK " +
            "   WHERE pp.DT_NAISSANCE > ? " +
            "   AND pp.DT_NAISSANCE < ? " +
            "   AND identiteDossier.DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      evt.DOSSIER_FK " +
            "      FROM NASCA.EVENEMENT evt " +
            "      LEFT JOIN PERIODE_AFFILIATION_PP periodeAffi ON periodeAffi.DOSSIER_FK = evt.DOSSIER_FK " +
            "      WHERE evt.NAC_INASTI LIKE ? " +
            "      AND evt.DT_DEBUT_PERIODE <= '2015-01-01'" +
            "      AND evt.DT_FIN_PERIODE IS NULL " +
            "      AND periodeAffi.DT_FIN_PERIODE IS NULL " +
            "      AND lienpp.ETAT_CIVIL = 'CELIB' " +
            "      AND evt.HIS_STATUT_RECORD = 'A' " +
            "      AND evt.TYPE_MONTANT_PLANCHER_FK IS NOT NULL " +
            "   ) " +
            ") FETCH FIRST 200 ROWS ONLY ";

    public static final String SELECT_NISS_AVEC_CJT_PENSIONNNE_PROFIL_V3_TRIMESTRIEL = "SELECT " +
            "NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT " +
            "WHERE TYPE_IDENTIFIANT = 'NISS' " +
            "AND IDENTITE_FK in " +
            "( " +
            "   SELECT " +
            "   pp.IDENTITE_IK " +
            "   FROM NASCA.PERSONNE_PHYSIQUE pp " +
            "   LEFT JOIN NASCA.LIEN_PP lienpp ON lienpp.IDENTITE_LIANTE_FK = pp.IDENTITE_IK " +
            "   LEFT JOIN NASCA.DOSSIER identiteDossier ON identiteDossier.IDENTITE_FK = pp.IDENTITE_IK " +
            "   WHERE pp.DT_NAISSANCE > ? " +
            "   AND pp.DT_NAISSANCE < ? " +
            "   AND identiteDossier.DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      evt.DOSSIER_FK " +
            "      FROM NASCA.EVENEMENT evt " +
            "      LEFT JOIN NASCA.PERIODE_AFFILIATION_PP periodeAffi ON periodeAffi.DOSSIER_FK = evt.DOSSIER_FK " +
            "      WHERE evt.NAC_INASTI LIKE ? " +
            "      AND evt.DT_DEBUT_PERIODE <= '2015-01-01'" +
            "      AND evt.DT_FIN_PERIODE IS NULL " +
            "      AND periodeAffi.DT_FIN_PERIODE IS NULL " +
            "      AND lienpp.ETAT_CIVIL IN('MARIE', 'COHAB') " +
            "      AND evt.HIS_STATUT_RECORD = 'A' " +
            "      AND evt.TYPE_MONTANT_PLANCHER_FK IS NULL " +
            "      AND EXISTS " +
            "      ( " +
            "         SELECT " +
            "         * " +
            "         FROM NASCA.PENSION pens " +
            "         WHERE pens.DOSSIER_FK = evt.DOSSIER_FK " +
            "         AND pens.DETENTEUR_PENSION = 'CJT' " +
            "         AND pens.TYPE_PENSION = 'PENSI' " +
            "         AND pens.REGIME_PENSION = 'IDP' " +
            "      ) " +
            "   ) " +
            ") " +
            "FETCH FIRST 200 ROWS ONLY ";

    public static final String SELECT_NISS_AVEC_CJT_NON_PENSIONNNE_PROFIL_V3_TRIMESTRIEL = "SELECT " +
            "NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT " +
            "WHERE TYPE_IDENTIFIANT = 'NISS' " +
            "AND IDENTITE_FK in " +
            "( " +
            "   SELECT " +
            "   pp.IDENTITE_IK " +
            "   FROM NASCA.PERSONNE_PHYSIQUE pp " +
            "   LEFT JOIN NASCA.LIEN_PP lienpp ON lienpp.IDENTITE_LIANTE_FK = pp.IDENTITE_IK " +
            "   LEFT JOIN NASCA.DOSSIER identiteDossier ON identiteDossier.IDENTITE_FK = pp.IDENTITE_IK " +
            "   WHERE pp.DT_NAISSANCE > ? " +
            "   AND pp.DT_NAISSANCE < ? " +
            "   AND identiteDossier.DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      evt.DOSSIER_FK " +
            "      FROM NASCA.EVENEMENT evt " +
            "      LEFT JOIN NASCA.PERIODE_AFFILIATION_PP periodeAffi ON periodeAffi.DOSSIER_FK = evt.DOSSIER_FK " +
            "      WHERE evt.NAC_INASTI LIKE ? " +
            "      AND evt.DT_DEBUT_PERIODE <= '2015-01-01'" +
            "      AND evt.DT_FIN_PERIODE IS NULL " +
            "      AND periodeAffi.DT_FIN_PERIODE IS NULL " +
            "      AND lienpp.ETAT_CIVIL IN('MARIE', 'COHAB') " +
            "      AND evt.HIS_STATUT_RECORD = 'A' " +
            "      AND evt.TYPE_MONTANT_PLANCHER_FK IS NULL " +
            "      AND NOT EXISTS " +
            "      ( " +
            "         SELECT " +
            "         * " +
            "         FROM NASCA.PENSION pens " +
            "         WHERE pens.DOSSIER_FK = evt.DOSSIER_FK " +
            "         AND pens.DETENTEUR_PENSION = 'CJT' " +
            "         AND pens.TYPE_PENSION = 'PENSI' " +
            "         AND pens.REGIME_PENSION = 'IDP' " +
            "      ) " +
            "   ) " +
            ") " +
            "FETCH FIRST 200 ROWS ONLY ";

    public static final String UPDATE_DATE_NAISSANCE_PROFIL_V3 = "UPDATE NASCA.PERSONNE_PHYSIQUE set DT_NAISSANCE = ? " +
            "WHERE IDENTITE_IK in " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.IDENTIFIANT identifiant " +
            "   WHERE identifiant.NUM_IDENTIFIANT = ? " +
            ") ";

    public static final String UPDATE_CARACTERISTIQUES_PENSION = "UPDATE NASCA.PENSION pens " +
            "SET REGIME_PENSION = ?, " +
            "INFO_CARRIERE = ?, " +
            "NATURE_PENSION = ?, " +
            "DETENTEUR_PENSION = ?, " +
            "A_ENFANT_A_CHARGE = ? " +
            "WHERE pens.DOSSIER_FK IN " +
            "( " +
            "   SELECT " +
            "   dos.DOSSIER_IK " +
            "   FROM NASCA.DOSSIER dos " +
            "   WHERE dos.IDENTITE_FK IN " +
            "   ( " +
            "      SELECT " +
            "      ident.IDENTITE_FK " +
            "      FROM NASCA.IDENTIFIANT ident " +
            "      WHERE ident.NUM_IDENTIFIANT = ? " +
            "   ) " +
            ") ";

    public static final String SELECT_NISS_PAS_ATTEINT_AGE_PENSION_PROFIL_V3_ANNUEL = "SELECT " +
            "NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT " +
            "WHERE TYPE_IDENTIFIANT = 'NISS' " +
            "AND IDENTITE_FK in " +
            "( " +
            "   SELECT " +
            "   pp.IDENTITE_IK " +
            "   FROM NASCA.PERSONNE_PHYSIQUE pp " +
            "   LEFT JOIN NASCA.DOSSIER identiteDossier ON identiteDossier.IDENTITE_FK = pp.IDENTITE_IK " +
            "   WHERE pp.DT_NAISSANCE > ? " +
            "   AND identiteDossier.DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      evt.DOSSIER_FK " +
            "      FROM NASCA.EVENEMENT evt " +
            "      LEFT JOIN PERIODE_AFFILIATION_PP periodeAffi ON periodeAffi.DOSSIER_FK = evt.DOSSIER_FK " +
            "      LEFT JOIN NASCA.PENSION pens ON pens.DOSSIER_FK = identiteDossier.DOSSIER_IK " +
            "      WHERE evt.NAC_INASTI LIKE ? " +
            "      AND evt.DT_DEBUT_PERIODE > '2015-01-01' " +
            "      AND evt.DT_FIN_PERIODE IS NULL " +
            "      AND periodeAffi.DT_FIN_PERIODE IS NULL " +
            "      AND evt.HIS_STATUT_RECORD = 'A' " +
            "      AND pens.REGIME_PENSION = ? " +
            "      AND pens.NATURE_PENSION = ? " +
            "      AND pens.A_ENFANT_A_CHARGE = ? " +
            "      AND evt.DT_DEBUT_PERIODE = pens.DT_DEBUT_PERIODE" +
            "   ) " +
            ") FETCH FIRST 200 ROWS ONLY ";

    public static final String SELECT_NISS_ATTEINT_AGE_PENSION_PROFIL_V3_ANNUEL = "SELECT " +
            "NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT " +
            "WHERE TYPE_IDENTIFIANT = 'NISS' " +
            "AND IDENTITE_FK in " +
            "( " +
            "   SELECT " +
            "   pp.IDENTITE_IK " +
            "   FROM NASCA.PERSONNE_PHYSIQUE pp " +
            "   LEFT JOIN NASCA.DOSSIER identiteDossier ON identiteDossier.IDENTITE_FK = pp.IDENTITE_IK " +
            "   WHERE pp.DT_NAISSANCE < ? " +
            "   AND identiteDossier.DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      evt.DOSSIER_FK " +
            "      FROM NASCA.EVENEMENT evt " +
            "      LEFT JOIN PERIODE_AFFILIATION_PP periodeAffi ON periodeAffi.DOSSIER_FK = evt.DOSSIER_FK " +
            "      LEFT JOIN NASCA.PENSION pens ON pens.DOSSIER_FK = identiteDossier.DOSSIER_IK " +
            "      WHERE evt.NAC_INASTI LIKE ? " +
            "      AND evt.DT_DEBUT_PERIODE > '2015-01-01' " +
            "      AND evt.DT_FIN_PERIODE IS NULL " +
            "      AND periodeAffi.DT_FIN_PERIODE IS NULL " +
            "      AND evt.HIS_STATUT_RECORD = 'A' " +
            "      AND pens.REGIME_PENSION = ? " +
            "      AND pens.NATURE_PENSION = ? " +
            "      AND pens.A_ENFANT_A_CHARGE = ? " +
            "      AND evt.DT_DEBUT_PERIODE = pens.DT_DEBUT_PERIODE" +
            "   ) " +
            ") FETCH FIRST 200 ROWS ONLY ";

    public static final String SELECT_SUSPENSION_COMPTE_A_0 = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec " +
            "INNER JOIN DOSSIER d " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK " +
            "INNER JOIN IDENTITE i " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN IDENTIFIANT id " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN (SELECT * FROM RECOUVREMENT r WHERE r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F') r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK " +
            "WHERE id.TYPE_IDENTIFIANT = 'BCE' AND ec.SOLDE = 0 AND ec.NATURE = 'COT_PM' AND r.SUSPENDU = 'F' FETCH FIRST 100 ROWS ONLY";

    public static final String SELECT_SUSPENSION_PM_COT_EN_COMPTE = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec " +
            "INNER JOIN DOSSIER d " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK " +
            "INNER JOIN IDENTITE i " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN IDENTIFIANT id " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN (SELECT * FROM RECOUVREMENT r WHERE r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F') r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK  " +
            "WHERE id.TYPE_IDENTIFIANT = 'BCE' AND ec.SOLDE > 0 AND ec.NATURE = 'COT_PM' AND r.SUSPENDU ='F' FETCH FIRST 100 ROWS ONLY";

//    public static final String SELECT_SUSPENSION_PP_COT_EN_COMPTE = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec " +
//            "INNER JOIN (SELECT * FROM DOSSIER d WHERE d.TYPE = 'CP' AND d.CESSATION_ACTIVITE_EN_COURS IS NULL) d " +
//            "ON ec.DOSSIER_FK = d.DOSSIER_IK " +
//            "INNER JOIN IDENTITE i " +
//            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
//            "INNER JOIN IDENTIFIANT id " +
//            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
//            "INNER JOIN (SELECT * FROM RECOUVREMENT r WHERE r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F') r " +
//            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK " +
//            "INNER JOIN (SELECT * FROM EVENEMENT e WHERE e.DT_FIN_PERIODE IS NULL) e " +
//            "ON e.DOSSIER_FK = d.DOSSIER_IK " +
//            "WHERE id.TYPE_IDENTIFIANT = 'NISS' AND ec.SOLDE > 0 AND ec.NATURE = 'COT_PP' FETCH FIRST 100 ROWS ONLY ";

    public static final String SELECT_SUSPENSION_PP_20172 = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec " +
            "INNER JOIN DOSSIER d " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK AND d.TYPE = 'CP' AND d.CESSATION_ACTIVITE_EN_COURS IS NULL AND ec.SOLDE > 0 " +
            "INNER JOIN IDENTITE i " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN IDENTIFIANT id " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN RECOUVREMENT r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK AND r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F' " +
            "INNER JOIN EVENEMENT e " +
            "ON e.DOSSIER_FK = d.DOSSIER_IK AND e.DT_FIN_PERIODE IS NULL AND e.DT_DEBUT_PERIODE IS NOT NULL " +
            "WHERE id.TYPE_IDENTIFIANT = 'NISS' AND ec.PERIODE = '2017/2' AND ec.NATURE = 'COT_PP'FETCH FIRST 30 ROWS ONLY ";

    public static final String SELECT_SUSPENSION_PP_COT_EN_COMPTE = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec " +
            "INNER JOIN DOSSIER d " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK AND d.TYPE = 'CP' AND d.CESSATION_ACTIVITE_EN_COURS IS NULL AND ec.SOLDE > 0 " +
            "INNER JOIN IDENTITE i " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN IDENTIFIANT id " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN RECOUVREMENT r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK AND r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F' " +
            "LEFT JOIN RECOUVREMENT r2 " +
            "ON r.RECOUVREMENT_IK = r2.RECOUVREMENT_IK AND r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'T' " +
            "INNER JOIN EVENEMENT e " +
            "ON e.DOSSIER_FK = d.DOSSIER_IK AND e.DT_FIN_PERIODE IS NULL AND e.DT_DEBUT_PERIODE IS NOT NULL " +
            "WHERE id.TYPE_IDENTIFIANT = 'NISS' AND ec.PERIODE >= '2015/1' AND ec.NATURE = 'COT_PP' AND r2.RECOUVREMENT_IK IS NULL FETCH FIRST 30 ROWS ONLY ";

    public static final String SELECT_SUSPENSION_PP_PLAN_APUREMENT = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec " +
            "INNER JOIN DOSSIER d " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK AND d.TYPE = 'CP' AND d.CESSATION_ACTIVITE_EN_COURS IS NULL AND ec.SOLDE > 0 " +
            "INNER JOIN IDENTITE i " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN DEMANDE de " +
            "ON de.DOSSIER_FK = d.DOSSIER_IK " +
            "INNER JOIN SUSPENSION_RECOUVREMENT sr " +
            "ON de.DEMANDE_IK = sr.DEMANDE_FK AND sr.CAUSE_CREATION = 'PA' " +
            "INNER JOIN IDENTIFIANT id " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN RECOUVREMENT r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK AND r.SOLIDAIRE = 'F' " +
            "INNER JOIN EVENEMENT e " +
            "ON e.DOSSIER_FK = d.DOSSIER_IK AND e.DT_FIN_PERIODE IS NULL AND e.DT_DEBUT_PERIODE IS NOT NULL " +
            "WHERE id.TYPE_IDENTIFIANT = 'NISS' AND ec.PERIODE >= '2015/1' AND ec.NATURE = 'COT_PP' FETCH FIRST 30 ROWS ONLY ";

    public static final String SELECT_SUSPENSION_PM_PLAN_APUREMENT = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec " +
            "INNER JOIN DOSSIER d " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK AND d.TYPE = 'CS' AND d.CESSATION_ACTIVITE_EN_COURS IS NULL AND ec.SOLDE > 0 " +
            "INNER JOIN IDENTITE i " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN DEMANDE de " +
            "ON de.DOSSIER_FK = d.DOSSIER_IK " +
            "INNER JOIN SUSPENSION_RECOUVREMENT sr " +
            "ON de.DEMANDE_IK = sr.DEMANDE_FK AND sr.CAUSE_CREATION = 'PA' " +
            "INNER JOIN IDENTIFIANT id " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN RECOUVREMENT r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK AND r.SOLIDAIRE = 'F' " +
            "WHERE id.TYPE_IDENTIFIANT = 'BCE' AND ec.PERIODE >= '2015' AND ec.NATURE = 'COT_PM' FETCH FIRST 30 ROWS ONLY ";

    /*public static final String SELECT_SUSPENSION_PP_EC_PARTIELLEMENT_PAYEE = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec " +
            "INNER JOIN (SELECT DOSSIER_FK, MAX(PERIODE) AS MaxPeriode FROM ENTITE_COMPTABLE GROUP BY DOSSIER_FK)groupeec " +
            "ON ec.PERIODE = groupeec.MaxPeriode " +
            "INNER JOIN DOSSIER d " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK AND d.TYPE = 'CP' AND d.CESSATION_ACTIVITE_EN_COURS IS NULL AND ec.SOLDE > 0 " +
            "INNER JOIN OPERATION_COMPTABLE oc " +
            "ON ec.ENTITE_COMPTABLE_IK = oc.ENTITE_COMPTABLE_FK AND oc.TYPE = 'REC' AND oc.ANNULE = 'F' " +
            "INNER JOIN IDENTITE i " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN IDENTIFIANT id " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN RECOUVREMENT r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK AND r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F' " +
            "INNER JOIN EVENEMENT e " +
            "ON e.DOSSIER_FK = d.DOSSIER_IK AND e.DT_FIN_PERIODE IS NULL AND e.DT_DEBUT_PERIODE IS NOT NULL " +
            "WHERE id.TYPE_IDENTIFIANT = 'NISS' AND ec.NATURE = 'COT_PP'FETCH FIRST 30 ROWS ONLY ";
    */
    /*public static final String SELECT_SUSPENSION_PP_EC_PARTIELLEMENT_PAYEE =

            "SELECT id.NUM_IDENTIFIANT, ec.ENTITE_COMPTABLE_IK FROM ENTITE_COMPTABLE ec " +
                    "INNER JOIN DOSSIER d " +
                    "ON ec.DOSSIER_FK = d.DOSSIER_IK AND d.TYPE = 'CP' AND d.CESSATION_ACTIVITE_EN_COURS IS NULL AND ec.SOLDE > 0 " +
                    "INNER JOIN OPERATION_COMPTABLE oc " +
                    "ON ec.ENTITE_COMPTABLE_IK = oc.ENTITE_COMPTABLE_FK AND oc.TYPE = 'REC' AND oc.ANNULE = 'F' " +
                    "INNER JOIN IDENTITE i " +
                    "ON d.IDENTITE_FK = i.IDENTITE_IK " +
                    "INNER JOIN IDENTIFIANT id " +
                    "ON id.IDENTITE_FK = i.IDENTITE_IK " +
                    "INNER JOIN RECOUVREMENT r " +
                    "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK AND r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F' " +
                    "INNER JOIN EVENEMENT e " +
                    "ON e.DOSSIER_FK = d.DOSSIER_IK AND e.DT_FIN_PERIODE IS NULL AND e.DT_DEBUT_PERIODE IS NOT NULL " +
                    "WHERE id.TYPE_IDENTIFIANT = 'NISS' AND ec.NATURE = 'COT_PP'FETCH FIRST 30 ROWS ONLY ";
*/
    public static final String SELECT_SUSPENSION_PP_EC_PARTIELLEMENT_PAYEE = "with last_coti as ( " +
            "                select ENTITE_COMPTABLE_IK, periode, solde,dossier_fk, ROW_NUMBER() over (PARTITION BY ec.DOSSIER_FK order by ec.DOSSIER_FK, periode desc)  as NB " +
            "                from nasca.entite_comptable ec,nasca.OPERATION_COMPTABLE oc " +
            "                where ec.ENTITE_COMPTABLE_IK = oc.ENTITE_COMPTABLE_FK AND oc.TYPE = 'REC' AND oc.ANNULE = 'F' " +
            "                and nature='COT_PP' AND SOLDE > 0 and ec.annule='F' " +
            ") " +
            "select id.NUM_IDENTIFIANT , PERIODE from last_coti lc " +
            "INNER JOIN nasca.DOSSIER d " +
            "ON lc.DOSSIER_FK = d.DOSSIER_IK AND d.TYPE = 'CP' AND d.CESSATION_ACTIVITE_EN_COURS IS NULL " +
            "INNER JOIN nasca.IDENTITE i " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN nasca.IDENTIFIANT id " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN nasca.RECOUVREMENT r " +
            "ON r.CREANCE_FK = lc.ENTITE_COMPTABLE_IK AND r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F' " +
            "INNER JOIN nasca.EVENEMENT e " +
            "ON e.DOSSIER_FK = d.DOSSIER_IK AND e.DT_FIN_PERIODE IS NULL AND e.DT_DEBUT_PERIODE IS NOT NULL " +
            "WHERE id.TYPE_IDENTIFIANT = 'NISS' AND lc.nb=1 " +
            "FETCH FIRST 30 ROWS ONLY ";

    public static final String SELECT_SUSPENSION_PM_EC_PARTIELLEMENT_PAYEE = "with last_coti as ( " +
            "                select ENTITE_COMPTABLE_IK, periode, solde,dossier_fk, ROW_NUMBER() over (PARTITION BY ec.DOSSIER_FK order by ec.DOSSIER_FK, periode desc)  as NB " +
            "                from nasca.entite_comptable ec,nasca.OPERATION_COMPTABLE oc " +
            "                where ec.ENTITE_COMPTABLE_IK = oc.ENTITE_COMPTABLE_FK AND oc.TYPE = 'REC' AND oc.ANNULE = 'F' " +
            "                and nature='COT_PM' AND SOLDE > 0 and ec.annule='F' " +
            ") " +
            "select id.NUM_IDENTIFIANT , PERIODE from last_coti lc " +
            "INNER JOIN nasca.DOSSIER d " +
            "ON lc.DOSSIER_FK = d.DOSSIER_IK AND d.TYPE = 'CS' AND d.CESSATION_ACTIVITE_EN_COURS IS NULL " +
            "INNER JOIN nasca.IDENTITE i " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN nasca.IDENTIFIANT id " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN nasca.RECOUVREMENT r " +
            "ON r.CREANCE_FK = lc.ENTITE_COMPTABLE_IK AND r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F' " +
            "INNER JOIN nasca.EVENEMENT e " +
            "ON e.DOSSIER_FK = d.DOSSIER_IK AND e.DT_FIN_PERIODE IS NULL AND e.DT_DEBUT_PERIODE IS NOT NULL " +
            "WHERE id.TYPE_IDENTIFIANT = 'BCE' AND lc.nb=1 " +
            "FETCH FIRST 30 ROWS ONLY ";

    public static final String SELECT_SUSPENSION_PP_MULTIPLE_COTISATION = "SELECT NUM_IDENTIFIANT FROM ( " +
            "	SELECT num_identifiant, COUNT(*) " +
            "	FROM nasca.DOSSIER d, nasca.ENTITE_COMPTABLE ec , nasca.RECOUVREMENT r, nasca.IDENTITE i , nasca.IDENTIFIANT id, nasca.EVENEMENT e " +
            "	where d.DOSSIER_IK=ec.DOSSIER_FK and  r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK and d.IDENTITE_FK = i.IDENTITE_IK and id.IDENTITE_FK = i.IDENTITE_IK and e.DOSSIER_FK = d.DOSSIER_IK " +
            "	and  ec.NATURE = 'COT_PP' AND ec.SOLDE > 0 and annule='F' AND PERIODE >= '2015/1' and r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F' " +
            "	and d.TYPE = 'CP' AND d.CESSATION_ACTIVITE_EN_COURS IS NULL  and id.TYPE_IDENTIFIANT = 'NISS' " +
            "	and  e.DT_FIN_PERIODE IS NULL AND e.DT_DEBUT_PERIODE IS NOT NULL and e.his_statut_record='A' " +
            "	GROUP BY num_identifiant " +
            "	HAVING COUNT(*) > 1 " +
            ") " +
            "FETCH FIRST 30 ROWS ONLY ";

    public static final String SELECT_SUSPENSION_PM_MULTIPLE_COTISATION = "SELECT NUM_IDENTIFIANT FROM ( " +
            "	SELECT num_identifiant, COUNT(*) " +
            "	FROM nasca.DOSSIER d, nasca.ENTITE_COMPTABLE ec , nasca.RECOUVREMENT r, nasca.IDENTITE i , nasca.IDENTIFIANT id " +
            "	where d.DOSSIER_IK=ec.DOSSIER_FK and  r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK and d.IDENTITE_FK = i.IDENTITE_IK and id.IDENTITE_FK = i.IDENTITE_IK " +
            "	and  ec.NATURE = 'COT_PM' AND ec.SOLDE > 0 and annule='F' AND PERIODE >= '2015' and r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F' " +
            "	and d.TYPE = 'CS' AND d.CESSATION_ACTIVITE_EN_COURS IS NULL  and id.TYPE_IDENTIFIANT = 'BCE' " +
            "	GROUP BY num_identifiant " +
            "	HAVING COUNT(*) > 1 " +
            ") " +
            "FETCH FIRST 30 ROWS ONLY ";

    public static final String SELECT_SUSPENSION_PP_COMPTE_A_0 = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec " +
            "INNER JOIN (SELECT * FROM DOSSIER d WHERE d.TYPE = 'CP' AND d.CESSATION_ACTIVITE_EN_COURS IS NULL) d " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK " +
            "INNER JOIN IDENTITE i " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN IDENTIFIANT id " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN (SELECT * FROM RECOUVREMENT r WHERE r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F') r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK " +
            "INNER JOIN (SELECT * FROM EVENEMENT e WHERE e.DT_FIN_PERIODE IS NULL) e " +
            "ON e.DOSSIER_FK = d.DOSSIER_IK " +
            "WHERE id.TYPE_IDENTIFIANT = 'NISS' AND ec.SOLDE = 0 AND ec.NATURE = 'COT_PP' FETCH FIRST 100 ROWS ONLY ";


    public static final String SELECT_SUSPENSION_PM_RANDOM = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec " +
            "INNER JOIN DOSSIER d " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK " +
            "INNER JOIN IDENTITE i " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN IDENTIFIANT id " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN RECOUVREMENT r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK  " +
            "WHERE id.TYPE_IDENTIFIANT = 'BCE' AND ec.SOLDE > 0 AND ec.NATURE = 'COT_PM' AND r.SUSPENDU ='F' FETCH FIRST 100 ROWS ONLY";

    public static final String SELECT_SUSPENSION_PP_PRINC = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec " +
            "INNER JOIN DOSSIER d " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK " +
            "INNER JOIN IDENTITE i " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN IDENTIFIANT id " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN (SELECT * FROM RECOUVREMENT r WHERE r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F') r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK  " +
            "INNER JOIN EVENEMENT e " +
            "ON d.DOSSIER_IK = e.DOSSIER_FK " +
            "WHERE id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND ec.SOLDE > 0 " +
            "AND ec.NATURE = 'COT_PP' " +
            "AND ec.TYPE = 'ORD' " +
            "AND e.NATURE_COTISANTE_FK = 1 " +
            /*"AND DT_DEBUT_PERIODE <= '2010-01-01' " +
            "AND TYPE_REDUC_COTISATION IS NULL " +
            "AND DT_FIN_PERIODE IS NULL " +
            "AND STATUT_EVENEMENT = 'ACT' " +
            "AND HIS_STATUT_RECORD = 'A' " +*/
            "FETCH FIRST 100 ROWS ONLY";

    public static final String SELECT_SUSPENSION_PP_COT_REGUL = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec " +
            "INNER JOIN DOSSIER d " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK " +
            "INNER JOIN IDENTITE i " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN IDENTIFIANT id " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN (SELECT * FROM RECOUVREMENT r WHERE r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F') r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK  " +
            "INNER JOIN EVENEMENT e " +
            "ON d.DOSSIER_IK = e.DOSSIER_FK " +
            "WHERE id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND ec.SOLDE > 0 " +
            "AND ec.TYPE = 'REG' " +
            "AND ec.NATURE = 'COT_PP' " +
            "AND e.NATURE_COTISANTE_FK = 1 " +
            /*"AND DT_DEBUT_PERIODE <= '2010-01-01' " +
            "AND TYPE_REDUC_COTISATION IS NULL " +
            "AND DT_FIN_PERIODE IS NULL " +
            "AND STATUT_EVENEMENT = 'ACT' " +
            "AND HIS_STATUT_RECORD = 'A' " +*/
            "FETCH FIRST 100 ROWS ONLY";


    public static final String SELECT_SUSPENSION_PP_MULT_COT_REGUL = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec " +
            "INNER JOIN DOSSIER d " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK " +
            "INNER JOIN IDENTITE i " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN IDENTIFIANT id " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN (SELECT * FROM RECOUVREMENT r WHERE r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F') r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK  " +
            "INNER JOIN EVENEMENT e " +
            "ON d.DOSSIER_IK = e.DOSSIER_FK " +
            "WHERE id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND ec.SOLDE > 0 " +
            "AND ec.TYPE = 'REG' " +
            "AND ec.NATURE = 'COT_PP' " +
            "AND e.NATURE_COTISANTE_FK = 1 " +
            /*"AND DT_DEBUT_PERIODE <= '2010-01-01' " +
            "AND TYPE_REDUC_COTISATION IS NULL " +
            "AND DT_FIN_PERIODE IS NULL " +
            "AND STATUT_EVENEMENT = 'ACT' " +
            "AND HIS_STATUT_RECORD = 'A' " +*/
            "FETCH FIRST 100 ROWS ONLY";

    public static final String SELECT_SUSPENSION_MULT_EC = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec  " +
            "INNER JOIN DOSSIER d  " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK  " +
            "INNER JOIN IDENTITE i  " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK  " +
            "INNER JOIN IDENTIFIANT id  " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK  " +
            "INNER JOIN (SELECT * FROM RECOUVREMENT r WHERE r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F') r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK " +
            "INNER JOIN EVENEMENT e  " +
            "ON d.DOSSIER_IK = e.DOSSIER_FK  " +
            "WHERE id.TYPE_IDENTIFIANT = 'NISS'  " +
            "AND ec.SOLDE > 0  " +
            "AND ec.TYPE = 'REG'  " +
            "AND ec.NATURE = 'COT_PP'  " +
            "GROUP BY id.NUM_IDENTIFIANT " +
            "HAVING COUNT(*) > 1 " +
            "FETCH FIRST 100 ROWS ONLY";

    public static final String SELECT_SUSPENSION_MULT_MAJ_EC = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec  " +
            "INNER JOIN DOSSIER d  " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK  " +
            "INNER JOIN IDENTITE i  " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK  " +
            "INNER JOIN IDENTIFIANT id  " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK  " +
            "INNER JOIN (SELECT * FROM RECOUVREMENT r WHERE r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F') r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK " +
            "INNER JOIN EVENEMENT e  " +
            "ON d.DOSSIER_IK = e.DOSSIER_FK  " +
            "WHERE id.TYPE_IDENTIFIANT = 'NISS'  " +
            "AND ec.SOLDE > 0  " +
            "AND ec.NATURE = 'MAJ_PP'  " +
            "GROUP BY id.NUM_IDENTIFIANT " +
            "HAVING COUNT(*) > 1 " +
            "FETCH FIRST 100 ROWS ONLY";

    public static final String SELECT_SUSPENSION_MAJ_EC = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec  " +
            "INNER JOIN DOSSIER d  " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK  " +
            "INNER JOIN IDENTITE i  " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK  " +
            "INNER JOIN IDENTIFIANT id  " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK  " +
            "INNER JOIN (SELECT * FROM RECOUVREMENT r WHERE r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F') r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK " +
            "INNER JOIN EVENEMENT e  " +
            "ON d.DOSSIER_IK = e.DOSSIER_FK  " +
            "WHERE id.TYPE_IDENTIFIANT = 'NISS'  " +
            "AND ec.SOLDE > 0  " +
            "AND ec.NATURE = 'MAJ_PP'  " +
            "GROUP BY id.NUM_IDENTIFIANT " +
            //"HAVING COUNT(*) > 1 " +
            "FETCH FIRST 100 ROWS ONLY";

    public static final String SELECT_SUSPENSION_PP_CODEBIT = "SELECT id.NUM_IDENTIFIANT FROM ( " +
            "                SELECT d.* FROM DOSSIER d " +
            "                INNER JOIN IDENTITE i " +
            "                ON d.CODEBITEUR_FK = i.IDENTITE_IK " +
            "                INNER JOIN IDENTIFIANT id " +
            "                ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "                WHERE id.TYPE_IDENTIFIANT = 'BCE' " +
            "              ) d " +
            "            INNER JOIN (SELECT * FROM RECOUVREMENT r WHERE r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F') r " +
            "            ON r.IDENTITE_FK = d.IDENTITE_FK " +
            "            INNER JOIN (SELECT CREANCE_FK, COUNT(IDENTITE_FK) AS nb_solidaire from RECOUVREMENT GROUP BY CREANCE_FK HAVING COUNT(IDENTITE_FK) > 2) sol " +
            "            ON sol.CREANCE_FK = r.CREANCE_FK " +
            "            INNER JOIN IDENTIFIANT id " +
            "            ON id.IDENTITE_FK = d.IDENTITE_FK " +
            "            INNER JOIN ENTITE_COMPTABLE ec " +
            "            ON ec.ENTITE_COMPTABLE_IK = r.CREANCE_FK " +
            "            LEFT JOIN SUSPENSION_RECOUVREMENT sr " +
            "            ON sr.IDENTITE_FK = d.IDENTITE_FK " +
            "            WHERE id.TYPE_IDENTIFIANT = 'NISS' AND sr.SUSPENSION_RECOUVREMENT_IK IS NULL AND ec.SOLDE > 0 AND ec.SOLDE_SOLIDAIRE > 0 FETCH FIRST 30 ROWS ONLY ";

    public static final String SELECT_SUSPENSION_PM_CODEBIT = "SELECT id.NUM_IDENTIFIANT FROM ( " +
            "                SELECT d.* FROM DOSSIER d " +
            "                INNER JOIN IDENTITE i " +
            "                ON d.CODEBITEUR_FK = i.IDENTITE_IK " +
            "                INNER JOIN IDENTIFIANT id " +
            "                ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "                WHERE id.TYPE_IDENTIFIANT = 'NISS' " +
            "              ) d " +
            "            INNER JOIN (SELECT * FROM RECOUVREMENT r WHERE r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F') r " +
            "            ON r.IDENTITE_FK = d.IDENTITE_FK " +
            "            INNER JOIN (SELECT CREANCE_FK, COUNT(IDENTITE_FK) AS nb_solidaire from RECOUVREMENT GROUP BY CREANCE_FK HAVING COUNT(IDENTITE_FK) > 2) sol " +
            "            ON sol.CREANCE_FK = r.CREANCE_FK " +
            "            INNER JOIN IDENTIFIANT id " +
            "            ON id.IDENTITE_FK = d.IDENTITE_FK " +
            "            INNER JOIN ENTITE_COMPTABLE ec " +
            "            ON ec.ENTITE_COMPTABLE_IK = r.CREANCE_FK " +
            "            LEFT JOIN SUSPENSION_RECOUVREMENT sr " +
            "            ON sr.IDENTITE_FK = d.IDENTITE_FK " +
            "            WHERE id.TYPE_IDENTIFIANT = 'BCE' AND sr.SUSPENSION_RECOUVREMENT_IK IS NULL AND ec.SOLDE > 0 AND ec.SOLDE_SOLIDAIRE > 0 FETCH FIRST 30 ROWS ONLY ";


    public static final String SELECT_SUSPENSION_PP_COMP = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec " +
            "INNER JOIN DOSSIER d " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK " +
            "INNER JOIN IDENTITE i " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN IDENTIFIANT id " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN (SELECT * FROM RECOUVREMENT r WHERE r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F') r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK  " +
            "INNER JOIN EVENEMENT e " +
            "ON d.DOSSIER_IK = e.DOSSIER_FK " +
            "WHERE id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND ec.SOLDE > 0 " +
            "AND ec.NATURE = 'COT_PP' " +
            "AND ec.TYPE = 'ORD' " +
            "AND e.NATURE_COTISANTE_FK = 4 " +
            /*"AND DT_DEBUT_PERIODE >= '2010-01-01' " +
            "AND TYPE_REDUC_COTISATION IS NULL " +
            "AND DT_FIN_PERIODE IS NULL " +
            "AND STATUT_EVENEMENT = 'ACT' " +
            "AND HIS_STATUT_RECORD = 'A' " +*/
            "FETCH FIRST 100 ROWS ONLY";


    public static final String SELECT_SUSPENSION_PP_CONJ_AID = "SELECT id.NUM_IDENTIFIANT FROM ENTITE_COMPTABLE ec " +
            "INNER JOIN DOSSIER d " +
            "ON ec.DOSSIER_FK = d.DOSSIER_IK " +
            "INNER JOIN IDENTITE i " +
            "ON d.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN IDENTIFIANT id " +
            "ON id.IDENTITE_FK = i.IDENTITE_IK " +
            "INNER JOIN (SELECT * FROM RECOUVREMENT r WHERE r.SOLIDAIRE = 'F' AND r.SUSPENDU = 'F') r " +
            "ON r.CREANCE_FK = ec.ENTITE_COMPTABLE_IK  " +
            "INNER JOIN EVENEMENT e " +
            "ON d.DOSSIER_IK = e.DOSSIER_FK " +
            "WHERE id.TYPE_IDENTIFIANT = 'NISS' " +
            "AND ec.SOLDE > 0 " +
            "AND ec.NATURE = 'COT_PP' " +
            "AND ec.TYPE = 'ORD' " +
            "AND e.NATURE_COTISANTE_FK in (5 ,6)" +
            /*"AND DT_DEBUT_PERIODE >= '2010-01-01' " +
            "AND TYPE_REDUC_COTISATION IS NULL " +
            "AND DT_FIN_PERIODE IS NULL " +
            "AND STATUT_EVENEMENT = 'ACT' " +
            "AND HIS_STATUT_RECORD = 'A' " +*/
            "FETCH FIRST 100 ROWS ONLY";

    public static final String SELECT_PP_NISS_FAILLITE_SOLDE = "SELECT NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT " +
            "WHERE TYPE_IDENTIFIANT = 'NISS' " +
            "AND IDENTITE_FK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER " +
            "   WHERE DOSSIER_IK IN " +
            "   ( " +
            "       SELECT " +
            "       DOSSIER_FK " +
            "       FROM NASCA.EVENEMENT " +
            "       WHERE NATURE_COTISANTE_FK = ? " +
            "       AND DT_DEBUT_PERIODE >= '2013-01-01' " +
            "       AND DT_FIN_PERIODE IS NULL " +
            "       AND HIS_STATUT_RECORD = 'A' " +
            "       AND STATUT_EVENEMENT = 'ACT' " +
            "   ) " +
            "   AND EXISTS " +
            "   ( " +
            "       SELECT * " +
            "       FROM NASCA.ENTITE_COMPTABLE" +
            "       WHERE DOSSIER_FK = DOSSIER_IK " +
            "       AND NATURE = 'COT_PP' " +
            "       AND ANNULE = 'F' " +
            "       AND SOLDE > 0 " +
            "   ) " +
            ") " +
            "AND IDENTITE_FK IN " +
            "( " +
            "   SELECT IDENTITE_IK " +
            "   FROM NASCA.IDENTITE " +
            "   WHERE IDENTITE_IK IN " +
            "   ( " +
            "       SELECT DEBITEUR_FK " +
            "       FROM PROCEDURE " +
            "       WHERE TYPE_PROCEDURE NOT LIKE 'FAI' " +
            "   ) " +
            ") FETCH FIRST 15 ROWS ONLY";

    public static final String SELECT_PP_NISS_FAILLITE_SS_SOLDE = "SELECT " +
            "NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT " +
            "WHERE TYPE_IDENTIFIANT = 'NISS' " +
            "AND IDENTITE_FK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER " +
            "   WHERE DOSSIER_IK IN " +
            "   ( " +
            "       SELECT " +
            "       DOSSIER_FK " +
            "       FROM NASCA.EVENEMENT " +
            "       WHERE NATURE_COTISANTE_FK = ? " +
            "       AND DT_DEBUT_PERIODE >= '2013-01-01' " +
            "       AND DT_FIN_PERIODE IS NULL " +
            "       AND HIS_STATUT_RECORD = 'A' " +
            "       AND STATUT_EVENEMENT = 'ACT' " +
            "   ) " +
            "   AND NOT EXISTS" +
            "   ( " +
            "       SELECT " +
            "       * " +
            "       FROM NASCA.ENTITE_COMPTABLE" +
            "       WHERE DOSSIER_FK = DOSSIER_IK " +
            "       AND NATURE = 'COT_PP' " +
            "       AND ANNULE = 'F' " +
            "       AND SOLDE > 0 " +
            "   ) " +
            ") " +
            "AND IDENTITE_FK IN " +
            "( " +
            "   SELECT IDENTITE_IK " +
            "   FROM NASCA.IDENTITE " +
            "   WHERE IDENTITE_IK IN " +
            "   ( " +
            "       SELECT DEBITEUR_FK " +
            "       FROM PROCEDURE " +
            "       WHERE TYPE_PROCEDURE NOT LIKE 'FAI' " +
            "   ) " +
            ") FETCH FIRST 15 ROWS ONLY";

    public static final String SELECT_PM_BEC_FAILLITE_SOLDE = "SELECT " +
            "a.NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b " +
            "WHERE a.IDENTITE_FK = b.IDENTITE_IK " +
            "AND a.TYPE_IDENTIFIANT = 'BCE' " +
            "AND b.IDENTITE_IK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER " +
            "   WHERE DOSSIER_IK IN " +
            "   ( " +
            "       SELECT " +
            "       DOSSIER_FK " +
            "       FROM NASCA.PERIODE_AFFILIATION_PM " +
            "       WHERE STATUT = 'ACTIF' " +
            "       AND DT_DEBUT_PERIODE > '2013-01-01' " +
            "       AND DT_FIN_PERIODE IS NULL " +
            "   ) " +
            "   AND DOSSIER_IK IN " +
            "   ( " +
            "       SELECT " +
            "       dossier_FK " +
            "       FROM NASCA.ENTITE_COMPTABLE " +
            "       WHERE nature = 'COT_PM' " +
            "       AND SOLDE > 0 " +
            "   ) " +
            ") " +
            "AND IDENTITE_FK IN " +
            "( " +
            "   SELECT IDENTITE_IK " +
            "   FROM NASCA.IDENTITE " +
            "   WHERE IDENTITE_IK IN " +
            "   ( " +
            "       SELECT DEBITEUR_FK " +
            "       FROM PROCEDURE " +
            "       WHERE TYPE_PROCEDURE NOT LIKE 'FAI' " +
            "   ) " +
            ") FETCH FIRST 15 ROWS ONLY ";

    public static final String SELECT_PM_BEC_FAILLITE_SS_SOLDE =  "SELECT " +
            "a.NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT a, NASCA.IDENTITE b " +
            "WHERE a.IDENTITE_FK = b.IDENTITE_IK " +
            "AND a.TYPE_IDENTIFIANT = 'BCE' " +
            "AND b.IDENTITE_IK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER " +
            "   WHERE DOSSIER_IK IN " +
            "   ( " +
            "       SELECT " +
            "       DOSSIER_FK " +
            "       FROM NASCA.PERIODE_AFFILIATION_PM " +
            "       WHERE STATUT = 'ACTIF' " +
            "       AND DT_DEBUT_PERIODE > '2013-01-01' " +
            "       AND DT_FIN_PERIODE IS NULL " +
            "   ) " +
            "   AND DOSSIER_IK NOT IN " +
            "   ( " +
            "       SELECT " +
            "       dossier_FK " +
            "       FROM NASCA.ENTITE_COMPTABLE " +
            "       WHERE nature = 'COT_PM' " +
            "       AND SOLDE > 0 " +
            "   ) " +
            ") " +
            "AND IDENTITE_FK IN " +
            "( " +
            "   SELECT IDENTITE_IK " +
            "   FROM NASCA.IDENTITE " +
            "   WHERE IDENTITE_IK IN " +
            "   ( " +
            "       SELECT DEBITEUR_FK " +
            "       FROM PROCEDURE " +
            "       WHERE TYPE_PROCEDURE NOT LIKE 'FAI' " +
            "   ) " +
            ") FETCH FIRST 15 ROWS ONLY ";

    public static final String SELECT_PP_REGULE = "SELECT " +
            "NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT, NASCA.IDENTITE " +
            "WHERE IDENTITE_FK = IDENTITE_IK " +
            "AND TYPE_IDENTIFIANT = 'NISS' " +
            "AND IDENTITE_IK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER " +
            "   WHERE DOSSIER_IK IN " +
            "   ( " +
            "      SELECT " +
            "      DOSSIER_FK " +
            "      FROM NASCA.EVENEMENT " +
            "      WHERE TYPE_EVENEMENT = 'DAC' " +
            "      AND NATURE_COTISANTE_FK = ? " +
            "      AND DT_DEBUT_PERIODE <= '2015-01-01' " +
            "      AND DT_FIN_PERIODE IS NULL " +
            "      AND TYPE_REDUC_COTISATION IS NULL " +
            "      AND STATUT_EVENEMENT = 'ACT' " +
            "      AND HIS_STATUT_RECORD = 'A' " +
            "   ) " +
            "   AND EXISTS " +
            "   ( " +
            "      SELECT " +
            "      * " +
            "      FROM nasca.ENTITE_COMPTABLE ent " +
            "      where ent.DOSSIER_FK = DOSSIER_IK " +
            "      and periode < '2015' " +
            "      and NATURE = 'COT_PP' " +
            "     AND SOLDE > 10 " +
            "   )" +
            ")FETCH FIRST 10 ROWS ONLY ";

    public static final String SELECT_PM_REGULE = " SELECT " +
            "NUM_IDENTIFIANT " +
            "FROM NASCA.IDENTIFIANT, NASCA.IDENTITE " +
            "WHERE IDENTITE_FK = IDENTITE_IK " +
            "AND TYPE_IDENTIFIANT = 'BCE' " +
            "AND IDENTITE_IK IN " +
            "( " +
            "   SELECT " +
            "   IDENTITE_FK " +
            "   FROM NASCA.DOSSIER " +
            "   WHERE DOSSIER_IK IN " +
            "   ( " +
            "      SELECT DOSSIER_FK " +
            "       FROM NASCA.PERIODE_AFFILIATION_PM " +
            "       WHERE STATUT = 'ACTIF' " +
            "        AND DT_DEBUT_PERIODE < '2015-01-01' " +
            "        AND DT_FIN_PERIODE IS NULL " +
            "        UNION " +
            "        SELECT DOSSIER_FK " +
            "        FROM NASCA.EVENEMENT " +
            "        WHERE TYPE_EVENEMENT ='DAC' " +
            "   ) " +
            "   AND EXISTS " +
            "   ( " +
            "      SELECT " +
            "      * " +
            "      FROM nasca.ENTITE_COMPTABLE ent " +
            "      where ent.DOSSIER_FK = DOSSIER_IK " +
            "      and periode < '2015' " +
            "      and NATURE = 'COT_PM' " +
            "     AND SOLDE > 10 " +
            "   ) " +
            ")FETCH FIRST 10 ROWS ONLY ";
}