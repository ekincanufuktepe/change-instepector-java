package cij.changerules;

public enum ChangeCategory {
	
	// Class change types
	AC_ADD_CLASS,
	DC_DELETE_CLASS,
	IAC_INCREASE_ACCESSIBILITY_CLASS,
	DAC_DECREASE_ACCESSIBILITY_CLASS,
	AFC_ADD_FINAL_MODIFIER_CLASS,
	DFC_DELETE_FINAL_MODIFIER_CLASS,
	ASC_ADD_STATIC_MODIFIER_CLASS,
	DSC_DELETE_STATIC_MODIFIER_CLASS,
	AAbC_ADD_ABSTRACT_MODIFIER_CLASS,
	DAbC_DELETE_ABSTRACT_MODIFIER_CLASS,
	CNC_CHANGE_NAME_CLASS,
	APC_ADD_PARENT_TO_CLASS,
	DPC_DELETE_PARENT_OF_CLASS,
	CPC_CHANGE_PARENT_CLASS,
	
	// Method Change types
	AM_ADD_METHOD,
	DM_DELETE_METHOD,
	IAM_INCREASE_ACCESSIBILITY_METHOD, // Example: private to public
	DAM_DECREASE_ACCESSIBILITY_METHOD, // Example: public to private
	CM_CHANGE_METHOD,
	AFM_ADD_FINAL_MODIFIER_METHOD,
	DFM_DELETE_FINAL_MODIFIER_METHOD,
	ASM_ADD_STATIC_MODIFIER_METHOD,
	DSM_DELETE_STATIC_MODIFIER_METHOD,
	AAbM_ADD_ABSTRACT_MODIFIER_METHOD,
	DAbM_DELETE_ABSTRACT_MODIFIER_METHOD,
	CNM_CHANGE_NAME_METHOD, // Not used
	CRM_CHANGE_RETURN_TYPE_METHOD,
	CNPM_CHANGE_NAME_OF_PARAMETERS_METHOD,
	CPM_CHANGE_PARAMETERS_OF_METHOD, // Except for the change of the names of the parameters
	
	// Method Body Change types
	MCA_METHOD_CALL_ADDED,
	MCD_METHOD_CALL_DELETED,
	AIS_ADD_IF_STATEMENT,
	DIS_IF_STATEMENT,
	AFS_ADD_FOR_STATEMENT,
	DFS_DELETE_FOR_STATEMENT,
	AWS_ADD_WHILE_STATEMENT,
	DWS_DELETE_WHLE_STATEMENT,
	
	// Field Change types
	AF_ADD_FIELD, // A field decleration
	DF_DELETE_FIELD,
	IAF_INCREASE_ACCESSIBILITY_FIELD,
	DAF_DECREASE_ACCESSIBILITY_FIELD,
	AFF_ADD_FINAL_MODIFIER_FIELD,
	DFF_DELETE_FINAL_MODIFIER_FIELD,
	ASF_ADD_STATIC_MODIFIER_FIELD,
	DSF_DELETE_STATIC_MODIFIER_FIELD,
	CTF_CHANGE_TYPE_FIELD,
	CNF_CHANGE_NAME_FIELD
}
