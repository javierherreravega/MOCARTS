/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation;

import SensingGeometry.LightSource;
import TissueModel.BiologicalSlab;
import TissueModel.HomogeneousLayer;
import Utility.Point3D;
import Utility.Utils;

public class Simulation {

    InParams inparams;
    OutParams outparams;
    long i_photon;
    Photon photon;
    long num_photons;
    long photon_rep = 10;
    short num_runs;
    static double COSZERO = (1.0 - 1.0E-12);
    static double CHANCE = 0.1;
    static double PI = 3.1415926;
    Grid myGrid = new Grid();

    public Simulation(InParams inparams) {
        this.inparams = inparams;
        this.num_photons = inparams.getNum_photons();
        outparams = new OutParams(this.inparams);
    }

    public void mainSimulationLoop() {
        DoOneRun(num_runs);
    }
    
    public void DoOneRun(short NumRuns) {
        for (int ns = 0; ns < inparams.getSourcesPositions().size(); ns++) {
            photon_rep = 10;
            CriticalAngle(inparams.getNum_layers(), inparams.getBiologicalSlab());
            outparams.setRsp(Rspecular(inparams.getBiologicalSlab()));
            i_photon = num_photons;
            long startTime = System.nanoTime();
            System.out.println("Light Source: " + (ns+1));
            do {
                if (num_photons - i_photon == photon_rep) {
                    System.out.println(i_photon + " photons & " + NumRuns + "  runs left, ");
                    PredictDoneTime(num_photons - i_photon, num_photons);
                    photon_rep *= 10;
                }
                photon = LaunchPhoton(outparams.Rsp, inparams.getBiologicalSlab(), ((LightSource) inparams.getSourcesPositions().get(ns)).getPosition());
                do {
                    HopDropSpin(inparams, photon, outparams, ns);
                } while (!photon.isDead());
            } while ((--i_photon) > 0);
            long stopTime = System.nanoTime();
            long elapsedTime = stopTime - startTime;
            System.out.println("Time: " + elapsedTime * 0.000000001 + " Seconds");
        }
    }

    private void PredictDoneTime(long l, long num_photons) {
    }

    private Photon LaunchPhoton(double Rsp, BiologicalSlab slab, Point3D sourceLocation) {
        Photon photon = new Photon();
        photon.setWeight(1.0 - Rsp);
        photon.setDead(false);
        photon.setCurrLayer(slab.getLayers().get(1));
        photon.setS(0);
        photon.setRemainingStep(0);
        photon.setLocation(new Point3D(sourceLocation.getX(), sourceLocation.getY(), sourceLocation.getZ()));
        photon.setDirection(0, 0, 1);

        if ((slab.getLayers().get(1).getMua() == 0.0)
                && (slab.getLayers().get(1).getMus() == 0.0)) {
            photon.setCurrLayer(slab.getLayers().get(2));
            photon.setLocation(new Point3D(photon.getLocation().getX(), photon.getLocation().getY(), slab.getLayers().get(2).getZ0()));
        }
        return photon;
    }

    private void HopDropSpin(InParams inparams, Photon photon, OutParams outparams, int sId) {

        if ((photon.getCurrLayer().getMua() == 0.0) && (photon.getCurrLayer().getMus() == 0.0)) {
            ;
            HopInGlass(inparams, photon, outparams, sId);
        } else {
            HopDropSpinInTissue(inparams, photon, outparams, sId);
        }

        if (photon.getWeight() < inparams.getWth() && !photon.isDead()) {
            Roulette(photon);
        }
    }

    /**
     * The implementation of this function is based on the same mcml code
     *
     * @param Layerspecs Struct that holds the layers
     * @return The specular reflectance computed accordingly to the refractive
     * indexes of each layer
     */
    private double Rspecular(BiologicalSlab Layerspecs) {
        double r1, r2;
        double temp;
        double nAbove = Layerspecs.getLayers().get(0).getRefractiveIndex(null);
        double nfirstLayer = Layerspecs.getLayers().get(1).getRefractiveIndex(null);

        temp = (nAbove - nfirstLayer) / (nAbove + nfirstLayer);
        r1 = temp * temp;

        if ((Layerspecs.getLayers().get(1).getMua() == 0.0)
                && (Layerspecs.getLayers().get(1).getMus() == 0.0)) {

            /*
             * If I ask for the second layer (index 2) is possible that this
             * does not exist because the model may has just: 1 above layer, 1
             * tissue layer and 1 below layer, so as I've defined above and
             * below layers as members of BiologicalSlab the index 2 not
             * exists!!! Finally the above and below should be included as layers
             * in the whole model, keeping relation with mcml
             */
            double nNextLayer = Layerspecs.getLayers().get(2).getRefractiveIndex(null);
            
            temp = (nfirstLayer - nNextLayer)
                    / (nfirstLayer + nNextLayer);
            r2 = temp * temp;
            r1 = r1 + (1 - r1) * (1 - r1) * r2 / (1 - r1 * r2);
        }
        return r1;
    }

