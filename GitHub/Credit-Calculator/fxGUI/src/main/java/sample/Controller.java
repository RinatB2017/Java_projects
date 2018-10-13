package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import su.ugatu.moodle.is.credit_calc.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @return Этот класс контролируете все события и действия пользователя
 * {@link Controller}
 */
public class Controller {


    private CreditApplication application;
    private CreditOffer offer;
    //Коллекция данных о кредите
    private ObservableList<CreditData> creditData = FXCollections.observableArrayList();
    //Коллекция типы данных о кредите
    private ObservableList<String> model = FXCollections.observableArrayList();

    @FXML
    private Button btn_res;
    @FXML
    private GridPane grid_pan;
    @FXML
    private Label schema;
    @FXML
    private ImageView im_exit; // Текстура кнопки выхода
    @FXML
    private ImageView im_help; // Текстура кнопки справки
    @FXML
    private Text text_res; // Текст результата эффективный процентный ставки
    @FXML
    private ComboBox combo_type; // Элемент выбора схем погашения
    @FXML
    private TableView<CreditData> table; // Таблица результатов расчета
    @FXML
    private TableColumn<CreditData,Integer> num; // Колонка номер платежа
    @FXML
    private TableColumn<CreditData,String> paymentDateCol; // Колонка Дата платежа
    @FXML
    private TableColumn<CreditData,String> amountClo; // Колонка Сумма платежа
    @FXML
    private TableColumn<CreditData,String> principalCol; // Колонка Основной долг
    @FXML
    private TableColumn<CreditData,String> accruedInterestCol; // Колонка Начисленные проценты
    @FXML
    private TableColumn<CreditData,String> monthlyCommissionCol; // Колонка Ежемесячные комиссии
    @FXML
    private TableColumn<CreditData,String> balancePayableCol; // Колонка Остаток задолженности
    @FXML
    private TextField amount; // Текстовое поле Сумма кредита
    @FXML
    private TextField durationInMonths; // Текстовое поле  Срок кредита
    @FXML
    private TextField interestRate; // Текстовое поле  Процентная ставка
    @FXML
    private TextField onceCommissionAmount; // Текстовое поле  Разовая комиссия (в деньгах)
    @FXML
    private TextField onceCommissionPercent; // Текстовое поле  Разовая комиссия (в процентах)
    @FXML
    private TextField monthlyCommissionAmount; // Текстовое поле  Ежемесячная комиссия (в деньгах)
    @FXML
    private TextField monthlyCommissionPercent; // Текстовое поле  Ежемесячная комиссия (в процентах)

    @FXML
    private Label iab_one_pr;// Текст Разовая комиссия (в процентах)
    @FXML
    private Label lab_one_mny;// Текст Разовая комиссия (в деньгах)
    @FXML
    private Label lab_month_pr;// Текст Ежемесячная комиссия (в процентах)
    @FXML
    private Label lab_month_mny;// Текст Ежемесячная комиссия (в деньгах)


