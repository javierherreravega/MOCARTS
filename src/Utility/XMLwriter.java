/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import SensingGeometry.Detector;
import Simulation.Cell;
import Simulation.InParams;
import Simulation.OutParams;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XMLwriter {

    InParams in;
    OutParams out;

    public XMLwriter(InParams in, OutParams out) {
        this.in = in;
        this.out = out;
    }

    public void write() {
        try {
            File file = new File(in.getOut_fname());
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("<?xml version=\"1.0\"?>\n");
            bw.write("<Simulation>\n");
//            bw.write("\t<RAT>\n");
//            bw.write("\t\t<R>");
//            bw.write("</R>\n");
//            bw.write("\t\t<A>");
//            bw.write("</A>\n");
//            bw.write("\t\t<T>");
//            bw.write("</T>\n");
//            bw.write("\t</RAT>\n");

            //Write reflectance matrix for each light source
            bw.write("\t<Reflectance_xya>\n");
            for (int source = 1; source <= in.getSourcesPositions().size(); source++) {
                bw.write("\t<Source id=\"" + source + "\">\n");
                for (int a = 0; a < in.getNa(); a++) {
                    printMatrixBySourcePlaneXY(out.getCell_Rd_xya(), a, source, bw);
                }
                bw.write("\t</Source>\n");
            }
            bw.write("\t</Reflectance_xya>\n");

            //Write Absorption matrix for each light source
            bw.write("\t<Absorption_xyz>\n");
            for (int source = 1; source <= in.getSourcesPositions().size(); source++) {
                bw.write("\t<Source id=\"" + source + "\">\n");
                for (int z = 0; z < in.getNz1(); z++) {
                    printMatrixBySourcePlaneXY(out.getCell_A_xyz(), z, source, bw);
                }
                bw.write("\t</Source>\n");
            }
            bw.write("\t</Absorption_xyz>\n");

            //Write Transmitance matrix for each light source
            bw.write("\t<Transmitance_xya>\n");
            for (int source = 1; source <= in.getSourcesPositions().size(); source++) {
                bw.write("\t<Source id=\"" + source + "\">\n");
                for (int a = 0; a < in.getNa(); a++) {
                    printMatrixBySourcePlaneXY(out.getCell_Tt_xya(), a, source, bw);
                }
                bw.write("\t</Source>\n");
            }
            bw.write("\t</Transmitance_xya>\n");

            //Write sensed values for each Detector
            bw.write("\t<Detectors>\n");
            for (int detector = 0; detector <in.getDetectorsPositions().size(); detector++) {
                Detector tempD=(Detector)in.getDetectorsPositions().get(detector);                
                bw.write("\t<Detector id=\"" + (detector+1) + "\">"+ tempD.getRd(out.getCell_Rd_xya(), in,tempD.getSourceId())+"</Detector>\n");
            }
            bw.write("\t</Detectors>\n");

            bw.write("</Simulation>\n");
            bw.close();

        } catch (IOException ex) {
            Logger.getLogger(XMLwriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//out.getCell_A_xyz()

    public void printMatrixBySourcePlaneXY(ArrayList[][][] matrix, int z, int sId, BufferedWriter bw) throws IOException {
        for (int y = 0; y < this.in.getNy1(); y++) {
            for (int x = 0; x < this.in.getNx1(); x++) {
                if (matrix[x][y][z] != null) {
                    double At = 0;
                    Iterator it = matrix[x][y][z].iterator();
                    while (it.hasNext()) {
                        Cell temp = (Cell) it.next();
                        if (temp.getsId() == sId) {
                            At += temp.getValue();
                            break;
                        }
                    }
                    if (x != this.in.getNx1() - 1) {
                        bw.write(At + ",");
                    } else {
                        bw.write(At + "");
                    }
                } else {
                    if (x != this.in.getNx1() - 1) {
                        bw.write(0 + ",");
                    } else {
                        bw.write(0);
                    }
                }
            }
            bw.write("\n");
        }

    }

    public static void main(String[] args) {
        XMLwriter w = new XMLwriter(null, null);
        w.write();
    }

}
