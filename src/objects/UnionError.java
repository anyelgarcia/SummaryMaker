package objects;

@SuppressWarnings("serial")
public class UnionError extends Exception {
	String msg;
	public UnionError(String r){
		msg = r;
	}
	public String toString(){
		return msg;
	}
}
