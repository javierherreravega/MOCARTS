/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation;


public class Fresnel extends ReflectanceModel{
    
    static double COSZERO = (1.0-1.0E-12);	
  /* cosine of about 1e-6 rad. */

    static double COS90D =1.0E-6;	
  /* cosine of about 1.57 - 1e-6 rad. */
    
    /***********************************************************
 *	Compute the Fresnel reflectance.
 *
 *	Make sure that the cosine of the incident angle a1
 *	is positive, and the case when the angle is greater 
 *	than the critical angle is ruled out.
 *
 * 	Avoid trigonometric function operations as much as
 *	possible, because they are computation-intensive.
 ****/
    @Override
public  double[] Reflectance(double n1,	/* incident refractive index.*/
		double n2,	/* transmit refractive index.*/
		double ca1)	/* cosine of the incident angle. 0<a1<90 degrees. */
{
    double[] res=new double[2];  /*res[1] pointer to the */
				/* cosine of the transmission */
				/* angle. a2>0. */
    
                                /*res[0] value returned by the function*/

  double r;
  
  if(n1==n2) {			  	/** matched boundary. **/
    res[1] = ca1;
    r = 0.0;
  }
  else if(ca1>COSZERO) {	/** normal incident. **/
    res[1] = ca1;
    r = (n2-n1)/(n2+n1);
    r *= r;
  }
  else if(ca1<COS90D)  {	/** very slant. **/
    res[1] = 0.0;
    r = 1.0;
  }
  else  {			  		/** general. **/
    double sa1, sa2;	
	  /* sine of the incident and transmission angles. */
    double ca2;
    
    sa1 = Math.sqrt(1-ca1*ca1);
    sa2 = n1*sa1/n2;
    if(sa2>=1.0) {	
	  /* double check for total internal reflection. */
      res[1] = 0.0;
      r = 1.0;
    }
    else  {
      double cap, cam;	/* cosines of the sum ap or */
						/* difference am of the two */
						/* angles. ap = a1+a2 */
						/* am = a1 - a2. */
      double sap, sam;	/* sines. */
      
      res[1] = ca2 = Math.sqrt(1-sa2*sa2);
      
      cap = ca1*ca2 - sa1*sa2; /* c+ = cc - ss. */
      cam = ca1*ca2 + sa1*sa2; /* c- = cc + ss. */
      sap = sa1*ca2 + ca1*sa2; /* s+ = sc + cs. */
      sam = sa1*ca2 - ca1*sa2; /* s- = sc - cs. */
      r = 0.5*sam*sam*(cam*cam+cap*cap)/(sap*sap*cam*cam); 
		/* rearranged for speed. */
    }
  }
  res[0]=r;
  return(res);
}
    
}
