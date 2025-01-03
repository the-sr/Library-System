package library.services.mappers;

public interface MapperClass<E, D, P> {

    E dtoToEntity(D d);

    D entityToDto(E e);

    D projectionToDto(P p);

}
