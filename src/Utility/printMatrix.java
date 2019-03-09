/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Simulation.Cell;

public class printMatrix {
 
    
    static public void print(double[][] mat ){
    
        for (int i=0; i<mat.length; i++){
            for(int j=0; j<mat[0].length;j++)
            {
                System.out.print(mat[i][j]+" ");
            }
            System.out.println();
        }
    }
    
}
    

