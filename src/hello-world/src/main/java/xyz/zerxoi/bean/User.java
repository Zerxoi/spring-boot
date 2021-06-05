package xyz.zerxoi.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class User {
    private String name;
    private Integer age;
    private Pet pet;
}
