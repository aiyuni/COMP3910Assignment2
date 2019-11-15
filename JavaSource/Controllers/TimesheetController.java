package Controllers;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
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

    private boolean toBeDeleted;
    private boolean toBeAdded;

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
    	if (timesheetList ==null) {
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
    
}
