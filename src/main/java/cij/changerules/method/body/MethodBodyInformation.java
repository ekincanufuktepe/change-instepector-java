package cij.changerules.method.body;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cij.changerules.MethodBodyInformationDataCollector;
import cij.changerules.method.body.info.ForStatement;
import cij.changerules.method.body.info.IfStatement;
import cij.changerules.method.body.info.MethodInvocation;
import cij.changerules.method.body.info.WhileStatement;
import cij.grammar.java.CodeComponentNode;

public class MethodBodyInformation {
	private List<IfStatement> ifStatements;
	private Set<WhileStatement> whileStatements;
	private List<ForStatement> forStatements;
	private Set<MethodInvocation> methodInvocations;
	
	public MethodBodyInformation(CodeComponentNode root) {
		this.ifStatements = new ArrayList<IfStatement>();
		this.methodInvocations = new HashSet<MethodInvocation>();
		this.forStatements = new ArrayList<ForStatement>();
		initiateMethodBodyInformationCollection(root);
	}

	public List<IfStatement> getIfStatements() {
		return ifStatements;
	}

	public void setIfStatements(List<IfStatement> ifStatements) {
		this.ifStatements = ifStatements;
	}

	public Set<WhileStatement> getWhileStatements() {
		return whileStatements;
	}

	public void setWhileStatements(Set<WhileStatement> whileStatements) {
		this.whileStatements = whileStatements;
	}

	public List<ForStatement> getForStatements() {
		return forStatements;
	}

	public void setForStatements(List<ForStatement> forStatements) {
		this.forStatements = forStatements;
	}
	
	private void initiateMethodBodyInformationCollection(CodeComponentNode root) {
		MethodBodyInformationDataCollector collector = new MethodBodyInformationDataCollector();
		// Initiate collection
		collector.collectMethodBodyDetails(root);
		// set if-statement information
		setIfStatements(collector.getIfStmts());
		// set for-statement information
		setForStatements(collector.getForStmts());
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
