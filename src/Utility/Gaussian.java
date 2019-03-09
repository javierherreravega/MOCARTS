package Utility;



/** 
 *  This class defines a Gaussian distribution function.
 */
public class Gaussian extends ProbabilityDensityFunction {

  public int mean;

  public int stdDev;
  public double probability;
  public int x;


  public double evaluate(int mean, int stdDev, int x) {
        probability=(1/(Math.sqrt(2*Math.PI*(Math.pow(stdDev,2)))))*Math.pow(Math.E, ((-1*(Math.pow(x-mean, 2)))/(2*Math.pow(stdDev,2))));
        return probability;
  }


public static void main(String[] args) {
       Gaussian myGaussian = new Gaussian();
       System.out.println(myGaussian.evaluate(30,5,30));
      }
}
