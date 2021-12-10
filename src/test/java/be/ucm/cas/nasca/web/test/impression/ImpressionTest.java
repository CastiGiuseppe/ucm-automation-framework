package be.ucm.cas.nasca.web.test.impression;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Date;

public class ImpressionTest extends ImpressionAbstract {

    private String documentId = null;

    private String numeroDossier = null;

    private String niss = null;

    private String nomDocument = null;

    @BeforeTest
    public void findDocument() {
        documentId = DaoFunctionality.getNascaDao().getDocumentImpression();

        numeroDossier = DaoFunctionality.getNascaDao().getDocumentImpressionIdentifiant(documentId, "NIDENT");

        niss = DaoFunctionality.getNascaDao().getDocumentImpressionIdentifiant(documentId, "NISS");

        nomDocument = DaoFunctionality.getNascaDao().getNomDocumentImpression(documentId);

        DaoFunctionality.getNascaDao().updateTypeDocumentImpression(documentId);
    }

    @Test
    public void filtreParNumeroClient() {
        doCheckImpression("Impression : filtre par numero de client", Thread.currentThread().getStackTrace()[1].getFileName(), numeroDossier, TestData.TYPE_NUM_CLIENT, null, null,
                null, null, nomDocument, null);
    }

    @Test
    public void filtreParNiss() {
        doCheckImpression("Impression : filtre par NISS", Thread.currentThread().getStackTrace()[1].getFileName(), niss, TestData.TYPE_NISS, null, null,
                null, null, nomDocument, null);
    }

    @Test
    public void filtreParNumeroDocument() {
        doCheckImpression("Impression : filtre par numero de document", Thread.currentThread().getStackTrace()[1].getFileName(), documentId, TestData.TYPE_DOCUMENT, null, null,
                null, null, nomDocument, null);
    }

    @Test
    public void filtreParTypeSortieLocale() {
        DaoFunctionality.getNascaDao().updateTypeSortieDocumentImpression("LOCALE", documentId);

        doCheckImpression("Impression : filtre par type sortie Locale", Thread.currentThread().getStackTrace()[1].getFileName(), documentId, TestData.TYPE_DOCUMENT, null, null,
                TestData.TYPE_SORTIE_LOCALE, null, nomDocument, null);
    }

    @Test
    public void filtreParTypeSortieCentrale() {
        DaoFunctionality.getNascaDao().updateTypeSortieDocumentImpression("CENTRALE", documentId);

        doCheckImpression("Impression : filtre par type sortie Centrale", Thread.currentThread().getStackTrace()[1].getFileName(), documentId, TestData.TYPE_DOCUMENT, null, null,
                TestData.TYPE_SORTIE_CENTRALE, null, nomDocument, null);
    }

    @Test
    public void filtreParTypeSortieExterne() {
        DaoFunctionality.getNascaDao().updateTypeSortieDocumentImpression("EXTERNE", documentId);

        doCheckImpression("Impression : filtre par type sortie Externe", Thread.currentThread().getStackTrace()[1].getFileName(), documentId, TestData.TYPE_DOCUMENT, null, null,
                TestData.TYPE_SORTIE_EXTERNE, null, nomDocument, null);
    }

    @Test
    public void filtreParTypeSortieEmail() {
        DaoFunctionality.getNascaDao().updateTypeSortieDocumentImpression("MAIL", documentId);

        doCheckImpression("Impression : filtre par type sortie Email", Thread.currentThread().getStackTrace()[1].getFileName(), documentId, TestData.TYPE_DOCUMENT, null, null,
                TestData.TYPE_SORTIE_EMAIL, null, nomDocument, null);
    }

    @Test
    public void filtreParTypeSortieFax() {
        DaoFunctionality.getNascaDao().updateTypeSortieDocumentImpression("FAX", documentId);

        doCheckImpression("Impression : filtre par type sortie Fax", Thread.currentThread().getStackTrace()[1].getFileName(), documentId, TestData.TYPE_DOCUMENT, null, null,
                TestData.TYPE_SORTIE_FAX, null, nomDocument, null);
    }

    @Test
    public void filtreParEtatAImprimer() {
        DaoFunctionality.getNascaDao().updateEtatDocumentImpression("A_IMPRIMER", documentId);

        doCheckImpression("Impression : filtre par etat A Imprimer", Thread.currentThread().getStackTrace()[1].getFileName(), documentId, TestData.TYPE_DOCUMENT, null, null,
                null, TestData.ETAT_A_IMPRIMER, nomDocument, null);
    }

    @Test
    public void filtreParEtatImprime() {
        DaoFunctionality.getNascaDao().updateEtatDocumentImpression("IMPRIME", documentId);

        doCheckImpression("Impression : filtre par etat Imprime", Thread.currentThread().getStackTrace()[1].getFileName(), documentId, TestData.TYPE_DOCUMENT, null, null,
                null, TestData.ETAT_IMPRIME, nomDocument, null);
    }

    @Test
    public void filtreParEtatSuspendu() {
        DaoFunctionality.getNascaDao().updateEtatDocumentImpression("AV_SUSPEN", documentId);

        doCheckImpression("Impression : filtre par etat Suspendu", Thread.currentThread().getStackTrace()[1].getFileName(), documentId, TestData.TYPE_DOCUMENT, null, null,
                null, TestData.ETAT_SUSPENDU, nomDocument, null);
    }

    @Test
    public void filtreParEtatAnnule() {
        DaoFunctionality.getNascaDao().updateEtatDocumentImpression("ANNULE", documentId);

        DaoFunctionality.getNascaDao().updateAnnulationDocumentImpression(DateUtils.doFormat(TestData.DATE_FORMAT_XML_WITH_HOUR, new Date()), "1", documentId);

        doCheckImpression("Impression : filtre par etat Annule", Thread.currentThread().getStackTrace()[1].getFileName(), documentId, TestData.TYPE_DOCUMENT, null, null,
                null, TestData.ETAT_ANNULE, nomDocument, null);

        DaoFunctionality.getNascaDao().updateNullAnnulationDocumentImpression(documentId);
    }

    @Test
    public void filtreParEtatErreur() {
        DaoFunctionality.getNascaDao().updateEtatDocumentImpression("ERREUR", documentId);

        doCheckImpression("Impression : filtre par etat Erreur", Thread.currentThread().getStackTrace()[1].getFileName(), documentId, TestData.TYPE_DOCUMENT, null, null,
                null, TestData.ETAT_ERREUR, nomDocument, null);
    }
}