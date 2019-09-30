package it.alma.geditor.domain;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A CompilationFile.
 */
@Entity
@Table(name = "compilation_file")
public class CompilationFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path")
    private String path;

    @ManyToOne
    @JsonIgnoreProperties("compilationFiles")
    private CompilationLog compilationLog;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public CompilationFile path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public CompilationLog getCompilationLog() {
        return compilationLog;
    }

    public CompilationFile compilationLog(CompilationLog compilationLog) {
        this.compilationLog = compilationLog;
        return this;
    }

    public void setCompilationLog(CompilationLog compilationLog) {
        this.compilationLog = compilationLog;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompilationFile)) {
            return false;
        }
        return id != null && id.equals(((CompilationFile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompilationFile{" +
            "id=" + getId() +
            ", path='" + getPath() + "'" +
            "}";
    }
}
