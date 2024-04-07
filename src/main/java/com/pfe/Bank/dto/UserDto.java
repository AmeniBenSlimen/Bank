package com.pfe.Bank.dto;

import com.pfe.Bank.form.UserForm;
import com.pfe.Bank.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDto extends UserForm {
    private Set<RoleDto> userRoles;

    public UserDto(User user) {
        super(user);
        this.userRoles = user.getRoles().stream().map(RoleDto::new).collect(Collectors.toSet());
    }

    public static UserDto of(User user){
        return new UserDto(user);
    }

    public static List<UserDto> of(List<User> users){
        return users.stream().map(UserDto::of).collect(Collectors.toList());
    }
}
