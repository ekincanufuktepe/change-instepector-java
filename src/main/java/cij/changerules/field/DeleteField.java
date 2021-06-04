package cij.changerules.field;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.FieldInformationDataCollector;
import cij.grammar.java.JavaParseTree;

public class DeleteField extends ChangeRule{
	
	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;
	
	public DeleteField(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}
	
	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.DF_DELETE_FIELD;
		}
		return null;
	}
	
	@Override
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode) {
		FieldInformationDataCollector beforeChange = new FieldInformationDataCollector();
		beforeChange.collectFields(beforeChangeCode.getRootNode());

		FieldInformationDataCollector afterChange = new FieldInformationDataCollector();
		afterChange.collectFields(afterChangeCode.getRootNode());

		for(FieldInformation field : beforeChange.getFieldList()) {
			if(!afterChange.getFieldList().contains(field)) {
				return true;
			}
		}
		return false;
	}

}