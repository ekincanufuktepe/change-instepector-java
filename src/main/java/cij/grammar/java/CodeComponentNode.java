package cij.grammar.java;

import java.util.ArrayList;
import java.util.List;

public class CodeComponentNode {
	
	private CodeComponentNode parent;
	private List<String> codeList = new ArrayList<>();
	private String type;
	private List<CodeComponentNode> children = new ArrayList<>();;
	
	public CodeComponentNode(String type, String code, CodeComponentNode parent) {
		this.type = type;
		this.codeList.add(code);
//		this.children = new ArrayList<>();
		this.parent = parent;
	}
	
	public CodeComponentNode getParent() {
		return parent;
	}

	public void setParent(CodeComponentNode parent) {
		this.parent = parent;
	}

	public List<String> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<String> codeList) {
		this.codeList = codeList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<CodeComponentNode> getChildren() {
		return children;
	}

	public void setChildren(List<CodeComponentNode> children) {
		this.children = children;
	}

	public CodeComponentNode(String type, CodeComponentNode parent) {
		this.type = type;
//		this.children = new ArrayList<>();
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "CodeComponentNode [parent=" + parent + ", codeList=" + codeList + ", type=" + type + ", children="
				+ children + "]";
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(((CodeComponentNode)obj).toString().equals(this.toString()))
			return true;
		return false;
	}
}
