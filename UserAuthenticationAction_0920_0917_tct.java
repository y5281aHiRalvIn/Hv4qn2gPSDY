// 代码生成时间: 2025-09-20 09:17:23
package com.example.auth;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;

public class UserAuthenticationAction extends ActionSupport {

    private String username;
    private String password;
    private String errorMessage;

    // Getters and setters for username and password
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /*
     * login method is called when the user submits the login form.
     * It checks the provided username and password against a list
     * of valid credentials.
     */
    public String login() {
        HttpServletRequest request = ServletActionContext.getRequest();
        boolean isValid = checkCredentials(username, password);

        if (isValid) {
            // Set the user's session
            request.getSession().setAttribute("user", username);
            return SUCCESS;
        } else {
            // Set an error message if credentials are invalid
            setErrorMessage("Invalid username or password.");
            return ERROR;
        }
    }

    /*
     * checkCredentials is a mock method to simulate credential checking.
     * In a real-world scenario, this would check against a database.
     */
    private boolean checkCredentials(String username, String password) {
        // For demonstration purposes, let's assume there is one valid user
        // In production, this should be replaced with a database lookup
        return "admin".equals(username) && "admin123".equals(password);
    }

    // This method is called when the login fails
    public String showError() {
        return LOGIN;
    }
}
