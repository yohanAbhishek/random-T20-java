package PreGameScenes;

import GameClasses.Player;
import GameClasses.Utilities;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;

public class PreGameController extends Application {

    @FXML private TextField nameTextFieldInput, ageTextFieldInput;
    @FXML private AnchorPane preGameMenuAnchorPane, logInAnchorPane;
    @FXML private Label menuTeamLabel, showMessage;
    @FXML private TableView<Player> tableView;
    @FXML private TableColumn<Player, String> nameColumn, ageColumn, IdColumn;
    @FXML private BorderPane mainPane;
    private static String saveTeam, saveGroup;
    String fileName = "src/profileStorage/"+saveGroup+"/"+saveTeam+".txt";
    Utilities utilities = new Utilities();

    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../PreGameScenes/logIn.fxml"));
        primaryStage.setTitle("Cricket Simulator");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @FXML private void login(ActionEvent event) throws IOException {
        Pane view;
        if (utilities.validateIfAllProfilesCreated()){
            FXMLLoader loader= new FXMLLoader(getClass().getResource("../GameScenes/gameMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else{
            view = utilities.getPage("../PreGameScenes/selectTeam");
            logInAnchorPane.getChildren().setAll(view);
        }
    }

    public void getTeam(String groupField, String teamField){
        menuTeamLabel.setText("You are in group: "+groupField+" | "+teamField);
        saveTeam = teamField;
        saveGroup = groupField;
    }

    @FXML private void logUser(){
        Pane view = utilities.getPage("../PreGameScenes/tableView");
        mainPane.setCenter(view);
    }

    @FXML private void loadTableData() {
        utilities.loadPlayersToTable(tableView, fileName);
        utilities.setFactoryValues(nameColumn, ageColumn, IdColumn);
    }

    @FXML private void startMatch(ActionEvent event) throws IOException {
        FXMLLoader loader;
        if (utilities.validateIfAllProfilesCreated()){
            loader= new FXMLLoader(getClass().getResource("../GameScenes/gameMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            showMessage.setText("All profiles not created!");
        }
    }

    @FXML private void goBack() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../PreGameScenes/selectTeam.fxml"));
        preGameMenuAnchorPane.getChildren().setAll(pane);
    }

    @FXML private void addPlayerProfile() throws IOException {
        if (!ageTextFieldInput.getText().equals("") && !nameTextFieldInput.getText().equals("")){
            showMessage.setText(nameTextFieldInput.getText()+" added. Player Id: "+Player.generateID(fileName));
            Player player = new Player(ageTextFieldInput.getText(), nameTextFieldInput.getText());
            tableView.getItems().setAll();
            if (player.addPlayerToTextFile(player, fileName, showMessage)){
                loadTableData();
            }
            ageTextFieldInput.clear();
            nameTextFieldInput.clear();
        } else {
            showMessage.setText("Please fill in all details to save profile!");
        }
    }

    @FXML private void deletePlayer() throws IOException {
        if (tableView.getSelectionModel().getSelectedItem()!=null){
            String age = tableView.getSelectionModel().getSelectedItem().ageProperty().get();
            String name = tableView.getSelectionModel().getSelectedItem().nameProperty().get();
            String Id = tableView.getSelectionModel().getSelectedItem().IdProperty().get();
            utilities.removeProfileFromTextFile(name, age, Id, saveGroup, saveTeam);
            showMessage.setText("Successfully removed "+name);
            tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());
        } else {
            showMessage.setText("Please select a profile to delete");
        }
    }

    @FXML private void editPlayer() throws IOException {
        try {
            nameTextFieldInput.setText(tableView.getSelectionModel().getSelectedItem().nameProperty().getValue());
            ageTextFieldInput.setText(tableView.getSelectionModel().getSelectedItem().ageProperty().getValue());
            deletePlayer();
        } catch (NullPointerException e){
            showMessage.setText("Please select a profile to make changes");
        }
    }
}
