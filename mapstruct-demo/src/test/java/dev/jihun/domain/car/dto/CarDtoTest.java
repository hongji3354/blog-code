package dev.jihun.domain.car.dto;

import dev.jihun.domain.car.domain.Car;
import dev.jihun.domain.car.mapper.CarMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarDtoTest {

    @Autowired
    CarMapper carMapper;

    @DisplayName("CarToCarDto 매핑 테스트")
    @Test
    public void carToCarDto(){
        Car car = Car.builder()
                .vehicleIdentificationNumber("202001030000001")
                .manufacturer("Hyundai")
                .color("RED")
                .numberOfSeat(5)
                .build();
        CarDto carDto = carMapper.carToCarDto(car);
        assertThat(carDto.getManufacturer(),is("Hyundai"));
        assertThat(carDto.getColor(),is("RED"));
        assertThat(carDto.getNumberOfSeat(),is(5));
    }
}