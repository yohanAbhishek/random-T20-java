package GameClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TournamentStanding {
    private String score;
    private String team;

    public TournamentStanding(String score, String team) {
        this.score = score;
        this.team = team;
    }

    public TournamentStanding(){}

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public ArrayList<String> getBatsmanScores() {
        ArrayList<String> scores = new ArrayList<>();
        File myObj = new File("src/matchData/batsman.txt");
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                scores.add(data);
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        return scores;
    }

    public ArrayList<String> getTeamsWhoPlayed()  {
        ArrayList<String> teams = new ArrayList<>();
        for (String team : getBatsmanScores()) {
            if(!teams.contains(team.split(",")[0])){
                teams.add(team.split(",")[0]);
            }
        } return teams;
    }

    public HashMap<String, Integer> calcScores() {
        HashMap<String, Integer> teamsScores = new HashMap<>();
        for (String team : getTeamsWhoPlayed()) {
            int totalScore = 0;
            team = team.split("")[0]+team.split("")[1];
            for (String score : getBatsmanScores()) {
                if (team.equals(score.split(",")[0].split("")[0] + score.split(",")[0].split("")[1])) {
                    totalScore += Integer.parseInt(score.split(",")[2]);
                }
            } teamsScores.put(team, totalScore);
        } return teamsScores;
    }

    public ArrayList<String> convertHashMapToArrayList(){
        ArrayList<String> convertedArrayList = new ArrayList<>();
        for (int i = 0; i < calcScores().size(); i++) {
            Object key = calcScores().keySet().toArray()[i];
            Object value = calcScores().get(key);
            convertedArrayList.add(key+","+value);
        } return convertedArrayList;
    }
}
