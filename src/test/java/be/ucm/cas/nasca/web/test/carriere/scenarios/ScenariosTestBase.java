package be.ucm.cas.nasca.web.test.carriere.scenarios;

import be.ucm.cas.nasca.web.test.support.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static be.ucm.cas.nasca.web.test.support.EnumTypeTest.AUTRE;

/**
 * Created by sylvie.degraef on 7/06/2017.
 */
public class ScenariosTestBase extends TestBase {
    private Map<String, List<String>> doScenario = new LinkedHashMap<>();


    @Override
    public EnumTypeTest getTypeTest() {
        return AUTRE;
    }

    void doScenario(String id,String niss,String nature,String type,String chgtProfil,String pmt,
                    String pmtPeriode,String pmtNbre,String pmtTrim,String maj,String majLevee,String annulCot,
                    String typeCess,String dip,String annul,String tftDroit,String ass,String asf,String planFamille,
                    String regime,String envoi,String revenuMnt,String revenuPresume,String revenuAnnee,String article,
                    String bareme,String anneeTrim,String info,String pension,String erreurChgt,String demandeRefresh,String spf) {

        try {receptionScenario(id,niss,nature,type,chgtProfil,pmt,pmtPeriode,pmtNbre,pmtTrim,maj,majLevee,annulCot,
                typeCess,dip,annul,tftDroit,ass,asf,planFamille,regime,envoi,revenuMnt,revenuPresume,revenuAnnee,article,
                bareme,anneeTrim,info,pension,erreurChgt,demandeRefresh,spf);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }


    static void receptionScenario(String id,String niss,String nature,String type,String chgtProfil,String pmt,
                                  String pmtPeriode,String pmtNbre,String pmtTrim,String maj,String majLevee,String annulCot,
                                  String typeCess,String dip,String annul,String tftDroit,String ass,String asf,String planFamille,
                                  String regime,String envoi,String revenuMnt,String revenuPresume,String revenuAnnee,String article,
                                  String bareme,String anneeTrim,String info,String pension,String erreurChgt,String demandeRefresh,String spf)
    {
        Map<String, String> map = new LinkedHashMap<>();



    }

}
