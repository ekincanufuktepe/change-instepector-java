package cij.changerules.method;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.MethodClassDataCollector;
import cij.grammar.java.JavaParseTree;

public class AddMethod implements ChangeRule{
	
	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;
	
	public AddMethod(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}
	
	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.AM_ADD_METHOD;
		}
		return null;
	}

	@Override
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode) {
		MethodClassDataCollector beforeChange = new MethodClassDataCollector();
		beforeChange.collectMethods(beforeChangeCode.getRootNode());
		MethodClassDataCollector afterChange = new MethodClassDataCollector();
		afterChange.collectMethods(afterChangeCode.getRootNode());
		// If after change method contains every method before change and if the current number of methods is greater than before change 
		if(afterChange.getMethodList().containsAll(beforeChange.getMethodList()) && 
				afterChange.getMethodList().size() > beforeChange.getMethodList().size()) {
			return true;
		}
		return false;
	}

}
