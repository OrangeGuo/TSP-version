package backends.model;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class City {
    private final int x;
    private final int y;
    private final int no;
    public int getdistence(City p){
        int dx=this.x-p.getX();
        int dy=this.y-p.getY();
        return dx*dx+dy*dy;
    }
}
