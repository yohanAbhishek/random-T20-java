package GameClasses;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TopPlayerTest {

    @Test
    public void getTop5() {
        TopPlayer topPlayer = new TopPlayer();
        int expectedSize = 5;
        int actualSize = topPlayer.getTop5("batsman").size();
        System.out.println("Expected list size: "+expectedSize);
        System.out.println("Actual list size: "+actualSize+"\n");
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void getNameFromID() {
        TopPlayer topPlayer = new TopPlayer();
        int expectedSize = 5;
        int actualSize = topPlayer.getNameFromID(topPlayer.getTop5("batsman")).size();
        System.out.println("Expected list size: "+expectedSize);
        System.out.println("Actual list size: "+actualSize+"\n");
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void loopThroughGroupToFindName() {
        TopPlayer topPlayer = new TopPlayer();
        int expectedSize = 5;
        ArrayList<String> top5Names = new ArrayList<>();
        top5Names.addAll(topPlayer.loopThroughGroupToFindName("A", topPlayer.getTop5("batsman")));
        top5Names.addAll(topPlayer.loopThroughGroupToFindName("B", topPlayer.getTop5("batsman")));
        int actualSize = top5Names.size();
        System.out.println("Expected list size: "+expectedSize);
        System.out.println("Actual list size: "+actualSize+"\n");
    }
}