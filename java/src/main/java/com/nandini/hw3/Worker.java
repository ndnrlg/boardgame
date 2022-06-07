package com.nandini.hw3;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Worker {
    private int id;
    private Square square;
    private Player player;
    private int builds;
    public Worker(int id) {
        this.id = id;
        square = null;
        player = null;
        builds = 1;
    }
    /**
     * Sets the square of this worker to the square parameter
     * @param square - square of this worker
     */
    public void setOnSquare(Square square) {
        this.square = square;
    }
    /**
     * Places worker on an unoccupied and valid square position on the board
     * @param pos - location on board to place worker
     * @param player - the Player that is initialized to this worker
     * @throws InvalidParameterException when the pos is invalid (see checkPosInRange)
     * @throws IllegalStateException when there is a Worker on Square at pos (see hasWorker)
     */
    public void placeWorker(int pos, Player player) {
        this.player = player;
        if (!(pos >= 0 && pos < 25)) {
            throw new InvalidParameterException("invalid position on board");
        }
        Square p = player.getGame().getGameBoardSquare(pos);
        if (p.hasWorker()) {
            throw new IllegalStateException("worker is present on square");
        }
        this.square = player.getGame().getGameBoardSquare(pos);
        this.square.addWorker(this);
    }
    /**
     * Determines whether the square that this worker is on is at the third level (without dome)
     * @return true if the worker is on a square of 3 levels and doesn't have a dome, false otherwise
     */
    public boolean on3rdLevel() {
        return square.getLevels() == 3 && !square.hasDome();
    }
    /**
     * Makes a move if the new position (given the direction) is on the board, and the new position is a valid place to move
     * (has appropriate level difference, does not have a worker, and does not have a dome)
     * @param direction - direction to move (U, D, L, R, LU, LD, RU, RD)
     * @throws IllegalStateException if the direction given is invalid
     * @throws IllegalStateException if the direction results in an invalid position
     * @throws IllegalStateException if the new square is not valid to move on
     */
    public void makeMove(String direction) {
        int pos = getPos(direction);
        if (!square.isValidToMoveOn(pos)) {
            throw new IllegalStateException("worker cannot move to that position");
        }
        updateSquare(pos);
    }
    /**
     * Places worker and updates the square with worker
     * @param pos - places a worker on a new square
     */
    public void updateSquare(int pos) {
        System.out.println("Moving Player " + player.getID() + "'s Worker #" + this.id + " from position " + square.getPosition()
        + " to position " + pos);
        this.square.removeWorker();
        this.square = this.square.getNewBoardSquare(pos);
        this.square.addWorker(this);
    }

    /**
     * Gets the new position based on the direction of the move
     * @param direction - direction of the move (U, D, L, R, LU, LD, RU, RD)
     * @return int value of new position on the board
     * @throws IllegalStateException if direction is not valid direction not (U, D, L, R, LU, LD, RU, RD)
     */
    public int getPos(String direction) {
        if (validInputDirection(direction)) {
            if (direction.equalsIgnoreCase("L")) {
                checkSideBounds(0);
                return this.square.getPosition() - 1;
            }
            if (direction.equalsIgnoreCase("R")) {
                checkSideBounds(4);
                return this.square.getPosition() + 1;
            }
            if (direction.equalsIgnoreCase("U")) {
                checkUpperBounds();
                return this.square.getPosition() - 5;
            }
            if (direction.equalsIgnoreCase("D")) {
                checkLowerBounds();
                return this.square.getPosition() + 5;
            }
            if (direction.equalsIgnoreCase("LU")) {
                checkSideBounds(0);
                checkUpperBounds();
                return this.square.getPosition() - 6;
            }
            if (direction.equalsIgnoreCase("LD")) {
                checkSideBounds(0);
                checkLowerBounds();
                return this.square.getPosition() + 4;
            }
            if (direction.equalsIgnoreCase("RU")) {
                checkSideBounds(4);
                checkUpperBounds();
                return this.square.getPosition() - 4;
            }
            if (direction.equalsIgnoreCase("RD")) {
                checkSideBounds(4);
                checkLowerBounds();
                return this.square.getPosition() + 6;
            }
        }
        else {
            throw new IllegalStateException("invalid direction on board");
        }
        return -1;
    }

    /**
     * Checks if the direction input is valid
     * @param direction - direction to move
     * @return true if direction is valid false otherwise
     */
    private boolean validInputDirection(String direction) {
        return direction.equalsIgnoreCase("L") || direction.equalsIgnoreCase("R") ||
                direction.equalsIgnoreCase("U") || direction.equalsIgnoreCase("D") ||
                direction.equalsIgnoreCase("LU") || direction.equalsIgnoreCase("LD") ||
                direction.equalsIgnoreCase("RU") || direction.equalsIgnoreCase("RD");
    }
    /**
     * Checks lower bounds of board
     */
    public void checkLowerBounds() {
        if (this.square.getPosition() >= 20) {
            throw new IllegalStateException("invalid position on board");
        }
    }
    /**
     * Checks upper bounds of board
     */
    public void checkUpperBounds() {
        if (this.square.getPosition() <= 4) {
            throw new IllegalStateException("invalid position on board");
        }
    }
    /**
     * Checks side bounds of board
     * @param i - side cols
     */
    public void checkSideBounds(int i) {
        if (this.square.getPosition()%5 == i) {
            throw new IllegalStateException("invalid position on board");
        }
    }
    /**
     * Makes build on square
     * @param pos - pos to build on
     */
    public void build(ArrayList<Integer> pos) {
        if (!this.player.checkPlayerWin()) {
            for (int i = 0; i < this.builds; i++) {
                Square buildSq = square.getNewBoardSquare(pos.get(i));
                buildSq.build();
            }
        }
    }
    public void setBuilds(int num) { this.builds = num;}
    public int getBuilds() {return this.builds;}
    public Square getSquare() {
        return this.square;
    }
    public int getPosition() {return this.square.getPosition();}
    public Player getPlayer() {
        return this.player;
    }
    public int getId() {return this.id;}
    public String getPlayerID() {
        return this.player.getID();
    }
}
