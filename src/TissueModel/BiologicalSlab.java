package TissueModel;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A BiologicalSlab is a collection of biological slices. All simulations of
 * radiation transport are done over a biologicalSlab.
 *
 * Geometrically, BiologicalSlices are arranged from top to bottom. Top-most
 * index is 1. The top boundary of the slab corresponds to the X-Y plane, and
 * the positive Z axis corresponds to the depth. Location units are expressed in
 * [cm].
 */
public class BiologicalSlab {
    
    private String name;
    private HomogeneousLayer above;
    private ArrayList<BiologicalSlice> TheBiologicalSlices;
    private HomogeneousLayer below;
    /**
     * Class constructor
     */
    public BiologicalSlab(String Name) {
        this.name = Name;
        TheBiologicalSlices = new ArrayList<BiologicalSlice>();
    }

    /**
     * Gets the name of the bioloigcal slab
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrives the i-th slice
     */
    public BiologicalSlice getSlice(int i) {
        try {
            return this.TheBiologicalSlices.get(i);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("getSlice: Index out of bounds");
            return null;
        }
    }

    public ArrayList<Layer> getLayers() {
        ArrayList theLayers = new ArrayList<Layer>();
        /*Adding the above medium as a layer*/
        theLayers.add(above);
        Iterator itBS = this.TheBiologicalSlices.iterator();
        while (itBS.hasNext()) {
            Organ organ = (Organ) itBS.next();
            Iterator itOrgan = organ.getTissues().iterator();
            while (itOrgan.hasNext()) {
                Tissue tissue = (Tissue) itOrgan.next();
                Iterator itTissue = tissue.getLayers().iterator();
                while (itTissue.hasNext()) {
                    Layer layer = (Layer) itTissue.next();
                    theLayers.add(layer);
                }
            }
        }
        /*Adding the below medium as a layer*/
        theLayers.add(below);        
        return theLayers;
    }

    public void addSlice(BiologicalSlice s) {
        this.TheBiologicalSlices.add(s);        
    }

    /**
     * Updates the information for the i-th slice.
     *
     * The i-th slice has to exist, otherwise a warning is issued and nothing is
     * done.
     */
    public void setSlice(Integer i, BiologicalSlice s) {
        try {
            this.TheBiologicalSlices.set(i, s);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("setSlice: Index out of bounds");
        }
    }

    /**
     * Eliminates the i-th slice from the slab
     */
    public void removeSlice(int i) {
        try {
            this.TheBiologicalSlices.remove(i);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("removeSlice: Index out of bounds");
        }
    }

    
    public HomogeneousLayer getAbove() {
        return above;
    }

    public void setAbove(HomogeneousLayer above) {
        this.above = above;
        this.above.setIdx((short)0);
    }

    public HomogeneousLayer getBelow() {
        return below;
    }

    public void setBelow(HomogeneousLayer below) {
        //6-4-15 below.setIdx((short)(this.getLayers().size()-1));
        below.setIdx((short)(this.getLayers().size()+1));
        this.below = below;
        
    }
    
    
    /**
     * Returns a string that represents the current object.
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getName() + "\n");
        Iterator it;
        it = this.TheBiologicalSlices.iterator();
        while (it.hasNext()) {
            BiologicalSlice temp = (BiologicalSlice) it.next();
            string.append("\t" + temp.toString());
        }
        return string.toString();
    }

    public int getNumSlices()
    {
        return this.TheBiologicalSlices.size();
    }
       
    /**
     * Outputs the object to an XML file. Returns the result of the writing
     * operation.
     */
    public Integer write2XML(String filename) {
        return null;
    }
    
}
