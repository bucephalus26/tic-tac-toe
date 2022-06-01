package com.tictactoe;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Stack;

public class TileBoard {

    private Label label;
    private StackPane pane;
    private InfoCenter infoCenter;
    private Tile[][] tiles = new Tile[3][3];
    private Line winningLine;

    private char playerTurn = 'X';
    private boolean isEndOfGame = true;

    // Sets size of board, draws tiles
    public TileBoard(InfoCenter infoCenter){
        this.infoCenter = infoCenter;
        pane = new StackPane();
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.INFO_CENTER_HEIGHT);
        pane.setTranslateX(UIConstants.APP_WIDTH / 2);
        pane.setTranslateY((UIConstants.TILE_BOARD_HEIGHT / 2) + UIConstants.INFO_CENTER_HEIGHT);

        addAllTiles();

        winningLine = new Line();
        pane.getChildren().add(winningLine);

    }

    // Drawing tiles
    private void addAllTiles() {
        for(int row = 0; row < 3; row ++){
            for(int col = 0; col < 3; col ++){
                Tile tile = new Tile();
                tile.getStackPane().setTranslateX((col * 100) - 100);
                tile.getStackPane().setTranslateY((row * 100) - 100);
                pane.getChildren().add(tile.getStackPane());
                tiles[row][col] = tile;
            }
        }
    }

    public void startNewGame(){
        isEndOfGame = false;
        playerTurn = 'X';
        for(int row = 0; row < 3; row ++){
            for(int col = 0; col < 3; col ++){
                tiles[row][col].setValue("");
            }
        }
        winningLine.setVisible(false);
    }

    // Change text for player
    public void changePlayerTurn(){
        if(playerTurn == 'X'){
            playerTurn = 'O';
        }
        else{
            playerTurn = 'X';
        }
        infoCenter.updateMessage("Player "+ playerTurn + "'s Turn");
    }

    // Returns player turn
    public String getPlayerTurn(){
        return String.valueOf(playerTurn);
    }

    // returns StackPane to add to root
    public StackPane getStackPane(){
        return pane;
    }

    // Calls methods that check for winner
    public void checkForWinner() {
        checkRowsForWinner();
        checkColsForWinner();
        checkTopLeftToBottomRightForWinner();
        checkTopRightToBottomLeftForWinner();
        checkForDraw();
    }


    public void checkRowsForWinner() {
        if(!isEndOfGame){
            for(int row = 0; row < 3; row++){
                if(tiles[row][0].getValue().equals(tiles[row][1].getValue())  &&
                        tiles[row][0].getValue().equals(tiles[row][2].getValue()) &&
                        !tiles[row][0].getValue().isEmpty()){

                    String winner = tiles[row][0].getValue();
                    endGame(winner, new WinningTiles(tiles[row][0], tiles[row][1], tiles[row][2]));
                    return;
                }
            }
        }
    }

    public void checkColsForWinner() {
        if(!isEndOfGame){
            for(int col = 0; col < 3; col++){
                if(tiles[0][col].getValue().equals(tiles[1][col].getValue())  &&
                        tiles[0][col].getValue().equals(tiles[2][col].getValue()) &&
                        !tiles[0][col].getValue().isEmpty()){

                    String winner = tiles[0][col].getValue();
                    endGame(winner, new WinningTiles(tiles[0][col], tiles[1][col], tiles[2][col]));
                    return;
                }
            }
        }
    }

    public void checkTopLeftToBottomRightForWinner() {
        if(!isEndOfGame){
            if(tiles[0][0].getValue().equals(tiles[1][1].getValue())  &&
                    tiles[0][0].getValue().equals(tiles[2][2].getValue()) &&
                    !tiles[0][0].getValue().isEmpty()){

                String winner = tiles[0][0].getValue();
                endGame(winner, new WinningTiles(tiles[0][0], tiles[1][1], tiles[2][2]));
                return;
            }
        }
    }
    public void checkTopRightToBottomLeftForWinner() {
        if(!isEndOfGame){
            if(tiles[0][2].getValue().equals(tiles[1][1].getValue())  &&
                    tiles[0][2].getValue().equals(tiles[2][0].getValue()) &&
                    !tiles[0][2].getValue().isEmpty()){

                String winner = tiles[0][2].getValue();
                endGame(winner, new WinningTiles(tiles[0][2], tiles[1][1], tiles[2][0]));
                return;
            }
        }
    }

    // Checks all tiles, ends the game
    public void checkForDraw() {
        if(!isEndOfGame){
            for(int row = 0; row < 3; row++){
                for(int col = 0; col < 3; col++){
                    if(tiles[row][col].getValue().isEmpty()){
                        return;
                    }
                }
            }
            isEndOfGame = true;
            infoCenter.updateMessage("The game ended in a draw...");
            infoCenter.showStartButton();
        }

    }

    // Ends the game. Draws winning line, updates text to show winner, allows user to restart game
    private void endGame(String winner, WinningTiles winningTiles) {
        isEndOfGame = true;
        drawWinningLine(winningTiles);
        infoCenter.updateMessage("Player " + winner + " wins!");
        infoCenter.showStartButton();

    }

    // Drawing line
    private void drawWinningLine(WinningTiles winningTiles){
        winningLine.setStartX(winningTiles.start.getStackPane().getTranslateX());
        winningLine.setStartY(winningTiles.start.getStackPane().getTranslateY());
        winningLine.setTranslateX(winningTiles.middle.getStackPane().getTranslateX());
        winningLine.setTranslateY(winningTiles.middle.getStackPane().getTranslateY());
        winningLine.setEndX(winningTiles.end.getStackPane().getTranslateX());
        winningLine.setEndY(winningTiles.end.getStackPane().getTranslateY());



        winningLine.setVisible(true);
    }

    // Drawing the line on winning tiles
    private class WinningTiles{
        Tile start;
        Tile middle;
        Tile end;

        public WinningTiles(Tile start, Tile middle, Tile end){
            this.start = start;
            this.middle = middle;
            this.end = end;
        }
    }

    // Tiles layout
    private class Tile{

        private StackPane pane;
        private Label label;

        // Tile layout
        public Tile(){
            pane = new StackPane();
            pane.setMinSize(100, 100);

            // Tile border
            Rectangle border = new Rectangle();
            border.setHeight(100);
            border.setWidth(100);
            border.setFill(Color.TRANSPARENT);
            border.setStroke(Color.BLACK);

            pane.getChildren().add(border);

            // Tile label
            label = new Label("");
            label.setAlignment(Pos.CENTER);
            label.setFont(Font.font(24));
            pane.getChildren().add(label);

            // Change to text on tile to X or O depending on player turn
            pane.setOnMouseClicked(event -> {
                if(label.getText().isEmpty() && !isEndOfGame){
                    label.setText(getPlayerTurn());
                    changePlayerTurn();
                    checkForWinner();
                }
            });

        }

        // Returns pane to add to root
        public StackPane getStackPane() {
            return pane;
        }

        // Returns text on each tile
        public String getValue(){
            return label.getText();
        }

        // Sets text on tiles - X/O
        public void setValue(String value){
            label.setText(value);
        }

    }




}
