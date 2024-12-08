package hotil.baemo.domains.exercise.domain.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Coordinate(
    @NotNull
    @Min(-90)
    @Max(90)
    double latitude,
    @NotNull
    @Min(-180)
    @Max(180)
    double longitude
){
    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        BaemoValueObjectValidator.valid(this);
    }
}