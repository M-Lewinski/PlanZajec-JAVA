package GUI;

import dataBase.MySql;
import dataBase.generator.Generator;
import dataBase.structure.Kierunek;
import dataBase.structure.Wydzial;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
//        launch(args);
//        MySql baza = new MySql("admin1","zxc");
        MySql.setUser("admin","zxc");
        MySql baza = MySql.getInstance();
        if (baza==null){
            System.err.println("BAZA NULL");
            return;
        }
        Generator generator = new Generator();
//        generator.createWydzialy();
//        generator.createKierunki();
//        generator.createSqlObjectsFromFiles(new Wydzial(),0,new String[]{"wydzialy.txt"});
//        generator.createSqlObjectsFromFiles(new Kierunek(),0,new String[]{"kierunki.txt"});
        baza.closeConnect();
    }
}
