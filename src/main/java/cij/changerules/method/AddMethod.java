package cij.changerules.method;

import java.util.HashSet;
import java.util.Set;

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
		
//		constructMethodDeclaration(treeBeforeChange.getRootNode());
//		for(MethodInformation method : getMethodList()) {
//			System.out.println("Method: " + method);
//		}
	}

	@Override
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode) {
		MethodClassDataCollector beforeChange = new MethodClassDataCollector();
		beforeChange.collectMethods(beforeChangeCode.getRootNode());
		for(MethodInformation method : beforeChange.getMethodList()) {
			System.out.println("Before Method: " + method);
		}
		MethodClassDataCollector afterChange = new MethodClassDataCollector();
		afterChange.collectMethods(afterChangeCode.getRootNode());
		for(MethodInformation method : afterChange.getMethodList()) {
			System.out.println("After Method: " + method);
		}
		// If after change method contains every method before change and if the current number of methods is greater than before change 
		if(afterChange.getMethodList().containsAll(beforeChange.getMethodList()) && 
				afterChange.getMethodList().size() > beforeChange.getMethodList().size()) {
			return true;
		}
		return false;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.AM_ADD_METHOD;
		}
		return null;
	}

}
