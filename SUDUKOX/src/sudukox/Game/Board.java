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
public final class Board {

    private Cell[] cells;
    private int[][] state;
    private final GridPane pane;
    
    public Board(GridPane pane){
        this.pane = pane;
        setupCells();
    }
    
    //Initialize each cell for use in the sudoku puzzle. 
    //Each cell is given a Row and Column index as well as a general index to use as a primary key
    public void setupCells(){
        cells = new Cell[81];
        state = new int[9][9];
        int r=0,c=0;
        for(int i = 0; i < cells.length; i++){
            Cell current = new Cell(i,c,r, getRegionIndex(c,r));  
//            current.printCell();
            pane.add(current.getInitalBody(),c,r);
            cells[i] = current;
            state[c][r] = i;
            //Keeps track of the grid cells row and column index being initalized into the Cell object
            c++;
            if(c%9 == 0){
                r++;
                c = 0;
            }
            
        }
    }
    
    public void setupTestState(int[] state){
        int i = 0;
        for(Cell curr : cells){
//            System.out.println("Index " + i + " Value: " + state[i]);
            if(state[i] != 0)
                curr.setCellValue(state[i]);
            i++;
        }
    }
    public void setupRemove(int[] state){
        int i = 0;
        for(Cell curr : cells){
//            System.out.println("Index " + i + " Value: " + state[i]);
            if(state[i] != 0)
                curr.setCellValue(state[i]);
            i++;
        }
    }    
    public void setValueTest(int i){
        if(i <= 80){
        int[] state = this.testState0();
        Cell curr = cells[i];
        if(state[i] != 0)
                curr.setCellValue(state[i]);
        }
    }
    
    public int[] testState0(){
        int[] state = new int[81];
        state[2] = 1;
        state[5] = 2;
        state[11] = 5;
        state[14] = 6;
        state[16] = 3;
        state[18] = 4;
        state[19] = 6;
        state[23] = 5;
        state[30] = 1;
        state[32] = 4;
        state[36] = 6;
        state[39] = 8;
        state[42] = 1;
        state[43] = 4;
        state[44] = 3;
        state[49] = 9;
        state[51] = 5;
        state[53] = 8;
        state[54] = 8;
        state[58] = 4;
        state[59] = 9;
        state[61] = 5;
        state[63] = 1;
        state[66] = 3;
        state[67] = 2;
        state[74] = 9;
        state[78] = 3;
        return state;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    //Search Functions
    
    
        
    private int getRegionIndex(int col, int row){
        int rowReg = row / 3;
        int colReg = col / 3;
        int reg=-1;
//        System.out.println("Cordinate: " + col + ", " + row);       
//        System.out.println("X: " + rowReg + " Y: " + colReg);
        switch(rowReg){
            case 0:
                switch(colReg){
                    case 0:
                        reg = 0;
                        break;
                    case 1:
                        reg = 1;
                        break;
                    case 2:
                        reg = 2;
                        break;
                }
                break;
            case 1:                
                switch(colReg){
                    case 0:
                        reg = 3;
                        break;
                    case 1:
                        reg = 4;
                        break;
                    case 2:
                        reg = 5;
                        break;
                }
                break;
            case 2:              
                switch(colReg){
                    case 0:
                        reg = 6;
                        break;
                    case 1:
                        reg = 7;
                        break;
                    case 2:
                        reg = 8;
                        break;
                }
                break;  
        }
//                System.out.println("Region: " + reg);
        return reg;
    }
    
    private void setValue(Cell current, int value){
        current.setCellValue(value);
        for(Cell hold : cells){
            if(hold.row == current.row || hold.col == current.col || hold.region == hold.region)
                hold.removeFromDomain(value);
        }
    }
    
    private void unsetValue(Cell current, int value){
        current.setCellValue(0);
        for(Cell hold : cells){
            if(hold.row == current.row || hold.col == current.col || hold.region == hold.region)
                hold.addToDomain(value);
        }
    }
    
    private boolean constraints(Cell curr){
        return checkRowConstraint(curr) && checkColumnConstraint(curr) && checkRegionConstraint(curr); 
   }
    
    private boolean checkRowConstraint(Cell current){
        int[] row = new int[9];
        int i = 0;
        for(Cell hold : cells){
            if(current.row == hold.row)
                row[i] = hold.getCellValue();
        }
        return checkConstraint(row);
    }
    
    private boolean checkColumnConstraint(Cell current){
        int[] col = new int[9];
        int i = 0;
        for(Cell hold : cells){
            if(current.col == hold.col)
                col[i] = hold.getCellValue();
        }
        return checkConstraint(col);
    }
        
    private boolean checkRegionConstraint(Cell current){
        int[] reg = new int[9];
        int i = 0;
        for(Cell hold : cells){
            if(current.region == hold.region)
                reg[i] = hold.getCellValue();
        }
        return checkConstraint(reg);
    }
        
    private boolean checkConstraint(int[] children){
        int[] test = new int[9];
        for(int i = 0; i < 9; i++){
            int curr = children[i];
            curr--;
            if(curr != -1){
                if(test[curr] != 1)
                    test[curr] = 1;
                else{
                    return false;
                }
            }
        }
        return true;
    }

}
