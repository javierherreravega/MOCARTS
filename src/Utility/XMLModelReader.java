package Utility;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import SensingGeometry.Detector;
import SensingGeometry.LightSource;
import Simulation.InParams;
import Simulation.OutParams;
import Simulation.Simulation;
import TissueModel.*;
import Utility.Point3D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


public class XMLModelReader extends DefaultHandler {

    private final XMLReader xr;
    private BiologicalSlab bs;
    private InParams ip;
    private Layer tempLayer;
    private ArrayList sourcesPositions=new ArrayList();
    private ArrayList detectorsPositions=new ArrayList();
    private LightSource tempLS;
    private Detector tempD;

    private boolean bmodel = false;
    private boolean bnabove = false;
    private boolean bnbelow = false;
    private boolean bn = false;
    private boolean bmua = false;
    private boolean bmus = false;
    private boolean bg = false;
    private boolean bthickness = false;
    private boolean bdeltax=false;
    private boolean bdeltay=false;
    private boolean bdeltaz=false;
    private boolean bnx=false;
    private boolean bny=false;
    private boolean bnz=false;
    private boolean bphotons=false;
    private boolean bLS=false;
    private boolean bD=false;
    private boolean bx=false;
    private boolean by=false;
    private boolean br=false;
    private boolean bCh=false;
    private boolean bfileOut=false;


    public XMLModelReader() throws SAXException {
        xr = XMLReaderFactory.createXMLReader();
        xr.setContentHandler(this);
        xr.setErrorHandler(new XMLerrorHandler(System.err));
        bs = new BiologicalSlab("Test");
        ip= new InParams();     
    }

    public void read(final String xmlFileName) throws FileNotFoundException, IOException, SAXException {
        FileReader fr = new FileReader(xmlFileName);
        xr.parse(new InputSource(fr));
    }

    @Override
    public void startDocument() {
        
    }

    @Override
    public void endDocument() {        
        
        /**********************/               
        ip.setDz(0.01);
        ip.setDr(0.01);
        ip.setNz(240);
        ip.setNr(500);
        ip.setNa(90);
        //ip.setDa(0.5*Math.PI/ip.getNa());
        ip.setDa(0.5*Utils.PI/ip.getNa());
        /**********************/
        this.ip.setLayers(this.bs);

       
        this.ip.setSourcesPositions(sourcesPositions);
        this.ip.setDetectorsPositions(detectorsPositions);
        
        System.out.println(this.ip.getBiologicalSlab().toString());
        System.out.println(ip.toString());
    }

    @Override
    public void startElement(String uri, String name, String qname, Attributes atts) {
        if (name.equalsIgnoreCase("MODEL")) {
            this.bs.setName(atts.getValue(0));
        }
        if (name.equalsIgnoreCase("NABOVE")) {
            bnabove = true;
        }
        if (name.equalsIgnoreCase("NBELOW")) {
            bnbelow = true;
        }
        if (name.equalsIgnoreCase("ORGAN")) {
            this.bs.addSlice(new Organ(atts.getValue(0)));
        }
        if (name.equalsIgnoreCase("TISSUE")) {
            ((Organ) this.bs.getSlice(this.bs.getNumSlices() - 1)).addTissue(new Tissue(atts.getValue(0)));
        }
        if (name.equalsIgnoreCase("LAYER")) {
            tempLayer=new HomogeneousLayer(atts.getValue(0));
            //((Organ) this.bs.getSlice(this.bs.getNumSlices() - 1)).getLastTissue().addLayer(new HomogeneousLayer(atts.getValue(0)));
        }
        if (name.equalsIgnoreCase("N")) {
            bn = true;
        }
        if (name.equalsIgnoreCase("MUA")) {
            bmua = true;
        }
        if (name.equalsIgnoreCase("MUS")) {
            bmus = true;
        }
        if (name.equalsIgnoreCase("G")) {
            bg = true;
        }
        if (name.equalsIgnoreCase("D")) {
            bthickness = true;
        }
        if (name.equalsIgnoreCase("DELTAX")){
            bdeltax=true;
        }
        if (name.equalsIgnoreCase("DELTAY")){
            bdeltay=true;
        }
        if (name.equalsIgnoreCase("DELTAZ")){
            bdeltaz=true;
        }
        if (name.equalsIgnoreCase("NX")){
            bnx=true;
        }
        if (name.equalsIgnoreCase("Ny")){
            bny=true;
        }
        if (name.equalsIgnoreCase("Nz")){
            bnz=true;
        }
        if (name.equalsIgnoreCase("photons")){
            bphotons=true;
        }
        if (name.equalsIgnoreCase("lightsource")){
            bLS=true;
            tempLS=new LightSource(Integer.parseInt(atts.getValue(0)));
        }
        if (name.equalsIgnoreCase("x")){
            bx=true;
        }
        if (name.equalsIgnoreCase("y")){
            by=true;
        }
        if (name.equalsIgnoreCase("detector")){
            bD=true;
            tempD=new Detector(Integer.parseInt(atts.getValue(0)));
        }
        if (name.equalsIgnoreCase("r")){
            br=true;
        }
        if (name.equalsIgnoreCase("Chanel")){
            bCh=true;
        }

        if (name.equalsIgnoreCase("fileOut")){
            bfileOut=true;
        }       

    }

