package GUI;

import dataBase.MySql;
import dataBase.actors.Prowadzacy;
import dataBase.generator.Generator;
import dataBase.structure.Kierunek;
import dataBase.structure.Semestr;
import dataBase.structure.Wydzial;
import dataBase.subjects.Sala;
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
//        generator.createSqlObjectsFromFiles(new Wydzial(),0,new String[]{"wydzialy.txt"});
//        generator.createSqlObjectsFromFiles(new Kierunek(),0,new String[]{"kierunki.txt"});
//        generator.createSqlObjectsFromFiles(new Sala(),0,new String[]{"sale.txt"});
//        generator.createSqlObjectsFromFiles(new Semestr(),0,new String[]{"semestry.txt"});
        generator.createSqlObjectsFromFiles(new Prowadzacy(),1,new String[]{"NameList.txt","SurnameList.txt"});
        baza.closeConnect();
    }
}
