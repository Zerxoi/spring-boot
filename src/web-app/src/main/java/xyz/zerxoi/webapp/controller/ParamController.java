package xyz.zerxoi.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zouxin <zouxin@kuaishou.com>
 * Created on 2021-06-06
 */
@RestController
public class ParamController {
    @RequestMapping(value = "/car/{id}/owner/{name}")
    public Map<String, Object> getCar(@PathVariable("id") Integer id, @PathVariable("name") String name,
            // @PathVariable 如果方法参数是 Map<String, String>，则映射将填充所有路径变量名称和值。
            @PathVariable Map<String, String> kv,
            // @RequestHeader 如果方法参数是 Map<String, String>、MultiValueMap<String, String> 或 HttpHeaders，则映射将填充所有标头名称和值。
            @RequestHeader("User-Agent") String userAgent, @RequestHeader Map<String, String> headers,
            @RequestParam("age") Integer age, @RequestParam("interests")
            // @RequestParam 如果方法参数是 Map<String, String> 或 MultiValueMap<String, String> 并且未指定参数名称，则使用所有请求参数名称和值填充
            // map 参数。
            List<String> interests, @RequestParam Map<String, String> params) {
        Map<String, Object> map = new HashMap<>();
        // map.put("id", id);
        // map.put("owner", name);
        // map.put("kv", kv);
        // map.put("User-Agent", userAgent);
        // map.put("headers", headers);
        map.put("age", age);
        map.put("interests", interests);
        map.put("params", params);
        return map;
    }

    // localhost:8080/cars/sell;low=200000;brand=audi,benz,bwm
    // {"low":200000,"sell":"sell","brand":["audi","benz","bwm"]}
    @GetMapping("/cars/{sell}")
    public Map<String, Object> carsSell(@MatrixVariable("low") Integer low, @MatrixVariable("brand") List<String> brand,
            @PathVariable("sell") String sell) {
        Map<String, Object> map = new HashMap<>();
        map.put("low", low);
        map.put("brand", brand);
        map.put("sell", sell);
        return map;
    }

    // localhost:8080/boss/1;age=45/emp/6;age=24
    // {"bossAge":45,"empAge":24}
    @RequestMapping("/boss/{bossId}/emp/{empId}")
    public Map<String, Object> bossEmp(@MatrixVariable(value = "age", pathVar = "bossId") Integer bossAge,
            @MatrixVariable(value = "age", pathVar = "empId") Integer empAge) {
        Map<String, Object> map = new HashMap<>();
        map.put("bossAge", bossAge);
        map.put("empAge", empAge);
        return map;
    }
}
