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
	/**
	 * Gets all timesheet rows for a specific timesheet
	 * @param timesheet the specific timesheet 
	 * @return a list of all the timesheet rows belonging to that specific timesheet
	 */
	public List<TimesheetRow> getAllTimesheetRows(Timesheet timesheet){
		currentTimesheet = timesheet;
		timesheetRowList = service.getAllTimesheetRows(currentTimesheet.getTimesheetId());
		timesheetRowList.add(new TimesheetRow(currentTimesheet.getTimesheetId()));
		return service.getAllTimesheetRows(currentTimesheet.getTimesheetId());
	}
	
	//Not used
	/**
	 * Gets all the timesheet rows in database.
	 * @return list of all timesheet rows in the database. 
	 */
	public List<TimesheetRow> getAllTimesheetRows(){
		return service.getAllTimesheetRows();
	}
	
	//Not used since we want a Timesheet object to be DIed to this class, and not just a Timesheet Id
	/**
	 * overloaded method to get timesheetrows for the specific timesheetId. 
	 */
	public List<TimesheetRow> getAllTimesheetRows(int timesheetId){
		return service.getAllTimesheetRows(timesheetId);
	}
	
	/**
	 * Method to populate the timesheetRowList with the newest data in the database
	 */
	private void refreshList() {
		for (int i = 0; i < service.getAllTimesheetRows(currentTimesheet.getTimesheetId()).size(); i++) {
			timesheetRowList.add(service.getAllTimesheetRows(currentTimesheet.getTimesheetId()).get(i));
		}
		timesheetRowList.add(new TimesheetRow(currentTimesheet.getTimesheetId()));
	}

	/**
	 * Method to add a timesheet row
	 * @param tsRow the timesheetrow to add
	 */
	public void addTimesheetRow(TimesheetRow tsRow) {
		service.addTimesheetRow(tsRow);
		timesheetRowList.add(tsRow);
	}
	
	/**
	 * Method to update timesheet rows
	 */
	public String updateTimesheetRow() {
		boolean hasPlaceholder = false;
		TimesheetRow tempTsr = null;
		for (TimesheetRow ts : timesheetRowList) {
			if (!(ts.getCompPrimaryKey().getProjectId()==0)) {
				if(service.merge(ts) == false) {
					tempTsr = ts;
				};
			}else {
				hasPlaceholder = true;
			}
		}
		if(tempTsr != null) {
			//timesheetRowList.remove(tempTsr);
		}
		if (!hasPlaceholder) {
			timesheetRowList.add(new TimesheetRow(currentTimesheet.getTimesheetId()));  //this breaks the program somehow
		}
		//return "CurrentTimesheetView"; //after add/update, redirect to view page
		return "";
	}
	
	/**
	 * Method to delete a timesheet row
	 * @param thisRow the timesheet row to delete
	 * @return
	 */
	public String deleteTimesheetRow(TimesheetRow thisRow) {
		service.removeTimesheetRow(thisRow);
		timesheetRowList.remove(thisRow);
		return "";
	}
	
	/**
	 * Method to calculate and return the total hours worked for a timesheet row
	 * @param mon hours worked on monday
	 * @param tues hours worked on tuesday
	 * @param wed  hours worked on wednesday
	 * @param thurs hours worked on Thursday
	 * @param fri hours worked on Friday
	 * @param sat hours worked on Saturday
	 * @param sun hours worked on Sunday
	 * @return the total hours worked in the timesheet row 
	 */
	public double getTotalHoursWorkedOnTimesheetRow(double mon, double tues, double wed, double thurs, double fri, 
			double sat, double sun) {
		return mon + tues + wed + thurs + fri + sat + sun;
	}
	
	/**
	 * Method to calculate and return the total hours worked in the timesheet
	 * @return the total hours worked in the timesheet
	 */
	public double getTotalHoursWorkedOnTimesheet() {
		double overallTotal = 0;
		for (TimesheetRow ts : timesheetRowList) {
			overallTotal += ts.getTotalHours();
		}
		return overallTotal;
	}
	
	/**
	 * Method to calculate and return the total number of hours worked in a given day of the week
	 * @param dayOfWeek the day of week
	 * @return the total number of hours worked in a given day of the week
	 */
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
	
	
	/**
	 * Getter for timesheetRow
	 * @return the timesheetRow
	 */
	public TimesheetRow getTimesheetRow() {
		if (timesheetRow == null) {
			timesheetRow = new TimesheetRow();
			timesheetRow.setCompPrimaryKey(new TimesheetRowKey());
		}
		if (timesheetRow.getCompPrimaryKey().getTimesheetId() == 0) {
			timesheetRow.getCompPrimaryKey().setTimesheetId(currentTimesheet.getTimesheetId());
		} 
		return timesheetRow;
	}
	
	/**
	 * Setter for timesheetRow
	 * @param timesheetRow the timesheetRow to set
	 */
	public void setTimesheetRow(TimesheetRow timesheetRow) {
		this.timesheetRow = timesheetRow;
	}
	
	/**
	 * Getter for the timesheetRow list
	 * @return the timesheetRow list
	 */
	public List<TimesheetRow> getTimesheetRowList(){
		if (timesheetRowList == null) {
			refreshList();
		}
		return timesheetRowList;
	}
	
	/**
	 * Setter for the timesheetRow list
	 * @param timesheetrowlist the timesheetRow list to set
	 */
	public void setTimesheetRowList(List<TimesheetRow> timesheetrowlist) {
		this.timesheetRowList = timesheetrowlist;
	}
	
	/**
	 * Getter for the current timesheet
	 * @return the current timesheet
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
