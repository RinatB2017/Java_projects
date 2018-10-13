package su.ugatu.moodle.is.credit_calc;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Реализует методы финансовых расчетов.
 *
 * @author rinat.enikeev@gmail.com
 * Date: 18.01.14
 * Time: 23:30
 */
class FinUtil {

    /**
     * Рассчитать платежи по кредиту по аннуитетной схеме.
     *
     * @param creditAmount размер кредита.
     * @param durationInMonths продолжительность в месяцах.
     * @param date дата.
     * @param rate ставка кредита в долях от единицы.
     * @param monthlyCommission ежемесячная комиссия.
     * @return упорядоченный по дате список платежей по кредиту.
     */
     static List<CreditPayment> calcAnnuityPayments
                                    (final BigDecimal creditAmount,
                                     final int durationInMonths,
                                     Date date,
                                     final BigDecimal rate,
                                     final BigDecimal monthlyCommission) {
        ArrayList<CreditPayment> payments = new ArrayList<CreditPayment>();
         // Формула расчета платежа по аннуитетной схеме A = (L / 12) / (1 - (1 + (l / 12)) ^ -n )
        // месячная ставка по кредиту   L / 12
        BigDecimal monthlyRate = rate.divide(
             new BigDecimal(CalendarUtil.NUMBER_OF_MONTHS),
                    Constants.CALC_SCALE,
                    Constants.ROUNDING_MODE
        );

        //  1 + (l / 12) ^ -n
        BigDecimal pow = monthlyRate.add(new BigDecimal(1)).pow(-durationInMonths, MathContext.DECIMAL64);

        BigDecimal denominator = new BigDecimal(1);


        denominator = denominator.subtract(pow);

        //Расчет суммы платежа без комиссии
        BigDecimal amount = creditAmount.multiply(monthlyRate).divide(
                denominator,
                Constants.CALC_SCALE,
                Constants.ROUNDING_MODE
        );

        BigDecimal withCommAmount = amount;

        //Проверка на месячную комиссию, если существует, то учитываем
        if (monthlyCommission != null) {
            withCommAmount = withCommAmount.add(monthlyCommission);// Сумма платежа с комиссией
        }

        BigDecimal base = creditAmount; //Сумма кредита

        for (int i = 0; i < durationInMonths; i++) {
            //Начисленные проценты
            BigDecimal interest = base.multiply(monthlyRate);

            //Суммируем остаток с начисленными процентами
            base = base.add(interest);

            date = CalendarUtil.nextMonthDate(date);


            //Создаем объект платежа по кредиту с суммой платежа с учетом комисии, если она существует.
            CreditPayment payment = new CreditPaymentImpl(withCommAmount.setScale(
                    Constants.CALC_SCALE,Constants.ROUNDING_MODE),date);

            //Добавляем и округляем Основной долг, разница между суммой платежа и начисленными процентами
            payment.setDebt(amount.subtract(interest).setScale(
                    Constants.CALC_SCALE,
                    Constants.ROUNDING_MODE
                )
            );

            //Добавляем и Округляем остаток с начисленными процентами
            payment.setInterest(
                    interest.setScale(Constants.CALC_SCALE,
                    Constants.ROUNDING_MODE)
            );

            // Расчитываем остаток
            base = base.subtract(amount);


            //Добавляем Остаток на следующий месяц
            payment.setTotalLeft(base.setScale(
                    Constants.CALC_SCALE,
                    Constants.ROUNDING_MODE)
            );

            //Если комиссия существует, то добавляем её
            if (monthlyCommission != null) {
                payment.setCommission(monthlyCommission.setScale(
                        Constants.CALC_SCALE,
                        Constants.ROUNDING_MODE
                    )
                );
            }

            //Прибавляем записи
            payments.add(payment);

        }

            return payments;
    }

