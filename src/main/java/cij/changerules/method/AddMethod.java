package cij.changerules.method;

import java.util.HashSet;
import java.util.Set;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.MethodInformationDataCollector;
import cij.grammar.java.JavaParseTree;

public class AddMethod extends ChangeRule{
	
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
		MethodInformationDataCollector beforeChange = new MethodInformationDataCollector();
		beforeChange.collectMethods(beforeChangeCode.getRootNode());

		MethodInformationDataCollector afterChange = new MethodInformationDataCollector();
		afterChange.collectMethods(afterChangeCode.getRootNode());
		
		// This code can be slightly efficient compared to the commented code below, 
		// but will not identify all the changed methods. Finding on new added method will be enough.
		for(MethodInformation method : afterChange.getMethodList()) {
			if(!beforeChange.getMethodList().contains(method)) {
				return true;
			}
		}
		
		// The code below can be used for identifying the added methods
//		Set<MethodInformation> afterChangeMethodSet = new HashSet<>();
//		afterChangeMethodSet.addAll(afterChange.getMethodList());
		
//		for(MethodInformation method : beforeChange.getMethodList()) {
//			afterChangeMethodSet.remove(method);
//		}
//		
//		if(!afterChangeMethodSet.isEmpty()) {
//			return true;
//		}
		return false;
	}

}
