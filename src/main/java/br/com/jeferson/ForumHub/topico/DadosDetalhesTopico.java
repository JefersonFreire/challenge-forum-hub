package br.com.jeferson.ForumHub.topico;

import br.com.jeferson.ForumHub.domain.curso.Categoria;
import br.com.jeferson.ForumHub.domain.curso.Curso;
import br.com.jeferson.ForumHub.domain.resposta.Resposta;
import br.com.jeferson.ForumHub.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhesTopico(

        Long id,
        String titulo,
        String mensagem,
        Status status,
        String autor,
        String curso,
        Categoria categoria,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
        LocalDateTime dataCriacao

) {
    public DadosDetalhesTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(),
               topico.getStatus(), topico.getAutor().getNome(), topico.getCurso().getNome(),
                topico.getCurso().getCategoria(),
                topico.getDataCriacao());
    }
}
