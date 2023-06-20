package de.civento.eahtools.routingrepository.base.businessobjects;

public interface IMapper<T, G> {
    G convert(T t);
}
