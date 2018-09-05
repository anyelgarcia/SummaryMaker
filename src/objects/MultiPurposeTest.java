package objects;

public class MultiPurposeTest extends Test{

	public MultiPurposeTest(String value){
		this.value = value;
	}
	
	public MultiPurposeTest(MultiPurposeTest t){
		this.value = t.getValue();
	}
}
