package org.ktlnbstn.puppylove.controllers;

import org.ktlnbstn.puppylove.models.Puppy;
import org.ktlnbstn.puppylove.models.User;
import org.ktlnbstn.puppylove.models.data.PuppyDao;
import org.ktlnbstn.puppylove.models.data.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import javax.validation.Valid;

import javax.validation.Valid;


@Controller
@RequestMapping("puppy")
public class PuppyController extends AbstractController {

    // Request path: /puppy
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @RequestMapping(value = "")
    public String index(Model model, HttpServletRequest request) {

        User user = getUserFromSession(request.getSession());

        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("puppies", user.getPuppies());
        model.addAttribute("title", "My Puppies");

        return "puppy/index";
    }

    // Change Puppy: display adding a puppy form for specific user
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddPuppyForm(Model model, HttpServletRequest request) {

        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("title", "Add Your Puppy");
        model.addAttribute(new Puppy());
        model.addAttribute("users", userDao.findAll());
        return "puppy/add";
    }

    // Change Puppy: process adding a puppy for specific user
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddPuppyForm(@ModelAttribute @Valid Puppy newPuppy,
                                      Errors errors,
                                      Model model, HttpServletRequest request) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Your Puppy");
            model.addAttribute("sessionActive", isSessionActive(request.getSession()));
            model.addAttribute("users", userDao.findAll());
            return "puppy/add";
        }

        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        User user = userDao.findOne(getUserIdFromSession(request));
        newPuppy.setUser(user);
        puppyDao.save(newPuppy);
        user.addPuppy(newPuppy);
        userDao.save(user);

        return "puppy/view";
    }

    // Change Puppy: display removal form
    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemovePuppyForm(Model model, HttpServletRequest request) {
        model.addAttribute("puppies", puppyDao.findAll());
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("title", "Remove Your Puppy");
        return "puppy/remove";
    }

    // Change Puppy: process removal of a puppy for specific user
    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemovePuppyForm(@RequestParam int[] puppyIds,
                                         Model model, HttpServletRequest request) {

        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        User user = userDao.findOne(getUserIdFromSession(request));

        for (int puppyId : puppyIds) {
            user.removePuppy(puppyDao.findOne(puppyId));
            puppyDao.delete(puppyId);
        }

        return "redirect:";
    }

    // View a puppy's profile
    @RequestMapping(value = "view/{puppyId}", method = RequestMethod.GET)
    public String viewPuppy(Model model, @PathVariable int puppyId,
                            HttpServletRequest request) {
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("puppy", puppyDao.findOne(puppyId));
        return "puppy/view";
    }
}