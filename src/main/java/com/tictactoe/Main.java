package com.tictactoe;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private InfoCenter infoCenter;
    private TileBoard tileBoard;

    @Override
    public void start(Stage primaryStage) {
        try{
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, UIConstants.APP_WIDTH, UIConstants.APP_HEIGHT);
            initLayout(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Tic-Tac-Toe");
            primaryStage.show();
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    // Initialise the layout
    private void initLayout(BorderPane root) {
        initInfoCenter(root);
        initTileBoard(root);
    }

    // Initialise the tileBoard
    private void initTileBoard(BorderPane root) {
        tileBoard = new TileBoard(infoCenter);
        root.getChildren().add(tileBoard.getStackPane());
    }

    // Initialise game functionality
    private void initInfoCenter(BorderPane root) {
        infoCenter = new InfoCenter();
        infoCenter.setStartButtonOnAction(startNewGame());
        root.getChildren().add(infoCenter.getStackPane());

    }

    // Registering EventHandler for Start button
    private EventHandler<ActionEvent> startNewGame(){
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // Hides button
                infoCenter.hideStartButton();
                // Starts with X
                infoCenter.updateMessage("Player X's Turn");
                // Starts the game
                tileBoard.startNewGame();

            }
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}