package be.ucm.cas.nasca.web.test.revenu;

import be.ucm.cas.nasca.web.test.carriere.CarriereTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public abstract class RevenuAbstract extends RevenuTestBase {

    class RevenuPojo {
        String libelle;
        String type;
        String regime;
        String nature;
        String dateDebut;
        String typeRevenuDepart;
        String statutRevenuDepart;
        String revenuDepart;
        String typeRevenu;
        String revenu;
        String sourceRevenu;
        String statutRevenu;
        String anneeRevenu;
        String faireRegul;
        String courrier;
        String regulCalculee;
        String erreur;
        String anneeCotisation;
        String montantRegul;
        String codeActivite;
        String codeProvisoire;
        String skip;

        public String getLibelle() {
            return libelle;
        }

        public void setLibelle(String libelle) {
            this.libelle = libelle;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRegime() {
            return regime;
        }

        public void setRegime(String regime) {
            this.regime = regime;
        }

        public String getNature() {
            return nature;
        }

        public void setNature(String nature) {
            this.nature = nature;
        }

        public String getDateDebut() {
            return dateDebut;
        }

        public void setDateDebut(String dateDebut) {
            this.dateDebut = dateDebut;
        }

        public String getTypeRevenuDepart() {
            return typeRevenuDepart;
        }

        public void setTypeRevenuDepart(String typeRevenuDepart) {
            this.typeRevenuDepart = typeRevenuDepart;
        }

        public String getStatutRevenuDepart() {
            return statutRevenuDepart;
        }

        public void setStatutRevenuDepart(String statutRevenuDepart) {
            this.statutRevenuDepart = statutRevenuDepart;
        }

        public String getRevenuDepart() {
            return revenuDepart;
        }

        public void setRevenuDepart(String revenuDepart) {
            this.revenuDepart = revenuDepart;
        }

        public String getTypeRevenu() {
            return typeRevenu;
        }

        public void setTypeRevenu(String typeRevenu) {
            this.typeRevenu = typeRevenu;
        }

        public String getRevenu() {
            return revenu;
        }

        public void setRevenu(String revenu) {
            this.revenu = revenu;
        }

        public String getSourceRevenu() {
            return sourceRevenu;
        }

        public void setSourceRevenu(String sourceRevenu) {
            this.sourceRevenu = sourceRevenu;
        }

        public String getStatutRevenu() {
            return statutRevenu;
        }

        public void setStatutRevenu(String statutRevenu) {
            this.statutRevenu = statutRevenu;
        }

        public String getAnneeRevenu() {
            return anneeRevenu;
        }

        public void setAnneeRevenu(String anneeRevenu) {
            this.anneeRevenu = anneeRevenu;
        }

        public String getFaireRegul() {
            return faireRegul;
        }

        public void setFaireRegul(String faireRegul) {
            this.faireRegul = faireRegul;
        }

        public String getCourrier() {
            return courrier;
        }

        public void setCourrier(String courrier) {
            this.courrier = courrier;
        }

        public String getRegulCalculee() {
            return regulCalculee;
        }

        public void setRegulCalculee(String regulCalculee) {
            this.regulCalculee = regulCalculee;
        }

        public String getErreur() {
            return erreur;
        }

        public void setErreur(String erreur) {
            this.erreur = erreur;
        }

        public String getAnneeCotisation() {
            return anneeCotisation;
        }

        public void setAnneeCotisation(String anneeCotisation) {
            this.anneeCotisation = anneeCotisation;
        }

        public String getMontantRegul() {
            return montantRegul;
        }

        public void setMontantRegul(String montantRegul) {
            this.montantRegul = montantRegul;
        }

        public String getSkip() {
            return skip;
        }

        public void setSkip(String skip) {
            this.skip = skip;
        }

        public String getCodeActivite() {
            return codeActivite;
        }

        public void setCodeActivite(String codeActivite) {
            this.codeActivite = codeActivite;
        }

        public String getCodeProvisoire() {
            return codeProvisoire;
        }

        public void setCodeProvisoire(String codeProvisoire) {
            this.codeProvisoire = codeProvisoire;
        }
    }

    private RevenuPojo setRevenuPojo(String libelle, String type, String regime, String nature, String dateDebut, String typeRevenuDepart, String statutRevenuDepart, String revenuDepart, String typeRevenu, String revenu, String sourceRevenu, String statutRevenu, String anneeRevenu, String faireRegul, String courrier, String regulCalculee, String erreur, String anneeCotisation, String montantRegul, String codeActivite, String codeProvisoire, String skip) {
        RevenuPojo revenuPojo = new RevenuPojo();

        revenuPojo.setLibelle(libelle);
        revenuPojo.setType(type);
        revenuPojo.setRegime(regime);
        revenuPojo.setNature(nature);
        revenuPojo.setDateDebut(dateDebut);
        revenuPojo.setTypeRevenuDepart(typeRevenuDepart);
        revenuPojo.setStatutRevenuDepart(statutRevenuDepart);
        revenuPojo.setRevenuDepart(revenuDepart);
        revenuPojo.setTypeRevenu(typeRevenu);
        revenuPojo.setRevenu(revenu);
        revenuPojo.setSourceRevenu(sourceRevenu);
        revenuPojo.setStatutRevenu(statutRevenu);
        revenuPojo.setAnneeRevenu(anneeRevenu);
        revenuPojo.setFaireRegul(faireRegul);
        revenuPojo.setCourrier(courrier);
        revenuPojo.setRegulCalculee(regulCalculee);
        revenuPojo.setErreur(erreur);
        revenuPojo.setAnneeCotisation(anneeCotisation);
        revenuPojo.setMontantRegul(montantRegul);
        revenuPojo.setSkip(skip);
        revenuPojo.setCodeActivite(codeActivite);
        revenuPojo.setCodeProvisoire(codeProvisoire);

        return revenuPojo;
    }

    void testRevenu(String libelle, String type, String regime, String nature, String dateDebut, String typeRevenuDepart, String statutRevenuDepart, String revenuDepart, String typeRevenu, String revenu, String sourceRevenu, String statutRevenu, String anneeRevenu, String faireRegul, String courrier, String regulCalculee, String erreur, String anneeCotisation, String montantRegul, String codeActivite, String codeProvsoire, String skip, String fileName) {
        if (!Boolean.valueOf(skip)) {
            initTest(libelle, fileName);

            RevenuAbstract.RevenuPojo revenuPojo = setRevenuPojo(libelle, type, regime, nature, dateDebut, typeRevenuDepart,
                    statutRevenuDepart, revenuDepart, typeRevenu, revenu, sourceRevenu, statutRevenu, anneeRevenu,
                    faireRegul, courrier, regulCalculee, erreur, anneeCotisation, montantRegul, codeActivite, codeProvsoire, skip);

            revenuFromExcel(revenuPojo);

            finishTestExecution();
        }
    }

    private void revenuFromExcel(RevenuPojo revenuPojo) {
        loginNasca();

        String niss;
        if (revenuPojo.getTypeRevenuDepart() == null || StringUtils.isEmpty(revenuPojo.getTypeRevenuDepart().trim())) {
            if ("RDE".equals(revenuPojo.getRegime())) {
                niss = DaoFunctionality.getNascaDao().getNissSansRevenuRDE(revenuPojo.getNature(), revenuPojo.getDateDebut(), revenuPojo.getAnneeRevenu());
            } else {
                niss = DaoFunctionality.getNascaDao().getNissSansRevenuRDA(revenuPojo.getNature(), revenuPojo.getDateDebut(), revenuPojo.getAnneeRevenu());
            }
        } else {
            if ("RDE".equals(revenuPojo.getRegime())) {
                niss = DaoFunctionality.getNascaDao().getNissAvecRevenuRDE(revenuPojo.getNature(), revenuPojo.getDateDebut(), revenuPojo.getTypeRevenuDepart(), revenuPojo.getStatutRevenuDepart(), revenuPojo.getAnneeRevenu(), revenuPojo.getRevenuDepart(), DateUtils.doFormat(TestData.DATE_FORMAT_XML, new Date()));
            } else {
                niss = DaoFunctionality.getNascaDao().getNissAvecRevenuRDA(revenuPojo.getNature(), revenuPojo.getDateDebut(), revenuPojo.getTypeRevenuDepart(), revenuPojo.getStatutRevenuDepart(), revenuPojo.getAnneeRevenu(), revenuPojo.getRevenuDepart(), DateUtils.doFormat(TestData.DATE_FORMAT_XML, new Date()));
            }
        }

        if (niss != null) {
            String trimestre = revenuPojo.getAnneeCotisation() + "/1";

            switch (revenuPojo.getType()) {
                case "ajout":
                    ajoutRevenu(revenuPojo, niss, trimestre);
                    break;
                case "modification":
                    modificationRevenu(revenuPojo, niss, trimestre);
                    break;
                default:
                    break;
            }
        } else {
            logSkip(Thread.currentThread().getStackTrace()[1].getFileName(), TestData.PAS_CAS_TROUVE_DB);
        }
    }

    private void modificationRevenu(RevenuPojo revenuPojo, String niss, String trimestre) {
        if (Boolean.valueOf(revenuPojo.getErreur())) {
            RevenuTestBase.editerRevenuAvecErreur(niss, revenuPojo.getAnneeRevenu(), revenuPojo.getTypeRevenu(), revenuPojo.getStatutRevenu(), Boolean.valueOf(revenuPojo.getFaireRegul()));
        } else {
            boolean pasCalcul = RevenuTestBase.editerRevenuSansErreur(niss, revenuPojo.getAnneeRevenu(), revenuPojo.getTypeRevenu(), revenuPojo.getStatutRevenu(), Boolean.valueOf(revenuPojo.getFaireRegul()));

            if (!pasCalcul) {
                EnrolementPPTestBase.checkCreanceEtTypeOperationTrimestrePP(niss, trimestre, TestData.COTISATION, TestData.ENROLEMENT_ANNULATION, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()));
            } else {
                EnrolementPPTestBase.checkAucuneOperationTrimestrePP(niss, trimestre, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), TestData.ENROLEMENT, TestData.COTISATION_PP, TestData.PAS_DE_REGROUPEMENT);
            }
        }
    }

    private void ajoutRevenu(RevenuPojo revenuPojo, String niss, String trimestre) {
        if (Boolean.valueOf(revenuPojo.getErreur())) {
            RevenuTestBase.ajouterRevenuAvecErreur(niss, revenuPojo.getAnneeRevenu(), revenuPojo.getRevenu(), revenuPojo.getTypeRevenu(), revenuPojo.getStatutRevenu(), revenuPojo.getSourceRevenu(), Boolean.valueOf(revenuPojo.getFaireRegul()), DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()));
        } else {
            RevenuTestBase.ajouterRevenuSansErreur(niss, revenuPojo.getAnneeRevenu(), revenuPojo.getRevenu(), revenuPojo.getTypeRevenu(), revenuPojo.getStatutRevenu(), revenuPojo.getSourceRevenu(), Boolean.valueOf(revenuPojo.getFaireRegul()), DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), revenuPojo.getCourrier());

            if (Boolean.valueOf(revenuPojo.getRegulCalculee())) {
                String dossierIk = DaoFunctionality.getNascaDao().getDossierIkFromNiss(niss);

                String montantCible = DaoFunctionality.getNascaDao().getMontantCibleRegularisation(dossierIk, trimestre, DateUtils.doFormat(TestData.DATE_FORMAT_XML, new Date()));

                if (montantCible.equals(revenuPojo.getMontantRegul())) {
                    logSuccess("Montant regularisation attendu " + revenuPojo.getMontantRegul() + " OK");
                } else {
                    logFailed("Montant regularisation different (" + montantCible + ") du montant attendu (" + revenuPojo.getMontantRegul() + ") KO");
                }

                if (Boolean.valueOf(revenuPojo.getCourrier())) {
                    checkCourrier(revenuPojo, niss);
                }

                if (!StringUtils.isEmpty(revenuPojo.getCodeActivite().trim())) {
                    CarriereTestBase.checkCarriere(niss, "trimestre", "1", revenuPojo.getAnneeCotisation(), "4", revenuPojo.getCodeActivite(), revenuPojo.getRevenu(), revenuPojo.getCodeProvisoire(), revenuPojo.getAnneeRevenu(), "false", null);
                }
            } else {
                EnrolementPPTestBase.checkAucuneOperationTrimestrePP(niss, trimestre, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), TestData.ENROLEMENT, TestData.COTISATION_PP, TestData.PAS_DE_REGROUPEMENT);
            }
        }
    }

    private void checkCourrier(RevenuPojo revenuPojo, String niss) {
        if (TestData.TYPE_REVENU_PRESUME.equals(revenuPojo.getTypeRevenu())) {
            ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS,
                    DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null, TestData.ETAT_A_IMPRIMER,
                    "PP - Régularisation - Revenu Présumé", null);
        } else {
            ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS,
                    DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null, TestData.ETAT_A_IMPRIMER,
                    "PP - Régularisation - Débit/Crédit", null);
        }
    }
}