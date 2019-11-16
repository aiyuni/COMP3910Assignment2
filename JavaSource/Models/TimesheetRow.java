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
    
    @EmbeddedId
    private TimesheetRowKey compPrimaryKey;


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
    	this.compPrimaryKey = new TimesheetRowKey();
    }
    
    public TimesheetRow(int timesheetId) {
    	this.compPrimaryKey = new TimesheetRowKey(timesheetId);
    }

    
    public double getTotalHours() {
    	return mon + tues + wed + thurs + fri + sat + sun;
    }
    
    public TimesheetRowKey getCompPrimaryKey() {
		return compPrimaryKey;
	}


	public void setCompPrimaryKey(TimesheetRowKey compPrimaryKey) {
		this.compPrimaryKey = compPrimaryKey;
	}

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
