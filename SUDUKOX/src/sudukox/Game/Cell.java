package sudukox.Game;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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
    protected final int col, row, region, index;
    private int[] domain;
    
    protected Cell(int index, int col, int row, int region){
        this.col = col;
        this.row = row;
        this.region = region;
        this.index = index;
        domain = new int[]{1,1,1,1,1,1,1,1,1};
    }
    
    protected StackPane getInitalBody(){
        cellBody = new StackPane();
        text = new Label();
        cellBody.setAlignment(Pos.CENTER);
        //text.setTextAlignment(TextAlignment.CENTER);
        
        cellBody.getChildren().add(text);
        
        return cellBody;
    }
    
    protected void printCell(){
        System.out.println("Index: " + index);
        System.out.println("Column: " + col + " Row: " + row + " Region: " + region);
    }
    
    protected Cell cloneCell(){
        return new Cell(index, col, row, region);
    }
    
    protected void setCellValue(int val){
        this.value = val;
        updateDisplay();
        cellBody.setStyle("-fx-background-color: green;");
    }
    
    protected int getCellValue(){
        return this.value;
    }
    
    protected void removeFromDomain(int val){
        if(domain[val-1] != 1){
            System.out.println("Value not in domain: " + val);
            printDomain();
        }
        else{
            domain[val-1] = 0;
        }
    }
    
    public void printDomain(){
        for(int i : domain)
            System.out.print(i + " " );
        System.out.println();
    }
    
    protected void addToDomain(int val){
        if(domain[val-1] != 0)
            System.out.println("Value already in domain");
        else{
            domain[val-1] = 1;
        }
    }
    
    private void updateDisplay(){
        text.setText("");
        text.setText(Integer.toString(value));
    }
    
}
