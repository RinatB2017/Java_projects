package su.ugatu.moodle.is.credit_calc;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 19.01.14
 * Time: 2:06
 */
public class WithoutBanksCalcTest {

    @Test
    public void withoutBanksTest() {
        CreditApplication app = new CreditApplicationImpl(new BigDecimal("100000"));
        app.setDurationInMonths(12);
        app.setPaymentType(CreditPaymentType.ANNUITY);

        CreditOffer offer = new CreditOfferImpl();
        offer.setRate(new BigDecimal("0.1699"));

        CreditProposal proposal = offer.calculateProposal(app);
        TestUtil.printProposal(proposal);
    }
}
