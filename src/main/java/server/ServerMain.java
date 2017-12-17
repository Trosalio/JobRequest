package server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/config/Sprint-Server-Module.xml");
        System.out.println(context.getDisplayName());
        System.out.println("Server is running!");
    }
}
