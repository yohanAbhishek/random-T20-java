package GameClasses;

import org.junit.Assert;
import org.junit.Test;

public class SummaryTest {

    @Test
    public void calcScore() {
        Summary summary = new Summary();
        int actualScore = summary.calcScore("A1A2");
        System.out.println("Actual value: "+actualScore);
        System.out.println("Expected value should be less than 480\n");
        // 480 assuming a maximum score rate of 4 runs per ball for 20 overs
        Assert.assertTrue(actualScore<480);
    }

    @Test
    public void calcTotalBalls() {
        Summary summary = new Summary();
        int actualBalls = Integer.parseInt(summary.calcTotalBalls("A1A2"));
        System.out.println("Actual value: "+actualBalls);
        System.out.println("Expected value should be less than 121\n");
        // 121 because there is only 120 balls in one inning
        Assert.assertTrue(actualBalls<121);
    }

    @Test
    public void calcTotalWickets() {
        Summary summary = new Summary();
        int actualWickets = Integer.parseInt(summary.calcTotalWickets("A1A2"));
        System.out.println("Actual value: "+actualWickets);
        System.out.println("Expected value should be less than 12");
        // 12 because there is only 11 batsman in one inning
        Assert.assertTrue(actualWickets<12);
    }
}