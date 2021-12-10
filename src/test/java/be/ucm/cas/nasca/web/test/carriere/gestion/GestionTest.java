package be.ucm.cas.nasca.web.test.carriere.gestion;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;


/**
 * Created by sylvie.degraef on 7/06/2017.
 */
public class GestionTest extends GestionTestBase {
    private final String fileName = Thread.currentThread().getStackTrace()[1].getFileName();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Carriere.xlsx", "Gestion", "GestionCarriere", "Gestion", null);
    }

    @Test(dataProvider = "data")

    public void gestioncarriere(String id,String niss, String anneeTrimestre,String annee,String anneeReference,String anneeTrimestre2){

        GestionTestBase.gestioncarriereenactivite(niss,anneeTrimestre,annee,anneeReference,anneeTrimestre2);

        finishTestExecution();
    }


}

