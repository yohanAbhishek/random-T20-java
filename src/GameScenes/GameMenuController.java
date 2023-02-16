package GameScenes;

import GameClasses.Match;
import GameClasses.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;

public class GameMenuController {
    @FXML
    private ChoiceBox<String> getHeadOrTail, batOrBowl;
    @FXML
    private AnchorPane matchSelectorAnchorPane, tossAnchorPane, anchorPane2;
    @FXML
    private Label tossWon, displayBatOrBowl, errorLabel, headerLabel;
    @FXML
    private BorderPane gameMenuBorderPane;
    @FXML
    private TableView<Match> matchTeamCombTableView;
    @FXML
    private TableColumn<Match, String> matchCombinationColumn, matchStatusColumn ;

    Utilities utilities = new Utilities();
    private static String team1, team2, tossWin, tossLoss;

    @FXML
    private void loadStartMatch(){
        Pane view = utilities.getPage("../GameScenes/matchTeamCombination");
        gameMenuBorderPane.setCenter(view);
        headerLabel.setText("START MATCH");
    }

    @FXML
    private void loadMatchesToTable(){
        Match match = new Match();
        matchCombinationColumn.setCellValueFactory(new PropertyValueFactory<>("matchCombination"));
        matchStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        matchTeamCombTableView.getItems().setAll(match.getMatchCombinations());
    }

    @FXML
    private void handleLogOutButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../PreGameScenes/logIn.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML private void loadTossOptions(){
        getHeadOrTail.getItems().setAll("Head","Tail");
        batOrBowl.getItems().setAll("Bat", "Bowl");
    }

    @FXML
    private void getTossSelection() {
        try {
            if (!matchTeamCombTableView.getSelectionModel().getSelectedItem().getStatus().equals("Played")){
                team1 = matchTeamCombTableView.getSelectionModel().getSelectedItem().getMatchCombination().split(" ")[0];
                team2 = matchTeamCombTableView.getSelectionModel().getSelectedItem().getMatchCombination().split(" ")[2];
                Pane view = utilities.getPage("../GameScenes/toss");
                matchSelectorAnchorPane.getChildren().setAll(view);
            } else {
                errorLabel.setText("Match already played!");
            }
        } catch (NullPointerException e){
            errorLabel.setText("Please select an option from the table");
        }
    }

    @FXML
    private void tossResult(){
        if (getHeadOrTail.getValue()!=null){
            Random random = new Random();
            String[] sampleSpace = {"Head", "tail"};
            int headOrTail = random.nextInt(sampleSpace.length);
            String randChosenSample = sampleSpace[headOrTail];
            if(getHeadOrTail.getValue().equalsIgnoreCase((randChosenSample))){
                tossWin =  team1;
                tossLoss = team2;
            } else {
                tossWin =  team2;
                tossLoss = team1;
            } tossWon.setText("Team "+tossWin+" Won!");
        } else {
            errorLabel.setText("please select head or tails");
        }

    }

    @FXML
    private void batOrBowl(){
        if (batOrBowl.getValue()!=null){
            displayBatOrBowl.setText("Team "+tossWin+" has decided to "+batOrBowl.getValue());
        } else {
            errorLabel.setText("Please select bat or bowl!");
        }
    }

    @FXML
    private void handleNextButton() throws IOException {
        Pane view = utilities.getPage("../GameScenes/executeMatch");
        tossAnchorPane.getChildren().setAll(view);
        String group1 = tossWin.split("")[0];
        String team1 = tossWin.split("")[1];
        String group2 = tossLoss.split("")[0];
        String team2 = tossLoss.split("")[1];
        Match match = new Match();
        match.runOneInning(Match.getProfiles(team1, group1), Match.getProfiles(team2, group2)); // 1st innings
        match.runOneInning(Match.getProfiles(team2, group2), Match.getProfiles(team1, group1)); //2nd innings
    }

    @FXML
    private void returnToMatchSelect() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../GameScenes/matchTeamCombination.fxml"));
        anchorPane2.getChildren().setAll(pane);
    }

    @FXML
    private void loadMatchSummary(){
        Pane view = utilities.getPage("../PostGameScenes/summaryTeamCombination");
        gameMenuBorderPane.setCenter(view);
        headerLabel.setText("MATCH SUMMARY");
    }

    @FXML
    private void handleTop5BatsmanButton(){
        Pane view = utilities.getPage("../PostGameScenes/top5Batsman");
        gameMenuBorderPane.setCenter(view);
        headerLabel.setText("TOP 5 BATSMAN");
    }

    @FXML
    private void handleTop5BowlersButton(){
        Pane view = utilities.getPage("../PostGameScenes/top5Bowlers");
        gameMenuBorderPane.setCenter(view);
        headerLabel.setText("TOP 5 BOWLERS");
    }

    @FXML
    private void handleTournamentStandingsButton(){
        Pane view = utilities.getPage("../PostGameScenes/tournamentStandings");
        gameMenuBorderPane.setCenter(view);
        headerLabel.setText("TOURNAMENT STANDINGS");
    }

    public static String getTeam1() {
        return team1;
    }

    public static String getTeam2(){
        return team2;
    }
}
