package hotil.baemo.domains.report.adapter.output.persist.entity.converter;

import hotil.baemo.domains.report.domain.value.post.PostReportReason;
import jakarta.persistence.AttributeConverter;

import java.util.EnumSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RelationReasonConverter implements AttributeConverter<EnumSet<PostReportReason>, String> {
    @Override
    public String convertToDatabaseColumn(EnumSet<PostReportReason> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return attribute.stream()
            .map(Enum::name)
            .collect(Collectors.joining(","));
    }

    @Override
    public EnumSet<PostReportReason> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return EnumSet.noneOf(PostReportReason.class);
        }
        String[] reasons = dbData.split(",");
        return Stream.of(reasons)
            .map(PostReportReason::valueOf)
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(PostReportReason.class)));
    }
}
