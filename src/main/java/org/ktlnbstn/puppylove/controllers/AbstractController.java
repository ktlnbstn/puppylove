package org.ktlnbstn.puppylove.controllers;

import org.ktlnbstn.puppylove.models.User;
import org.ktlnbstn.puppylove.models.data.PuppyDao;
import org.ktlnbstn.puppylove.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AbstractController {

    @Autowired
    protected UserDao userDao;

    @Autowired
    protected PuppyDao puppyDao;

    public static final String userSessionKey = "user_id";

    protected User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        return userId == null ? null : userDao.findOne(userId);
    }

    protected void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    protected void clearSession(HttpSession session){
        session.setAttribute(userSessionKey, null);
    }

    protected boolean isSessionActive(HttpSession session){
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        return userId != null;
    }

    @ModelAttribute("userId")
    public Integer getUserIdFromSession(HttpServletRequest request) {
        return (Integer) request.getSession().getAttribute(userSessionKey);
    }

}
