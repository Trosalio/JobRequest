package client;

import client.controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import java.io.IOException;

public class ClientMain extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("/config/Spring-Client-Module.xml");
        MainController mc = context.getBean("mainController", MainController.class);
        mc.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
