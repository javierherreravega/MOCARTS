/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * We should keep the array type of the output variables since this struct
 * (array) is faster than List objects (however I need to investigate for a
 * faster access struct, if any, to improve the performance)
 *
 */
public class OutParams {

    InParams inparams;
    double Rsp;         /*
     * specular reflectance. [-]
     */

    double[][] Rd_ra;	/*
     * 2D distribution of diffuse reflectance. [1/(cm2 sr)]
     */

    double[] Rd_r;      /*
     * 1D radial distribution of diffuse reflectance. [1/cm2]
     */

    double[] Rd_a;	/*
     * 1D angular distribution of diffuse reflectance. [1/sr]
     */

    double Rd;		/*
     * total diffuse reflectance. [-]
     */

    double[][] A_rz;    /*
     * 2D probability density in turbid
     */
    /*
     * 3D probability density in turbid media over r & z. [1/cm3]
     */

    double[] A_z;	/*
     * 1D probability density over z.[1/cm]
     */

    double[] A_l;	/*
     * each layer's absorption probability. [-]
     */

    double A;		/*
     * total absorption probability. [-]
     */

    double[][] Tt_ra;	/*
     * 2D distribution of total transmittance. [1/(cm2 sr)]
     */

    double[] Tt_r;	/*
     * 1D radial distribution of transmittance. [1/cm2]
     */

    double[] Tt_a;	/*
     * 1D angular distribution of transmittance. [1/sr]
     */

    double Tt;		/*
     * total transmittance. [-]
     */

    //double[][][] Rd_xya; /*Matrix to store the diffuse reflectance in the xy plane*/
    ArrayList[][] Cell_Rd_xy;
    ArrayList[][][] Cell_Rd_xya;
    ArrayList[][][] Cell_Tt_xya;
    //double[][][] A_xyz; /*Matrix to store the absorbance in the space xyz*/
    ArrayList[][][] Cell_A_xyz; /*
     * Matrix to store the energy of every photon from every light source
     */

    double[][] Tt_xy; /*
     * Matrix to store the transmitance in the xy plane
     */


    public OutParams(InParams inparams) {
        this.inparams = inparams;
        this.Rsp = 0.0;
        this.Rd = 0.0;
        this.A = 0.0;
        this.Tt = 0.0;
        initArrays();
    }

    /*
     * Instantiate the output variables with the corresponding size. As defined
     * by the members of the InParams object.
     */
    private void initArrays() {

        this.Rd_ra = new double[inparams.getNr()][inparams.getNa()];
        this.Rd_r = new double[inparams.getNr()];
        this.Rd_a = new double[inparams.getNa()];
        this.A_rz = new double[inparams.getNr()][inparams.getNz()];
        this.A_z = new double[inparams.getNz()];
        this.A_l = new double[inparams.getNum_layers() + 1];
        this.Tt_ra = new double[inparams.getNr()][inparams.getNa()];
        this.Tt_r = new double[inparams.getNr()];
        this.Tt_a = new double[inparams.getNa()];

        //this.Rd_xya = new double[inparams.getNx1()][inparams.getNy1()][inparams.getNa()];
        this.Cell_Rd_xy = new ArrayList[inparams.getNx1()][inparams.getNy1()];
        this.Cell_Rd_xya = new ArrayList[inparams.getNx1()][inparams.getNy1()][inparams.getNa()];
        this.Cell_Tt_xya = new ArrayList[inparams.getNx1()][inparams.getNy1()][inparams.getNa()];
        //this.A_xyz = new double[inparams.getNx1()][inparams.getNy1()][inparams.getNz1()];
        this.Cell_A_xyz = new ArrayList[inparams.getNx1()][inparams.getNy1()][inparams.getNz1()];
        this.Tt_xy = new double[inparams.getNx1()][inparams.getNy1()];
    }

    /*
     * Return the total absorption [-] in the tissue model
     */
    public double getA() {
        return A;
    }

