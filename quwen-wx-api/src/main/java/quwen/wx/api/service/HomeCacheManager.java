package quwen.wx.api.service;

//import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class HomeCacheManager {
    public static final boolean ENABLE = false;
    public static final String INDEX = "index";

    private static Map<String, Map<String, Object>> cacheDataList = new HashMap<>();

    public static void loadData(String cacheKey, Map<String, Object> data){
        Map<String, Object> cacheData = cacheDataList.get(cacheKey);
        if(cacheData != null){
            cacheData.remove(cacheKey) ;
        }
        cacheData = new HashMap<>();
        cacheData.putAll(data);
        cacheData.put("isCache", true);
        //缓存有效期10分钟
        cacheData.put("expiredTime", LocalDateTime.now().plusMinutes(10));
        cacheDataList.put(cacheKey,data);
    }

    public static Map<String, Object> getCacheData(String cacheKey){
        return cacheDataList.get(cacheKey);
    }

    public static boolean hasData(String cacheKey){
        if(!ENABLE){
            return false;
        }
        Map<String, Object> cacheData = cacheDataList.get(cacheKey);
        if(cacheData == null){
            return false;
        }else{
            LocalDateTime expire = (LocalDateTime) cacheData.get("expiredTime");
            if(expire.isBefore(LocalDateTime.now())){
                return false;
            }
            else{
                return true;
            }
        }
    }

    public static void clearAll(){
        cacheDataList = new HashMap<>();
    }

    public static void clear(String cacheKey){
        Map<String, Object> cacheData = cacheDataList.get(cacheKey);
        if(cacheData != null){
            cacheDataList.remove(cacheKey);
        }
    }
}
