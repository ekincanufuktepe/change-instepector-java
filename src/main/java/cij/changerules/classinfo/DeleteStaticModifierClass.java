package cij.changerules.classinfo;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.ClassInformationDataCollector;
import cij.grammar.java.JavaParseTree;

public class DeleteStaticModifierClass extends ChangeRule {

	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;
	
	public DeleteStaticModifierClass(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.DSC_DELETE_STATIC_MODIFIER_CLASS;
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
					if((beforeChangeClass.getModifiers().contains("static") &&
							!afterChangeClass.getModifiers().contains("static"))) {
						return true;
					}
				}
			}
		}
		return false;
	}

}