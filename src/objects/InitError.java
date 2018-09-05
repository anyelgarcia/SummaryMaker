package objects;

@SuppressWarnings("serial")
public class InitError extends Exception {
	String msg;
	public InitError(String r){
		msg = r;
	}
	public String toString(){
		return msg;
	}
}
