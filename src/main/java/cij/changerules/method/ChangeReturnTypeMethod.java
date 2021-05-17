package cij.changerules.method;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.MethodInformationDataCollector;
import cij.grammar.java.JavaParseTree;

public class ChangeReturnTypeMethod extends ChangeRule {
	
	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;
	
	public ChangeReturnTypeMethod(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.CRM_CHANGE_RETURN_TYPE_METHOD;
		}
		return null;
	}

	@Override
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode) {
		MethodInformationDataCollector beforeChange = new MethodInformationDataCollector();
		beforeChange.collectMethods(beforeChangeCode.getRootNode());
		MethodInformationDataCollector afterChange = new MethodInformationDataCollector();
		afterChange.collectMethods(afterChangeCode.getRootNode());
		
		/*
		 *  There is nothing to do with the access modifiers if methods are same (method name, 
		 *  parameters types). Therefore we check only if the method name and parameters are same,
		 *  and if the return types are different, then this is a CRM type change.
		 */
		for(MethodInformation beforeChangeMethod : beforeChange.getMethodList()) {
			for(MethodInformation afterChangeMethod : afterChange.getMethodList()) {
				if(beforeChangeMethod.getMethodName().equals(afterChangeMethod.getMethodName()) &&
						beforeChangeMethod.getParameterTypeList().equals(afterChangeMethod.getParameterTypeList()) &&
						!beforeChangeMethod.getReturnType().equals(afterChangeMethod.getReturnType())) {
					return true;
				}
			}
		}
		return false;
	}
}