    /**
     * @return Метод для начальной инициализации данных {@link #initialize()}
     */
    @FXML
    private void initialize() {
        // Инициализация текстур
        im_help.setImage(new Image("img/help.png"));
        im_exit.setImage(new Image("img/exit_def.png"));

        model.addAll("Аннуитетная", "Дифференцированная");
        combo_type.setPrefSize(298, 28);
        combo_type.setItems(model); // Добавление данные схем погашения
        combo_type.getSelectionModel().select(0); // Начальная состояния "Аннуитетная"
        selectType(true); // ночальная положения компонентов

        combo_type.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if(s2.equals("Дифференцированная")){
                    selectType(false);
                }
                else {
                    selectType(true);
                }
            }
        });
        // Инициализация текста
        text_res.setText("Кредитный калькулятор позволяет\n" +
                "построить график погашения\n" +
                "произвольного кредита,а также\n" +
                "рассчитать эффективную процентную\n" +
                "ставку по кредиту.");

        // Устанавливаем тип и значение которое должно хранится в колонке
        num.setCellValueFactory(new PropertyValueFactory<CreditData, Integer>("num"));
        paymentDateCol.setCellValueFactory(new PropertyValueFactory<CreditData, String>("paymentDateCol"));
        amountClo.setCellValueFactory(new PropertyValueFactory<CreditData, String>("amountClo"));
        principalCol.setCellValueFactory(new PropertyValueFactory<CreditData, String>("principalCol"));
        accruedInterestCol.setCellValueFactory(new PropertyValueFactory<CreditData, String>("accruedInterestCol"));
        monthlyCommissionCol.setCellValueFactory(new PropertyValueFactory<CreditData, String>("monthlyCommissionCol"));
        balancePayableCol.setCellValueFactory(new PropertyValueFactory<CreditData, String>("balancePayableCol"));

        // Заполняем таблицу данными
        table.setPlaceholder(new Text(""));
        table.setItems(creditData);
    }
    /**
     * @return Метод выбора схему погашения
     */
    private void initData() {

        if(combo_type.getSelectionModel().isSelected(1)){
            application.setPaymentType(CreditPaymentType.DIFFERENTIAL); // Выбрана "Дифференцированная"
            CreditProposal proposal = offer.calculateProposal(application);
            printProposal(proposal); // Печать результата
        }
        else{
            application.setPaymentType(CreditPaymentType.ANNUITY);// Выбрана "Аннуитетная"
            CreditProposal proposal = offer.calculateProposal(application);
            printProposal(proposal); // Печать результата
        }
    }

    /**
     * @return Событие расчёта данных и занесение их в таблицу
     */
    @FXML
    public void onClickMethod(){
        creditData.clear(); // Очистка таблицы
        if(control(amount) && control(durationInMonths) && control(interestRate)){ // Если все три поле числа то..
            application = new CreditApplicationImpl(new BigDecimal(amount.getText())); // Возвращение текста сумма кредита в расчет
            application.setDurationInMonths(Integer.valueOf(durationInMonths.getText())); // Возвращение текста срок кредита в расчет
            offer = new CreditOfferImpl();
            offer.setRate(new BigDecimal(interestRate.getText()).divide(new BigDecimal(100)));// Возвращение текста процентная савка в расчет

            // Проверки на числа комиссии
            if(control(onceCommissionAmount))
                offer.setOnceCommissionAmount(new BigDecimal(onceCommissionAmount.getText()));
            else
                onceCommissionAmount.setText("0");
            if(control(onceCommissionPercent))
                offer.setOnceCommissionPercent(new BigDecimal(onceCommissionPercent.getText()).divide(new BigDecimal(100)));
            else
                onceCommissionPercent.setText("0");
            if(control(monthlyCommissionPercent))
                offer.setMonthlyCommissionPercent(new BigDecimal(monthlyCommissionPercent.getText()).divide(new BigDecimal(100)));
            else
                monthlyCommissionPercent.setText("0");
            if(control(monthlyCommissionAmount))
                offer.setMonthlyCommissionAmount(new BigDecimal(monthlyCommissionAmount.getText()));
            else
                monthlyCommissionAmount.setText("0");

            initData(); // Метод выбора схему погашения
        }

    }

    /**
     * @return Метод выхода из программы
     */
    @FXML
    public void onExit(){
        System.exit(0);
    }

    /**
     * @return Метод вызова справки
     * @throws Exception
     */
    @FXML
    public void onClickHelp() throws Exception {
        new HelpDialog(); // Объект окна справки
    }

    /**
     * @return Метод наведение курсора на кнопку выхода
     */
    @FXML
    public void onHover(){
        im_exit.setImage(new Image("img/exit.png"));
        im_help.setImage(new Image("img/help_exit.png"));
    }

    /**
     * @return Метод деактивированные курсора на кнопку выхода
     */
    @FXML
    public void onHotHover(){
        im_exit.setImage(new Image("img/exit_def.png"));
        im_help.setImage(new Image("img/help.png"));
    }

    /**
     * @return Метод наведение курсора на кнопку справки
     */
    @FXML
    public void onHoverHelp(){
        im_help.setImage(new Image("img/help_hover.png"));
    }

    /**
     * @return Метод деактивированные курсора на кнопку справки
     */
    @FXML
    public void onHotHoverHelp(){
        im_help.setImage(new Image("img/help.png"));
    }

    /**
     * @param tf входной параметр проверки
     * @return Метод проверки на число
     */
    private boolean control(TextField tf){
        double d;
        try {
            d = Double.parseDouble(tf.getText());
            if(d < 0) tf.setText(""+Math.abs(d)); // Если введен отрицательный число то берется по модулю
        }
        catch (NumberFormatException e){
            tf.setText("");
            tf.setPromptText("Введите число");
            return false;
        }
        return true;
    }

    /**
     * @return Метод печать результата
     * @param proposal - Заявка на получение кредита
     */
    private void printProposal(CreditProposal proposal){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");// Форматирования даты
        DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Форматирования числа
        List<CreditPayment> payments = proposal.getPayments();
        text_res.setText("Всего: "+decimalFormat.format(proposal.getTotalPayment())+" денежных единиц\n"
                        +"Эффективная процентная ставка: "+(decimalFormat.format(proposal.getEffectiveRate().doubleValue() * 100)) + "%\n"
                        +"Комиссия: " + decimalFormat.format(proposal.getTotalCreditCommission()));
        int i = 1;
        // Вывод результатов на таблицу
        for (CreditPayment payment: payments) {
            creditData.add(new CreditData(
                    (i++),
                    "" + dateFormat.format(payment.getDate()),
                    "" + decimalFormat.format(payment.getAmount()),
                    "" + decimalFormat.format(payment.getDebt()),
                    "" + decimalFormat.format(payment.getInterest()),
                    "" + decimalFormat.format(payment.getCommission()),
                    "" + decimalFormat.format(payment.getTotalLeft())));
        }

    }

    /**
     * @return Метод расстановки компонентов в зависемости comboBox
     * @param select - индекатор расположения
     */
    private void selectType(Boolean select){
        // если выбрано Дифференцированная
        if(select){
            // Установка видемости текставых полей
            lab_one_mny.setVisible(false);
            onceCommissionAmount.setVisible(false);
            onceCommissionAmount.setText("");
            iab_one_pr.setVisible(false);
            onceCommissionPercent.setVisible(false);
            onceCommissionPercent.setText("");
            lab_month_mny.setVisible(true);
            monthlyCommissionAmount.setVisible(true);
            monthlyCommissionAmount.setText("");
            lab_month_pr.setVisible(false);
            monthlyCommissionPercent.setVisible(false);
            monthlyCommissionPercent.setText("");

            // Перестановка мест компонентов
            grid_pan.getChildren().removeAll(monthlyCommissionAmount,
                    lab_month_mny,schema,combo_type,btn_res,onceCommissionPercent,
                    iab_one_pr,onceCommissionAmount,lab_one_mny,monthlyCommissionPercent,
                    lab_month_pr);
            grid_pan.add(monthlyCommissionAmount,1,3);
            grid_pan.add(lab_month_mny,0,3);
            grid_pan.add(schema,0,4);
            grid_pan.add(combo_type,1,4);
            grid_pan.add(btn_res,1,5);
        }
        // если выбрано Аннуитетная
        else {
            // Установка видемости текставых полей
            lab_one_mny.setVisible(true);
            onceCommissionAmount.setVisible(true);
            iab_one_pr.setVisible(true);
            onceCommissionPercent.setVisible(true);
            lab_month_mny.setVisible(false);
            monthlyCommissionAmount.setVisible(false);
            monthlyCommissionAmount.setText("");
            lab_month_pr.setVisible(true);
            monthlyCommissionPercent.setVisible(true);

            // Перестановка мест компонентов
            grid_pan.getChildren().removeAll(monthlyCommissionAmount,
                    lab_month_mny,schema,combo_type,btn_res,onceCommissionPercent,
                    iab_one_pr,onceCommissionAmount,lab_one_mny,monthlyCommissionPercent,
                    lab_month_pr);
            grid_pan.add(onceCommissionPercent,1,3);
            grid_pan.add(iab_one_pr,0,3);
            grid_pan.add(onceCommissionAmount,1,4);
            grid_pan.add(lab_one_mny,0,4);
            grid_pan.add(monthlyCommissionPercent,1,5);
            grid_pan.add(lab_month_pr,0,5);
            grid_pan.add(schema,0,6);
            grid_pan.add(combo_type,1,6);
            grid_pan.add(btn_res,1,7);
        }
    }
}
