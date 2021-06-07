package cij.changerules.classinfo;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.ClassInformationDataCollector;
import cij.grammar.java.JavaParseTree;

public class DeleteClass extends ChangeRule {

	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;
	
	public DeleteClass(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.DC_DELETE_CLASS;
		}
		return null;
	}

	@Override
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode) {
		ClassInformationDataCollector beforeChange = new ClassInformationDataCollector();
		beforeChange.collectClasses(beforeChangeCode.getRootNode());
		ClassInformationDataCollector afterChange = new ClassInformationDataCollector();
		afterChange.collectClasses(afterChangeCode.getRootNode());
		
		boolean flag = true;
		// if the flag is set to false that means the after change class also exists before the change
		for(ClassInformation beforeChangeClass : beforeChange.getClassList()) {
			flag = true;
			for(ClassInformation afterChangeClass : afterChange.getClassList()) {
				if(beforeChangeClass.getClassName().equals(afterChangeClass.getClassName())) {
					flag = false;
				}
			}
			if(flag) {
				return true;
			}
		}
		return false;
	}

}