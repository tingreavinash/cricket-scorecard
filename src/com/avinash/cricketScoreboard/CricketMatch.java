package com.avinash.cricketScoreboard;

import com.avinash.helper.InputScanner;
import com.avinash.helper.Validations;

import java.util.Scanner;

public class CricketMatch {
    private int playersPerTeam;
    private int overs;
    private Team team1;
    private Team team2;


    public static void main(String[] args) {
        CricketMatch cricketMatch = new CricketMatch();
        cricketMatch.start();
        cricketMatch.calculateResults();
        cricketMatch.stop();

    }

    private void start() {
        getPlayersAndOvers();
        team1 = new Team(1, playersPerTeam, overs);
        team1.startPlaying();

        team2 = new Team(2, playersPerTeam, overs);
        team2.startPlaying();
    }

    private void stop() {
        InputScanner.stop();
    }

    private void calculateResults() {
        Team winnerTeam = null;
        int diff = team1.getTotalRuns() - team2.getTotalRuns();
        if (team1.getTotalRuns() > team2.getTotalRuns()) {
            winnerTeam = team1;
        } else if (team1.getTotalRuns() < team2.getTotalRuns()) {
            winnerTeam = team2;
        }
        if (winnerTeam == null) {
            System.out.print("\n\nResult: Match is a tie");
        } else {
            System.out.print("\n\nResult: Team " + winnerTeam.getTeamNumber() + " won the match by " + Math.abs(diff) + " runs");
        }
    }

    private void getPlayersAndOvers() {
        Scanner sc = InputScanner.getScanner();

        System.out.println("No. of players for each team: ");
        String line = sc.nextLine();
        while (!Validations.isIntegerValue(line) || (Validations.isIntegerValue(line) && Integer.parseInt(line) < 2)) {
            System.out.println("Please enter valid input (>=2)");
            line = sc.nextLine();
        }
        playersPerTeam = Integer.parseInt(line);

        System.out.println("No. of overs: ");
        line = sc.nextLine();
        while (!Validations.isIntegerValue(line)) {
            System.out.println("Please enter valid input (>=2)");
            line = sc.nextLine();
        }
        overs = Integer.parseInt(line);

    }

}
