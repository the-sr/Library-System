package library.services.mappers;

public interface MapperClass<E, D> {

    E dtoToEntity(D d);

    D entityToDto(E e);

}