    /**
     * Set the total Absorption for the output parameters
     */
    public void setA(double A) {
        this.A = A;
    }

    /**
     * Return an array with the absorption for every layer
     *
     * @return A_l
     */
    public double[] getA_l() {
        return A_l;
    }

    /*
     * Set the absorption computed for each layer to the output parameters.
     */
    public void setA_l(double[] A_l) {
        this.A_l = A_l;
    }

    public double[][] getA_rz() {
        return A_rz;
    }

    public void setA_rz(double[][] A_rz) {
        this.A_rz = A_rz;
    }

    public double getA_rzValue(int ir, int iz) {
        return A_rz[ir][iz];
    }

    public void setA_rzValue(int ir, int iz, double val) {
        this.A_rz[ir][iz] += val;
        //System.out.format("%d, %d, ,%5.12f\n",ir,iz,this.A_rz[ir][iz]);
    }

    public void setA_xyzValue(int sId, int ix, int iy, int iz, double val) {
        double value = val;

        if (this.Cell_A_xyz[ix][iy][iz] == null) {
            this.Cell_A_xyz[ix][iy][iz] = new ArrayList<Cell>();
            this.Cell_A_xyz[ix][iy][iz].add(new Cell((byte) sId, value));
        } else {
            boolean found = false;
            for (int i = 0; i < this.Cell_A_xyz[ix][iy][iz].size(); i++) {
                if (((Cell) this.Cell_A_xyz[ix][iy][iz].get(i)).getsId() == sId) {
                    ((Cell) this.Cell_A_xyz[ix][iy][iz].get(i)).addValue(val);
                    found = true;
                    break;
                }
            }
            if (!found) {
                this.Cell_A_xyz[ix][iy][iz].add(new Cell((byte) sId, value));
            }

        }
    }

    public void setRd_xyaValue(int sId, int ix, int iy, int ia, double val) {
        //this.Rd_xya[ix][iy][ia]+=val;
        double value = val;

        if (this.Cell_Rd_xya[ix][iy][ia] == null) {
            this.Cell_Rd_xya[ix][iy][ia] = new ArrayList<Cell>();
            this.Cell_Rd_xya[ix][iy][ia].add(new Cell((byte) sId, value));
        } else {
            boolean found = false;
            for (int i = 0; i < this.Cell_Rd_xya[ix][iy][ia].size(); i++) {
                if (((Cell) this.Cell_Rd_xya[ix][iy][ia].get(i)).getsId() == sId) {
                    ((Cell) this.Cell_Rd_xya[ix][iy][ia].get(i)).addValue(val);
                    found = true;
                    break;

                }
            }
            if (!found) {
                this.Cell_Rd_xya[ix][iy][ia].add(new Cell((byte) sId, value));
            }

        }
    }

    public void setTt_xyaValue(int sId, int ix, int iy, int ia, double val) {
        //this.Rd_xya[ix][iy][ia]+=val;
        double value = val;

        if (this.Cell_Tt_xya[ix][iy][ia] == null) {
            this.Cell_Tt_xya[ix][iy][ia] = new ArrayList<Cell>();
            this.Cell_Tt_xya[ix][iy][ia].add(new Cell((byte) sId, value));
        } else {
            boolean found = false;
            for (int i = 0; i < this.Cell_Tt_xya[ix][iy][ia].size(); i++) {
                if (((Cell) this.Cell_Tt_xya[ix][iy][ia].get(i)).getsId() == sId) {
                    ((Cell) this.Cell_Tt_xya[ix][iy][ia].get(i)).addValue(val);
                    found = true;
                    break;

                }
            }
            if (!found) {
                this.Cell_Tt_xya[ix][iy][ia].add(new Cell((byte) sId, value));
            }

        }
    }

