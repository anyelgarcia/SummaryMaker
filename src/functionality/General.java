package functionality;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import objects.InitError;
import objects.MultiPurposeTest;
import objects.Node;
import objects.Summary;

public class General {
	
	public static final Dimension PREF = new Dimension(543,346);
	public static final Dimension MAX = new Dimension(2^31-1,2^31-1);
	public static final Dimension MIN = new Dimension(543,346);
	public static final int NIMAGES = 10;
	public static final Font TTLS = new Font("Kristen ITC", Font.PLAIN, 60);
	public static final Font BTNS = new Font("Constantia", Font.BOLD, 17);
	public static final Font TXT = new Font("SimSun", Font.PLAIN, 20);
	public static final Font TXTxs = new Font("SimSun", Font.PLAIN, 15);
	public static final Color BLACK = new Color(0,0,0,150);
	public static final Color BLUE = new Color(0,0,150);
	public static final Color GREEN = new Color(0,150,0);
	public static final Color RED = new Color(150,0,0);
	public static final int SLIDERMAX = 10000;
	
	
	public static BufferedReader readFile(String filename) {
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return br;
	}

	public static void writeFileType1(File file, Summary<Node> summary) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		writer.println("label\tcolor\tweight\torder\tclass\tid\tto");
		Summary<Node> aux = new Summary<Node>(summary.getGene(), summary.getOutcome());
		aux.addAll(summary);
		for(int i=1; !aux.isEmpty(); i++){
			int j=i;
			List<Node> nodessizei = summary.stream().filter(n->n.getLevel()==j).collect(Collectors.toList());
			if(nodessizei.isEmpty())continue;
			Iterator<Node> it = nodessizei.iterator();
			Node initial = it.next();
			Node actual = initial;
			if(nodessizei.size() == 1){
				writer.println(initial.toCytoFormat() + "\t" + initial.getLabelLeveled());
			}else{
				while(it.hasNext()){
					Node next = it.next();
					writer.println(actual.toCytoFormat() + "\t" + actual.getLabelLeveled()+ "\t" +  next.getLabelLeveled());
					actual = next;
				}
				writer.println(actual.toCytoFormat() + "\t" + actual.getLabelLeveled()+ "\t" +  initial.getLabelLeveled());
			}
			aux.removeAll(nodessizei);
		}
		writer.close();
	}
	
	public static void writeFileType2(File file, Summary<Node> summary) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		Node n = null;
		try {
			n = new Node(summary.getOutcome(), 1, new MultiPurposeTest(""));
		} catch (InitError e1) {
			e1.printStackTrace();
		}
		n.setWeight(summary.stream().mapToDouble(Node::getWeight).sum()/3);
		
		writer.println("from\tcolor\tweight\torder\tclass\tto");
		for(Node node: summary)
			writer.println(node.toCytoFormat() +"\t"+ n.getLabel());
		writer.println(n.toCytoFormat());
		writer.close();
	}
}
