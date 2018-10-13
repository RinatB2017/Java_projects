package su.ugatu.moodle.is.credit_calc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Платеж по кредиту.
 *
 * @author rinat.enikeev@gmail.com
 * Date: 14.01.14
 * Time: 20:58
 */
public interface CreditPayment {

    /**
     * @return дата платежа.
     */
    Date getDate();

    /**
     * @return размер платежа.
     */
    BigDecimal getAmount();

    /**
     * @return долговая часть в платеже.
     */
    BigDecimal getDebt();

    /**
     * @return процент в платеже.
     */
    BigDecimal getInterest();

    /**
     * @return сколько еще всего осталось уплатить.
     */
    BigDecimal getTotalLeft();

    /**
     * @return комиссия в платеже.
     */
    BigDecimal getCommission();

    /**
     * Устанавливает долговую часть платежа.
     *
     * @param debt долговая часть платежа.
     * @return платеж, в целях fluent interface.
     */
    CreditPayment setDebt(BigDecimal debt);

    /**
     * Устанавливает процентную часть платежа.
     *
     * @param interest процентная часть платежа.
     * @return платеж, в целях fluent interface.
     */
    CreditPayment setInterest(BigDecimal interest);

    /**
     * Устанавливает остаток долга по кредиту.
     *
     * @param totalLeft остаток долга по кредиту.
     * @return платеж, в целях fluent interface.
     */
    CreditPayment setTotalLeft(BigDecimal totalLeft);

    /**
     * Устанавливает комиссию в платеже.
     *
     * @param commission комиссия в платеже.
     * @return платеж, в целях fluent interface.
     */
    CreditPayment setCommission(BigDecimal commission);
}
