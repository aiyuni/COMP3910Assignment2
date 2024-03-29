package Services;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import Models.Timesheet;

@Stateless
public class TimesheetService {
	
	@PersistenceContext(name = "TimesheetApp")
	private EntityManager em;

	/**
	 * Method to add a timesheet to the database
	 * @param ts the timesheet to add
	 */
	public void addTimesheet(Timesheet ts) {
		if(em.contains(ts)) {
			System.out.println("merging inside addTimesheet");
			em.merge(ts);
		}
		else {
			em.persist(ts);
		}

	}
	
	/**
	 * Method to find a timesheet in the database based on its id
	 * @param id the Timesheet Id
	 * @return the timesheet that is found
	 */
    public Timesheet find(int id) {
        return em.find(Timesheet.class, id);
    }
    
    /**
     * Method to update a timesheet in the database
     * @param ts the timesheet to update
     */
    public void merge(Timesheet ts) {
    	try {
    		em.merge(ts);
    	} catch(Exception e) {
    		System.out.println("Something went wrong in services.merge(): " + e.toString());
    	}
    }
    
    /**
     * Method to remove a timesheet in the database
     * @param thisTimesheet the timesheet to delete
     */
    public void removeEmployee(Timesheet thisTimesheet) {
        //attach product
       thisTimesheet = find(thisTimesheet.getEmployeeId());
        em.remove(thisTimesheet);
    }
    
    /**
     * Gets a list of all the timesheets in the database belong to the employeeId
     * @param employeeId the employeeId to find timesheets for
     * @return the list of all timesheets for that employeeId
     */
    public List<Timesheet> getAllTimesheets(int employeeId){
    	
    	TypedQuery<Timesheet> query = em.createQuery(
	                 "select ps from Timesheet ps " +
	                 "where employeeId=:employeeId",
		                 Timesheet.class); 
    	query.setParameter("employeeId", employeeId);  //set query parameter for employeeId
	   
		java.util.List<Timesheet> timesheetList = query.getResultList();
		
		Timesheet[] psarray = new Timesheet[timesheetList.size()];
		for (int i=0; i < psarray.length; i++) {
			psarray[i] = timesheetList.get(i);
		}
		
		ArrayList<Timesheet> returningTimesheetList = new ArrayList<Timesheet>();
		for (int i = 0; i < timesheetList.size(); i++) {
			returningTimesheetList.add(timesheetList.get(i));
		}
		//return Arrays.asList(psarray);
		return returningTimesheetList;
    
    }
    
    /**
     * Gets the current Timesheet represented by the employee Id, startWeek, and endWeek. 
     * Try to use timesheetId as the only paramater?
     * @param employeeId the employeeId
     * @param startWeek the start week
     * @param endWeek the end week
     * @return a list of all Timesheets belonging to the params being searched
     */
    public Timesheet getCurrentTimesheet(int employeeId, Date startWeek, Date endWeek) {
    	 Query query = em.createNativeQuery(
                 "select * from Timesheet where employeeId=:employeeId"
                 + " and startWeek=:startWeek limit 1", Timesheet.class);
         query.setParameter("employeeId", employeeId);
         query.setParameter("startWeek", startWeek);
         Timesheet timesheet = null;
         try {
             timesheet = (Timesheet) query.getSingleResult();
         } catch (NoResultException e) {
        	 System.out.println("Timesheet query is null!");
         }
         if (timesheet == null) {
        	 System.out.println("Timesheet query is null!");
             timesheet = new Timesheet();
             timesheet.setEmployeeId(employeeId);
             timesheet.setStartWeek(startWeek);
             timesheet.setEndWeek(endWeek);
         }
         return timesheet;
    }
    
    /*
   public List<Timesheet> getAllTimesheets(){
    	
    	TypedQuery<Timesheet> query = em.createQuery(
	                 "select ps from Timesheet ps ",
		                 Timesheet.class); 
	   
		java.util.List<Timesheet> employeeList = query.getResultList();
		
		Timesheet[] psarray = new Timesheet[employeeList.size()];
		for (int i=0; i < psarray.length; i++) {
			psarray[i] = employeeList.get(i);
		}
		
		ArrayList<Timesheet> returningEmployeeList = new ArrayList<Timesheet>();
		for (int i = 0; i < employeeList.size(); i++) {
			returningEmployeeList.add(employeeList.get(i));
		}
		//return Arrays.asList(psarray);
		return returningEmployeeList;
    
    } */
    
   /* public Timesheet login(String userName, String password) {
    	//doesn't seem to work with TypedQuery? 
    	Query query;
    	
    	try {
    		query = em.createNativeQuery("select * from Employee "
                + "where username=:username and password=:password",
                Timesheet.class);
    	} catch (Exception e) {
    		System.out.println("inside catch in service.login");
    		return null;
    	}
    	
        query.setParameter("username", userName);
        query.setParameter("password", password);
        
        Timesheet emp;
        try {
        	emp = (Timesheet)query.getSingleResult();
        } catch (NoResultException e) {
        	System.out.println("No results in query!");
        	return null;
        }
        return (Timesheet)query.getSingleResult();
    } */
   

}

