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
    private int[] assignments;

    private GridPane pane;
    private Cell[] cells;

    public BoardV_02() {

    }

    /////////////////////////////////////////////////////////////////CLASS FUNCTIONS///////////////////////////////////////////////////////////////////////
    public void setupGame() {
        initializeArrays();
    }

    public void setupGame(int[] initialState) {
        initializeArrays();
        for (int i = 0; i < initialState.length; i++) {
            if (initialState[i] != 0) {
                setValue(i, initialState[i]);
                removeFromAllDomain(i, initialState[i]);
            }
        }
    }

    public boolean BTSearch() {
        int[] solution = null;
        for(int a : values){
            if(getCellValue(a) == 0){
                solution = recursiveBTSearch(a);
            }
            if(solution != null)
                break;
        }
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
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                System.out.print(state[k + (9 * i)] + "-");
            }
            System.out.println();
        }
    }

    private void initializeArrays() {
        positions = new Vector[81];
        values = new int[81];
        domains = new int[81][9];
        assignments = new int[81];
    }

    public int[] testState0() {
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
        
    public int[] testState1() {
        int[] state = new int[81];
        state[2] = 5;
        state[4] = 1;
        state[11] = 2;
        state[14] = 4;
        state[16] = 3;
        state[18] = 1;
        state[20] = 9;
        state[24] = 2;
        state[26] = 6;
        state[27] = 2;
        state[31] = 3;
        state[37] = 4;
        state[42] = 7;
        state[45] = 5;
        state[50] = 7;
        state[53] = 1;
        state[57] = 6;
        state[59] = 3;
        state[64] = 6;
        state[66] = 1;
        state[76] = 7;
        state[79] = 5;
        return state;
    }
    

    //////////////////////////////////////////////////////////////////HELPER FUNCTIONS//////////////////////////////////////////////////////////////
    private int[] recursiveBTSearch(int curr) {
        if (checkComplete()) {
            return values;
        }
        int[] validValues = orderedValues(curr);
        System.out.print("Valid Value ");
        this.printArray(validValues);
        for (int value : validValues) {
            setValue(curr, value);
            removeFromAllDomain(curr, value);
            if(domainsRemain()){
                int[] result = recursiveBTSearch(chooseNextIndex());
                if (result != null) {
                    return result;
                }
            }
            System.out.println("Reseting index " + curr);
            unsetValue(curr, 0);
            System.out.println("---------------------------------------------------------------------------------------|");
            addToAllDomain(curr, value);
        }
        return null;
    }

    private void setValue(int index, int value) {
        values[index] = value;
        assignments[index] = 1;
        System.out.println("Cell " + index + " set to value " + value + ".");
    }
        
    private void unsetValue(int index, int value) {
        values[index] = value;
        assignments[index] = 0;
        System.out.println("Cell " + index + " set to value " + value + ".");
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
            if (remainingValues(i) == 0 && getCellValue(i) == 0) {
                ///////////////////////////////////////////////////////////////////PRINT
                System.out.println("Cell " + i + " has no remaing values");
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
        int doneIndex = 0;
        int MRV = Integer.MAX_VALUE;
        ArrayList<Integer> ties = new ArrayList<>();
        for (int i = 0; i < 81; i++) {
            if (getCellValue(i) == 0) {
                int currMRV = remainingValues(i);
                if (currMRV == MRV) {
                    ties.add(i);
                } else if (currMRV < MRV) {
//                    System.out.println("MRV for " + i + ": " + currMRV);
                    MRV = currMRV;
                    ties = new ArrayList();
                    ties.add(i);
                    doneIndex = i;
                }
            }
        }
        ties.trimToSize();
        for (int curr : ties) {
            int doneDegree = remainingDegree(doneIndex), currDegree = remainingDegree(curr);
            if (currDegree > doneDegree) {
                doneIndex = curr;
            }
//            System.out.println("Indexed: " + curr + " has " + currDegree + " remaining values.");

        }
        System.out.println("---------------------------------------------------------------------------------------+");
        System.out.println("Chosen index: " + doneIndex);
        return doneIndex;
    }

    private int remainingValues(int index) {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (domains[index][i] == 0) {
                count++;
            }
        }
        return count;
    }

    private int remainingDegree(int index) {
        int count = 0;
        for (int i = 0; i < 81; i++) {
            if ((getRow(i) == getRow(index)) || (getColumn(index) == getColumn(i)) || (getRegion(i) == getRegion(index))) {
                if (getCellValue(i) == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    private int[] orderedValues(int index) {
        ArrayList<Integer> hold = new ArrayList();
        int[] list = null;
        for (int i = 0; i < 9; i++) {
            if (domains[index][i] == 0) {
                hold.add(i + 1);
            }
        }
        if (!hold.isEmpty()) {
            list = new int[hold.size()];
            for (int i = 0; i < list.length; i++) {
                list[i] = hold.get(i);
            }
        }
        return list;
    }

    private boolean removeFromAllDomain(int index, int value) {
        for (int i = 0; i < 81; i++) {
            if ((getRow(i) == getRow(index)) || (getColumn(index) == getColumn(i)) || (getRegion(i) == getRegion(index))) {
                removeFromDomain(i, value);
            }
        }
//        System.out.println();
        return true;
    }

    private boolean addToAllDomain(int index, int value) {
        for (int i = 0; i < 81; i++) {
            if ((getRow(i) == getRow(index)) || (getColumn(index) == getColumn(i)) || (getRegion(i) == getRegion(index))) {
                addToDomain(i, value);
            }
        }
//        System.out.println();
        return true;
    }

    private boolean removeFromDomain(int index, int value) {
//        this.printDomain(index);
        if (domains[index][value - 1] != 0) {
//            System.out.println("Value " + value + " not in domain of " + index + ". Removal failed.");
            return false;
        }
//        System.out.println("Value " + value + " removed from " + index + "'s domain.");
        domains[index][value - 1] = 1;
//        this.printDomain(index);
        return true;
    }

    private boolean addToDomain(int index, int value) {
//        this.printDomain(index);
        if (domains[index][value - 1] != 1) {
//            System.out.println("Value " + value + " not in domainof " + index + ". Addition failed.");
            return false;
        }
//        System.out.println("Value " + value + " added to " + index + "'s domain.");
        domains[index][value - 1] = 0;
//        this.printDomain(index);
        return true;
    }

    private void printDomain(int index) {
        System.out.print("Domain of " + index + ": ");
        for (int i = 0; i < 9; i++) {
            System.out.print(domains[index][i] + " ");
        }
        System.out.println();
    }
    
    private void printArray(int[] array){
        System.out.print("Array:");
        for(int a : array){
            System.out.print(" " + a);
        }
        System.out.println();
    }

}
