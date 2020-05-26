package com.bysj.sys.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.bysj.common.util.MD5Util;
import com.bysj.sys.entity.Role;
import com.bysj.sys.entity.User;
import com.bysj.sys.mapper.RoleMapper;
import com.bysj.sys.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 登录认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        // 1.获取用户输入的用户名
        String username = token.getUsername();
        // 2.获取用户输入的密码
        String password = new String(token.getPassword());

        // 3.根据用户名及用户类型 去DB查询对应的用户信息
        QueryWrapper<User> param = new QueryWrapper<>();
        param.eq("password", MD5Util.md5_public_salt(password));
        param.eq("user_id",username);
        User user = userMapper.selectOne(param);

        if(user == null) {
            throw new UnknownAccountException("用户名或密码错误");
        }

        if (user.getStatus() == 1) {
            throw new DisabledAccountException("账号被禁用");
        }
        if (user.getStatus() == 2) {
            throw new LockedAccountException("账号被锁定");
        }
        System.out.println("认证成功...");
        // 创建简单认证信息对象
        SimpleAuthenticationInfo info =
                new SimpleAuthenticationInfo(user, token.getCredentials(), getName());
        return info;
    }


    /**
     * 授权
     * 将认证通过的用户的角色和权限信息设置到对应用户主体上
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        User user = (User) principals.getPrimaryPrincipal();
        // 简单授权信息对象，对象中包含用户的角色和权限信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //从数据库获取当前用户的角色 通过用户名查询该用户拥有的角色名称
         //1.通过用户名获取用户类型
        QueryWrapper<User>  wrapperUser = new QueryWrapper();
        wrapperUser.eq("user_id",user.getUserId());
        int usertype = userMapper.selectOne(wrapperUser).getUsertype();
        //2.根据用户类型查出用户角色名称
        QueryWrapper<Role>  wrapperRole = new QueryWrapper();
        wrapperRole.eq("role_id",usertype);
        String roleName =  roleMapper.selectOne(wrapperRole).getRolename();
        info.addRole(roleName);
        //从数据库获取当前用户的权限 通过用户名查询该用户拥有的权限名称
        Set<String> permissionNameSet = userMapper.selectUserPermissionNameSet(user.getUserId());

        Set<String> permissions = new HashSet<>();
        for(String name : permissionNameSet) {
            for(String permission : name.split(",")){
                permissions.add(permission);
            }
        }
        info.addStringPermissions(permissions);
        System.out.println("授权完成....");
        return info;
    }
}
