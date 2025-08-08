package br.com.jeferson.ForumHub.topico;

import br.com.jeferson.ForumHub.domain.curso.Curso;
import br.com.jeferson.ForumHub.domain.curso.DadosDetalhesCurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTopico(

        Long id,
        String titulo,
        String mensagem,
        Status status,
        String curso
) {
}
