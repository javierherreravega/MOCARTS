package Utility;


/** 
 *  A wavelength in [nm].
 *  Ideally this should be defined as a "basic" type.
 */
public class Wavelength {
  /* {author=Bibiana Cuervo Soto, version=0.5, since=0.5}*/


  public double w;

  /** 
   *  The units in which the wavelength is expressed. Reserved for future use.
   */
  private String units;

  /** 
   *  Class constructor
   */
  public Wavelength(double w) {
      this.w=w;
  }

  /** 
   *  Gets the length of the wavelength in [nm]
   */
  public double getValue() {
  return w;
  }

  /** 
   *  Sets the length of the wavelength in [nm]
   */
  public void setValue(double w) {
      this.w=w;
  }

}