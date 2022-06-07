package com.nandini.hw3;

import java.security.InvalidParameterException;

public class Board {
    private Square[][] grid;
    public Board() {
        grid = new Square[5][5];
        for (int i = 0; i < 25; i++) {
            grid[i/5][i%5] = new Square(i, this);
        }
    }
    /**
     * Returns the square at pos on grid
     * @param pos - index of position
     * @return the Square at position pos on grid
     * @throws InvalidParameterException if pos is not a value between 0 and 24 inclusive
     */
    public Square getSquare(int pos) {
        if (!(pos >= 0 && pos < 25)){
            throw new InvalidParameterException("invalid position");
        }
        int row = pos/5;
        int col = pos%5;
        return grid[row][col];
    }

}
