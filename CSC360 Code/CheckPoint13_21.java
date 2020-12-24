public class CheckPoint13_21 {
  public static void main(String[] args) {
	  Person[] persons = {new Person(3), new Person(4), new Person(2)};
	  java.util.Arrays.sort(persons);
  }
}

class Person {
	int id;
	
	public Person(int id) {
		this.id = id;
	}
	
	int compareTo(Person other) {
		return this.id - other.id;
	}
}