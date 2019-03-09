package TissueModel;

import Utility.Point3D;
import java.util.Vector;
//import Utility.Double;

/**
 * A basic layer (often composed of a single type of cells) which for purposes
 * of radiation transport simulation is homogeneous in its optical properties
 * all throughout.
 *
 * Although the parameter for every inherited method is a point3D, for a 
 * homogeneous layer this is ignored because all the cells in the layer has 
 * the same optical properties, then, the methods return/set the same value for 
 * any given point.
 * 
 * Thickness of the layer is expressed in [cm].
 */
public class HomogeneousLayer extends Layer {
    
    double thickness;
    OpticalProperties theOpticalProperties;
    double z0, z1;  /* z coordinates of a layer. [cm] */
    public double cos_crit0,	cos_crit1;	
    private short idx;
    
    /**
     * Class constructor
     */
    
    public HomogeneousLayer(String name)
    {
        this.setName(name);
        theOpticalProperties=new OpticalProperties();
//        z0=layer==null?0:layer.getZ1();
//        z1=layer==null?d:z0+d;
    }
    
    public HomogeneousLayer(String name,double n, double mua, double mus, double g, double d) {
        this.setName(name);        
        theOpticalProperties=new OpticalProperties(n,mua,mus,g);
        this.thickness=d;
        //z0=layer==null?0:layer.getZ1();
        //z1=layer==null?d:z0+d;
    }    
    
        
    public OpticalProperties getOpticalProperties()
    {
        return this.theOpticalProperties;
    }
    
    public void setOpticalProperties(double n, double mua, double mus, double g)
    {
        this.theOpticalProperties.set(n,mua,mus,g);
    }
    
    @Override
    public double getRefractiveIndex(Point3D l) {       
       return this.theOpticalProperties.getRefractiveIndex();
    }
    
    @Override
    public void setRefractiveIndex(Point3D l, Double n) {
        this.theOpticalProperties.setRefractiveIndex(n);
    }
    
    @Override
    public double getAnisotropy(Point3D l) {
        return this.theOpticalProperties.getAnisotropyFactor();
    }

    @Override
    public void setAnisotropy(Point3D l, double af) {
        this.theOpticalProperties.setAnisotropyFactor(af);
    }

    @Override
    public void setAbsorption(Point3D l, double mua) {
         this.theOpticalProperties.setAbsorptionCoefficient(mua);
    }

    @Override
    public void setScattering(Point3D l, double mus) {
        this.theOpticalProperties.setScatteringCoefficient(mus);
    }
    

    @Override
    public String toString() {
        OpticalProperties op=this.getOpticalProperties();
        return "\t\t"+this.getName()+": "+op.toString()+", "+this.thickness+" \n";
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
        return this.thickness;
    }

    @Override
    public void setThickness(double d) {
        this.thickness=d;
    }
    
    /*This method should not be used. Insted use getAbsorption*/
    public double getMua() {
        return this.theOpticalProperties.getAbsorptionCoefficient();
    } 
    
    public double getMus() {
        return this.theOpticalProperties.getScatteringCoefficient();
    }

    public double getCos_crit0() {
        return cos_crit0;
    }

    public void setCos_crit0(double cos_crit0) {
        this.cos_crit0 = cos_crit0;
    }

    public double getCos_crit1() {
        return cos_crit1;
    }

    public void setCos_crit1(double cos_crit1) {
        this.cos_crit1 = cos_crit1;
    }

    public double getZ0() {
        return z0;
    }

    public double getZ1() {
        return z1;
    }

    @Override
    public void setZ0(double z0) {
        this.z0 = z0;
    }

    @Override
    public void setZ1(double z1) {
       this.z1 = z1;
    }

    @Override
    public short getIdx() {
        return this.idx;
    }

    @Override
    public void setIdx(short idx) {
        this.idx=idx;
    }
    
}