    /**
     * Рассчитать платежи по кредиту по дифференцированной схеме.
     *
     * @param creditAmount размер кредита.
     * @param durationInMonths продолжительность в месяцах.
     * @param date дата.
     * @param rate ставка кредита в долях от единицы.
     * @param monthlyCommission ежемесячная комиссия.
     * @return упорядоченный по дате список платежей по кредиту.
     */
    static List<CreditPayment> calcDifferentialPayments(final BigDecimal creditAmount,
                                          final int durationInMonths,
                                          Date date,
                                          final BigDecimal rate,
                                          final BigDecimal monthlyCommission) {
        //Формула расчета платежа по дифференцированной схеме
        //A = (1 / n + L / 12) * S - (S * L / 12) / n * (k - 1)
        //amount = (1 / durationInMonths + monthlyRate) * creditAmount
        // - (creditAmount * monthlyRate / durationInMonthsBD) * (k - 1);
        ArrayList<CreditPayment> payments = new ArrayList<CreditPayment>();
        //Ежемесячная процентная ставка по кредиту   L / 12
        BigDecimal monthlyRate = rate.divide(
                new BigDecimal(CalendarUtil.NUMBER_OF_MONTHS),
                Constants.CALC_SCALE, Constants.ROUNDING_MODE);
        BigDecimal durationInMonthsBD = new BigDecimal(durationInMonths);
        //Устанавливаем остаток равным размеру кредита
        BigDecimal base = creditAmount;

        for (int k = 1; k <= durationInMonths; k++) {
            //процентная часть платежа
            BigDecimal interest = base.multiply(monthlyRate);
            //добавляем к остатку
            base = base.add(interest);
            //первое слагаемое в формуле
            //(1 / n + L / 12) * S
            BigDecimal firstSum = new BigDecimal(1)
                    .divide(durationInMonthsBD,
                            Constants.CALC_SCALE,
                            Constants.ROUNDING_MODE);

            firstSum = firstSum.add(monthlyRate).multiply(creditAmount);
            //первое слагаемое в формуле
            //(S * L / 12) / n * (k - 1)
            BigDecimal secondSum = creditAmount
                    .multiply(monthlyRate)
                    .divide(durationInMonthsBD,
                            Constants.CALC_SCALE,
                            Constants.ROUNDING_MODE)
                    .multiply(new BigDecimal(k - 1));
            //второе слагаемое в формуле
            //будет вычислено в 0 для первого платежа, так как k-1=0 при k=1
            //A = (1 / n + L / 12) * S - (S * L / 12) / n * (k - 1)
            BigDecimal amount = firstSum.subtract(secondSum);

            //Если есть ежемесячная комиссия, учесть ее в размере платежа
            BigDecimal withCommAmount = amount;
            if (monthlyCommission != null) {
                withCommAmount = withCommAmount.add(monthlyCommission);
            }

            //следующая дата платежа
            date = CalendarUtil.nextMonthDate(date);
            CreditPayment payment
                    = new CreditPaymentImpl(withCommAmount
                    .setScale(Constants.CALC_SCALE,
                            Constants.ROUNDING_MODE),
                    date);
            //долговая часть платежа: creditAmount/durationInMonths
            payment.setDebt(creditAmount
                    .divide(durationInMonthsBD,
                            Constants.CALC_SCALE,
                            Constants.ROUNDING_MODE)
                    .setScale(Constants.OUTPUT_AMOUNT_SCALE,
                            Constants.ROUNDING_MODE));
            //вычитаем от остатка размер платежа
            base = base.subtract(amount);

            //процентная часть платежа
            payment.setInterest(interest
                    .setScale(Constants.OUTPUT_AMOUNT_SCALE,
                            Constants.ROUNDING_MODE));
            //остаток на следующий месяц
            payment.setTotalLeft(base
                    .setScale(Constants.OUTPUT_AMOUNT_SCALE,
                            Constants.ROUNDING_MODE));
            //размер ежемесячной комиссии
            if (monthlyCommission != null) {
                payment.setCommission(monthlyCommission
                        .setScale(Constants.CALC_SCALE,
                                Constants.ROUNDING_MODE));
            }
            //добавляем данный платеж в список платежей
            payments.add(payment);
        }
        //возвращаем список платежей
        return payments;
    }