    public void CriticalAngle(short numLayers, BiologicalSlab slab) {
        short i = 0;
        double n1, n2;        
        for (i = 1; i <= numLayers - 2; i++) {
            n1 = slab.getLayers().get(i).getRefractiveIndex(null);
            n2 = slab.getLayers().get(i - 1).getRefractiveIndex(null);
            ((HomogeneousLayer) slab.getLayers().get(i)).cos_crit0 = n1 > n2 ? Math.sqrt(1.0 - n2 * n2 / (n1 * n1)) : 0.0;

            n2 = slab.getLayers().get(i + 1).getRefractiveIndex(null);
            ((HomogeneousLayer) slab.getLayers().get(i)).cos_crit1 = n1 > n2 ? Math.sqrt(1.0 - n2 * n2 / (n1 * n1)) : 0.0;
        }
    }

    private void HopDropSpinInTissue(InParams inparams, Photon photon, OutParams outparams, int sId) {
        StepSizeInTissue(photon, inparams);
        if (HitBoundary(photon, inparams)) {
            Hop(photon);
            CrossOrNot(inparams, photon, outparams, sId);
        } else {
            Hop(photon);
            Drop(inparams, photon, outparams, sId);
            Spin(photon.getCurrLayer().getAnisotropy(null), photon);
        }
    }

    private void StepSizeInTissue(Photon photon, InParams inparams) {
        double mua = photon.getCurrLayer().getMua();
        double mus = photon.getCurrLayer().getMus();
        if (photon.getRemainingStep() == 0) { 
            double rnd;
            do {
                rnd = Utils.RandomNum();
            } while (rnd <= 0.0);
            photon.setS(-Math.log(rnd) / (mua + mus));
        } else {
            photon.setS(photon.getRemainingStep() / (mua + mus));
            photon.setRemainingStep(0.0);
        }
    }

    private boolean HitBoundary(Photon photon, InParams inparams) {
        double dl_b = 0;  
        double uz = photon.getUz();
        boolean hit;
        
        if (uz > 0.0) {
            dl_b = (photon.getCurrLayer().getZ1() - photon.getLocation().getZ()) / uz;	

        } else if (uz < 0.0) {
            dl_b = (photon.getCurrLayer().getZ0() - photon.getLocation().getZ()) / uz;	
        }

        if (uz != 0.0 && photon.getS() > dl_b) {
           
            double mut = photon.getCurrLayer().getMua() + photon.getCurrLayer().getMus();

            photon.setRemainingStep((photon.getS() - dl_b) * mut);
            photon.setS(dl_b);

            hit = true;
        } else {
            hit = false;
        }

        return (hit);
    }

    private void Hop(Photon photon) {
        double s = photon.getS();
        photon.getLocation().setX(photon.getLocation().getX() + (s * photon.getUx()));
        photon.getLocation().setY(photon.getLocation().getY() + (s * photon.getUy()));
        photon.getLocation().setZ(photon.getLocation().getZ() + (s * photon.getUz()));
    }

    private void CrossOrNot(InParams inparams, Photon photon, OutParams outparams, int sId) {
        if (photon.getUz() < 0.0) {
            CrossUpOrNot(inparams, photon, outparams, sId);
        } else {
            CrossDnOrNot(inparams, photon, outparams, sId);
        }
    }

    private void CrossUpOrNot(InParams inparams, Photon photon, OutParams outparams, int sId) {
        double uz = photon.getUz();
        double uz1 = 0.0;
        double r = 0.0;
        double ni = photon.getCurrLayer().getRefractiveIndex(null);
        double nt = inparams.getBiologicalSlab().getLayers().get(photon.getCurrLayer().getIdx() - 1).getRefractiveIndex(null);

        HomogeneousLayer tempLayer = (HomogeneousLayer) photon.getCurrLayer();
        if (-uz <= tempLayer.getCos_crit0()) {
            r = 1.0;
        } else {
            ReflectanceModel RModel = new Fresnel();
            double[] res = RModel.Reflectance(ni, nt, -uz);
            r = res[0];
            uz1 = res[1];
        }

        if (Utils.RandomNum() > r) {		

            if (photon.getCurrLayer().getIdx() == 1) {
                photon.setUz(-uz1);
                RecordR(0.0, inparams, photon, outparams, sId);
                photon.setDead(true);
            } else {
                photon.setCurrLayer(inparams.getBiologicalSlab().getLayers().get(photon.getCurrLayer().getIdx() - 1));
                photon.setUx((ni / nt) * photon.getUx());
                photon.setUy((ni / nt) * photon.getUy());
                photon.setUz(-uz1);
            }
        } else {
            photon.setUz(-uz);
        }
    }

