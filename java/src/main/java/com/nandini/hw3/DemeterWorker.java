package com.nandini.hw3;

import java.util.ArrayList;

public class DemeterWorker extends Worker{
    private int firstBuild;
    public DemeterWorker(int id) {
        super(id);
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
     * Demeter optional build is -1 if player wants to pass on it. Prevents building
     * on same square with firstBuild field.
     * @param buildPos - positions to build on square
     */
    public void build(ArrayList<Integer> buildPos) {
        System.out.println(getBuilds());
        System.out.println(getPlayer().getBuildsMade());
        resetFirstBuild();
        if (!getPlayer().checkPlayerWin()) {
            for (int i = 0; i < buildPos.size(); i++) {
                if (buildPos.get(i) != -1) {
                    if (firstBuild != -1 && firstBuild == buildPos.get(i)) {
                        throw new IllegalStateException();
                    }
                    if (firstBuild == -1 && buildPos.get(i) != -1) {
                        firstBuild = buildPos.get(0);
                        Square buildSq = getSquare().getNewBoardSquare(buildPos.get(i));
                        buildSq.build();
                    }
                    if (firstBuild != buildPos.get(i) && buildPos.get(i) != -1) {
                        Square buildSq = getSquare().getNewBoardSquare(buildPos.get(i));
                        buildSq.build();
                    }
                }
            }
        }
    }
}
