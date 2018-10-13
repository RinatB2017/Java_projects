package su.ugatu.moodle.is.credit_calc;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 20.01.14
 * Time: 19:06
 */
public class CommissionTest {

    private CreditApplication application;
    private CreditOffer offer;

    @Before
    /**
     * don't change! All asserts depend on this particular values.
     */
    public void setUp() {
        BigDecimal amount = new BigDecimal(100000).setScale(Constants.OUTPUT_AMOUNT_SCALE);
        application = new CreditApplicationImpl(amount);
        application.setDurationInMonths(12);

        offer = new CreditOfferImpl();
        offer.setRate(new BigDecimal("0.1699").setScale(Constants.OUTPUT_PERCENT_SCALE));
        offer.setOnceCommissionAmount(new BigDecimal("100").setScale(Constants.OUTPUT_AMOUNT_SCALE));
        offer.setOnceCommissionPercent(new BigDecimal("0.01").setScale(Constants.OUTPUT_PERCENT_SCALE));
        offer.setMonthlyCommissionAmount(new BigDecimal("100").setScale(Constants.OUTPUT_AMOUNT_SCALE));
        offer.setMonthlyCommissionPercent(new BigDecimal("0.01").setScale(Constants.OUTPUT_PERCENT_SCALE));
    }

    @Test
    public void annuityCommissionTest() {
        application.setPaymentType(CreditPaymentType.ANNUITY);
        TestUtil.printApplication(application);
        CreditProposal proposal = offer.calculateProposal(application);
        assertEquals(proposal.getEffectiveRate(), (new BigDecimal("0.5068")));

        TestUtil.printProposal(proposal);
    }

    @Test
    public void differentialCommissionTest() {
        application.setPaymentType(CreditPaymentType.DIFFERENTIAL);
        CreditProposal proposal = offer.calculateProposal(application);
        assertEquals(proposal.getEffectiveRate(), new BigDecimal("0.5152"));
        TestUtil.printProposal(proposal);
    }
}
