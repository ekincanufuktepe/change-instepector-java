package cij.changerules.classinfo;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.ClassInformationDataCollector;
import cij.grammar.java.JavaParseTree;

public class AddClass extends ChangeRule {

	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;
	
	public AddClass(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.AC_ADD_CLASS;
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
		for(ClassInformation afterChangeClass : afterChange.getClassList()) {
			flag = true;
			for(ClassInformation beforeChangeClass : beforeChange.getClassList()) {
				if(afterChangeClass.getClassName().equals(beforeChangeClass.getClassName())) {
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