    static BigDecimal calcTotalAmount(final List<CreditPayment> payments) {
        BigDecimal totalAmount = new BigDecimal(0).setScale(Constants.CALC_SCALE, Constants.ROUNDING_MODE);
        for (CreditPayment payment : payments) {
            totalAmount = totalAmount.add(payment.getAmount());
        }
        return totalAmount.setScale(Constants.OUTPUT_AMOUNT_SCALE,
                Constants.ROUNDING_MODE);
    }


    /**
     * Рассчитывает ежемесячную комиссию по офферу.
     *
     * @param creditAmount размер кредита.
     * @param creditOffer оффер.
     * @return ежемесячная комиссия.
     */
    static BigDecimal calcMonthlyCommission(final BigDecimal creditAmount,
                                            final CreditOffer creditOffer) {

        BigDecimal monCommAmount = creditOffer.getMonthlyCommissionAmount();
        BigDecimal monCommPercent = creditOffer.getMonthlyCommissionPercent();

        BigDecimal monComm = new BigDecimal(0)
                .setScale(Constants.CALC_SCALE, Constants.ROUNDING_MODE);
        if (monCommAmount != null) {
            monComm = monComm.add(monCommAmount);
        }
        if (monCommPercent != null) {
            monComm = monComm.add(creditAmount.multiply(monCommPercent));
        }
        return monComm.setScale(Constants.OUTPUT_AMOUNT_SCALE, Constants.ROUNDING_MODE);
    }

    /**
     * Рассчитывает изначальную комиссию (не входит в ежемесячные платежи).
     *
     * @param creditAmount размер кредита.
     * @param creditOffer оффер.
     * @return начальная комиссия (не входит в ежемесячные платежи).
     */
    static BigDecimal calcInitialCommission(final BigDecimal creditAmount,
                                            final CreditOffer creditOffer) {

        BigDecimal initCommPay = new BigDecimal("0")
                .setScale(Constants.OUTPUT_AMOUNT_SCALE);
        BigDecimal onceCommAmount = creditOffer.getOnceCommissionAmount();
        if (onceCommAmount != null) {
            initCommPay = initCommPay.add(onceCommAmount);
        }
        BigDecimal onceCommPrcnt = creditOffer.getOnceCommissionPercent();
        if (onceCommPrcnt != null) {
            initCommPay = initCommPay.add(creditAmount.multiply(onceCommPrcnt));
        }
        return initCommPay.setScale(Constants.OUTPUT_AMOUNT_SCALE, Constants.ROUNDING_MODE);
    }

    /**
     * Используйте для расчета эффективной процентоной ставки по предложению.
     *
     * @param proposal кредитное предложение.
     * @return эффективаная процентная ставка в долях от единицы.
     */
    static BigDecimal calcEffectiveRate(CreditProposal proposal) {

        // Метод Ньютона (касательных)
        final double eps = 0.00001d; // погрешность
        // начальное значение множителя дисконирования
        double x = 1d;
        double currentEps = 1d;    // текущая погрешность
        double er = 0d;              // эффективная процентная ставка
        final int paymentCount = proposal.getPayments().size();

        while (currentEps > eps) {

            // 1. Инициализация переменных
            double fx = 0d;
            double dfx = 0d;

            // 2. Цикл для вычисления сумм в функции и производной в точке х
            for (int i = 1; i <= paymentCount; i++) {
                CreditPayment payment = proposal.getPayments().get(i - 1);
                fx += payment.getAmount().doubleValue() * Math.pow(x, i);
                dfx += i*payment.getAmount().doubleValue() * Math.pow(x, i - 1);
            }

            // 3. Для функции f(x) вычтем размер кредита
            fx -= proposal.getCreditAmount().doubleValue();
            //    и добавим первоначальную комиссию
            if (proposal.getInitialCommission() != null) {
                fx += proposal.getInitialCommission().doubleValue();
            }

            // 4. Шаг при поиске корня методом Ньютона
            x -= fx / dfx;

            // 5. Расчет погрешности - разница между предыдущим значением
            //    ЭПС и вычисленным.
            double previousER = er;
            er = Math.pow(x, -CalendarUtil.NUMBER_OF_MONTHS) - 1;
            currentEps = Math.abs(previousER - er);
        }

        return new BigDecimal(er).setScale(Constants.CALC_SCALE,
                                           Constants.ROUNDING_MODE);
    }
}
