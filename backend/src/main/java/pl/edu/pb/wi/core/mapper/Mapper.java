package pl.edu.pb.wi.core.mapper;

import pl.edu.pb.wi.core.dto.DTO;
import pl.edu.pb.wi.ws.resposne.Response;

public interface Mapper<K extends DTO, T extends Response> {
    K map(T wsResponse);
    T map(K entity);
}
