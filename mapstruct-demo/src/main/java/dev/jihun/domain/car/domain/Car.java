package dev.jihun.domain.car.domain;

import lombok.*;

import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Car {

    @Id
    private String vehicleIdentificationNumber;
    private String manufacturer;
    private String color;
    private int numberOfSeat;

    @Builder
    public Car(String vehicleIdentificationNumber, String manufacturer, String color, int numberOfSeat) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
        this.manufacturer = manufacturer;
        this.color = color;
        this.numberOfSeat = numberOfSeat;
    }
}
