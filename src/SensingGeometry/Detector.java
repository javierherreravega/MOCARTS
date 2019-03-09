/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SensingGeometry;

import Simulation.Cell;
import Simulation.InParams;
import Utility.Point3D;
import java.util.ArrayList;

public class Detector {

    private Point3D position;
    private double radious;
    private int id;
    private int sourceId;

    public Detector(int id) {
        this.id = id;
        position = new Point3D(0, 0, 0); //Default position
    }

    public Detector(Point3D position, double radious, int id) {
        this.position = position;
        this.radious = radious;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setSourceId(int Sid)
    {
        this.sourceId=Sid;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public void setRadious(double radious) {
        this.radious = radious;
    }

    public double getRadious() {
        return radious;
    }

    /**
     * Computes the diffuse reflectance collected by this detector
     *
     * @param mat The matrix where the diffuse reflectance has been computed and
     * stored
     * @return The diffuse reflectance Rd sensed by this detector.
     */
    public double getRd(ArrayList[][][] mat, InParams inparams, int sId) {
        //Determinar los índices en la matriz mat correspondientes a los valores x,y 
        //de la posición definida del detector.

        int longX = inparams.getNx1();
        int longY = inparams.getNy1();
        short ix, iy, ia;
        double ixd, iyd, iad;

        iyd = this.getPosition().getY() / inparams.getDy1();
        iyd = iyd < 0 ? (short) ((longY / 2) - Math.abs(iyd)) : (short) ((longY / 2) + iyd);

        if (iyd > inparams.getNy1() - 1) {
            iy = (short) (inparams.getNy1() - 1);
        } else if (iyd < 0) {
            iy = 0;
        } else {
            iy = (short) iyd;
        }

        ixd = this.getPosition().getX() / inparams.getDx1();
        ixd = ixd < 0 ? (short) ((longX / 2) - Math.abs(ixd)) : (short) ((longX / 2) + ixd);
        if (ixd > inparams.getNx1() - 1) {
            ix = (short) (inparams.getNx1() - 1);
        } else if (ixd < 0) {
            ix = 0;
        } else {
            ix = (short) ixd;
        }

        double Rd = 0;
        int Nx = (int) (radious / inparams.getDx1());

        for (int i = ix - Nx; i <= ix + Nx; i++) {
            for (int j = iy - Nx; j <= iy + Nx; j++) {
                //System.out.print("("+i+","+j+"): ");
                //Suma la reflectancia den todos los ángulos de salida de fotones
                for (int a = 0; a < mat[ix][iy].length; a++) {

                    if (mat[i][j][a]!=null){
                    for (int k = 0; k < mat[i][j][a].size(); k++) {
                        if (((Cell) mat[i][j][a].get(k)).getsId() == sId) {
                            //System.out.println(((Cell) mat[i][j][a].get(k)).getValue());
                            Rd += ((Cell) mat[i][j][a].get(k)).getValue();
                            break;
                        }
                    }
                    }
                }
            }
        }
        return Rd;
    }
    
    public double getRdAllSources(ArrayList[][][] mat, InParams inparams) {
        //Determinar los índices en la matriz mat correspondientes a los valores x,y 
        //de la posición definida del detector.

        int longX = inparams.getNx1();
        int longY = inparams.getNy1();
        short ix, iy, ia;
        double ixd, iyd, iad;

        iyd = this.getPosition().getY() / inparams.getDy1();
        iyd = iyd < 0 ? (short) ((longY / 2) - Math.abs(iyd)) : (short) ((longY / 2) + iyd);

        if (iyd > inparams.getNy1() - 1) {
            iy = (short) (inparams.getNy1() - 1);
        } else if (iyd < 0) {
            iy = 0;
        } else {
            iy = (short) iyd;
        }

        ixd = this.getPosition().getX() / inparams.getDx1();
        ixd = ixd < 0 ? (short) ((longX / 2) - Math.abs(ixd)) : (short) ((longX / 2) + ixd);
        if (ixd > inparams.getNx1() - 1) {
            ix = (short) (inparams.getNx1() - 1);
        } else if (ixd < 0) {
            ix = 0;
        } else {
            ix = (short) ixd;
        }

        double Rd = 0;
        int Nx = (int) (radious / inparams.getDx1());

        for (int i = ix - Nx; i <= ix + Nx; i++) {
            for (int j = iy - Nx; j <= iy + Nx; j++) {
                //System.out.print("("+i+","+j+"): ");
                //Suma la reflectancia en todos los ángulos de salida de fotones
                for (int a = 0; a < mat[ix][iy].length; a++) {

                    if (mat[i][j][a]!=null){
                    for (int k = 0; k < mat[i][j][a].size(); k++) {                        
                            //System.out.println(((Cell) mat[i][j][a].get(k)).getValue());
                            Rd += ((Cell) mat[i][j][a].get(k)).getValue();
                            break;                        
                    }
                    }
                }
            }
        }
        return Rd;
    }

    public int getSourceId() {
        return sourceId;
    }
}
