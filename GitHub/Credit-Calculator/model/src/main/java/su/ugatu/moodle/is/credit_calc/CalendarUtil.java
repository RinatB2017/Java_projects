package su.ugatu.moodle.is.credit_calc;

import java.util.Date;

/**
 * Утилита, реализует методы работы с датами.
 *
 * @author rinat.enikeev@gmail.com
 * Date: 14.01.14
 * Time: 22:04
 */
public class CalendarUtil {

    public static final int NUMBER_OF_MONTHS = 12;

    /**
     * Calculate next month's date. Attention! Next month date for January 31
     * will be 28 or 29 February.
     *
     * @param date to which add month.
     * @return next month's date. If Day of @param date is greater than number
     *         of days in next month, then day is set to last day of next month.
     */
    public static Date nextMonthDate(Date date) {
        Date result = copyDate(date);
        int newMonth = result.getMonth() + 1;
        while (newMonth > 11) {
            newMonth -= 12;
            result.setYear(result.getYear() + 1);
        }
        result.setMonth(newMonth);
        if (result.getMonth() != newMonth) {
            /* If the date was January 31, for example, and
             * the month is set to February, then the new date will be treated as
             * if it were on March 3, because February has only 30 days. */
            result.setDate(date.getDate() - result.getDate());
            result.setMonth(newMonth);
            /* day was forcibly set to last day of month */
        }
        return result;
    }

    /**
     * Copies a date.
     *
     * @param date the date
     * @return the copy
     */
    public static Date copyDate(Date date) {
        if (date == null) {
            return null;
        }
        Date newDate = new Date();
        newDate.setTime(date.getTime());
        return newDate;
    }
}
