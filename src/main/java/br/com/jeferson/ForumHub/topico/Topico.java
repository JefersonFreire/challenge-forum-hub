package br.com.jeferson.ForumHub.topico;

import br.com.jeferson.ForumHub.domain.curso.Curso;
import br.com.jeferson.ForumHub.domain.curso.DadosDetalhesCurso;
import br.com.jeferson.ForumHub.domain.resposta.Resposta;
import br.com.jeferson.ForumHub.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Topico")
@Table(name = "topicos")
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensagem;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico")
    private List<Resposta> respostas = new ArrayList<>();

    public Topico(DadosCadastroTopico dados) {
        this.titulo = dados.titulo().trim();
        this.mensagem = dados.mensagem().trim();
        this.status = dados.status();
        this.curso = dados.curso();
    }
}


