package univ.syllogismverificator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Traductor {
    private static String lang ;
    private static HashMap<String, String> dictionnaire = new HashMap<>();
    public Traductor() {
        try {
            File myObj = new File("src/main/resources/data/config.txt");
            Scanner myReader = new Scanner(myObj);
            lang = myReader.nextLine();
            Object o = new JSONParser().parse(new FileReader("src/main/resources/data/traduction.json"));
            JSONArray j = (JSONArray) o;
            for (Object object : j) {
                JSONObject tempObject = (JSONObject) object;
                dictionnaire.put((String) tempObject.get("key"), (String) tempObject.get(lang)) ;
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String key) {
        return dictionnaire.get(key) ;
    }
}
