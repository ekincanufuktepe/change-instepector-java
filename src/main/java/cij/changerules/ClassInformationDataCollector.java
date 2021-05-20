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

	public void setClassList(Set<ClassInformation> classList) {
		this.classList = classList;
	}

	public void collectClasses(CodeComponentNode root) {
		if(root.getType().equals("(typeDeclaration")) {
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
			// initialize super classes/interfaces
			ci.setParentClassNames(getParentClasses(baseClass));
			classList.add(ci);
		}
		else {
			for(CodeComponentNode child : root.getChildren()) {
				collectClasses(child);
			}
		}
		return;
	}

	/**
	 * Finds the node where the class declaration starts
	 * @param node
	 * @return
	 */
	private CodeComponentNode getBaseClass(CodeComponentNode node) {
		for(CodeComponentNode child : node.getChildren()) {
			if(child.getType().equals("(classDeclaration")) {
				// get first child of class declaration
				return child.getChildren().get(0);
			}
		}
		return null;
	}

	/**
	 * Collects the modifiers of a class such as: final, static, public, private, abstract
	 * @param node
	 * @return Set of modifiers
	 */
	private Set<String> getBaseClassModifiers(CodeComponentNode node) {
		Set<String> modifiers = new HashSet<>();
		for(CodeComponentNode child : node.getChildren()) {
			if(child.getType().equals("(classModifier")) {
				for(String modifier : child.getCodeList()) {
					modifier = modifier.replace("(", "");
					modifier = modifier.replace(")", "");
					modifiers.add(modifier);
				}
			}
		}
		return modifiers;
	}

	private Set<String> getParentClasses(CodeComponentNode node) {
		Set<String> parentClasses = new HashSet<>();
		for(CodeComponentNode child : node.getChildren()) {
			// Collect extended super classes
			if(child.getType().equals("(superclass")) {
				for(CodeComponentNode superClass : child.getChildren()) {
					if(superClass.getType().equals("(classType")) {
						String superClassName = superClass.getCodeList().get(0).replace("(", "");
						superClassName = superClassName.replace(")", "");
						parentClasses.add(superClassName);
					}
				}
			}
			// Collect interfaces
			else if(child.getType().equals("(superinterfaces")) {
				// Gets the "interfaceType" nodes
				for(CodeComponentNode superInterface : child.getChildren().get(0).getChildren()) {
					// Gets the "classType" nodes for collecting the interface names
					CodeComponentNode interfaceType = superInterface.getChildren().get(0);
					if(interfaceType.getType().equals("(classType")) {
						String superInterfaceName = interfaceType.getCodeList().get(0).replace("(", "");
						superInterfaceName = superInterfaceName.replace(")", "");
						parentClasses.add(superInterfaceName);
					}
				}
			}
		}
		return parentClasses;
	}

}
