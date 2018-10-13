package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @return Главный класс GUI. Оно начинается с метода {@link #start(javafx.stage.Stage)}
 */
public class Main extends Application {
    private double initX = 0, // Инициализация главного окна по х
                   initY = 0; // Инициализация главного окна по у

    /**
     * @return Метод инициализации компонентов, стилей, сцен.
     * @param stage - главное окно
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Кредитный калькулятор");
        stage.initStyle(StageStyle.TRANSPARENT); // Тип окна без дополнительных элементов окна
        /*
        В объект root заносятся файл "sample.fxml" по типу как xml,
        в нем содержится все атрибутов компонента (кнопки, текстовые поля...)
        такие как id, размер, какие менеджеры расположений, какой стиль(файл style.css)...
         */
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("fxml/sample.fxml"));
        // Установлении размера сцены
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight(),Color.TRANSPARENT);
        stage.setScene(scene); // Добавления сцены в окно
        setUp(stage, scene); // Метод перетаскивания окна
        scene.setFill(Color.TRANSPARENT);
        stage.show(); // Вывод окна
    }

    /**
     * @return Главный метод запуска
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @return Метод перетаскивания окна
     * @param stage - главное окно
     * @param scene - Контейнер(сцена) которые расположена компоненты
     */
    public void setUp(final Stage stage, Scene scene){
        // Нажатая окно
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                initX = me.getScreenX() - stage.getX();
                initY = me.getScreenY() - stage.getY();
            }
        });
        // Разжатая окно
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent me) {
                stage.setX(me.getScreenX() - initX);
                stage.setY(me.getScreenY() - initY);
            }
        });
    }
}
