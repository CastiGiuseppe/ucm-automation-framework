package be.ucm.cas.nasca.web.test.profil.change;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class ProfilChangeMultipleSuppression3Test extends ProfilChangeAbstract {

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Profil.xlsx", "chang multiple - supprimer", "Changement multiple - supprimer", "Profil" + 3, null);
    }

    @Test(dataProvider = "data")
    public void changeMultipleSuppression(String id, String typeEvenement0,
                                          String codeNature0, String codeReduction0, String dateDeb0,
                                          String typeEvenement1, String codeNature1, String codeReduction1,
                                          String dateDeb1, String typeEvenement2, String codeNature2,
                                          String codeReduction2, String dateDeb2, String typeEvenement3,
                                          String codeNature3, String codeReduction3, String dateDeb3,
                                          String typeEvenement4, String codeNature4, String codeReduction4,
                                          String dateDeb4, String typeEvenement5, String codeNature5,
                                          String codeReduction5, String dateDeb5, String typeEvenement6,
                                          String codeNature6, String codeReduction6, String dateDeb6,
                                          String controleTrimestre1, String controleCotisation1,
                                          String controleCodenature1, String controleTrimestre2,
                                          String controleCotisation2, String controleCodenature2, String jour,
                                          String mois, String annee, String pivotdate, String baremepivot1,
                                          String baremepivot23, String libelle) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        changeMultipleSuppression(typeEvenement0, codeNature0, codeReduction0, dateDeb0, typeEvenement1, codeNature1,
                codeReduction1, dateDeb1, typeEvenement2, codeNature2, codeReduction2, dateDeb2, typeEvenement3,
                codeNature3, codeReduction3, dateDeb3, typeEvenement4, codeNature4, codeReduction4, dateDeb4,
                typeEvenement5, codeNature5, codeReduction5, dateDeb5, typeEvenement6, codeNature6, codeReduction6,
                dateDeb6, controleTrimestre1, controleCotisation1, controleCodenature1, controleTrimestre2,
                controleCotisation2, controleCodenature2);

        finishTestExecution();
    }
}