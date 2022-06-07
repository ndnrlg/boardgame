package com.nandini.hw3;

import java.util.Arrays;

public final class GameState {

    private final Cell[] cells;
    private final Player winner;
    private final String turn;
    private final String godCardA;
    private final String godCardB;


    private GameState(Cell[] cells, Player winner, String turn, String godCardA, String godCardB) {
        this.cells = cells;
        this.turn = turn;
        this.winner = winner;
        this.godCardA = godCardA;
        this.godCardB = godCardB;
    }

    /**
     * Returns the game state based off the game
     * @param game - game being played
     * @param gcA - god card for player A
     * @param gcB - god card for player B
     * @return the game state
     */
    public static GameState forGame(Game game, String gcA, String gcB) {
        Cell[] cells = getCells(game);
        Player winner = getWinner(game);
        String  turn = getTurn(game);
        return new GameState(cells, winner, turn, gcA, gcB);
    }

    /**
     * Game state in string
     * @return JSON format of game state
     */
    @Override
    public String toString() {
        if (this.winner == null) {
            return "{ \"cells\": " + Arrays.toString(this.cells) + "," +
                    "\"turn\": \"" + String.valueOf(this.turn)  + "\" ," +
                    "\"godCardA\": \"" + String.valueOf(this.godCardA) + "\" ," +
                    "\"godCardB\": \"" + String.valueOf(this.godCardB) + "\" }" ;
        }
        return "{ \"cells\": " + Arrays.toString(this.cells) + "," +
                "\"turn\": \"" + String.valueOf(this.turn)  + "\" ," +
                "\"winner\": \"" + String.valueOf(this.winner.getID())  + "\" ," +
                "\"godCardA\": \"" + String.valueOf(this.godCardA) + "\" ," +
                "\"godCardB\": \"" + String.valueOf(this.godCardB) + "\" }" ;
    }
    /**
     * Gets the game board's cells
     * @return JSON format for cells
     */
    private static Cell[] getCells(Game game) {
        Cell[] cells = new Cell[25];
        for (int x = 0; x <= 4; x++) {
            for (int y = 0; y <= 4; y++) {
                String text = "";
                String link = "";
                String clazz = "";
                Square sq = game.getGameBoardSquare(5 * y + x);
                if (sq.hasWorker()) {
                    if (sq.getWorker().getPlayerID().equalsIgnoreCase("A")) {
                        text = "A" + sq.getWorker().getId();
                        link = "/play?x=" + x + "&y=" + y;
                    }
                    else {
                        text = "B" + sq.getWorker().getId();
                        link = "/play?x=" + x + "&y=" + y;
                    }
                }
                else {
                    clazz = "playable";
                    link = "/play?x=" + x + "&y=" + y;
                }
                for (int i = 0; i < sq.getLevels(); i++) {
                    text = "[" + text + "]";
                }
                if (sq.hasDome()) {
                    text = "DOME";
                }
                cells[5 * y + x] = new Cell(text, clazz, link);
            }
        }
        return cells;
    }

    private static Player getWinner(Game game) {
        return game.getWinner();
    }

    private static String getTurn(Game game) {
        return game.getTurn();
    }
}
class Cell {
    private final String text;
    private final String clazz;
    private final String link;

    Cell(String text, String clazz, String link) {
        this.text = text;
        this.clazz = clazz;
        this.link = link;
    }
    public String getText() {
        return this.text;
    }
    public String getClazz() {
        return this.clazz;
    }
    public String getLink() {
        return this.link;
    }
    /**
     * A cell in string
     * @return JSON format of cell
     */
    @Override
    public String toString() {
        return "{ \"text\": \"" + this.text + "\"," +
                " \"clazz\": \"" + this.clazz + "\"," +
                " \"link\": \"" + this.link + "\"}";
    }
}
