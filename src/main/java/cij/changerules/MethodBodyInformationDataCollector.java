package cij.changerules;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cij.changerules.method.body.info.Expression;
import cij.changerules.method.body.info.ForStatement;
import cij.changerules.method.body.info.IfStatement;
import cij.changerules.method.body.info.MethodInvocation;
import cij.changerules.method.body.info.Statement;
import cij.changerules.method.body.info.WhileStatement;
import cij.grammar.java.CodeComponentNode;

public class MethodBodyInformationDataCollector {

	private Set<IfStatement> ifStmts = new HashSet<IfStatement>();
	private Set<MethodInvocation> methodInvocationSet = new HashSet<MethodInvocation>(); 

	public void collectMethodIfStatement(CodeComponentNode root) {
		//		System.out.println("Let's go!");
		if((root.getType().equals("(ifThenStatement") || root.getType().equals("(ifThenElseStatement")) 
				&& !root.getCodeList().isEmpty()) {
			IfStatement ifStmt = new IfStatement();
			for(CodeComponentNode child : root.getChildren()) {
				if(child.getType().equals("(expression")) {
					// expression information collected and added to the given if-statement
					collectExpressionInfo(root, ifStmt);
				}
				else if(child.getType().equals("(statementNoShortIf")) {
					CodeComponentNode ifBlock = findBlockStatements(child);
					collectBlockStatementsInfo(ifBlock, ifStmt);
				}
			}
			// add to if-statements set
			ifStmts.add(ifStmt);
		}
		else {
			for(CodeComponentNode child : root.getChildren()) {
				collectMethodIfStatement(child);
			}
		}
	}
	
	// collect method invocations
	public void collectionMethodInvocation(CodeComponentNode root) {
		if(root.getType().equals("(methodInvocation")) {
			MethodInvocation methodInvocation = new MethodInvocation();
			collectMethodInvocations(root, methodInvocation);
			methodInvocationSet.add(methodInvocation);
		}
		else {
			for(CodeComponentNode child : root.getChildren()) {
				collectionMethodInvocation(child);
			}
		}
	}

	public void collectExpressionInfo(CodeComponentNode ifNode, IfStatement ifStmt) {
		for(CodeComponentNode child : ifNode.getChildren()) {
			if(!child.getCodeList().isEmpty()) {
				Expression exp = new Expression(child.getType().substring(1), child.getCodeList().get(0));
				ifStmt.getExpressions().add(exp);
			}
			collectExpressionInfo(child, ifStmt);
		}
	}

	private CodeComponentNode findBlockStatements(CodeComponentNode ifNode) {
		CodeComponentNode blockStatementNode = null;
		for(CodeComponentNode child : ifNode.getChildren()) {
			if(child.getType().equals("(blockStatements")) {
				blockStatementNode = child;
				break;
			}
			else {
				blockStatementNode = findBlockStatements(child);
			}
		}
		return blockStatementNode;
	}

	public void collectBlockStatementsInfo(CodeComponentNode ifNode, IfStatement ifStmt) {
		// gets block statements
		for(CodeComponentNode child : ifNode.getChildren()) {
			Statement stmt = new Statement();
			ifStmt.getStatements().add(collectStatementInfo(child, stmt));
		}
	}

	public Statement collectStatementInfo(CodeComponentNode blockNode, Statement stmt) {
		for(CodeComponentNode child : blockNode.getChildren()) {
			if(!child.getCodeList().isEmpty()) {
				stmt.getStatementOperationType().add(child.getType());
				stmt.getOperation().add(child.getCodeList().toString());
			}
			collectStatementInfo(child, stmt);
		}
		return stmt;
	}
	
	public void collectMethodInvocations(CodeComponentNode methodInvocationNode, MethodInvocation methodInvocation) {
		if(!methodInvocationNode.getCodeList().isEmpty()) {
			methodInvocation.getMethodInvationInforamation().put(methodInvocationNode.getType(), methodInvocationNode.getCodeList());
		}
		for(CodeComponentNode child : methodInvocationNode.getChildren()) {
			collectMethodInvocations(child, methodInvocation);
		}
	}

	public Set<IfStatement> getIfStmts() {
		return ifStmts;
	}

	public void setIfStmts(Set<IfStatement> ifStmts) {
		this.ifStmts = ifStmts;
	}
	
	public Set<MethodInvocation> getMethodInvocationSet() {
		return methodInvocationSet;
	}

	public void setMethodInvocationSet(Set<MethodInvocation> methodInvocationSet) {
		this.methodInvocationSet = methodInvocationSet;
	}
}
