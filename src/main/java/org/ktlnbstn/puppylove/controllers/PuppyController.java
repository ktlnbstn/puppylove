package org.ktlnbstn.puppylove.controllers;

import org.ktlnbstn.puppylove.models.Puppy;
import org.ktlnbstn.puppylove.models.User;
import org.ktlnbstn.puppylove.models.forms.EditPuppyForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping("puppy")
public class PuppyController extends AbstractController {

    // Request path: /puppy

    // Change Puppy: display adding a puppy form for specific user
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddPuppyForm(Model model, HttpServletRequest request) {

        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("title", "Add A Puppy");
        model.addAttribute(new Puppy());

        return "puppy/add";
    }

    // Change Puppy: process adding a puppy for specific user
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddPuppyForm(@ModelAttribute @Valid Puppy newPuppy, BindingResult errors,
                                      Model model, HttpServletRequest request) {

        if (errors.hasErrors()) {

            model.addAttribute("title", "Add A Puppy");
            model.addAttribute("sessionActive", isSessionActive(request.getSession()));
            model.addAttribute("puppy", newPuppy); //necessary? helpful?

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

        User user = getUserFromSession(request.getSession());

        model.addAttribute("puppies", user.getPuppies());
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("title", "Remove A Puppy");

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

        return "redirect:/home";
    }

    // View a puppy's profile
    @RequestMapping(value = "view/{puppyId}", method = RequestMethod.GET)
    public String viewPuppy(Model model, @PathVariable int puppyId, HttpServletRequest request) {

        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("puppy", puppyDao.findOne(puppyId));

        return "puppy/view";
    }

    // display edit puppy
    @RequestMapping(value = "editpuppy/{puppyId}", method = RequestMethod.GET)
    public String displayEditPuppy(Model model, @PathVariable int puppyId, HttpServletRequest request) {

        EditPuppyForm editPuppy = new EditPuppyForm();
        editPuppy.setName(puppyDao.findOne(puppyId).getName());
        editPuppy.setSize(puppyDao.findOne(puppyId).getSize());
        editPuppy.setDescription(puppyDao.findOne(puppyId).getDescription());

        model.addAttribute("editPuppy", editPuppy);
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("title", "Edit Your Puppy");

        return "puppy/editpuppy";
    }

    // process edit puppy
    @RequestMapping(value = "editpuppy/{puppyId}", method = RequestMethod.POST)
    public String processEditPuppy (@PathVariable int puppyId, @Valid @ModelAttribute("editPuppy") EditPuppyForm editPuppy,
                                   BindingResult errors, HttpServletRequest request, Model model) {

        if (errors.hasErrors()) {

            model.addAttribute("title", "Edit Your Puppy");
            model.addAttribute("sessionActive", isSessionActive(request.getSession()));
            model.addAttribute("editPuppy", editPuppy);

            return "puppy/editpuppy";
        }

        model.addAttribute("title", "Edit Your Puppy");
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));

        Puppy editedPuppy = puppyDao.findOne(puppyId);
        editedPuppy.setName(editPuppy.getName());
        editedPuppy.setSize(editPuppy.getSize());
        editedPuppy.setDescription(editPuppy.getDescription());

        puppyDao.save(editedPuppy);

        return "redirect:/home";
    }
}