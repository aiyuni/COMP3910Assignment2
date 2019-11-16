package Models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TimesheetRowKey implements Serializable {
	@Column(name = "timesheetId", nullable = false)
	private int timesheetId = 0;
	
	@Column(name = "projectId", nullable = false)
	private int projectId = 0;
	
	@Column(name = "workPackage", nullable = false)
	private String workPackage = "";
	
	public TimesheetRowKey() {
		
	}
	
	public TimesheetRowKey(int timesheetId) {
		this.timesheetId = timesheetId;
		this.workPackage = "New Work Package";
	}
	
	public int getTimesheetId() {
		return timesheetId;
	}

	public void setTimesheetId(int timesheetId) {
		this.timesheetId = timesheetId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getWorkPackage() {
		return workPackage;
	}

	public void setWorkPackage(String workPackage) {
		this.workPackage = workPackage;
	}

}
