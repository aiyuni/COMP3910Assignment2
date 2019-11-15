package Models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TimesheetRow")
public class TimesheetRow implements Serializable {

    private static final long serialVersionUID = 1L;

  /*  @Id
    @Column(name = "timesheetrowid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int timesheetRowId; */
    
    @EmbeddedId
    private TimesheetRowKey compPrimaryKey;

	/*@Column(name = "timesheetid")
    private int timesheetId;

    @Column(name = "projectid")
    private int projectId;

    @Column(name = "workpackage")
    private String workPackage = ""; */

    @Column(name = "monday")
    private Integer mon = 0;

    @Column(name = "tuesday")
    private Integer tues = 0;

    @Column(name = "wednesday")
    private Integer wed = 0;

    @Column(name = "thursday")
    private Integer thurs = 0;

    @Column(name = "friday")
    private Integer fri = 0;

    @Column(name = "saturday")
    private Integer sat = 0;

    @Column(name = "sunday")
    private Integer sun = 0;

    @Column(name = "notes")
    private String notes = "";

    /**
     * Default constructor
     */
    public TimesheetRow() {

    }

    
  /*  public int getTimesheetRowId() {
        return timesheetRowId;
    }

    public void setTimesheetRowId(int timesheetRowId) {
        this.timesheetRowId = timesheetRowId;
    } */
    
    public double getTotalHours() {
    	return mon + tues + wed + thurs + fri + sat + sun;
    }
    
    public TimesheetRowKey getCompPrimaryKey() {
		return compPrimaryKey;
	}


	public void setCompPrimaryKey(TimesheetRowKey compPrimaryKey) {
		this.compPrimaryKey = compPrimaryKey;
	}

   /* public int getTimesheetId() {
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
    }  */

    public Integer getMon() {
        return mon;
    }

    
    public void setMon(Integer monday) {
        this.mon = monday;
    }

    public Integer getTues() {
        return tues;
    }


    public void setTues(Integer tuesday) {
        this.tues = tuesday;
    }

    public Integer getWed() {
        return wed;
    }

    public void setWed(Integer wednesday) {
        this.wed = wednesday;
    }

    public Integer getThurs() {
        return thurs;
    }

    public void setThurs(Integer thursday) {
        this.thurs = thursday;
    }

    public Integer getFri() {
        return fri;
    }

    public void setFri(Integer friday) {
        this.fri = friday;
    }

    public Integer getSat() {
        return sat;
    }

    public void setSat(Integer saturday) {
        this.sat = saturday;
    }

    public Integer getSun() {
        return sun;
    }

    public void setSun(Integer sunday) {
        this.sun = sunday;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
