package cij.changerules.method.body;

import java.util.List;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.MethodInformationDataCollector;
import cij.changerules.method.MethodInformation;
import cij.changerules.method.body.info.IfStatement;
import cij.grammar.java.JavaParseTree;

public class DeleteIfStatement extends ChangeRule {
	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;

	public DeleteIfStatement(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.DIS_IF_STATEMENT;
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
					// if an if-statement exists in previous version but not in new version
					if(compareIfStatements(beforeChangeMethod, afterChangeMethod)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// find if a matching if exists based on the conditions.
	private boolean compareIfStatements(MethodInformation beforeChangeMethod, MethodInformation afterChangeMethod) {
		for(IfStatement ifStmtBefore : beforeChangeMethod.getMethodBodyInformation().getIfStatements()) {
			if(!containsIfStatement(afterChangeMethod.getMethodBodyInformation().getIfStatements(), ifStmtBefore)) {
				return true;
			}
		}
		return false;
	}
	
	// Checks if the if statement is in the previous version if-statements list
	private boolean containsIfStatement(List<IfStatement> listOfIfStatements, IfStatement ifStmtBefore) {
		boolean containsFlag = false;
		for(IfStatement ifStmt : listOfIfStatements) {
			if(ifStmtBefore.equalsIfCondition(ifStmt)) {
				containsFlag = true;
			}
		}
		return containsFlag;
	}
}
