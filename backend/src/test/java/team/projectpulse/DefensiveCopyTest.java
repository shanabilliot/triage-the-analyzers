package team.projectpulse;

import org.junit.jupiter.api.Test;
import team.projectpulse.evaluation.PeerEvaluationAverage;
import team.projectpulse.evaluation.WeeklyPeerEvaluationReport;
import team.projectpulse.notification.dto.OwingStudentDto;
import team.projectpulse.notification.dto.OwingTeamDto;
import team.projectpulse.ram.document.template.DocumentTemplate;
import team.projectpulse.rubric.dto.CriterionDto;
import team.projectpulse.rubric.dto.RubricDto;
import team.projectpulse.section.Section;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefensiveCopyTest {

    @Test
    void sectionActiveWeeksAreCopiedAtBothBoundaries() {
        Section section = new Section();
        List<String> supplied = new ArrayList<>(List.of("2026-W01"));

        section.setActiveWeeks(supplied);
        supplied.add("2026-W02");

        assertThat(section.getActiveWeeks()).containsExactly("2026-W01");
        assertThatThrownBy(() -> section.getActiveWeeks().add("2026-W03"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void evaluationAverageCommentsAreCopiedAndRetainNullElements() {
        PeerEvaluationAverage average = new PeerEvaluationAverage();
        List<String> supplied = new ArrayList<>();
        supplied.add("useful");
        supplied.add(null);

        average.setPublicComments(supplied);
        supplied.add("later");

        assertThat(average.getPublicComments()).containsExactly("useful", null);
        assertThatThrownBy(() -> average.getPublicComments().add("outside"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void collectionRecordsCopyInputsAndExposeUnmodifiableSnapshots() {
        List<OwingStudentDto> students = new ArrayList<>();
        students.add(null);
        OwingTeamDto team = new OwingTeamDto(1, "Team", students);
        students.clear();

        assertThat(team.students()).containsExactly((OwingStudentDto) null);
        assertThatThrownBy(() -> team.students().clear())
                .isInstanceOf(UnsupportedOperationException.class);

        Set<CriterionDto> criteria = new LinkedHashSet<>();
        criteria.add(null);
        RubricDto rubric = new RubricDto(1, "Rubric", criteria, 1);
        criteria.clear();

        assertThat(rubric.criteria()).containsExactly((CriterionDto) null);
        assertThatThrownBy(() -> rubric.criteria().clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void weeklyReportKeepsItsIntentionalLiveGetterAndCopiesSetterInput() {
        WeeklyPeerEvaluationReport report = new WeeklyPeerEvaluationReport();
        List<PeerEvaluationAverage> supplied = new ArrayList<>();

        report.setPeerEvaluationAverages(supplied);
        supplied.add(new PeerEvaluationAverage());
        assertThat(report.getPeerEvaluationAverages()).isEmpty();

        report.getPeerEvaluationAverages().add(new PeerEvaluationAverage());
        assertThat(report.getPeerEvaluationAverages()).hasSize(1);
    }

    @Test
    void documentTemplateCopiesMutableSectionsAtBothBoundaries() {
        DocumentTemplate template = new DocumentTemplate();
        DocumentTemplate.SectionTemplate section = new DocumentTemplate.SectionTemplate();
        section.setTitle("Original");
        List<DocumentTemplate.SectionTemplate> supplied = new ArrayList<>(List.of(section));

        template.setSections(supplied);
        supplied.clear();
        section.setTitle("Changed input");

        assertThat(template.getSections()).hasSize(1);
        assertThat(template.getSections().get(0).getTitle()).isEqualTo("Original");
        assertThatThrownBy(() -> template.getSections().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        template.getSections().get(0).setTitle("Changed output");
        assertThat(template.getSections().get(0).getTitle()).isEqualTo("Original");

        template.setSections(null);
        assertThat(template.getSections()).isNull();
    }
}
