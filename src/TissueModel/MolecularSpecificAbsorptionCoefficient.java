package TissueModel;

/** 
 *  This class represents an specific absorption coefficient.
 */
public class MolecularSpecificAbsorptionCoefficient extends  MolecularSpecificCoefficient {
  
  /** 
   *  Class constructor
   */
  public MolecularSpecificAbsorptionCoefficient(String fileName) {
      this.readCoefficientFile(fileName);
      this.setUnits("cm^2");
  }

  public static void main(String[] args)
  {     
    Coefficient _c;
    _c=new MolecularSpecificAbsorptionCoefficient(".\\coeffsFolder\\specificAbsorptionCoeffs_test1.txt");
    _c.getUnits();
  }
}
