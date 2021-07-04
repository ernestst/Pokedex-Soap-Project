package pl.edu.pb.wi.ws.resposne;

import java.util.List;
import lombok.Data;

@Data
public class Page<T> {
    private int page;
    private int pages;
    private List<T> results;
}
