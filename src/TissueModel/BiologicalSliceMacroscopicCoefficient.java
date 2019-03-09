package TissueModel;

import java.util.Vector;

/** 
 *  This class represents the extinction for a whole BiologicalSlice
 * (whether organ, tissue, or layer). In a biological slice, there may be
 * several chromophores or pigments contributing to the extinction, plus of
 * course an overall background extinction due to non-explicitly modelled elements.
 *  
 *  Commonly it will be expressed in [cm^-1]
 */
public class BiologicalSliceMacroscopicCoefficient extends Coefficient {
  
  private Coefficient bgCoefficient;

    /**
   * 
   * @element-type MolecularMacroscopicCoefficient
   */
  public Vector  pigments;
    
}