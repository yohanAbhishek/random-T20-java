package GameClasses;

import org.junit.Assert;
import org.junit.Test;

public class MatchTest {
    @Test
    public void getRandomScoreForOneBall() {
        Assert.assertTrue(Match.getRandomScoreForOneBall()<=6);
        Assert.assertTrue(Match.getRandomScoreForOneBall()>=0);
        Assert.assertTrue(Match.getRandomScoreForOneBall()!=5);
    }

    @Test
    public void getRandomDismissal() {
        System.out.println(Match.getRandomDismissal());
        String possibilities =  "score,LBW,Bowled,Caught,Run-Out,Stumped";
        Assert.assertTrue(possibilities.contains(Match.getRandomDismissal()));
    }

    @Test
    public void checkIfMatchHasBeenPlayed() {
        System.out.println(Match.checkIfMatchHasBeenPlayed("A1A2"));
        String expectedTest = Match.checkIfMatchHasBeenPlayed("A1A2");
        Assert.assertEquals("Played", expectedTest);
    }
}