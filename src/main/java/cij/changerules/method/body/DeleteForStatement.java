package cij.changerules.method.body;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.MethodInformationDataCollector;
import cij.changerules.method.MethodInformation;
import cij.changerules.method.body.info.ForStatement;
import cij.grammar.java.JavaParseTree;

public class DeleteForStatement extends ChangeRule {
	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;

	public DeleteForStatement(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.DFS_DELETE_FOR_STATEMENT;
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
					// if an for-statement exists in new version but not in previous version
					if(compareForStatements(beforeChangeMethod, afterChangeMethod)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean compareForStatements(MethodInformation beforeChangeMethod, MethodInformation afterChangeMethod) {
		for(ForStatement forStmt : beforeChangeMethod.getMethodBodyInformation().getForStatements()) {
			if(!afterChangeMethod.getMethodBodyInformation().getForStatements().contains(forStmt)) {
				return true;
			}
		}
		return false;
	}
}
