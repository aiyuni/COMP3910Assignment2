package Controllers;

import java.io.Serializable;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import Models.Employee;
import Models.Timesheet;
import Services.TimesheetService;

@Named("timesheetController")
@SessionScoped
public class TimesheetController implements Serializable {

    @EJB
    private TimesheetService service;
    
    private Employee currentEmployee;

    private Timesheet timesheet;
    private List<Timesheet> timesheetList = new ArrayList<Timesheet>();
    
    Timesheet currentTimesheet;

    
    /**
     * Method to get a list of timesheets belonging to an employee
     * @param emp the employee to get timesheets for 
     * @return a list of timesheets for that employee
     */
    public List<Timesheet> getAllTimesheets(Employee emp){
    	currentEmployee = emp;
    	return service.getAllTimesheets(currentEmployee.getEmployeeId());
    }
    
    /**
     * Getter to get the timesheet list
     * @return the timesheet list
     */
    public List<Timesheet> getTimesheetList(){
    	if (timesheetList ==null || timesheetList.size() == 0) {
    		refreshList();
    	}
    	return timesheetList;
    }
    
    /**
     * Helper method to refresh the timesheet list
     */
    private void refreshList() {
    	for (int i = 0; i < service.getAllTimesheets(currentEmployee.getEmployeeId()).size();i++) {
    		timesheetList.add(service.getAllTimesheets(currentEmployee.getEmployeeId()).get(i));
    	}
    }

    /**
     * Method to go to a specific timesheet for a specific employee
     * @param employeeId the specific employee
     * @param startWeek the start week of the timesheet
     * @param endWeek the end week of the timesheet
     * @return the navigation string
     */
	public String goToTimesheet(int employeeId, Date startWeek, Date endWeek) {
		this.currentTimesheet = service.getCurrentTimesheet(employeeId, startWeek, endWeek);
		return "CurrentTimesheetView";
	}
	
	/**
	 * Method to add a new weeksheet belonging to the current week if it doesn't already exist.
	 */
	public void addNewTimesheet() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		ZoneId zoneId = ZoneId.of("America/Vancouver");
		LocalDate today = LocalDate.now(zoneId);
		LocalDate currentFriday = today.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
		LocalDate currentSaturday = currentFriday.minusDays(6);
		System.out.println("Current Friday is: " + currentFriday);
		System.out.println("Current Saturday is: " + currentSaturday);
		
		timesheetList = getAllTimesheets(currentEmployee);
		for(Timesheet ts : timesheetList) {
			//ts.getEndOfWeek() is sql.date, not util.date, so we must use toLocalDate instead of toInstant
			LocalDate tsEndOfWeek = ts.getEndWeek().toLocalDate();  
			System.out.println("tsEndOfWeek is: " + tsEndOfWeek);
			if (tsEndOfWeek.equals(currentFriday)) {
				System.out.println("Timesheet for " + tsEndOfWeek + " already exists!");
				context.addMessage(null,  new FacesMessage("Failed to add Timesheet; "
						+ "Timesheet for week ending in " + tsEndOfWeek + " already exists."));
				return;
			}
		}
		
		Timesheet newTs = new Timesheet(currentEmployee.getEmployeeId(), Date.valueOf(currentSaturday),
				Date.valueOf(currentFriday));
		service.addTimesheet(newTs);
		timesheetList.add(newTs);
	}
	
	/**
	 * Method to go to the Edit Mode of a timesheet
	 * @return the navigation string
	 */
	public String goToEditTimesheet() {
		FacesContext context = FacesContext.getCurrentInstance();
		ZoneId zoneId = ZoneId.of("America/Vancouver");
		LocalDate today = LocalDate.now(zoneId);
		LocalDate currentFriday = today.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
		LocalDate currentSaturday = currentFriday.minusDays(6);
		System.out.println("Current Friday is: " + currentFriday);
		
		if (currentTimesheet.getEndWeek().toLocalDate().equals(currentFriday)) {
			return "CurrentTimesheetEdit";
		} else {
			context.addMessage(null, new FacesMessage("Cannot edit a timesheet from previous weeks."));
			return null;
		}
		
	}
	
	/**
	 * Calculates the current week of the year based on the timesheet end week.
	 * @return the current week of the year
	 */
    public String getWeekOfYear() {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(currentTimesheet.getEndWeek());
    	String week = String.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
    	return week;
    }
    
    /**
     * Getter for the timesheet
     * @return the timesheet to get
     */
    public Timesheet getTimesheet() {
    	return timesheet;
    }
    
    /**
     * Setter for the timesheet
     * @param timeSheet the timesheet to set
     */
    public void setTimesheet(Timesheet timeSheet) {
    	this.timesheet = timeSheet;
    }
    
    /**
     * Getter for the current timesheet;
     * @return the current timesheet to get
     */
	public Timesheet getCurrentTimesheet() {
		return currentTimesheet;
	}

	/**
	 * Setter for the current timesheet
	 * @param currentTimesheet the current timesheet to set
	 */
	public void setCurrentTimesheet(Timesheet currentTimesheet) {
		this.currentTimesheet = currentTimesheet;
	}
	
}
