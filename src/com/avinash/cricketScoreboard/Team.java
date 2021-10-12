package com.avinash.cricketScoreboard;

import com.avinash.helper.InputScanner;
import com.avinash.helper.Validations;

import java.util.Scanner;

public class Team {
    private Player[] players;
    private final int teamNumber;
	private final int totalOvers;
	private final int totalPlayers;
	private int totalRuns;
	private int totalWickets;
    private double completedOvers;
    private Player strikerPlayer, nonstrikerPlayer;

    Team(int teamNumber, int playerCount, int overCount) {
        this.teamNumber = teamNumber;
        totalOvers = overCount;
        totalPlayers = playerCount;

        totalRuns = 0;
        completedOvers = 0;
        totalWickets = 0;

        getPlayersDetails();
        strikerPlayer = players[0];
        nonstrikerPlayer = players[1];

    }

    void getPlayersDetails() {
        Scanner sc = InputScanner.getScanner();
        players = new Player[totalPlayers];
        System.out.println("\nBatting Order for team " + teamNumber + ":");
        for (int i = 0; i < totalPlayers; i++) {
            players[i] = new Player(sc.nextLine());
        }

    }

    void startPlaying() {
        Scanner sc = InputScanner.getScanner();
        int nextPlayerIndex = 2;

        // Calculate the wickets at which team to be out
        int teamOut = totalPlayers;
        if (totalPlayers % 2 == 1) {
            teamOut--;
        }

        // Get ball by ball input
        for (int currentOver = 1; currentOver <= totalOvers && totalWickets != teamOut; currentOver++) {
            System.out.println("\n\nOver " + currentOver + ":");
            int ballCount;
            for (ballCount = 1; ballCount <= 6 && totalWickets != teamOut; ballCount++) {


                String currentBall = sc.nextLine();
                while (!Validations.isBallValid(currentBall)) {
                    System.out.println("Please enter valid score (integer / W / Wd / N)");
                    currentBall = sc.nextLine();
                }

                while (!Validations.isDeliveryValid(currentBall)) {
                    totalRuns++;
                    currentBall = sc.nextLine();
                }

                // Increment ball counter of striker player
                strikerPlayer.setBalls(strikerPlayer.getBalls() + 1);

                if (currentBall.equals("W")) {
                    strikerPlayer.setOut(true);
                    totalWickets++;
                    if (nextPlayerIndex < totalPlayers) {
                        strikerPlayer = players[nextPlayerIndex];
                        nextPlayerIndex++;
                    } else {
                        strikerPlayer = null;
                    }
                } else {
                    int runCount = Integer.parseInt(currentBall);
                    strikerPlayer.setCurrentScore(strikerPlayer.getCurrentScore() + runCount);
                    totalRuns += runCount;

                    if (runCount == 4) {
                        strikerPlayer.setFours(strikerPlayer.getFours() + 1);
                    } else if (runCount == 6) {
                        strikerPlayer.setSixes(strikerPlayer.getSixes() + 1);
                    } else if (runCount % 2 == 1) {
                        toggleStrike();
                    }
                }
            }
            ballCount--;
            if (ballCount == 6) {
                completedOvers++;
            } else {
                completedOvers += ballCount * 0.1;
            }
            toggleStrike();
            printScoreForCurrentOver(totalWickets);
        }
    }

    private void toggleStrike() {
        Player temp = nonstrikerPlayer;
        nonstrikerPlayer = strikerPlayer;
        strikerPlayer = temp;

    }

    private void printScoreForCurrentOver(int wickets) {
        System.out.println("Scorecard for Team " + teamNumber + ":");
        System.out.println("Player Name\t\t\tScores\t\t4s\t6s\tBalls");

        for (int i = 0; i < totalPlayers; i++) {
            String playerName = players[i].getName();
            if ((strikerPlayer == players[i] || nonstrikerPlayer == players[i]) && !players[i].isOut()) {
                playerName += "*";
            }
            System.out.println(playerName + "\t\t\t\t\t"
                    + players[i].getCurrentScore() + "\t\t" + players[i].getFours() + "\t"
                    + players[i].getSixes() + "\t" + players[i].getBalls());

        }
        System.out.println("Total: " + totalRuns + "/" + wickets);
        System.out.println("Overs: " + completedOvers);


    }

    public int getTotalRuns() {
        return totalRuns;
    }

    public int getTeamNumber() {
        return teamNumber;
    }
}
