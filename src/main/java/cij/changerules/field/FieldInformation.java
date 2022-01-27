package cij.changerules.field;

import java.util.HashSet;
import java.util.Set;

public class FieldInformation {
	private String fieldName;
	private Set<String> fieldModifiers = new HashSet<>();
	private String fieldType;
	
	public FieldInformation() {
		
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Set<String> getFieldModifiers() {
		return fieldModifiers;
	}
	public void setFieldModifiers(Set<String> fieldModifiers) {
		this.fieldModifiers = fieldModifiers;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	@Override
	public boolean equals(Object obj) {
		FieldInformation field = (FieldInformation)obj;
		if(field.getFieldName().equals(this.getFieldName()))
			return true;
		return false;
	}
	@Override
	public int hashCode() {
		return getFieldName().hashCode();
	}
}
