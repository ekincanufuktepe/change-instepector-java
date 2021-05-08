package cij.changerules;

import cij.grammar.java.JavaParseTree;

public interface ChangeRule {
	public ChangeCategory getCategory(); 
	
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode);
}
