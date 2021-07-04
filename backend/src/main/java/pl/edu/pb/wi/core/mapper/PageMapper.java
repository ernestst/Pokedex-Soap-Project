package pl.edu.pb.wi.core.mapper;

import java.util.stream.Collectors;
import pl.edu.pb.wi.core.dto.DTO;
import pl.edu.pb.wi.core.dto.Pageable;
import pl.edu.pb.wi.ws.resposne.Page;
import pl.edu.pb.wi.ws.resposne.Response;

public class PageMapper<K extends DTO, T extends Response> {

    private final Mapper<K, T> mapper;

    public PageMapper(Mapper<K, T> mapper) {
        this.mapper = mapper;
    }

    public Page<T> map(Pageable<K> page) {
        Page<T> response = new Page<>();
        response.setPage(page.getPage());
        response.setPages(page.getPages());
        response.setResults(page.getResults().stream().map(mapper::map).collect(Collectors.toList()));

        return response;
    }
}
