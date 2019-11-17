package Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import Models.Employee;


@Stateless
public class EmployeeService {

	@PersistenceContext(name = "EmployeeApp")
	private EntityManager em;

	/**
	 * Method to add an given employee
	 * @param emp the employee to add 
	 */
	public void addEmployee(Employee emp) {
		if(em.contains(emp)) {
			System.out.println("merging inside addEmployee");
			em.merge(emp);
		}
		else {
			em.persist(emp);
		}

	}
	
	/**
	 * Method to find an Employee based on Employee username
	 * @param userName the employee username to search for
	 * @return the Employee
	 */
    public Employee find(String userName) {
        return em.find(Employee.class, userName);
    }
    
    /**
     * Method to update an Employee
     * @param employee the Employee to update
     */
    public void merge(Employee employee) {
    	em.merge(employee);
    }
    
    /**
     * Method to remove an Employee
     * @param thisEmployee the Employee to remove
     */
    public void removeEmployee(Employee thisEmployee) {
        //attach product
       thisEmployee = find(thisEmployee.getUserName());
        em.remove(thisEmployee);
    }
    
    /**
     * Gets a list of all the employees in the Employee table
     * @return the list of Employees
     */
    public List<Employee> getAllEmployees(){
    	
    	TypedQuery<Employee> query = em.createQuery(
	                 "select ps from Employee ps " +
	                 "order by ps.employeeId",
		                 Employee.class); 
	   
		java.util.List<Employee> employeeList = query.getResultList();
		
		Employee[] psarray = new Employee[employeeList.size()];
		for (int i=0; i < psarray.length; i++) {
			psarray[i] = employeeList.get(i);
		}
		
		ArrayList<Employee> returningEmployeeList = new ArrayList<Employee>();
		for (int i = 0; i < employeeList.size(); i++) {
			returningEmployeeList.add(employeeList.get(i));
		}
		//return Arrays.asList(psarray);
		return returningEmployeeList;
    
    }
    
    /**
     * Method to handle Employee logins
     * @param userName the Employee userName
     * @param password the Employee password
     * @return the Employee based on the userName and password, or null if the Employee cannot be found in database
     */
    public Employee login(String userName, String password) {
    	//doesn't seem to work with TypedQuery? 
    	Query query;
    	
    	try {
    		query = em.createNativeQuery("select * from Employee "
                + "where username=:username and password=:password",
                Employee.class);
    	} catch (Exception e) {
    		System.out.println("inside catch in service.login");
    		return null;
    	}
    	
        query.setParameter("username", userName);
        query.setParameter("password", password);
        
        Employee emp;
        try {
        	emp = (Employee)query.getSingleResult();
        } catch (NoResultException e) {
        	System.out.println("No results in query!");
        	return null;
        }
        return (Employee)query.getSingleResult();
    }

}