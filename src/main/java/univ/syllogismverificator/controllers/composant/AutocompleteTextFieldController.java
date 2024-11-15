package univ.syllogismverificator.controllers.composant;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text ;
import javafx.scene.paint.Color ;

import java.util.*;

import javafx.scene.control.Label ;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextFlow;

public class AutocompleteTextFieldController extends TextField {
    //entries to autocomplete
    public SortedSet<String> entries;
    //popup GUI
    private ContextMenu entriesPopup;

    boolean myBool = true ;

    public AutocompleteTextFieldController() {
        super();
        this.entries = new TreeSet<>();
        this.entriesPopup = new ContextMenu();

        setListner();
    }

    private void setListner() {
        //Add "suggestions" by changing text
        textProperty().addListener((observable, oldValue, newValue) -> {
            String enteredText = getText();
            //always hide suggestion if nothing has been entered (only "spacebars" are dissalowed in TextFieldWithLengthLimit)
            //build popup - list of "CustomMenuItem"
            populatePopup(new ArrayList<String>(entries));
            if (!entriesPopup.isShowing()) { //optional
                entriesPopup.show(AutocompleteTextFieldController.this, Side.BOTTOM, 0, 0); //position of popup
            }
            //no suggestions -> hide

        });

        //Hide always by focus-in (optional) and out
        focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue){
                populatePopup(new ArrayList<>(entries));
                entriesPopup.show(AutocompleteTextFieldController.this, Side.BOTTOM, 0, 0);
            } else {
                entriesPopup.hide();
            }
        });
    }

    private void populatePopup(List<String> searchResult) {
        //List of "suggestions"
        List<CustomMenuItem> menuItems = new LinkedList<>();
        //List size - 10 or founded suggestions count
        int count = searchResult.size();
        //Build list as set of labels
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            //label with graphic (text flow) to highlight founded subtext in suggestions
            Label entryLabel = new Label();
            entryLabel.setText(result);
            entryLabel.setPrefHeight(15);  //don't sure why it's changed with "graphic"
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            menuItems.add(item);

            //if any suggestion is select set it into text and close popup
            item.setOnAction(actionEvent -> {
                setText(result);
                positionCaret(result.length());
                entriesPopup.hide();
            });
        }

        //"Refresh" context menu
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }

    public static TextFlow buildTextFlow(String text, String filter) {
        int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
        Text textBefore = new Text(text.substring(0, filterIndex));
        Text textAfter = new Text(text.substring(filterIndex + filter.length()));
        Text textFilter = new Text(text.substring(filterIndex,  filterIndex + filter.length())); //instead of "filter" to keep all "case sensitive"
        textFilter.setFill(Color.ORANGE);
        textFilter.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
        return new TextFlow(textBefore, textFilter, textAfter);
    }
}
