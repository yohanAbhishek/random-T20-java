package GameClasses;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Player {
    private SimpleStringProperty name;
    private SimpleStringProperty Id;
    private SimpleStringProperty age;

    public Player(String Id, String age, String name) {
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleStringProperty(age);
        this.Id = new SimpleStringProperty(Id);
    }

    public Player(String age, String name) {
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleStringProperty(age);
    }

    public Player(){}

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty ageProperty() {
        return age;
    }

    public SimpleStringProperty IdProperty() {
        return Id;
    }

    public String getName() {
        return name.get();
    }

    public String getID() {
        return Id.get();
    }

    public String getAge() {
        return age.get();
    }

    public boolean addPlayerToTextFile(Player getInfo, String fileName, Label message) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int lineCountInFile = (int) reader.lines().count();
        if (lineCountInFile < 11) {
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.write(generateID(fileName) + "," + getInfo.getAge() + "," + getInfo.getName() + "\n");
            fileWriter.close();
            return true;
        } else {
            message.setText("Max number of profiles created!");
            return false;
        }
    }

    public static String generateID(String filename) throws FileNotFoundException {
        List<String> IDs = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11");
        ArrayList<String> IDsFromFile = new ArrayList<>();
        File myObj = new File(filename);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            IDsFromFile.add(data.split(",")[0].split("")[2]+data.split(",")[0].split("")[3]);
        }
        for (String id : IDs) {
            if (!IDsFromFile.contains(id)){
                return filename.split("/")[2]+filename.split("/")[3].split("")[4]+id;
            }
        } return null;
    }
}
