package GameClasses;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TournamentStandingTest {

    @Test
    public void getBatsmanScores() {
        TournamentStanding standing = new TournamentStanding();
        String[] compare = standing.getBatsmanScores().get(0).split(",");
        assertTrue(Integer.parseInt(compare[2])>=0);
        assertEquals(3, compare.length);
    }

    @Test
    public void getTeamsWhoPlayed() {
        TournamentStanding standing = new TournamentStanding();
        ArrayList<String> teamCombinations = standing.getTeamsWhoPlayed();
        System.out.println(teamCombinations.size());
        assertTrue(teamCombinations.size()<13);
    }

    public Set<String> getPlayedTeams(){
        TournamentStanding standing = new TournamentStanding();
        ArrayList<String> teamCombinations = standing.getTeamsWhoPlayed();
        Set<String> teams = new HashSet<>();
        for (String teamCombination : teamCombinations) {
            teams.add(teamCombination.split("")[0]+teamCombination.split("")[1]);
        } return teams;
    }

    @Test
    public void calcScores() {
        TournamentStanding standing = new TournamentStanding();
        assertTrue(standing.calcScores().containsKey(Arrays.toString(getPlayedTeams().toArray()).split(", ")[1]));
    }

    @Test
    public void convertHashMapToArrayList() {
        TournamentStanding standing = new TournamentStanding();
        System.out.println(standing.convertHashMapToArrayList().getClass().getName().equals("java.util.ArrayList"));
        assertEquals("java.util.ArrayList", standing.convertHashMapToArrayList().getClass().getName());
    }
}