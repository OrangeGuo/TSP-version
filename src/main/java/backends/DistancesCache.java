package backends;

import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class DistancesCache {
    private static final String COLON = ":";
    private final Map<String, Integer> distances = new HashMap<>();


    public int getDistanceByCity(City src, City des) {
        String key = getKey(src, des);
        if (!distances.containsKey(key)) {
            log.info("Cache miss:{}", key);
            distances.put(key, src.getdistence(des));
        }
        return distances.get(key);
    }

    private String getKey(City src, City des) {
        return src.getNo() < des.getNo() ? src.getNo() + COLON + des.getNo() :
                des.getNo() + COLON + src.getNo();
    }
}
