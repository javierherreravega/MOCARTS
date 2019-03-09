package TissueModel;

import java.util.ArrayList;



/** 
 *  This class represents a specific scattering coefficient.
 */
public class MolecularSpecificScatteringCoefficient extends MolecularSpecificCoefficient {

    public ArrayList  myMolecularSpecificScatteringCoefficient;

  /** 
   *  Class constructor
   */
  public MolecularSpecificScatteringCoefficient(String fileName) {
     this.readCoefficientFile(fileName);
      this.setUnits("cm^2");
  }

  public static void main(String[] args)
  {     
    Coefficient _c;
    _c=new MolecularSpecificAbsorptionCoefficient(".\\coeffsFolder\\specificCoeffs_test1.txt");
    _c.getUnits();
  }
}