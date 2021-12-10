package be.ucm.cas.nasca.web.test.enrolement.pm;

import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.RunBatch;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.Test;

import java.util.Calendar;

public class EnrolementPMTest extends EnrolementPMTestBase {

    private static final String BCE = "0471992397";

    @Test
    public void EnrolementPM() {
        initTest("Enrôlement PM", Thread.currentThread().getStackTrace()[1].getFileName());

        Calendar dateJour = Calendar.getInstance();
        dateJour.add(Calendar.YEAR, 1);

        String annee = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR_ONLY, dateJour.getTime());

        RunBatch.runBatchChangeDate(DateUtils.doFormat(TestData.DATE_FORMAT_XML, dateJour.getTime()));

        EnrolementPMTestBase.initialisationDateEnrolementAnnuel(annee, DateUtils.doFormat(TestData.DATE_FORMAT_XML, dateJour.getTime()));

        loginNasca();

        EnrolementPMTestBase.doEnrolementPM(BCE, annee, DateUtils.doFormat(TestData.DATE_FORMAT_XML, dateJour.getTime()), TestData.MONTANT_BILAN_PM, TestData.MONTANT_COTISATION_PM);

        loginNasca();

        EnrolementPMTestBase.checkCotisationAnneePM(BCE, annee);

        finishTestExecution();
    }
}