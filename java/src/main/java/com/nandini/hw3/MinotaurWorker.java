package com.nandini.hw3;

public class MinotaurWorker extends Worker {
    public MinotaurWorker(int workerID) {
        super(workerID);
    }

    /**
     * Makes regular moves & also moves into an opponent workers position by forcing them down
     * @param direction - direction to move (U, D, L, R, LU, LD, RU, RD)
     */
    @Override
    public void makeMove(String direction) {
        int pos = getPos(direction);
        Square newSq = getSquare().getNewBoardSquare(pos);
        if (specialCondition(pos, newSq)) {
            Worker opponent = newSq.getWorker();
            if (canMakeForceMove(pos, newSq, 5)) {
                opponent.updateSquare(pos + 5);
            }
            else if (canMakeForceMove(pos, newSq, 4)) {
                opponent.updateSquare(pos + 4);
            }
            else if (pos + 6 < 25 && canMakeForceMove(pos, newSq, 6)) {
                opponent.updateSquare(pos + 6);
            }
        }
        else if (!getSquare().isValidToMoveOn(pos)) {
            throw new IllegalStateException("worker cannot move to that position");
        }
        updateSquare(pos);
    }

    /**
     * Determines whether the worker can make a force move for the given move distance
     * @param pos - position to try to move to
     * @param newSq - new square to move on
     * @param moveDist - distance to move
     * @return true if this worker can make the force move for given move distance, false otherwise
     */
    private boolean canMakeForceMove(int pos, Square newSq, int moveDist) {
        return !getSquare().hasWorker(pos + moveDist) && !newSq.getNewBoardSquare(pos + moveDist).hasDome();
    }

    /**
     * Checks whether the worker is trying to move on a position that has a worker on it
     * @param pos - position that worker is trying to move on
     * @param newSq - square that worker is trying to move on
     * @return true if the special condition applies, false otherwise
     */
    private boolean specialCondition(int pos, Square newSq) {
        return pos + 5 < 25 && newSq.hasWorker() && !newSq.hasDome() && getSquare().checkLevels(pos)
                && !newSq.getWorker().getPlayerID().equalsIgnoreCase(getPlayerID());
    }
}
