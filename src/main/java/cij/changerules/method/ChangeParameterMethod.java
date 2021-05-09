package cij.changerules.method;

import java.util.HashSet;
import java.util.Set;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.MethodClassDataCollector;
import cij.grammar.java.CodeComponentNode;
import cij.grammar.java.JavaParseTree;

public class ChangeParameterMethod implements ChangeRule {

	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;

	public ChangeParameterMethod(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.CPM_CHANGE_PARAMETERS_OF_METHOD;
		}
		return null;
	}

	@Override
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode) {
		MethodClassDataCollector beforeChange = new MethodClassDataCollector();
		beforeChange.collectMethods(beforeChangeCode.getRootNode());
		MethodClassDataCollector afterChange = new MethodClassDataCollector();
		afterChange.collectMethods(afterChangeCode.getRootNode());

		Set<MethodInformation> afterChangeMethodSet = new HashSet<>();
		afterChangeMethodSet.addAll(afterChange.getMethodList());

		for(MethodInformation method : beforeChange.getMethodList()) {
			afterChangeMethodSet.remove(method);
		}

		for(MethodInformation afterChangeMethod : afterChangeMethodSet) {
			for(MethodInformation beforeChangeMethod : beforeChange.getMethodList()) {
				if(beforeChangeMethod.getMethodName().equals(afterChangeMethod.getMethodName()) &&
						beforeChangeMethod.getReturnType().equals(afterChangeMethod.getReturnType())) {
					if(!beforeChangeMethod.getParameterTypeList().equals(afterChangeMethod.getParameterTypeList()) && 
							compareMethodBodies(beforeChangeMethod.getMethodBody(), afterChangeMethod.getMethodBody())) {
						return true;
					}
					else if(beforeChangeMethod.getParameterTypeList().containsAll(afterChangeMethod.getParameterTypeList()) &&
							afterChangeMethod.getParameterTypeList().size() > beforeChangeMethod.getParameterTypeList().size()) {
						return true;
					}
					else if(afterChangeMethod.getParameterTypeList().containsAll(beforeChangeMethod.getParameterTypeList()) &&
							beforeChangeMethod.getParameterTypeList().size() > afterChangeMethod.getParameterTypeList().size()) {
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