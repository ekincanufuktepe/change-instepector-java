package cij.changerules.method.body.info;

import java.util.ArrayList;
import java.util.List;

public class Expression {
	private String expressionType;
	private List<String> expression = new ArrayList<String>();
	
	public Expression(String expressionType, List<String> expression) {
		super();
		this.expressionType = expressionType;
		this.expression = expression;
	}
	
	public String getExpressionType() {
		return expressionType;
	}
	public void setExpressionType(String expressionType) {
		this.expressionType = expressionType;
	}
	public List<String> getExpression() {
		return expression;
	}
	public void setExpression(List<String> expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "Expression [expressionType=" + expressionType + ", expression=" + expression + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this.expressionType.equals(((Expression)obj).getExpressionType()) &&
				this.expression.equals(((Expression)obj).getExpression())) {
			return true;
		}
		return false;
	}
}
