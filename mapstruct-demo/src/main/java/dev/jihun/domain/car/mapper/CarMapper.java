package dev.jihun.domain.car.mapper;

import dev.jihun.domain.car.domain.Car;
import dev.jihun.domain.car.dto.CarDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface CarMapper {

    CarDto carToCarDto(Car car);

    @Mappings(
            @Mapping(source = "identificationNumber", target = "vehicleIdentificationNumber")
    )
    Car carDtoToCar(CarDto carDto);

    List<Car> carDtoToCar(List<CarDto> carDto);
}
