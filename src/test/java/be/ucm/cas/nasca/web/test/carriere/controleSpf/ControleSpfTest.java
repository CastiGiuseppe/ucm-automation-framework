package be.ucm.cas.nasca.web.test.carriere.controleSpf;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;


/**
 * Created by sylvie.degraef on 7/06/2017.
 */
public class ControleSpfTest extends ControleSpfTestBase {
    private final String fileName = Thread.currentThread().getStackTrace()[1].getFileName();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Carriere.xlsx", "ControleSpf", "ControleSpfCarriere", "ControleSpf", null);
    }

    @Test(dataProvider = "data")
    public void controleSpfCarriere(String id,String niss, String annee,String anneeTrimestre,String anneeTrimestre2){

        ControleSpfTestBase.controleSpfCarriere(niss,annee,anneeTrimestre,anneeTrimestre2);

        finishTestExecution();
    }


}

