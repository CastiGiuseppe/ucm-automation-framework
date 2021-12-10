package be.ucm.cas.nasca.web.test.baremes.pp;

import be.ucm.cas.nasca.web.test.support.DateQuarters;
import org.testng.annotations.Test;

import java.util.Calendar;

public class BaremesPPTest extends BaremesPPTestBase {

    @Test
    public void modificationIndexFuturBaremePP() {
        initTest("Baremes PP (Index) - Modification Index trimestre futur",
                Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);

        DateQuarters dateQuarters = new DateQuarters(calendar.getTime());

        String trimestre = calendar.get(Calendar.YEAR) + "/"
                + dateQuarters.getQuarter();

        BaremesPPTestBase.modificationIndexBaremesPP(trimestre, true);

        finishTestExecution();
    }

    @Test
    public void modificationIndexPresentBaremePP() {
        initTest("Baremes PP (Index) - Modification Index trimestre present",
                Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        Calendar calendar = Calendar.getInstance();

        DateQuarters dateQuarters = new DateQuarters(calendar.getTime());

        String trimestre = calendar.get(Calendar.YEAR) + "/"
                + dateQuarters.getQuarter();

        BaremesPPTestBase.modificationIndexBaremesPP(trimestre, false);

        finishTestExecution();
    }

    @Test
    public void modificationIndexPasseBaremePP() {
        initTest("Baremes PP (Index) - Modification Index trimestre passe",
                Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);

        DateQuarters dateQuarters = new DateQuarters(calendar.getTime());

        String trimestre = calendar.get(Calendar.YEAR) + "/"
                + dateQuarters.getQuarter();

        BaremesPPTestBase.modificationIndexBaremesPP(trimestre, false);

        finishTestExecution();
    }

    @Test
    public void modificationMajorationsFuturBaremePP() {
        initTest("Baremes PP (Majorations) - Modification Majorations trimestre futur",
                Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 3);

        DateQuarters dateQuarters = new DateQuarters(calendar.getTime());

        String trimestre = calendar.get(Calendar.YEAR) + "/"
                + dateQuarters.getQuarter();

        BaremesPPTestBase.modificationMajorationsBaremesPP(trimestre, true);

        finishTestExecution();
    }

    @Test
    public void modificationMajorationsPresentBaremePP() {
        initTest("Baremes PP (Majorations) - Modification Majorations trimestre present",
                Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        Calendar calendar = Calendar.getInstance();

        DateQuarters dateQuarters = new DateQuarters(calendar.getTime());

        String trimestre = calendar.get(Calendar.YEAR) + "/"
                + dateQuarters.getQuarter();

        BaremesPPTestBase.modificationMajorationsBaremesPP(trimestre, false);

        finishTestExecution();
    }

    @Test
    public void modificationMajorationsPasseBaremePP() {
        initTest("Baremes PP (Majorations) - Modification Majorations trimestre passe",
                Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);

        DateQuarters dateQuarters = new DateQuarters(calendar.getTime());

        String trimestre = calendar.get(Calendar.YEAR) + "/"
                + dateQuarters.getQuarter();

        BaremesPPTestBase.modificationMajorationsBaremesPP(trimestre, false);

        finishTestExecution();
    }

    @Test
    public void modificationPfcPlfMontantPivotFuturBaremePP() {
        initTest("Baremes PP (Montants pivots) - Modification planchers et plafonds trimestre futur",
                Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 3);

        DateQuarters dateQuarters = new DateQuarters(calendar.getTime());

        String trimestre = calendar.get(Calendar.YEAR) + "/" + dateQuarters.getQuarter();

        BaremesPPTestBase.modificationPlanchersEtPlafondsMontantsPivotsBaremesPP(trimestre, true);

        finishTestExecution();
    }

    @Test
    public void modificationPfcPlfMontantPivotPresentBaremePP() {
        initTest("Baremes PP (Montants pivots) - Modification planchers et plafonds trimestre present",
                Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        Calendar calendar = Calendar.getInstance();

        DateQuarters dateQuarters = new DateQuarters(calendar.getTime());

        String trimestre = calendar.get(Calendar.YEAR) + "/" + dateQuarters.getQuarter();

        BaremesPPTestBase.modificationPlanchersEtPlafondsMontantsPivotsBaremesPP(trimestre, false);

        finishTestExecution();
    }

    @Test
    public void modificationPfcPlfMontantPivotPasseBaremePP() {
        initTest("Baremes PP (Montants pivots) - Modification planchers et plafonds trimestre passe",
                Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);

        DateQuarters dateQuarters = new DateQuarters(calendar.getTime());

        String trimestre = calendar.get(Calendar.YEAR) + "/" + dateQuarters.getQuarter();

        BaremesPPTestBase.modificationPlanchersEtPlafondsMontantsPivotsBaremesPP(trimestre, false);

        finishTestExecution();
    }

    @Test
    public void modificationPlfPensCjtMontantPivotFuturBaremePP() {
        initTest("Baremes PP (Montants pivots) - Modification plafonds pension conjoint trimestre futur",
                Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 3);

        DateQuarters dateQuarters = new DateQuarters(calendar.getTime());

        String trimestre = calendar.get(Calendar.YEAR) + "/" + dateQuarters.getQuarter();

        BaremesPPTestBase.modificationPlafondsPensionConjointMontantsPivotsBaremesPP(trimestre, true);

        finishTestExecution();
    }

    @Test
    public void modificationPlfPensCjtMontantPivotPresentBaremePP() {
        initTest("Baremes PP (Montants pivots) - Modification plafonds pension conjoint trimestre present",
                Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        Calendar calendar = Calendar.getInstance();

        DateQuarters dateQuarters = new DateQuarters(calendar.getTime());

        String trimestre = calendar.get(Calendar.YEAR) + "/" + dateQuarters.getQuarter();

        BaremesPPTestBase.modificationPlafondsPensionConjointMontantsPivotsBaremesPP(trimestre, false);

        finishTestExecution();
    }

    @Test
    public void modificationPlfPensCjtMontantPivotPasseBaremePP() {
        initTest("Baremes PP (Montants pivots) - Modification plafonds pension conjoint trimestre passe",
                Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);

        DateQuarters dateQuarters = new DateQuarters(calendar.getTime());

        String trimestre = calendar.get(Calendar.YEAR) + "/" + dateQuarters.getQuarter();

        BaremesPPTestBase.modificationPlafondsPensionConjointMontantsPivotsBaremesPP(trimestre, false);

        finishTestExecution();
    }

    @Test
    public void modificationTauxValeurFuturBaremePP() {
        initTest("Baremes PP (Taux) - Modification taux trimestre futur",
                Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 3);

        DateQuarters dateQuarters = new DateQuarters(calendar.getTime());

        String trimestre = calendar.get(Calendar.YEAR) + "/" + dateQuarters.getQuarter();

        BaremesPPTestBase.modificationTauxBaremesPP(trimestre, true);

        finishTestExecution();
    }

    @Test
    public void modificationTauxValeurPresentBaremePP() {
        initTest("Baremes PP (Taux) - Modification taux trimestre present",
                Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        Calendar calendar = Calendar.getInstance();

        DateQuarters dateQuarters = new DateQuarters(calendar.getTime());

        String trimestre = calendar.get(Calendar.YEAR) + "/" + dateQuarters.getQuarter();

        BaremesPPTestBase.modificationTauxBaremesPP(trimestre, false);

        finishTestExecution();
    }

    @Test
    public void modificationTauxValeurPasseBaremePP() {
        initTest("Baremes PP (Taux) - Modification taux trimestre passe",
                Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);

        DateQuarters dateQuarters = new DateQuarters(calendar.getTime());

        String trimestre = calendar.get(Calendar.YEAR) + "/" + dateQuarters.getQuarter();

        BaremesPPTestBase.modificationTauxBaremesPP(trimestre, false);

        finishTestExecution();
    }
}
