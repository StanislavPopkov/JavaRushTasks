package com.javarush.task.task35.task3513;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile [][] gameTiles;
    protected int score = 0;
    protected int maxTile;
    private Stack<Tile[][]> previousStates = new Stack<>();
    private Stack<Integer> previousScores = new Stack<>();
    private boolean isSaveNeeded = true;

    public Model() {
        resetGameTiles();
    }

    private List<Tile> getEmptyTiles(){
        List<Tile> tileList = new ArrayList<>();
        for(int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].value == 0) tileList.add(gameTiles[i][j]);
            }
        }
        return tileList;
    }

    private void saveState(Tile [][] tiles){
        Tile [][] savedTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for(int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                savedTiles[i][j] = new Tile(gameTiles[i][j].value);
            }
        }
        previousStates.push(savedTiles);
        previousScores.push(new Integer(score));
        isSaveNeeded = false;
    }

    public void rollback(){
        if(!previousStates.empty() && !previousScores.empty()) {
            gameTiles = previousStates.pop();
            score = previousScores.pop();
        }

    }

    private void addTile(){
        List<Tile> tileList = getEmptyTiles();
        if(tileList.size() > 0){
            int randomNumberList = (int) (Math.random() * tileList.size());
            int randomNumberValue = (int) (Math.random() < 0.9 ? 2 : 4);
            tileList.get(randomNumberList).value = randomNumberValue;
        }
    }



    public void left(){
        saveState(gameTiles);
        isSaveNeeded = false;
        boolean isChanged = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) {
                isChanged = true;
            }
        }
        if (isChanged) {
            addTile();
        }
        isSaveNeeded = true;

    }

    public void down(){
        saveState(gameTiles);
        isSaveNeeded = false;
        Tile[][] downArray = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for(int i = 0; i < gameTiles.length; i++) {
            for(int j = 0; j < gameTiles[i].length; j++) {
                downArray[j][FIELD_WIDTH - i - 1] = gameTiles[i][j];
            }
        }
        boolean isChanged = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(downArray[i]) | mergeTiles(downArray[i])) {
                isChanged = true;
            }
        }
        if (isChanged) {
            addTile();
        }
        for(int i = 0; i < gameTiles.length; i++) {
            for(int j = 0; j < gameTiles[i].length; j++) {
                gameTiles[i][j] = downArray[j][FIELD_WIDTH - i - 1];
            }
        }

        isSaveNeeded = true;
    }

    public void up(){
        saveState(gameTiles);
        isSaveNeeded = false;
        Tile[][] upArray = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for(int i = 0; i < gameTiles.length; i++) {
            for(int j = 0; j < gameTiles[i].length; j++) {
                upArray[i][j] = gameTiles[j][FIELD_WIDTH - i - 1];
            }
        }
        boolean isChanged = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(upArray[i]) | mergeTiles(upArray[i])) {
                isChanged = true;
            }
        }
        if (isChanged) {
            addTile();
        }
        for(int i = 0; i < gameTiles.length; i++) {
            for(int j = 0; j < gameTiles[i].length; j++) {
                gameTiles[j][FIELD_WIDTH - i - 1] = upArray[i][j];
            }
        }

        isSaveNeeded = true;
    }

    public void right(){
        saveState(gameTiles);
        isSaveNeeded = false;
        Tile[][] rightArray = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for(int i = 0; i < gameTiles.length; i++) {
            for(int j = 0; j < gameTiles[i].length; j++) {
                rightArray[i][j] = gameTiles[FIELD_WIDTH - i - 1][FIELD_WIDTH - j - 1];
            }
        }
        boolean isChanged = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(rightArray[i]) | mergeTiles(rightArray[i])) {
                isChanged = true;
            }
        }
        if (isChanged) {
            addTile();
        }
        for(int i = 0; i < gameTiles.length; i++) {
            for(int j = 0; j < gameTiles[i].length; j++) {
                gameTiles[i][j] = rightArray[FIELD_WIDTH - i - 1][FIELD_WIDTH - j - 1];
            }
        }

        isSaveNeeded = true;
    }

    protected   void resetGameTiles(){
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for(int i = 0; i < FIELD_WIDTH; i++){
            for(int j = 0; j < FIELD_WIDTH; j++){
                gameTiles[i][j] = new Tile();
            }
        }
        maxTile = 0;
        addTile();
        addTile();

    }

    private boolean compressTiles(Tile[] tiles){
        AtomicBoolean marker = new AtomicBoolean();
        Arrays.sort(tiles, new Comparator<Tile>() {
            @Override
            public int compare(Tile o1, Tile o2) {
                if(o1.value == 0 & o2.value == 0){
                    return 0;
                }
                else if(o1.value == 0 | o2.value == 0){
                    int result = o2.value - o1.value;
                    if(result < 0) marker.set(true);
                    return result;
                }
                else return 0;
            }
        });

        return marker.get();
    }

    private boolean mergeTiles(Tile[] tiles){
        AtomicBoolean marker = new AtomicBoolean();
        compressTiles(tiles);
            Arrays.sort(tiles, new Comparator<Tile>() {
                @Override
                public int compare(Tile o1, Tile o2) {
                    if (o1.value == o2.value & o1.value != 0) {
                        o1.value *= 2;
                        o2.value = 0;
                        compressTiles(tiles);
                        score += o1.value;
                        if (maxTile < o1.value) maxTile = o1.value;
                        marker.set(true);
                        return 0;
                    }
                    else return 0;
                }
            });
            return marker.get();
        }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public boolean canMove(){
        if(getEmptyTiles().size() > 0){
            return true;
        }
        for(int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if((j+1) < FIELD_WIDTH && gameTiles[i][j].value == gameTiles[i][j+1].value) return true;
                else if((i+1) < FIELD_WIDTH && gameTiles[i][j].value == gameTiles[i+1][j].value) return true;

            }
        }
        return false;
    }

    public boolean hasBoardChanged(){
        int countTemp = 0;
        Tile[][] tempTile = previousStates.peek();
        for(int i = 0; i < tempTile.length; i++) {
            for (int j = 0; j < tempTile.length; j++) {
                countTemp += tempTile[i][j].value;
            }
        }

        int countReal = 0;
        for(int i = 0; i < getGameTiles().length; i++) {
            for (int j = 0; j < gameTiles.length; j++) {
                countReal += gameTiles[i][j].value;
            }
        }

        if(countTemp != countReal) return true;
        else return false;
    }

    public MoveEfficiency getMoveEfficiency(Move move){
        MoveEfficiency eff = null;
        move.move();
        if(!hasBoardChanged()) eff = new MoveEfficiency(-1, 0, move);
        else eff = new MoveEfficiency(getEmptyTiles().size(), score, move);
        rollback();
        return eff;
    }

    public void autoMove(){
        PriorityQueue<MoveEfficiency> queue = new PriorityQueue<>(4, Collections.reverseOrder());
        queue.offer(getMoveEfficiency(new Move(){
            @Override
            public void move() {
                   left();
            }
        }));
        queue.offer(getMoveEfficiency(this::right));
        queue.offer(getMoveEfficiency(() -> up()));
        queue.offer(getMoveEfficiency(() -> down()));


        queue.poll().getMove().move();
    }

    public void randomMove(){
        int random = ((int) (Math.random() * 100)) % 4;
        switch (random) {
            case 0:
                left();
                break;
            case 1:
                right();
                break;
            case 2:
                up();
                break;
            case 3:
                down();
                break;
        }

    }



}
