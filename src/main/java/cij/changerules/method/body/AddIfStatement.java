package cij.changerules.method.body;

import java.util.List;

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
					// if an if-statement exists in new version but not in previous version
					if(compareIfStatements(beforeChangeMethod, afterChangeMethod)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// find if a matching if exists, checks if an "if-statement" with same body and condition exists
	// does not handle ifs with same conditions but different body, or vice versa.
	private boolean compareIfStatements(MethodInformation beforeChangeMethod, MethodInformation afterChangeMethod) {
		for(IfStatement ifStmtAfter : afterChangeMethod.getMethodBodyInformation().getIfStatements()) {
			
			if(!containsIfStatement(beforeChangeMethod.getMethodBodyInformation().getIfStatements(), ifStmtAfter)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean containsIfStatement(List<IfStatement> listOfIfStatements, IfStatement ifStmtAfter) {
		boolean containsFlag = false;
		for(IfStatement ifStmt : listOfIfStatements) {
			if(ifStmtAfter.equalsIfCondition(ifStmt)) {
				containsFlag = true;
			}
		}
		return containsFlag;
	}
}
