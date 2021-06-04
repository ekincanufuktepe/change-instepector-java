package cij.changerules.field;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.FieldInformationDataCollector;
import cij.grammar.java.JavaParseTree;

public class AddStaticModifierField extends ChangeRule {

	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;
	
	public AddStaticModifierField(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.ASF_ADD_STATIC_MODIFIER_FIELD;
		}
		return null;
	}

	@Override
	public boolean isChangeCategory(JavaParseTree beforeChangeCode, JavaParseTree afterChangeCode) {
		FieldInformationDataCollector beforeChange = new FieldInformationDataCollector();
		beforeChange.collectFields(beforeChangeCode.getRootNode());
		FieldInformationDataCollector afterChange = new FieldInformationDataCollector();
		afterChange.collectFields(afterChangeCode.getRootNode());
		
		for(FieldInformation beforeChangeField : beforeChange.getFieldList()) {
			for(FieldInformation afterChangeField : afterChange.getFieldList()) {
				if(beforeChangeField.getFieldName().equals(afterChangeField.getFieldName())) {
					if((!beforeChangeField.getFieldModifiers().contains("static") &&
							afterChangeField.getFieldModifiers().contains("static"))) {
						return true;
					}
				}
			}
		}
		return false;
	}

}