    @Override
    public void endElement(String uri, String name, String qName) {
        if (name.equalsIgnoreCase("LAYER")) {
            ((Organ) this.bs.getSlice(this.bs.getNumSlices() - 1)).getLastTissue().addLayer(tempLayer);
            tempLayer=null;
        }
        if (name.equalsIgnoreCase("lightsource")) {
            sourcesPositions.add(tempLS);
            tempLS=null;
        }
        if (name.equalsIgnoreCase("detector")) {
            detectorsPositions.add(tempD);
            tempD=null;
        }
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        String val = new String(ch, start, length);
        if (bmodel) {
            this.bs.setName(val);
            bmodel = false;
        }
        if (bnabove) {
            this.bs.setAbove(new HomogeneousLayer("Air", Double.parseDouble(val), 0, 0, 0, 0));
            bnabove = false;
        }
        if (bnbelow) {
            this.bs.setBelow(new HomogeneousLayer("Air", Double.parseDouble(val), 0, 0, 0, 0));
            bnbelow = false;
        }
        if (bn) {
            //((Organ) this.bs.getSlice(this.bs.getNumSlices() - 1)).getLastTissue().getLastLayer().setRefractiveIndex(null, Double.parseDouble(val));
            tempLayer.setRefractiveIndex(null, Double.parseDouble(val));
            bn = false;
        }
        if (bmua) {
            //((Organ) this.bs.getSlice(this.bs.getNumSlices() - 1)).getLastTissue().getLastLayer().setAbsorption(null, Double.parseDouble(val));
            tempLayer.setAbsorption(null, Double.parseDouble(val));
            bmua = false;
        }
        if (bmus) {
            //((Organ) this.bs.getSlice(this.bs.getNumSlices() - 1)).getLastTissue().getLastLayer().setScattering(null, Double.parseDouble(val));
            tempLayer.setScattering(null, Double.parseDouble(val));
            bmus = false;
        }
        if (bg) {
            //((Organ) this.bs.getSlice(this.bs.getNumSlices() - 1)).getLastTissue().getLastLayer().setAnisotropy(null, Double.parseDouble(val));
            tempLayer.setAnisotropy(null, Double.parseDouble(val));
            bg = false;
        }
        if (bthickness) {
            //((Organ) this.bs.getSlice(this.bs.getNumSlices() - 1)).getLastTissue().getLastLayer().setThickness(Double.parseDouble(val));
            tempLayer.setThickness(Double.parseDouble(val));
            bthickness = false;
        }
        if (bdeltax){
            this.ip.setDx1(Double.parseDouble(val));
            bdeltax=false;
        }
        if (bdeltay){
            this.ip.setDy1(Double.parseDouble(val));
            bdeltay=false;
        }
        if (bdeltaz){
            this.ip.setDz1(Double.parseDouble(val));
            bdeltaz=false;
        }
        if (bnx){
            this.ip.setNx1(Integer.parseInt(val));
            bnx=false;
        }
        if (bny){
            this.ip.setNy1(Integer.parseInt(val));
            bny=false;
        }
        if (bnz){
            this.ip.setNz1(Integer.parseInt(val));
            bnz=false;
        }
        if (bphotons){
            this.ip.setNum_photons(Long.parseLong(val));
            bphotons=false;
        }
        if(bx){
            if (bLS)
                this.tempLS.getPosition().setX(Double.parseDouble(val));
            if (bD)
                this.tempD.getPosition().setX(Double.parseDouble(val));
            bx=false;
        }
        if(by){
            if (bLS)
                this.tempLS.getPosition().setY(Double.parseDouble(val));
            if (bD)
                this.tempD.getPosition().setY(Double.parseDouble(val));
            by=false;
            bLS=false;
            bD=false;
        }
        if(br){
            this.tempD.setRadious(Double.parseDouble(val));
            br=false;
        }
        if(bCh){
            this.tempD.setSourceId(Integer.parseInt(val));
            bCh=false;
        }
        if(bfileOut){
            this.ip.setOut_fname(val);            
            bfileOut=false;
        }
        
    }

     public InParams getInparams() {
        return this.ip;
    }
}
