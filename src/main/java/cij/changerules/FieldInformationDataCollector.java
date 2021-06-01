package cij.changerules;

import java.util.HashSet;
import java.util.Set;

import cij.changerules.field.FieldInformation;
import cij.grammar.java.CodeComponentNode;

public class FieldInformationDataCollector {

	private Set<FieldInformation> fieldList = new HashSet<>();

	public Set<FieldInformation> getFieldList() {
		return fieldList;
	}

	public void setClassList(Set<FieldInformation> fieldList) {
		this.fieldList = fieldList;
	}

	public void collectFields(CodeComponentNode root) {
		// Find and get the node where the field is declared
		if(root.getType().equals("(fieldDeclaration")) {
			FieldInformation fi = new FieldInformation();

			// get and set the field name
			fi.setFieldName(getFieldName(root));
			// get and set the field modifiers
			fi.setFieldModifiers(getFieldModifiers(root));
			fieldList.add(fi);
		}
		else {
			for(CodeComponentNode child : root.getChildren()) {
				collectFields(child);
			}
		}
		return;
	}


	private Set<String> getFieldModifiers(CodeComponentNode root) {
		Set<String> modifiers = new HashSet<>();
		for(CodeComponentNode child : root.getChildren()) {
			if(child.getType().equals("(fieldModifier")) {
				for(String modifier : child.getCodeList()) {
					modifier = modifier.replace("(", "");
					modifier = modifier.replace(")", "");
					modifiers.add(modifier);
				}
			}
		}
		return modifiers;
	}

	private String getFieldName(CodeComponentNode root) {
		String fieldName = "";
		if(root.getType().equals("(variableDeclaratorId")) {
			fieldName = root.getCodeList().get(0).replace("(", "");
			fieldName = fieldName.replace(")", "");
			return fieldName;
		}
			for(CodeComponentNode child : root.getChildren()) {
				fieldName = fieldName + getFieldName(child);
			}
		return fieldName;
	}

}
