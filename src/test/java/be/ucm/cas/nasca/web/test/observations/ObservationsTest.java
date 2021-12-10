package be.ucm.cas.nasca.web.test.observations;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

public class ObservationsTest extends ObservationsTestBase {

    private static final Map<Integer, String> mapRevision = new LinkedHashMap<>();

    private static List<String> listNiss = new ArrayList<>();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Observation.xlsx", "Observations", "Observations", "Observation", null);
    }

    @Test(dataProvider = "data")
    public void observationFromExcel(String id, String libelle,
                                     String revision, String action, String typeMedia,
                                     String type, String composant) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        String niss = null;
        if (revision == null || StringUtils.isEmpty(revision.trim())) {
            if (listNiss.isEmpty()) {
                listNiss = DaoFunctionality.getNascaDao().getNissObservations();
            }
            niss = listNiss.get(0);
            listNiss.remove(0);
        }

        switch (action) {
            case "Ajout":
                ajoutObservation(id, typeMedia, type, composant, niss);
                break;
            case "Modification":
                niss = mapRevision.get(Integer.valueOf(revision));
                ObservationsTestBase.editionObservation(niss, typeMedia, type);
                break;
            case "Archivage":
                niss = mapRevision.get(Integer.valueOf(revision));
                ObservationsTestBase.archivageObservation(niss, typeMedia, type);
                break;
            default:
                break;
        }

        finishTestExecution();
    }

    private void ajoutObservation(String id, String typeMedia, String type, String composant, String niss) {
        mapRevision.put(Integer.valueOf(id), niss);
        switch (composant) {
            case "Observations":
                ObservationsTestBase.creationObservation(niss, typeMedia, type);
                break;
            case "Cessation":
                ObservationsTestBase.creationObservationCessation(niss, typeMedia);
                ObservationsTestBase.checkObservation(niss, typeMedia, type);
                break;
            default:
                break;
        }
    }
}