package xyz.zerxoi.webapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zouxin <zouxin@kuaishou.com>
 * Created on 2021-06-06
 */
@RestController
public class UserController {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUser() {
        return "GET";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String postUser() {
        return "POST";
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String putUser() {
        return "PUT";
    }

    @RequestMapping(value = "/user", method = RequestMethod.PATCH)
    public String patchUser() {
        return "PATCH";
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public String deleteUser() {
        return "DELETE";
    }
}
