package backends.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

//适应度
@Data
@Builder
public class Adapt {
    private double distance=0;//该个体(路径)总距离
    private List<Integer> individual;
}
