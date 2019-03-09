package Utility;

import java.util.Vector;

/** 
 *  A 3 dimensional point that is represented by double precision x,y,z coordinates.
 */
public class Point3D {

  public double x;

  public double y;

  public double z;
  
  public Object data;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean equal(Point3D p)
    {        
        return this.x==p.x && this.y==p.y && this.z==p.z;
    }
}