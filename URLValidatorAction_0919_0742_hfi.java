// 代码生成时间: 2025-09-19 07:42:44
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.Map;

/**
 * Action class for validating URL links.
 */
@Namespace("/urlValidation")
public class URLValidatorAction extends ActionSupport {

    // URL to be validated
    private String url;

    // Validator response
    private boolean isValid;

    // Validator
    private UrlValidator urlValidator;

    // Default constructor
    public URLValidatorAction() {
        super();
        // Initialize URL validator with default schemes
        urlValidator = new UrlValidator(new String[] { "http", "https", "ftp" }, UrlValidator.ALLOW_ALL_SCHEMES);
    }

    /**
     * Sets the URL to be validated.
     *
     * @param url The URL to be validated.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the URL to be validated.
     *
     * @return The URL to be validated.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the validation result.
     *
     * @param isValid The result of the URL validation.
     */
    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    /**
     * Gets the validation result.
     *
     * @return The result of the URL validation.
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * Validates the provided URL.
     *
     * @return A String indicating the result of the validation.
     */
    @Action(value = "validate", results = {
        @Result(name = ActionSupport.SUCCESS, location = "success.jsp"),
        @Result(name = ActionSupport.ERROR, location = "error.jsp")
    })
    public String validate() {
        try {
            // Validate the URL using the URLValidator
            isValid = urlValidator.isValid(url);
        } catch (Exception e) {
            // Handle any exceptions that occur during validation
            addActionError("An error occurred while validating the URL: " + e.getMessage());
            return ERROR;
        }

        return isValid ? SUCCESS : ERROR;
    }
}
