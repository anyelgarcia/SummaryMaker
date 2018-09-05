package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

import frames.FrameBye;
import frames.FrameFilterType;
import frames.FrameFoundGenes;
import frames.FrameUnionDiagrams;
import frames.FrameMostOperators;
import frames.FrameRepeat;
import frames.FrameWelcome;
import frames.MyFrame;
import functionality.General;
import functionality.Operators;
import objects.BooleanTest;
import objects.InitError;
import objects.IntervalTest;
import objects.Node;
import objects.Summary;
import objects.Test;


public class Controller{

	private static Controller unicaInstanciaControlador; 
	
	private ArrayList<Summary<Node>> summaries;
	private int currentGene;
	
	private ArrayList<MyFrame> frames;
	private int currentFrame;
	
	private Summary<Node> result;
	
	public static Controller getUnicaInstancia() {
		if(unicaInstanciaControlador==null)
			unicaInstanciaControlador = new Controller();
		return unicaInstanciaControlador;
	}
	
	
	public Controller(){
		summaries = new ArrayList<Summary<Node>>();
		frames = new ArrayList<MyFrame>();
		
		currentGene = 0;
		currentFrame = 0;
		
		frames.add(new FrameWelcome());
		frames.add(new FrameFoundGenes());
		frames.add(new FrameUnionDiagrams());
		frames.add(new FrameMostOperators());
		frames.add(new FrameFilterType());
		frames.add(new FrameRepeat());
		frames.add(new FrameBye());
	}


	public void changeFrame(int change){
		frames.get(currentFrame).setVisible(false);
		currentFrame+=change;
		frames.get(currentFrame).setVisible(true);
		
		frames.get(currentFrame).update();
		
	}
	
	public void run(){
		frames.get(currentFrame).setVisible(true);
		try {
			Thread.sleep(3000);
			if(currentFrame==0&&!frames.isEmpty())
				frames.get(currentFrame).update();
		} catch (InterruptedException e) {
		}
	}

	public Object readRuleFile(String filename) {
		
		BufferedReader br = General.readFile(filename);
		String sCurrentLine = null;
		try {
			sCurrentLine = br.readLine();
			sCurrentLine = br.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String gene = "";
		Summary<Node> summaryDisease = null;
		while(sCurrentLine!=null && sCurrentLine.length()>2){
			try {
				sCurrentLine = sCurrentLine.replaceAll("\"", "");
				String[] atribs = sCurrentLine.split(",");
				if(!gene.equals(atribs[1])){
					if(!gene.isEmpty())
						summaries.add(summaryDisease);
					gene = atribs[1];
					summaryDisease = new Summary<Node>(gene,"Disease");
				}
				Test t = null;
				if(atribs[4].equals("=")){
					if(atribs[5].equals("Yes"))
						t = new BooleanTest(true);
					else if(atribs[5].equals("No"))
						t = new BooleanTest(true);
					else throw new InitError("Bad format. A boolean test must be =Yes or =No");							
				}else if(atribs[4].equals(">")){
					t = new IntervalTest(Double.valueOf(atribs[5]), true);
				}else if(atribs[4].equals("<=")){
					t = new IntervalTest(Double.valueOf(atribs[5]), false);
				}else throw new InitError("Bad format. A test must be either >, <= or =");
				
				Node node = new Node(atribs[3], Integer.valueOf(atribs[6]), t);
				if(atribs[8].equals("Disease")){
					summaryDisease.add(node);
				}
				
				sCurrentLine = br.readLine();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "There has been a problem with the input.", "IO Exception", JOptionPane.ERROR_MESSAGE);
				return null;
			} catch (InitError e) {
				JOptionPane.showMessageDialog(null, "There has been a problem with the format of the input.", "Format Exception", JOptionPane.ERROR_MESSAGE);
				return null;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "There has been an unknown problem with the format of the input.", "Unknown Exception", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		summaries.add(summaryDisease);
		
		return new Object();
	}


	public void clearSummaries() {
		summaries = new ArrayList<Summary<Node>>();
	}


	public int getCurrentGene() {
		return currentGene;
	}
	
	public String getCurrentGeneName() {
		return summaries.get(currentGene).getGene();
	}

	public List<String> getCurrentGenes() {
		return summaries.stream().map(Summary::getGene).collect(Collectors.toList()); 
	}

	public void closeAll() {
		
		frames.stream().forEach(f->f.setVisible(false));
		summaries.retainAll(new ArrayList<Summary<Node>>());
		frames.retainAll(new ArrayList<MyFrame>());
		
		currentGene = 0;
		currentFrame = 0;
		System.exit(0);
	}


	public ArrayList<Summary<Node>> getGenes() {
		return summaries;
	}


	public Summary<Node> getSummary() {
		return result;
	}


	public void applyOperatorDiagramUnion(ArrayList<Summary<Node>> selectedGenes) {
		result = Operators.operatorSummaryUnion(selectedGenes);
	}


	public void applyUnionOperator(int optionunion) {
		if(optionunion==0) Operators.operatorUnionByLevels(result);
		else if(optionunion==1) Operators.operatorNormalUnion(result);
		else if(optionunion==2) Operators.operatorUnionByTypes(result);
	}


	public void applyScalingOperator(int scale) {
		Operators.operatorReductionByScaling(result, scale);
	}


	public void applyPruningOperator(int threshold) {
		Operators.operatorReductionByWeights(result, threshold);
	}

	public void applyTypeFilterOperator(ArrayList<String> selectedTypes) {
		Operators.operatorReductionByTypes2(result, selectedTypes);
		
	}

	public void updateCurrentSummary() {
		result = summaries.get(currentGene);
	}

	public void writeFile(File file){
		General.writeFileType1(file, result);
	}
	
	public void writeFile2(File file) {
		General.writeFileType2(file, result);
	}


	public void updateGenesSaved() {
		ArrayList<Summary<Node>> aux = new ArrayList<Summary<Node>>();
		String genes_saved = result.getGene();
		summaries.stream().forEach(new Consumer<Summary<Node>>(){

			@Override
			public void accept(Summary<Node> s) {
				if(genes_saved.contains(s.getGene()))
					aux.add(s);
			}
			
		});
		summaries.removeAll(aux);
		if(summaries.isEmpty())
			currentGene = -1;
		else{
			currentGene = 0;
			updateCurrentSummary();
		}
		
	}


	public double getIntegrity() {
		return result.getIntegrity();
	}
}
