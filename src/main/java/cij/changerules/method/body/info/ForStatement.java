package cij.changerules.method.body.info;

import java.util.ArrayList;
import java.util.List;

public class ForStatement {
	// Basic For-statement
	private List<Expression> forInits = new ArrayList<Expression>();
	private List<Expression> forExpressions = new ArrayList<Expression>();
	private List<Expression> forUpdates = new ArrayList<Expression>();
	// Enhanced For-statement
	private List<Expression> forUnannType = new ArrayList<Expression>();
	private List<Expression> forVariableDeclaratorId = new ArrayList<Expression>();
	
	public List<Expression> getForInits() {
		return forInits;
	}

	public void setForInits(List<Expression> forInits) {
		this.forInits = forInits;
	}

	public List<Expression> getForExpressions() {
		return forExpressions;
	}

	public void setForExpressions(List<Expression> forExpressions) {
		this.forExpressions = forExpressions;
	}

	public List<Expression> getForUpdates() {
		return forUpdates;
	}

	public void setForUpdates(List<Expression> forUpdates) {
		this.forUpdates = forUpdates;
	}

	public boolean equalsForExpressions(Object obj) {
		if(this.forExpressions.equals(((ForStatement)obj).getForExpressions())) {
			return true;
		}
		return false;
	}
	
	public boolean equalsForUpdates(Object obj) {
		if(this.forUpdates.equals(((ForStatement)obj).getForUpdates())) {
			return true;
		}
		return false;
	}
	
	public boolean equalsForInits(Object obj) {
		if(this.forInits.equals(((ForStatement)obj).getForInits())) {
			return true;
		}
		return false;
	}
	
	public boolean equalsForUnannTypes(Object obj) {
		if(this.forUnannType.equals(((ForStatement)obj).getForUnannType())) {
			return true;
		}
		return false;
	}
	
	public boolean equalsForforVariableDeclaratorIds(Object obj) {
		if(this.forVariableDeclaratorId.equals(((ForStatement)obj).getForVariableDeclaratorId())) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this.equalsForExpressions(obj) && 
				this.equalsForInits(obj) &&
				this.equalsForUpdates(obj) &&
				this.equalsForUnannTypes(obj) &&
				this.equalsForforVariableDeclaratorIds(obj)) {
			return true;
		}
		return false;
	}

	

	@Override
	public String toString() {
		return "ForStatement [forInits=" + forInits + ", forExpressions=" + forExpressions + ", forUpdates="
				+ forUpdates + ", forUnannType=" + forUnannType + ", forVariableDeclaratorId=" + forVariableDeclaratorId
				+ "]";
	}

	public List<Expression> getForUnannType() {
		return forUnannType;
	}

	public void setForUnannType(List<Expression> forUnannType) {
		this.forUnannType = forUnannType;
	}

	public List<Expression> getForVariableDeclaratorId() {
		return forVariableDeclaratorId;
	}

	public void setForVariableDeclaratorId(List<Expression> forVariableDeclaratorId) {
		this.forVariableDeclaratorId = forVariableDeclaratorId;
	}
	
	
}
