package TissueModel;

//import Utility.Wavelength;
//import Utility.Double;
import java.util.*;
import java.io.*;
import Utility.*;

/** 
 *  A coefficient represents an extinction coefficient, whether absorption or 
 * scattering, applicable to a molecule or a full medium, and regardless of
 * whether it is specific or macroscopic.
 *  Units are [nm] for wavelengths. However extinction units vary according to
 * the type of coefficient being represeted.
 *  
 *  Internally is a discrete set of pairs <wavelength, extinction value>.
 * Extinction values at unknown wavelengths are interpolated.
 * @invariant arrayLength: wavelengths -> size = values -> size 
 */
public class Coefficient {
    
    /**
     *  An array of wavelengths at which the coefficient is known.
     *
     */
    protected ArrayList<Wavelength> wlengths;
    /**
     *  An array of extinction values at those wavelengths at which the coefficient is known.
     */
    protected ArrayList<Double> values;
    
    private String name;
    /**
     *  The units in which the extinction values are expressed.
     *  Often, specific extinction coefficient will be expressed in [cm^2] whereas
     * macroscopic extinction coefficients will be expressed in [cm^-1].
     */
    private String units;

    /**
     *  Class constructor
     */
    public Coefficient() {
        this.wlengths = new ArrayList<Wavelength>();
        this.values = new ArrayList<Double>();
    }

    public String getName() {
        return this.name;
    }

    /**
     *  Sets the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *  Gets the units of the extinction values.
     */
    public String getUnits() {
        return this.units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    /**
     *  Gets the extinction value at wavelength @w.
     */
    public Double getValue(Double w) {
        int position = Utility.Utils.searchInArrayList(wlengths, w);
        return this.values.get(position);
    }
    
     /**
     *  Gets the wavelength at position i in the array
     */
    public double getWavelength(int i) {        
        return this.wlengths.get(i).getValue();
    }

    /**
     *  Adds a new point at wavelength @w with extinction value @v expressed in [cm^-1].
     *
     *  If the coefficient is already known at wavelength @w then a warning
     * is issued (requesting to use method setPoint) and nothing is done.
     */
    public void addPoint(Wavelength w, Double v) {
        //Comprobar primero si w existe en wlengths
        if (Utility.Utils.searchInArrayList(wlengths, w.getValue()) != -1) {
            System.out.println("The wavelength already exists. Use method setPoint instead.");
        } else {
            this.wlengths.add(w);
            this.values.add(v);
        }
    }

    /**
     *  Updates the extinction value @v at wavelength @w.
     *
     *  If the coeficient is not known at wavelength @w then a warning is issued
     * and nothing is done.
     */
    public void setPoint(Wavelength w, Double v) {
        int idx = Utility.Utils.searchInArrayList(wlengths, w.getValue());
        if (idx == -1) {
            System.out.println("The wavelength does not exist. Use method addPoint instead.");
        } else {
            this.values.set(idx, v);
        }
    }

    /**
     *  Eliminates the point at wavelength @w.
     *  If the coefficient is not known at wavelength @w then nothing is done.
     */
    public void removePoint(Wavelength w) {
        int idx = Utility.Utils.searchInArrayList(wlengths, w.getValue());
        //Comprobar primero si w existe en wlengths
        if (idx != -1) {
            this.wlengths.remove(idx);
            this.values.remove(idx);
        }
    }

    public void readCoefficientFile(String filename) {
        try {
            File f = new File(filename);
            FileReader readFile = new FileReader(f);
            BufferedReader br = new BufferedReader(readFile);
            String l = "";
            while (true) {
                l = br.readLine();
                if (l != null) {
                    StringTokenizer tokens = new StringTokenizer(l, " ");
                    while (tokens.hasMoreTokens()) {
                        String wavelength = tokens.nextToken();
                        String coefficient = tokens.nextToken();
                        addPoint(new Wavelength(new Double(wavelength)), new Double(coefficient));
                    }
                } else {
                    break;
                }
            }
            br.close();
            readFile.close();
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());

        }


    }

    /**
     *  Returns a string that represents the current object.
     */
    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < this.wlengths.size(); i++) {
            res = res + "-" + (wlengths.get(i).getValue()) + "," + (values.get(i).toString());
        }
        return res;
    }

    /**
     *  Outputs the object to an XML file.
     *
     *  Returns the result of the writing operation.
     */
    public Integer write2XML(String filename) {
        return null;
    }


    public double[][] toDoubleArray()
    {
        double _wavelength;
        double [][] ret=new double[this.wlengths.size()][2];
        for(int w=0;w<this.wlengths.size();w++)
        {
            _wavelength=this.wlengths.get(w).getValue();
            ret[w][0]=_wavelength;
            ret[w][1]=this.getValue(_wavelength);
        }
        return ret;
    }
    
    public int size()
    {
        return this.wlengths.size();
    }
    /*
     * Method to test this class
     */
    public static void main(String[] args) {
       Coefficient myCoefficient = new Coefficient();
         /*  myCoefficient.addPoint(new Wavelength(new Double(700)), new Double(2.5));
        myCoefficient.addPoint(new Wavelength(new Double(705)), new Double(6.5));
        myCoefficient.addPoint(new Wavelength(new Double(800)), new Double(5.8));
        myCoefficient.addPoint(new Wavelength(new Double(700)), new Double(5.5));
        System.out.println(myCoefficient.toString());

        myCoefficient.setPoint(new Wavelength(new Double(300)), new Double(5.5));
        myCoefficient.setPoint(new Wavelength(new Double(700)), new Double(90));
          
        System.out.println(myCoefficient.toString());
*/
        myCoefficient.readCoefficientFile("C:\\Users\\Bibiana\\Documents\\MCRTS\\app\\filecoefficient.txt");
        System.out.println(myCoefficient.toString());
        System.out.println(myCoefficient.getValue(700.0));
    }
}
