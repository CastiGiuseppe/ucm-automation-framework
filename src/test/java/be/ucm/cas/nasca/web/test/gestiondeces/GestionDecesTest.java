package be.ucm.cas.nasca.web.test.gestiondeces;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateQuarters;
import org.testng.annotations.Test;

import java.util.Date;

public class GestionDecesTest extends GestionDecesTestBase {

    @Test
    public void gestionDeces() {
        initTest("Creation Deces a partir L010", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        Date currentDatePreviousquarter = DateQuarters.getNextOrPreviousQuarterOfDate(new Date(), -1);

        String trimestre = DateQuarters.getQuarterCurrentWithSlash(currentDatePreviousquarter);

        String niss = DaoFunctionality.getNascaDao().getNissFromNumero(DaoFunctionality.getNascaDao().getNumeroEnrolMajoPP(trimestre));

        injectInastiFlux(niss);
        doCheckDeces(niss);

        finishTestExecution();
    }
}