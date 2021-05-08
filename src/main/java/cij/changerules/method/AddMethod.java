package cij.changerules.method;

import java.util.Set;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.MethodClassDataCollector;
import cij.grammar.java.CodeComponentNode;
import cij.grammar.java.JavaParseTree;

public class AddMethod extends MethodClassDataCollector implements ChangeRule{
	
	private JavaParseTree tree;
	
	public AddMethod(JavaParseTree tree) {
		this.tree = tree;
		constructMethodDeclaration(tree.getRootNode());
		for(MethodInformation method : getMethodList()) {
			System.out.println("Method: " + method);
		}
	}

	@Override
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode) {
		return false;
	}

	@Override
	public ChangeCategory getCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode) {
		if(isChangeCategory(beforeChangeCode, afterChangeCode)) {
			return ChangeCategory.AM_ADD_METHOD;
		}
		return null;
	}
	
	private Set<MethodInformation> constructMethodDeclaration(CodeComponentNode codeTree) {
		collectMethods(codeTree);
		return getMethodList();
	}

}
