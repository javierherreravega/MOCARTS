package TissueModel;

/** 
 *  A macroscopic absorption coefficient of a molecule within a medium
 */
public class MolecularMacroscopicAbsorptionCoefficient extends MolecularMacroscopicCoefficient {
    
public MolecularMacroscopicAbsorptionCoefficient(double conc, MolecularSpecificAbsorptionCoefficient msac)
    {
        this.moleculeSpecificExtinction=msac;
        this.concentration=conc; 
    }
}