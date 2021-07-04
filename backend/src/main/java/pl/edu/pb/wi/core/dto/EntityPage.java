package pl.edu.pb.wi.core.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityPage<T> implements Pageable<T> {
    private int page;
    private int pages;
    private List<T> results;
}
