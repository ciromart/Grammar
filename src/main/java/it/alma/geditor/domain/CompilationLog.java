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
}