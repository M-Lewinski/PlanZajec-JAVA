package GUI;

import dataBase.MySql;
import dataBase.actors.Prowadzacy;
import dataBase.generator.Generator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage currentPrimaryStage;
    private static FXMLLoader loader;
    private static Scene currentScene;
    private static Parent rootLayout;
    private static Controller controller;
    private static MySql.Refresher refresher;
    private static  boolean firstMax = false;

    public static void changeScene(String title,String resource) throws Exception{
        try {
            Main.loader = new FXMLLoader();
            Main.loader.setLocation(Main.class.getResource(resource));
            Main.rootLayout = Main.loader.load();
            Main.controller = Main.loader.getController();

            double oldWidth = Main.currentPrimaryStage.getWidth();
            double oldHeight = Main.currentPrimaryStage.getHeight();
            boolean isMax = Main.currentPrimaryStage.isMaximized();
            Scene newScene = new Scene(Main.rootLayout);

//
//            if (Main.getCurrentPrimaryStage() != null){
//                if (Main.getCurrentPrimaryStage().getWidth() > oldWidth){
//                    System.out.println("HEJ");
//                    oldWidth = Main.getCurrentPrimaryStage().getWidth();
//                }
//                if(Main.getCurrentPrimaryStage().getHeight() > oldHeight){
//                    oldHeight = Main.getCurrentPrimaryStage().getHeight();
//                }
//            }
            Main.currentScene = newScene;
            Main.currentPrimaryStage.setTitle(title);
            Main.currentPrimaryStage.setScene(newScene);
            if (Main.controller!=null){
                Main.controller.setStyleSheets(newScene);
            }
            if (Main.currentPrimaryStage.getWidth() <= oldWidth)
            Main.currentPrimaryStage.setWidth(oldWidth);
            if (Main.currentPrimaryStage.getHeight() <= oldHeight)
            Main.currentPrimaryStage.setHeight(oldHeight);
            boolean max = Main.currentPrimaryStage.isMaximized();
            if(max && !Main.firstMax){
                Main.currentPrimaryStage.hide();
                Main.currentPrimaryStage.show();
                Main.firstMax = true;
            }
//            Main.currentPrimaryStage.requestFocus();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("LoginMenu/LoginMenu.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("ScheduleMenu/MainMenu.fxml"));
        Main.currentPrimaryStage = primaryStage;

        Main.changeScene("Logging Screen","LoginMenu/LoginMenu.fxml");
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        if(Main.refresher!=null){
            Main.refresher.setThreadIsAlive(false);
            try {
                Main.refresher.getThread().join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
            if(MySql.getInstance()!=null) {
                MySql.getInstance().closeConnect();
            }
    }

    public static Stage getCurrentPrimaryStage() {
        return currentPrimaryStage;
    }

    public static FXMLLoader getLoader() {
        return loader;
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static Parent getRootLayout() {
        return rootLayout;
    }

    public static Controller getController() {
        return controller;
    }

    public static MySql.Refresher getRefresher() {
        return refresher;
    }

    public static void setRefresher(MySql.Refresher refresher) {
        Main.refresher = refresher;
    }
}
