package su.ugatu.moodle.is.credit_calc;

import java.math.BigDecimal;

/**
 * Оффер на кредит.
 *
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 21:47
 */
public interface CreditOffer {

    /**
     * @return имя заявки, например, кредит "Особый"
     */
    String getName();

    /**
     * @return минимальный размер кредита.
     */
    BigDecimal getMinAmount();

    /**
     * @return максимальный размер кредита.
     */
    BigDecimal getMaxAmount();

    /**
     * @return ставка кредита.
     */
    BigDecimal getRate();

    /**
     * @return валюта кредита в ISO 4217.
     */
    String getCurrency();

    /**
     * @return минимальный срок кредита в месяцах.
     */
    Integer getMinMonthDuration();

    /**
     * @return максимальный срок кредита в месяцах.
     */
    Integer getMaxMonthDuration();

    /**
     * @return процент ежемесячной комиссии на тело кредита.
     */
    BigDecimal getMonthlyCommissionPercent();

    /**
     * @return размер единовременной комиссии.
     */
    BigDecimal getOnceCommissionAmount();

    /**
     * @return процент единовременной комиссии на тело кредита.
     */
    BigDecimal getOnceCommissionPercent();

    /**
     * @return величина ежемесячной комиссии
     * (независимой от процента ежемесячной комиссии).
     */
    BigDecimal getMonthlyCommissionAmount();

    /**
     * Подготавливает предложение по кредиту, заполняя неустановленный поля
     * заявки на значения по умолчанию и рассчитывая график и размер платежей.
     *
     * @param application заявка на кредит.
     * @return предложение по кредиту.
     */
    CreditProposal calculateProposal(CreditApplication application);

    /**
     * Устанавливает ставку оффера по кредиту, в долях от единицы.
     *
     * @param rate ставка в долях от единицы (0.1699 соответствует 16.99%).
     * @return оффер, в целях fluent interface.
     */
    CreditOffer setRate(BigDecimal rate);

    /**
     * Устанавливает величину единовременной комиссии
     *
     * @param onceCommissionAmount размер единовременной комиссии.
     * @return оффер, в целях fluent interface.
     */
    CreditOffer setOnceCommissionAmount(BigDecimal onceCommissionAmount);

    /**
     * Устанавливает процент единовременной комиссии к телу кредита.
     *
     * @param onceCommissionPercent процент единовременной комиссии.
     * @return оффер, в целях fluent interface.
     */
    CreditOffer setOnceCommissionPercent(BigDecimal onceCommissionPercent);

    /**
     * Устанавливает размер ежемесячной комиссии, независимой от процентной
     * ежемесячной комиссии.
     *
     * @param monthlyCommissionAmount размер ежемесячной комиссии.
     * @return оффер, в целях fluent interface.
     */
    CreditOffer setMonthlyCommissionAmount(BigDecimal monthlyCommissionAmount);

    /**
     * Устанавливает процент ежемесячной комиссии.
     *
     * @param monthlyCommissPercent процент ежемесячной комиссии в долях от
     *                              единицы.
     * @return оффер, в целях fluent interface.
     */
    CreditOffer setMonthlyCommissionPercent(BigDecimal monthlyCommissPercent);
}
