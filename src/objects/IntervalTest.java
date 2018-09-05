package objects;

public class IntervalTest extends Test{
	
	private Double min;
	private Double max;
	
	public IntervalTest(double min, double max){
		this.min = min;
		this.max = max;
		this.value = "[" + min + "," + max + "]";
	}
	
	public IntervalTest(double number, boolean less){
		if(less){
			min = number;
			max = null;
			this.value = ">" + min;
		}else{
			min = null;
			max = number;
			this.value = "<=" + max;
		}
	}
	
	public IntervalTest(IntervalTest t){
		min = t.getMin();
		max = t.getMax();
		value = t.getValue();
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}
	
	public void combine(Test test) throws UnionError{
		if(test instanceof IntervalTest){
			IntervalTest t = (IntervalTest) test;
			if(min==null&&t.getMin()==null){
				max = Math.min(max, t.getMax());
				value = "<=" + max;
			}else if(max==null&&t.getMax()==null){
				min = Math.max(min, t.getMin());
				value = ">" + min;
			}else{
				if(max==null)max = t.getMax();
				else if(t.getMax()==null);
				else max = Math.min(max, t.getMax());
				if(min==null)min = t.getMin();
				else if(t.getMin()==null);
				else min = Math.max(min, t.getMin());
				
				value = "[" + min +"," + max +"]";
			}
		}else
			throw new UnionError("Error when combining tests. You can't combine tests of different types: " + value + "" + test.getValue() + ". "); 
 	}
	
	public void weightedCombine(Test test, double w1, double w2) throws UnionError{
		if(test instanceof IntervalTest){
			IntervalTest t = (IntervalTest) test;
			if(min==null&&t.getMin()==null){
				max = max*w1 + t.getMax()*w2;
				value = "<=" + max;
			}else if(max==null&&t.getMax()==null){
				min = min*w1 + t.getMin()*w2;
				value = ">" + min;
			}else{
				if(max==null)max = t.getMax();
				else if(t.getMax()==null);
				else max = max*w1 + t.getMax()*w2;
				if(min==null)min = t.getMin();
				else if(t.getMin()==null);
				else min = min*w1 +  t.getMin()*w2;
				
				value = "[" + min +"," + max +"]";
			}
		}else
			throw new UnionError("Error when combining tests. You can't combine tests of different types: " + value + "" + test.getValue() + ". "); 
	}
	
	

}
