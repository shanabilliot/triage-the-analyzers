package team.projectpulse.ram.document.template;

import team.projectpulse.ram.requirement.SectionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentTemplate {

    private String title;
    private List<SectionTemplate> sections = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Preserve null for a template that explicitly has no sections.
    @SuppressWarnings("PMD.ReturnEmptyCollectionRatherThanNull")
    public List<SectionTemplate> getSections() {
        return sections == null ? null : Collections.unmodifiableList(copySections(sections));
    }

    public void setSections(List<SectionTemplate> sections) {
        this.sections = copySections(sections);
    }

    // Preserve null because YAML may explicitly provide a null sections value.
    @SuppressWarnings("PMD.ReturnEmptyCollectionRatherThanNull")
    private static List<SectionTemplate> copySections(List<SectionTemplate> sections) {
        if (sections == null) {
            return null;
        }

        List<SectionTemplate> copy = new ArrayList<>();
        for (SectionTemplate section : sections) {
            if (section == null) {
                copy.add(null);
                continue;
            }

            SectionTemplate sectionCopy = new SectionTemplate();
            sectionCopy.setSectionKey(section.getSectionKey());
            sectionCopy.setTitle(section.getTitle());
            sectionCopy.setType(section.getType());
            sectionCopy.setGuidance(section.getGuidance());
            copy.add(sectionCopy);
        }
        return copy;
    }

    public static class SectionTemplate {
        private String sectionKey;
        private String title;
        private SectionType type;
        private String guidance;

        public String getSectionKey() {
            return sectionKey;
        }

        public void setSectionKey(String sectionKey) {
            this.sectionKey = sectionKey;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public SectionType getType() {
            return type;
        }

        public void setType(SectionType type) {
            this.type = type;
        }

        public String getGuidance() {
            return guidance;
        }

        public void setGuidance(String guidance) {
            this.guidance = guidance;
        }
    }
}
