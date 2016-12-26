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
    private static FXMLLoader loader = new FXMLLoader();
    private static Scene currentScene;
    private static Parent rootLayout;
    private static Controller controller;

    public static void changeScene(String title,String resource) throws Exception{
        try {
            Main.loader.setLocation(Main.class.getResource(resource));
            Main.rootLayout = Main.loader.load();
            Main.controller = Main.loader.getController();
            Scene newScene = new Scene(Main.rootLayout);
            Main.currentScene = newScene;
            Main.currentPrimaryStage.setTitle(title);
            Main.currentPrimaryStage.setScene(newScene);
            Main.controller.setStyleSheets(newScene);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("LoginMenu/LoginMenu.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("ScheduleMenu/Schedule.fxml"));
        Main.currentPrimaryStage = primaryStage;
        Main.changeScene("Logging Screen","LoginMenu/LoginMenu.fxml");
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
//        MySql baza = new MySql("admin1","zxc");
        MySql.setUser("admin","zxc");
        MySql baza = MySql.getInstance();
        if (baza==null){
            System.err.println("BAZA NULL");
            return;
        }
        Generator generator = new Generator();
//        generator.createSqlObjectsFromFiles(new Wydzial(),0,new String[]{"wydzialy.txt"});
//        generator.createSqlObjectsFromFiles(new Kierunek(),0,new String[]{"kierunki.txt"});
//        generator.createSqlObjectsFromFiles(new Sala(),0,new String[]{"sale.txt"});
//        generator.createSqlObjectsFromFiles(new Semestr(),0,new String[]{"semestry.txt"});
//        generator.createSqlObjectsFromFiles(new Prowadzacy(),1,new String[]{"NameList.txt","SurnameList.txt"});
        baza.closeConnect();
        launch(args);
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
}
