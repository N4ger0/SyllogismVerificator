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

/**
 * Class for implementing a text field with a menu including proposition for the text field
 */
public class AutocompleteTextFieldController extends TextField {
    /**
     * Set of entry who will then be put in the popup for completion
     */
    public Set<String> entries;
    /**
     * FXML element who contains the completion MenuItem
     */
    private ContextMenu entriesPopup;

    /**
     * Constructor for AutocompleteTextField initalise all the entry to empty
     */
    public AutocompleteTextFieldController() {
        super();
        this.entries = new TreeSet<>();
        this.entriesPopup = new ContextMenu();
        setListner();
    }

    /**
     * Function called in the constructor, no need to call it manually. Set event for building and
     * showing the popup on focusing in and out.
     */
    private void setListner() {
        textProperty().addListener((observable, oldValue, newValue) -> {
            populatePopup(new ArrayList<String>(entries));
            if (!entriesPopup.isShowing()) {
                entriesPopup.show(AutocompleteTextFieldController.this, Side.BOTTOM, 0, 0); //position of popup
            }
        });

        focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue){
                populatePopup(new ArrayList<>(entries));
                entriesPopup.show(AutocompleteTextFieldController.this, Side.BOTTOM, 0, 0);
            } else {
                entriesPopup.hide();
            }
        });
    }

    /**
     * Function for creating the popup, called automatically by the events.
     * @param searchResult list of the entries to put in menu
     */
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
}
