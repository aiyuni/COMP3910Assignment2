package Controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import Models.Employee;
import Services.EmployeeService;



@Named("employeeController")
@SessionScoped
public class EmployeeController implements Serializable {
 
	private Employee employee = new Employee();
	private List<Employee> employeeList = new ArrayList<Employee>();
	Employee currentEmployee;
 
	@EJB
	private EmployeeService service;
 
	/**
	 * Getter for Employee
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}
 
	/**
	 * Setter for Employee
	 * @param employee
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
 
	/**
	 * Method to add a given employee
	 * @param emp the employee to add
	 */
	public void addEmployee(Employee emp) {
		FacesContext context = FacesContext.getCurrentInstance();
		/*Need to create a new employee object with the fields equal to the passed in Employee object apart from
		the auto generated employee id.
		This is to avoid 'detached entity passed to persist' error.  */
		Employee newEmployee = new Employee();
		List<Employee> currentEmployees = service.getAllEmployees();
		
		//Check to see if employee username trying to add is already existing in database
		for(Employee e : currentEmployees) {
			if(e.getUserName().equals(emp.getUserName())){
				System.out.println("Cannot add duplicate username.");
				context.addMessage(null, new FacesMessage("Error: Cannot add a duplicate userName."));
				return;
			}
		}
		newEmployee.setName(emp.getName());
		newEmployee.setUserName(emp.getUserName());
		newEmployee.setPassword(emp.getPassword());
		newEmployee.setAdmin(emp.getAdmin());
		newEmployee.setEmployeeId(emp.getEmployeeId());
		service.addEmployee(newEmployee);
		employeeList.add(newEmployee);
	}
	
	/**
	 * Method to remove a given employee
	 * @param emp the employee to remove
	 * @return empty string 
	 */
	public String removeEmployee(Employee emp) {
		service.removeEmployee(emp);
		employeeList.remove(emp);

		return "";
	}
	
	/**
	 * Method to get all the employees in the database 
	 * @return a list of all employees in database
	 */
	public List<Employee> getAllEmployees(){
		employeeList = service.getAllEmployees(); //this is important so that employeeList is not null
		return service.getAllEmployees();
		
	}
	
	/**
	 * Getter for employee list
	 * @return the employee list 
	 */
	public List<Employee> getEmployeeList(){
		if (employeeList == null) {
			refreshList();
		}
		//refreshList();
		return employeeList;
	}
	
	/**
	 * Setter for employee list 
	 * @param list the list to set
	 */
	public void setEmployeeList(List<Employee> list) {
		this.employeeList = list;
	}
	
	/**
	 * Method to update employeeList with all the employees in the database
	 */
	private void refreshList() {
		for (int i = 0; i < service.getAllEmployees().size(); i++) {
			employeeList.add(service.getAllEmployees().get(i));
		}
	}
	

	/**
	 * Method to update employees in the database
	 */
	public void updateEmployee() {

		for (Employee e : employeeList) {
			service.merge(e);
		}
	}
	
	/**
	 * Method to handle user logins
	 * @param userName the user username
	 * @param password the user password
	 * @return String that represents the navigation url if login success, or null if false
	 */
   public String login(String userName, String password) {
        FacesContext context = FacesContext.getCurrentInstance();

        currentEmployee = service.login(userName, password);
        if (currentEmployee == null) {
            System.out.println("INCORRECT USERNAME AND/OR PASSWORD!");
            context.addMessage(null, new FacesMessage("Incorrect Username or Password."));
            return null;
        }
        System.out.println(currentEmployee.isAdmin());
        return "MenuPage";
    }
   
   /**
    * Method to handle navigation to the employee management page
    * @return the navigation url if successful, null otherwise
    */
   public String goToEmployeeManagementPage() {
	   FacesContext context = FacesContext.getCurrentInstance();
	   if (currentEmployee == null) {
		   System.out.println("something went wrong, current employee is null");
		   return null;
	   }
	   if (currentEmployee.isAdmin()) {
		   return "EmployeePageView";
	   } else {
		   System.out.println("NOT AN ADMIN");
		   context.addMessage(null, new FacesMessage("You do not have sufficient permissions "
		   		+ "to access Employee Management Page."));
		   return null;
	   }
   }
   
   /**
    * Method to handle user logout
    * @return the navigation url to Login page
    */
   public String logout() {
	   return "Login";
   }
   
   /**
    * Getter for the current Employee
    * @return the current Employee
    */
   public Employee getCurrentEmployee() {
	   return currentEmployee;
   }
   
   /**
    * Setter for the current Employee
    * @param emp the employee to set
    */
   public void setCurrentEmployee(Employee emp) {
	   this.currentEmployee = emp;
   }
 
}