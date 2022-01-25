package cij.changerules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cij.changerules.method.body.info.Expression;
import cij.changerules.method.body.info.ForStatement;
import cij.changerules.method.body.info.IfStatement;
import cij.changerules.method.body.info.MethodInvocation;
import cij.changerules.method.body.info.Statement;
import cij.grammar.java.CodeComponentNode;

public class MethodBodyInformationDataCollector {

	private List<IfStatement> ifStmts = new ArrayList<IfStatement>();
	private List<ForStatement> forStmts = new ArrayList<ForStatement>();
	private Set<MethodInvocation> methodInvocationSet = new HashSet<MethodInvocation>(); 

	public void collectMethodBodyDetails(CodeComponentNode root) {
		if((root.getType().equals("(ifThenStatement") || root.getType().equals("(ifThenElseStatement")) 
				&& !root.getCodeList().isEmpty()) {
			IfStatement ifStmt = new IfStatement();
			ifStmt = initializeIfStatment(root, ifStmt);
			// add to if-statements list
//			System.out.println("IF STATEMENT: " + ifStmt);
			ifStmts.add(ifStmt);
		}
		else if(root.getType().equals("(forStatement")) {
			ForStatement forStmt = new ForStatement();
			forStmt = initializeForStatment(root, forStmt);
			for(CodeComponentNode child : root.getChildren()) {
				forStmt = initializeForStatment(child, forStmt);
				// add to if-statements list
			}
			// add to for-statements list
			forStmts.add(forStmt);
		}
		else {
			for(CodeComponentNode child : root.getChildren()) {
				collectMethodBodyDetails(child);
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
					collectMethodBodyDetails(chainedIf);
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


	public void collectExpressionInfo(CodeComponentNode ifNode, Object stmt) {
		for(CodeComponentNode child : ifNode.getChildren()) {
			if(!child.getCodeList().isEmpty()) {
				if(child.getType().equals("(ifThenStatement") || child.getType().equals("(ifThenElseStatement")) {
					IfStatement chainedIf = new IfStatement();
					chainedIf = initializeIfStatment(child, chainedIf);
					((IfStatement)stmt).getIfStatements().add(chainedIf);
					continue;
				}
				else {
					Expression exp = new Expression(child.getType(), child.getCodeList());
					if(stmt.getClass().isInstance(new IfStatement())) {
						((IfStatement)stmt).getExpressions().add(exp);
					}	
//					collectExpressionInfo(child, ifStmt);
				}
			}
			collectExpressionInfo(child, stmt);
		}
	}
	
	public void collectExpressionForForInit(CodeComponentNode ifNode, Object stmt) {
		for(CodeComponentNode child : ifNode.getChildren()) {
			if(!child.getCodeList().isEmpty()) {
				if(child.getType().equals("(ifThenStatement") || child.getType().equals("(ifThenElseStatement")) {
					IfStatement chainedIf = new IfStatement();
					chainedIf = initializeIfStatment(child, chainedIf);
					((IfStatement)stmt).getIfStatements().add(chainedIf);
					continue;
				}
				else {
					Expression exp = new Expression(child.getType(), child.getCodeList());
					if(stmt.getClass().isInstance(new IfStatement())) {
						((IfStatement)stmt).getExpressions().add(exp);
					}
					else if(stmt.getClass().isInstance(new ForStatement())){
//						System.out.println("Detected ForStatement");
						((ForStatement)stmt).getForInits().add(exp);
					}
				}
			}
			collectExpressionForForInit(child, stmt);
		}
	}
	
	public void collectExpressionForForExpression(CodeComponentNode ifNode, Object stmt) {
		for(CodeComponentNode child : ifNode.getChildren()) {
			if(!child.getCodeList().isEmpty()) {
				if(child.getType().equals("(ifThenStatement") || child.getType().equals("(ifThenElseStatement")) {
					IfStatement chainedIf = new IfStatement();
					chainedIf = initializeIfStatment(child, chainedIf);
					((IfStatement)stmt).getIfStatements().add(chainedIf);
					continue;
				}
				else {
					Expression exp = new Expression(child.getType(), child.getCodeList());
					if(stmt.getClass().isInstance(new IfStatement())) {
						((IfStatement)stmt).getExpressions().add(exp);
					}
					else if(stmt.getClass().isInstance(new ForStatement())){
//						System.out.println("Detected ForStatement");
						((ForStatement)stmt).getForExpressions().add(exp);
					}
				}
			}
			collectExpressionForForExpression(child, stmt);
		}
	}
	
	public void collectExpressionForForUpdate(CodeComponentNode ifNode, Object stmt) {
		for(CodeComponentNode child : ifNode.getChildren()) {
			if(!child.getCodeList().isEmpty()) {
				if(child.getType().equals("(ifThenStatement") || child.getType().equals("(ifThenElseStatement")) {
					IfStatement chainedIf = new IfStatement();
					chainedIf = initializeIfStatment(child, chainedIf);
					((IfStatement)stmt).getIfStatements().add(chainedIf);
					continue;
				}
				else {
					Expression exp = new Expression(child.getType(), child.getCodeList());
					if(stmt.getClass().isInstance(new IfStatement())) {
						((IfStatement)stmt).getExpressions().add(exp);
					}
					else if(stmt.getClass().isInstance(new ForStatement())){
//						System.out.println("Detected ForStatement");
						((ForStatement)stmt).getForUpdates().add(exp);
					}
				}
			}
			collectExpressionForForUpdate(child, stmt);
		}
	}
	
	public void collectExpressionForForVariableId(CodeComponentNode ifNode, Object stmt) {
		for(CodeComponentNode child : ifNode.getChildren()) {
			if(!child.getCodeList().isEmpty()) {
				if(child.getType().equals("(ifThenStatement") || child.getType().equals("(ifThenElseStatement")) {
					IfStatement chainedIf = new IfStatement();
					chainedIf = initializeIfStatment(child, chainedIf);
					((IfStatement)stmt).getIfStatements().add(chainedIf);
					continue;
				}
				else {
					Expression exp = new Expression(child.getType(), child.getCodeList());
					if(stmt.getClass().isInstance(new IfStatement())) {
						((IfStatement)stmt).getExpressions().add(exp);
					}
					else if(stmt.getClass().isInstance(new ForStatement())){
//						System.out.println("Detected ForStatement");
						((ForStatement)stmt).getForUpdates().add(exp);
					}
				}
			}
			collectExpressionForForVariableId(child, stmt);
		}
	}
	
	public void collectExpressionForForUannType(CodeComponentNode ifNode, Object stmt) {
		for(CodeComponentNode child : ifNode.getChildren()) {
			if(!child.getCodeList().isEmpty()) {
				if(child.getType().equals("(ifThenStatement") || child.getType().equals("(ifThenElseStatement")) {
					IfStatement chainedIf = new IfStatement();
					chainedIf = initializeIfStatment(child, chainedIf);
					((IfStatement)stmt).getIfStatements().add(chainedIf);
					continue;
				}
				else {
					Expression exp = new Expression(child.getType(), child.getCodeList());
					if(stmt.getClass().isInstance(new IfStatement())) {
						((IfStatement)stmt).getExpressions().add(exp);
					}
					else if(stmt.getClass().isInstance(new ForStatement())){
//						System.out.println("Detected ForStatement");
						((ForStatement)stmt).getForUnannType().add(exp);
					}
				}
			}
			collectExpressionForForUannType(child, stmt);
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
	
	public void collectMethodForStatement(CodeComponentNode root) {
		if(root.getType().equals("(forStatement") && !root.getCodeList().isEmpty()) {
			ForStatement forStmt = new ForStatement();
			for(CodeComponentNode child : root.getChildren()) {
				forStmt = initializeForStatment(child, forStmt);
				// add to if-statements list
			}
			forStmts.add(forStmt);
		}
		else {
			for(CodeComponentNode child : root.getChildren()) {
				collectMethodForStatement(child);
			}
		}
	}
	
	public ForStatement initializeForStatment(CodeComponentNode root, ForStatement forStmt) {
		for(CodeComponentNode child : root.getChildren()) {
//			System.out.println("CHILD: "+ child.getType());
			// for-statement expression/condition
			if(child.getType().equals("(expression")) {
				// expression information collected and added to the given if-statement
//				System.out.println("FOREXP");
				collectExpressionForForExpression(child, forStmt);
			}
			// for-statement initialization
			else if(child.getType().equals("(forInit")) {
//				System.out.println("FORINIT");
				collectExpressionForForInit(child, forStmt);
			}
			// for-statement update
			else if(child.getType().equals("(forUpdate")) {
//				collectBlockStatementsInfo(child, ifStmt);
				// search for chained if-then-else statements
//				System.out.println("FORUPDATE");
				for(CodeComponentNode chainedFor : child.getChildren()) {
					collectExpressionForForUpdate(chainedFor, forStmt);
				}
			}
			else if(child.getType().equals("(unannType")) {
//				System.out.println("FORUANNTYPE");
				for(CodeComponentNode chainedFor : child.getChildren()) {
					collectExpressionForForUannType(chainedFor, forStmt);
				}
			}
			else if(child.getType().equals("(variableDeclaratorId")) {
//				System.out.println("FORVARIABLE");
				for(CodeComponentNode chainedFor : child.getChildren()) {
					collectExpressionForForVariableId(chainedFor, forStmt);
				}
			}
			// check for inner 
			else if(child.getType().equals("(statement")) {
//				collectBlockStatementsInfo(child, ifStmt);
				// search for chained if-then-else statements
				for(CodeComponentNode chainedFor : child.getChildren()) {
					collectMethodBodyDetails(chainedFor);
				}
			}
		}
		return forStmt;
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

	public List<ForStatement> getForStmts() {
		return forStmts;
	}

	public void setForStmts(List<ForStatement> forStmts) {
		this.forStmts = forStmts;
	}
}
