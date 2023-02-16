package GameClasses;

import PreGameScenes.PreGameController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Utilities implements Initializable{
    @FXML
    private ChoiceBox<String> teamField, groupField;

    @FXML
    private Label errorLabel;

    public String getTeam(){
        return teamField.getValue();
    }

    public String getGroup(){
        return groupField.getValue();
    }

    public Pane getPage(String fileName){
        Pane view = new Pane();
        try {
            URL fileUrl = Utilities.class.getResource(fileName+".fxml");
            if(fileUrl == null){
                throw new java.io.FileNotFoundException("Fxml file can't be found");
            }new FXMLLoader();
            view = FXMLLoader.load(fileUrl);
        } catch (Exception e) {
            System.out.println(fileName+" not found.");
        }return view;
    }

    public void removeProfileFromTextFile(String name, String age, String Id, String saveGroup, String saveTeam) throws IOException {
        ArrayList<String> profile = new ArrayList<>();
        File myObj = new File("src/profileStorage/"+saveGroup+"/"+saveTeam+".txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            profile.add(data);
        }profile.remove(Id+","+age+","+name);

        FileWriter myWriter = new FileWriter("src/profileStorage/"+saveGroup+"/"+saveTeam+".txt");
        for (String s : profile) {
            myWriter.write(s + "\n");
        }
        myWriter.close();
        myReader.close();
    }

    public void selectMenu(ActionEvent event) {
        if (teamField.getValue() != null && groupField.getValue()!=null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../PreGameScenes/mainMenu.fxml"));
            try {
                Parent root = loader.load();
                PreGameController menuController = loader.getController();
                menuController.getTeam(getGroup(), getTeam());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e){
                System.out.println("Loader exception");
            }
        }
        else {
            errorLabel.setText("Please select one option from both choiceBoxes!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teamField.getItems().addAll("team1", "team2","team3","team4");
        groupField.getItems().addAll("A", "B");
    }

    public void loadPlayersToTable(TableView<Player> tableView, String fileName) {
        tableView.getItems().setAll();
        try {
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                List<String> convertedPlayer = Arrays.asList(line.split(",", -1)); // -1 to indicate to perform as many time as possible
                final ObservableList<Player> data = FXCollections.observableArrayList(
                        new Player(convertedPlayer.get(0), convertedPlayer.get(1), convertedPlayer.get(2)));
                tableView.getItems().addAll(data);
            }
        } catch (FileNotFoundException e){
            System.out.println(fileName+" not found");
        }
    }

    public void setFactoryValues(TableColumn<Player, String> col1, TableColumn<Player, String> col2, TableColumn<Player, String> col3){
        col1.setCellValueFactory(new PropertyValueFactory<>("name"));
        col2.setCellValueFactory(new PropertyValueFactory<>("age"));
        col3.setCellValueFactory(new PropertyValueFactory<>("Id"));
    }

    public int getNumberOfProfilesInEachTeam(String group, String team) {
        File myObj = new File("src/profileStorage/"+group+"/"+team+".txt");
        int numberOfLines = 0;
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                myReader.nextLine();
                numberOfLines++;
            } myReader.close();
        } catch (FileNotFoundException e){
            System.out.println(myObj+" not found.");
        }
        return numberOfLines;
    }

    public int getTotalProfileCount() {
        int totalProfileCount = 0;
        for (int i = 1; i <= 4 ; i++) {
            totalProfileCount+=getNumberOfProfilesInEachTeam("A","team"+i);
        }
        for (int i = 1; i <= 4 ; i++) {
            totalProfileCount+=getNumberOfProfilesInEachTeam("B", "team"+i);
        } return totalProfileCount;
    }

    public boolean validateIfAllProfilesCreated()  {
        return getTotalProfileCount()>=88;
    }
}