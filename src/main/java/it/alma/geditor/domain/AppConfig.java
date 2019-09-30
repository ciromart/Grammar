package it.alma.geditor.domain;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;

@Data
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

}