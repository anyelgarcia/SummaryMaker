package objects;

public class Test {
	protected String nodename;
	protected String value;
	
	
	public String toString(){
		return getNodename() + "" + getValue();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public void combine(Test t) throws UnionError{
		if(!t.getValue().equals(value))
			throw new UnionError("Error when combining two tests: " + value + "" + t.getValue() + ". "); 
	}
}
