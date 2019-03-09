/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mcrts;

import Simulation.InParams;
import Simulation.OutParams;
import Simulation.Simulation;
import Utility.XMLModelReader;
import Utility.XMLwriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.xml.sax.SAXException;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
            throws FileNotFoundException, IOException, SAXException {
        if (args.length == 1) {
            String inputFile = args[0];
            
            InParams simulationSpecs;
            XMLModelReader xmlr = new XMLModelReader();
            xmlr.read(inputFile);
            simulationSpecs = xmlr.getInparams();
            Simulation mySimulation = new Simulation(simulationSpecs);
            mySimulation.mainSimulationLoop();
            OutParams out=mySimulation.getOutparams();
            System.out.println("Writing results...");
            XMLwriter writer=new XMLwriter(simulationSpecs,out);
            writer.write();            
        } else {
            System.out.println("USAGE: java -jar mocarts.jar inputFile.xml");            
        }
    }

}
