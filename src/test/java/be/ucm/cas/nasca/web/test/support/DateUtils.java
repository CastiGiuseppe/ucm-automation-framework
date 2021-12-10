package be.ucm.cas.nasca.web.test.support;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

    public DateUtils() {
    }

    public static DateTime doConvertStringtoDatTime(String date) {
        DateTimeFormatter format = DateTimeFormat.forPattern("ddMMyyyy");
        return format.parseDateTime(SeleniumUtils.deleteFormat(date));
    }

    public static DateTime doConvertStringtoDatTimeProfil(String date) {
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMdd");
        return format.parseDateTime(SeleniumUtils.deleteFormat(date));
    }

    public static DateTime doConvertStringtoDatTime(String date, String patternformat) {
        DateTimeFormatter format = DateTimeFormat.forPattern(patternformat);
        return format.parseDateTime(SeleniumUtils.deleteFormat(date));
    }

    public static String getPlusYears(int i) {
        DateTime dt = new DateTime(new Date());
        dt = dt.plusYears(i);
        return new SimpleDateFormat(TestData.DATE_FORMAT_SIMPLE).format(dt.toDate());
    }

    public static Date getMinusYearsMachine(int i) {
        DateTime dt = new DateTime(new Date());
        dt = dt.minusYears(i);
        return dt.toDate();
    }

    public static String getYearNow() {
        return String.valueOf(new DateTime(new Date()));
    }

    public static int getQuarterNumber(String dateargs) {
        if (checkMonths(dateargs, "01", "02", "03")) {
            return 1;
        } else if (checkMonths(dateargs, "04", "05", "06")) {
            return 2;
        } else if (checkMonths(dateargs, "07", "08", "09")) {
            return 3;
        } else {
            return 4;
        }
    }

    private static boolean checkMonths(String date, String month1, String month2, String month3) {
        return month1.equals(date.substring(3, 5)) ||
                month2.equals(date.substring(3, 5)) ||
                month3.equals(date.substring(3, 5));
    }

    public static String getFirstDateofYear(String date) {
        String quarter = getQuarterofDate(date);
        int quarterint = Integer.valueOf(quarter);

        return "01-01-" + getYearfromStringTrimestre(String.valueOf(quarterint));
    }

    public static String getLastDateofYear(String date) {
        String quarter = getQuarterofDate(date);
        int quarterint = Integer.valueOf(quarter);
        return "31-12-" + getYearfromStringTrimestre(String.valueOf(quarterint));
    }

    public static String getFirstDateofQuarter(String date) {
        String quarter = getQuarterofDate(date);
        int quarterint = Integer.valueOf(quarter);
        int i = quarterint % 10;

        switch (i) {
            case 1:
                return "01-01-" + getYearfromStringTrimestre(String.valueOf(quarterint));
            case 2:
                return "01-04-" + getYearfromStringTrimestre(String.valueOf(quarterint));
            case 3:
                return "01-07-" + getYearfromStringTrimestre(String.valueOf(quarterint));
            case 4:
                return "01-10-" + getYearfromStringTrimestre(String.valueOf(quarterint));
            default:
                return date;
        }
    }

    public static String getLastDateofQuarter(String date) {
        String quarter = getQuarterofDate(date);
        int quarterint = Integer.valueOf(quarter);
        int i = quarterint % 10;

        switch (i) {
            case 1:
                return "31-03-" + getYearfromStringTrimestre(String.valueOf(quarterint));
            case 2:
                return "30-06-" + getYearfromStringTrimestre(String.valueOf(quarterint));
            case 3:
                return "30-09-" + getYearfromStringTrimestre(String.valueOf(quarterint));
            case 4:
                return "31-12-" + getYearfromStringTrimestre(String.valueOf(quarterint));
            default:
                return date;
        }
    }

    public static String getQuarterofDate(String date) {
        int quarternumber = getQuarterNumber(date);
        int year = getYearOfDate(date);
        String quarter = String.valueOf(year);
        return quarter + String.valueOf(quarternumber);
    }

    public static String getNextQuarterofDate(String date) {
        int quarternumber = getQuarterNumber(date);
        int year = getYearOfDate(date);
        if (quarternumber == 4) {
            year++;
            quarternumber = 1;
            String quarter = String.valueOf(year);
            return quarter + String.valueOf(quarternumber);
        } else {
            String quarter = String.valueOf(year + 1);
            return quarter + String.valueOf(quarternumber);
        }
    }

    public static String getPreviousQuarterofDate(String date) {
        int quarternumber = getQuarterNumber(date);
        int year = getYearOfDate(date);
        if (quarternumber == 1) {
            quarternumber = 4;
            year--;
            String quarter = String.valueOf(year);
            return quarter + String.valueOf(quarternumber);
        } else {
            quarternumber--;
            String quarter = String.valueOf(year);
            return quarter + String.valueOf(quarternumber);
        }
    }

    private static int getYearOfDate(String date) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(TestData.DATE_FORMAT_SIMPLE);
        try {
            cal.setTime(formatter.parse(date));
            return cal.get(Calendar.YEAR);
        } catch (ParseException e) {
            return 0;
        }
    }

    public static int getNextYearOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);

        return cal.get(Calendar.YEAR);
    }

    private static String getYearfromStringTrimestre(String trimestre) {
        return trimestre.substring(0, 4);
    }

    public static String getMonthfromStringTrimestre(String trimestre) {
        return trimestre.substring(4);
    }

    public static String getDateToday() {
        return new SimpleDateFormat(TestData.DATE_FORMAT_SIMPLE).format(new Date());
    }

    public static String getDateToday(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String getDateTodayXmlFomat() {
        return new SimpleDateFormat(TestData.DATE_FORMAT_XML).format(new Date());
    }

    public static String getDateTodayNoFomat() {
        return new SimpleDateFormat(TestData.DATE_NOFORMAT).format(new Date());
    }

    public static String getDateFutur(int nbrdaysinfutur, String format) throws ParseException {
        String date = new SimpleDateFormat(format).format(new Date());
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateformat.parse(date));
        cal.add(Calendar.DATE, nbrdaysinfutur);
        return dateformat.format(cal.getTime());
    }

    public static Date getDateFuturOrPass(int nbrdaysinfutur, Date currentdate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentdate);
        cal.add(Calendar.DAY_OF_MONTH, nbrdaysinfutur);
        return cal.getTime();
    }

    public static String getTodayDateFormatCODA() {
        return new SimpleDateFormat(TestData.DATE_FORMAT_CODA).format(new Date());
    }

    public static String doFormat(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String doFormatToString(String formatSortie, String formatEntree, String dateString) throws ParseException {
        DateFormat format = new SimpleDateFormat(formatEntree);
        return new SimpleDateFormat(formatSortie).format(format.parse(dateString));
    }

    public static String getDatePivotforProfilChange(String dtEvenement, int nbreMois) {
        DateTime dt = DateUtils.doConvertStringtoDatTime(dtEvenement, TestData.DATE_NOFORMAT);
        dt = dt.minusMonths(nbreMois);
        return DateUtils.doFormat(TestData.DATE_FORMAT_XML, dt.toDate());
    }
}