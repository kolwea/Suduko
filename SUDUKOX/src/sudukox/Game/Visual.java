/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudukox.Game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 *
 * @author Kolbe
 */
public class Visual extends Thread{
    private BoardV_03 cake;
    private GridPane pane;
    private KeyFrame keyframe;
    private Timeline timeline;    
    private Cell[] cells;
    private int[] values;
    
    public Visual(GridPane pane, BoardV_03 look){
        this.pane = pane;
        this.cake = look;
        this.values = look.getValues();
    }
    
    public void setup(){
        setupCells();
        setupTimeline();
    }
    
    @Override
    public void start(){
        setup();     
    }
    
    protected void updateValues(int[] values){
        this.values = values;
    }
    
    private void setupCells() {
        cells = new Cell[values.length];
        for (int i = 0; i < values.length; i++) {
            Cell current = new Cell(i);
            pane.add(current.getInitalBody(), cake.getColumn(i), cake.getRow(i));
            cells[i] = current;
        }
    }
    
    private void setupTimeline(){        
        keyframe = new KeyFrame(Duration.millis(10), (ActionEvent event) -> {
            update();
        });
        timeline = new Timeline(keyframe);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    private void update(){
        for(int i = 0; i < cells.length ; i++){
            cells[i].update(values[i]);
        }
    }}
