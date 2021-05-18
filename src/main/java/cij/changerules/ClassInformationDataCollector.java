package cij.changerules;

import java.util.HashSet;
import java.util.Set;

import cij.changerules.classinfo.ClassInformation;
import cij.grammar.java.CodeComponentNode;

public class ClassInformationDataCollector {
	
	private Set<ClassInformation> classList = new HashSet<>();

	public Set<ClassInformation> getClassList() {
		return classList;
	}

	public void setMethodList(Set<ClassInformation> classList) {
		this.classList = classList;
	}

	public void collectClasses(CodeComponentNode root) {
		if(root.getType().equals("typeDeclaration")) {
			ClassInformation ci = new ClassInformation();
			CodeComponentNode baseClass = getBaseClass(root);
			/*
			 * TODO: First index seems to be the type of the class, 
			 * class, interface etc. Test what would happen with an interface.
			 * In a class the type would be simply "class".
			 */
			
			// Initialize base class name
			// Second index is where the class name is
			ci.setClassName(baseClass.getCodeList().get(1));
			// initialize base class modifiers
			ci.setModifiers(getBaseClassModifiers(baseClass));
			classList.add(ci);
		}

	}

	private CodeComponentNode getBaseClass(CodeComponentNode node) {
		for(CodeComponentNode child : node.getChildren()) {
			if(child.getType().equals("(classDeclaration")) {
				// get first child of class declaration
				return child.getChildren().get(0);
			}
		}
		return null;
	}
	
	private Set<String> getBaseClassModifiers(CodeComponentNode node) {
		Set<String> modifiers = new HashSet<>();
		for(CodeComponentNode child : node.getChildren()) {
			if(child.getType().equals("(classModifier")) {
				for(String modifier : child.getCodeList()) {
					modifiers.add(modifier);
				}
			}
		}
		return modifiers;
	}

}
