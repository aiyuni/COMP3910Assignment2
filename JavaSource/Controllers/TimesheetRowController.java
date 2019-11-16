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
import Models.TimesheetRowKey;
import Services.TimesheetRowService;

@Named
@SessionScoped
public class TimesheetRowController implements Serializable{
	
	@EJB
	private TimesheetRowService service;
	
	private TimesheetRow timesheetRow;
	private List<TimesheetRow> timesheetRowList = new ArrayList<>();
	
	private Timesheet currentTimesheet;

	//We use this method.
	public List<TimesheetRow> getAllTimesheetRows(Timesheet timesheet){
		currentTimesheet = timesheet;
		timesheetRowList = service.getAllTimesheetRows(currentTimesheet.getTimesheetId());
		timesheetRowList.add(new TimesheetRow(currentTimesheet.getTimesheetId()));
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
	
	/**
	 * Populates the timesheetRowList (called if the list is null)
	 */
	private void refreshList() {
		for (int i = 0; i < service.getAllTimesheetRows(currentTimesheet.getTimesheetId()).size(); i++) {
			timesheetRowList.add(service.getAllTimesheetRows(currentTimesheet.getTimesheetId()).get(i));
		}
		timesheetRowList.add(new TimesheetRow(currentTimesheet.getTimesheetId()));
	}

	/**
	 * Add method
	 * @param tsRow
	 */
	public void addTimesheetRow(TimesheetRow tsRow) {
		service.addTimesheetRow(tsRow);
		timesheetRowList.add(tsRow);
	}
	
	/**
	 * Update method
	 */
	public String updateTimesheetRow() {
		boolean hasPlaceholder = false;
		for (TimesheetRow ts : timesheetRowList) {
			if (!(ts.getCompPrimaryKey().getProjectId()==0)) {
				service.merge(ts);
			}else {
				hasPlaceholder = true;
			}
		}
		if (!hasPlaceholder) {
			timesheetRowList.add(new TimesheetRow(currentTimesheet.getTimesheetId()));  //this breaks the program somehow
		}
		//return "CurrentTimesheetView"; //after add/update, redirect to view page
		return "";
	}
	
	/**
	 * Delete method
	 * @param thisRow
	 * @return
	 */
	public String deleteTimesheetRow(TimesheetRow thisRow) {
		service.removeTimesheetRow(thisRow);
		timesheetRowList.remove(thisRow);
		return "";
	}
	
	public double getTotalHoursWorkedOnTimesheetRow(double mon, double tues, double wed, double thurs, double fri, 
			double sat, double sun) {
		return mon + tues + wed + thurs + fri + sat + sun;
	}
	
	public double getTotalHoursWorkedOnTimesheet() {
		double overallTotal = 0;
		for (TimesheetRow ts : timesheetRowList) {
			overallTotal += ts.getTotalHours();
		}
		return overallTotal;
	}
	
	public double getTotalHoursOnDay(int dayOfWeek) {
		double total = 0;
		for (TimesheetRow ts : timesheetRowList) {
			switch (dayOfWeek) {
				case 1: 
					total += ts.getMon();
					break;
				case 2:
					total += ts.getTues();
					break;
				case 3:
					total += ts.getWed();
					break;
				case 4:
					total += ts.getThurs();
					break;
				case 5:
					total += ts.getFri();
					break;
				case 6:
					total += ts.getSat();
					break;
				case 7: 
					total += ts.getSun();
					break;
				default: 
					System.out.println("Something went wrong... dayOfWeek invalid");
			}
		}
		return total;
	}
	
	
	//Getters and Setters
	public TimesheetRow getTimesheetRow() {
		if (timesheetRow == null) {
			timesheetRow = new TimesheetRow();
			timesheetRow.setCompPrimaryKey(new TimesheetRowKey());
		}
	/*	if (timesheetRow.getTimesheetId() == 0) {
			timesheetRow.setTimesheetId(currentTimesheet.getTimesheetId());
		} */
		if (timesheetRow.getCompPrimaryKey().getTimesheetId() == 0) {
			timesheetRow.getCompPrimaryKey().setTimesheetId(currentTimesheet.getTimesheetId());
		} 
		return timesheetRow;
	}
	
	public void setTimesheetRow(TimesheetRow timesheetRow) {
		this.timesheetRow = timesheetRow;
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
	
	public Timesheet getCurrentTimesheet() {
		return currentTimesheet;
	}

	public void setCurrentTimesheet(Timesheet currentTimesheet) {
		this.currentTimesheet = currentTimesheet;
	}
	
}
