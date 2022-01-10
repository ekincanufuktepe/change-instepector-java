package cij.changerules.method.body.info;

import java.util.ArrayList;
import java.util.List;

public class Statement {
	private List<String> statementOperationType = new ArrayList<String>();
	private List<String> operation = new ArrayList<String>();
	
	public Statement() {
	}
	
	public Statement(List<String> statementOperationType, List<String> operation) {
		super();
		this.statementOperationType = statementOperationType;
		this.operation = operation;
	}
	
	public List<String> getStatementOperationType() {
		return statementOperationType;
	}
	public void setStatementOperationType(List<String> statementOperationType) {
		this.statementOperationType = statementOperationType;
	}
	public List<String> getOperation() {
		return operation;
	}
	public void setOperation(List<String> statement) {
		this.operation = statement;
	}

	@Override
	public String toString() {
		return "Statement [statementOperationType=" + statementOperationType + ", operation=" + operation + "]";
	}
	
	
}
