package functionality;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import objects.Node;
import objects.Summary;

public class Operators {

	public static void operatorUnionByLevels(Summary<Node> summary){
		
		Collection<Node> aux = new ArrayList<Node>();
		while(!summary.isEmpty()){
			Node node = summary.iterator().next();
			List<Node> matches = summary.stream().filter(n -> n.getName().equals(node.getName())) 
												.filter(n-> n.getLevel()==node.getLevel()) 
												.filter(n -> n.getBooleanValue()==node.getBooleanValue()) 
												.collect(Collectors.toList());
			if(summary.getNumberOfSamples()==1)
				matches.stream().forEach(n->node.uniteTest(n));   
			else
				matches.stream().forEach(n->node.uniteWeightedTest(n));
			node.setWeight(matches.stream().mapToDouble(Node::getWeight).sum());
			summary.removeAll(matches);
			aux.add(node);
		}
		summary.addAll(aux);
		summary.setLeveled(true);
	}
	
	public static void operatorNormalUnion(Summary<Node> summary){
		
		Collection<Node> aux = new ArrayList<Node>();
		while(!summary.isEmpty()){
			Node node = summary.iterator().next();
			List<Node> matches= summary.stream().filter(n -> n.getName().equals(node.getName()))
												.filter(n -> n.getBooleanValue()==node.getBooleanValue())		
												.collect(Collectors.toList());
			if(summary.getNumberOfSamples()==1)
				matches.stream().forEach(n->node.uniteTest(n));
			else
				matches.stream().forEach(n->node.uniteWeightedTest(n));
			node.setWeight(matches.stream().mapToDouble(Node::getWeight).sum());
			summary.removeAll(matches);
			aux.add(node);
		}
		summary.addAll(aux);
		summary.setLeveled(false);

	}
	
	public static void operatorUnionByTypes(Summary<Node> summary){
		Collection<Node> aux = new ArrayList<Node>();
		while(!summary.isEmpty()){
			Node node = summary.iterator().next();
			List<Node> matches = summary.stream().filter(n -> n.getType()==node.getType())
												 .filter(n -> n.getBooleanValue()==node.getBooleanValue())
												 .collect(Collectors.toList());
			if(summary.getNumberOfSamples()==1)
				matches.stream().forEach(n->node.uniteTest(n));   
			else
				matches.stream().forEach(n->node.uniteWeightedTest(n));
			node.setWeight(matches.stream().mapToDouble(Node::getWeight).sum());
			summary.removeAll(matches);
			node.turnToCluster();
			aux.add(node);
		}
		summary.addAll(aux);
		summary.setLeveled(false);

	}
	
	public static void operatorReductionByWeights(Summary<Node> summary, int threshold){
		
		Collection<Node> aux = new ArrayList<Node>(summary.stream().filter(n->n.getWeight()<=threshold).collect(Collectors.toList()));
		
		double loss = aux.stream().mapToDouble(Node::getWeight).sum();
		double total = summary.stream().mapToDouble(Node::getWeight).sum();
				
		summary.removeAll(aux);
		summary.updateIntegrity(loss/total);
	}
	
	
	public static void operatorReductionByScaling(Summary<Node> summary, int factor){
		
		double total = summary.stream().mapToDouble(Node::getWeight).sum();
		Collection<Node> aux = new ArrayList<Node>(summary.stream().filter(n->n.getWeight()*100/total <1).collect(Collectors.toList()));
		double loss = aux.stream().mapToDouble(Node::getWeight).sum();
		
		summary.removeAll(aux);
		summary.stream().forEach(n->n.setWeight((factor*n.getWeight()*100)/total));
		summary.updateIntegrity(loss/total);
	}
	
	public static void operatorReductionByTypes(Summary<Node> summary, int... types){
		
		Collection<Node> aux = new ArrayList<Node>();
		for(int i: types)
			aux.addAll(summary.stream().filter(n->n.getType()==i).collect(Collectors.toList()));

		double retain = aux.stream().mapToDouble(Node::getWeight).sum();
		double total = summary.stream().mapToDouble(Node::getWeight).sum();
		
		summary.retainAll(aux);
		summary.updateIntegrity((total-retain)/total);
	}
	
	public static void operatorReductionByTypes2(Summary<Node> summary, Collection<String> types){
		
		Collection<Node> aux = new ArrayList<Node>();
		for(String i: types)
			aux.addAll(summary.stream().filter(n->n.checkType(i)).collect(Collectors.toList()));

		double retain = aux.stream().mapToDouble(Node::getWeight).sum();
		double total = summary.stream().mapToDouble(Node::getWeight).sum();
		
		summary.retainAll(aux);
		summary.updateIntegrity((total-retain)/total);
	}
	
	
	
	public static Summary<Node> operatorSummaryUnion(Collection<Summary<Node>> summaries){
		
		Summary<Node> aux = new Summary<Node>(summaries.iterator().next().getGene(), summaries.iterator().next().getOutcome());
		
		summaries.stream().forEach( new Consumer<Summary<Node>>(){

			@Override
			public void accept(Summary<Node> s) {
				aux.addAll(s);
				aux.addGene(s.getGene());
			}
			
		});
		
		double integrity = summaries.stream().mapToDouble(Summary::getIntegrity).sum();
		int n = summaries.size();
		aux.setNumberOfSamples(n);
		aux.setIntegrity(integrity/n);
		
		return aux;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
