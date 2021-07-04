package pl.edu.pb.wi.core.dto;

import java.util.List;

public interface Pageable<T> {
    void setPage(int page);
    int getPage();
    void setPages(int pages);
    int getPages();
    void setResults(List<T> results);
    List<T> getResults();
}
