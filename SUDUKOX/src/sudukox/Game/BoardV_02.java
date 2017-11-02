/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudukox.Game;

import java.util.ArrayList;
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

    public BoardV_02() {

    }

    public void setupGame() {
        initializeArrays();
    }

    public void setupGame(int[][] intialState) {

    }

    public boolean BTSearch() {
        int[] solution = recursiveBTSearch();
        if (solution != null) {
            System.out.println("Search successful!");
            this.printState(solution);
            return true;
        } else {
            System.out.println("Search failed.");
            return false;
        }
    }

    protected void printState(int[] state) {
        for(int i = 0; i < 9; i++){
            for(int k = 0; k < 9; k++){
                System.out.print(state[k] + "-");
            }
            System.out.println();
        }
    }

    /////////////////////////////////////////////////////////////////HELPER FUNCTIONS///////////////////////////////////////////////////////////////////////
    private void initializeArrays() {
        positions = new Vector[81];
        values = new int[81];
        domains = new int[81][9];
    }

    private int[] recursiveBTSearch() {
        if (checkComplete()) {
            return values;
        }
        int curr = chooseNextIndex();
        for (int value : getOrderedValues(curr)) {
            setValue(curr, value);
            if(removeFromAllDomain(curr, value)!=true)
                return null;
            int[] result = recursiveBTSearch();
            if (result != null) {
                return result;
            }
            unsetValue(curr);
            if(addToAllDomain(curr, value)!=true)
                return null;
//            }
        }
        return null;
    }

    private void setValue(int index, int value) {
        values[index] = value;
        System.out.println("Cell " + index + "set to value " + value + "." );
    }

    private void unsetValue(int index) {
        values[index] = 0;
    }

    private int getCellValue(int index) {
        return values[index];
    }

    private boolean checkComplete() {
        for (int i = 0; i < 81; i++) {
            if (values[i] == 0) {
                System.out.println("Puzzle incomplete.");
                return false;
            }
        }
        return true;
    }

    private boolean domainsRemain() {
        boolean remains = true;
        for (int i = 0; i < 81; i++) {
            if(getRemainingValueCount(i) == 0){
                ///////////////////////////////////////////////////////////////////PRINT
                System.out.println("Cell " + i + "has no remaing values");
                remains = false;
            }
        }
        return remains;
    }

    private int getRow(int index) {
        return (int) index / 9;
    }

    private int getColumn(int index) {
        return (int) index % 9;
    }

    private int getRegion(int index) {
        int row = getRow(index);
        int col = getColumn(index);

        int rowReg = row / 3;
        int colReg = col / 3;
        int reg = -1;

        switch (rowReg) {
            case 0:
                switch (colReg) {
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
                switch (colReg) {
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
                switch (colReg) {
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
        return reg;
    }

    private int chooseNextIndex() {
        int MRV=20, degree=-1, chosen=-1;
        ArrayList<Integer> ties = new ArrayList<>();
        for(int i = 0; i < 81; i++){
            if(getRemainingValueCount(i) < MRV && this.getCellValue(i)==0){
                MRV = getRemainingValueCount(i);
                ties = new ArrayList();
                ties.add(i);
                chosen = i;
            }
            else if(getRemainingValueCount(i) == MRV){
                ties.add(i);
            }
        }
        ties.trimToSize();
        if(ties.size() > 1){
            for(int a : ties){
                if(getDegreeValue(a) > degree){
                    degree = getDegreeValue(a);
                    chosen = a;
                }
            }
        }
        System.out.println("Chosen index: " + chosen);
        return chosen;
    }

    private int getRemainingValueCount(int index) {
        int count = 0;
        for(int i = 0; i < 9; i++){
            if(domains[index][i] == 0)
                count++;
        }
        return count;
    }

    private int getDegreeValue(int index) {
        int count = 0;
        for(int i = 0; i < 81; i++){
            if((getRow(i) == getRow(index))||(getColumn(index) == getColumn(i))||(getRegion(i) == getRegion(index)))
                if(getCellValue(i) != 0)
                    count++;
        }
        return count;
    }

    private int[] getOrderedValues(int index) {
        ArrayList<Integer> hold = new ArrayList();
        for(int i = 0; i < 9; i++){
            if(domains[index][i] != 1)
                hold.add(i+1);
        }
        int[] list = new int[hold.size()];
        int i = 0;
        for(int a:hold){
            list[i] = a;
            i++;
        }
        return list;
    }

    private boolean removeFromAllDomain(int index, int value) {
        for(int i = 0; i < 81; i++){
            if((getRow(i) == getRow(index))||(getColumn(index) == getColumn(i))||(getRegion(i) == getRegion(index))){
                if(removeFromDomain(i,value)==false)
                    return false;
            }
        }
        System.out.println();
        return true;    
    }

    private boolean addToAllDomain(int index, int value) {
        for(int i = 0; i < 81; i++){
            if((getRow(i) == getRow(index))||(getColumn(index) == getColumn(i))||(getRegion(i) == getRegion(index))){
                if(addToDomain(i,value)==false)
                    return false;
            }
        }
        System.out.println();
        return true;    
    }

    private boolean removeFromDomain(int index, int value) {
        this.printDomain(index);
        if(domains[index][value-1] != 0){
            System.out.println("Value " + value +" not in domain of " + index + ". Removal failed.");
            return false;
        }
        System.out.println("Value " + value + " removed from " + index + "'s domain.");
        domains[index][value-1] = 1;
        this.printDomain(index);
        return true;
    }

    private boolean addToDomain(int index, int value) {
        this.printDomain(index);
        if(domains[index][value-1] != 1){
            System.out.println("Value " + value +" not in domainof " + index + ". Addition failed.");
            return false;
        }
        System.out.println("Value " + value + " added to " + index + "'s domain.");
        domains[index][value-1] = 0;
        this.printDomain(index);
        return true;
    }

    private void printDomain(int index){
        System.out.print("Domain of " + index + ": ");
        for(int i = 0; i <9 ; i++){
            System.out.print(domains[index][i] + " ");
        }
        System.out.println();
    }
}
