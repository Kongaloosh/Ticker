/*
 * Alex Kearney 27 / 1 / 13
 */
package tic.tac.toe;

public class Location {

    private int row;
    private int column;
    
    public Location (int r, int c){
        row = r;
        column = c;
        
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
    
// Re-defined compareTo and equals to prevent mis-use     

    public boolean compareTo (Location other) {
        if (other.getRow() == row && other.getColumn() == column) {
            return true;
        }else{
            return false;
        }   
    }
    
    public boolean equals (Location other){
        return compareTo(other);
    }
}
