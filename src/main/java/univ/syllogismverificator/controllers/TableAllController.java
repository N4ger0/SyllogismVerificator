package univ.syllogismverificator.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import univ.syllogismverificator.models.Proposition;
import univ.syllogismverificator.models.Syllogism;
import univ.syllogismverificator.models.SyllogismResult;
import univ.syllogismverificator.models.SyllogismsGenerator;

import java.util.Objects;

public class TableAllController {

    public Button back;
    @FXML
    private TableColumn<SyllogismData, String> figure;
    @FXML
    private TableView<SyllogismData> tableView;
    @FXML
    private TableColumn<SyllogismData, String> qql1;
    @FXML
    private TableColumn<SyllogismData, String> qql2;
    @FXML
    private TableColumn<SyllogismData, String> qql3;
    @FXML
    private TableColumn<SyllogismData, String> validity;
    @FXML
    private TableColumn<SyllogismData, String> interesting;
    @FXML
    private TableColumn<SyllogismData, String> ruu;
    @FXML
    private CheckBox isValid;
    @FXML
    private CheckBox isInteresting;
    @FXML
    private CheckBox isRuu;

    private ObservableList<SyllogismData> data;
    private FilteredList<SyllogismData> filteredData;

    @FXML
    public void initialize(){
        SyllogismsGenerator generator = new SyllogismsGenerator();
        qql1.setCellValueFactory(new PropertyValueFactory<>("qql1"));
        qql2.setCellValueFactory(new PropertyValueFactory<>("qql2"));
        qql3.setCellValueFactory(new PropertyValueFactory<>("qql3"));
        figure.setCellValueFactory(new PropertyValueFactory<>("figure"));
        validity.setCellValueFactory(new PropertyValueFactory<>("validity"));
        interesting.setCellValueFactory(new PropertyValueFactory<>("interesting"));
        ruu.setCellValueFactory(new PropertyValueFactory<>("ruu"));

        data = FXCollections.observableArrayList();
        for (Pair<Syllogism, SyllogismResult> syllogismPair : generator.getSyllogisms()) {
            Syllogism syllogism = syllogismPair.getKey();
            SyllogismResult result = syllogismPair.getValue();
            String valid = "false";
            String interesting = "false";

            // Verification if the syllogism is valid without Ruu.
            if (result.getResults().getFirst().isValid()
                    && result.getResults().get(1).isValid()
                    && result.getResults().get(2).isValid()
                    && result.getResults().get(3).isValid()
                    && result.getResults().get(4).isValid()
                    && result.getResults().get(5).isValid()
                    && result.getResults().get(6).isValid()
            ) {valid = "true";}

            // Verification if the syllogism is interesting.
            if (valid.equals("true") && result.getResults().size() == 9 && result.getResults().get(8).isValid()) {
                interesting = "true";
            }

            data.add(new SyllogismData(syllogism.getPropositions().get(0).toString().split(" ")[0],
                    syllogism.getPropositions().get(1).toString().split(" ")[0],
                    syllogism.getConclusion().toString().split(" ")[0],
                    Integer.toString(getFigureAndProps(syllogism)),
                    valid,
                    interesting,
                    Objects.toString(result.isValid())
            ));
        }

        filteredData = new FilteredList<>(data, p -> true);
        SortedList<SyllogismData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);

        isValid.selectedProperty().addListener((observable, oldValue, newValue) -> filterData());
        back.setOnAction(event -> goToMenu());
        isInteresting.selectedProperty().addListener((observable, oldValue, newValue) -> filterData());
        isRuu.selectedProperty().addListener((observable, oldValue, newValue) -> filterData());
    }

    private void filterData() {
        filteredData.setPredicate(syllogismData -> {
            if (isRuu.isSelected()) {
                return syllogismData.getRuu().equals("true");
            }
            if (isInteresting.isSelected()) {
                return syllogismData.getInteresting().equals("true");
            }
            if (isValid.isSelected()) {
                return syllogismData.getValidity().equals("true");
            }
            return true;
        });
    }

    private int getFigureAndProps(Syllogism syllogism){
        Proposition majorPremise = syllogism.getPropositions().get(0);
        Proposition minorPremise = syllogism.getPropositions().get(1);
        if(majorPremise.subject.equals("p")){
            if(minorPremise.subject.equals("s")) return 1;
            else return 3;
        } else {
            if(minorPremise.subject.equals("s")) return 0;
            else return 2;
        }
    }

    public void goToMenu(){
        SolverController.goToMenu(back);
    }

}
