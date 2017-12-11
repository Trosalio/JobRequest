package client;

import client.controller.MainController;
import common.service.AdvertiseService;
import common.service.JobService;
import common.service.StationService;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import java.io.IOException;

public class ClientMain extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        ClientMain.primaryStage = stage;
        ApplicationContext context = new ClassPathXmlApplicationContext("config/Spring-Client-Module.xml");
        MainController mc = context.getBean("mainController", MainController.class);
        mc.handleLoad();
        mc.start();
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
