package dataBase.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by lewin on 11/17/16.
 */
public class Generator {
    public Generator() {

    }
    /**
     * Czytanie z pliku linii i zapisywanie ich do listy, która jest zwracana.
     * @param filePath - Ścieżka do pliku.
     */
    private List<String> readFile(String filePath){
        BufferedReader buffer = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filePath)));
        List listaLinii = new ArrayList<String>();
        try {
            String wydzial;
            while((wydzial = buffer.readLine())!= null){
                listaLinii.add(wydzial);
            }
        }
        catch (IOException e){
            System.err.println("Generowanie z pliku");
            e.printStackTrace();
        }
        finally {
            try {
                buffer.close();
            }
            catch (IOException e){
                System.err.println("Zamykanie buffora");
                e.printStackTrace();
            }
        }
        return listaLinii;
    }

}
