import java.util.Calendar;

public class TestHouse {
  public static void main(String[] args) throws CloneNotSupportedException {
    House house1 = new House(1, 1750.0);
    House house2 = (House) (house1.clone());
    System.out.println("house1: " + house1);
    System.out.println("house2: " + house2);
    System.out.println(house1 == house2);
    System.out.println("house1.getWhenBuilt() == house2.getWhenBuilt(): "
    	+ (house1.getWhenBuilt() == house2.getWhenBuilt()));
    System.out.println("house1.getWhenBuilt().equals(house2.getWhenBuilt()): "
    	+ house1.getWhenBuilt().equals(house2.getWhenBuilt()));
    System.out.println("house1.getWhenBuilt(): " + house1.getWhenBuilt());
    System.out.println("house2.getWhenBuilt(): " + house2.getWhenBuilt());
	System.out.println("Changing house1's whenBuilt to February.");
    house1.getWhenBuilt().setMonth(1);
    System.out.println("house1.getWhenBuilt(): " + house1.getWhenBuilt());
    System.out.println("house2.getWhenBuilt(): " + house2.getWhenBuilt());
    System.out.println("house1.getWhenBuilt().equals(house2.getWhenBuilt()): "
    	+ house1.getWhenBuilt().equals(house2.getWhenBuilt()));
  }
}
