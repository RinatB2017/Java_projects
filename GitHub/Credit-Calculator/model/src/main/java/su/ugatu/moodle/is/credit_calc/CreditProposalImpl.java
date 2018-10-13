package su.ugatu.moodle.is.credit_calc;

import java.math.BigDecimal;
import java.util.List;

/**
 * Реализация кредитного предложения.
 *
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 18:50
 */
class CreditProposalImpl implements CreditProposal {

    private final BigDecimal creditAmount;      // размер кредита
    private final BigDecimal effectiveRate;     // эффективная процентная ставка
    private final BigDecimal totalPayment;      // полная стоимость кредита
    private final List<CreditPayment> payments; // список платежей
    private final BigDecimal initCredComm;      // первоначальная комиссия
    private final BigDecimal totalCredComm;     // итоговая комиссия

    /**
     *
     *
     * @param application заявка на кредит
     * @param creditOffer оффер на кредит
     */
    CreditProposalImpl(final CreditApplication application,
                       final CreditOffer creditOffer) {

        // 1. Проверка заполненности свойств заявки на кредит
        checkCreditApplication(application);

        // 2. Заполняем свойства предложения
        this.creditAmount = application.getAmount();
        this.initCredComm = FinUtil.calcInitialCommission(
                                                        application.getAmount(),
                                                        creditOffer);

        BigDecimal monthlyCommission = FinUtil.calcMonthlyCommission(
                                                        application.getAmount(),
                                                        creditOffer);
        this.totalCredComm=this.initCredComm.add(monthlyCommission.multiply(new BigDecimal(application.getDurationInMonths())));
        switch (application.getPaymentType()) {
            case ANNUITY: {
                this.payments = FinUtil.calcAnnuityPayments(
                                             application.getAmount(),
                                             application.getDurationInMonths(),
                                             application.getStartDate(),
                                             creditOffer.getRate(),
                                             monthlyCommission);
                break;

            }
            case DIFFERENTIAL: {
                this.payments = FinUtil.calcDifferentialPayments(
                                            application.getAmount(),
                                            application.getDurationInMonths(),
                                            application.getStartDate(),
                                            creditOffer.getRate(),
                                            monthlyCommission);
                break;
            }
            default:
                throw new UnsupportedOperationException(
                       application.getPaymentType() + " type is not supported");
        }

        this.totalPayment  = FinUtil.calcTotalAmount(payments).add(this.initCredComm);
        this.effectiveRate = FinUtil.calcEffectiveRate(this);
    }

    @Override
    public List<CreditPayment> getPayments() {
        return payments;
    }

    @Override
    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    @Override
    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    @Override
    public BigDecimal getEffectiveRate() {
        return effectiveRate;
    }

    @Override
    public BigDecimal getInitialCommission() {
        return initCredComm;
    }
    public BigDecimal getTotalCreditCommission() {
        return totalCredComm;
    }
    /**
     * Проверяет заполненность свойств заявки на кредит.
     *
     * @param application заявка на кредит.
     * @throws IllegalArgumentException в случае, когда параметры не заполнены.
     */
    private void checkCreditApplication(final CreditApplication application) {
        if (application.getPaymentType() == null) {
            throw new IllegalArgumentException(
                    "Credit application must have non-null PaymentType param.");
        }
        if (application.getDurationInMonths() == null) {
            throw new IllegalArgumentException(
             "Credit application must have non-null duration in months param.");
        }
        if (application.getStartDate() == null) {
            throw new IllegalArgumentException(
                    "Credit application must have non-null start date param.");
        }
    }
}
