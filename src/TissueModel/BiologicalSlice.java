package TissueModel;

/** 
 *  A BiologicalSlice is a generalization of any type of cell arrangement;
 * may it be an organ, a tissue, a cell layer, etc
 */
public abstract class BiologicalSlice {
  

  private String name;

  public String getName() {
  return this.name;
  }

  public void setName(String name) {
      this.name=name;
  }

  /** 
   *  Returns a string that represents the current object.
   */
  public abstract String toString();

  /** 
   *  Outputs the object to an XML file.
   *  Returns the result of the writing operation.
   */
  public abstract Integer write2XML(String filename);

}