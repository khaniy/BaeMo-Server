package hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.mapper;

import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ExerciseLocationEntity;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

public class ExerciseLocationEntityMapper {

    private static final GeometryFactory geometryFactory = new GeometryFactory();

    public static ExerciseLocationEntity toEntity(ExerciseId exerciseId, Exercise exercise){
        return ExerciseLocationEntity.builder()
            .exerciseId(exerciseId.id())
            .location(exercise.getLocation().location())
            .address(exercise.getAddress().address())
            .locationCode(Long.parseLong(exercise.getLocationCode().code()))
            .coordinate(geometryFactory.createPoint(new Coordinate(exercise.getCoordinate().longitude(), exercise.getCoordinate().latitude())))
            .build();
    }
}
