package cij.changerules.method;

import java.util.ArrayList;

import cij.changerules.method.body.MethodBodyInformation;
import cij.grammar.java.CodeComponentNode;

public class MethodInformation {
	
	private String methodName;
	private ArrayList<String> parameterList = new ArrayList<>();
	private ArrayList<String> parameterNameList = new ArrayList<>();
	private ArrayList<String> parameterTypeList = new ArrayList<>();
	private ArrayList<String> parameterModifierList = new ArrayList<>();
	private String returnType;
	private ArrayList<String> accessModifier = new ArrayList<>();
	private CodeComponentNode methodBody;
	private MethodBodyInformation methodBodyInformation;
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public ArrayList<String> getParameterList() {
		return parameterList;
	}
	public void setParameterList(ArrayList<String> parameterList) {
		this.parameterList = parameterList;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public ArrayList<String> getAccessModifier() {
		return accessModifier;
	}
	public void setAccessModifier(ArrayList<String> accessModifier) {
		this.accessModifier = accessModifier;
	}
	
	public String getMethodByNameReturnParamType() {
		return returnType + " " + methodName + " " + parameterTypeList;
	}
	
	@Override
	public String toString() {
		return "MethodInformation [methodName=" + methodName + ", parameterList=" + parameterList + ", returnType="
				+ returnType + ", accessModifier=" + accessModifier + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		MethodInformation method = (MethodInformation)obj;
		if(method.getMethodByNameReturnParamType().equals(this.getMethodByNameReturnParamType()))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return getMethodByNameReturnParamType().hashCode();
	}
	public ArrayList<String> getParameterNameList() {
		return parameterNameList;
	}
	public void setParameterNameList(ArrayList<String> parameterNameList) {
		this.parameterNameList = parameterNameList;
	}
	public ArrayList<String> getParameterTypeList() {
		return parameterTypeList;
	}
	public void setParameterTypeList(ArrayList<String> parameterTypeList) {
		this.parameterTypeList = parameterTypeList;
	}
	public CodeComponentNode getMethodBody() {
		return methodBody;
	}
	public void setMethodBody(CodeComponentNode methodBody) {
		this.methodBody = methodBody;
	}
	public ArrayList<String> getParameterModifierList() {
		return parameterModifierList;
	}
	public void setParameterModifierList(ArrayList<String> parameterModifierList) {
		this.parameterModifierList = parameterModifierList;
	}
	public MethodBodyInformation getMethodBodyInformation() {
		return methodBodyInformation;
	}
	public void setMethodBodyInformation(MethodBodyInformation methodBodyInformation) {
		this.methodBodyInformation = methodBodyInformation;
	}
	
	
}
