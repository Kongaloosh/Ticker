/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

/**
 *
 * @author Alexandr
 */
public class UnboundedGrid {
    
    int rows;
    int columns;
    
    public UnboundedGrid (int r, int c){
        rows = r;
        columns = c;
    }
   
    public int getNumberRows (){
        return  rows;
    }
    
    public int getNumberColumns(){
        return columns;
    }
    
    public boolean isValid (Location loc){
        if (
                getNumberColumns() >= loc.getColumn() &&
                getNumberRows()>= getNumberRows() &&
                loc.getColumn() >= 0 &&
                loc.getRow() >=0 ) {
           
            return true;
        
        }else{
        
            return false;
        }
    }

    
}
