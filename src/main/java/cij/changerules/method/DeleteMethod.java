package cij.changerules.method;

import java.util.HashSet;
import java.util.Set;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.MethodClassDataCollector;
import cij.grammar.java.JavaParseTree;

public class DeleteMethod extends ChangeRule{
	
	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;
	
	public DeleteMethod(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}
	
	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.DM_DELETE_METHOD;
		}
		return null;
	}

	@Override
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode) {
		MethodClassDataCollector beforeChange = new MethodClassDataCollector();
		beforeChange.collectMethods(beforeChangeCode.getRootNode());

		MethodClassDataCollector afterChange = new MethodClassDataCollector();
		afterChange.collectMethods(afterChangeCode.getRootNode());
		Set<MethodInformation> beforeChangeMethodSet = new HashSet<>();
		beforeChangeMethodSet.addAll(beforeChange.getMethodList());
		
		for(MethodInformation method : afterChange.getMethodList()) {
			beforeChangeMethodSet.remove(method);
		}
		
		if(!beforeChangeMethodSet.isEmpty()) {
			return true;
		}
		return false;
	}

}