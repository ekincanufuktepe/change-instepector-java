package cij.changerules.method.body.info;

import java.util.HashMap;
import java.util.List;

public class MethodInvocation {
	private HashMap<String, List<String>> methodInvationInforamation;
	
	public MethodInvocation() {
		methodInvationInforamation = new HashMap<>();
	}

	public HashMap<String, List<String>> getMethodInvationInforamation() {
		return methodInvationInforamation;
	}

	public void setMethodInvationInforamation(HashMap<String, List<String>> methodInvationInforamation) {
		this.methodInvationInforamation = methodInvationInforamation;
	}

	@Override
	public String toString() {
		return "MethodInvocation [methodInvationInforamation=" + methodInvationInforamation + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this.methodInvationInforamation.entrySet().equals(((MethodInvocation)obj).getMethodInvationInforamation().entrySet())) {
			return true;
		}
		return false;
	}
}
