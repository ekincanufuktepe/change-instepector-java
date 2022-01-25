package cij.changerules.method.body;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.MethodInformationDataCollector;
import cij.changerules.method.MethodInformation;
import cij.changerules.method.body.info.WhileStatement;
import cij.grammar.java.JavaParseTree;

public class AddWhileStatement extends ChangeRule {

	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;

	public AddWhileStatement(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.AWS_ADD_WHILE_STATEMENT;
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
					// if an while-statement exists in new version but not in previous version
					if(compareWhileStatements(beforeChangeMethod, afterChangeMethod)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean compareWhileStatements(MethodInformation beforeChangeMethod, MethodInformation afterChangeMethod) {
		for(WhileStatement whileStmtAfter : afterChangeMethod.getMethodBodyInformation().getWhileStatements()) {
			if(!beforeChangeMethod.getMethodBodyInformation().getWhileStatements().contains(whileStmtAfter)) {
				return true;
			}
		}
		return false;
	}
}
