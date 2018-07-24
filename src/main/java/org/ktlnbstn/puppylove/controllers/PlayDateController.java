package org.ktlnbstn.puppylove.controllers;

import org.ktlnbstn.puppylove.models.PlayDate;
import org.ktlnbstn.puppylove.models.User;
import org.ktlnbstn.puppylove.models.forms.EditPlayDateForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("playdate")
public class PlayDateController extends AbstractController {

    // Request path: /playdate

    // Display adding a play date for specific user
    @RequestMapping(value = "add/{userId}", method = RequestMethod.GET)
    public String displayAddPlayDateForm(Model model, @PathVariable int userId,
                                         HttpServletRequest request) {

        PlayDate playDate = new PlayDate();

        User user = userDao.findOne(getUserIdFromSession(request));
        playDate.addUser(user);

        user = userDao.findOne(userId);
        playDate.addUser(user);

        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("title", "Add A Playdate");
        model.addAttribute("users", playDate.getUsers());
        model.addAttribute("playDate", playDate);

        return "playdate/add";
    }

    // Process adding a play date for specific user
    @RequestMapping(value = "add/{userId}", method = RequestMethod.POST)
    public String processAddPlayDateForm(@PathVariable int userId, @Valid @ModelAttribute("playDate") PlayDate playDate,
                                         BindingResult errors, Model model, HttpServletRequest request) {

        if (errors.hasErrors()) {

            model.addAttribute("title", "Add A Playdate");
            model.addAttribute("sessionActive", isSessionActive(request.getSession()));
            model.addAttribute("users", playDate.getUsers());
            model.addAttribute("playDate", playDate);

            return "playdate/add";
        }

        //grab both users' info
        User user1 = userDao.findOne(getUserIdFromSession(request));
        User user2 = userDao.findOne(userId);

        //add users to set for user field in playdate object
        playDate.addUser(user1);
        playDate.addUser(user2);
        //save users to playdateDAO
        playDate.setUsers(playDate.getUsers());
        //save 1st user to set for playdate field in user object
        user1.addPlaydate(playDate);
        //save 2nd user to set for playdate field in user object
        user2.addPlaydate(playDate);

        // save all other info into playdate object
        playDate.setDate(playDate.getDate());
        playDate.setDescription(playDate.getDescription());
        playDate.setDogParkLocation(playDate.getDogParkLocation());
        playDateDao.save(playDate);

        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("users", playDate.getUsers());
        model.addAttribute("playDate", playDate);
        model.addAttribute("id", playDate.getId());

        return "playdate/viewplaydate";
    }

    // display edit playDate form
    @RequestMapping(value = "editplaydate/{id}", method = RequestMethod.GET)
    public String displayEditPlayDateForm(@PathVariable int id, Model model, HttpServletRequest request) {

        PlayDate playDate = playDateDao.findById(id);

        EditPlayDateForm editPlayDateForm = new EditPlayDateForm();
        editPlayDateForm.setDate(playDate.getDate());
        editPlayDateForm.setDescription(playDate.getDescription());
        editPlayDateForm.setDogParkLocation(playDate.getDogParkLocation());

        model.addAttribute("title", "Edit Your Playdate");
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("editPlayDateForm", editPlayDateForm);

        return "playdate/editplaydate";
    }

    // process edit playDate form
    @RequestMapping(value = "editplaydate/{id}", method = RequestMethod.POST)
    public String processEditForm (@PathVariable int id, @Valid @ModelAttribute("editPlayDateForm")
            EditPlayDateForm editPlayDateForm, BindingResult errors, HttpServletRequest request, Model model) {

        if (errors.hasErrors()) {

            model.addAttribute("title", "Edit Your Playdate");
            model.addAttribute("sessionActive", isSessionActive(request.getSession()));
            model.addAttribute("editPlayDateForm", editPlayDateForm);

            return "playdate/editplaydate";
        }

        model.addAttribute("title", "Edit Your Playdate");
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));

        PlayDate editedPlayDateForm = playDateDao.findById(id);
        editedPlayDateForm.setDate(editPlayDateForm.getDate());
        editedPlayDateForm.setDescription(editPlayDateForm.getDescription());
        editedPlayDateForm.setDogParkLocation(editPlayDateForm.getDogParkLocation());
        playDateDao.save(editedPlayDateForm);

        return "redirect:/home";
    }
}
