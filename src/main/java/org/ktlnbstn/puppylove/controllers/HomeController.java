package org.ktlnbstn.puppylove.controllers;

import org.ktlnbstn.puppylove.models.PlayDate;
import org.ktlnbstn.puppylove.models.User;
import org.ktlnbstn.puppylove.models.forms.EditProfileForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.ktlnbstn.puppylove.models.forms.SearchForm;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequestMapping("home")
public class HomeController extends AbstractController {

    // display user homepage
    @RequestMapping("")
    public String displayHomePage(Model model, HttpServletRequest request){

        User user = getUserFromSession(request.getSession());

        // Format LocalDateTime for comparison of future/past playdates
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date finalizedLocalTime = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        // Obtain future and past playdates from playdates set
        Iterator<PlayDate> playdatesToSort = user.getPlaydates().iterator();
        Set<PlayDate> futurePlaydates = new TreeSet<>();
        Set<PlayDate> pastPlaydates = new TreeSet<>();

        while(playdatesToSort.hasNext()) {
            PlayDate playdateItem = playdatesToSort.next();
            if(playdateItem.getDate().compareTo(finalizedLocalTime) >= 0) {
                futurePlaydates.add(playdateItem);
            } else {
                pastPlaydates.add(playdateItem);
            }
        }

        // Obtain (current/past)playdates in date order (most recent at top)
        List orderedFuturePlaydates = new ArrayList(futurePlaydates);
        Collections.sort(orderedFuturePlaydates);
        List orderedPastPlaydates = new ArrayList(pastPlaydates);
        Collections.sort(orderedPastPlaydates, Collections.reverseOrder());

        model.addAttribute("user", user);
        model.addAttribute("title", "Your Homepage");
        model.addAttribute("puppies", user.getPuppies());
        model.addAttribute("welcome", "Welcome " + user.getName() + "!");
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("playdates", orderedFuturePlaydates);
        model.addAttribute("pastPlaydates", orderedPastPlaydates);

        return "home/index";
    }

//    //redirect to a user's puppies
//    @RequestMapping("viewpups")
//    public String displayViewPupsPage(Model model){
//
//        return "redirect:/puppy/index";
//    }

    // search for users
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String displaySearchPage(Model model, HttpServletRequest request){

        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("title", "Search for Puppy Owners");
        model.addAttribute("users", userDao.findAll());
        model.addAttribute(new SearchForm());

        return "home/search";
    }

    // process search form
    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String processSearchForm(@ModelAttribute @Valid SearchForm searchForm, Model model, Errors errors,
                                    HttpServletRequest request){

        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("title", "Search for Puppy Owners");
        model.addAttribute(new SearchForm());

        if(errors.hasErrors()){

            model.addAttribute("users", userDao.findAll());

            return "home/search";
        }

        if(searchForm.getDogParkLocation().toString() == "All Puppy Owners") {
            model.addAttribute("users", userDao.findAll());
        } else {
            model.addAttribute("users", userDao.findByDogParkLocation(searchForm.getDogParkLocation()));
        }

        return "home/search";
    }

    // view users public profile via PathVariable
    @RequestMapping(value="viewprofile/{userId}", method = RequestMethod.GET)
    public String viewProfile(Model model, @PathVariable int userId,
                                     HttpServletRequest request){

        User user = getUserFromSession(request.getSession());

        // Format LocalDateTime for comparison of future/past playdates
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date finalizedLocalTime = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        // Obtain future and past playdates from playdates set
        Iterator<PlayDate> playdatesToSort = userDao.findOne(userId).getPlaydates().iterator();
        Set<PlayDate> futurePlaydates = new TreeSet<>();
        Set<PlayDate> pastPlaydates = new TreeSet<>();

        while(playdatesToSort.hasNext()) {
            PlayDate playdateYo = playdatesToSort.next();
            if(playdateYo.getDate().compareTo(finalizedLocalTime) >= 0) {
                futurePlaydates.add(playdateYo);
            } else {
                pastPlaydates.add(playdateYo);
            }
        }

        // Obtain (past)playdates in date order (most recent at top)
        List orderedPastPlaydates = new ArrayList(pastPlaydates);
        Collections.sort(orderedPastPlaydates, Collections.reverseOrder());

        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("userId", userId);
        model.addAttribute("currentUserId", user.getId());
        model.addAttribute("user", userDao.findOne(userId));
        model.addAttribute("puppies", userDao.findOne(userId).getPuppies());
        model.addAttribute("pastPlaydates", orderedPastPlaydates);

        return "home/viewprofile";
    }

    //logout
    @RequestMapping("logout")
    public String logout(HttpServletRequest request){

        clearSession(request.getSession());

        return "redirect:/authenticate";
    }

    // display edit profile form
    @RequestMapping(value = "editprofile", method = RequestMethod.GET)
    public String displayEditForm(Model model, HttpServletRequest request) {

        User user = getUserFromSession(request.getSession());

        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("title", "Edit Your Profile");

        EditProfileForm editForm = new EditProfileForm();
        editForm.setName(user.getName());
        editForm.setEmail(user.getEmail());
        editForm.setDescription(user.getDescription());
        editForm.setDogParkLocation(user.getDogParkLocation());

        model.addAttribute("editForm", editForm);

        return "home/editprofile";
    }

    // process edit profile form
    @RequestMapping(value = "editprofile", method = RequestMethod.POST)
    public String processEditForm (@Valid @ModelAttribute("editForm") EditProfileForm editForm,
                                   BindingResult errors, HttpServletRequest request, Model model) {

        if (errors.hasErrors()) {

            model.addAttribute("title", "Edit Your Profile");
            model.addAttribute("sessionActive", isSessionActive(request.getSession()));
            model.addAttribute("editForm", editForm);

            return "home/editprofile";
        }

        model.addAttribute("title", "Edit Your Profile");
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));

        User user = getUserFromSession(request.getSession());
        user.setName(editForm.getName());
        user.setDescription(editForm.getDescription());
        user.setDogParkLocation(editForm.getDogParkLocation());
        userDao.save(user);

        return "redirect:";
    }

}
