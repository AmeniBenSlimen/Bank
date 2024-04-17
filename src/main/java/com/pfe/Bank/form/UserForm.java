package com.pfe.Bank.form;

import com.pfe.Bank.model.Role;
import com.pfe.Bank.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class UserForm {
    private Long id;
    @NotBlank
    @Size(max=20)
    private String username;
    @NotBlank
    private String fullname;
    @NotBlank
    private String phone;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(max=120)
    private String password;
    private Boolean status;
    public UserForm() {
    }
    public UserForm(User user) {
        this.username = user.getUsername();
        this.fullname = user.getFullname();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.id=user.getId();
        this.status=user.getStatus();
    }
}
