package com.example.guessgame.validator;

import com.example.guessgame.model.User;
import com.example.guessgame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> classToValidate) {
        return User.class.equals(classToValidate);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");

        if((user.getUsername().length() < 4) || (user.getUsername().length() > 10)){
            errors.rejectValue("username", "Size.userForm.username");
        }

        if(userService.findUser(user.getUsername()) != null){
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");

        if(user.getPassword().length() < 2){
            errors.rejectValue("password", "Size.userForm.password");
        }

        if(!user.getConfirmPassword().equals(user.getPassword())){
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }
    }
}
