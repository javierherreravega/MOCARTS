package TissueModel;



/** 
 *  The macroscopic scattering coefficient of a molecule within a medium
 */
public class MolecularMacroscopicScatteringCoefficient extends MolecularMacroscopicCoefficient {
  

    public MolecularMacroscopicScatteringCoefficient(double conc, MolecularSpecificScatteringCoefficient mssc)
    {
        this.moleculeSpecificExtinction=mssc;
        this.concentration=conc; 
    }
}