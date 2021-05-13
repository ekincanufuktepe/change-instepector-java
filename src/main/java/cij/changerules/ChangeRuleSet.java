package cij.changerules;

import java.util.ArrayList;

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
		for(ChangeRule changeRule : changeRuleSet) {
			changeRule.start();
		}
	}
	
}
