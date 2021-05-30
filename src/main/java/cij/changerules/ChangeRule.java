package cij.changerules;

import cij.grammar.java.JavaParseTree;

public abstract class ChangeRule extends Thread {
	private ChangeCategory changeCategory;
	public abstract ChangeCategory getCategory(); 
	
	public abstract boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode);
	
	@Override
	public void run() {
		changeCategory = getCategory();
	}

	public ChangeCategory getChangeCategory() {
		return changeCategory;
	}

	public void setChangeCategory(ChangeCategory changeCategory) {
		this.changeCategory = changeCategory;
	}
	
}
