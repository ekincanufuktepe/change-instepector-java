package cij.changerules.method;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.MethodInformationDataCollector;
import cij.grammar.java.CodeComponentNode;
import cij.grammar.java.JavaParseTree;

public class ChangeMethod extends ChangeRule {

	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;

	public ChangeMethod(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.CM_CHANGE_METHOD;
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
					// check if bodies are same
					if(!compareMethodBodies(beforeChangeMethod.getMethodBody(), afterChangeMethod.getMethodBody())) {
						return true;
					}
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