package be.ucm.cas.nasca.web.test.carriere.consultation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;


/**
 * Created by sylvie.degraef on 7/06/2017.
 */
public class ConsultationTest extends ConsultationTestBase {
    private final String fileName = Thread.currentThread().getStackTrace()[1].getFileName();


    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Carriere.xlsx", "Consultation", "ConsultationCarriere", "Consultation", null);
    }

    @Test(dataProvider = "data")
    public void consultationprincipalenactivite(String id,String niss,String type){

        ConsultationTestBase.consultationcarriereenactivite(niss);
        if ("Compl".equals(type)) {
            ConsultationTestBase.consultationcarrieresuite();
        }

        finishTestExecution();
    }




}

