package com.javarush.task.task35.task3513;

public class MoveEfficiency implements Comparable<MoveEfficiency> {
    private int numberOfEmptyTiles;
    private int score;
    private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public int compareTo(MoveEfficiency o1) {
         if(numberOfEmptyTiles  != o1.numberOfEmptyTiles) return new Integer(numberOfEmptyTiles).compareTo(new Integer(o1.numberOfEmptyTiles));
         else if(score != o1.score) return new Integer(score).compareTo(new Integer(o1.score));
         else return 0;
    }



}