    public void set_Rd_xy(byte sId) {
        double sum = 0;
        for (int ix = 0; ix < this.Cell_Rd_xya.length; ix++) {
            for (int iy = 0; iy < this.Cell_Rd_xya[ix].length; iy++) {
                sum = 0;
                for (int ia = 0; ia < this.Cell_Rd_xya[ix][iy].length; ia++) {
                    if (this.Cell_Rd_xya[ix][iy][ia] != null) {
                        for (int i = 0; i < this.Cell_Rd_xya[ix][iy][ia].size(); i++) {
                            if (((Cell) this.Cell_Rd_xya[ix][iy][ia].get(i)).getsId() == sId) {
                                sum += ((Cell) this.Cell_Rd_xya[ix][iy][ia].get(i)).getValue();
                                break;
                            }
                        }
                        this.Cell_Rd_xy[ix][iy] = new ArrayList<Cell>();
                        this.Cell_Rd_xy[ix][iy].add(new Cell((byte) sId, sum));
                    }
                }
            }
        }
    }

    public void setTt_xyValue(int ix, int iy, double val) {
        this.Tt_xy[ix][iy] += val;
    }

    public double[] getA_z() {
        return A_z;
    }

    public void setA_z(double[] A_z) {
        this.A_z = A_z;
    }

    public double getRd() {
        return Rd;
    }

    public void setRd(double Rd) {
        this.Rd = Rd;
    }

    public double[] getRd_a() {
        return Rd_a;
    }

    public void setRd_a(double[] Rd_a) {
        this.Rd_a = Rd_a;
    }

    public double[] getRd_r() {
        return Rd_r;
    }

    public void setRd_r(double[] Rd_r) {
        this.Rd_r = Rd_r;
    }

    public double[][] getRd_ra() {
        return Rd_ra;
    }

    public void setRd_ra(double[][] Rd_ra) {
        this.Rd_ra = Rd_ra;
    }

    public void setRd_raValue(int ir, int ia, double val) {
        this.Rd_ra[ir][ia] += val;
    }

    public double getRd_raValue(int ir, int ia) {
        return this.Rd_ra[ir][ia];
    }

    public double getRsp() {
        return Rsp;
    }

    public void setRsp(double Rsp) {
        this.Rsp = Rsp;
    }

    public double getTt() {
        return Tt;
    }

    public void setTt(double Tt) {
        this.Tt = Tt;
    }

    public double[] getTt_a() {
        return Tt_a;
    }

    public void setTt_a(double[] Tt_a) {
        this.Tt_a = Tt_a;
    }

    public double[] getTt_r() {
        return Tt_r;
    }

    public void setTt_r(double[] Tt_r) {
        this.Tt_r = Tt_r;
    }

    public double[][] getTt_ra() {
        return Tt_ra;
    }

    public void setTt_ra(double[][] Tt_ra) {
        this.Tt_ra = Tt_ra;
    }

    public void setTt_raValue(int ir, int ia, double val) {
        this.Tt_ra[ir][ia] += val;
    }

    public double getTt_raValue(int ir, int ia) {
        return this.Tt_ra[ir][ia];
    }

    public InParams getInparams() {
        return inparams;
    }

    public void setInparams(InParams inparams) {
        this.inparams = inparams;
    }

    public void printRdXY(byte sId) {
        this.set_Rd_xy(sId);

        for (int ix = 0; ix < this.Cell_Rd_xy.length; ix++) {
            for (int iy = 0; iy < this.Cell_Rd_xy[ix].length; iy++) {
                if (this.Cell_Rd_xy[ix][iy] != null) {
                    for (int i = 0; i < this.Cell_Rd_xy[ix][iy].size(); i++) {
                        if (((Cell) this.Cell_Rd_xy[ix][iy].get(i)).getsId() == sId) {
                            System.out.print(((Cell) this.Cell_Rd_xy[ix][iy].get(i)).getValue() + " ");
                            break;
                        }
                    }
                } else {
                    System.out.print(0 + " ");
                }
            }
            System.out.println();
        }
    }

