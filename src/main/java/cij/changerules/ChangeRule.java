package cij.changerules;

import cij.grammar.java.JavaParseTree;

public abstract class ChangeRule extends Thread {
	public abstract ChangeCategory getCategory(); 
	
	public abstract boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode);
	
	@Override
	public void run() {
		getCategory();
	}
}
