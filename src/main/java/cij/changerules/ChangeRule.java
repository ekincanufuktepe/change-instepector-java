package cij.changerules;

import cij.grammar.java.JavaParseTree;

public interface ChangeRule {
	public ChangeCategory findCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode); 
}
