package TissueModel;

import Utility.Point3D;

/**
 * This class represents slabs of tissue. For simulations of radiation
 * transport, this is the basic building block, and they are to be simulated as
 * a single entity. In a sense it bounds the limit of interest of the user, who
 * is "not interested" in more detail for this region of the tissue.
 *
 * A bit more formally, a tissue layer is often a collection of cells with
 * similar histological properties. However, strictly a layer object is more
 * general than just a collection of morphologically similar neighbour cells, as
 * it may also represent heterogeneous layers with cells of different type.
 *
 * Importantly, a layer is described by its optical parameters at the different
 * locations. This includes: the tickness of the layer, the refraction index,
 * the absorption coefficient, the scattering coefficient and the anisotropy
 * function.
 *
 * The layer is flat a 3D structure with length (X-axes), width (Y-axes) and
 * depth (Z-axes). To locate any given point within a layer, each layer refers
 * to points locally. The layer surface is at the X-Y plane. Larger z values
 * represent deeper locations. Location units are in [cm]
 */
public abstract class Layer extends BiologicalSlice {

    private String Name;

    /**
     * Class constructor
     */
    public Layer() {
       
    }

    /**
     * Gets the thickness of the layer.
     */
    public abstract double getThickness();

    public abstract void setThickness(double d);

    /**
     * Sets values of the optical properties of the layer
     */
    public abstract double getRefractiveIndex(Point3D l);

    /**
     * Calculates the absorption at a certain location within the layer.
     */
    public abstract BiologicalSliceMacroscopicAbsorptionCoefficient getAbsorption(Point3D l);

    public abstract BiologicalSliceMacroscopicScatteringCoefficient getScattering(Point3D l);

    public abstract double getAnisotropy(Point3D l);

    public abstract void setRefractiveIndex(Point3D l, Double n);

    //public abstract void setAbsorption(Point3D l, BiologicalSliceMacroscopicAbsorptionCoefficient mua);
    public abstract void setAbsorption(Point3D l, double mua);

    //public abstract void setScattering(Point3D l, BiologicalSliceMacroscopicScatteringCoefficient mus);
    
    public abstract void setScattering(Point3D l, double mus);
    
    //public abstract void setAnisotropy(Point3D l, AnisotropyFunction af);
    public abstract void setAnisotropy(Point3D l, double af);
    
    public abstract String toString();

    @Override
    public void setName(String name) {
        this.Name = name;
    }

    @Override
    public String getName() {
        return Name;
    }

    public abstract double getMua();
    public abstract double getMus();
    public abstract double getZ0();
    public abstract void setZ0(double z0);
    public abstract double getZ1();
    public abstract void setZ1(double z1);

    public abstract short getIdx();
    public abstract void setIdx(short idx);
 
}