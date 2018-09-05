package objects;

public class BooleanTest extends Test{
	public BooleanTest(boolean check){
		if(check) this.value = "=Yes";
		else this.value = "=No";
	}
	
	public BooleanTest(BooleanTest t){
		this.value = t.getValue();
	}
}
