package be.ucm.cas.nasca.web.test.impression;

import java.util.Map;

public class ImpressionAbstract extends ImpressionTestBase {

    void doCheckImpression(String libelle, String fileName, String id, String typeNumero, String dateDebut, String dateFin,
                           String typeSortie, String etatDocument, String nomDocument, Map<Integer, String> elements) {
        initTest(libelle, fileName);

        loginNasca();

        checkImpression(id, typeNumero, dateDebut, dateFin, typeSortie, etatDocument, nomDocument, elements);

        finishTestExecution();
    }
}