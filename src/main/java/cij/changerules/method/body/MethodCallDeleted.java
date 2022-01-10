package cij.changerules.method.body;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.MethodInformationDataCollector;
import cij.changerules.method.MethodInformation;
import cij.changerules.method.body.info.MethodInvocation;
import cij.grammar.java.JavaParseTree;

public class MethodCallDeleted extends ChangeRule{

	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;

	public MethodCallDeleted(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.MCD_METHOD_CALL_DELETED;
		}
		return null;
	}

	/*
	 * Unlike the Method Call Added (MCA) rule, we especially look for method matches.
	 * We cannot detect a method call/invocation deletion if the method and the body
	 * doesn't exist.
	 * */
	@Override
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode) {
		MethodInformationDataCollector beforeChange = new MethodInformationDataCollector();
		beforeChange.collectMethods(beforeChangeCode.getRootNode());
		MethodInformationDataCollector afterChange = new MethodInformationDataCollector();
		afterChange.collectMethods(afterChangeCode.getRootNode());

		// When an existing method's body is modified look if a method invocation is deleted
		for(MethodInformation beforeChangeMethod : beforeChange.getMethodList()) {
			for(MethodInformation afterChangeMethod : afterChange.getMethodList()) {
				// Find method matches
				if(beforeChangeMethod.getMethodByNameReturnParamType().equals(afterChangeMethod.getMethodByNameReturnParamType())) {
					if(findNewMethodCallInvocation(beforeChangeMethod, afterChangeMethod)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean findNewMethodCallInvocation(MethodInformation beforeChangeMethod, MethodInformation afterChangeMethod) {
		for(MethodInvocation methodInvocation : beforeChangeMethod.getMethodBodyInformation().getMethodInvocations()) {
			if(!afterChangeMethod.getMethodBodyInformation().getMethodInvocations().contains(methodInvocation)) {
				return true;
			}
		}
		return false;
	}

}
