/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Simulation;

import TissueModel.BiologicalSlab;
import java.util.ArrayList;

public class InParams {

    private String out_fname;
    private String out_fformat;
    private long num_photons;
    private static double Wth=1E-4;
    private int nz;
    private int nr;
    private int na;
    private double dz;
    private double dr;
    double da;
    private short num_layers;         
    private BiologicalSlab slab;
    
    /*variables to allow a rectagular grid definition*/
    private double dx1;
    private double dy1;
    private double dz1;
    
    private int nx1;
    private int ny1;
    private int nz1;
    private ArrayList sourcesPositions;
    private ArrayList detectorsPositions;

    public InParams() {
    }

    public void read()
    {
        /*Durante la lectura de parámetros se comprueba la validez de
         * los valores definidos
         */
        if(nz<=0 || nr<=0 || na<=0 || num_layers<=0)
            System.err.println("Wrong grid parameters.\n");
    }

    public double getWth() {
        return Wth;
    }

    public void setWth(double Wth) {
        this.Wth = Wth;
    }

    public double getDa() {
        return da;
    }

    public void setDa(double da) {
        this.da = da;
    }

    public double getDr() {
        return dr;
    }

    public void setDr(double dr) {
        this.dr = dr;
    }

    public double getDz() {
        return dz;
    }

    public void setDz(double dz) {
        this.dz = dz;
    }
    
    

    public BiologicalSlab getBiologicalSlab() {
        return slab;
    }

    public void setLayers(BiologicalSlab slab) {
        this.slab = slab;
        this.num_layers=(short)this.slab.getLayers().size();
    }

    public int getNa() {
        return na;
    }

    public void setNa(int na) {
        this.na = na;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public short getNum_layers() {   
        this.num_layers=(short)this.slab.getLayers().size();
        return this.num_layers;
    }

//    public void setNum_layers(short num_layers) {
//        this.num_layers = num_layers;
//    }

    public long getNum_photons() {
        return num_photons;
    }

    public void setNum_photons(long num_photons) {
        this.num_photons = num_photons;
    }

    public int getNz() {
        return nz;
    }

    public void setNz(int nz) {
        this.nz = nz;
    }

    public String getOut_fformat() {
        return out_fformat;
    }

    public void setOut_fformat(String out_fformat) {
        this.out_fformat = out_fformat;
    }

    public String getOut_fname() {
        return out_fname;
    }

    public void setOut_fname(String out_fname) {
        this.out_fname = out_fname;
    }

    @Override
    public String toString()
    {
        String ret="out file name: "+this.out_fname+"\n"+                
                "num photons: "+this.num_photons+"\n"+
                "dx: "+this.dx1+"\n"+
                "dy: "+this.dy1+"\n"+
                "dz: "+this.dz1+"\n"+
                "nx: "+this.nx1+"\n"+
                "ny: "+this.ny1+"\n"+
                "nz: "+this.nz1+"\n"+
                "num layers: "+getNum_layers()+"\n";
        return ret;
    }

    public double getDx1() {
        return dx1;
    }

    public void setDx1(double dx1) {
        this.dx1 = dx1;
    }

    public double getDy1() {
        return dy1;
    }

    public void setDy1(double dy1) {
        this.dy1 = dy1;
    }

    public double getDz1() {
        return dz1;
    }

    public void setDz1(double dz1) {
        this.dz1 = dz1;
    }

    public int getNx1() {
        return nx1;
    }

    public void setNx1(int nx1) {
        this.nx1 = nx1;
    }

    public int getNy1() {
        return ny1;
    }

    public void setNy1(int ny1) {
        this.ny1 = ny1;
    }

    public int getNz1() {
        return nz1;
    }

    public void setNz1(int nz1) {
        this.nz1 = nz1;
    }


    public void setSourcesPositions(ArrayList sourcesPositions) {
        this.sourcesPositions=sourcesPositions;
    }

    public ArrayList getSourcesPositions() {
        return sourcesPositions;
    }

    public void setDetectorsPositions(ArrayList detectorsPositions) {
        this.detectorsPositions=detectorsPositions;
    }

    public ArrayList getDetectorsPositions() {
        return detectorsPositions;
    }
    
}
