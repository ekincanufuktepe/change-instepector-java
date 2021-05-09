package cij.changerules.method;

import java.util.ArrayList;

public class MethodInformation {
	
	private String methodName;
	private ArrayList<String> parameterList = new ArrayList<>();
	private String returnType;
	private ArrayList<String> accessModifier = new ArrayList<>();
	
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
	
	public String getMethodByNameReturnParam() {
		return returnType + " " + methodName + " " + parameterList;
	}
	
	@Override
	public String toString() {
		return "MethodInformation [methodName=" + methodName + ", parameterList=" + parameterList + ", returnType="
				+ returnType + ", accessModifier=" + accessModifier + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		MethodInformation method = (MethodInformation)obj;
		if(method.getMethodByNameReturnParam().equals(this.getMethodByNameReturnParam()))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return getMethodByNameReturnParam().hashCode();
	}
}