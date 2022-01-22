package cij.changerules.method.body.info;

import java.util.ArrayList;
import java.util.List;

public class IfStatement {
	

	//	private List<String> expressions = new ArrayList<String>();
	//	private CodeComponentNode expressionsNode;
	//	private List<String> statements = new ArrayList<String>();
	private List<Expression> expressions = new ArrayList<Expression>();
	private List<Statement> statements = new ArrayList<Statement>();
	private List<IfStatement> ifStatements = new ArrayList<IfStatement>();
	
	public List<Expression> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<Expression> expressions) {
		this.expressions = expressions;
	}

	public List<Statement> getStatements() {
		return statements;
	}

	public void setStatements(List<Statement> statements) {
		this.statements = statements;
	}

//	@Override
//	public String toString() {
//		return "IfStatement [expressions=" + expressions + ", statements=" + statements + ", ifStatements="
//				+ ifStatements + "]";
//	}
	
	@Override
	public String toString() {
		return "IfStatement [expressions=" + expressions + "]";
	}

	// only check expressions (aka. conditions)
	public boolean equalsIfCondition(Object obj) {
		if(this.expressions.equals(((IfStatement)obj).getExpressions())) {
			return true;
		}
		return false;
	}

	// only check if-body is equal
	public boolean equalsIfBody(Object obj) {
		if(this.statements.equals(((IfStatement)obj).getStatements())) {
			return true;
		}
		return false;
	}

//	@Override
//	public boolean equals(Object obj) {
//		if(this.equalsIfBody(obj) && this.equalsIfCondition(obj)) {
//			return true;
//		}
//		return false;
//	}
	@Override
	public boolean equals(Object obj) {
		if(this.equalsIfCondition(obj)) {
			return true;
		}
		return false;
	}

	public List<IfStatement> getIfStatements() {
		return ifStatements;
	}

	public void setIfStatements(List<IfStatement> ifStatements) {
		this.ifStatements = ifStatements;
	}
}
