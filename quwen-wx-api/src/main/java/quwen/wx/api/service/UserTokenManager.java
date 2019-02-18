package quwen.wx.api.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import quwen.core.util.CharUtil;
import quwen.wx.api.dao.UserToken;


public class UserTokenManager {
	private static Map<String, UserToken> tokens = new HashMap<>();
	private static Map<Integer, UserToken> ids = new HashMap<>();
	
	public static Integer getUserId(String token) {
		UserToken userToken = tokens.get(token);
		if(userToken == null) {
			return null;
		}
		if(userToken.getExpireTime().isBefore(LocalDateTime.now())){
			tokens.remove(token);
			ids.remove(userToken.getUserId());
			return null;
		}
		return userToken.getUserId();
	}
	public static UserToken generateToken(Integer id) {
		UserToken userToken =null;
		String token = CharUtil.getRandomString(32);
        while (tokens.containsKey(token)) {
            token = CharUtil.getRandomString(32);
        }
        
        LocalDateTime update = LocalDateTime.now();
        LocalDateTime expire =update.plusDays(1);
        
        userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUpdateTime(update);
        userToken.setExpireTime(expire);
        userToken.setUserId(id);
        tokens.put(token, userToken);
        ids.put(id, userToken);
        
        return userToken;
	}
	
	public static void removeToken(Integer id) {
		UserToken userToken = ids.get(id);
		String token = userToken.getToken();
		tokens.remove(token);
		ids.remove(id);
		
	}
	
    public static String getSessionKey(Integer userId) {
        UserToken userToken = ids.get(userId);
        if (userToken == null) {
            return null;
        }

        if (userToken.getExpireTime().isBefore(LocalDateTime.now())) {
            tokens.remove(userToken.getToken());
            ids.remove(userId);
            return null;
        }

        return userToken.getSessionKey();
    }
}
