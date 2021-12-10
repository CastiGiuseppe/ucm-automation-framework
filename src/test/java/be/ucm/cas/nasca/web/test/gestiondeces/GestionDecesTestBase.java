package be.ucm.cas.nasca.web.test.gestiondeces;

import be.ucm.cas.nasca.web.test.support.*;

public class GestionDecesTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    static void injectInastiFlux(String niss) {
        try {
            DaoFunctionality.getNascaDao().prepareFluxInInjection(niss);
            BatchUtils.doSendFlatFileL010InastiBatch(niss);
            RunBatch.runBatchExtractFluxFromFile();
            RunBatch.runBatchAdaptInastiToNasca();

            logSuccess("Flux injection deces OK pour " + niss);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    public static void doCheckDeces(String niss) {
        try {
            loadNissOrBce(niss);

            if (gestionClientPage.isDecede()) {
                logSuccess("Affilie decede pour " + niss);
            } else {
                logFailed("Affilie pas decede pour " + niss);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }
}