    private void RecordR(double Refl, InParams inparams, Photon photon, OutParams outparams, int sId) {
        double x = photon.getLocation().getX();
        double y = photon.getLocation().getY();

        int longX = inparams.getNx1();
        int longY = inparams.getNy1();

        short ix, iy;
        short ir, ia;
        double ixd, iyd;
        double ird, iad;

        ird = Math.sqrt(x * x + y * y) / inparams.getDr();
        if (ird > inparams.getNr() - 1) {
            ir = (short) (inparams.getNr() - 1);
        } else {
            ir = (short) ird;
        }

        iad = Math.acos(-photon.getUz()) / inparams.getDa();
        if (iad > inparams.getNa() - 1) {
            ia = (short) (inparams.getNa() - 1);
        } else {
            ia = (short) iad;
        }

        iyd = photon.getLocation().getY() / inparams.getDy1();
        iyd = iyd < 0 ? (short) ((longY / 2) - Math.abs(iyd)) : (short) ((longY / 2) + iyd);

        if (iyd > inparams.getNy1() - 1) {
            iy = (short) (inparams.getNy1() - 1);
        } else if (iyd < 0) {
            iy = 0;
        } else {
            iy = (short) iyd;
        }

        ixd = photon.getLocation().getX() / inparams.getDx1();
        ixd = ixd < 0 ? (short) ((longX / 2) - Math.abs(ixd)) : (short) ((longX / 2) + ixd);
        if (ixd > inparams.getNx1() - 1) {
            ix = (short) (inparams.getNx1() - 1);
        } else if (ixd < 0) {
            ix = 0;
        } else {
            ix = (short) ixd;
        }

        outparams.setRd_xyaValue(sId, ix, iy, ia, photon.getWeight() * (1.0 - Refl));
        photon.setWeight(Refl * photon.getWeight());
    }

    private void CrossDnOrNot(InParams inparams, Photon photon, OutParams outparams, int sId) {
        double uz = photon.getUz(); 

        double uz1 = 0.0;	

        double r = 0.0;	

        double ni = photon.getCurrLayer().getRefractiveIndex(null);
        double nt = 0;
        try {
            nt = inparams.getBiologicalSlab().getLayers().get(photon.getCurrLayer().getIdx() + 1).getRefractiveIndex(null);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("asda");
        }
        
        HomogeneousLayer tempLayer = (HomogeneousLayer) photon.getCurrLayer();
        if (uz <= tempLayer.getCos_crit1()) {
            r = 1.0;		
        } else {
            ReflectanceModel RModel = new Fresnel();
            double[] res = RModel.Reflectance(ni, nt, uz);
            r = res[0];
            uz1 = res[1];
        }
        if (Utils.RandomNum() > r) {		

            if (photon.getCurrLayer().getIdx() == inparams.getNum_layers() - 2) {

                photon.setUz(uz1);
                RecordT(0.0, inparams, photon, outparams, sId);
                photon.setDead(true);
            } else {

                photon.setCurrLayer(inparams.getBiologicalSlab().getLayers().get(photon.getCurrLayer().getIdx() + 1));

                photon.setUx((ni / nt) * photon.getUx());
                photon.setUy((ni / nt) * photon.getUy());
                photon.setUz(uz1);

            }
        } else {
            
            photon.setUz(-uz);
        }      
    }

    private void RecordT(double Refl, InParams inparams, Photon photon, OutParams outparams, int sId) {
        double x = photon.getLocation().getX();
        double y = photon.getLocation().getY();

        int longX = inparams.getNx1();
        int longY = inparams.getNy1();

        short ix, iy;
        short ir, ia;

        double ixd, iyd;
        double ird, iad;

        ird = Math.sqrt(x * x + y * y) / inparams.getDr();
        if (ird > inparams.getNr() - 1) {
            ir = (short) (inparams.getNr() - 1);
        } else {
            ir = (short) ird;
        }

        iad = Math.acos(photon.getUz()) / inparams.getDa();
        if (iad > inparams.getNa() - 1) {
            ia = (short) (inparams.getNa() - 1);
        } else {
            ia = (short) iad;
        }

        iyd = photon.getLocation().getY() / inparams.getDy1();
        iyd = iyd < 0 ? (short) ((longY / 2) - Math.abs(iyd)) : (short) ((longY / 2) + iyd);

        if (iyd > inparams.getNy1() - 1) {
            iy = (short) (inparams.getNy1() - 1);
        } else if (iyd < 0) {
            iy = 0;
        } else {
            iy = (short) iyd;
        }

        ixd = photon.getLocation().getX() / inparams.getDx1();
        ixd = ixd < 0 ? (short) ((longX / 2) - Math.abs(ixd)) : (short) ((longX / 2) + ixd);
        if (ixd > inparams.getNx1() - 1) {
            ix = (short) (inparams.getNx1() - 1);
        } else if (ixd < 0) {
            ix = 0;
        } else {
            ix = (short) ixd;
        }

        outparams.setTt_xyaValue(sId, ix, iy, ia, photon.getWeight() * (1.0 - Refl));
        photon.setWeight(Refl * photon.getWeight());
    }

