package com.nandini.hw3;

import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class AppTest
{
    private Game g;
    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        g = new Game();
    }

    /**
     * Testing with player A being the winner, structural
     */
    @Test
    public void testAWinner()
    {
        g.setUpWorkers(1, 2, 3, 4);
        g.play(1, "D", new ArrayList<Integer>(){{add(5);}});
        g.play(1, "D", new ArrayList<Integer>(){{add(7);}});
        g.play(1, "L", new ArrayList<Integer>(){{add(0);}});
        g.play(1, "L", new ArrayList<Integer>(){{add(0);}});
        g.play(1, "U", new ArrayList<Integer>(){{add(1);}});
        g.play(1, "L", new ArrayList<Integer>(){{add(1);}});
        g.play(2, "R", new ArrayList<Integer>(){{add(1);}});
        g.play(2, "D", new ArrayList<Integer>(){{add(22);}});
        g.play(1, "R", new ArrayList<Integer>(){{add(22);}});
        assert(g.getWinnerID().equals("A"));
    }
    /**
     * Testing with player A being the winner, structural
     */
    @Test
    public void testAWinnerTwo()
    {
        g.setUpWorkers(20, 21, 22, 23);
        g.play(1, "RU", new ArrayList<Integer>(){{add(15);}});
        g.play(1, "U", new ArrayList<Integer>(){{add(11);}});
        g.play(1, "L", new ArrayList<Integer>(){{add(11);}});
        g.play(2, "U", new ArrayList<Integer>(){{add(12);}});
        g.play(1, "RU", new ArrayList<Integer>(){{add(12);}});
        g.play(1, "RU", new ArrayList<Integer>(){{add(12);}});
        //next line - build position does not matter, player A has already won
        g.play(1, "R", new ArrayList<Integer>(){{add(-1);}});
        assert(g.getWinnerID().equals("A"));
    }
    /**
     * Testing with player B being the winner, structural
     */
    @Test
    public void testBWinner()
    {
        g.setUpWorkers(12, 20, 6, 18);
        g.play(1, "L", new ArrayList<Integer>(){{add(12);}});
        g.play(1, "RD", new ArrayList<Integer>(){{add(13);}});
        g.play(2, "RU" , new ArrayList<Integer>(){{add(15);}});
        g.play(2, "L", new ArrayList<Integer>(){{add(13);}});
        g.play(2, "L", new ArrayList<Integer>(){{add(10);}});
        g.play(1, "R", new ArrayList<Integer>(){{add(12);}});
        g.play(1, "L", new ArrayList<Integer>(){{add(12);}});
        g.play(1, "L", new ArrayList<Integer>(){{add(0);}});
        assert(g.getWinnerID().equals("B"));
    }
    /**
     * Testing that there is no winner in the game, structural
     */
    @Test
    public void testNoWinner()
    {
        g.setUpWorkers(12, 20, 6, 18);
        g.play(1, "L", new ArrayList<Integer>(){{add(12);}});
        g.play(1, "RD", new ArrayList<Integer>(){{add(13);}});
        g.play(2, "RU" , new ArrayList<Integer>(){{add(15);}});
        g.play(2, "L", new ArrayList<Integer>(){{add(13);}});
        g.play(2, "L", new ArrayList<Integer>(){{add(10);}});
        g.play(1, "R", new ArrayList<Integer>(){{add(12);}});
        g.play(1, "L", new ArrayList<Integer>(){{add(12);}});
        assert(g.getWinnerID().equals(""));
    }
    /**
     * Testing whether player can move in a direction out of grid.
     * Expecting to throw IllegalStateException
     */
    @Test
    public void testInvalidMoveDirection()
    {
        g.setUpWorkers(1, 2, 3, 4);
        boolean thrown = false;
        try {
            g.move(1, "U");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        assert(thrown);
    }
    /**
     * Testing if a worker can move to a position that is already occupied by another worker
     * Expecting to throw IllegalStateException
     */
    @Test
    public void testInvalidMoveOccupied()
    {
        boolean thrown = false;
        try {
            g.setUpWorkers(1, 2, 1, 3);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        assert(thrown);
    }
    /**
     * Testing if a worker can move to a position that is on a level that is more than one block higher.
     * Expecting to throw IllegalStateException
     */
    @Test
    public void testInvalidMoveLevels()
    {
        g.setUpWorkers(1, 2, 3, 4);
        g.play(1, "D", new ArrayList<Integer>(){{add(0);}});
        g.play(1, "D", new ArrayList<Integer>(){{add(0);}});
        boolean thrown = false;
        try {
            g.move(1, "LU");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        assert(thrown);
    }
    /**
     * Testing if a worker can move to a position that is already occupied.
     * Expecting to throw IllegalStateException
     */
    @Test
    public void testIfInvalidMoveDome()
    {
        g.setUpWorkers(1, 2, 3, 4);
        g.play(1, "D", new ArrayList<Integer>(){{add(5);}});
        g.play(1, "D", new ArrayList<Integer>(){{add(0);}});
        g.play(1, "L", new ArrayList<Integer>(){{add(0);}});
        g.play(2, "L", new ArrayList<Integer>(){{add(1);}});
        g.play(1, "U", new ArrayList<Integer>(){{add(1);}});
        g.play(1, "D", new ArrayList<Integer>(){{add(1);}});
        g.play(2, "D", new ArrayList<Integer>(){{add(1);}});
        //Next line adds a dome at position 1
        g.play(1, "D", new ArrayList<Integer>(){{add(20);}});
        boolean thrown = false;
        try {
            g.move(1, "R");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        assert(thrown);
    }
    /**
     * Testing if a worker can move in an invalid direction.
     * Expecting to throw InvalidParameterException
     */
    @Test
    public void testInvalidDirectionParameter()
    {
        g.setUpWorkers(11, 22, 7, 4);
        g.play(1, "LU", new ArrayList<Integer>(){{add(0);}});
        g.play(1, "RU", new ArrayList<Integer>(){{add(0);}});
        g.play(2, "L", new ArrayList<Integer>(){{add(0);}});
        boolean thrown = false;
        try {
            g.move(1, "diagonal");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        assert(thrown);
    }
    /**
     * Testing if a worker can move on a position that is not on the grid.
     * Expecting to throw IllegalStateException
     */
    @Test
    public void testIfInvalidMovePosition()
    {
        g.setUpWorkers(2, 22, 0, 4);
        boolean thrown = false;
        g.play(1, "L", new ArrayList<Integer>(){{add(5);}});
        try {
            g.move(1, "L");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        assert(thrown);
    }
    /**
     * Testing if a worker can build to a position that is occupied by a worker.
     * Expecting to throw IllegalStateException.
     */
    @Test
    public void testInvalidBuildOnWorker()
    {
        g.setUpWorkers(11, 22, 7, 18);
        boolean thrown = false;
        try {
            g.play(1, "U", new ArrayList<Integer>(){{add(18);}});
        } catch (IllegalStateException e) {
            thrown = true;
        }
        assert(thrown);
    }
    /**
     * Testing if a worker can build on a position that isn't on the grid.
     * Expecting to throw InvalidParameterException.
     */
    @Test
    public void testInvalidBuildPosOutOfBounds()
    {
        g.setUpWorkers(11, 22, 7, 18);
        boolean thrown = false;
        try {
            g.build(new ArrayList<Integer>(){{add(-10);}});
        } catch (InvalidParameterException e) {
            thrown = true;
        }
        assert(thrown);
    }
    /**
     * Testing if a worker can build on a position that has a dome on it.
     * Expecting to throw IllegalStateException.
     */
    @Test
    public void testInvalidBuildOnDome()
    {
        g.setUpWorkers(11, 22, 7, 4);
        g.play(1, "LU", new ArrayList<Integer>(){{add(0);}});
        g.play(1, "RU", new ArrayList<Integer>(){{add(0);}});
        g.play(2, "L", new ArrayList<Integer>(){{add(0);}});
        g.play(1, "D", new ArrayList<Integer>(){{add(0);}});
        boolean thrown = false;
        try {
            g.play(1, "D", new ArrayList<Integer>(){{add(0);}});
        } catch (IllegalStateException e) {
            thrown = true;
        }
        assert(thrown);
    }
    /**
     * Testing if it is possible to get a square that is not on the board.
     * Expecting to throw InvalidParameterException.
     */
    @Test
    public void getSquareInvalidParam()
    {
        boolean thrown = false;
        try {
            g.getGameBoardSquare(10000);
        } catch (InvalidParameterException e) {
            thrown = true;
        }
        assert(thrown);
    }

    //Testing Demeter Builds!
    /**
     * Testing that a player with Demeter who chooses to make an additional build,
     * does so successfully
     */
    @Test
    public void testDemeterBuild()
    {
        g.setPlayer(new Player("A",
                new DemeterWorker(1), new DemeterWorker(2)));
        g.setUpWorkers(1, 2, 3, 4);
        g.play(1, "D", new ArrayList<Integer>(){{add(5); add(18);}});
        g.play(1, "D", new ArrayList<Integer>(){{add(7);}});
        g.play(1, "L", new ArrayList<Integer>(){{add(0); add(7);}});
        assert(g.getGameBoardSquare(18).getLevels() == 1);
        assert(g.getGameBoardSquare(7).getLevels() == 2);
    }
    /**
     * Testing that a player with Demeter who chooses to pass on the additional build,
     * does not build
     */
    @Test
    public void testDemeterBuildPass() {
        g.setPlayer(new Player("A",
                new DemeterWorker(1), new DemeterWorker(2)));
        g.setUpWorkers(1, 2, 3, 4);
        g.play(1, "D", new ArrayList<Integer>(){{add(5); add(-1);}});
        for (int i = 0; i < 25; i++) {
            if (i != 5) {
                assert(g.getGameBoardSquare(i).getLevels() == 0);
            }
            else {
                assert(g.getGameBoardSquare(i).getLevels() == 1);
            }
        }
    }
    /**
     * Testing that a player who doesn't have Demeter card cannot make an additional build
     * (despite given valid parameter value)
     */
    @Test
    public void testDemeterOpponentBuild() {
        g.setPlayer(new Player("B",
                new DemeterWorker(1), new DemeterWorker(2)));
        g.setUpWorkers(1, 2, 3, 4);
        g.play(1, "D", new ArrayList<Integer>(){{add(5); add(8);}});
        g.play(1, "D", new ArrayList<Integer>(){{add(21); add(10);}});
        assert(g.getGameBoardSquare(8).getLevels() == 0);
    }
    /**
     * Checking that the Minotaur worker won't be able to move when the opponent's worker
     * cannot move down (invalid position to move on)
     */
    @Test
    public void testMinotaurForceDown() {
        g.setPlayer(new Player("A",
                new MinotaurWorker(1), new MinotaurWorker(2)));
        g.setUpWorkers(10, 11, 7, 18);
        g.play(2, "RU", new ArrayList<Integer>(){{add(0);}});
        assert(g.getPLayerBWorkerPos(1) == 12);
        assert(g.getPLayerAWorkerPos(2) == 7);
    }
    /**
     * Testing that Minotaur forces the worker to the left (if that is a downward option available).
     */
    @Test
    public void testMinotaurForceDownLeft() {
        g.setPlayer(new Player("B",
                new MinotaurWorker(1), new MinotaurWorker(2)));
        g.setUpWorkers( 2, 18, 1, 7);
        g.play(2, "U", new ArrayList<Integer>(){{add(0);}});
        g.play(1, "R", new ArrayList<Integer>(){{add(0);}});
        assert (g.getPLayerAWorkerPos(1) == 6);
        assert (g.getPLayerBWorkerPos(1) == 2);
    }

    /**
     * Testing that Minotaur forces the worker to the right (if that is the only downward option available).
     */
    @Test
    public void testMinotaurForceDownRight() {
        g.setPlayer(new Player("A",
                new MinotaurWorker(1), new MinotaurWorker(2)));
        g.setUpWorkers( 1, 6, 2, 7);
        g.play(1, "R", new ArrayList<Integer>(){{add(24);}});
        assert (g.getPLayerBWorkerPos(1) == 8);
        assert (g.getPLayerAWorkerPos(1) == 2);
    }
    /**
     * Checking that the Minotaur worker won't be able to move when the opponent's worker
     * cannot move down (out of board)
     */
    @Test
    public void testMinotaurUnableForceDownBottom() {
        g.setPlayer(new Player("A",
                new MinotaurWorker(1), new MinotaurWorker(2)));
        g.setUpWorkers(20, 15, 21, 12);
        boolean thrown = false;
        try {
            g.play(1, "R", new ArrayList<Integer>(){{add(0);}});
        } catch (IllegalStateException e) {
            thrown = true;
        }
        assert(thrown);
    }
    /**
     * Testing that the Pan win condition (moving down 2 levels) allows for a win
     */
    @Test
    public void testPanWin() {
        g.setPlayer(new Player("A",
                new PanWorker(1), new PanWorker(2)));
        g.setUpWorkers(12, 20, 6, 18);
        g.play(1, "L", new ArrayList<Integer>(){{add(12);}});
        g.play(1, "U", new ArrayList<Integer>(){{add(13);}});
        g.play(1, "R" , new ArrayList<Integer>(){{add(11);}});
        g.play(1, "D", new ArrayList<Integer>(){{add(11);}});
        g.play(1, "L", new ArrayList<Integer>(){{add(7);}});
        g.play(1, "L", new ArrayList<Integer>(){{add(7);}});
        g.play(1, "L", new ArrayList<Integer>(){{add(7);}});
        assert(g.getWinnerID().equals("A"));
    }
    /**
     * Testing that the game does not continue if Pan has already won by moving down
     * 2 levels
     */
    @Test
    public void testPanWinCheck() {
        g.setPlayer(new Player("A",
                new PanWorker(1), new PanWorker(2)));
        g.setUpWorkers(12, 20, 6, 18);
        g.play(1, "L", new ArrayList<Integer>(){{add(12);}});
        g.play(1, "U", new ArrayList<Integer>(){{add(13);}});
        g.play(1, "R" ,  new ArrayList<Integer>(){{add(11);}});
        g.play(1, "D",  new ArrayList<Integer>(){{add(11);}});
        g.play(1, "L", new ArrayList<Integer>(){{add(7);}});
        g.play(1, "L", new ArrayList<Integer>(){{add(7);}});
        g.play(1, "L", new ArrayList<Integer>(){{add(7);}});
        g.play(2, "R", new ArrayList<Integer>(){{add(24);}});
        assert(g.getWinnerID().equals("A"));
        assert(g.getPLayerBWorkerPos(2) == 18);
    }
    /**
     * Testing that Apollo worker swapped position with the other, opponent worker.
     */
    @Test
    public void testApolloForceMove() {
        g.setPlayer(new Player("B",
                new ApolloWorker(1), new ApolloWorker(2)));
        g.setUpWorkers(12, 13, 17, 18);
        g.play(2, "R", new ArrayList<Integer>(){{add(24);}});
        g.play(1, "U", new ArrayList<Integer>(){{add(24);}});
        assert(g.getPLayerBWorkerPos(1) == 12);
        assert(g.getPLayerAWorkerPos(1) == 17);
    }
    /**
     * Testing that Apollo worker swapped position with the other, opponent worker.
     */
    @Test
    public void testApolloForceMoveTwo() {
        g.setPlayer(new Player("A",
                new ApolloWorker(1), new ApolloWorker(2)));
        g.setUpWorkers(3, 2, 9, 1);
        g.play(1, "RD", new ArrayList<Integer>(){{add(24);}});
        assert(g.getPLayerAWorkerPos(1) == 9);
        assert(g.getPLayerBWorkerPos(1) == 3);
    }
    /**
     * Testing that Hephaestus cannot build on a different positon.
     */
    @Test
    public void testHephaestusMustBuildOnSamePosition() {
        g.setPlayer(new Player("A",
                new HephaestusWorker(1), new HephaestusWorker(2)));
        g.setUpWorkers(3, 4, 8, 9);
        boolean thrown = false;
        try {
            g.play(1, "L", new ArrayList<Integer>(){{add(10);add(11);}});
        } catch (IllegalStateException e) {
            thrown = true;
        }
        assert(thrown);
    }
    /**
     * Testing that Hephaestus cannot build on a dome,
     */
    @Test
    public void testHephaestusCantBuildOnDome() {
        g.setPlayer(new Player("A",
                new HephaestusWorker(1), new HephaestusWorker(2)));
        g.setUpWorkers(1, 2, 3, 4);
        g.play(1, "D", new ArrayList<Integer>(){{add(5);add(5);}});
        g.play(1, "D", new ArrayList<Integer>(){{add(7);}});
        boolean thrown = false;
        try {
            g.play(1, "L", new ArrayList<Integer>(){{add(5);add(5);}});
        } catch (IllegalStateException e) {
            thrown = true;
        }
        assert(thrown);
    }
    /**
     * Testing that Hephaestus can make a second build (on appropriate position).
     */
    @Test
    public void testHephaestusAdditionalBuild(){
        g.setPlayer(new Player("A",
                new HephaestusWorker(1), new HephaestusWorker(2)));
        g.setUpWorkers(1, 2, 3, 4);
        g.play(1, "D", new ArrayList<Integer>(){{add(5);add(5);}});
        assert(g.getGameBoardSquare(5).getLevels()==2);
    }

    /**
     * Testing that Hephaestus can pass a build.
     */
    @Test
    public void testHephaestusPass() {
        g.setPlayer(new Player("A",
                new HephaestusWorker(1), new HephaestusWorker(2)));
        g.setUpWorkers(1, 2, 3, 4);
        g.play(1, "D", new ArrayList<Integer>(){{add(5);add(-1);}});
        assert(g.getGameBoardSquare(5).getLevels()==1);
    }

}
