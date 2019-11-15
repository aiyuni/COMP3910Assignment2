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
	List<Employee> employeeList = new ArrayList<Employee>();
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
		/*Need to create a new employee object with the fields equal to the passed in Employee object apart from
		the auto generated employee id.
		This is to avoid 'detached entity passed to persist' error.  */
		Employee newEmployee = new Employee();
		List<Employee> currentEmployees = service.getAllEmployees();
		
		//Check to see if employee username trying to add is already existing in database
		for(Employee e : currentEmployees) {
			if(e.getUserName().equals(emp.getUserName())){
				System.out.println("Cannot add duplicate username.");
				return;
			}
		}
		newEmployee.setName(emp.getName());
		newEmployee.setUserName(emp.getUserName());
		newEmployee.setPassword(emp.getPassword());
		newEmployee.setAdmin(emp.getAdmin());
		service.addEmployee(newEmployee);
		employeeList.add(newEmployee);
	}
	
	public String removeEmployee(Employee emp) {
		service.removeEmployee(emp);
		employeeList.remove(emp);
		//employeeList = new ArrayList<Employee>();
		//refreshList();
		//ArrayList<Employee> empArrayList = new ArrayList (ArrayList<Employee>) employeeList;
		//empArrayList.remove(emp.getEmployeeId());
		//employeeList = empArrayList;
		return "";
	}
	
	public List<Employee> getAllEmployees(){
		employeeList = service.getAllEmployees(); //this is important so that employeeList is not null?
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
		for(Employee e : employeeList) {
			service.merge(e);
		}
	}
	
   public String login(String userName, String password) {
        FacesContext context = FacesContext.getCurrentInstance();

        currentEmployee = service.login(userName, password);
        if (currentEmployee == null) {
           /* context.addMessage(null, new FacesMessage("Fail",
                    "Incorrect username/password combo")); */
            System.out.println("INCORRECT USERNAME AND/OR PASSWORD!");
            return null;
        }
        System.out.println(currentEmployee.isAdmin());
       // return currentEmployee.isAdmin() ? "admin" : "success";
        return "MenuPage";
    }
   
   public String goToEmployeeManagementPage() {
	   if (currentEmployee == null) {
		   System.out.println("something went wrong, current employee is null");
		   return null;
	   }
	   if (currentEmployee.isAdmin()) {
		   return "EmployeePageView";
	   } else {
		   System.out.println("NOT AN ADMIN");
		   return null;
	   }
   }
   
   public Employee getCurrentEmployee() {
	   return currentEmployee;
   }
   public void setCurrentEmployee(Employee emp) {
	   this.currentEmployee = emp;
   }
 
}