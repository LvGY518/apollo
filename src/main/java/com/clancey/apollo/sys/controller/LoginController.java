package com.clancey.apollo.sys.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.clancey.apollo.common.base.BaseController;
import com.clancey.apollo.common.utils.JwtUtil;
import com.clancey.apollo.common.utils.PasswordUtil;
import com.clancey.apollo.common.utils.dingding.DingDingUtil;
import com.clancey.apollo.common.utils.dingding.pojo.DingDingUserInfoVo;
import com.clancey.apollo.common.utils.dingding.pojo.DingDingUserVo;
import com.clancey.apollo.common.utils.ocr.CustomTemplateScan;
import com.clancey.apollo.common.vo.CommonResponse;
import com.clancey.apollo.common.vo.Result;
import com.clancey.apollo.config.DingDingConfig;
import com.clancey.apollo.sys.entity.User;
import com.clancey.apollo.sys.service.UserService;
import com.clancey.apollo.sys.vo.UserVo;
import com.clancey.apollo.sys.vo.UsernamePasswordVo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "LoginController", description = "用户登录登出接口")
@Controller
public class LoginController extends BaseController<UserService, User> {
    @Autowired
    UserService userService;

    @ApiIgnore
    @RequestMapping(value = "/login")
    public String doLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("appId", DingDingConfig.APP_ID);
        model.addAttribute("redirectUrl", DingDingConfig.URL);

        //已经登录过，直接进入主页
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        if (subject.isAuthenticated()) {
            Object authorized = session.getAttribute("isAuthorized");
            //boolean isAuthorized = Boolean.valueOf(subject.getSession().getAttribute("isAuthorized").toString());
            if (authorized != null && Boolean.valueOf(authorized.toString()))
                return "main";
        }
        String userName = request.getParameter("username");
        //默认首页，第一次进来
        if (StringUtils.isEmpty(userName)) {
            return "login";
        }
        String password = request.getParameter("password");
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        token.setRememberMe(true);
        String msg;
        try {
            subject.login(token);
            //通过认证
            if (subject.isAuthenticated()) {
                session.setAttribute("isAuthorized", true);
                session.setAttribute("user", userService.getUserByUsername(userName));

                SavedRequest savedRequest = WebUtils.getSavedRequest(request);

                String lastUrl = savedRequest == null ? "login" : savedRequest.getRequestUrl();
                WebUtils.issueRedirect(request, response, lastUrl);
                return null;
            } else {
                return "login";
            }
            //0 未授权 1 账号问题 2 密码错误  3 账号密码错误
        } catch (IncorrectCredentialsException e) {
            msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect";
            model.addAttribute("message", new Result("2", msg));
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
            model.addAttribute("message", new Result("3", msg));
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
            model.addAttribute("message", new Result("1", msg));
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
            model.addAttribute("message", new Result("1", msg));
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
            model.addAttribute("message", new Result("1", msg));
        } catch (UnknownAccountException e) {
            msg = "帐号不存在. There is no user with username of " + token.getPrincipal();
            model.addAttribute("message", new Result("1", msg));
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！" + e.getMessage();
            model.addAttribute("message", new Result("1", msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "login";
    }

    @ApiOperation(value="用户登录", notes="用户登录接口")
    @ApiImplicitParams({
          @ApiImplicitParam(name = "username", value = "用户名", required = true ,dataType = "string"),
          @ApiImplicitParam(name = "password", value = "密码", required = true ,dataType = "string")
    })
    @ResponseBody
    @RequestMapping(value = "/login/login_ajax",method = RequestMethod.POST)
    public CommonResponse doLoginAjax(@RequestBody @Valid UsernamePasswordVo usernamePasswordVo) {
        //已经登录过，返回成功
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()) {
            Object authorized = subject.getSession().getAttribute("isAuthorized");
            //boolean isAuthorized = Boolean.valueOf(subject.getSession().getAttribute("isAuthorized").toString());
            if (authorized != null && Boolean.valueOf(authorized.toString()))
                return success();
        }
        String userName = usernamePasswordVo.getUsername();
        //默认首页，第一次进来
        if (StringUtils.isEmpty(userName)) {
            return fail("000001", "请填写用户名");
        }
        String password = usernamePasswordVo.getPassword();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        token.setRememberMe(true);
        subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            if (!subject.isAuthenticated()) {
                return fail("000010", "登录失败，未知错误");
            }

            //通过认证
            if (subject.isAuthenticated()) {
                User user = (User) SecurityUtils.getSubject().getPrincipal();
                //List<String> roles = roleService.selectRoleIdsByUserId(user.getId());
                //Set<String> functions = functionService.getFunctionCodeSet(roles, userId);
                //---------调用realm doGetAuthorizationInfo----------
                //boolean isPermitted = subject.isPermitted("user");

                //if (roles.isEmpty()) {
                //    subject.getSession().setAttribute("isAuthorized", false);
                //    return fail("000002", "您没有得到相应的授权");
                //}

                String departmentId = user.getDepartmentId();
                String roleId = user.getRoleId();
                UserVo userVo = new UserVo();
                userVo.setUsername(user.getUsername());
                userVo.setName(user.getName());
                userVo.setDepartmentId(departmentId);
                userVo.setRoleId(roleId);
                userVo.setLeader(user.getLeader());
                subject.getSession().setAttribute("isAuthorized", true);
                return success("登录成功", userVo);
            }
            //0 未授权 1 账号问题 2 密码错误  3 账号密码错误
        } catch (IncorrectCredentialsException e) {
            return fail("000003", "登录密码错误");
        } catch (ExcessiveAttemptsException e) {
            return fail("000004", "登录失败次数过多");
        } catch (LockedAccountException e) {
            return fail("000005", "帐号已被锁定");
        } catch (DisabledAccountException e) {
            return fail("000006", "帐号已被禁");
        } catch (ExpiredCredentialsException e) {
            return fail("000007", "帐号已过期");
        } catch (UnknownAccountException e) {
            return fail("000008", "帐号不存在");
        } catch (UnauthorizedException e) {
            return fail("000009", "您没有得到相应的授权");
        }
        return fail("000010", "登录失败，未知错误");
    }

