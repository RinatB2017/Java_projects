package su.ugatu.moodle.is.credit_calc;

import java.math.BigDecimal;
import java.util.List;

/**
 * Предложение по кредиту.
 *
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 20:44
 */
public interface CreditProposal {
    /**
     * @return упорядоченный по датам список платежей.
     */
    List<CreditPayment> getPayments();

    /**
     * @return размер кредита.
     */
    BigDecimal getCreditAmount();

    /**
     * @return полная стоимость кредита.
     */
    BigDecimal getTotalPayment();

    /**
     * @return эффективная процентная ставка кредита.
     */
    BigDecimal getEffectiveRate();

    /**
     * @return исходная комиссия, не включаемая в ежемесячные платежи.
     */
    BigDecimal getInitialCommission();
    /**
     * @return полная комиссия, включая ежемесячные коммиссии.
     */
    BigDecimal getTotalCreditCommission();
}
