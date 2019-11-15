package Services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import Models.TimesheetRow;

@Stateless
public class TimesheetRowService {

	@PersistenceContext(name = "TimesheetApp")
	private EntityManager em;

	public void addTimesheetRow(TimesheetRow timesheetRow) {
		if(em.contains(timesheetRow)) {
			System.out.println("merging inside addTimesheetRow");
			em.merge(timesheetRow);
		}
		else {
			em.persist(timesheetRow);
		}

	}
	
    public TimesheetRow find(int id) {
        return em.find(TimesheetRow.class, id);
    }
    
    public void merge(TimesheetRow timesheetRow) {
    	em.merge(timesheetRow);
    }
    
    public void removeTimesheetRow(TimesheetRow thisEmployee) {
        //attach product
       thisEmployee = find(thisEmployee.getTimesheetId());
        em.remove(thisEmployee);
    }
    
    /**
     * Gets a list of all the timesheetRows belong to the timesheetId
     * @return
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
