package com.xxl.job.admin.shiro;

import com.xxl.job.admin.dao.UserMapper;
import com.xxl.job.admin.model.Resources;
import com.xxl.job.admin.model.Role;
import com.xxl.job.admin.model.User;
import com.xxl.job.admin.service.ResourcesService;
import com.xxl.job.admin.service.RoleService;
import com.xxl.job.admin.service.UserRoleService;
import com.xxl.job.admin.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: 刘广鑫
 * @Date: 2019-01-10 08:43
 * @Copyright: 2018 www.pansoft.com Inc. All rights reserved.
 **/
public class MyCustomRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private ResourcesService resourcesService;

    /**
     * 获取授权信息
     *
     * @param  principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user= (User) SecurityUtils.getSubject().getPrincipal();//User{id=1, username='admin', password='3ef7164d1f6167cb9f2658c07d3c2f0a', enable=1}
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userid",user.getId());
        List<Resources> resourcesList = resourcesService.loadUserResources(map);
        List<Integer> roles = userRoleService.getRoleByUser(user.getId());
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (resourcesList != null && resourcesList.size() > 0){
            for(Resources resources: resourcesList){
                info.addStringPermission(resources.getResurl());
            }
        }
        if (roles != null && roles.size() > 0){
            for(Integer integer: roles){
                info.addRole(String.valueOf(integer));
            }
        }
        return info;
    }
    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     *
     * @param token 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        User user = userService.selectByUsername(username);
        if(user==null) throw new UnknownAccountException();
        if (0==user.getEnable()) {
            throw new LockedAccountException(); // 帐号锁定
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户
                user.getPassword(), //密码
                ByteSource.Util.bytes(username),
                getName()  //realm name
        );
        // 当验证都通过后，把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userSession", user);
        session.setAttribute("userSessionId", user.getId());
        return authenticationInfo;
    }


    /**
     * 根据userId 清除当前session存在的用户的权限缓存
     * @param userIds 已经修改了权限的userId
     */
//    public void clearUserAuthByUserId(List<Integer> userIds){
//        if(null == userIds || userIds.size() == 0)	return ;
//        //获取所有session
//        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
//        //定义返回
//        List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
//        for (Session session:sessions){
//            //获取session登录信息。
//            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
//            if(null != obj && obj instanceof SimplePrincipalCollection){
//                //强转
//                SimplePrincipalCollection spc = (SimplePrincipalCollection)obj;
//                //判断用户，匹配用户ID。
//                obj = spc.getPrimaryPrincipal();
//                if(null != obj && obj instanceof User){
//                    User user = (User) obj;
//                    System.out.println("user:"+user);
//                    //比较用户ID，符合即加入集合
//                    if(null != user && userIds.contains(user.getId())){
//                        list.add(spc);
//                    }
//                }
//            }
//        }
//        RealmSecurityManager securityManager =
//                (RealmSecurityManager) SecurityUtils.getSecurityManager();
//        MyShiroRealm realm = (MyShiroRealm)securityManager.getRealms().iterator().next();
//        for (SimplePrincipalCollection simplePrincipalCollection : list) {
//            realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
//        }
//    }
}
