# Spring Cloud基于数据库的认证
# 1.新建数据库

新建数据库security，执行如下sql：

```sql
/*
Navicat MySQL Data Transfer
Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : security
Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001
Date: 2018-07-28 15:26:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `nameZh` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'dba', '数据库管理员');
INSERT INTO `role` VALUES ('2', 'admin', '系统管理员');
INSERT INTO `role` VALUES ('3', 'user', '用户');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `locked` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'root', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', '1', '0');
INSERT INTO `user` VALUES ('2', 'admin', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', '1', '0');
INSERT INTO `user` VALUES ('3', 'sang', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', '1', '0');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
INSERT INTO `user_role` VALUES ('2', '1', '2');
INSERT INTO `user_role` VALUES ('3', '2', '2');
INSERT INTO `user_role` VALUES ('4', '3', '3');
SET FOREIGN_KEY_CHECKS=1;
```

role：

|  id  | name  | nameZH       |
| :--: | ----- | ------------ |
|  1   | dba   | 数据库管理员 |
|  2   | admin | 系统管理员   |
|  3   | user  | 用户         |

user:

|  id  | username | password                                                  | enabled | locked |
| :--: | -------- | --------------------------------------------------------- | :-----: | :----: |
|  1   | root     | $10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq |    1    |   0    |
|  2   | admin    | $10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq |    1    |   0    |
|  3   | sang     | $10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq |    1    |   0    |

user_role:

|  id  | uid  | rid  |
| :--: | :--: | :--: |
|  1   |  1   |  1   |
|  2   |  1   |  2   |
|  3   |  2   |  2   |
|  4   |  3   |  3   |

新建Spring工程: **[Security-DB-Auth](https://github.com/Fateasstring/Security-DB-Auth)**

跳转链接：https://github.com/Fateasstring/Security-DB-Auth

# 2.更新pom依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.6.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.fateasstring</groupId>
    <artifactId>securitydbauth</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>securitydbauth</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.2</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
            <version>5.1.27</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

# 3.配置application.properties

src/main/resources/application.properties配置更新如下：

```xml
spring.datasource.url=jdbc:mysql://localhost:3306/security?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.username=root
spring.datasource.password=root

```

# 4.代码实现

## 4.1 bean

在com/fateasstring/securitydbauth/目录下增加bean文件夹，

在bean中添加类：User.class

```java
package com.fateasstring.securitydbauth.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** 如果需要自己从数据加载用户，
   那么定义用户时，需要实现一个 UserDetails 接口 */

public class User implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean locked;
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override /** 账户是否未过期 */
    public boolean isAccountNonExpired() {
    /** 这次数据库没有定义，直接写死返回true，即没有过期 */
        return true;
    }

    @Override /** 账户是否未锁定 */
    public boolean isAccountNonLocked() {
    /** 数据库有相关配置，这里改为locked，并相当于get方法 */
        return !locked;
    }

    @Override /** 密码/凭证是否过期 */
    public boolean isCredentialsNonExpired() {
        /** 直接写死 */
        return true;
    }

    @Override /** 是否可用 */
    public boolean isEnabled() {
        /** 直接返回 */
        return enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override /** 这个集合是返回用户的所有角色 */
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        /** 直接从roles中获取当前用户所具有的角色，构造SimpleGrantedAuthority然后返回 */
        for (Role role : roles){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /** 前面已经有isEnabled()相当于get方法，这里注释 */
//    public Boolean getEnabled() {
//        return enabled;
//    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /** 前面已经有isAccountNonLocked()相当于get方法，这里注释 */
//    public Boolean getLocked() {
//        return locked;
//    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}
```

在bean中添加Role.class类：

```java
package com.fateasstring.securitydbauth.bean;

public class Role {

    private Integer id;
    private String name;
    private String nameZh;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }
}

```

## 4.2 service

新增service文件夹，添加UserService.class类

```java
package com.fateasstring.securitydbauth.config;

import com.fateasstring.securitydbauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Bean
    PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/dba/**").hasRole("dba")
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .csrf().disable();

    }
}

```



## 4.3 mapper

新增接口UserMapper.class

```java
package com.fateasstring.securitydbauth.mapper;

import com.fateasstring.securitydbauth.bean.Role;
import com.fateasstring.securitydbauth.bean.User;
import java.util.List;

public interface UserMapper {

    User loadUserByUsername(String username);

    /** User中定义id为Integer */
    List<Role> getUserRolesById(Integer id);
}

```

UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.fateasstring.securitydbauth.mapper.UserMapper" >
    <select id="loadUserByUsername" resultType="com.fateasstring.securitydbauth.bean.User">
        select *from user where username=#{username}
    </select>

    <select id="getUserRolesById" resultType="com.fateasstring.securitydbauth.bean.Role">
        select *from role where id in (select rid from user_role where uid=#{id})
    </select>
</mapper>
```

## 4.4 config

SecurityConfig.class

```java
package com.fateasstring.securitydbauth.config;

        import com.fateasstring.securitydbauth.service.UserService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Bean
    PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/dba/**").hasRole("dba")
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .csrf().disable();
    }
}

```

## 4.5  更新pom.xml

```xml
<build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
            </resource>

            <resource>
                <directory>src/main/resources</directory>
            </resource>

        </resources>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

## 4.6 controller

HelloController.class

```java
package com.fateasstring.securitydbauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello security!!";
    }

    @GetMapping("/dba/hello")
    public String dba(){
        return "hello dba!!";
    }

    @GetMapping("/admin/hello")
    public String admin(){
        return "hello admin!!";
    }

    @GetMapping("/user/hello")
    public String user(){
        return "hello user!!";
    }

}

```

## 4.7 运行

root用户具备dba和admin权限，能访问：

http://localhost:8080/hello

http://localhost:8080/dba/hello

http://localhost:8080/admin/hello

admin能访问：

http://localhost:8080/admin/hello

user能访问：

http://localhost:8080/user/hello

# 5. 报错

## 5.1 XxxMapper.xml相关问题

Invalid bound statement (not found): com.fateasstring.securitydbauth.mapper.UserMapper.loadUserByUsername

可能的原因有：

1）集成MyBatis后，mapper文件夹没有放在resources路径下，但却没有更新pom依赖，解决办法时更新pom依赖：

```xml
<build>
        <!--这个元素描述了项目相关的所有资源路径列表，例如和项目相关的属性文件，这些资源被包含在最终的打包文件里。-->
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

2）xml中的方法名写错了，要和XxxMapper.class中的一致；

3）XxxMapper.xml没有创建成功，应该仔细检查是否真的以.xml结尾。

## 5.2  数据库连接问题

**create connection SQLException, url**: jdbc:mysql://localhost:3306/security

解决办法：

url: jdbc:mysql://localhost:3306/Xxx**?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8**

## 5.3 MySQL时区问题

关联mysql失败_Server returns invalid timezone. Go to 'Advanced' tab and set 'serverTimezon'

解决办法：

在mysql的命令模式下，输入：

```sql
set global time_zone='+8:00';
```

再次连接成功。

