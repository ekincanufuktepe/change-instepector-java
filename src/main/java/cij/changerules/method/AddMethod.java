package cij.changerules.method;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.grammar.java.JavaParseTree;

public class AddMethod implements ChangeRule{

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

}
