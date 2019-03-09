package TissueModel;

import Utility.Point3D;
import java.util.Vector;
//import Utility.Double;

/**
 * A basic layer (often composed of a single type of cells) which for purposes
 * of radiation transport simulation is homogeneous in its optical properties
 * all throughout.
 *
 * Thickness of the layer is expressed in [cm].
 */
public class HomogeneousLayer_old extends Layer {
        
    LayerPropertiesGrid lpg;
    /**
     * Class constructor
     */
    public void HomogeneousLayer(int nx,int ny, int nz, double dx, double dy, double dz) {
        lpg = new LayerPropertiesGrid(nx,ny,nz,dx,dy,dz);
    }    

    @Override
    public double getRefractiveIndex(Point3D l) {
       return this.lpg.getPoint(l).getRefractiveIndex();
    }

 
    public double getAnisotropy(Point3D l) {
        return this.lpg.getPoint(l).getAnisotropyFactor();
    }

    @Override
    public void setRefractiveIndex(Point3D l, Double n) {
        this.lpg.getPoint(l).setRefractiveIndex(n);
    }

//    @Override
//    public void setAbsorption(Point3D l, BiologicalSliceMacroscopicAbsorptionCoefficient mua) {
//         throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public void setScattering(Point3D l, BiologicalSliceMacroscopicScatteringCoefficient mus) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

//    @Override
//    public void setAnisotropy(Point3D l, AnisotropyFunction af) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Integer write2XML(String filename) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BiologicalSliceMacroscopicAbsorptionCoefficient getAbsorption(Point3D l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BiologicalSliceMacroscopicScatteringCoefficient getScattering(Point3D l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getThickness() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setThickness(double d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getMua() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getMus() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getZ0() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setZ0(double z0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getZ1() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setZ1(double z1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public short getIdx() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setIdx(short idx) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setAbsorption(Point3D l, double mua) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setScattering(Point3D l, double mus) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setAnisotropy(Point3D l, double af) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}