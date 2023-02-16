package GameClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Match {
    private String matchCombination;
    private String status;
    static Random rand = new Random();

    public Match(String matchCombination, String status) {
        this.matchCombination = matchCombination;
        this.status = status;
    }

    public String getMatchCombination() {
        return matchCombination;
    }

    public String getStatus() {
        return status;
    }

    public Match(){}

    public static List<String> getProfiles(String team, String group) throws IOException {
        List<String> data = new ArrayList<>();
        String s;
            BufferedReader abc = new BufferedReader(new FileReader("src/profileStorage/"+group+"/team"+team+".txt"));
            while((s=abc.readLine())!=null) data.add(s.split(",")[0]);{
            abc.close();
        } return data;
    }

    private static void writeBatsmanToFile(String matchID,String playerID, int score){
        try {
            FileWriter fileWriter = new FileWriter("src/matchData/batsman.txt", true);
            fileWriter.write(matchID+","+playerID+","+score+"\n");
            fileWriter.close();
        } catch (IOException e){
            System.out.println("File not found");
        }

    }

    private static void writeBowlerToFile(String matchID, String playerID, int balls, int wickets){
        try {
            FileWriter fileWriter = new FileWriter("src/matchData/bowlers.txt", true);
            fileWriter.write(matchID+","+playerID+","+wickets+","+balls+"\n");
            fileWriter.close();
        } catch (IOException e){
            System.out.println("File not found");
        }
    }

    private static String genMatchID(String batTeam, String bowlTeam){
        String[] bat = batTeam.split("");
        String[] bowl = bowlTeam.split("");
        return bat[0]+bat[1]+bowl[0]+bowl[1];
    }

    private static String getNewPlayer(List<String> teamProfile){
        return teamProfile.get(rand.nextInt(teamProfile.size()));
    }

    public void runOneInning(List<String> battingTeam, List<String> bowlingTeam) {
        int wickets = 0;
        int scoreOfBatsman = 0;
        String batsmanID = getNewPlayer(battingTeam);
        for(int completedOvers = 0; completedOvers < 20 && wickets <11; completedOvers++) {
            int ballDeliveries = 0;
            int currentBowlersWickets = 0;
            String bowlerID = getNewPlayer(bowlingTeam); // get new bowler for each over
            for (int ballCount = 0; ballCount < 6 && wickets <11; ballCount++){ // interpret 1 over (loop 6 balls)
                String dismissal = getRandomDismissal();
                if(dismissal.equals("score")){
                    scoreOfBatsman+=getRandomScoreForOneBall(); //save each batsman's score
                } else {
                    wickets++;
                    currentBowlersWickets++;
                    writeBatsmanToFile(genMatchID(batsmanID, bowlerID), batsmanID, scoreOfBatsman);
                    scoreOfBatsman=0;
                    batsmanID = getNewPlayer(battingTeam); // get new batsman for each dismissal
                } ballDeliveries++;
            } writeBowlerToFile(genMatchID(batsmanID, bowlerID),bowlerID, ballDeliveries, currentBowlersWickets);
        }
    }

    public static int getRandomScoreForOneBall(){
        final String[] possibleScores = {"0", "1", "2", "3", "4", "6"};
        int indexScore = rand.nextInt(possibleScores.length);
        return Integer.parseInt(possibleScores[indexScore]);
    }

    public static String getRandomDismissal(){
        final String[] possibleDismissalMethods = {
        "score","score","score","score","score","score" ,"score","score","score","score",
        "score","score","score","score","score","score","score","score","score","score",
        "score","score","score","score","score","score","score","score","score","score",
        "score","score","score","score","score","score","score","score","score","score",
        "score","score","score","score","score","score","score","score","score","score",
        "LBW", "Bowled", "Caught", "Stumped", "Run-Out"}; // Initialize the possible methods of dismissal
        int indexDismissal = rand.nextInt(possibleDismissalMethods.length);
        return possibleDismissalMethods[indexDismissal];
    }

    public ObservableList<Match> getMatchCombinations() {
        return FXCollections.observableArrayList(
                new Match("A1 vs A2", checkIfMatchHasBeenPlayed("A1A2")),
                new Match("A1 vs A3", checkIfMatchHasBeenPlayed("A1A3")),
                new Match("A1 vs A4", checkIfMatchHasBeenPlayed("A1A4")),
                new Match("A2 vs A3", checkIfMatchHasBeenPlayed("A2A3")),
                new Match("A2 vs A4", checkIfMatchHasBeenPlayed("A2A4")),
                new Match("A3 vs A4", checkIfMatchHasBeenPlayed("A3A4")),
                new Match("B1 vs B2", checkIfMatchHasBeenPlayed("B1B2")),
                new Match("B1 vs B3", checkIfMatchHasBeenPlayed("B1B3")),
                new Match("B1 vs B4", checkIfMatchHasBeenPlayed("B1B4")),
                new Match("B2 vs B3", checkIfMatchHasBeenPlayed("B2B3")),
                new Match("B2 vs B4", checkIfMatchHasBeenPlayed("B2B4")),
                new Match("B3 vs B4", checkIfMatchHasBeenPlayed("B3B4"))
        );
    }

    public static List<String> getPlayedMatchData(){
        List<String> data = new ArrayList<>();
        String s;
        try {
            BufferedReader abc = new BufferedReader(new FileReader("src/MatchData/batsman.txt"));
            while((s=abc.readLine())!=null) {
                data.add(s.split(",")[0]);
            }
            abc.close();
        } catch (IOException e){
            System.out.println("File not found");
        } return data;
    }

    public static String checkIfMatchHasBeenPlayed(String matchCombination) {
        if (getPlayedMatchData().contains(matchCombination)){
            return "Played";
        } else {
            return "Not Played";
        }
    }
}
