package com.nandini.hw3;

import java.util.ArrayList;

public class HephaestusWorker extends Worker{
    private int firstBuild;
    public HephaestusWorker(int workerID) {
        super(workerID);
        setBuilds(2);
        firstBuild = -1;
    }
    /**
     * Resets the firstbuild field to -1 after a round
     */
    public void resetFirstBuild() {
        if (getPlayer().getBuildsMade() == 0) {
            firstBuild = -1;
        }
    }
    /**
     * Hephaestus optional build is -1 if player wants to pass on it. Prevents building
     * on different square with firstBuild field.
     * @param pos - positions to build on square
     */
    @Override
    public void build(ArrayList<Integer> pos) {
        resetFirstBuild();
        if (!getPlayer().checkPlayerWin()) {
            for (int i = 0; i < pos.size(); i++) {
                if (pos.get(i) != -1) {
                    if (canBuildDome(pos.get(i))) {
                        throw new IllegalStateException();
                    }
                    if (firstBuild != -1 && firstBuild != pos.get(i)) {
                        throw new IllegalStateException();
                    }
                    if (firstBuild == -1) {
                            firstBuild = pos.get(0);
                            Square buildSq = getSquare().getNewBoardSquare(pos.get(i));
                            buildSq.build();
                    }
                    else {
                        Square buildSq = getSquare().getNewBoardSquare(pos.get(i));
                        buildSq.build();
                    }
                }
            }
        }
    }

    /**
     * Checks if you can immediately build a dome on a square
     * @param pos - position of square to check
     * @return true if you can build dome, false otherwises
     */
    public boolean canBuildDome(int pos) {
        return getSquare().getNewBoardSquare(pos).getLevels() == 3;
    }
}
