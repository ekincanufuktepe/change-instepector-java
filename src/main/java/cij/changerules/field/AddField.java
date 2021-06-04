package cij.changerules.field;

import java.util.HashSet;
import java.util.Set;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.FieldInformationDataCollector;
import cij.grammar.java.JavaParseTree;

public class AddField extends ChangeRule{
	
	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;
	
	public AddField(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}
	
	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.AF_ADD_FIELD;
		}
		return null;
	}

	@Override
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode) {
		FieldInformationDataCollector beforeChange = new FieldInformationDataCollector();
		beforeChange.collectFields(beforeChangeCode.getRootNode());

		FieldInformationDataCollector afterChange = new FieldInformationDataCollector();
		afterChange.collectFields(afterChangeCode.getRootNode());
		
		for(FieldInformation field : afterChange.getFieldList()) {
			if(!beforeChange.getFieldList().contains(field)) {
				return true;
			}
		}
		return false;
	}

}
