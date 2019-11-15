package Controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import Models.Timesheet;
import Models.TimesheetRow;
import Services.TimesheetRowService;

@Named
@SessionScoped
public class TimesheetRowController implements Serializable{
	
	@EJB
	private TimesheetRowService service;
	
	private TimesheetRow timesheetRow;
	private List<TimesheetRow> timesheetRowList = new ArrayList<>();
	
	private Timesheet currentTimesheet;
	
	public TimesheetRow getTimesheetRow() {
		return timesheetRow;
	}
	
	public void setTimesheetRow(TimesheetRow timesheetRow) {
		this.timesheetRow = timesheetRow;
	}
	
	//We use this method.
	public List<TimesheetRow> getAllTimesheetRows(Timesheet timesheet){
		currentTimesheet = timesheet;
		timesheetRowList = service.getAllTimesheetRows(currentTimesheet.getTimesheetId());
		return service.getAllTimesheetRows(currentTimesheet.getTimesheetId());
	}
	
	//Gets all the timesheetrows in database
	public List<TimesheetRow> getAllTimesheetRows(){
		return service.getAllTimesheetRows();
	}
	
	/*overloaded method to get timesheetrows for the specific timesheetId. We cannot use this since we need to 
	 * dependency inject a timesheet object!
	 */
	public List<TimesheetRow> getAllTimesheetRows(int timesheetId){
		return service.getAllTimesheetRows(timesheetId);
	}
	
	public List<TimesheetRow> getTimesheetRowList(){
		if (timesheetRowList == null) {
			refreshList();
		}
		return timesheetRowList;
	}
	
	public void setTimesheetRowList(List<TimesheetRow> timesheetrowlist) {
		this.timesheetRowList = timesheetrowlist;
	}
	
	private void refreshList() {
		for (int i = 0; i < service.getAllTimesheetRows(currentTimesheet.getTimesheetId()).size(); i++) {
			timesheetRowList.add(service.getAllTimesheetRows(currentTimesheet.getTimesheetId()).get(i));
		}
	}

	/**
	 * Update method
	 */
	public void updateTimesheetRow() {
		for (TimesheetRow ts : timesheetRowList) {
			service.merge(ts);
		}
	}
}
