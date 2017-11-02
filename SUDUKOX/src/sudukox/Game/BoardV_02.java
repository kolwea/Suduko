/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudukox.Game;

import javafx.scene.layout.GridPane;

/**
 *
 * @author Kolbe
 */
public class BoardV_02 {
    private Vector[] positions;
    private int[] values;
    private int[][] domains;
    
    private GridPane pane;
    private Cell[] cells;
    
    public BoardV_02(){
        
    }
    
    protected void setupGame(){
        
    }
    
    protected void setupGame(int[][] intialState){
        
    }
    
    protected boolean BTSearch(){
        if(recursiveBTSearch() != null){
            System.out.println("Search successful!");
            return true;
        }
        else{
            System.out.println("Search failed.");
            return false;
        }       
    }
    
    protected void printState(){
        
    }
    
    /////////////////////////////////////////////////////////////////HELPER FUNCTIONS///////////////////////////////////////////////////////////////////////
    
    private void initializeArrays(){
        positions = new Vector[81];
        values = new int[81];
        domains = new int[81][9];
        for(int i = 0 ; i < 81 ; i ++){
            for(int k = 0; k < 9; k++){
                domains[i][k] = 1;
            }
        }
    }
    
    private int[] recursiveBTSearch(){
        if(checkComplete())
            return values;
        int curr = chooseNextIndex();
        for(int value : getOrderedValues()){
            setValue(curr, value);
            if(checkConstraints(curr)){
                removeFromOtherDomain(curr,value);
                removeFromDomain(curr,value);
                int[] result = recursiveBTSearch();
                if(result != null)
                    return result;
                unsetValue(curr);
                addToDomain(curr,value);
                addToOtherDomain(curr,value);
            }
        }
        return null;
    }
    
    private void setValue(int index, int value){
        
    }
    
    private void unsetValue(int index){
        
    }
    
    private int getCellValue(){
        
    }
    
    private boolean checkComplete(){
        
    }
    
    private boolean checkConstraints(int index){
    
    }
    
    private boolean checkRow(int index){
        
    }
    
    private boolean checkColumn(int index){
        
    }
    
    private boolean checkRegion(int index){
        
    }
    
    private boolean forwardCheckDomains(){
        
    }
    
    private int getRow(int index){
        
    }
    
    private int getColumn(int index){
        
    }
    
    private int getRegion(int index){
        
    }
        
    private int chooseNextIndex(){
        
    }
    
    private int getReaminingValueCount(){
        
    }
    
    private int getDegreeValue(){
        
    }
    
    private int[] getOrderedValues(){
        
    }
    
    private boolean removeFromOtherDomain(int index, int value){
        
    }
    
    private boolean addToOtherDomain(int index, int value){
        
    }
        
    private boolean removeFromDomain(int index, int value){
        
    }
    
    private boolean addToDomain(int index, int value){
        
    }
    
    
    
}
