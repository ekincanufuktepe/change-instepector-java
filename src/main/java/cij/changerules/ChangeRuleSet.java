package cij.changerules;

import java.util.ArrayList;

import cij.changerules.classinfo.AddAbstractModifierClass;
import cij.changerules.classinfo.AddFinalModifierClass;
import cij.changerules.classinfo.AddParentClass;
import cij.changerules.classinfo.AddStaticModifierClass;
import cij.changerules.classinfo.DecreaseClassAccessibility;
import cij.changerules.classinfo.DeleteAbstractModifierClass;
import cij.changerules.classinfo.DeleteFinalModifierClass;
import cij.changerules.classinfo.DeleteParentClass;
import cij.changerules.classinfo.DeleteStaticModifierClass;
import cij.changerules.classinfo.IncreaseClassAccessibility;
import cij.changerules.field.AddFinalModifierField;
import cij.changerules.field.DecreaseFieldAccessibility;
import cij.changerules.field.DeleteFinalModifierField;
import cij.changerules.field.IncreaseFieldAccessibility;
import cij.changerules.method.AddAbstractModifierMethod;
import cij.changerules.method.AddFinalModifierMethod;
import cij.changerules.method.AddMethod;
import cij.changerules.method.AddStaticModifierMethod;
import cij.changerules.method.ChangeMethod;
import cij.changerules.method.ChangeNameMethod;
import cij.changerules.method.ChangeParameterMethod;
import cij.changerules.method.ChangeParameterNamesMethod;
import cij.changerules.method.ChangeReturnTypeMethod;
import cij.changerules.method.DecreaseMethodAccessibility;
import cij.changerules.method.DeleteAbstractModifierMethod;
import cij.changerules.method.DeleteFinalModifierMethod;
import cij.changerules.method.DeleteMethod;
import cij.changerules.method.DeleteStaticModifierMethod;
import cij.changerules.method.IncreaseMethodAccessibility;
import cij.grammar.java.JavaParseTree;

public class ChangeRuleSet{
	
	private ArrayList<ChangeRule> changeRuleSet = new ArrayList<>();
	private void initilizeChangeRules(JavaParseTree beforeChangeTree, JavaParseTree afterChangeTree) {
		// Method Change Rules
		changeRuleSet.add(new AddMethod(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new IncreaseMethodAccessibility(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new DecreaseMethodAccessibility(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new AddFinalModifierMethod(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new DeleteFinalModifierMethod(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new AddStaticModifierMethod(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new DeleteStaticModifierMethod(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new AddAbstractModifierMethod(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new DeleteAbstractModifierMethod(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new ChangeReturnTypeMethod(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new ChangeParameterNamesMethod(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new ChangeMethod(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new ChangeParameterMethod(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new DeleteMethod(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new ChangeNameMethod(beforeChangeTree, afterChangeTree));
		
		// Class Change Rules
		changeRuleSet.add(new IncreaseClassAccessibility(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new DecreaseClassAccessibility(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new AddFinalModifierClass(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new DeleteFinalModifierClass(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new AddStaticModifierClass(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new DeleteStaticModifierClass(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new AddAbstractModifierClass(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new DeleteAbstractModifierClass(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new AddParentClass(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new DeleteParentClass(beforeChangeTree, afterChangeTree));
		
		// Field Change Rules
		changeRuleSet.add(new IncreaseFieldAccessibility(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new DecreaseFieldAccessibility(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new AddFinalModifierField(beforeChangeTree, afterChangeTree));
		changeRuleSet.add(new DeleteFinalModifierField(beforeChangeTree, afterChangeTree));
	}
	
	public ChangeRuleSet(JavaParseTree beforeChangeTree, JavaParseTree afterChangeTree) {
		initilizeChangeRules(beforeChangeTree, afterChangeTree);
		runChangeRules();
	}

	public ArrayList<ChangeRule> getChangeRuleSet() {
		return changeRuleSet;
	}

	public void setChangeRuleSet(ArrayList<ChangeRule> changeRuleSet) {
		this.changeRuleSet = changeRuleSet;
	}
	
	public void printChangeTypes() {
		for(ChangeRule changeRule : changeRuleSet) {
			System.out.println("Change Rule: " + changeRule.getCategory());
		}
	}
	
	private void runChangeRules() {
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for(ChangeRule changeRule : changeRuleSet) {
			Thread t = new Thread(changeRule);
			threads.add(t);
			t.start();
		}
		// Wait until the other threads complete before analyzing another .java file
		for(Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
