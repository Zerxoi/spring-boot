package xyz.zerxoi.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zouxin <zouxin@kuaishou.com>
 * Created on 2021-06-06
 */
@Controller
public class RequestAttributeController {
    @RequestMapping("/forward")
    public String forward(HttpServletRequest request) {
        request.setAttribute("msg", "成功");
        request.setAttribute("code", 200);
        return "forward:/success";
    }

    @ResponseBody
    @RequestMapping("/success")
    public Map<String, Object> success(@RequestAttribute("msg") String msg, @RequestAttribute("code") Integer code, HttpServletRequest request) {
        Object msg1 = request.getAttribute("msg");
        Map<String, Object> map = new HashMap<>();
        map.put("msg", msg);
        map.put("msg1", msg1);
        map.put("code", code);
        return map;
    }
}
