package com.batteryapp.services.web.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.qubyte.base.domain.BaseApiRequest;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Data
public class AddVendorsAdminRequestDomain extends BaseApiRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @NotEmpty(message = "First name should be mandatory.")
    @Size(max = 30, message = "First name size must be less than 30 characters.")
    @JsonProperty("first_name")
    private String firstName;

    @NotNull(message = "Middle name should not be null.")
    @Size(max = 30, message = "Middle name size must be less than 30 characters.")
    @JsonProperty("middle_name")
    private String middleName;

    @NotNull(message = "Last name should not be null.")
    @Size(max = 30, message = "Last name size must be less than 30 characters.")
    @JsonProperty("last_name")
    private String lastName;

    @NotEmpty(message = "Email id should be mandatory.")
    @Email(message = "Email id format invalid.")
    @Size(max = 100, message = "Email id size must be less than 100 characters.")
    @JsonProperty("email_id")
    private String emailId;

    @NotNull(message = "Department should not be null.")
    @JsonProperty("department")
    private String department;

    @NotEmpty(message = "Mobile no should be mandatory.")
    @Pattern(regexp = "[0-9]+", message = "Mobile number should be number only.")
    @Size(min = 10, max = 10, message = "Mobile no size must be 10 characters.")
    @JsonProperty("mobile_no")
    private String mobileNo;

    @NotEmpty(message = "User type should be mandatory.")
    @Pattern(
            regexp = "^(VA)$",
            message = "User type should be only : VA"
    )
    @JsonProperty("user_type")
    private String userType;

    @NotNull(message = "Pin should not be null.")
    @JsonProperty("pin")
    private String pin;

    @NotEmpty(message = "DOB should be mandatory.")
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "Timestamp format should be dd-MM-yyyy")
    @JsonProperty("dob")
    private String dob;

    @NotEmpty(message = "Gender should be mandatory.")
    @Pattern(
            regexp = "^(M|F|O)$",
            message = "Gender should be only : M, F and O"
    )
    @JsonProperty("gender")
    private String gender;
}
