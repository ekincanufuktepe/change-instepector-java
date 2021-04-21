package cij.changerules;

import cij.grammar.java.JavaParseTree;

public interface ChangeRule {
	public ChangeCategory getCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode); 
	
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode);
}
