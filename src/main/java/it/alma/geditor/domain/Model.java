package it.alma.geditor.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
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

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "model")
    private Set<CompilationLog> compilationLogs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("models")
    private LmTemplate lmTemplate;
}
