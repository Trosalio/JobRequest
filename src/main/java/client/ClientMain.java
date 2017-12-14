package client;

import client.controller.ActionController;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/config/Spring-Client-Module.xml");
        ActionController handler = context.getBean("actionController", ActionController.class);
        handler.handleLoad();
        handler.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
