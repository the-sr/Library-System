package library.projection;

public interface AddressProjection {
    long getId();

    String getStreet();

    String getCity();

    String getState();

    String getZip();

    String getCountry();
}