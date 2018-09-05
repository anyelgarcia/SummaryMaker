package objects;

import java.util.ArrayList;
import java.util.Collection;

public class Node {
	
	private int level;
	private double weight;
	private String color;
	private String name;
	private Collection<Test> tests;
	private int type;
	
	private String metadata;
	private String metadataFusion;
	
	
	public Node(String label, int level, Test test) throws InitError {
		
		weight = 1;
		tests = new ArrayList<Test>();
		if(test instanceof BooleanTest) tests.add(new BooleanTest((BooleanTest)test));
		if(test instanceof IntervalTest) tests.add(new IntervalTest((IntervalTest)test));
		if(test instanceof MultiPurposeTest) tests.add(new MultiPurposeTest((MultiPurposeTest)test));
		
		this.level = level;
		
		classify(label);
		tests.iterator().next().setNodename(name);
		this.metadata = "";
		this.metadataFusion = "";
	}
	
	public double getWeight() {
		return weight;
	}
	
	public int getWeightInt() {
		return (int)weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public boolean getBooleanValue(){
		return tests.iterator().next().getValue().equals("=Yes");
	}

	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getMetadata() {
		return metadata;
	}
	
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	
	public String getMetadataFusion() {
		return metadataFusion;
	}

	public void setMetadataFusion(String metadataFusion) {
		this.metadataFusion = metadataFusion;
	}

	public String getLabel(){
		if(tests.size()==1)
			return tests.iterator().next().toString();
		String str = name + ": ";
		for(Test t: tests)
			str += "  " + t;
		return str;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Test> getTests() {
		return tests;
	}

	public void addTest(Test test) {
		tests.add(test);
	}
	
	public void removeTest(Test test){
		tests.remove(test);
	}

	public int getTipo() {
		return type;
	}

	public void setTipo(int tipo) {
		this.type = tipo;
	}

	private void classify(String lbl) throws InitError {
		String begin = lbl.substring(0, 5);
		
		switch(begin){
			case "Ranke":
				name = "MM";
				clasificacionCentroides(0,lbl.substring(22));
				break;
			case "AdjSp":
				name = "Adj";
				clasificacionCentroides(1,lbl.substring(14));
				break;
			case "ExprS":
				name = "ES";
				clasificacionCentroides(2,lbl.substring(12));
				break;
			case "ExACp":
				name = "ExAC";
				switch(lbl.charAt(5)){
					case 'R':
						type = 101;
						name+="Rec";
						color="#009090";
						break;
					case 'L':
						type = 102;
						name+="LI";
						color="#00FFFF";
						break;
					case 'M':
						type = 103;
						name+="Miss";
						color="#000090";
						break;
					case 'N':
						type = 104;
						name+="Null";
						color="#0000FF";
						break;
					default:
						throw new InitError("Error en exac " +  begin+ " "+ lbl.charAt(5));
				}
				break;
			case "GeneL":
				type = 80;
				name="GeneL";
				color="#F064AA";
				break;
			case "GCcon":
				type = 81;
				name="GCcon";
				color="#F064AA";
				break;
			case "alt3E":
				type = 82;
				name="alt3";
				color="#F064AA";
				break;
			case "alt5E":
				type = 83;
				name="alt5";
				color="#F064AA";
				break;
			case "alt3.":
				type = 84;
				name="alt3.5";
				color="#F064AA";
				break;
			case "NumJu":
				type = 85;
				name="NJunc";
				color="#F064AA";
				break;
			case "Intro":
				type = 86;
				name="IntroL";
				color="#F064AA";
				break;
			case "Trans":
				type = 87;
				name="TransC";
				color="#F064AA";
				break;
			case "Disea":
				type = -1;
				name="Disease";
				color="#000000";
				break;
			case "No Di":
				type = -2;
				name="No Disease";
				color="#000000";
				break;
			case "Count":
				if(lbl.substring(6,7).equals("O")){
					type = 88;
					name="COver";
					color="#F064AA";
					break;
				}
				if(lbl.substring(6,7).equals("P")){
					type = 89;
					name="CPOver";
					color="#F064AA";
					break;
				}
				throw new InitError("Count ?" + lbl );
			default:
				throw new InitError("NoMatch: " + begin +" " + lbl);
		}
	}

	private void clasificacionCentroides(int op, String substring) throws InitError {
		int n;
		switch(substring){
			case "AdiposeSub":
				n=1;
				name+="AdipSub";
				break;
			case "AdiposeVisceral":
				n=1;
				name+="AdipVis";
				break;
			case "Amygdala":
				n=2;
				name+="Amygd";
				break;
			case "AntCingCortex":
				n=2;
				name+="ACCortex";
				break;
			case "Caudate":
				name+="Caudate";
				n=2;
				break;
			case "Cerebellum":
				name+="Cerebe";
				n=2;
				break;
			case "CerebHemisphere":
				name+="CerebHe";
				n=2;
				break;
			case "Cortex":
				name+="Cortex";
				n=2;
				break;
			case "FCortex":
				name+="FCortex";
				n=2;
				break;
			case "Hippocampus":
				name+="Hippoca";
				n=2;
				break;
			case "Hypothalamus":
				name+="Hypotha";
				n=2;
				break;
			case "NucAccumbens":
				name+="NucAcc";
				n=2;
				break;
			case "Putamen":
				name+="Putamen";
				n=2;
				break;
			case "Spinalcord":
				name+="SpinCord";
				n=2;
				break;
			case "Substantianigra":
				name+="Substnigra";
				n=2;
				break;
			case "ArteryAorta":
				name+="ArtAort";
				n=3;
				break;
			case "ArteryTibial":
				name+="ArtTibi";
				n=3;
				break;
			case "ArteryCoronary":
				name+="ArtCoro";
				n=3;
				break;
			case "HeartAtrialApp":
				name+="HeartAA";
				n=3;
				break;
			case "HeartLeftVent":
				name+="HeartLV";
				n=3;
				break;
			case "WholeBlood":
				name+="WBlood";
				n=3;
				break;
			case "ColonSigmoid":
				name+="ColonSig";
				n=4;
				break;
			case "SmallIntestine":
				name+="SmIntest";
				n=4;
				break;
			case "Stomach":
				name+="Stomac";
				n=4;
				break;
			case "ColonTransverse":
				name+="ColonTr";
				n=4;
				break;
			case "EsophMucosa":
				name+="EsophMuc";
				n=4;
				break;
			case "EsophMuscularis":
				name+="EsophMusc";
				n=4;
				break;
			case "EsophGastJunction":
				name+="EsophGJ";
				n=4;
				break;
			case "SkinLowerLeg":
				name+="SkinLeg";
				n=5;
				break;
			case "SkinSuprapubic":
				name+="SkinPub";
				n=5;
				break;
			case "AdrenalGland":
				type = 18 +op*17;
				name+="AdrnGland";
				n=6;
				break;
			case "Breast":
				type = 19+op*17;;
				name+="Breast";
				n=6;
				break;
			case "CellsFibroblasts":
				type = 20+op*17;;
				name+="CellsFib";
				n=6;
				break;
			case "CellsFirbroblasts"://Este lo dejo igual porque creo que es lo miso
				type = 20+op*17;;
				name+="CellsFib";
				n=6;
				break;
			case "CellsLymphocytes":
				type = 21+op*17;;
				name+="CellsLym";
				n=6;
				break;
			case "Liver":
				type = 22+op*17;;
				name+="Liver";
				n=6;
				break;
			case "Lung":
				type = 23+op*17;;
				name+="Lung";
				n=6;
				break;
			case "MuscleSkeletal":
				type = 24+op*17;;
				name+="MuscSke";
				n=6;
				break;
			case "NerveTibial":
				type = 25+op*17;;
				name+="NervTibi";
				n=6;
				break;
			case "Ovary":
				type = 26+op*17;;
				name+="Ovary";
				n=6;
				break;
			case "Pancreas":
				type = 27+op*17;;
				name+="Pancreas";
				n=6;
				break;
			case "Pituitary":
				type = 28+op*17;;
				name+="Pituit";
				n=6;
				break;
			case "Prostate":
				type = 29+op*17;;
				name+="Prostat";
				n=6;
				break;
			case "Spleen":
				type = 30+op*17;;
				name+="Spleen";
				n=6;
				break;
			case "Testis":
				type = 31+op*17;;
				name+="Testis";
				n=6;
				break;
			case "Thyroid":
				type = 32+op*17;;
				name+="Thyroid";
				n=6;
				break;
			case "Uterus":
				type = 33+op*17;;
				name+="Uterus";
				n=6;
				break;
			case "Vagina":
				type = 34+op*17;;
				name+="Vagina";
				n=6;
				break;
			default:
				throw new InitError(name + " ? " + substring);
		}
		
		switch(n+6*op){
			case 18:
				color = "#CCCCCC";
				break;
			case 1:
				type = 3;
				color = "#AAAA00";
				break;
			case 2:
				type = 4;
				color = "#500050";
				break;
			case 3:
				type = 5;
				color = "#500000";
				break;
			case 4:
				type = 6;
				color = "#005000";
				break;
			case 5:
				type = 7;
				color = "#AA3C00";
				break;
			case 6:
				color = "#505050";
				break;
			case 7:
				type = 8;
				color = "#F0F000";
				break;
			case 8:
				type = 9;
				color = "#9600C8";
				break;
			case 9:
				type = 10;
				color = "#F00000";
				break;
			case 10:
				type = 11;
				color = "#00F000";
				break;
			case 11:
				type = 12;
				color = "#F06400";
				break;
			case 12:
				color = "#969696";
				break;
			case 13:
				type = 13;
				color = "#FFFF64";
				break;
			case 14:
				type = 14;
				color = "#C864FA";
				break;
			case 15:
				type = 15;
				color = "#FF6464";
				break;
			case 16:
				type = 16;
				color = "#64FF64";
				break;
			case 17:
				type = 17;
				color = "#FFAF5A";
				break;
			default:
				throw new InitError("PUTAMIERDACORORWMRNFBWOJF D");
		}
		
		
	}
	
	public String toString(){
		int n = level+1;
		return name + (name+1) + "\t" + color + "\t"  + weight + "\t" + n + "\t" + type;
		
	}

	public String toCytoFormat(){
		return getLabel() + "\t" + color + "\t"  + weight + "\t" +level + "\t" + type;
		
	}
	
	public void addMetadata(Node node) {
		metadata += "NodeUnion with " + node.name + "\nNew metadata: " + node.metadata;
		
	}
	
	public void addWeight(int weight2) {
		weight+=weight2;
	}

	public boolean ableToClassify(){
		if(type<=17 && type>=1) return true;
		return false;
	}
	
	public void initFusion(){
		metadata ="Nodes in the class:" + name + " con peso "+weight;
	}
	public void addFusion(Node node){
		metadata+= ", " + node.getLabel() + " con peso " + weight;
	}
	public boolean almostEquals(Node n){
		if(n.getName().equals(name) && n.getTests().iterator().next().getValue().equals(tests.iterator().next().value)) 
			return true;
		return false;
	}

	public void uniteTest(Node n) {
		Collection<Test> aux = new ArrayList<Test>(n.getTests());
		for(Test tn: n.getTests())
			for(Test test: tests)
				if(test.getNodename().equals(tn.nodename)){
					try {
						test.combine(tn);
					} catch (UnionError e) {
						System.out.println(e);
					}
					aux.remove(tn);
				}
		tests.addAll(aux);
	}
	
	public void uniteWeightedTest(Node n) {
		Collection<Test> aux = new ArrayList<Test>(n.getTests());
		for(Test tn: n.getTests())
			for(Test test: tests)
				if(test.getNodename().equals(tn.nodename)){
					try {
						if(test instanceof IntervalTest){
							IntervalTest t = (IntervalTest) test;
							double w =  weight/(weight + n.getWeight());
							t.weightedCombine(tn, w, 1-w);
						}else{
							test.combine(tn);
						}
					} catch (UnionError e) {
						System.out.println(e);
					}
					aux.remove(tn);
				}
		tests.addAll(aux);
	}
	
	public boolean checkType(String t){
		switch(t){
			case "ExACp":
				return type>100&&type<105;
			case "MMAdipose":
				return type ==3;
			case "MMBrain":
				return type ==4;
			case "MMCirculatory":
				return type ==5;
			case "MMDigestive":
				return type ==6;
			case "MMSkin":
				return type ==7;
			case "AdjAdipose":
				return type ==8;
			case "AdjBrain":
				return type ==9;
			case "AdjCirculatory":
				return type ==10;
			case "AdjDigestive":
				return type ==11;
			case "AdjSkin":
				return type ==12;
			case "ESAdipose":
				return type ==13;
			case "ESBrain":
				return type ==14;
			case "ESCirculatory":
				return type ==15;
			case "ESDigestive":
				return type ==16;
			case "ESSkin":
				return type ==17;
			case "GeneStructure":
				return type>=80 && type<90;
			case "Others":
				return type>=18 && type <69;
			default:
				return false;
			
		}
		
	}
	
	public String getLabelLeveled(){
		return getLabel() + "(L" + level +")";
	}

	public void turnToCluster() {
		if(type>2 && type <18){
			name = "CL";
			if(type<8) name+= "MM";
			else if(type<13) name+= "Adj";
			else name+= "ES";

			int n = (type-3)%5;
			switch(n){
			case 0:
				name+= "Adipose";
				break;
			case 1:
				name+= "Brain";
				break;
			case 2:
				name+= "Circulatory";
				break;
			case 3:
				name+= "Digestive";
				break;
			case 4:
				name+= "Skin";
				break;
			}
		}
	}

}
