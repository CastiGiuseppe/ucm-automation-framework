package be.ucm.cas.nasca.web.test.baremes.pm;

import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;

import java.util.Calendar;
import java.util.Date;

public class BaremesPmAbstract extends BaremesPMTestBase {
    private String dateDebut;
    private String dateFin;

    void initTest(String libelle, String fileName, Integer amount) {
        initTest(libelle, fileName);

        loginNasca();

        if (amount == null) {
            prepareDatePrec();
        } else {
            prepareDate(amount);
        }
    }

    private void prepareDate(int amount) {
        if (amount != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, amount);

            setDateDebut(DateUtils.getFirstDateofYear(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, calendar.getTime())));
            setDateFin(DateUtils.getLastDateofYear(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, calendar.getTime())));
        } else {
            setDateDebut(DateUtils.getFirstDateofYear(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, new Date())));
            setDateFin(DateUtils.getLastDateofYear(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, new Date())));
        }
    }

    private void prepareDatePrec() {
        Calendar calendar = Calendar.getInstance();
        setDateDebut(DateUtils.getFirstDateofYear(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, calendar.getTime())));

        calendar.add(Calendar.YEAR, -1);
        setDateFin(DateUtils.getLastDateofYear(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, calendar.getTime())));
    }

    String getDateDebut() {
        return dateDebut;
    }

    private void setDateDebut(String date) {
        this.dateDebut = date;
    }

    String getDateFin() {
        return dateFin;
    }

    private void setDateFin(String date) {
        this.dateFin = date;
    }
}
