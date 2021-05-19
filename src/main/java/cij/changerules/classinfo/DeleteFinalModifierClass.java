package cij.changerules.classinfo;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.ClassInformationDataCollector;
import cij.grammar.java.JavaParseTree;

public class DeleteFinalModifierClass extends ChangeRule {

	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;
	
	public DeleteFinalModifierClass(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.DFC_DELETE_FINAL_MODIFIER_CLASS;
		}
		return null;
	}

	@Override
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode) {
		ClassInformationDataCollector beforeChange = new ClassInformationDataCollector();
		beforeChange.collectClasses(beforeChangeCode.getRootNode());
		ClassInformationDataCollector afterChange = new ClassInformationDataCollector();
		afterChange.collectClasses(afterChangeCode.getRootNode());
		
		for(ClassInformation beforeChangeClass : beforeChange.getClassList()) {
			for(ClassInformation afterChangeClass : afterChange.getClassList()) {
				if(beforeChangeClass.getClassName().equals(afterChangeClass.getClassName())) {
					if((beforeChangeClass.getModifiers().contains("final") &&
							!afterChangeClass.getModifiers().contains("final"))) {
						return true;
					}
				}
			}
		}
		return false;
	}

}