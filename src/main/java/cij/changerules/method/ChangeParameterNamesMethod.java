package cij.changerules.method;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.MethodInformationDataCollector;
import cij.grammar.java.JavaParseTree;

public class ChangeParameterNamesMethod extends ChangeRule {
	
	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;
	
	public ChangeParameterNamesMethod(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.CNPM_CHANGE_NAME_OF_PARAMETERS_METHOD;
		}
		return null;
	}

	@Override
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode) {
		MethodInformationDataCollector beforeChange = new MethodInformationDataCollector();
		beforeChange.collectMethods(beforeChangeCode.getRootNode());
		MethodInformationDataCollector afterChange = new MethodInformationDataCollector();
		afterChange.collectMethods(afterChangeCode.getRootNode());
		
		for(MethodInformation beforeChangeMethod : beforeChange.getMethodList()) {
			for(MethodInformation afterChangeMethod : afterChange.getMethodList()) {
				if(beforeChangeMethod.getMethodName().equals(afterChangeMethod.getMethodName()) &&
						beforeChangeMethod.getParameterTypeList().equals(afterChangeMethod.getParameterTypeList()) &&
						beforeChangeMethod.getReturnType().equals(afterChangeMethod.getReturnType()) &&
						!beforeChangeMethod.getParameterNameList().containsAll(afterChangeMethod.getParameterNameList())) {
					return true;
				}
			}
		}
		return false;
	}
}