    public void printAbsorptionTotalPlaneXY(int z) {
        PrintStream output = null;
        try {
            output = new PrintStream(new File("AbsorptionTotalPlaneXY.csv"));
            for (int y = 0; y < this.inparams.getNy1(); y++) {
                for (int x = 0; x < this.inparams.getNx1(); x++) {
                    //System.out.print(A_xyz[x][y][z] + " ");
                    if (this.Cell_A_xyz[x][y][z] != null) {
                        double At = 0;
                        Iterator it = Cell_A_xyz[x][y][z].iterator();
                        while (it.hasNext()) {
                            Cell temp = (Cell) it.next();
                            At += temp.getValue();
                        }
                        //System.out.print(At + " ");
                        if (x != this.inparams.getNx1() - 1) {
                            output.print(At + ",");
                        } else {
                            output.print(At);
                        }
                    } else {
                        //System.out.print(0 + " ");
                        if (x != this.inparams.getNx1() - 1) {
                            output.print(0 + ",");
                        } else {
                            output.print(0);
                        }
                    }
                }
                //System.out.println();
                output.println();
            }
            output.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OutParams.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            output.close();
        }
    }

    public void printAbsorptionTotalPlaneZX(int y) {
        PrintStream output = null;
        try {
            output = new PrintStream(new File("AbsorptionTotalPlaneZX.csv"));
            for (int z = 0; z < this.inparams.getNz1(); z++) {
                for (int x = 0; x < this.inparams.getNx1(); x++) {
                    //System.out.print(A_xyz[x][y][z] + " ");
                    if (this.Cell_A_xyz[x][y][z] != null) {
                        double At = 0;
                        Iterator it = Cell_A_xyz[x][y][z].iterator();
                        while (it.hasNext()) {
                            Cell temp = (Cell) it.next();
                            At += temp.getValue();
                        }
                        if (x != this.inparams.getNx1() - 1) {
                            output.print(At + ",");
                        } else {
                            output.print(At);
                        }

                    } else {
                        if (x != this.inparams.getNx1() - 1) {
                            output.print(0 + ",");
                        } else {
                            output.print(0);
                        }
                    }
                }
                output.println();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OutParams.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            output.close();
        }
    }

public void printAbsorptionTotalPlaneZX(int y, PrintStream output) {
            for (int z = 0; z < this.inparams.getNz1(); z++) {
                for (int x = 0; x < this.inparams.getNx1(); x++) {                   
                    if (this.Cell_A_xyz[x][y][z] != null) {
                        double At = 0;
                        Iterator it = Cell_A_xyz[x][y][z].iterator();
                        while (it.hasNext()) {
                            Cell temp = (Cell) it.next();
                            At += temp.getValue();
                        }
                        if (x != this.inparams.getNx1() - 1) {
                            output.print(At + " ");
                        } else {
                            output.print(At);
                        }

                    } else {
                        if (x != this.inparams.getNx1() - 1) {
                            output.print(0 + " ");
                        } else {
                            output.print(0);
                        }
                    }
                }
                output.println();
            }        
    }    
    
    public void printAbsorptionBySourcePlaneZX(int y, int sId) {
        PrintStream output = null;
        try {
            output = new PrintStream(new File("AbsorptionBySourcePlaneZX_s"+sId+".csv"));
            for (int z = 0; z < this.inparams.getNz1(); z++) {
                for (int x = 0; x < this.inparams.getNx1(); x++) {
                    //System.out.print(A_xyz[x][y][z] + " ");
                    if (this.Cell_A_xyz[x][y][z] != null) {
                        double At = 0;
                        Iterator it = Cell_A_xyz[x][y][z].iterator();
                        while (it.hasNext()) {
                            Cell temp = (Cell) it.next();
                            if (temp.getsId() == sId) {
                                At += temp.getValue();
                                break;
                            }
                        }
                        if (x != this.inparams.getNx1() - 1) {
                            output.print(At + ",");
                        } else {
                            output.print(At);
                        }
                    } else {
                        if (x != this.inparams.getNx1() - 1) {
                            output.print(0 + ",");
                        } else {
                            output.print(0);
                        }
                    }
                }
                output.println();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OutParams.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            output.close();
        }
    }

    public ArrayList[][][] getCell_Rd_xya() {
        return this.Cell_Rd_xya;
    }

    public void printRd_ra(String fileName) {
        PrintStream output = null;
        try {
            //double scale1 = 4.0*Simulation.PI*Simulation.PI*0.01*Math.sin((0.5*Simulation.PI/90)/2)*0.01*100000;
            double scale1 = 4.0 * Simulation.PI * Simulation.PI * inparams.getDr() * Math.sin(inparams.getDa() / 2) * inparams.getDr() * inparams.getNum_photons();
            double scale2 = 0;
            output = new PrintStream(new File(fileName));
            for (int i = 0; i < this.Rd_ra.length; i++) {
                for (int j = 0; j < this.Rd_ra[0].length; j++) {
                    scale2 = 1.0 / ((i + 0.5) * Math.sin(2.0 * (j + 0.5) * inparams.getDa()) * scale1);
                    this.Rd_ra[i][j] *= scale2;
                    
                    if (j != this.inparams.getNa() - 1) {
                            output.format("%12.4E,",this.Rd_ra[i][j] );                            
                            
                        } else {
                            output.format("%12.4E",this.Rd_ra[i][j]);
                        }
                    
                    //System.out.print(this.Rd_ra[i][j]+", ");
                    //System.out.format("%12.4E ", this.Rd_ra[i][j]);
                }
               output.println();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OutParams.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            output.close();
        }
    }

    public void printTt_ra(String fileName) {
        try {
            double scale1 = 4.0 * Simulation.PI * Simulation.PI * inparams.getDr() * Math.sin(inparams.getDa() / 2) * inparams.getDr() * inparams.getNum_photons();
            double scale2 = 0;
            PrintStream output = new PrintStream(new File(fileName));
            for (int i = 0; i < this.Tt_ra.length; i++) {
                for (int j = 0; j < this.Tt_ra[0].length; j++) {
                    scale2 = 1.0 / ((i + 0.5) * Math.sin(2.0 * (j + 0.5) * inparams.getDa()) * scale1);
                    this.Tt_ra[i][j] *= scale2;
                    
                    if (j != this.inparams.getNa() - 1) {
                            output.format("%12.4E,",this.Tt_ra[i][j] );                            
                            
                        } else {
                            output.format("%12.4E",this.Tt_ra[i][j]);
                        }
                    
                    //System.out.print(this.Tt_ra[i][j]+", ");
                    //System.out.format("%12.4E ", this.Tt_ra[i][j]);
                }
                output.println();
                //System.out.println();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OutParams.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printA_rz(String fileName) {        
        PrintStream output = null;
        try {
            double scale1 = 2.0 * Simulation.PI * 0.01 * 0.01 * 0.01 * this.inparams.getNum_photons();
            output = new PrintStream(new File(fileName));
            for (int i = 0; i < this.A_rz.length; i++) {
                for (int j = 0; j < this.A_rz[0].length; j++) {
                    this.A_rz[i][j] /= (i + 0.5) * scale1;
                    if (j != this.inparams.getNr() - 1) {
                            output.format("%12.4E,",this.A_rz[i][j] );                            
                            
                        } else {
                            output.format("%12.4E",this.A_rz[i][j]);
                        }                    
                    //System.out.format("%12.4E ", this.A_rz[i][j]);
                    //System.out.print(this.A_rz[i][j]+" ");
                    //System.out.print(this.A_rz[i][j]+" ");
                }
                //System.out.println();
                output.println();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OutParams.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            output.close();
        }
    }

    public ArrayList[][][] getCell_A_xyz() {
        return Cell_A_xyz;
    }

    public ArrayList[][][] getCell_Tt_xya() {
        return Cell_Tt_xya;
    }

    

    
}
