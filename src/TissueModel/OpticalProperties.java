package TissueModel;

import java.util.Vector;

/**
 * A basic set of optical properties at a given location. This location may
 * actually be a single point within a layer, or refer to a whole layer at once.
 *
 * The basic optical properties are: + Thickness of the location expressed in
 * [cm]. Note that the thickness may actually be meaningless if the optical
 * properties refer to a single point within a tissue or layer. + Refractive
 * index which is dimensionless [-] + Specific absorption coefficient + Specific
 * scattering coefficient + Anisotropy function
 */
public class OpticalProperties {

    private double refractiveIndex;
    private double absorptionCoefficient;
    private double scatteringCoefficient;
    private double anisotropyFactor;    

    public OpticalProperties() {
    }

    /**
     * Class constructor where
     *
     * @param n -Refractive Index
     * @param mua -Absorption Coefficient
     * @param mus -Scattering Coefficient
     * @param g -Anisotropy Factor
     */
    public OpticalProperties(double n, double mua, double mus, double g) {
        refractiveIndex = n;
        absorptionCoefficient = mua;
        scatteringCoefficient = mus;
        anisotropyFactor = g;
    }

    /**
     * Gets the refractive index n
     */
    public double getRefractiveIndex() {
        return this.refractiveIndex;
    }

    /**
     * Sets the refractive index n
     */
    public void setRefractiveIndex(double n) {
        refractiveIndex = n;
    }

    public double getAbsorptionCoefficient() {
        return absorptionCoefficient;
    }

    public void setAbsorptionCoefficient(double absorptionCoefficient) {
        this.absorptionCoefficient = absorptionCoefficient;
    }

    public double getScatteringCoefficient() {
        return scatteringCoefficient;
    }

    public void setScatteringCoefficient(double scatteringCoefficient) {
        this.scatteringCoefficient = scatteringCoefficient;
    }
    
    public double getAnisotropyFactor() {
        return anisotropyFactor;
    }

    public void setAnisotropyFactor(double anisotropyFactor) {
        this.anisotropyFactor = anisotropyFactor;
    }

    /**
     * Gets the specific absorption coefficient
     */
    // public MolecularSpecificAbsorptionCoefficient getAbsorptionCoefficient() {
    // return this.absorptionCoefficient;
    // }
    /**
     * Sets the specific absorption coefficient
     */
//  public void setAbsorptionCoefficient(MolecularSpecificAbsorptionCoefficient mua) {
//      absorptionCoefficient=mua;
//}

    /**
     * Gets the anisotropy function
     */
    public void getAnisotropyFunction() {
    }

    public void setAnisotropyFunction(AnisotropyFunction af) {
    }

    @Override
    public String toString() {
        return String.valueOf(refractiveIndex) + "," + String.valueOf(absorptionCoefficient) + "," + String.valueOf(scatteringCoefficient) + "," + String.valueOf(anisotropyFactor);
    }

    void set(double n, double mua, double mus, double g) {
        this.refractiveIndex = n;
        this.absorptionCoefficient = mua;
        this.scatteringCoefficient = mus;
        this.anisotropyFactor = g;
    }

   
    
    
}