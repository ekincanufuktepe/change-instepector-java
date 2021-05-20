package cij.changerules.classinfo;

import java.util.HashSet;
import java.util.Set;

public class ClassInformation {

	private String className;
	private Set<String> parentClassNames = new HashSet<>();
	private Set<String> modifiers = new HashSet<>();
	
	public ClassInformation() {

	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Set<String> getParentClassNames() {
		return parentClassNames;
	}

	public void setParentClassNames(Set<String> parentClassNames) {
		this.parentClassNames = parentClassNames;
	}

	public Set<String> getModifiers() {
		return modifiers;
	}

	public void setModifiers(Set<String> modifiers) {
		this.modifiers = modifiers;
	}

	@Override
	public String toString() {
		return "ClassInformation [className=" + className + ", modifiers=" + modifiers + "]";
	}

	@Override
	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((className == null) ? 0 : className.hashCode());
//		result = prime * result + ((modifiers == null) ? 0 : modifiers.hashCode());
//		result = prime * result + ((parentClassNames == null) ? 0 : parentClassNames.hashCode());
//		return result;
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassInformation other = (ClassInformation) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (modifiers == null) {
			if (other.modifiers != null)
				return false;
		} else if (!modifiers.equals(other.modifiers))
			return false;
		if (parentClassNames == null) {
			if (other.parentClassNames != null)
				return false;
		} else if (!parentClassNames.equals(other.parentClassNames))
			return false;
		return true;
	}
	
	
	
}
