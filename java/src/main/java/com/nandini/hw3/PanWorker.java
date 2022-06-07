package com.nandini.hw3;

public class PanWorker extends Worker {
    public PanWorker(int workerID) {
        super(workerID);
    }

    /**
     * Updates the win condition for Pan's special move
     * @param direction - direction to move (U, D, L, R, LU, LD, RU, RD)
     */
    @Override
    public void makeMove(String direction) {
        int pos = getPos(direction);
        if (getSquare().getLevels() - getSquare().getNewSquareLevels(pos) >= 2) {
            getPlayer().setWinTrue();
        }
        super.makeMove(direction);
    }
}
