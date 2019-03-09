package TissueModel;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A tissue is an aggregation of morphologically similar cells which act
 * together to perform a certain body function. In terms of radiation transport
 * it may be considered to be conformed by a set of layers (mostly of a given
 * type of cells with similar histo-physiological properties); each layer
 * sharing to a extent similar histological properties and thus similar optical
 * properties.
 *
 * Current implementation only allows for the tissue to be conformed by a naive
 * pile of parallel layers.
 */
public class Tissue extends BiologicalSlice {

    /**
     * A tissue is composed of a set of layers. Layers are ordered from top to
     * bottom.
     */
    private ArrayList<Layer> theLayers;
    private String tissueName;        

    /**
     * Class constructor
     */
    public Tissue(String name) {
        tissueName = name;
        theLayers = new ArrayList<Layer>();
    }

    public String getName() {
        return tissueName;
    }

    public int getNLayers() {
        return this.theLayers.size();
    }

    /**
     * Gets the i-th layer of the tissue.
     *
     * If the i-th layer does not exist, a warning is thrown and nothing is
     * done.
     */
    public Layer getLayer(int i) {
        try {
            return this.theLayers.get(i);
        } catch (IndexOutOfBoundsException e) {
            System.out.print("getLayer: Index out of bounds");
            return null;
        }
    }

    /**
     * Adds a new layer at the bottom of the tissue. Before to add the layer
     * the values z0 and z1 should be set for the new layer, these are in
     * function of the previous layer, so we need to check if there is a previos
     * layer in the layers' list.
     *
     * @param l-The new layer to add in the Tissue.
     */
    public void addLayer(Layer l) {

        if (this.theLayers.isEmpty()){
            l.setZ0(0);
            l.setZ1(l.getThickness());
        }
        else{
            double z0=this.theLayers.get(this.theLayers.size()-1).getZ1();
            l.setZ0(z0);
            l.setZ1(z0+l.getThickness());
        }
        this.theLayers.add(l);
        l.setIdx((short)(this.theLayers.size()));
    }

    /**
     * Sets the i-th layer.
     *
     * The i-th layer has to exist, otherwise a warning is issued and nothing is
     * done.
     */
    public void setLayer(int i, Layer l) {
        try {
            this.theLayers.set(i, l);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("setLayer: Index out of bounds.");
        }
    }

    /**
     * Eliminates the i-th layer from the tissue. The indexes of all other
     * layers below this one will be updated accordingly.
     *
     * If the i-th layer does not exist, then nothing is done.
     */
    public void removeLayer(int i) {
        try {
            this.theLayers.remove(i);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("removeLayer: Index out of bounds.");
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("\t" + this.getName() + "\n");

        Iterator it;
        it = this.theLayers.iterator();
        while (it.hasNext()) {
            Layer temp = (Layer) it.next();
            string.append("\t" + temp.toString());
        }

        return string.toString();
    }

    @Override
    public Integer write2XML(String filename) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<Layer> getLayers() {
        return this.theLayers;
    }

    public Layer getLastLayer() {
        return this.getLayer(this.getNLayers()-1);
    }

}
