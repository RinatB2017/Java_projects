package su.ugatu.moodle.is.credit_calc;

import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;

/**
* Created: iiinjoy@gmail.com
* Date: 31.01.14
* Time: 18:36
*/

public class ThreeYearEPR {

  @Test
  public void calcThreeYearEPR() {
    CreditOffer offer = new CreditOfferImpl().setRate(new BigDecimal("0.155"))
    .setOnceCommissionPercent(new BigDecimal("0.015"))
    .setOnceCommissionAmount(new BigDecimal("3000"))
    .setMonthlyCommissionPercent(new BigDecimal("0.005"))
    .setMonthlyCommissionAmount(new BigDecimal("100"));

    CreditApplication app =
    new CreditApplicationImpl(new BigDecimal("300000"))
    .setPaymentType(CreditPaymentType.ANNUITY)
    .setDurationInMonths(36);

    CreditProposal proposal = offer.calculateProposal(app);
    assertEquals(proposal.getEffectiveRate(), new BigDecimal("0.3168"));
  }
}
