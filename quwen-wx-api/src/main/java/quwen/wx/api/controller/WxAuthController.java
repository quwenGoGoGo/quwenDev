package quwen.wx.api.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import quwen.core.util.ResponseUtil;
import quwen.wx.api.dao.UserInfo;
import quwen.wx.api.dao.UserToken;
import quwen.wx.api.dao.WxLoginInfo;
import quwen.wx.api.service.UserTokenManager;
import quwen.db.domain.User;
import quwen.db.service.UserService;
import quwen.wx.api.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import me.chanjar.weixin.common.error.WxErrorException;

@RestController
@RequestMapping("/wx/auth")
@Validated
public class WxAuthController {
	private final Log logger = LogFactory.getLog(WxAuthController.class);
	
	@Autowired
	private WxMaService wxService;
	@Autowired
	private UserService userService;
	@RequestMapping("login_by_weixin")
	public Object loginByWeiXin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
		String code= wxLoginInfo.getCode();
		UserInfo userInfo = wxLoginInfo.getUserInfo();
		if(code == null || userInfo == null) {
			return ResponseUtil.badArgument();
		}
		String sessionKey = null;
		String openId = null;
		try {
			WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
			sessionKey = result.getSessionKey();
			openId = result.getOpenid();
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		if(sessionKey == null || openId == null) {
			return ResponseUtil.fail();
		}
		User user = userService.queryByOid(openId);
		if(user == null) {
			user = new User();
			user.setWeixinOpenid(openId);
			user.setNickname(userInfo.getNickName());
			user.setAvatar(userInfo.getAvatarUrl());
			user.setGender(userInfo.getGender());
			user.setLastLoginTime(LocalDateTime.now());
			user.setLastLoginIp(IpUtil.client(request));
			
			userService.saveAndFlush(user);
		}
		else {
			user.setLastLoginTime(LocalDateTime.now());
			user.setLastLoginIp(IpUtil.client(request));
			userService.saveAndFlush(user);
		}
		UserToken userToken = UserTokenManager.generateToken(user.getUserID());
		Map<String, Object> result = new HashMap<>();
		result.put("token", userToken.getToken());
		result.put("tokenExpire", userToken.getExpireTime().toString());
		result.put("userInfo", userInfo);
		return ResponseUtil.ok(result);
	}



}
