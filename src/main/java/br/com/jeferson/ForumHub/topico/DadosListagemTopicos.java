package br.com.jeferson.ForumHub.topico;

import br.com.jeferson.ForumHub.domain.curso.Categoria;
import br.com.jeferson.ForumHub.domain.curso.Curso;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DadosListagemTopicos(
        Long id,
        String titulo,
        String mensagem,
        String autor,
        String curso,
        Categoria categoria,
        Status status,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
        LocalDateTime dataCriacao
) {
    public DadosListagemTopicos(Topico dados) {
        this(dados.getId(), dados.getTitulo(), dados.getMensagem(),
                dados.getAutor().getNome(), dados.getCurso().getNome(),
                dados.getCurso().getCategoria(), dados.getStatus(),
                dados.getDataCriacao());
    }
}
