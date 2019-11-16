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

	public void addEmployee(Employee emp) {
		if(em.contains(emp)) {
			System.out.println("merging inside addEmployee");
			em.merge(emp);
		}
		else {
			em.persist(emp);
		}

	}
	
    public Employee find(String userName) {
        return em.find(Employee.class, userName);
    }
    
    public void merge(Employee employee) {
    	em.merge(employee);
    }
    
    public void removeEmployee(Employee thisEmployee) {
        //attach product
       thisEmployee = find(thisEmployee.getUserName());
        em.remove(thisEmployee);
    }
    
    /**
     * Gets a list of all the employees in the Employee table
     * @return
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