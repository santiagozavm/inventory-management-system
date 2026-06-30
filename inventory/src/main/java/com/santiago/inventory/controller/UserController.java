package com.santiago.inventory.controller;

import com.santiago.inventory.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.santiago.inventory.dto.UserForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {

        model.addAttribute(
                "users",
                userService.getAllUsers()
        );

        return "users";
    }
    @GetMapping("/users/new")
    public String showCreateUserForm(Model model) {

        model.addAttribute(
                "userForm",
                new UserForm()
        );

        return "create-user";
    }
    @PostMapping("/users")
    public String createUser(
            @Valid @ModelAttribute("userForm") UserForm userForm,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (userService.usernameExists(
                userForm.getUsername())) {

            result.addError(
                    new FieldError(
                            "userForm",
                            "username",
                            "El nombre de usuario ya existe"
                    )
            );
        }

        if (result.hasErrors()) {
            return "create-user";
        }

        userService.createUser(
                userForm.getUsername(),
                userForm.getPassword(),
                userForm.getRole()
        );

        redirectAttributes.addFlashAttribute(
                "success",
                "Usuario creado correctamente"
        );

        return "redirect:/users";
    }

}
