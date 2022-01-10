package cij.changerules.method.body;

import java.util.HashSet;
import java.util.Set;

import cij.changerules.MethodBodyInformationDataCollector;
import cij.changerules.method.MethodInformation;
import cij.changerules.method.body.info.ForStatement;
import cij.changerules.method.body.info.IfStatement;
import cij.changerules.method.body.info.MethodInvocation;
import cij.changerules.method.body.info.WhileStatement;
import cij.grammar.java.CodeComponentNode;

public class MethodBodyInformation {
	private Set<IfStatement> ifStatements;
	private Set<WhileStatement> whileStatements;
	private Set<ForStatement> forStatements;
	private Set<MethodInvocation> methodInvocations;
	
	public MethodBodyInformation(CodeComponentNode root) {
//		this.setIfStatements(new HashSet<>());
//		this.setWhileStatements(new HashSet<>());
//		this.setForStatements(new HashSet<>());
		this.ifStatements = new HashSet<IfStatement>();
		this.methodInvocations = new HashSet<MethodInvocation>();
		initiateMethodBodyInformationCollection(root);
	}

	public Set<IfStatement> getIfStatements() {
		return ifStatements;
	}

	public void setIfStatements(Set<IfStatement> ifStatements) {
		this.ifStatements = ifStatements;
	}

	public Set<WhileStatement> getWhileStatements() {
		return whileStatements;
	}

	public void setWhileStatements(Set<WhileStatement> whileStatements) {
		this.whileStatements = whileStatements;
	}

	public Set<ForStatement> getForStatements() {
		return forStatements;
	}

	public void setForStatements(Set<ForStatement> forStatements) {
		this.forStatements = forStatements;
	}
	
	private void initiateMethodBodyInformationCollection(CodeComponentNode root) {
		MethodBodyInformationDataCollector collector = new MethodBodyInformationDataCollector();
		// Collect if statements
		collector.collectMethodIfStatement(root);
		setIfStatements(collector.getIfStmts());
		// Collect method invocations
		collector.collectionMethodInvocation(root);
		setMethodInvocations(collector.getMethodInvocationSet());
	}

	public Set<MethodInvocation> getMethodInvocations() {
		return methodInvocations;
	}

	public void setMethodInvocations(Set<MethodInvocation> methodInvocations) {
		this.methodInvocations = methodInvocations;
	}
}
