package cij.changerules.method.body.info;

import java.util.ArrayList;
import java.util.List;

public class IfStatement {
//	private List<String> expressions = new ArrayList<String>();
//	private CodeComponentNode expressionsNode;
//	private List<String> statements = new ArrayList<String>();
	private List<Expression> expressions = new ArrayList<Expression>();
	private List<Statement> statements = new ArrayList<Statement>();

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

	@Override
	public String toString() {
		return "IfStatement [expressions=" + expressions + ", statements=" + statements + "]";
	} 
	
	
}