    private void Drop(InParams inparams, Photon photon, OutParams outparams, int sId) {
        double dwa;
        double x = photon.getLocation().getX();
        double y = photon.getLocation().getY();
        double izd, ird, ixd, iyd;
        short iz, ir, ix, iy;
        double mua, mus;
        int longX = inparams.getNx1();
        int longY = inparams.getNy1();

        izd = photon.getLocation().getZ() / inparams.getDz1();
        if (izd > inparams.getNz1() - 1) {
            iz = (short) (inparams.getNz1() - 1);
        } else {
            iz = (short) izd;
        }

        iyd = photon.getLocation().getY() / inparams.getDy1();
        iyd = iyd < 0 ? (short) ((longY / 2) - Math.abs(iyd)) : (short) ((longY / 2) + iyd);

        if (iyd > inparams.getNy1() - 1) {
            iy = (short) (inparams.getNy1() - 1);
        } else if (iyd < 0) {
            iy = 0;
        } else {
            iy = (short) iyd;
        }

        ixd = photon.getLocation().getX() / inparams.getDx1();
        ixd = ixd < 0 ? (short) ((longX / 2) - Math.abs(ixd)) : (short) ((longX / 2) + ixd);
        if (ixd > inparams.getNx1() - 1) {
            ix = (short) (inparams.getNx1() - 1);
        } else if (ixd < 0) {
            ix = 0;
        } else {
            ix = (short) ixd;
        }


        ird = Math.sqrt((x * x) + (y * y)) / inparams.getDr();
        if (ird
                > inparams.getNr() - 1) {
            ir = (short) (inparams.getNr() - 1);
        } else {
            ir = (short) ird;
        }

        mua = photon.getCurrLayer().getMua();
        mus = photon.getCurrLayer().getMus();
        dwa = (photon.getWeight() * mua) / (mua + mus);
        photon.setWeight(photon.getWeight() - dwa);
        outparams.setA_xyzValue(sId, ix, iy, iz, dwa);
    }

    private void Spin(double g, Photon photon) {

        double cost, sint;	

        double cosp, sinp;	

        double ux = photon.getUx();
        double uy = photon.getUy();
        double uz = photon.getUz();
        double psi;

        cost = SpinTheta(g);
        sint = Math.sqrt(1.0 - cost * cost);
       

        psi = 2.0 * PI * Utils.RandomNum(); 

        cosp = Math.cos(psi);
        if (psi < PI) {
            sinp = Math.sqrt(1.0 - cosp * cosp);
        }  else {
            sinp = -Math.sqrt(1.0 - cosp * cosp);
        }

        if (Math.abs(uz) > COSZERO) { 	

            photon.setUx(sint * cosp);
            photon.setUy(sint * sinp);
            photon.setUz(cost * Math.signum(uz));
        } else {		

            double temp = Math.sqrt(1.0 - uz * uz);
            photon.setUx(sint * (ux * uz * cosp - uy * sinp) / temp + ux * cost);
            photon.setUy(sint * (uy * uz * cosp + ux * sinp) / temp + uy * cost);
            photon.setUz(-sint * cosp * temp + uz * cost);
        }
    }

    private double SpinTheta(double g) {
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

    private void Roulette(Photon photon) {
        if (photon.getWeight() == 0.0) {
            photon.setDead(true);
        } else if (Utils.RandomNum() < CHANCE) {
            photon.setWeight(photon.getWeight() / CHANCE);
        } else {
            photon.setDead(true);
        }
    }

    private void HopInGlass(InParams inparams, Photon photon, OutParams outparams, int sId) {
        double dl;     

        if (photon.getUz() == 0.0) {
            
            photon.setDead(true);
        } else {
            StepSizeInGlass(photon, inparams);
            Hop(photon);
            CrossOrNot(inparams, photon, outparams, sId);
        }
    }

    private void StepSizeInGlass(Photon photon, InParams inparams) {
        double dl_b;	

        double uz = photon.getUz();
       
        if (uz > 0.0) {
            dl_b = (photon.getCurrLayer().getZ1() - photon.getLocation().getZ()) / uz;
        } else if (uz < 0.0) {
            dl_b = (photon.getCurrLayer().getZ0() - photon.getLocation().getZ()) / uz;
        } else {
            dl_b = 0.0;
        }
        photon.setS(dl_b);
    }

    public OutParams getOutparams() {
        return outparams;
    }
}
