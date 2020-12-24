import java.util.ArrayList;

public class EncapObject implements Cloneable{

	private Object Obj ;
	private String Id ;
	
	public EncapObject( Object obj , String id ) {
		Obj = obj ;
		Id = id ;
	}
	
	@Override
	public EncapObject clone() {
		
		return new EncapObject( Obj, Id ) ;
		
	}
	
	public void UpdateObject( Object obj ) {
		Obj = obj ;
	}
	
	public Object GetObject() {
		return Obj ;
	}
	
	public String GetId() {
		return Id ;
	}

}