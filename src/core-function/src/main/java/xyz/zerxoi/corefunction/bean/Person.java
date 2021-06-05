package xyz.zerxoi.corefunction.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    private String userName;
    private Boolean boss;
    private Date birth;
    private Integer age;
    // 嵌套配置属性，用于 yaml 属性补全
    @NestedConfigurationProperty
    private Pet pet;
    private String[] interests;
    private List<String> animals;
    private Map<String, Object> scores;
    private Set<Double> salaries;
    private Map<String, List<Pet>> allPets;
}
