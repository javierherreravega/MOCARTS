/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation;

import Utility.Point3D;
import java.util.ArrayList;

public class Grid {
    
    private ArrayList<Point3D> dataStruct;
    
    public Grid()
    {
        dataStruct=new ArrayList<Point3D>();
    }
    
    /*
     * Verificar que si ya existe el punto en el arreglo entonces sólo se cambia el valor de Point3D.data
     * (la absorción acumulada en dicha posición)
     */
    public void add(Point3D p)
    {
        this.dataStruct.add(p);
    }
          
}
