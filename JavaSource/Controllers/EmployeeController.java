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
 
	public Employee getEmployee() {
		return employee;
	}
 
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
 
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
	
	public String removeEmployee(Employee emp) {
		service.removeEmployee(emp);
		employeeList.remove(emp);

		return "";
	}
	
	public List<Employee> getAllEmployees(){
		employeeList = service.getAllEmployees(); //this is important so that employeeList is not null
		return service.getAllEmployees();
		
	}
	
	public List<Employee> getEmployeeList(){
		if (employeeList == null) {
			refreshList();
		}
		//refreshList();
		return employeeList;
	}
	
	public void setEmployeeList(List<Employee> list) {
		this.employeeList = list;
	}
	
	private void refreshList() {
		for (int i = 0; i < service.getAllEmployees().size(); i++) {
			employeeList.add(service.getAllEmployees().get(i));
		}
	}
	

	public void updateEmployee() {

		for (Employee e : employeeList) {
			service.merge(e);
		}
	}
	
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
   
   public String logout() {
	   return "Login";
   }
   
   public Employee getCurrentEmployee() {
	   return currentEmployee;
   }
   public void setCurrentEmployee(Employee emp) {
	   this.currentEmployee = emp;
   }
 
}