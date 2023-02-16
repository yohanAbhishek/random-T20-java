package GameClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TopPlayer{
    private String score;
    private String name;
    private String ID;

    public TopPlayer(String score, String name, String ID) {
        this.score = score;
        this.name = name;
        this.ID = ID;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public TopPlayer(){}

    public List<String> getTop5(String bowlOrBat) { // main
        ArrayList<String> batsmanData = new ArrayList<>();
        File myObj = new File("src/matchData/"+bowlOrBat+".txt");
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine() ) {
                String data = myReader.nextLine();
                batsmanData.add(data);
                if (Integer.parseInt(batsmanData.get(batsmanData.size()-1).split(",")[2]) > Integer.parseInt(data.split(",")[2])){
                    batsmanData.add(data);
                }
            } myReader.close();
        } catch (FileNotFoundException e){
            System.out.println(myObj+"not found");
        } return sortBatsmanData(batsmanData).subList(sortBatsmanData(batsmanData).size()-5, sortBatsmanData(batsmanData).size());
    }

    public List<String> sortBatsmanData(ArrayList<String> batsmanData){ // insertion sort
        for (int i =1; i<batsmanData.size(); i++){
            String current = batsmanData.get(i);
            int j = i - 1;
            while (j>=0 && (Integer.parseInt(batsmanData.get(j).split(",")[2]) > Integer.parseInt(current.split(",")[2]))){
                batsmanData.set(j+1, batsmanData.get(j));
                j--;
            } batsmanData.set(j+1, current);
        }
        return batsmanData;
    }

    public ArrayList<String> getNameFromID(List<String> batsmanData) {
        ArrayList<String> top5Names = new ArrayList<>();
        top5Names.addAll(loopThroughGroupToFindName("A", batsmanData));
        top5Names.addAll(loopThroughGroupToFindName("B", batsmanData));
        return top5Names;
    }

    public ArrayList<String> loopThroughGroupToFindName(String group, List<String> top5) {
        ArrayList<String> top5Names = new ArrayList<>();
        for(int team = 1; team < 5; team++){
            File myObj = new File("src/profileStorage/"+group+"/team"+team+".txt");
            addNameList(top5, myObj, top5Names);
        } return top5Names;
    }

    public void addNameList(List<String> top5, File myObj, ArrayList<String> top5Names){
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                for (String s : top5) {
                    if (s.contains(data.split(",")[0])) {
                        top5Names.add(data.split(",")[2]);
                    }
                }
            }
        } catch (FileNotFoundException e){
            System.out.println(myObj+" not found");
        }
    }

    public void addDataToTable(List<String> top5, List<String> top5Names, TableView<TopPlayer> tableView){
        tableView.getItems().setAll(getTop5PlayerList(top5, top5Names));
    }

    public void setCellFactoryValues(TableColumn<TopPlayer, String> col1, TableColumn<TopPlayer, String> col2, TableColumn<TopPlayer, String> col3){
        col1.setCellValueFactory(new PropertyValueFactory<>("score"));
        col2.setCellValueFactory(new PropertyValueFactory<>("name"));
        col3.setCellValueFactory(new PropertyValueFactory<>("ID"));
    }

    public ObservableList<TopPlayer> getTop5PlayerList(List<String> top5, List<String> top5Names){
        return FXCollections.observableArrayList(
        new TopPlayer(top5.get(4).split(",")[2],top5Names.get(4), top5.get(4).split(",")[1]),
        new TopPlayer(top5.get(3).split(",")[2],top5Names.get(3), top5.get(3).split(",")[1]),
        new TopPlayer(top5.get(2).split(",")[2],top5Names.get(2), top5.get(2).split(",")[1]),
        new TopPlayer(top5.get(1).split(",")[2],top5Names.get(1), top5.get(1).split(",")[1]),
        new TopPlayer(top5.get(0).split(",")[2],top5Names.get(0), top5.get(0).split(",")[1]));
    }
}
