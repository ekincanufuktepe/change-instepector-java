package cij.changerules.classinfo;

import java.util.HashSet;
import java.util.Set;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.ClassInformationDataCollector;
import cij.grammar.java.JavaParseTree;

public class DeleteParentClass extends ChangeRule{
	
	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;
	
	public DeleteParentClass(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}
	
	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.DPC_DELETE_PARENT_OF_CLASS;
		}
		return null;
	}

	@Override
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode) {
		ClassInformationDataCollector beforeChange = new ClassInformationDataCollector();
		beforeChange.collectClasses(beforeChangeCode.getRootNode());

		ClassInformationDataCollector afterChange = new ClassInformationDataCollector();
		afterChange.collectClasses(afterChangeCode.getRootNode());
			
		// The  first two for loops is for precautions just in case there are inner classes. Otherwise, this isn't actually a high complexity algorithm
		for(ClassInformation beforeClassInfo : beforeChange.getClassList()) {
			for(ClassInformation afterClassInfo : afterChange.getClassList()) {
				if(beforeClassInfo.getClassName().equals(afterClassInfo.getClassName())) {
					Set<String> beforeChangeParentClassSet = new HashSet<>();
					beforeChangeParentClassSet.addAll(beforeClassInfo.getParentClassNames());
					for(String parentClass : afterClassInfo.getParentClassNames()) {
						beforeChangeParentClassSet.remove(parentClass);
					}
					if(!beforeChangeParentClassSet.isEmpty()) {
						return true;
					}
				}
			}
		}
		return false;
	}
}