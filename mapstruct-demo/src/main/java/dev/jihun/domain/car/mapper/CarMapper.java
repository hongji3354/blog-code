package dev.jihun.domain.car.mapper;

import dev.jihun.domain.car.domain.Car;
import dev.jihun.domain.car.dto.CarDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {
    CarDto carToCarDto(Car car);

    Car carDtoToCar(CarDto carDto);
}
