/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudukox.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Kolbe
 */
public class BoardV_03 {

    private int[] values;
    private Map<Integer, ArrayList<Integer>> domains;

    public BoardV_03() {

    }

    //////////////////////////////////////////////////CLASS FUNCTIONS/////////////////////////////////////////////////////    
    public void setupGame() {
        initializeArrays();
    }

    public void setupGame(int[] initialState) {
        initializeArrays();
        for (int i = 0; i < initialState.length; i++) {
            if (initialState[i] != 0) {
                setValue(i, initialState[i]);
                removeFromAllDomain(i, getCellValue(i));
            }
        }
    }

    public boolean BTSearch() {
        int[] solution = this.recursiveBTSearch(chooseNextIndex());
//        for (int a : values) {
//            if (getCellValue(a) == 0) {
//                solution = recursiveBTSearch(a);
//            }
//            if (solution != null) {
//                break;
//            }
//        }
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
        System.out.println("WMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMW");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("WMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMWMW");
        values = new int[81];
        domains = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            domains.put(i, getNewDomain());
        }
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
        int[] state2 = new int[]{0, 0, 5, 0, 1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 4, 0, 3, 0, 1, 0, 9, 0, 0, 0, 2, 0, 6, 2, 0, 0, 0, 3, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 7, 0, 0, 5, 0, 0, 0, 0, 7, 0, 0, 1, 0, 0, 0, 6, 0, 3, 0, 0, 0, 0, 6, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 5, 0};
        return state2;
    }

    public int[] testState2() {
        return new int[]{6, 7, 0, 0, 0, 0, 0, 0, 0, 0, 2, 5, 0, 0, 0, 0, 0, 0, 0, 9, 0, 5, 6, 0, 2, 0, 0, 3, 0, 0, 0, 8, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 1, 0, 0, 0, 4, 7, 0, 0, 0, 0, 0, 0, 8, 6, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 6, 0, 5, 0, 0, 7, 0};
    }

    ///////////////////////////////////////////////HELPER FUNCTIONS////////////////////////////////////////////////////////    
    private int[] recursiveBTSearch(int curr) {
        System.out.println("+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*");
        System.out.println("Current index: " + curr);
//        printState(values);
//        this.printAllDomains();
        if (checkComplete()) {
            return values;
        }

//        this.printDomain(curr);
        if (domainsRemain()) {
            ArrayList<Integer> validValues = (ArrayList<Integer>) domains.get(curr).clone();
            for (int value : validValues){
                setValue(curr, value);
                removeFromAllDomain(curr, value);
//                this.printState(values);
                if (checkConstraints(curr)) {
                    int[] result = recursiveBTSearch(chooseNextIndex());
                    if (result != null) {
                        return result;
                    }
                }
                addToAllDomain(curr, value);
                unsetValue(curr);
            }
        }
        System.out.println("Backing up...");
        return null;
    }

    private void setValue(int index, int value) {
        values[index] = value;
        System.out.println("Cell " + index + " set to value " + value + ".");
    }

    private void unsetValue(int index) {
        values[index] = 0;
        System.out.println("Cell " + index + " set to " + 0 + ".");
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
        boolean done = true;
        for (int i = 0; i < values.length; i++) {
            if (domains.get(i).isEmpty() && getCellValue(i) == 0) {
                done = false;
            }
        }
        return done;
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
//        System.out.println("Chosen index: " + doneIndex);
        return doneIndex;
    }

    private int remainingValues(int index) {
        ArrayList<Integer> values = domains.get(index);
        values.trimToSize();
        return values.size();
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

    private void removeFromAllDomain(int index, int value) {
        int row = getRow(index), col = getColumn(index), reg = getRegion(index);
        for (int i = 0; i < values.length; i++) {
            if (getRow(i) == row) {
                removeFromDomain(i, value);
            } else if (getColumn(i) == col) {
                removeFromDomain(i, value);
            } else if (getRegion(i) == reg) {
                removeFromDomain(i, value);
            }
        }
    }

    private void addToAllDomain(int index, int value) {
        int row = getRow(index), col = getColumn(index), reg = getRegion(index);
        for (int i = 0; i < values.length; i++) {
            if (getRow(i) == row) {
                addToDomain(i, value);
            } else if (getColumn(i) == col) {
                addToDomain(i, value);
            } else if (getRegion(i) == reg) {
                addToDomain(i, value);
            }
        }
    }

    private void removeFromDomain(int index, int value) {
        ArrayList currDomain = domains.get(index);
        if (currDomain.contains(value)) {
            currDomain.remove((Object) value);
//            System.out.println(value + " removed from " + index + "'s domain.");
//            this.printDomain(index);
        } else {
//            System.out.println(value + " not in " + index + "'s domain.");
        }

    }

    private void addToDomain(int index, int value) {
        ArrayList<Integer> currDomain = domains.get(index);
        if (!currDomain.contains(value)) {
            currDomain.add((Integer) value);
//            System.out.println(value + " added to " + index + "'s domain.");
//            this.printDomain(index);
        } else {
//            System.out.println(value + "already in " + index + "'s domain.");
        }

    }

    private boolean checkConstraints(int index) {
        boolean row, col, reg;
        row = this.checkRow(index);
        col = this.checkColumn(index);
        reg = this.checkRegion(index);
        if (!row) {
            System.out.println("Row failed");
        }
        if (!col) {
            System.out.println("Column failed");
        }
        if (!reg) {
            System.out.println("Region failed");
        }
        boolean done = row && col && reg;
        return done;
    }

    private boolean checkRow(int index) {
        int row = getRow(index);
        int start = row * 9;
        int[] check = new int[9];
        for (int i = start; i < start + 9; i++) {
            int cellValue = getCellValue(i);
            if (cellValue != 0) {
                if (check[cellValue - 1] == 1) {
                    System.out.println("Val: " + cellValue + " already in array");
                    return false;
                } else {
                    check[cellValue - 1] = 1;
                }
            }
        }
        return true;
    }

    private boolean checkColumn(int index) {
        int column = getColumn(index);
//        System.out.println("LOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOK: " + column);
        int start = column;
        int[] check = new int[9];
        for (int i = start; i < start + 9 * 9; i += 9) {
//            System.out.println("CURRENT COLUMN INDEX: " + i + "VALUE: " + getCellValue(i));
            int cellValue = getCellValue(i);
            if (cellValue != 0) {
                if (check[cellValue - 1] == 1) {
                    System.out.println("Val: " + cellValue + " already in array");
                    return false;
                } else {
                    check[cellValue - 1] = 1;
                }
            }
        }
        return true;
    }

    private boolean checkRegion(int index) {
        int[] check = new int[9];
        for (int i = 0; i < values.length; i++) {
            if (getRegion(i) == index) {
                int cellValue = getCellValue(i);
                if (cellValue != 0) {
                    if (check[cellValue - 1] == 1) {
                        return false;
                    } else {
                        check[cellValue - 1] = 1;
                    }
                }
            }
        }
        return true;
    }

    ///////////////////////////////////////////////UTILITY FUNCTIONS////////////////////////////////////////////////////
    private void printDomain(int index) {
        ArrayList<Integer> domain = domains.get(index);
        System.out.print("Domain of " + index + ": ");
        for (int i = 0; i < domain.size(); i++) {
            System.out.print(domain.get(i));
        }
        System.out.println();
    }

    private void printAllDomains() {
        for (int i = 0; i < values.length; i++) {
            printDomain(i);
        }
    }

    private void printArray(int[] array) {
        System.out.print("Array:");
        for (int a : array) {
            System.out.print(" " + a);
        }
        System.out.println();
    }

    private ArrayList<Integer> getNewDomain() {
        ArrayList<Integer> done = new ArrayList<>();
        done.add(new Integer(1));
        done.add(new Integer(2));
        done.add(new Integer(3));
        done.add(new Integer(4));
        done.add(new Integer(5));
        done.add(new Integer(6));
        done.add(new Integer(7));
        done.add(new Integer(8));
        done.add(new Integer(9));
        return done;
    }
}
