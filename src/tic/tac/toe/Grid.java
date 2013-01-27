/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

import java.util.ArrayList;

/**
 *
 */
public interface Grid <E extends Object>{
   
    public int getNumberRows();

    public int getNumberCols();

    public boolean isValid(Location lctn);

    public E put(Location lctn, E e);

    public E remove(Location lctn);

    public E get(Location lctn);

    public ArrayList<Location> getOccupiedLocations();

    public ArrayList<Location> getValidAdjacentLocations(Location lctn);

    public ArrayList<Location> getEmptyAdjacentLocations(Location lctn);

    public ArrayList<Location> getOccupiedAdjacentLocations(Location lctn);

    public ArrayList<E> getNeighbors(Location lctn);
}
