package Services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import Models.TimesheetRow;
import Models.TimesheetRowKey;

@Stateless
public class TimesheetRowService {

	@PersistenceContext(name = "TimesheetApp")
	private EntityManager em;

	/**
	 * Method to add a timesheetRow to database
	 * @param timesheetRow the timesheetRow to add
	 */
	public void addTimesheetRow(TimesheetRow timesheetRow) {
		if(em.contains(timesheetRow)) {
			System.out.println("merging inside addTimesheetRow");
			em.merge(timesheetRow);
		}
		else {
			em.persist(timesheetRow);
		}

	}
	
	/**
	 * Method to find a timesheetRow in the database based on its primary key
	 * @param key the composite primary key
	 * @return the TimesheetRow
	 */
    public TimesheetRow find(TimesheetRowKey key) {
        return em.find(TimesheetRow.class, key);
    }
    
    //testing stuff out by returning bool
    /**
     * Method to update a timesheetRow
     * @param timesheetRow the timesheetRow to merge
     * @return true if the database already contains the timesheet, false otherwise
     */
    public boolean merge(TimesheetRow timesheetRow) {
    	if(em.contains(timesheetRow)){
        	em.merge(timesheetRow);
        	return true;
    	} else {
    		em.merge(timesheetRow);
    		return false;
    	}
    }
    
    /**
     * Method to remove a timesheetRow from the database
     * @param thisRow the timesheetRow to remove
     */
    public void removeTimesheetRow(TimesheetRow thisRow) {
        //attach product
       thisRow = find(thisRow.getCompPrimaryKey());
        em.remove(thisRow);
    }
    
    /**
     * Gets a list of all the timesheetRows in the database belong to the timesheetId
     * @return a list of all the timesheetRows belonging to the timesheetId
     */
    public List<TimesheetRow> getAllTimesheetRows(int timesheetId){
    	
    	TypedQuery<TimesheetRow> query = em.createQuery(
	                 "select ps from TimesheetRow ps " +
	                 "where timesheetId=:timesheetId",
		                 TimesheetRow.class); 
    	query.setParameter("timesheetId", timesheetId);  //set query parameter for timesheetID
	   
		java.util.List<TimesheetRow> timesheetRowList = query.getResultList();
		
		TimesheetRow[] psarray = new TimesheetRow[timesheetRowList.size()];
		for (int i=0; i < psarray.length; i++) {
			psarray[i] = timesheetRowList.get(i);
		}
		
		ArrayList<TimesheetRow> returningTimesheetRowList = new ArrayList<TimesheetRow>();
		for (int i = 0; i < timesheetRowList.size(); i++) {
			returningTimesheetRowList.add(timesheetRowList.get(i));
		}
		//return Arrays.asList(psarray);
		return returningTimesheetRowList;
    
    }
    
    /**
     * Gets a list of all the timesheetRows in the database
     * @return list of all timesheetRows in the database
     */
    public List<TimesheetRow> getAllTimesheetRows(){
    	
    	TypedQuery<TimesheetRow> query = em.createQuery(
	                 "select ps from TimesheetRow ps",
		                 TimesheetRow.class); 
	   
		java.util.List<TimesheetRow> timesheetRowList = query.getResultList();
		
		TimesheetRow[] psarray = new TimesheetRow[timesheetRowList.size()];
		for (int i=0; i < psarray.length; i++) {
			psarray[i] = timesheetRowList.get(i);
		}
		
		ArrayList<TimesheetRow> returningTimesheetRowList = new ArrayList<TimesheetRow>();
		for (int i = 0; i < timesheetRowList.size(); i++) {
			returningTimesheetRowList.add(timesheetRowList.get(i));
		}
		//return Arrays.asList(psarray);
		return returningTimesheetRowList;
    
    }
}
