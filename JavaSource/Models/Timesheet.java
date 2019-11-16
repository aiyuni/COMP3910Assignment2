package Models;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Timesheet")
public class Timesheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "timesheetid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int timesheetId;

    @Column(name = "employeeid")
    private int employeeId;

    @Column(name = "startweek")
    private Date startWeek;
    
    @Column(name = "endweek")
    private Date endWeek;

    /**
     * Default constructor
     */
    public Timesheet() {

    }
    
    public Timesheet(int empId, Date startWeek, Date endWeek) {
    	this.employeeId = empId;
    	this.startWeek = startWeek;
    	this.endWeek = endWeek;
    }

    /**
     * Gets the TimesheetID
     * @return the TimesheetId
     */
    public int getTimesheetId() {
        return timesheetId;
    }

    /**
     * Sets the TimesheetId 
     * @param timesheetId the timesheetId to set
     */
    public void setTimesheetId(int timesheetId) {
        this.timesheetId = timesheetId;
    }

    /**
     * Gets the EmployeeId
     * @return the EmployeeId
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the Employee Id 
     * @param employeeId id of employee
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Gets the endWeek of the Timesheet
     * @return the endWeek
     */
    public Date getEndWeek() {
        return endWeek;
    }

    /**
     * Sets the endWeek of the Timesheet
     * @param the endWeek to set
     */
    public void setEndWeek(Date endWeek) {
        this.endWeek = endWeek;
    }

    /**
     * Gets the start of the week of the Timesheet
     * @return the start of the Week
     */
    public Date getStartWeek() {
        return startWeek;
    }

    /**
     * Sets the startWeek of Timesheet
     * @param the startWeek to set
     */
    public void setStartWeek(Date startWeek) {
        this.startWeek = startWeek;
    }


}
