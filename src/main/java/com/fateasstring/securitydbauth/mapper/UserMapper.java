package com.fateasstring.securitydbauth.mapper;

        import com.fateasstring.securitydbauth.bean.Role;
        import com.fateasstring.securitydbauth.bean.User;
        import java.util.List;

public interface UserMapper {

    User loadUserByUsername(String username);

    /** User中定义id为Integer */
    List<Role> getUserRolesById(Integer id);
}
