package GameClasses;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilitiesTest {
    @Test
    public void getNumberOfProfilesInEachTeam() {
        Utilities utilities = new Utilities();
        int compare = utilities.getNumberOfProfilesInEachTeam("A", "team1");
        assertTrue(compare<12 && compare>=0);
    }

    @Test
    public void getTotalProfileCount() {
        Utilities utilities = new Utilities();
        System.out.println(utilities.getTotalProfileCount());
        assertTrue(utilities.getTotalProfileCount()<89);
    }
}


