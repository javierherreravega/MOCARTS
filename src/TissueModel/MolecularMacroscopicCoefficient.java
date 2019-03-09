package TissueModel;

import java.util.ArrayList;
import Utility.*;

/** 
 *  A macroscopic extinction coefficient of a molecule within a medium. 
 * A macroscopic extinction coefficient is the product of the concentration 
 * of a molecule multiplied by the specific extinction coefficient of that 
 * molecule.
 *  
 *  mu = conc*eps
 *  
 *  Commonly it will be expressed in [cm^-1]
 */
public class MolecularMacroscopicCoefficient extends Coefficient {
  

  /** 
   *  Concentration of the molecule expressed in [muMolar]
   */
  protected double concentration;

  protected MolecularSpecificCoefficient  moleculeSpecificExtinction;


  /** 
   *  Class constructor
   */
  public MolecularMacroscopicCoefficient() {
                 
  }

  /** 
   *  Gets the concentration of the molecule
   */
  public double getConcentration() {
  return this.concentration;
  }

  /**
   * Sets the concentration of the molecule
   * @param conc Concentration of the molecule [muMolar]
   */
  public void setConcentration(double conc) {
      this.concentration=conc;
  }
  
  public void setSpecificCoeffs(MolecularSpecificCoefficient msc)
  {
      moleculeSpecificExtinction=msc;
  }
  
  public void computeMacroscopicCoefficient()
  {
      double w=0;
      for(int i=0;i<moleculeSpecificExtinction.size();i++)
      {
          w=moleculeSpecificExtinction.getWavelength(i);
          this.addPoint(new Wavelength(new Double(w)), moleculeSpecificExtinction.getValue(w)*concentration);
      }
          
  }
  
}