package travelAgency.domain;

public interface Mapper {

    <T> T toEntity(Object object);

    <T> T toViewDto(Object obj);
}
