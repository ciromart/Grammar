package it.alma.geditor.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;

@Data
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
}