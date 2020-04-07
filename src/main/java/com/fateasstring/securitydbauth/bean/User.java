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
