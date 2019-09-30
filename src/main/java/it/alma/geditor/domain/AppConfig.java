package it.alma.geditor.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A AppConfig.
 */
@Entity
@Table(name = "app_config")
public class AppConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "critical_words_max_file_size")
    private Long criticalWordsMaxFileSize;

    @Column(name = "critical_words_max_words")
    private Long criticalWordsMaxWords;

    @Column(name = "additional_context_max_file_size")
    private Long additionalContextMaxFileSize;

    @Column(name = "additional_context_max_file_words")
    private Long additionalContextMaxFileWords;

    @Column(name = "min_occurency_context")
    private Long minOccurencyContext;

    @Column(name = "windows_max_words")
    private Long windowsMaxWords;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCriticalWordsMaxFileSize() {
        return criticalWordsMaxFileSize;
    }

    public AppConfig criticalWordsMaxFileSize(Long criticalWordsMaxFileSize) {
        this.criticalWordsMaxFileSize = criticalWordsMaxFileSize;
        return this;
    }

    public void setCriticalWordsMaxFileSize(Long criticalWordsMaxFileSize) {
        this.criticalWordsMaxFileSize = criticalWordsMaxFileSize;
    }

    public Long getCriticalWordsMaxWords() {
        return criticalWordsMaxWords;
    }

    public AppConfig criticalWordsMaxWords(Long criticalWordsMaxWords) {
        this.criticalWordsMaxWords = criticalWordsMaxWords;
        return this;
    }

    public void setCriticalWordsMaxWords(Long criticalWordsMaxWords) {
        this.criticalWordsMaxWords = criticalWordsMaxWords;
    }

    public Long getAdditionalContextMaxFileSize() {
        return additionalContextMaxFileSize;
    }

    public AppConfig additionalContextMaxFileSize(Long additionalContextMaxFileSize) {
        this.additionalContextMaxFileSize = additionalContextMaxFileSize;
        return this;
    }

    public void setAdditionalContextMaxFileSize(Long additionalContextMaxFileSize) {
        this.additionalContextMaxFileSize = additionalContextMaxFileSize;
    }

    public Long getAdditionalContextMaxFileWords() {
        return additionalContextMaxFileWords;
    }

    public AppConfig additionalContextMaxFileWords(Long additionalContextMaxFileWords) {
        this.additionalContextMaxFileWords = additionalContextMaxFileWords;
        return this;
    }

    public void setAdditionalContextMaxFileWords(Long additionalContextMaxFileWords) {
        this.additionalContextMaxFileWords = additionalContextMaxFileWords;
    }

    public Long getMinOccurencyContext() {
        return minOccurencyContext;
    }

    public AppConfig minOccurencyContext(Long minOccurencyContext) {
        this.minOccurencyContext = minOccurencyContext;
        return this;
    }

    public void setMinOccurencyContext(Long minOccurencyContext) {
        this.minOccurencyContext = minOccurencyContext;
    }

    public Long getWindowsMaxWords() {
        return windowsMaxWords;
    }

    public AppConfig windowsMaxWords(Long windowsMaxWords) {
        this.windowsMaxWords = windowsMaxWords;
        return this;
    }

    public void setWindowsMaxWords(Long windowsMaxWords) {
        this.windowsMaxWords = windowsMaxWords;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppConfig)) {
            return false;
        }
        return id != null && id.equals(((AppConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
            "id=" + getId() +
            ", criticalWordsMaxFileSize=" + getCriticalWordsMaxFileSize() +
            ", criticalWordsMaxWords=" + getCriticalWordsMaxWords() +
            ", additionalContextMaxFileSize=" + getAdditionalContextMaxFileSize() +
            ", additionalContextMaxFileWords=" + getAdditionalContextMaxFileWords() +
            ", minOccurencyContext=" + getMinOccurencyContext() +
            ", windowsMaxWords=" + getWindowsMaxWords() +
            "}";
    }
}
