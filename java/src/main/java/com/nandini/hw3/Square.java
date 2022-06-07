package com.nandini.hw3;

public class Square {
    private final int position;
    private int levels;
    private boolean dome;
    private Worker worker;
    private Board board;
    public Square(int pos, Board board) {
        this.position = pos;
        dome = false;
        levels = 0;
        worker = null;
        this.board = board;
    }

    /**
     * Returns the square at position pos on this board
     * @param pos - int position of new square
     * @return Square at pos on board
     */
    public Square getNewBoardSquare(int pos) {
        return this.board.getSquare(pos);
    }

    /**
     * Checks if the levels of this square is at most 1 less than the levels of the square at position pos on the board
     * @param pos - position of square being checked on board
     * @return true if the levels of this square is at most 1 less than the level of the other square at position pos, false otherwise
     */
    public boolean checkLevels(int pos) {
        return getNewBoardSquare(pos).getLevels() <= this.levels + 1;
    }

    /**
     * Checks if this square at position pos has worker on it
     * @param pos - position of square that is being checked
     * @return true if there is a non-null worker on it, false otherwise
     */
    public boolean hasWorker(int pos) {
        return getNewBoardSquare(pos).hasWorker();
    }

    /**
     * Checks if this square is the appropriate levels and doesn't have a dome or worker.
     * @param pos - position on board to check if
     * @return true if this square is at the right levels compared to square at position pos, square at pos doesn't
     * have a dome or worker, false otherwise
     */
    public boolean isValidToMoveOn(int pos) {
        return checkLevels(pos) && !getNewBoardSquare(pos).hasDome() && !hasWorker(pos);
    }

    /**
     * Checks if this square is valid to build on
     * @return true if this square has 3 or fewer levels and Worker is not present, false otherwise
     */
    public boolean isValidToBuildOn() {
        return this.levels <= 3 && !this.dome && this.worker == null;
    }

    /**
     * Performs the build if this square is valid to build on
     * @throws IllegalStateException when this Square is not valid to be built on (see isValidToBuildOn)
     */
    public void build() {
        if (!isValidToBuildOn()) {
            throw new IllegalStateException("Cannot build block on tower, not a valid build");
        }
        if (this.levels == 3) {
            System.out.println("Adding a dome to the tower at " +  getPosition());
            addDome();
        }
        else {
            System.out.println("Adding a level to the tower at " + getPosition());
        }
        this.levels++;
    }

    /**
     * Sets the dome for this square to true
     */
    private void addDome() {
        this.dome = true;
    }

    /**
     * Adds a worker to this square if there isn't a Worker already on it, if the god is NOT Minotaur?!
     * @param worker - Worker to add on this square
     * @throws IllegalStateException if this square is already occupied
     */
    public void addWorker(Worker worker) {
        if (this.worker != null) {
            throw new IllegalStateException("Cannot add worker on this cell, occupied by worker");
        }
        this.worker = worker;
        this.worker.setOnSquare(this);
    }

    /**
     * Checks if this square has a Worker
     * @return true if the Worker on this square is non-null, false otherwise
     */
    public boolean hasWorker() {return this.worker != null;}

    /**
     * Gets the position of this square on the board
     * @return int position of this square
     */
     public int getPosition() {
        return this.position;
    }

    /**
     * Gets worker that is on this square
     * @return Worker on this square
     */
    public Worker getWorker() {
        return this.worker;
    }

    /**
     * Checks if there is a dome on this square
     * @return true if there is a dome on this square, false otherwise
     */
    public boolean hasDome() {
        return this.dome;
    }

    /**
     * Removes the worker from this square
     */
    public void removeWorker() {
        this.worker = null;
    }

    /**
     * Returns the levels added on this square
     * @return int levels of this square
     */
    public int getLevels() {
        return this.levels;
    }

    public int getNewSquareLevels(int pos){
        return getNewBoardSquare(pos).getLevels();
    }

    /**
     * Gets the direction of the move given the positions on the square
     * @param originalPos - square position worker tries to move from
     * @param finalPos - square position worker tries to move on
     * @return the String direction of the move, "invalid" if the final pos is not an option
     */
    public static String getDir(int originalPos, int finalPos) {
        if (originalPos - 5 == finalPos) {
            return "U";
        }
        else if (originalPos + 5 == finalPos) {
            return "D";
        }
        else if (originalPos - 1 == finalPos) {
            return "L";
        }
        else if (originalPos + 1 == finalPos) {
            return "R";
        }
        else if (originalPos - 4 == finalPos) {
            return "RU";
        }
        else if (originalPos - 6 == finalPos) {
            return "LU";
        }
        else if (originalPos + 6 == finalPos) {
            return "RD";
        }
        else if (originalPos + 4 == finalPos) {
            return "LD";
        }
        else {
            return "invalid";
        }
    }
}
