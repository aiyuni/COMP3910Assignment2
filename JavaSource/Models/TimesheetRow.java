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
    private Double mon = 0.0;

    @Column(name = "tuesday")
    private Double tues = 0.0;

    @Column(name = "wednesday")
    private Double wed = 0.0;

    @Column(name = "thursday")
    private Double thurs = 0.0;

    @Column(name = "friday")
    private Double fri = 0.0;

    @Column(name = "saturday")
    private Double sat = 0.0;

    @Column(name = "sunday")
    private Double sun = 0.0;

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

    public Double getMon() {
        return mon;
    }

    
    public void setMon(Double monday) {
        this.mon = monday;
    }

    public Double getTues() {
        return tues;
    }


    public void setTues(Double tuesday) {
        this.tues = tuesday;
    }

    public Double getWed() {
        return wed;
    }

    public void setWed(Double wednesday) {
        this.wed = wednesday;
    }

    public Double getThurs() {
        return thurs;
    }

    public void setThurs(Double thursday) {
        this.thurs = thursday;
    }

    public Double getFri() {
        return fri;
    }

    public void setFri(Double friday) {
        this.fri = friday;
    }

    public Double getSat() {
        return sat;
    }

    public void setSat(Double saturday) {
        this.sat = saturday;
    }

    public Double getSun() {
        return sun;
    }

    public void setSun(Double sunday) {
        this.sun = sunday;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
