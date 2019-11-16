package Models;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "employeeid")
    private int employeeId;

    @Column(name = "name")
    private String name;

    @Id
    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "rowUpdatedTime")
    private Date rowUpdatedTime;

    @Column(name = "isAdmin")
    private boolean admin;

    /**
     * Default Constructor
     */
    public Employee() {

    }

    /**
     * Gets the employeeId
     * @return the employeeId
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the employeeId
     * @param the employeeId to set
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Gets the employee name.
     * @return employee name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the employee name.
     * @param name new employee name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the employee username.
     * @return employee username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the employee username.
     * @param userName new employee username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the employee password.
     * @return employee password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the employee password.
     * @param password new employee password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the date employee was created.
     * @return date employee was created
     */
    public Date getRowUpdatedTime() {
        return rowUpdatedTime;
    }

    /**
     * Sets the date employee was created.
     * @param rowUpdatedTime new rowUpdatedTime
     */
    public void setRowUpdatedTime(Date rowUpdatedTime) {
        this.rowUpdatedTime = rowUpdatedTime;
    }

    /**
     * Check to see if employee is an admin.
     * @return true if the employee is an admin
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Sets if the employee is an admin.
     * @param admin true if employee is an admin
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
    public boolean getAdmin() {
    	return this.admin;
    }
}