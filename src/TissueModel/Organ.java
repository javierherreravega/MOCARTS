package TissueModel;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * An organ is a biologically differentiated part of the body that performs a
 * specific function. It is often conformed by a set of tissues.
 *
 * The organ itself is "meaningless" for a radiation transport simulation;
 * however, this class may facilitate the composition of multilayered tissue
 * models.
 *
 * Current implementation of an organ only allows for a trivial geometry whereby
 * the organ is conformed by an ordered piled set of tissues.
 */
public class Organ extends BiologicalSlice {

    /**
     * An organ is composed by a set of tissues. These tissues are ordered from
     * top to bottom. @element-type Tissue
     */
    private ArrayList<Tissue> theTissues;
    private String organName;

    /**
     * Class constructor
     */
    public Organ(String name) {
        organName = name;
        theTissues = new ArrayList<Tissue>();
    }

    public String getName()
    {
        return organName;
    }
    /**
     * Gets the number of tissues conforming the organ. This is calculated on
     * the fly from the size of the attribute tissues. It may return 0 if the
     * organ has no tissues.
     */
    public int getNTissues() {
        return this.theTissues.size();
    }

    /**
     * Retrieves the i-th tissue of the organ.
     *
     * If the i-th tissue does not exist, a warning is thrown and nothing is
     * done.
     */
    public Tissue getTissue(int i) {
        try {
            return this.theTissues.get(i);
        } catch (IndexOutOfBoundsException e) {
            System.out.print("getTissue: Index out of bounds");
            return null;
        }
    }

    /**
     * Adds a new tissue at the bottom of the organ
     */
    public void addTissue(Tissue t) {
        this.theTissues.add(t);
    }

    /**
     * Sets the i-th tissue of the organ
     *
     * The i-th tissue has to exist, otherwise a warning issued and nothing is
     * done.
     */
    public void setTissue(int i, Tissue t) {
        try {
            this.theTissues.set(i, t);
        } catch (IndexOutOfBoundsException e) {
            System.out.print("setTissue: Index out of bounds");
        }
    }

    /**
     * Eliminates the i-th tissue from the organ. The indexes of all other
     * tissues below this one will be updated accordingly.
     *
     * If the i-th tissue does not exist, nothing is done.
     */
    public void removeTissue(int i) {
        try {
            this.theTissues.remove(i);
        } catch (IndexOutOfBoundsException e) {
            System.out.print("removeTissue: Index out of bounds");
        }
    }

    public ArrayList<Tissue> getTissues()
    {
        return this.theTissues;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getName() + "\n");

        Iterator it;
        it=this.theTissues.iterator();
        while(it.hasNext())
        {
            Tissue temp=(Tissue)it.next();
            string.append("\t"+temp.toString());
        }
        return string.toString();
    }

    @Override
    public Integer write2XML(String filename) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Tissue getLastTissue() {
        return this.getTissue(this.getNTissues()-1);        
    }
}