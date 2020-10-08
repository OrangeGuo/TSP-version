package backends.model;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Data
@Builder
public class Solution implements Serializable {
    private static final long serialVersionUID = 8822818790694831649L;
    private final ArrayList<City> bestPath;
    private final double distance;
    private final long time;
    private final long runTime;
    private final String algorithm;
}
