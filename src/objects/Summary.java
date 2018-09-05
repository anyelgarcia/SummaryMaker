package objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Summary<T> implements Collection<T>, Comparable<Summary<T>>{

	private double integrity;
	private String outcome;
	private String gene;
	private int numberOfSamples;
	private boolean leveled;
	private Collection<T> nodes;
	
	public Summary(String gene, String outcome){
		integrity = 1;
		this.outcome = outcome;
		this.gene = gene;
		numberOfSamples = 1;
		nodes = new ArrayList<T>();
		leveled=false;
	}
	@Override
	public int size() {
		return nodes.size();
	}

	@Override
	public boolean isEmpty() {
		return nodes.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return nodes.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return nodes.iterator();
	}

	@Override
	public Object[] toArray() {
		return nodes.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return nodes.toArray(a);
	}
	
	@Override
	public boolean add(T e) {
		return nodes.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return nodes.remove(o);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return nodes.addAll(c);
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		return nodes.containsAll(c);
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		return nodes.removeAll(c);
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		return nodes.retainAll(c);
	}


	@Override
	public void clear() {
		nodes.clear();
	}
	
	public void updateIntegrity(double factor) {
		integrity *= (1 - factor);
	}
	
	public double getIntegrity() {
		return integrity;
	}
	
	public void setIntegrity(double i){
		integrity = i;
	}
	
	public String getOutcome() {
		return outcome;
	}
	
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	
	public int getNumberOfSamples() {
		return numberOfSamples;
	}
	
	public void setNumberOfSamples(int numberOfSamples) {
		this.numberOfSamples = numberOfSamples;
	}
	
	public String getGene() {
		return gene;
	}
	
	public void setGene(String gene) {
		this.gene = gene;
	}
	
	@Override
	public int compareTo(Summary<T> o) {
		return gene.compareTo(o.getGene());
	}
	
	public String toString(){
		return gene;
	}
	
	public void addGene(String str){
		if(!gene.contains(str))
			gene = gene + " - " + str;
	}
	
	public boolean isLeveled() {
		return leveled;
	}
	
	public void setLeveled(boolean leveled) {
		this.leveled = leveled;
	}
	
	

}
