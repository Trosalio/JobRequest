package server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import server.serviceImpl.AdvertiseServiceImpl;
import server.serviceImpl.JobServiceImpl;
import server.serviceImpl.StationServiceImpl;


public class ServerMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/config/Sprint-Server-Module.xml");
        AdvertiseServiceImpl advertiseService = (AdvertiseServiceImpl) context.getBean("advertiseServiceImpl");
        JobServiceImpl jobService = (JobServiceImpl) context.getBean("jobServiceImp");
        StationServiceImpl stationService = (StationServiceImpl) context.getBean("stationImpl");
        System.out.println("Server is running!");
    }
}
