package be.ucm.cas.nasca.web.test.affiliation.pp;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class AffiliationPpAccepteeTardive extends AffiliationPpAbstract {

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Affiliation.xlsx", "Affi PP", "Affiliation PP", "accepttard", null);
    }

    @Test(dataProvider = "data")
    public void AffiliationPpTest(String id, String libelle, String nature, String dpe, String dsg, String suite, String revenu) {
        initTest("Sc." + id + " - " + libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        affiliationPpTardive(nature, dpe, dsg, suite, revenu);

        finishTestExecution();
    }
}