package sudukox.Game;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kolbe
 */
public class Cell {

    private int value;
    private StackPane cellBody;
    private Label text;

    protected Cell(int index) {
      
    }

    protected StackPane getInitalBody() {
        cellBody = new StackPane();
        text = new Label();
        cellBody.setAlignment(Pos.CENTER);
        cellBody.setStyle("-fx-background-color: white;");
        //text.setTextAlignment(TextAlignment.CENTER);
        cellBody.getChildren().add(text);
        
        return cellBody;
    }

    protected void update(int val) {
        if (val == this.value); else {
            setCellValue(val);
            updateDisplay();
        }
    }

    protected void setCellValue(int val) {
        this.value = val;
        cellBody.setStyle("-fx-background-color: green;");
        if (value == 0) {
            cellBody.setStyle("-fx-background-color: yellow;");
        }

    }

    private void updateDisplay() {
        text.setText("");
        if (value != 0) {
            text.setText(Integer.toString(value));
        }
    }

}
