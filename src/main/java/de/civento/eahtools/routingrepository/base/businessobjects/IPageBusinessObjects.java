package de.civento.eahtools.routingrepository.base.businessobjects;

import java.util.List;

public interface IPageBusinessObjects<T> {
    int getTotalPages();
    void setTotalPages(int totalPages);
    long getTotalElements();
    void setTotalElements(long totalElements);
    int getPageNumber();
    void setPageNumber(int number);
    int getPageSize();
    void setPageSize(int size);
    List<T> getContent();
    void setContent(List<T> content);
    <G> IPageBusinessObjects<G> copyTo(IPageBusinessObjects<G> destination, IMapper<T, G> mapper);
}
