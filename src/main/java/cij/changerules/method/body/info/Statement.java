package cij.changerules.method.body.info;

import java.util.ArrayList;
import java.util.List;

public class Statement {

	private List<Expression> expressions = new ArrayList<Expression>();

	public Statement() {

	}

	public List<Expression> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<Expression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public boolean equals(Object obj) {
		if(this.expressions.equals(((Statement)obj).getExpressions())) {
			return true;
		}
		return false;
	}
}
