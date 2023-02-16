package GameClasses;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PlayerTest {

    @Test
    public void generateID() throws FileNotFoundException {
        String filename = "src/profileStorage/A/team1.txt";
        File myObj = new File(filename);
        Scanner myReader = new Scanner(myObj);
        int totalProfiles=0;
        while (myReader.hasNextLine()) {
            myReader.nextLine();
            totalProfiles+=1;
        }
        if (totalProfiles==11){
            System.out.println(true);
            Assert.assertNull(Player.generateID(filename));
        } else {
            System.out.println(false);
            Assert.assertEquals(4, Player.generateID(filename).split(",").length);
        }
    }
}