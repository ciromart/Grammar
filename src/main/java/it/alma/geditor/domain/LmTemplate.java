package it.alma.geditor.domain;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
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

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "lmTemplate")
    private Set<Model> models = new HashSet<>();

}
