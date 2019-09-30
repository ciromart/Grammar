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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A LmTemplate.
 */
@Entity
@Table(name = "lm_template")
public class LmTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lang_code")
    private Long langCode;

    @Column(name = "country_code")
    private Long countryCode;

    @Column(name = "lm_standard_code")
    private Long lmStandardCode;

    @Column(name = "path")
    private String path;

    @Column(name = "insert_ts")
    private Instant insertTs;

    @Column(name = "last_update_ts")
    private Instant lastUpdateTs;

    @OneToMany(mappedBy = "lmTemplate")
    private Set<Model> models = new HashSet<>();

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

    public LmTemplate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLangCode() {
        return langCode;
    }

    public LmTemplate langCode(Long langCode) {
        this.langCode = langCode;
        return this;
    }

    public void setLangCode(Long langCode) {
        this.langCode = langCode;
    }

    public Long getCountryCode() {
        return countryCode;
    }

    public LmTemplate countryCode(Long countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(Long countryCode) {
        this.countryCode = countryCode;
    }

    public Long getLmStandardCode() {
        return lmStandardCode;
    }

    public LmTemplate lmStandardCode(Long lmStandardCode) {
        this.lmStandardCode = lmStandardCode;
        return this;
    }

    public void setLmStandardCode(Long lmStandardCode) {
        this.lmStandardCode = lmStandardCode;
    }

    public String getPath() {
        return path;
    }

    public LmTemplate path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Instant getInsertTs() {
        return insertTs;
    }

    public LmTemplate insertTs(Instant insertTs) {
        this.insertTs = insertTs;
        return this;
    }

    public void setInsertTs(Instant insertTs) {
        this.insertTs = insertTs;
    }

    public Instant getLastUpdateTs() {
        return lastUpdateTs;
    }

    public LmTemplate lastUpdateTs(Instant lastUpdateTs) {
        this.lastUpdateTs = lastUpdateTs;
        return this;
    }

    public void setLastUpdateTs(Instant lastUpdateTs) {
        this.lastUpdateTs = lastUpdateTs;
    }

    public Set<Model> getModels() {
        return models;
    }

    public LmTemplate models(Set<Model> models) {
        this.models = models;
        return this;
    }

    public LmTemplate addModel(Model model) {
        this.models.add(model);
        model.setLmTemplate(this);
        return this;
    }

    public LmTemplate removeModel(Model model) {
        this.models.remove(model);
        model.setLmTemplate(null);
        return this;
    }

    public void setModels(Set<Model> models) {
        this.models = models;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LmTemplate)) {
            return false;
        }
        return id != null && id.equals(((LmTemplate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LmTemplate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", langCode=" + getLangCode() +
            ", countryCode=" + getCountryCode() +
            ", lmStandardCode=" + getLmStandardCode() +
            ", path='" + getPath() + "'" +
            ", insertTs='" + getInsertTs() + "'" +
            ", lastUpdateTs='" + getLastUpdateTs() + "'" +
            "}";
    }
}
