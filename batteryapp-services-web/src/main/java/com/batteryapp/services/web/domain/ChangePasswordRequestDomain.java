package com.batteryapp.services.web.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.qubyte.base.domain.BaseApiRequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Alok kumar on 30-04-2024
 * @project batteryapp-services-web
 */
@Data
public class ChangePasswordRequestDomain extends BaseApiRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @NotEmpty(message = "Current password should be mandatory.")
    //@Pattern(regexp = "[0-9]+", message = "Only Numbers are allowed for Username.")
    @Size(min = 6, max = 10, message = "Current password size must be between 6 and 10 characters.")
    @JsonProperty("current_password")
    private String currentPassword;

    @NotEmpty(message = "New password should be mandatory.")
    //@Pattern(regexp = "[0-9]+", message = "Only Numbers are allowed for Username.")
    @Size(min = 6, max = 10, message = "New password size must be between 6 and 10 characters.")
    @JsonProperty("new_password")
    private String newPassword;

    @NotEmpty(message = "Re-type new password should be mandatory.")
    //@Pattern(regexp = "[0-9]+", message = "Only Numbers are allowed for Username.")
    @Size(min = 6, max = 10, message = "Re-type new password size must be between 6 and 10 characters.")
    @JsonProperty("retype_new_password")
    private String retypeNewPassword;
}
