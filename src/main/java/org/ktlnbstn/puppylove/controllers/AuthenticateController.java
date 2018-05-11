package org.ktlnbstn.puppylove.controllers;

import org.ktlnbstn.puppylove.models.User;
import org.ktlnbstn.puppylove.models.forms.LoginForm;
import org.ktlnbstn.puppylove.models.forms.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("authenticate")
public class AuthenticateController extends AbstractController {

    //Displays Login Page
    @RequestMapping(value= "", method = RequestMethod.GET)
    public String displayLoginPage(Model model, HttpServletRequest request){
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("title", "Login");
        model.addAttribute("loginForm", new LoginForm());
        return "authenticate/login";
    }

    //Displays Register Page
    @RequestMapping(value="register", method = RequestMethod.GET)
    public String displayRegisterForm(Model model, HttpServletRequest request){
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("title", "Register");
        model.addAttribute("registerForm", new RegisterForm());

        return "authenticate/register";
    }

    //Processes Register Form and validates user input
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processRegisterForm(@ModelAttribute @Valid RegisterForm registerForm, Errors errors, HttpServletRequest request, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            model.addAttribute("sessionActive", isSessionActive(request.getSession()));
            return "authenticate/register";
        }

        User existingUser = userDao.findByEmail(registerForm.getEmail());

        if (existingUser != null) {
            errors.rejectValue("username", "email.alreadyexists", "A user with that email already exists");
            model.addAttribute("title", "Register");
            model.addAttribute("sessionActive", isSessionActive(request.getSession()));
            model.addAttribute("existingError", "User already exists.");
            return "authenticate/register";
        }

        User newUser = new User(registerForm.getName(), registerForm.getAge(), registerForm.getEmail(), registerForm.getPassword());

        userDao.save(newUser);
        setUserInSession(request.getSession(), newUser);

        return "redirect:/home";
    }

    //Processes Login Form and validates user input
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String processLoginForm(@ModelAttribute @Valid LoginForm loginForm, Errors errors, HttpServletRequest request, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Login");
            return "authenticate/login";
        }

        User theUser = userDao.findByEmail(loginForm.getEmail());
        String password = loginForm.getPassword();

        if (theUser == null) {
            errors.rejectValue("email", "email.invalid", "The given email is not linked to an account.");
            model.addAttribute("sessionActive", isSessionActive(request.getSession()));
            model.addAttribute("title", "Login");
            return "authenticate/login";
        }

        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("sessionActive", isSessionActive(request.getSession()));
            model.addAttribute("title", "Login");
            return "authenticate/login";
        }

        setUserInSession(request.getSession(), theUser);

        return "redirect:/home";
    }
}
