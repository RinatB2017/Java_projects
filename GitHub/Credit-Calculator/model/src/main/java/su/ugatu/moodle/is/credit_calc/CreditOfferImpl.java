package su.ugatu.moodle.is.credit_calc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Реализация кредитного оффера. ы
 *
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 21:47
 */
public class CreditOfferImpl implements CreditOffer {

    private static final CreditPaymentType DEFAULT_PAYMENT_TYPE = CreditPaymentType.ANNUITY;

    private String name;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private BigDecimal rate;
    private String currencyName;
    private Integer minMonthDuration;
    private Integer maxMonthDuration;
    private BigDecimal onceCommissionAmount;
    private BigDecimal onceCommissionPercent;
    private BigDecimal monthlyCommissionAmount;
    private BigDecimal monthlyCommissionPercent;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getMinAmount() {
        return minAmount;
    }

    @Override
    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    @Override
    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String getCurrency() {
        return currencyName;
    }

    @Override
    public Integer getMinMonthDuration() {
        return minMonthDuration;
    }

    @Override
    public Integer getMaxMonthDuration() {
        return maxMonthDuration;
    }

    @Override
    public BigDecimal getMonthlyCommissionPercent() {
        return monthlyCommissionPercent;
    }

    @Override
    public BigDecimal getOnceCommissionAmount() {
        return onceCommissionAmount;
    }

    @Override
    public BigDecimal getOnceCommissionPercent() {
        return onceCommissionPercent;
    }

    @Override
    public BigDecimal getMonthlyCommissionAmount() {
        return monthlyCommissionAmount;
    }

    @Override
    public CreditProposal calculateProposal(final CreditApplication application) {
        if (!applicationCorrespondsToOffer(application)) return null;
        configureApplicationBlankParams(application);
        return new CreditProposalImpl(application, this);
    }

    @Override
    public CreditOffer setRate(final BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    @Override
    public CreditOffer setOnceCommissionAmount(final BigDecimal onceCommissionAmount) {
        this.onceCommissionAmount = onceCommissionAmount;
        return this;
    }

    @Override
    public CreditOffer setOnceCommissionPercent(final BigDecimal onceCommissionPercent) {
        this.onceCommissionPercent = onceCommissionPercent;
        return this;
    }

    @Override
    public CreditOffer setMonthlyCommissionAmount(final BigDecimal monthlyCommissionAmount) {
        this.monthlyCommissionAmount = monthlyCommissionAmount;
        return this;
    }

    @Override
    public CreditOffer setMonthlyCommissionPercent(final BigDecimal monthlyCommissPercent) {
        this.monthlyCommissionPercent = monthlyCommissPercent;
        return this;
    }

    /**
     * Sets payment type, currency and duration of application to offers values
     * if some of them or all are null.
     *
     * @param creditApplication incoming application
     */
    private void configureApplicationBlankParams(final CreditApplication creditApplication) {
        if (creditApplication.getPaymentType() == null) {
            creditApplication.setPaymentType(DEFAULT_PAYMENT_TYPE);
        }
        if (creditApplication.getCurrency() == null) {
            creditApplication.setCurrency(getCurrency());
        }
        if (creditApplication.getDurationInMonths() == null) {
            creditApplication.setDurationInMonths(getMaxMonthDuration());
        }
        if (creditApplication.getStartDate() == null) {
            creditApplication.setStartDate(new Date());
        }
    }

    /**
     * Checks if applications parameters are in bounds of current offer.
     *
     * @param creditApplication incoming application
     * @return true if application is correct in context of this offer.
     */
    private boolean applicationCorrespondsToOffer(final CreditApplication creditApplication) {

        BigDecimal amount = creditApplication.getAmount();
        if (!amountInBounds(amount)) return false;

        Integer duration = creditApplication.getDurationInMonths();
        if (duration != null && !durationInBounds(duration)) return false;

        String currency = creditApplication.getCurrency();
        if (currency != null && getCurrency() != null
                && !currency.equals(getCurrency())) {
            return false;
        }

        return true;
    }

    private boolean durationInBounds(final Integer duration) {
        boolean durInBounds = true;
        if (getMaxMonthDuration() != null) {
            durInBounds = duration <= getMaxMonthDuration();
        }
        if (getMinMonthDuration() != null) {
            durInBounds = durInBounds && duration >= getMinMonthDuration();
        }
        return durInBounds;
    }

    private boolean amountInBounds(final BigDecimal amount) {
        boolean amountInBounds = true;
        if (getMaxAmount() != null) {
            amountInBounds = amount.compareTo(getMaxAmount()) <= 0;
        }
        if (getMinAmount() != null) {
            amountInBounds = amountInBounds && amount.compareTo(getMinAmount()) >= 0;
        }
        return amountInBounds;
    }
}
