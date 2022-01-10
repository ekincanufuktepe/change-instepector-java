package cij.changerules.method.body;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.MethodInformationDataCollector;
import cij.changerules.method.MethodInformation;
import cij.changerules.method.body.info.MethodInvocation;
import cij.grammar.java.JavaParseTree;

public class MethodCallAdded extends ChangeRule {

	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;
	
	public MethodCallAdded(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}
	
	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.MCA_METHOD_CALL_ADDED;
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
				// If method method name, return type, parameter types are same, but the body is different
				if(beforeChangeMethod.getMethodByNameReturnParamType().equals(afterChangeMethod.getMethodByNameReturnParamType())) {
					return findNewMethodCallInvocation(beforeChangeMethod, afterChangeMethod);
				}
			}
		}
		return false;
	}
	
	private boolean findNewMethodCallInvocation(MethodInformation beforeChangeMethod, MethodInformation afterChangeMethod) {
		for(MethodInvocation methodInvocation : afterChangeMethod.getMethodBodyInformation().getMethodInvocations()) {
//			System.out.println(methodInvocation);
			if(!beforeChangeMethod.getMethodBodyInformation().getMethodInvocations().contains(methodInvocation)) {
				return true;
			}
		}
		return false;
	}

}
