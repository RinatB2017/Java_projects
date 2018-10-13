package su.ugatu.moodle.is.credit_calc;
import static org.mockito.Mockito.*;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 19.01.14
 * Time: 0:21
 */
public class EffectiveRateMockTest {

    @Test
    /**
     * Example from
     */
    public void testCalcEffectiveScore() {
        CreditProposal proposal = mock(CreditProposal.class);
        when(proposal.getCreditAmount()).thenReturn(new BigDecimal(1000d));

        ArrayList<CreditPayment> payments = new ArrayList<CreditPayment>();
        Date date = new Date();
        payments.add(new CreditPaymentImpl(new BigDecimal(600d), date = CalendarUtil.nextMonthDate(date)));
        payments.add(new CreditPaymentImpl(new BigDecimal(0d), date = CalendarUtil.nextMonthDate(date)));
        payments.add(new CreditPaymentImpl(new BigDecimal(310d), date = CalendarUtil.nextMonthDate(date)));
        payments.add(new CreditPaymentImpl(new BigDecimal(194.25d), CalendarUtil.nextMonthDate(date)));
        when(proposal.getPayments()).thenReturn(payments);
        BigDecimal rate = FinUtil.calcEffectiveRate(proposal);
        System.out.println("Rate: " + rate);
    }
}
