package labeling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class LabelingSystem {

	// Attribute
	private String targetFileName, ruleFileName, labeledFileName;
	
	// Working Variable
	private int labeledCount = 0;
	
	// Component
	private Vector<LabelTargetData> labelTargetDataVector = new Vector<LabelTargetData>();
	private Vector<String> labelingRuleVector = new Vector<String>(); // "A B" --> A가 포함되면 B 라벨을 붙인다.
	
	// Constructor
	public LabelingSystem(String targetFileName, String ruleFileName, String labeledFileName) {
		// Set Attribute
		this.targetFileName = targetFileName;
		this.ruleFileName = ruleFileName;
		this.labeledFileName = labeledFileName;
	}
	
	public void labeling() {
		this.readTargetFile();
		this.readRuleFile();
		this.applyAllRule();
		
		this.labelingData();
		
		this.writeToLabeldFile();
		this.writeToRuleFile();
	}
	
	private void applyAllRule() {
		for(String rule:this.labelingRuleVector) {
			this.applyRule(rule);
		}
	}
	
	private void labelingData() {
		boolean noLabelExist = true;
		while(noLabelExist) {
			LabelTargetData noLabelData = this.getNoLabelData();
			if(noLabelData==null) {
				noLabelExist = false;
			}else {
				System.out.println("얘 라벨 없어요: "+noLabelData.originalData);
				System.out.println("현재 라벨링 된 개수: "+this.labeledCount);
				String newRule = this.addRule();
				if(newRule.equals("ExitSystem")) { // 중간 종료
					noLabelExist = false;
					this.labelingRuleVector.remove(this.labelingRuleVector.size()-1);
				}else {
					this.applyRule(newRule);
				}
			}
		}
		System.out.println("종료합니다!");
	}
	private LabelTargetData getNoLabelData() {
		for(LabelTargetData labelTargetData : this.labelTargetDataVector) {
			if(!labelTargetData.labeled) {return labelTargetData;}
		}
		return null;
	}
	private String addRule() {
		Scanner sc = new Scanner(System.in);
		String newRule = sc.nextLine();
		this.labelingRuleVector.add(newRule);
		return newRule;
	}
	private void applyRule(String newRule) {
		try {
			Scanner sc = new Scanner(newRule);
			String contain = sc.next();
			String label = sc.next();
			for(LabelTargetData labelTargetData : this.labelTargetDataVector) {
				if(!labelTargetData.labeled && labelTargetData.originalData.contains(contain)) {
					labelTargetData.label = label;
					labelTargetData.labeled = true;
					System.out.println("라벨 부여: "+label+"/"+labelTargetData.originalData);
					this.labeledCount++;
				}
			}
		}catch(Exception e) {
			this.writeToLabeldFile();
			this.writeToRuleFile();
		}
	}

	/**
	 * Read & Write File
	 */
	private void readTargetFile() {
		try {
			FileReader rw = new FileReader(this.targetFileName); 
			BufferedReader br = new BufferedReader(rw);
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				LabelTargetData labelTargetData = new LabelTargetData();
				labelTargetData.originalData = readLine;
				this.labelTargetDataVector.add(labelTargetData);
			}
			br.close();
		} catch (IOException e) {System.out.println(e);}
	}
	private void readRuleFile() {
		try {
			FileReader rw = new FileReader(this.ruleFileName); 
			BufferedReader br = new BufferedReader(rw);
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				this.labelingRuleVector.add(readLine);
			}
			br.close();
		} catch (IOException e) {System.out.println(e);}
	}
	private void writeToLabeldFile() {
		try {
			FileWriter fw = new FileWriter(this.labeledFileName, false);
			BufferedWriter bw = new BufferedWriter(fw);
			for(LabelTargetData labelTargetData : this.labelTargetDataVector) {
				bw.write(labelTargetData.label+"_"+labelTargetData.originalData);
				bw.newLine();
			}
			bw.flush(); 
			bw.close();
		} catch (IOException e) {System.out.println(e);}
	}
	private void writeToRuleFile() {
		try {
			FileWriter fw = new FileWriter(this.ruleFileName, false);
			BufferedWriter bw = new BufferedWriter(fw);
			for(String string : this.labelingRuleVector) {
				bw.write(string);
				bw.newLine();
			}
			bw.flush(); 
			bw.close();
		} catch (IOException e) {System.out.println(e);}
	}
}
