package de.civento.eahtools.routingrepository.base.businessobjects;

import java.util.List;


public class PageOfBusinessObjects<T> implements IPageBusinessObjects<T> {

    private int totalPages;
    private long totalElements;
    private int number;
    private int size;
    private List<T> content;

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public long getTotalElements() {
        return totalElements;
    }

    @Override
    public int getPageNumber() {
        return number;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    @Override
    public void setPageNumber(int number) {
        this.number = number;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void setContent(List<T> content) {
        this.content = content;
    }

    @Override
    public <G> IPageBusinessObjects<G> copyTo(IPageBusinessObjects<G> destination, IMapper<T, G> mapper) {
        destination.setPageNumber(this.getPageNumber());
        destination.setSize(this.getSize());
        destination.setTotalElements(this.getTotalElements());
        destination.setTotalPages(getTotalPages());
        this.getContent().parallelStream().forEach(e -> destination.getContent().add(mapper.convert(e)));
        return destination;
    }
}
