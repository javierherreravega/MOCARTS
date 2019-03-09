========================
Monte Carlo Radiation Transport Simulator 
========================

Monte Carlo Radiation Transport Simulator (MOCARTS) is a light weight radiator transport simulator inspired on Oregon Medical Laser Center's mcml, that eases some of the interface limitations of mcml. In particular, (i) it provides a flexible way to describe biological tissues in a more intuitive and reusable manner, (ii) it provides support to define and simulate multiple light sources and detectors that can be arranged in complex sensing geometries, and (iii) the formatting and organization of the input and output data is redefined over a more human-readable XML file format.

This simualtor has been developed in Java to provide portability accross plataforms. The project was developed in NetBeans IDE 8.0.2 and JDK 1.8.

=========================
Running MOCARTS
=========================

To run a simulation with MOCARTS you need to define a xml input file whit the info about: the tissue to simulate specified by their optical properties, the configuration of the simulation output grid (where absorption, reflectance and transmitance is stored) and the configuration of light sources and detectors (positions and channels). An example of a xml file is given in "\xmlFileExample\testSimulation.xml".

The executable file is located in ".\dits\mocarts.jar". The command line to run a simulation is as follows:

java -jar mocarts.jar inputFile.xml


The results will be stored in text file definied in the xml configuration file.