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

	public void addTimesheetRow(TimesheetRow emp) {
		if(em.contains(emp)) {
			System.out.println("merging inside addEmployee");
			em.merge(emp);
		}
		else {
			em.persist(emp);
		}

	}
	
    public TimesheetRow find(int id) {
        return em.find(TimesheetRow.class, id);
    }
    
    public void merge(TimesheetRow employee) {
    	em.merge(employee);
    }
    
    public void removeTimesheetRow(TimesheetRow thisEmployee) {
        //attach product
       thisEmployee = find(thisEmployee.getTimesheetId());
        em.remove(thisEmployee);
    }
    
    /**
     * Gets a list of all the employees in the Employee table belong to the user
     * @return
     */
    public List<TimesheetRow> getAllTimesheetRows(int employeeId){
    	
    	TypedQuery<TimesheetRow> query = em.createQuery(
	                 "select ps from TimesheetRow ps " +
	                 "order by ps.TimesheetRowId",
		                 TimesheetRow.class); 
    	query.setParameter("employeeId", employeeId);  //set query parameter for employeeId
	   
		java.util.List<TimesheetRow> employeeList = query.getResultList();
		
		TimesheetRow[] psarray = new TimesheetRow[employeeList.size()];
		for (int i=0; i < psarray.length; i++) {
			psarray[i] = employeeList.get(i);
		}
		
		ArrayList<TimesheetRow> returningEmployeeList = new ArrayList<TimesheetRow>();
		for (int i = 0; i < employeeList.size(); i++) {
			returningEmployeeList.add(employeeList.get(i));
		}
		//return Arrays.asList(psarray);
		return returningEmployeeList;
    
    }
    
 public List<TimesheetRow> getAllTimesheetRows(){
    	
    	TypedQuery<TimesheetRow> query = em.createQuery(
	                 "select ps from TimesheetRow ps",
		                 TimesheetRow.class); 
	   
		java.util.List<TimesheetRow> employeeList = query.getResultList();
		
		TimesheetRow[] psarray = new TimesheetRow[employeeList.size()];
		for (int i=0; i < psarray.length; i++) {
			psarray[i] = employeeList.get(i);
		}
		
		ArrayList<TimesheetRow> returningEmployeeList = new ArrayList<TimesheetRow>();
		for (int i = 0; i < employeeList.size(); i++) {
			returningEmployeeList.add(employeeList.get(i));
		}
		//return Arrays.asList(psarray);
		return returningEmployeeList;
    
    }
}
