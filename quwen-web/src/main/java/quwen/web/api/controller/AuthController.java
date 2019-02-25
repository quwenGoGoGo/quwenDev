package quwen.web.api.controller;

import org.apache.http.entity.mime.content.StringBody;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import quwen.core.util.JacksonUtil;
import quwen.core.util.ResponseUtil;

import static quwen.web.api.util.AdminResponseCode.ADMIN_INVALID_ACCOUNT;

@Controller
@RequestMapping("/")
public class AuthController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/authlogin")
    @ResponseBody
    public Object login(@RequestBody String body) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ResponseUtil.badArgument();
        }

        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(new UsernamePasswordToken(username, password));
        } catch (UnknownAccountException uae) {
            return ResponseUtil.fail(ADMIN_INVALID_ACCOUNT, "用户帐号或密码不正确");
        } catch (LockedAccountException lae) {
            return ResponseUtil.fail(ADMIN_INVALID_ACCOUNT, "用户帐号已锁定不可用");

        } catch (AuthenticationException ae) {
            return ResponseUtil.fail(ADMIN_INVALID_ACCOUNT, ae.getMessage());
        }
        System.out.println(currentUser.getSession().getId());
        return ResponseUtil.ok(currentUser.getSession().getId());
    }

    @GetMapping("/logout")
    @ResponseBody
    public Object logout(){
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return ResponseUtil.ok();
    }
}
