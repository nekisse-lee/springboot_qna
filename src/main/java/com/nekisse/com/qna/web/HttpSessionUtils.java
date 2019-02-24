package com.nekisse.com.qna.web;

import com.nekisse.com.qna.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "userSession";


    public static boolean isLoginUser(HttpSession session) {
        Object userSession = session.getAttribute(USER_SESSION_KEY);
        if (userSession == null) {
            return false;
        }
        return true;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            return null;
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }


}
