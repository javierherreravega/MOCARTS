/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation;

import TissueModel.BiologicalSlice;
import TissueModel.Layer;
import Utility.Point3D;
import Utility.Wavelength;

public class Photon {
    
    private Point3D location;
    private double ux,uy,uz;
    private double weight;
    private Layer currLayer;
    private double s; //Stepsize actualizar de acuerdo al modelo
    private double remainingStep;
    private boolean dead;
    private Wavelength wlength;
  

    public Photon()
    {
    }
    
    public Photon(Wavelength wlength)
    {
        this.wlength=wlength;        
    }

    public Layer getCurrLayer() {
        return currLayer;
    }

    public void setCurrLayer(Layer currLayer) {
        this.currLayer = currLayer;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**IMPLEMENTAR*/
    public double getDirection() {
        return 0.0; /*ux,uy,uz*/
    }

    public double getUx() {
        return ux;
    }

    public void setUx(double ux) {
        this.ux = ux;
    }

    public double getUy() {
        return uy;
    }

    public void setUy(double uy) {
        this.uy = uy;
    }

    public double getUz() {
        return uz;
    }

    public void setUz(double uz) {
        this.uz = uz;
    }
    
 
    

    public void setDirection(double ux, double uy, double uz) {
        this.ux= ux;
        this.uy=uy;
        this.uz=uz;
    }

    public Point3D getLocation() {
        return location;
    }

    public void setLocation(Point3D location) {
        this.location = location;
    }

    public double getRemainingStep() {
        return remainingStep;
    }

    public void setRemainingStep(double remainingStep) {
        this.remainingStep = remainingStep;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Wavelength getWlength() {
        return wlength;
    }

    public void setWlength(Wavelength wlength) {
        this.wlength = wlength;
    }

    
}
