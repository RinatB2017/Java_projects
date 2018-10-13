package sample;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * @return Класс выводит окно справки {@link #HelpDialog()}
 */
public class HelpDialog {

    public HelpDialog(){
        Stage stage = new Stage(); // Создания окна
        stage.setTitle("Справка");
        StackPane root = new StackPane(); // Создания панели
        WebView view = new WebView(); // Создания веб компонента
        WebEngine engine = view.getEngine(); // Создания вида
        // Занесения в веб компонент файл "help.html"
        engine.load(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("html/help.html")));
        root.getChildren().add(view); // Добавление панели в веб компонент

        Scene scene = new Scene(root, 700, 500); // Установка размера окна
        stage.setScene(scene); // Добавление сцены в окно
        stage.show(); // Вывод окна
    }
}
