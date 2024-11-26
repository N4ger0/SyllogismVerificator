package univ.syllogismverificator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Traductor used to translate text in the app
 */
public class Traductor {
    /**
     * The language chosen by the user, either fr or en
     */
    private static String lang ;
    /**
     * Dictionnary of the app, hashmap with an id, and the following translation depending
     * of the selected language
     */
    private static HashMap<String, String> dictionnaire = new HashMap<>();

    /**
     * Constructor, read the selected language in config.txt, then build the
     * dictionnary with traduction.json
     */
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

    /**
     * getter for lang
     * @return String, en or fr
     */
    public static String getLang(){
        return lang;
    }

    /**
     * Get a traduction
     * @param key the id of the traduction
     * @return String the traduction
     */
    public static String get(String key) {
        return dictionnaire.get(key) ;
    }

    /**
     * Write in the config.txt file the language selected
     */
    public void save() {
        try {
            File myObj = new File("src/main/resources/data/config.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(myObj));
            writer.write(lang);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set the lang when the user changes it
     * @param lang1 the new lang code
     */
    public void setLang(String lang1){
        lang = lang1;
        save();
        new Traductor();
    }
}
