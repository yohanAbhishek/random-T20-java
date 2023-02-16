// TODO add scatter plot for match summaries
package GameClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Summary {
    private int score;
    private String totalBalls;
    private String totalWickets;
    private String win;

    public Summary(int score, String totalBalls, String totalWickets, String win) {
        this.score = score;
        this.totalBalls = totalBalls;
        this.totalWickets = totalWickets;
        this.win = win;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTotalBalls() {
        return totalBalls;
    }

    public void setTotalBalls(String totalBalls) {
        this.totalBalls = totalBalls;
    }

    public String getTotalWickets() {
        return totalWickets;
    }

    public void setTotalWickets(String totalWickets) {
        this.totalWickets = totalWickets;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public Summary(){}

    public int calculator(String batOrBowl, String matchID, int searchIndex){
        File myObj = new File("src/matchData/"+batOrBowl+".txt");
        int calcTotal =0;
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.split(",")[0].equals(matchID)){
                    calcTotal += Integer.parseInt(data.split(",")[searchIndex]);
                }
            }
        } catch (FileNotFoundException e){
            System.out.println(myObj+" not found. "+e);
        }
        return calcTotal;
    }

    public int calcScore(String matchID) {
        return calculator("batsman", matchID, 2);
    }

    public String calcTotalBalls(String matchID) {
        return String.valueOf(calculator("bowlers", matchID, 3));
    }

    public String calcTotalWickets(String matchID) {
        return String.valueOf(calculator("bowlers", matchID, 2)-1);
    }
}