package com.nandini.hw3;

import java.util.ArrayList;

public class Game {
    private final Board board;
    private Player playerA;
    private Player playerB;
    private int round;
    private String winnerID;
    private String turn;
    private Player player;
    private Worker worker;
    public Game() {
        round = 1;
        playerA = new Player("A", new Worker(1), new Worker(2));
        playerB = new Player("B", new Worker(1), new Worker(2));
        playerA.playerPlayGame(this);
        playerB.playerPlayGame(this);
        turn = "A";
        board = new Board();
        winnerID = "";
        player = playerA;
        worker = null;
    }
    /**
     * Sets player to a new player
     * @param p - assigns p to the correct player
     */
    public void setPlayer(Player p) {
        if (p.getID().equalsIgnoreCase("A")) {
            playerA = p;
            playerA.playerPlayGame(this);
        }
        else {
            playerB = p;
            playerB.playerPlayGame(this);
        }
    }
    /**
     * Checks if the player in parameter has won the game. Updates winner if player has won.
     * Updates game to next round.
     * @param player - Player that has just made a move.
     */
    public void updateGame(Player player) {
        if (player.checkPlayerWin()) {
            System.out.println("Congrats Player " + player.getID() + " you have won Santorini!");
            winnerID = player.getID();
        }
        nextRound();
    }
    /**
     * Plays the game for the player whose turn it is.
     * @param workerID - the ID of the worker to move. Must be 1 or 2.
     * @param direction - the direction that the worker must move. Must be "U", "D", "L", "R",
     *                  "LU", "LD", "RU", or "RD".
     * @param buildPos - ArrayList of the build position for Worker
     */
    public void play(int workerID, String direction, ArrayList<Integer> buildPos) {
        if (winnerID.equals("")) {
            System.out.println("Round: " + round);
            if (turn.equals("A")) {
                playerA.playRound(workerID, direction, buildPos);
                turn = "B";
            } else if (turn.equals("B")) {
                playerB.playRound(workerID, direction, buildPos);
                turn = "A";
            }
        }
        else {
            System.out.println("Game has ended, Player " + winnerID + " has already won");
        }
    }
    /**
     * Plays a round of the game.
     * @param pos - square position
     * @return the game after a play
     */
    public Game play(int pos) {
        if (!winnerID.equals("")) {
            return this;
        }
        if (!playerA.workersPlaced() || !playerB.workersPlaced()) {
            placeWorker(pos);
        }
        else if (worker == null && getGameBoardSquare(pos).hasWorker() && player.moved() == false) {
            worker = getGameBoardSquare(pos).getWorker();
        }
        else if (worker != null && player.moved() == false) {
            move(pos);
        }
        else if (player.moved() == true && player.getBuildsMade() < player.getTotalBuilds()){
            build(new ArrayList<Integer>() {{add(pos);}});
        }
        return this;
    }
    /**
     * This move method is used for testing purposes as it throws exceptions.
     * @param workerID - id of the worker to move
     * @param direction - direction to move
     */
    public void move(int workerID, String direction) {
        player.makeMove(workerID, direction);
        updateGame(player);
    }
    /**
     * Moves onto a position
     * @param pos - position on grid to move on
     */
    public void move(int pos) {
        if (worker.getPlayerID().equalsIgnoreCase(turn)) {
            try {
                worker.makeMove(Square.getDir(worker.getPosition(), pos));
                worker = null;
                updateGame(player);
                player.setMoved(true);
            } catch (IllegalStateException e) {
                System.out.println("invalid place");
            }
        }
        else {worker = null;}
    }
    /**
     * This build method is used for testing purposes as it throws an exception if invalid.
     * @param buildPos - build pos
     */
    public void build(ArrayList<Integer> buildPos) {
        try {
            player.build(buildPos);
            player.incrementBuilds();
            if (player.getBuildsMade() == player.getTotalBuilds()) {
                player.resetBuilds();
                player.setMoved(false);
                System.out.println("build for " + player.getID());
                updateTurn();
            }
        }
        catch (IllegalStateException e) {
            System.out.println("invalid build");
        }
    }
    /**
     * Places the workers at the positions passed in, the first two positions are for player A,
     * second 2 positions are for player B
     * @param pos1A - position that worker 1 of player A will be placed on
     * @param pos2A - position that worker 2 of player A will be placed on
     * @param pos1B - position that worker 1 of player B will be placed on
     * @param pos2B - position that worker 2 of player B will be placed on
     */
    public void setUpWorkers(int pos1A, int pos2A, int pos1B, int pos2B) {
        playerA.placeWorkers(pos1A, pos2A);
        playerB.placeWorkers(pos1B, pos2B);
    }
    /**
     * Tries to place worker on position pos
     * @param pos - position to place worker
     */
    public void placeWorker(int pos) {
        try {
            player.placeWorker(pos);
            if (player.workersPlaced()) {
                System.out.println("both workers placed for player " + player.getID());
                updateTurn();
            }
        }
        catch (IllegalStateException e) {
            System.out.println("invalid place");
        }
    }
    /**
     * Gets worker position of playerA of this game
     * @param wID - the ID of the worker that is being checked
     * @return pos of worker
     */
    public int getPLayerAWorkerPos(int wID) {
        return playerA.getWorkersPosition(wID);
    }
    /**
     * Gets worker position of playerB of this game
     * @param wID - the ID of the worker that is being checked
     * @return pos of worker
     */
    public int getPLayerBWorkerPos(int wID) {
        return playerB.getWorkersPosition(wID);
    }
    /**
     * Increments round by 1
     */
    public void nextRound() {
        this.round++;
    }
    /**
     * Gets the game board square at pos
     * @param pos - the position of the square
     * @return Square at position pos on board
     */
    public Square getGameBoardSquare(int pos) {
        return board.getSquare(pos);
    }
    /**
     * Gets the id of the winner of this game
     * @return "" if there is no winner, "A" if player A won, "B" if player B won
     */
    public String getWinnerID() {
        return winnerID;
    }
    /**
     * Gets the winner
     * @return Player winner or null if there is no winner
     */
    public Player getWinner() {
        if (winnerID.equalsIgnoreCase("A")) {return this.playerA;}
        else if (winnerID.equalsIgnoreCase("B")) {return this.playerB;}
        else {return null;}
    }
    /**
     * The current player's ID
     * @return ID of player whose turn it is
     */
    public String getTurn(){
        return this.turn;
    }

    /**
     * Changes the turn to the other player
     */
    public void updateTurn() {
        if (this.turn == "A") {
            this.turn = "B";
            this.player = playerB;
        }
        else {
            this.turn = "A";
            this.player = playerA;
        }
    }
    /**
     * @return the current players
     */
    public Player getPlayer() {return this.player;}
}
