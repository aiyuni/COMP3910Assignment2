package Controllers;

import java.io.Serializable;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
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

	/*@Inject
    private TimesheetController timesheetController;
    //@Inject
  //  private TimesheetRowController timesheetRowController;
    @Inject
    private EmployeeController employeeController;
    */

    @EJB
    private TimesheetService service;
    
    private Employee currentEmployee;
   // private Date startWeek = getStartWeek();
    //private Date endWeek = getEndWeek();

    //private List<TimesheetRow> details = new ArrayList<>();

    private Timesheet timesheet;
    private List<Timesheet> timesheetList = new ArrayList<Timesheet>();
    private int weekNumber;
    
    Timesheet currentTimesheet;

    //private List<Timesheet> history;
    
    public Timesheet getTimesheet() {
    	return timesheet;
    }
    
    public void setTimesheet(Timesheet timeSheet) {
    	this.timesheet = timeSheet;
    }
    
    public List<Timesheet> getAllTimesheets(Employee emp){
    	currentEmployee = emp;
    	return service.getAllTimesheets(currentEmployee.getEmployeeId());
    }
    //no create/update methods for timesheet. All handled inside timesheetrowcontroller
    public List<Timesheet> getTimesheetList(){
    	if (timesheetList ==null || timesheetList.size() == 0) {
    		refreshList();
    	}
    	return timesheetList;
    }
    
    private void refreshList() {
    	for (int i = 0; i < service.getAllTimesheets(currentEmployee.getEmployeeId()).size();i++) {
    		timesheetList.add(service.getAllTimesheets(currentEmployee.getEmployeeId()).get(i));
    	}
    }

	public Timesheet getCurrentTimesheet() {
		return currentTimesheet;
	}

	public void setCurrentTimesheet(Timesheet currentTimesheet) {
		this.currentTimesheet = currentTimesheet;
	}
	
	public String goToTimesheet(int employeeId, Date startWeek, Date endWeek) {
		this.currentTimesheet = service.getCurrentTimesheet(employeeId, startWeek, endWeek);
		//this.currentTimesheet = timesheet;
		return "CurrentTimesheetView";
	}
	
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
    
}
