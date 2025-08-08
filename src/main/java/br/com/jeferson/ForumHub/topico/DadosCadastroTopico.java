package br.com.jeferson.ForumHub.topico;

import br.com.jeferson.ForumHub.domain.curso.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroTopico(

        @NotBlank
        String titulo,

        @NotBlank
        String mensagem,

        Status status,

        Curso curso
) {
}
