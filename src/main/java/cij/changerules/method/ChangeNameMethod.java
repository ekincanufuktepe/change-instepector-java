package cij.changerules.method;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.MethodClassDataCollector;
import cij.grammar.java.CodeComponentNode;
import cij.grammar.java.JavaParseTree;

public class ChangeNameMethod extends ChangeRule {

	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;

	public ChangeNameMethod(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.CNM_CHANGE_NAME_METHOD;
		}
		return null;
	}

	/*
	 * Compare based on a couple of criteria:
	 * 		- Parameter name list
	 * 		- Parameter type list
	 * 		- Method return type
	 * 		- Method body
	 * 
	 */
	@Override
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode) {
		MethodClassDataCollector beforeChange = new MethodClassDataCollector();
		beforeChange.collectMethods(beforeChangeCode.getRootNode());
		MethodClassDataCollector afterChange = new MethodClassDataCollector();
		afterChange.collectMethods(afterChangeCode.getRootNode());

		for(MethodInformation beforeChangeMethod : beforeChange.getMethodList()) {
			for(MethodInformation afterChangeMethod : afterChange.getMethodList()) {
				if(beforeChangeMethod.getParameterList().equals(afterChangeMethod.getParameterList()) && 
						beforeChangeMethod.getParameterTypeList().equals(afterChangeMethod.getParameterTypeList()) &&
						beforeChangeMethod.getReturnType().equals(afterChangeMethod.getReturnType()) &&
						compareMethodBodies(beforeChangeMethod.getMethodBody(), afterChangeMethod.getMethodBody()) &&
						!beforeChangeMethod.getMethodName().equals(afterChangeMethod.getMethodName())) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 *  Compare two methods if they have the same body.
	 *  If the bodies are not same return false
	 *  If the bodies are same return true
	 */
	public boolean compareMethodBodies(CodeComponentNode beforeChangeNode, CodeComponentNode afterChangeNode) {
		String beforeChangeMethodBody = stringifyMethodBody(beforeChangeNode);
		String afterChangeMethodBody = stringifyMethodBody(afterChangeNode);
		return beforeChangeMethodBody.equals(afterChangeMethodBody);
	}

	/*
	 * Stringify the method body tree into one string for easy compare
	 */
	private String stringifyMethodBody(CodeComponentNode beforeChangeNode) {
		String str = "";
		str = str + beforeChangeNode.getType() + " " + beforeChangeNode.getCodeList() + " ";
		for(CodeComponentNode child : beforeChangeNode.getChildren()) {
			str = str + stringifyMethodBody(child);
		}
		return str;
	}
}