package cij.changerules.method.body.info;

public class Expression {
	private String expressionType;
	private String expression;
	
	public Expression(String expressionType, String expression) {
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
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "Expression [expressionType=" + expressionType + ", expression=" + expression + "]";
	}
	
	
}
