package be.ucm.cas.nasca.web.test.solidarite;

import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

public class SolidariteTest extends SolidariteTestBase {

    private static final Map<Integer, List<String>> mapRevision = new LinkedHashMap<>();

    private String bceCodebiteur;
    private String bceDebiteur;
    private String nissCodebiteur;
    private String nissDebiteur;

    private List<String> numeros = null;

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Solidarité.xlsx", "Solidarité", "Solidarité", "Solidarite", null);
    }

    @Test(dataProvider = "data")
    public void solidariteFromExcel(String id, String libelle, String revision,
                                    String action, String typeDebiteur, String typeCodebiteur,
                                    String nouveauCodebiteur, String raisonNonSolidarite,
                                    String trimestreDebut, String dateEffetDebut, String trimestreFin,
                                    String dateEffetFin, String skip) {

        if (!Boolean.valueOf(skip)) {
            initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

            loginNasca();

            if (TestData.TYPE_PP.equals(typeDebiteur) && TestData.TYPE_PP.equals(typeCodebiteur)) {
                List<String> list = DaoFunctionality.getNascaDao().getNissForSolidariteDebiteurCodebiteurPP();
                nissDebiteur = list.get(0);
                nissCodebiteur = list.get(1);
                bceDebiteur = null;
                bceCodebiteur = null;
                if (revision == null) {
                    numeros = new ArrayList<>();
                    numeros.add(nissDebiteur);
                    numeros.add(nissCodebiteur);
                }
            } else if (TestData.TYPE_PP.equals(typeDebiteur) && TestData.TYPE_PM.equals(typeCodebiteur)) {
                nissDebiteur = DaoFunctionality.getNascaDao().getNissForSolidaritePP();
                bceCodebiteur = DaoFunctionality.getNascaDao().getBceForSolidaritePM();
                bceDebiteur = null;
                nissCodebiteur = null;
                if (revision == null) {
                    numeros = new ArrayList<>();
                    numeros.add(nissDebiteur);
                    numeros.add(bceCodebiteur);
                }
            } else if (TestData.TYPE_PM.equals(typeDebiteur) && TestData.TYPE_PP.equals(typeCodebiteur)) {
                bceDebiteur = DaoFunctionality.getNascaDao().getBceForSolidaritePM();
                nissCodebiteur = DaoFunctionality.getNascaDao().getNissForSolidaritePP();
                bceCodebiteur = null;
                nissDebiteur = null;
                if (revision == null) {
                    numeros = new ArrayList<>();
                    numeros.add(bceDebiteur);
                    numeros.add(nissCodebiteur);
                }
            } else {
                List<String> list = DaoFunctionality.getNascaDao().getBceForSolidariteDebiteurCodebiteurPM();
                bceDebiteur = list.get(0);
                bceCodebiteur = list.get(1);
                nissDebiteur = null;
                nissCodebiteur = null;
                if (revision == null) {
                    numeros = new ArrayList<>();
                    numeros.add(bceDebiteur);
                    numeros.add(bceCodebiteur);
                }
            }

            switch (action) {
                case "Ajout":
                    ajoutSolidarite(id, typeCodebiteur, nouveauCodebiteur, raisonNonSolidarite, trimestreDebut, dateEffetDebut, trimestreFin, dateEffetFin);
                    break;
                case "Modification":
                    modificationSolidarite(revision, typeDebiteur, typeCodebiteur, raisonNonSolidarite, trimestreDebut, dateEffetDebut, trimestreFin, dateEffetFin);
                    break;
                case "Envoi Courrier":
                    envoiCourrier(revision, typeDebiteur, typeCodebiteur);
                    break;
                default:
                    break;
            }

            finishTestExecution();
        }
    }

    private void envoiCourrier(String revision, String typeDebiteur, String typeCodebiteur) {
        List<String> listRevisionCourrier = mapRevision.get(Integer.valueOf(revision));
        if (listRevisionCourrier != null) {
            //set de l'identifiant en fonction du type PP/PM
            setIdentifiantCodebiteur(typeCodebiteur, listRevisionCourrier);
            setIdentifiantDebiteur(typeDebiteur, listRevisionCourrier);
            SolidariteTestBase.creationCourrierSolidaire(nissDebiteur, nissCodebiteur, bceDebiteur, bceCodebiteur);
            String dateDocument = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date());
            //TODO vérifier le nom du document
            if (nissDebiteur != null) {
                ImpressionTestBase.checkImpressionDernierDocument(nissDebiteur, TestData.TYPE_NISS, dateDocument, null, null, TestData.ETAT_A_IMPRIMER, "All - Solidarité");
            } else {
                ImpressionTestBase.checkImpressionDernierDocument(bceDebiteur, TestData.TYPE_BCE, dateDocument, null, null, TestData.ETAT_A_IMPRIMER, "All - Solidarité");
            }
        } else {
            logSkip(Thread.currentThread().getStackTrace()[1].getFileName(), "Test precedent en erreur");
        }
    }

    private void modificationSolidarite(String revision, String typeDebiteur, String typeCodebiteur, String raisonNonSolidarite, String trimestreDebut, String dateEffetDebut, String trimestreFin, String dateEffetFin) {
        List<String> listRevisionModification = mapRevision.get(Integer.valueOf(revision));
        if (listRevisionModification != null) {
            //set de l'identifiant en fonction du type PP/PM
            setIdentifiantCodebiteur(typeCodebiteur, listRevisionModification);
            setIdentifiantDebiteur(typeDebiteur, listRevisionModification);
            editionLienSolidarite(nissDebiteur, nissCodebiteur, bceDebiteur,
                    bceCodebiteur, raisonNonSolidarite,
                    trimestreDebut != null ? Boolean.valueOf(trimestreDebut) : null,
                    dateEffetDebut != null ? Boolean.valueOf(dateEffetDebut) : null,
                    trimestreFin != null ? Boolean.valueOf(trimestreFin) : null,
                    dateEffetFin != null ? Boolean.valueOf(dateEffetFin) : null);
        } else {
            logSkip(Thread.currentThread().getStackTrace()[1].getFileName(), "Test precedent en erreur");
        }
    }

    private void ajoutSolidarite(String id, String typeCodebiteur, String nouveauCodebiteur, String raisonNonSolidarite, String trimestreDebut, String dateEffetDebut, String trimestreFin, String dateEffetFin) {
        if (Boolean.valueOf(nouveauCodebiteur)) {
            cleanDb();
        }
        ajoutLienSolidarite(nissDebiteur, nissCodebiteur, bceDebiteur,
                bceCodebiteur, Boolean.valueOf(nouveauCodebiteur),
                raisonNonSolidarite, Boolean.valueOf(trimestreDebut),
                Boolean.valueOf(dateEffetDebut),
                Boolean.valueOf(trimestreFin),
                Boolean.valueOf(dateEffetFin), typeCodebiteur);
        mapRevision.put(Integer.valueOf(id), numeros);
        cleanDb();
    }

    private void setIdentifiantCodebiteur(String typeCodebiteur, List<String> listRevisionCourrier) {
        if (TestData.TYPE_PP.equals(typeCodebiteur)) {
            setNissCodebiteur(listRevisionCourrier.get(1));
            setBceCodebiteur(null);
        } else {
            setNissCodebiteur(listRevisionCourrier.get(1));
            setBceCodebiteur(null);
        }
    }

    private void setIdentifiantDebiteur(String typeDebiteur, List<String> listRevisionCourrier) {
        if (TestData.TYPE_PP.equals(typeDebiteur)) {
            setNissDebiteur(listRevisionCourrier.get(0));
            setBceDebiteur(null);
        } else {
            setNissDebiteur(listRevisionCourrier.get(0));
            setBceDebiteur(null);
        }
    }

    private void cleanDb() {
        try {
            DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER);
            DaoFunctionality.cleanAllDataByIdentite(TestData.BCE_NUMBER);
        } catch (Exception e) {
            Assert.fail("Test Failed (clean db)", e);
        }
    }

    private void ajoutLienSolidarite(String nissDebiteur,
                                     String nissCodebiteur, String bceDebiteur, String bceCodebiteur,
                                     boolean isNouveauCodebiteur, String raisonNonSolidarite,
                                     Boolean isTrimestreDebut, Boolean isDateEffetDebut,
                                     Boolean isTrimestreFin, Boolean isDateEffetFin, String typeCodebiteur) {
        Date dateJour = new Date();
        if (raisonNonSolidarite != null && "LRS partielle".equals(raisonNonSolidarite)) {
            Calendar cal = Calendar.getInstance();

            int diff = cal.get(Calendar.YEAR) - 2014;
            cal.add(Calendar.YEAR, -diff);
            dateJour = cal.getTime();
        }

        String trimestreDebut = null;
        if (isTrimestreDebut) {
            trimestreDebut = DateQuarters.getQuarterCurrent(dateJour);
        }
        String trimestreFin = null;
        if (isTrimestreFin) {
            trimestreFin = DateQuarters.getQuarterCurrent(dateJour);
        }
        String dateDebutPriseEffet = null;
        if (isDateEffetDebut) {
            dateDebutPriseEffet = SeleniumUtils.deleteFormat(DateUtils.getFirstDateofQuarter(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, dateJour)));
        }
        String dateFinPriseEffet = null;
        if (isDateEffetFin) {
            dateFinPriseEffet = SeleniumUtils.deleteFormat(DateUtils.getLastDateofQuarter(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, dateJour)));
        }
        SolidariteTestBase.addLienSolidaire(nissDebiteur, nissCodebiteur,
                bceDebiteur, bceCodebiteur, isNouveauCodebiteur,
                raisonNonSolidarite, trimestreDebut, trimestreFin,
                dateDebutPriseEffet, dateFinPriseEffet, typeCodebiteur);
        if (!isNouveauCodebiteur) {
            SolidariteTestBase.checkSolidaireDe(nissDebiteur, nissCodebiteur, bceDebiteur, bceCodebiteur, raisonNonSolidarite != null, raisonNonSolidarite);
        }
    }

    private void editionLienSolidarite(String nissDebiteur,
                                       String nissCodebiteur, String bceDebiteur, String bceCodebiteur,
                                       String raisonNonSolidarite, Boolean isTrimestreDebut,
                                       Boolean isDateEffetDebut, Boolean isTrimestreFin,
                                       Boolean isDateEffetFin) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 3);
        Date dateJour = cal.getTime();
        if (raisonNonSolidarite != null && "LRS partielle".equals(raisonNonSolidarite)) {
            cal = Calendar.getInstance();

            int diff = cal.get(Calendar.YEAR) - 2014;
            cal.add(Calendar.YEAR, -diff);
            dateJour = cal.getTime();
        }

        String trimestreDebut = null;
        if (isTrimestreDebut != null) {
            if (isTrimestreDebut) {
                trimestreDebut = DateQuarters.getQuarterCurrent(dateJour);
            } else {
                trimestreDebut = "";
            }
        }

        String trimestreFin = null;
        if (isTrimestreFin != null) {
            if (isTrimestreFin) {
                trimestreFin = DateQuarters.getQuarterCurrent(dateJour);
            } else {
                trimestreFin = "";
            }
        }

        String dateDebutPriseEffet = null;
        if (isDateEffetDebut != null) {
            if (isDateEffetDebut) {
                dateDebutPriseEffet = SeleniumUtils.deleteFormat(DateUtils.getFirstDateofQuarter(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, dateJour)));
            } else {
                dateDebutPriseEffet = "";
            }
        }

        String dateFinPriseEffet = null;
        if (isDateEffetFin != null) {
            if (isDateEffetFin) {
                dateFinPriseEffet = SeleniumUtils.deleteFormat(DateUtils.getLastDateofQuarter(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, dateJour)));
            } else {
                dateFinPriseEffet = "";
            }
        }

        SolidariteTestBase.editLienSolidaire(nissDebiteur, nissCodebiteur,
                bceDebiteur, bceCodebiteur, raisonNonSolidarite,
                trimestreDebut, trimestreFin, dateDebutPriseEffet,
                dateFinPriseEffet);
    }

    private void setBceCodebiteur(String bceCodebiteur) {
        this.bceCodebiteur = bceCodebiteur;
    }

    private void setNissCodebiteur(String nissCodebiteur) {
        this.nissCodebiteur = nissCodebiteur;
    }

    private void setBceDebiteur(String bceDebiteur) {
        this.bceDebiteur = bceDebiteur;
    }

    private void setNissDebiteur(String nissDebiteur) {
        this.nissDebiteur = nissDebiteur;
    }
}