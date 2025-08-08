package br.com.jeferson.ForumHub.domain.curso;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Curso")
@Table(name = "cursos")
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Curso(DadosDetalhesCurso dadosCurso){
        this.nome = dadosCurso.nome();
        this.categoria = dadosCurso.categoria();
    }

}
