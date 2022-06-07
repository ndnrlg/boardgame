package com.nandini.hw3;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Player {
    private String id;
    private Worker w1;
    private Worker w2;
    private Game game;
    private boolean win;
    private boolean worker1Placed;
    private boolean worker2Placed;
    private int builds;
    private boolean moved;
    public Player(String id, Worker w1, Worker w2) {
        this.id = id;
        this.w1 = w1;
        this.w2 = w2;
        this.worker1Placed = false;
        this.worker2Placed = false;
        this.builds = 0;
        this.moved = false;
    }
    /**
     * Adding the workers to the initial positions
     * @param pos1 - int position of this Player's worker 1 
     * @param pos2 - int position of this Player's worker 2
     */
    public void placeWorkers(int pos1, int pos2) {
        w1.placeWorker(pos1, this);
        w2.placeWorker(pos2, this);
    }

    /**
     * Adding the workers to the initial positions
     * @param pos - int position to place worker
     */
    public void placeWorker(int pos) {
        if (!worker1Placed) {
            w1.placeWorker(pos, this);
            worker1Placed = true;
        }
        else {
            w2.placeWorker(pos, this);
            worker2Placed = true;
        }
    }
    /**
     * Goes through a players turn
     * @param workerID - int ID of this player's worker
     * @param direction - direction for one of this player's workers to move
     * @param pos - square pos to build
     */
    public void playRound(int workerID, String direction, ArrayList<Integer> pos) {
        makeMove(workerID, direction);
        game.updateGame(this);
        build(pos);
    }
    /**
     * Makes the move if possible for a worker ID (1 or 2)
     * @param workerID - int ID of the worker that the player is trying to move
     * @param direction - direction which this player's worker tries to move
     * @throws InvalidParameterException if the workerID is invalid (not 1 or 2)
     */
    public void makeMove(int workerID, String direction) {
        checkInvalidID(workerID);
        if (workerID == 1) {
            w1.makeMove(direction);
        }
        else {
            w2.makeMove(direction);
        }
    }
    /**
     * Checks if the worker ID is invalid
     * @param workerID - int value of worker ID (1 or 2)
     */
    public void checkInvalidID(int workerID) {
        if (workerID != 1 && workerID!= 2) {
            throw new InvalidParameterException("invalid worker ID");
        }
    }
    /**
     * Checks if the player has either worker on the third level
     * @return true if this player has a worker on the third level, false otherwise
     */
    public boolean checkPlayerWin() {
        return w1.on3rdLevel() || w2.on3rdLevel() || this.win;
    }
    /**
     * Gets the game this player is playing
     * @return the Game the player is playing
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * Sets the game the player is playing to the given game
     * @param game - game that player will play
     */
    public void playerPlayGame(Game game) {
        this.game = game;
    }
    public int getWorkersPosition(int wID) {
        if (wID == 1){
            return w1.getSquare().getPosition();
        }
        else {
            return w2.getSquare().getPosition();
        }
    }
    public String getID() {
        return this.id;
    }
    public void setWinTrue() {
        this.win = true;
    }
    public void build(ArrayList<Integer> pos) {
        this.w1.build(pos);
    }
    public void incrementBuilds() {
        this.builds++;
    }
    public int getBuildsMade() {
        return this.builds;
    }
    public int getTotalBuilds() {
        return w1.getBuilds();
    }
    public boolean workersPlaced() {return worker1Placed && worker2Placed;}
    public void setMoved(boolean b) {this.moved = b;}
    public boolean moved() {return this.moved;}
    public void resetBuilds() {this.builds = 0;}
}
