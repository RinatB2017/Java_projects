package su.ugatu.moodle.is.credit_calc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Реализация заявки на кредит.
 *
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 18:50
 */
public class CreditApplicationImpl implements CreditApplication {

    private final BigDecimal amount;       // размер кредита
    private String currency;               // валюта кредита (ISO 4217)
    private CreditPaymentType paymentType; // тип платежа (аннуитет, дифф.)
    private Integer durationInMonths;      // срок кредита в месяцах
    private Date startDate;                // дата получения кредита

    public CreditApplicationImpl(final BigDecimal anAmount) {
        this.amount = anAmount;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public CreditApplication setCurrency(final String aCurrency) {
        this.currency = aCurrency;
        return this;
    }

    @Override
    public CreditPaymentType getPaymentType() {
        return paymentType;
    }

    @Override
    public CreditApplication setPaymentType(CreditPaymentType aPaymentType) {
        this.paymentType = aPaymentType;
        return this;
    }

    @Override
    public Integer getDurationInMonths() {
        return durationInMonths;
    }

    @Override
    public CreditApplication setDurationInMonths(Integer aDurationInMonths) {
        this.durationInMonths = aDurationInMonths;
        return this;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    public CreditApplication setStartDate(final Date aStartDate) {
        this.startDate = aStartDate;
        return this;
    }

}
