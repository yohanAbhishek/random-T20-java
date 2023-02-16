package PostGameScenes;

import GameClasses.Match;
import GameClasses.Summary;
import GameClasses.TopPlayer;
import GameClasses.TournamentStanding;
import GameScenes.GameMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostGameController {
    @FXML private BarChart<?, ?> scoreSummaryChart;

    @FXML private TableView<Summary> summaryTable; //table for summary
    @FXML private TableColumn<Summary, String> scoreColumn, ballsColumn, wicketsColumn, winColumn;

    @FXML private TableView<TopPlayer> top5BatsmanTableView; // table for top5 batsman
    @FXML private TableColumn<TopPlayer, String> columnBatID, columnBatName, columnBatScore;

    @FXML private TableView<TopPlayer> top5BowlersTableView; // table for top5 bowlers
    @FXML private TableColumn<TopPlayer, String> columnBowlID, columnBowlName, columnBowlScore;

    @FXML private TableView<Match> SummaryTeamCombTableView; // table to get team combination to view summary
    @FXML private TableColumn<Match, String> summaryCombinationColumn, summaryStatusColumn ;
    @FXML private AnchorPane anchorPane3;
    @FXML private Label errorLabel, team1Label, team2Label;
    private static String team1 = GameMenuController.getTeam1();
    private static String team2 = GameMenuController.getTeam2();

    @FXML private TableView<TournamentStanding> tournamentTable; // table for tournament
    @FXML private TableColumn<TournamentStanding, String> columnTournamentTeam, columnTournamentScore;

    @FXML private void matchSummary() { // get selected row(match Combination) from the table
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../PostGameScenes/matchSummary.fxml"));
            anchorPane3.getChildren().setAll(pane);
            team1 = SummaryTeamCombTableView.getSelectionModel().getSelectedItem().getMatchCombination().split(" ")[0];
            team2 = SummaryTeamCombTableView.getSelectionModel().getSelectedItem().getMatchCombination().split(" ")[2];
        } catch (NullPointerException | IOException e){
            errorLabel.setText("Please select an option from the table");
        }
    }

    @FXML private void loadSummary(){
        Summary summary = new Summary();
        String team1Status="Not played", team2Status="Not played";
        if (summary.calcScore(team1 + team2)>summary.calcScore(team2 + team1)){
            team1Status = "Won";
            team2Status = "Lost";
        } else if(summary.calcScore(team1 + team2)<summary.calcScore(team2 + team1)){
            team1Status = "Lost";
            team2Status = "Won";
        }
        final ObservableList<Summary> summaries = FXCollections.observableArrayList(
        new Summary(summary.calcScore(team1 + team2),
            summary.calcTotalBalls(team1 + team2),
            String.valueOf(Integer.parseInt(summary.calcTotalWickets(team1 + team2))+1), team1Status),
        new Summary(summary.calcScore(team2 + team1),
            summary.calcTotalBalls(team2 + team1),
            String.valueOf(Integer.parseInt(summary.calcTotalWickets(team2 + team1))+1), team2Status)
        );
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        ballsColumn.setCellValueFactory(new PropertyValueFactory<>("totalBalls"));
        wicketsColumn.setCellValueFactory(new PropertyValueFactory<>("totalWickets"));
        winColumn.setCellValueFactory(new PropertyValueFactory<>("win"));
        summaryTable.getItems().setAll(summaries);
        team1Label.setText("Team "+team1);
        team2Label.setText("Team "+team2);
    }

    @FXML private void loadTop5Batsman(){
        TopPlayer batsmanAction = new TopPlayer();
        List<String> top5 = batsmanAction.getTop5("batsman");
        List<String> top5Names = batsmanAction.getNameFromID(top5);
        batsmanAction.addDataToTable(top5, top5Names,top5BatsmanTableView);
        batsmanAction.setCellFactoryValues( columnBatScore, columnBatName, columnBatID);
    }

    @FXML
    private void loadTop5Bowlers() {
        TopPlayer bowlerAction = new TopPlayer();
        List<String> top5 = bowlerAction.getTop5("bowlers");
        List<String> top5Names = bowlerAction.getNameFromID(top5);
        bowlerAction.addDataToTable(top5, top5Names, top5BowlersTableView);
        bowlerAction.setCellFactoryValues(columnBowlScore, columnBowlName, columnBowlID);

    }

    @FXML private void loadTournamentStanding(){
        TournamentStanding standingsAction = new TournamentStanding();
        ArrayList<String> teamsScores= standingsAction.convertHashMapToArrayList();
        for (String teamsScore : teamsScores) {
            columnTournamentScore.setCellValueFactory(new PropertyValueFactory<>("score"));
            columnTournamentTeam.setCellValueFactory(new PropertyValueFactory<>("team"));
            TournamentStanding standing = new TournamentStanding(teamsScore.split(",")[1],
                    teamsScore.split(",")[0]);
            tournamentTable.getItems().add(standing);
        }
        columnTournamentScore.setSortType(TableColumn.SortType.DESCENDING);
        tournamentTable.getSortOrder().add(columnTournamentScore);
        tournamentTable.sort();
    }

    @FXML private void loadMatchesToTable(){
        Match match = new Match();
        summaryCombinationColumn.setCellValueFactory(new PropertyValueFactory<>("matchCombination"));
        summaryStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        SummaryTeamCombTableView.getItems().setAll(match.getMatchCombinations());
    }


    @FXML
    private void handleSummaryChartButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../PostGameScenes/summaryChart.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Tournament standing Box-Chart");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void loadDataIntoChart(){
        TournamentStanding standingsAction = new TournamentStanding();
        ArrayList<String> teamsScores= standingsAction.convertHashMapToArrayList();
        XYChart.Series Data = new XYChart.Series<>();
        for (String teamsScore : teamsScores) {
            Data.getData().add(new XYChart.Data<>(teamsScore.split(",")[0], Integer.parseInt(teamsScore.split(",")[1])));
        }
        scoreSummaryChart.getData().setAll(Data);
    }
}
