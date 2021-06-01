package cij.changerules.field;

import cij.changerules.ChangeCategory;
import cij.changerules.ChangeRule;
import cij.changerules.FieldInformationDataCollector;
import cij.grammar.java.JavaParseTree;

public class DecreaseFieldAccessibility extends ChangeRule {

	private JavaParseTree treeBeforeChange;
	private JavaParseTree treeAfterChange;
	
	public DecreaseFieldAccessibility(JavaParseTree treeBeforeChange, JavaParseTree treeAfterChange) {
		this.treeBeforeChange = treeBeforeChange;
		this.treeAfterChange = treeAfterChange;
	}

	@Override
	public ChangeCategory getCategory() {
		if(isChangeCategory(treeBeforeChange, treeAfterChange)) {
			return ChangeCategory.DAF_DECREASE_ACCESSIBILITY_FIELD;
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
					if((beforeChangeField.getFieldModifiers().contains("public") &&
							afterChangeField.getFieldModifiers().contains("private")) || 
							(!beforeChangeField.getFieldModifiers().contains("private") &&
									afterChangeField.getFieldModifiers().contains("private"))) {
						return true;
					}
				}
			}
		}
		return false;
	}

}