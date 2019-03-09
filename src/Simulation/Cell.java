/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation;



public class Cell {
    
    private byte sId;
    private double val;
    
    public Cell(byte sId, double val)
    {
        this.sId=sId;
        this.val=val;
    }
    
    public void addValue(double val)
    {        
        this.val+=val;
    }
    
    public double getValue()
    {
            return this.val;
    }
    
    public byte getsId(){
        return this.sId;
    }
}
