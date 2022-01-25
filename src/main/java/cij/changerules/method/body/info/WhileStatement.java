package cij.changerules.method.body.info;

import java.util.ArrayList;
import java.util.List;

public class WhileStatement {
	private List<Expression> whileExpressions = new ArrayList<Expression>();

	public List<Expression> getWhileExpressions() {
		return whileExpressions;
	}

	public void setWhileExpressions(List<Expression> whileExpressions) {
		this.whileExpressions = whileExpressions;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this.whileExpressions.equals(((WhileStatement)obj).getWhileExpressions())) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "WhileStatement [whileExpressions=" + whileExpressions + "]";
	}
	
	
}
