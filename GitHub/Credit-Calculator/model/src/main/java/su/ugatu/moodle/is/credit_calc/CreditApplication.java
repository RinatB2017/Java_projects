package su.ugatu.moodle.is.credit_calc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Заявка на получение кредита.
 *
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 20:39
 */
public interface CreditApplication {

    /**
     * @return размер запрошенного кредита в валюте {@link #getCurrency()}
     */
    BigDecimal getAmount();

    /**
     * @return строка валюты заявки на кредит в формате ISO 4217.
     */
    String getCurrency();

    /**
     * @return тип отплаты кредита (например, аннуитет или дифференцированный)
     */
    CreditPaymentType getPaymentType();

    /**
     * @return срок кредита в месяцах.
     */
    Integer getDurationInMonths();

    /**
     * @return день, в который планируется получить кредит.
     */
    Date getStartDate();

    /**
     * Устанавить валюту заявки на кредит.
     * @param currency код валюты по ISO 4217.
     * @return заявка на кредит, в целях fluent interface.
     */
    CreditApplication setCurrency(final String currency);

    /**
     * Установить тип оплаты кредита (нопример, аннуитет или дифф.).
     *
     * @param paymentType тип платежа.
     * @return заявка на кредит.
     */
    CreditApplication setPaymentType(final CreditPaymentType paymentType);

    /**
     * Установить срок выплаты кредита в месяцах.
     * @param durationInMonths количество месяцев.
     * @return заявка на кредит, в целях fluent interface.
     */
    CreditApplication setDurationInMonths(final Integer durationInMonths);

    /**
     * Установить желаемую дату получения кредита.
     * @param startDate желаемая дата получения кредита.
     * @return заявка на кредит, в целях fluent interface.
     */
    CreditApplication setStartDate(final Date startDate);
}
