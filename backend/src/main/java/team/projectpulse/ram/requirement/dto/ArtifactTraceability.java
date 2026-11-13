package team.projectpulse.ram.requirement.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record ArtifactTraceability(Long artifactId,
                                   List<ArtifactLinkViewDto> incoming,
                                   List<ArtifactLinkViewDto> outgoing) {
    public ArtifactTraceability {
        if (incoming != null) {
            incoming = Collections.unmodifiableList(new ArrayList<>(incoming));
        }
        if (outgoing != null) {
            outgoing = Collections.unmodifiableList(new ArrayList<>(outgoing));
        }
    }
}
