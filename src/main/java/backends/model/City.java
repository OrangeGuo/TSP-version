package backends.model;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class City implements Serializable {
    private static final long serialVersionUID = 8822818790694831649L;
    private final int x;
    private final int y;
    private final int no;
    public int getDistance(City p){
        int dx=this.x-p.getX();
        int dy=this.y-p.getY();
        return dx*dx+dy*dy;
    }
}
