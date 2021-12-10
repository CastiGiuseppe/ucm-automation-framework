package be.ucm.cas.nasca.web.test.impression;

import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.PdfFileUtils;
import be.ucm.cas.nasca.web.test.support.TestBase;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class ImpressionTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    public static void checkImpression(String nissorbcenumber, String typeNumero, String dateDebut, String dateFin, String typeSortie, String etatDocument, String nomDocument, Map<Integer, String> elements) {
        try {
            rechercherDocument(nissorbcenumber, typeNumero, dateDebut, dateFin, typeSortie, etatDocument);

            logInfo("Recherche document d'impression " + nissorbcenumber, "");

            if (multiDossiersPage.isExistOuPreviewDocument(nomDocument, false)) {
                String reference = multiDossiersPage.getReferenceDocument(nomDocument);

                logSuccess("Le document " + nomDocument + " est présent pour " + nissorbcenumber, reference);

                if (elements != null) {
                    multiDossiersPage.isExistOuPreviewDocument(nomDocument, true);
                    previewDocument(nissorbcenumber, nomDocument, elements);
                }
            } else {
                logFailed("Le document " + nomDocument + " n'est pas présent pour " + nissorbcenumber, "Impression KO");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + nissorbcenumber, e);
        }
    }

    private static void previewDocument(String nissorbcenumber, String nomDocument, Map<Integer, String> elements) {
        if (multiDossiersPage.isPreviewDisplayed()) {
            logInfo("Affichage de la preview pour le document " + nomDocument + " de " + nissorbcenumber, "");

            for (Map.Entry<Integer, String> entry : elements.entrySet()) {
                String element = entry.getValue();

                PdfFileUtils.verifyPDFContent(multiDossiersPage.getPreviewDocumentAttributSrc(), element);
            }
            multiDossiersPage.clickBtnPreviewFermer();
        } else {
            logFailed("Problème lors de la preview du document d'impression pour " + nissorbcenumber);
        }
    }

    public static void checkImpressionDernierDocument(String nissorbcenumber, String typeNumero, String dateDebut, String dateFin, String typeSortie, String etatDocument, String nomDocument) {
        try {
            rechercherDocument(nissorbcenumber, typeNumero, dateDebut, dateFin, typeSortie, etatDocument);

            logInfo("Recherche document d'impression " + nissorbcenumber, "");

            if (multiDossiersPage.isExistDernierDocument(nomDocument)) {
                logSuccess("Le document " + nomDocument + " est présent pour " + nissorbcenumber, "Impression OK");

                multiDossiersPage.clickPreviewDernierDocument(nomDocument);

                if (multiDossiersPage.isPreviewDisplayed()) {
                    logInfo("Affichage de la preview pour le document " + nomDocument + " de " + nissorbcenumber, "");
                    multiDossiersPage.clickBtnPreviewFermer();
                } else {
                    logFailed("Problème lors de la preview du document d'impression pour " + nissorbcenumber);
                }
            } else {
                logFailed("Le document " + nomDocument + " n'est pas présent pour " + nissorbcenumber, "Impression KO");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + nissorbcenumber, e);
        }
    }

    public static void checkAucunImpressionAImprimer(String nissorbcenumber, String typeNumero, String dateDebut, String dateFin, String typeSortie, String etatDocument, String nomDocument) {
        try {
            rechercherDocument(nissorbcenumber, typeNumero, dateDebut, dateFin, typeSortie, etatDocument);

            multiDossiersPage.isExistOuPreviewDocument(nomDocument, true);

            if (multiDossiersPage.isPreviewDisplayed()) {
                multiDossiersPage.clickBtnPreviewFermer();
                logFailed("Le document " + nomDocument + " est présent pour " + nissorbcenumber, "Impression KO");
            } else {
                logSuccess("Le document " + nomDocument + " n'est pas présent pour " + nissorbcenumber, "Impression OK");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + nissorbcenumber, e);
        }
    }

    private static void rechercherDocument(String nissorbcenumber, String typeNumero, String dateDebut, String dateFin, String typeSortie, String etatDocument) {
        homeMenuPage.clickOngletMultiDossiers();

        multiDossiersPage.selectTypeNumero(typeNumero);
        multiDossiersPage.fillNumero(nissorbcenumber);
        if (dateDebut != null && !StringUtils.isEmpty(dateDebut.trim())) {
            multiDossiersPage.fillDateDebutCreationDocument(dateDebut);
        }
        if (dateFin != null && !StringUtils.isEmpty(dateFin.trim())) {
            multiDossiersPage.fillDateFinCreationDocument(dateFin);
        }
        if (typeSortie != null && !StringUtils.isEmpty(typeSortie.trim())) {
            multiDossiersPage.selectTypeSortie(typeSortie);
        } else {
            multiDossiersPage.clickBtnEmptyTypeSortie();
        }
        if (etatDocument != null && !StringUtils.isEmpty(etatDocument.trim())) {
            multiDossiersPage.selectEtatDocument(etatDocument);
        } else {
            multiDossiersPage.clickBtnEmptyEtatDocument();
        }

        multiDossiersPage.clickBtnRechercherDocument();
    }
}