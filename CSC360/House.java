public class House implements Cloneable, Comparable<House> {
  private int id;
  private double area;
  private java.util.Date whenBuilt;
  
  public House(int id, double area) {
    this.id = id;
    this.area = area;
    whenBuilt = new java.util.Date();
  }
  
  public int getId() {
    return id;
  }
  
  public double getArea() {
    return area;
  }

  public java.util.Date getWhenBuilt() {
    return whenBuilt;
  }

  @Override
  public Object clone() throws CloneNotSupportedException { // This version does a shallow copy
    return super.clone();
  }
  
  /*public Object clone() { // This version also does a shallow copy without the throws declaration
    try {
      return super.clone();
    }
    catch (CloneNotSupportedException ex) {
      return null;
    }
  }*/
  
  /*public Object clone() throws CloneNotSupportedException { // This version does a deep copy
    // Perform a shallow copy
	House houseClone = (House)super.clone();
	// Deep copy on whenbuilt
	houseClone.whenBuilt = (java.util.Date)(whenBuilt.clone());
	return houseClone;    
  }*/
  
  /*public Object clone() { // This version does a deep copy without the throws declaration
    try {
      // Perform a shallow copy
      House houseClone = (House)super.clone();
      // Deep copy on whenbuilt
      houseClone.whenBuilt = (java.util.Date)whenBuilt.clone();
      return houseClone;
    }
    catch (CloneNotSupportedException ex) {
      return null;
    }
  }*/
  
  @Override // Implement the compareTo method defined in Comparable
  public int compareTo(House o) {
    if (area > o.area)
      return 1;
    else if (area < o.area)
      return -1;
    else
      return 0;
  }  
}
