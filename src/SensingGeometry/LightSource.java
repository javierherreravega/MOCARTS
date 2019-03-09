/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SensingGeometry;

import Utility.Point3D;

public class LightSource {
    private Point3D position;
    private long nphotons;
    private int sId;

    public LightSource(Point3D position, long nphotons, int sId) {
        this.position = position;
        this.nphotons = nphotons;
        this.sId=sId;
        position=new Point3D(0,0,0);
    }

    public LightSource(int id) {
        this.sId=id;
        position=new Point3D(0,0,0); //Default position
    }

    public long getNphotons() {
        return nphotons;
    }

    public void setNphotons(long nphotons) {
        this.nphotons = nphotons;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

}
