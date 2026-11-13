package team.projectpulse.ram.usecase.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record UseCaseMainStepDto(
        Long id,
        String actor,
        String actionText,
        List<UseCaseExtensionDto> extensions) {
    public UseCaseMainStepDto {
        if (extensions != null) {
            extensions = Collections.unmodifiableList(new ArrayList<>(extensions));
        }
    }
}