    /**
     * 微信登录
     * @param request
     * @param code
     * @param state
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login/login_wechat", method = RequestMethod.POST)
    public Map<String, Object> doLoginWechat(HttpServletRequest request, String code, String state) {
        Map<String, Object> result = new HashMap<>();
        result.put("errcode", 0);
        result.put("token", JwtUtil.createToken(code));
        return result;
    }

    /**
     * 微信登录
     * @param usernamePasswordVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login/jwt_auth", method = RequestMethod.POST)
    public CommonResponse jwtAuth(@RequestBody @Valid UsernamePasswordVo usernamePasswordVo) {
        String username = usernamePasswordVo.getUsername();
        String password = usernamePasswordVo.getPassword();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return fail("000001", "用户名或密码错误");
        }

        if (!PasswordUtil.match(password, user.getPassword())) {
            return fail("000001", "用户名或密码错误");
        }

        Map<String, Object> result = new HashMap<>();
        result.put("token", JwtUtil.createToken(username));
        return success(result);
    }

    @ApiIgnore
    @ResponseBody
    @RequestMapping(value = "/login/doLoginDingDing")
    public DingDingUserInfoVo doLoginDingDing(String code, String state) {
        DingDingUserVo userVo = DingDingUtil.getPersistentInfo(code);
        String snsToken = DingDingUtil.getSnsToken(userVo.getOpenId(), userVo.getPersistentCode());
        DingDingUserInfoVo userInfoVo = DingDingUtil.getUserInfo(snsToken);
        return userInfoVo;
    }

    @RequestMapping(value = "/ocr",method = RequestMethod.POST)
    @ResponseBody
    public String ocr(HttpServletRequest request, Model model) {

        String imgUrl = "C:\\Users\\16692\\Desktop\\daobantu\\1526369754604AI一撕得箱型.jpg";
        try {
            System.out.println(CustomTemplateScan.knifeLayoutScan(imgUrl,true).toJSONString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "login";
    }
}
