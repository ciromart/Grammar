package it.alma.geditor.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A CompilationLog.
 */
@Entity
@Table(name = "compilation_log")
public class CompilationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "insert_ts")
    private Instant insertTs;

    @Column(name = "last_updat_ts")
    private Instant lastUpdatTs;

    @Column(name = "status")
    private String status;

    @Column(name = "rpk_link")
    private String rpkLink;

    @OneToMany(mappedBy = "compilationLog")
    private Set<CompilationFile> compilationFiles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("compilationLogs")
    private Model model;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInsertTs() {
        return insertTs;
    }

    public CompilationLog insertTs(Instant insertTs) {
        this.insertTs = insertTs;
        return this;
    }

    public void setInsertTs(Instant insertTs) {
        this.insertTs = insertTs;
    }

    public Instant getLastUpdatTs() {
        return lastUpdatTs;
    }

    public CompilationLog lastUpdatTs(Instant lastUpdatTs) {
        this.lastUpdatTs = lastUpdatTs;
        return this;
    }

    public void setLastUpdatTs(Instant lastUpdatTs) {
        this.lastUpdatTs = lastUpdatTs;
    }

    public String getStatus() {
        return status;
    }

    public CompilationLog status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRpkLink() {
        return rpkLink;
    }

    public CompilationLog rpkLink(String rpkLink) {
        this.rpkLink = rpkLink;
        return this;
    }

    public void setRpkLink(String rpkLink) {
        this.rpkLink = rpkLink;
    }

    public Set<CompilationFile> getCompilationFiles() {
        return compilationFiles;
    }

    public CompilationLog compilationFiles(Set<CompilationFile> compilationFiles) {
        this.compilationFiles = compilationFiles;
        return this;
    }

    public CompilationLog addCompilationFile(CompilationFile compilationFile) {
        this.compilationFiles.add(compilationFile);
        compilationFile.setCompilationLog(this);
        return this;
    }

    public CompilationLog removeCompilationFile(CompilationFile compilationFile) {
        this.compilationFiles.remove(compilationFile);
        compilationFile.setCompilationLog(null);
        return this;
    }

    public void setCompilationFiles(Set<CompilationFile> compilationFiles) {
        this.compilationFiles = compilationFiles;
    }

    public Model getModel() {
        return model;
    }

    public CompilationLog model(Model model) {
        this.model = model;
        return this;
    }

    public void setModel(Model model) {
        this.model = model;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompilationLog)) {
            return false;
        }
        return id != null && id.equals(((CompilationLog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompilationLog{" +
            "id=" + getId() +
            ", insertTs='" + getInsertTs() + "'" +
            ", lastUpdatTs='" + getLastUpdatTs() + "'" +
            ", status='" + getStatus() + "'" +
            ", rpkLink='" + getRpkLink() + "'" +
            "}";
    }
}
