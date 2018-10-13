package sample;

/**
 * @return Класс отвечает за типы данных в таблице и их get/set каждого поле {@link #CreditData}
 */
public class CreditData {
    private Integer num; // Номер платежа
    private String paymentDateCol, //Дата платежа
                   amountClo, //Сумма платежа
                   principalCol, //Основной долг
                   accruedInterestCol, //Начисленные проценты
                   monthlyCommissionCol, //Ежемесячные комиссии
                   balancePayableCol; //Остаток задолженности
    public CreditData(Integer num, String paymentDateCol, String amountClo, String principalCol, String accruedInterestCol,
                      String monthlyCommissionCol, String balancePayableCol){

        ////// Инициализация всех данных //////
        this.num = num;
        this.paymentDateCol = paymentDateCol;
        this.amountClo = amountClo;
        this.principalCol = principalCol;
        this.accruedInterestCol = accruedInterestCol;
        this.monthlyCommissionCol = monthlyCommissionCol;
        this.balancePayableCol = balancePayableCol;
    }

    //// Добавление мотодов геттеры и сеттеры для данный /////
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getPaymentDateCol() {
        return paymentDateCol;
    }

    public void setPaymentDateCol(String paymentDateCol) {
        this.paymentDateCol = paymentDateCol;
    }

    public String getAmountClo() {
        return amountClo;
    }

    public void setAmountClo(String amountClo) {
        this.amountClo = amountClo;
    }

    public String getPrincipalCol() {
        return principalCol;
    }

    public void setPrincipalCol(String principalCol) {
        this.principalCol = principalCol;
    }

    public String getAccruedInterestCol() {
        return accruedInterestCol;
    }

    public void setAccruedInterestCol(String accruedInterestCol) {
        this.accruedInterestCol = accruedInterestCol;
    }

    public String getMonthlyCommissionCol() {
        return monthlyCommissionCol;
    }

    public void setMonthlyCommissionCol(String monthlyCommissionCol) {
        this.monthlyCommissionCol = monthlyCommissionCol;
    }

    public String getBalancePayableCol() {
        return balancePayableCol;
    }

    public void setBalancePayableCol(String balancePayableCol) {
        this.balancePayableCol = balancePayableCol;
    }
}
