package TissueModel;

import Utility.*;
         /*  This class implements the Heyney-Greenstein anisotropy function to calculate
         * the deflection angle in a scattering event.
         *
         *  The Henyey-Greenstein is a well known anisotropy function which only depends
         * on a single parameter; the anisotropy facot g.
         */

public class HenyeyGreensteinAnisotropyFunction extends AnisotropyFunction {

    /**
     *  Anisotropy factor
     */
    private double g;
 
    

    /**
     *  Class constructor
     */
    public double HenyeyGreensteinAnisotropyFunction() {
        double cost;

        if (g == 0.0) {
            cost = 2 * Utils.RandomNum() - 1;
        } else {
            double temp = (1 - g * g) / (1 - g + 2 * g * Utils.RandomNum());
            cost = (1 + g * g - temp * temp) / (2 * g);
            if (cost < -1) {
                cost = -1;
            } else if (cost > 1) {
                cost = 1;
            }
        }

        return (cost);
    }

    
    /**
     *  Gets the anisotropy factor g.
     */
    public double getAnisotropyFactor() {
        return this.g;


    }

    /**
     *  Sets the anisotropy factor g
     */
    public void setAnisotropyFactor(double g) {
        this.g = g;

    }

    public static void main(String[] args) {
        HenyeyGreensteinAnisotropyFunction myHenyeyGreensteinAnisotropyFunction = new HenyeyGreensteinAnisotropyFunction();
        myHenyeyGreensteinAnisotropyFunction.setAnisotropyFactor(0.90);        
        System.out.println(myHenyeyGreensteinAnisotropyFunction.HenyeyGreensteinAnisotropyFunction());
        
    }
}
