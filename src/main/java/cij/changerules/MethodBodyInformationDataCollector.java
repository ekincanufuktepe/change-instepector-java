package cij.changerules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cij.changerules.method.body.info.Expression;
import cij.changerules.method.body.info.IfStatement;
import cij.changerules.method.body.info.MethodInvocation;
import cij.changerules.method.body.info.Statement;
import cij.grammar.java.CodeComponentNode;

public class MethodBodyInformationDataCollector {

	private List<IfStatement> ifStmts = new ArrayList<IfStatement>();
	private Set<MethodInvocation> methodInvocationSet = new HashSet<MethodInvocation>(); 

	public void collectMethodIfStatement(CodeComponentNode root) {
		if((root.getType().equals("(ifThenStatement") || root.getType().equals("(ifThenElseStatement")) 
				&& !root.getCodeList().isEmpty()) {
			IfStatement ifStmt = new IfStatement();
			ifStmt = initializeIfStatment(root, ifStmt);
			// add to if-statements list
			ifStmts.add(ifStmt);
		}
		else {
			for(CodeComponentNode child : root.getChildren()) {
				collectMethodIfStatement(child);
			}
		}
	}
	
	public IfStatement initializeIfStatment(CodeComponentNode root, IfStatement ifStmt) {
		for(CodeComponentNode child : root.getChildren()) {
			// if-statement condition
			if(child.getType().equals("(expression")) {
				// expression information collected and added to the given if-statement
				collectExpressionInfo(child, ifStmt);
			}
			// if-body statements
//			else if(child.getType().equals("(statementNoShortIf")) {
//				CodeComponentNode ifBlock = findBlockStatements(child);
//				collectBlockStatementsInfo(ifBlock, ifStmt);
//			}
			// if there are chained if-statements check inside statement
			else if(child.getType().equals("(statement")) {
//				collectBlockStatementsInfo(child, ifStmt);
				// search for chained if-then-else statements
				for(CodeComponentNode chainedIf : child.getChildren()) {
					collectMethodIfStatement(chainedIf);
				}
			}
		}
		return ifStmt;
	}

	// collect method invocations
	public void collectionMethodInvocation(CodeComponentNode root) {
		if(root.getType().startsWith("(methodInvocation")) {
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

	/*
	 * TODO: This where the chained if statements are added as a part of the upper if statement! 
	 * */
	// collects the conditions written for the if statement
	public void collectExpressionInfo(CodeComponentNode ifNode, IfStatement ifStmt) {
		for(CodeComponentNode child : ifNode.getChildren()) {
			if(!child.getCodeList().isEmpty()) {
				if(child.getType().equals("(ifThenStatement") || child.getType().equals("(ifThenElseStatement")) {
//					System.out.println("ISSUE HERE!");
					IfStatement chainedIf = new IfStatement();
					chainedIf = initializeIfStatment(child, chainedIf);
					ifStmt.getIfStatements().add(chainedIf);
					continue;
				}
				else {
					Expression exp = new Expression(child.getType(), child.getCodeList());
					ifStmt.getExpressions().add(exp);
//					collectExpressionInfo(child, ifStmt);
				}
			}
			collectExpressionInfo(child, ifStmt);
		}
	}

	/**
	 * This method is used for collecting statements in an if block. 
	 * Since the collection is disabled it is not used 
	 * /
//	private CodeComponentNode findBlockStatements(CodeComponentNode ifNode) {
//		CodeComponentNode blockStatementNode = null;
//		for(CodeComponentNode child : ifNode.getChildren()) {
//			if(child.getType().equals("(blockStatements")) {
//				blockStatementNode = child;
//				break;
//			}
//			else {
//				blockStatementNode = findBlockStatements(child);
//			}
//		}
//		return blockStatementNode;
//	}

	public void collectBlockStatementsInfo(CodeComponentNode ifNode, IfStatement ifStmt) {
		// gets block statements
		for(CodeComponentNode child : ifNode.getChildren()) {
			Statement stmt = new Statement();
			ifStmt.getStatements().add(collectStatementInfo(child, stmt));
		}
	}

	/* A statement can consist of multiple expression, so it find all expression in the if body
	 * and adds it to the statement object
	*/
	public Statement collectStatementInfo(CodeComponentNode blockNode, Statement stmt) {
		for(CodeComponentNode child : blockNode.getChildren()) {
			if(!child.getCodeList().isEmpty()) {
				Expression exp = new Expression(child.getType(), child.getCodeList());
				stmt.getExpressions().add(exp);
			}
			collectStatementInfo(child, stmt);
		}
		return stmt;
	}

	private void collectMethodInvocations(CodeComponentNode methodInvocationNode, MethodInvocation methodInvocation) {
		if(!methodInvocationNode.getCodeList().isEmpty()) {
			methodInvocation.getMethodInvationInforamation().put(methodInvocationNode.getType(), methodInvocationNode.getCodeList());
		}
		for(CodeComponentNode child : methodInvocationNode.getChildren()) {
			collectMethodInvocations(child, methodInvocation);
		}
	}

	public List<IfStatement> getIfStmts() {
		return ifStmts;
	}

	public void setIfStmts(List<IfStatement> ifStmts) {
		this.ifStmts = ifStmts;
	}

	public Set<MethodInvocation> getMethodInvocationSet() {
		return methodInvocationSet;
	}

	public void setMethodInvocationSet(Set<MethodInvocation> methodInvocationSet) {
		this.methodInvocationSet = methodInvocationSet;
	}
}
