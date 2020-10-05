package backends;

import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
public class Caches {
    private static final String COLON = ":";
    private final Map<String, Integer> distances = new HashMap<>();
    private  Map<Integer,City> cityMap;

    public Caches(){
        cityMap=Config.cities.stream().collect(Collectors.toMap(City::getNo,city->city));
    }

    public int getDistanceByCity(City src, City des) {
        String key = getKey(src, des);
        if (!distances.containsKey(key)) {
            log.info("Cache miss:{}", key);
            distances.put(key, src.getdistence(des));
        }
        return distances.get(key);
    }

    public City getCityByNo(int no){
        return cityMap.get(no);
    }

    private String getKey(City src, City des) {
        return src.getNo() < des.getNo() ? src.getNo() + COLON + des.getNo() :
                des.getNo() + COLON + src.getNo();
    }
}
