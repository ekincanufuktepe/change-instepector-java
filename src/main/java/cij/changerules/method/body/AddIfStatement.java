package cij.changerules.method.body;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.MethodInformationDataCollector;
import cij.changerules.method.MethodInformation;
import cij.changerules.method.body.info.IfStatement;
import cij.grammar.java.JavaParseTree;

public class AddIfStatement extends ChangeRule {

	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;

	public AddIfStatement(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.AIS_ADD_IF_STATEMENT;
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
				// Find method matches
				if(beforeChangeMethod.getMethodByNameReturnParamType().equals(afterChangeMethod.getMethodByNameReturnParamType())) {
					if(findNoneExistingIfStatement(beforeChangeMethod, afterChangeMethod)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// find if a matching if exists, checks if an "if-statement" with same body and condition exists
	// does not handle ifs with same conditions but different body, or vice versa.
	private boolean findNoneExistingIfStatement(MethodInformation beforeChangeMethod, MethodInformation afterChangeMethod) {
		for(IfStatement ifStmts : afterChangeMethod.getMethodBodyInformation().getIfStatements()) {
			if(!beforeChangeMethod.getMethodBodyInformation().getIfStatements().contains(ifStmts)) {
				return true;
			}
		}
		return false;
	}

}
