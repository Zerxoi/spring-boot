package xyz.zerxoi.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 根据配置绑定创建名为 passat 的 Car 类型组件
// @Component
// 配置绑定：将 properties 中 passat 开头的属性绑定到组件对应属性上
@ConfigurationProperties("passat")
@Data
@ToString
@NoArgsConstructor
public class Car {
    private String brand;
    private Integer price;
}
