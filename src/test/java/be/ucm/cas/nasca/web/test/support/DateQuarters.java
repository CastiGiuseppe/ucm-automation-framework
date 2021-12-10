package be.ucm.cas.nasca.web.test.support;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

public class DateQuarters {

    private static final RangeMap<Date, Integer> quarters = TreeRangeMap.create();
    private static Date dateToCompare;

    public DateQuarters(Date date) {
        DateQuarters.dateToCompare = date;
        fillQuarters();
    }

    public Integer getQuarter() {
        return quarters.get(dateToCompare);
    }

    private static Integer getQuarter(Date date) {
        DateQuarters.dateToCompare = date;
        fillQuarters();

        return quarters.get(date);
    }

    private static void fillQuarters() {
        DateTime currentDate = new DateTime(dateToCompare);

        // set quarters
        DateTime q1BeginDate = new DateTime(currentDate.getYear(), 1, 1, 0, 0, 0, 0);
        DateTime q1EndDate = new DateTime(currentDate.getYear(), 3, 31, 23, 59, 59, 59);

        DateTime q2BeginDate = new DateTime(currentDate.getYear(), 4, 1, 0, 0, 0, 0);
        DateTime q2EndDate = new DateTime(currentDate.getYear(), 6, 30, 23, 59, 59, 59);

        DateTime q3BeginDate = new DateTime(currentDate.getYear(), 7, 1, 0, 0, 0, 0);
        DateTime q3EndDate = new DateTime(currentDate.getYear(), 9, 30, 0, 59, 59, 59);

        DateTime q4BeginDate = new DateTime(currentDate.getYear(), 10, 1, 0, 0, 0, 0);
        DateTime q4EndDate = new DateTime(currentDate.getYear(), 12, 31, 23, 59, 59, 59);

        // add to quarters
        quarters.put(Range.closed(q1BeginDate.toDate(), q1EndDate.toDate()), 1);
        quarters.put(Range.closed(q2BeginDate.toDate(), q2EndDate.toDate()), 2);
        quarters.put(Range.closed(q3BeginDate.toDate(), q3EndDate.toDate()), 3);
        quarters.put(Range.closed(q4BeginDate.toDate(), q4EndDate.toDate()), 4);
    }

    public static String getQuarterCurrent(Date date) {
        DateTime currentDate = new DateTime(date);
        int trimestre = getQuarter(date);
        int year = currentDate.getYear();
        return String.valueOf(year) + String.valueOf(trimestre);
    }

    public static String getQuarterCurrentWithSlash(Date date) {
        DateTime currentDate = new DateTime(date);
        int trimestre = getQuarter(date);
        int year = currentDate.getYear();
        return year + "/" + trimestre;
    }

    public static String getOnlyNumberQuarter(Date date) {
        DateTime currentDate = new DateTime(date);
        return String.valueOf(getQuarter(date));
    }

    public static Date getNextOrPreviousQuarterOfDate(Date date, int nbrquarter) {
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);
        calDate.add(Calendar.MONTH, nbrquarter * 3);

        String quarter = getQuarterCurrent(calDate.getTime()).substring(4);

        switch (quarter) {
            case "1":
                return new DateTime(calDate.get(Calendar.YEAR), 1, 2, 0, 0, 0, 0).toDate();
            case "2":
                return new DateTime(calDate.get(Calendar.YEAR), 4, 2, 0, 0, 0, 0).toDate();
            case "3":
                return new DateTime(calDate.get(Calendar.YEAR), 7, 2, 0, 0, 0, 0).toDate();
            case "4":
                return new DateTime(calDate.get(Calendar.YEAR), 10, 2, 0, 0, 0, 0).toDate();
            default:
                return date;
        }
    }
}