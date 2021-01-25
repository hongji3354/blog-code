package dev.jihun.domain.car.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CarDto {

    private String manufacturer;
    private String color;
    private int numberOfSeat;
    private String identificationNumber;
}
