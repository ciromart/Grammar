package it.alma.geditor.domain;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Model.
 */
@Entity
@Table(name = "model")
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "mail_network_name")
    private String mailNetworkName;

    @Column(name = "insert_ts")
    private Instant insertTs;

    @Column(name = "last_update_ts")
    private Instant lastUpdateTs;

    @OneToMany(mappedBy = "model")
    private Set<CompilationLog> compilationLogs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("models")
    private LmTemplate lmTemplate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Model name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailNetworkName() {
        return mailNetworkName;
    }

    public Model mailNetworkName(String mailNetworkName) {
        this.mailNetworkName = mailNetworkName;
        return this;
    }

    public void setMailNetworkName(String mailNetworkName) {
        this.mailNetworkName = mailNetworkName;
    }

    public Instant getInsertTs() {
        return insertTs;
    }

    public Model insertTs(Instant insertTs) {
        this.insertTs = insertTs;
        return this;
    }

    public void setInsertTs(Instant insertTs) {
        this.insertTs = insertTs;
    }

    public Instant getLastUpdateTs() {
        return lastUpdateTs;
    }

    public Model lastUpdateTs(Instant lastUpdateTs) {
        this.lastUpdateTs = lastUpdateTs;
        return this;
    }

    public void setLastUpdateTs(Instant lastUpdateTs) {
        this.lastUpdateTs = lastUpdateTs;
    }

    public Set<CompilationLog> getCompilationLogs() {
        return compilationLogs;
    }

    public Model compilationLogs(Set<CompilationLog> compilationLogs) {
        this.compilationLogs = compilationLogs;
        return this;
    }

    public Model addCompilationLog(CompilationLog compilationLog) {
        this.compilationLogs.add(compilationLog);
        compilationLog.setModel(this);
        return this;
    }

    public Model removeCompilationLog(CompilationLog compilationLog) {
        this.compilationLogs.remove(compilationLog);
        compilationLog.setModel(null);
        return this;
    }

    public void setCompilationLogs(Set<CompilationLog> compilationLogs) {
        this.compilationLogs = compilationLogs;
    }

    public LmTemplate getLmTemplate() {
        return lmTemplate;
    }

    public Model lmTemplate(LmTemplate lmTemplate) {
        this.lmTemplate = lmTemplate;
        return this;
    }

    public void setLmTemplate(LmTemplate lmTemplate) {
        this.lmTemplate = lmTemplate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Model)) {
            return false;
        }
        return id != null && id.equals(((Model) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Model{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", mailNetworkName='" + getMailNetworkName() + "'" +
            ", insertTs='" + getInsertTs() + "'" +
            ", lastUpdateTs='" + getLastUpdateTs() + "'" +
            "}";
    }
}
