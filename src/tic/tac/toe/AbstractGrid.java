/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

import java.util.ArrayList;

/**
 *
 */
public class AbstractGrid extends Object implements Grid<Object> {
    
    public AbstractGrid () {
        
    }

    @Override
    public int getNumberRows() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getNumberCols() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isValid(Location lctn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object put(Location lctn, Object e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object remove(Location lctn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Location> getEmptyAdjacentLocations(Location lctn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Object> getNeighbors(Location lctn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Location> getOccupiedAdjacentLocations(Location lctn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Location> getValidAdjacentLocations(Location lctn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Location> getOccupiedLocations() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    
    
    @Override
    public Object get(Location lctn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
}
