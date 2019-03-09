/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TissueModel;

import Utility.Point3D;

public class LayerPropertiesGrid {

    int nx;
    int ny;
    int nz;
    double deltax;
    double deltay;
    double deltaz;
    OpticalProperties[][][] grid;

    /**
     * Class constructor Creates a grid to define the optical properties for
     * each cell in the matrix. The parameters:
     * @param nx -Number of cells in x-direction
     * @param ny -Number of cells in y-direction
     * @param nz -Number of cells in z-direction
     * @param deltax -Defines the separation between points in x-axes
     * @param deltay -Defines the separation between points in y-axes
     * @param deltaz -Defines the separation between points in z-axes
     */
    public LayerPropertiesGrid(int nx, int ny, int nz, double deltax, double deltay, double deltaz) {
        this.nx = nx;
        this.ny = ny;
        this.nz = nz;
        this.deltax = deltax;
        this.deltay = deltay;
        this.deltaz = deltaz;
        grid = new OpticalProperties[nx][ny][nz];
    }

    public void setPoint(Point3D p, OpticalProperties op) {
        int idxX = (int) (p.getX() / deltax);
        int idxY = (int) (p.getY() / deltay);
        int idxZ = (int) (p.getZ() / deltaz);
        grid[idxX][idxY][idxZ] = op;
    }

    public OpticalProperties getPoint(Point3D p) {
        int idxX = (int) (p.getX() / deltax);
        int idxY = (int) (p.getY() / deltay);
        int idxZ = (int) (p.getZ() / deltaz);
        return grid[idxX][idxY][idxZ];
    }

    public void printSliceXY(int depthZ) {
        for (int x = 0; x < this.nx; x++) {
            for (int y = 0; y < this.ny; y++) {
                if (grid[x][y][depthZ] != null) {
                    System.out.print(grid[x][y][depthZ].toString() + "\t");
                } else {
                    System.out.print("null" + "\t");
                }
            }
            System.out.println();
        }
    }

    public double getDeltax() {
        return deltax;
    }

    public void setDeltax(double deltax) {
        this.deltax = deltax;
    }

    public double getDeltay() {
        return deltay;
    }

    public void setDeltay(double deltay) {
        this.deltay = deltay;
    }

    public double getDeltaz() {
        return deltaz;
    }

    public void setDeltaz(double deltaz) {
        this.deltaz = deltaz;
    }

    public int getNx() {
        return nx;
    }

    public void setNx(int nx) {
        this.nx = nx;
    }

    public int getNy() {
        return ny;
    }

    public void setNy(int ny) {
        this.ny = ny;
    }

    public int getNz() {
        return nz;
    }

    public void setNz(int nz) {
        this.nz = nz;
    }

    /*
     * Method to test this class
     */
    public static void main(String[] args) {
        LayerPropertiesGrid lpg = new LayerPropertiesGrid(5, 4, 3, 0.2, 0.2, 0.2);
        lpg.printSliceXY(0);
        System.out.println();
        lpg.printSliceXY(1);
        System.out.println();
        lpg.printSliceXY(2);

        lpg.setPoint(new Point3D(0, 0, 0), new OpticalProperties(1.2, 0.1, 100, 0.9));
        lpg.setPoint(new Point3D(0, 0, 0.4), new OpticalProperties(1.8, 0.2, 200, 0.8));

        for (int it = 0; it < lpg.getNz(); it++) {
            System.out.println();
            System.out.println("Plano XY , Z=" + (it * lpg.getDeltaz()));
            lpg.printSliceXY(it);
        }

    }
}
