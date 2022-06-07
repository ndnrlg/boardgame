package com.nandini.hw3;

public class ApolloWorker extends Worker{
    public ApolloWorker(int workerID) {
        super(workerID);
    }
    /**
     * Makes regular moves & also forces into an opponent workers position into their position
     * @param direction - direction to move (U, D, L, R, LU, LD, RU, RD)
     */
    @Override
    public void makeMove(String direction) {
        int pos = getPos(direction);
        Square newSq = getSquare().getNewBoardSquare(pos);
        Square sq = getSquare();
        if (specialCondition(pos, newSq)) {
            Worker opponent = newSq.getWorker();
            getSquare().removeWorker();
            opponent.setOnSquare(getSquare());
            newSq.removeWorker();
            newSq.addWorker(this);
            sq.addWorker(opponent);
        }
        else if (!getSquare().isValidToMoveOn(pos)) {
            throw new IllegalStateException("worker cannot move to that position");
        }
        updateSquare(pos);
    }
    /**
     * Checks whether the worker is trying to move on a position that has a worker on it
     * @param pos - position that worker is trying to move on
     * @param newSq - square that worker is trying to move on
     * @return true if the special condition applies, false otherwise
     */
    private boolean specialCondition(int pos, Square newSq) {
        return newSq.hasWorker() && !newSq.hasDome() && getSquare().checkLevels(pos)
                && !newSq.getWorker().getPlayerID().equalsIgnoreCase(getPlayerID());
    }
}

