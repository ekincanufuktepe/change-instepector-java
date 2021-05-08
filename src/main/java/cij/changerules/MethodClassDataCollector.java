package cij.changerules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cij.changerules.method.MethodInformation;
import cij.grammar.java.CodeComponentNode;

public class MethodClassDataCollector {

	private Set<MethodInformation> methodList = new HashSet<>();

	public Set<MethodInformation> getMethodList() {
		return methodList;
	}

	public void setMethodList(Set<MethodInformation> methodList) {
		this.methodList = methodList;
	}

	public void collectMethods(CodeComponentNode root) {
		// find the method declaration
		if(root.getType().equals("(methodDeclaration")) {
			MethodInformation method = new MethodInformation();
			for(CodeComponentNode child : root.getChildren()) {
				if(child.getType().equals("(methodModifier")) {
					// initialize the modifiers
					method.getAccessModifier().add(child.getCodeList().get(0));
				}
				if(child.getType().equals("(methodHeader")) {
					// initialize the method return type
					method.setReturnType(collectMethodReturnType(child));
					// initialize the method name
					method.setMethodName(collectMethodName(child));

					@SuppressWarnings("unlikely-arg-type")
					int methodDeclaratorIndex = child.getChildren().indexOf("(methodDeclarator");
					  
					//method.setParameterList(collectMethodParameterList(child.getChildren().get(methodDeclaratorIndex).getChildren().get(0)));
				}
			}
			methodList.add(method);
		}
		else {
			for(CodeComponentNode child : root.getChildren()) {
				collectMethods(child);
			}
		}
	}

	private String collectMethodReturnType(CodeComponentNode root) {
		for(CodeComponentNode child : root.getChildren()) {
			if(child.getType().equals("(result")) {
				if(!child.getCodeList().isEmpty()) {
					child.getCodeList().get(0).replace("(", "");
					return child.getCodeList().get(0).replace(")", "");
				}
				else {

					do {
						child = child.getChildren().get(0);
					} while(child.getCodeList().isEmpty());

					child.getCodeList().get(0).replace("(", "");
					return child.getCodeList().get(0).replace(")", "");
				}
			}
		}
		return "";
	}

	private String collectMethodName(CodeComponentNode root) {
		for(CodeComponentNode child : root.getChildren()) {
			if(child.getType().equals("(methodDeclarator")) {
				return child.getCodeList().get(0);
			}
		}
		return "";
	}

}
