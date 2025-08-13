package br.com.jeferson.ForumHub.topico;

import br.com.jeferson.ForumHub.domain.curso.Curso;
import br.com.jeferson.ForumHub.domain.curso.CursoRepository;
import br.com.jeferson.ForumHub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public Topico cadastrar(DadosCadastroTopico dados) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario autor = (Usuario) authentication.getPrincipal();

        boolean topicoExiste = topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem());

        if (topicoExiste) {
            throw new RuntimeException("Tópico já existente com mesmo título e mensagem");
        }

        var curso = cursoRepository.findByNome(dados.curso().getNome())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado!"));

        var topico = new Topico();
        topico.setTitulo(dados.titulo());
        topico.setMensagem(dados.mensagem());
        topico.setStatus(Status.NAO_RESOLVIDO);
        topico.setCurso(curso);
        topico.setAutor(autor);
        return topicoRepository.save(topico);
    }

    @Transactional
    public Topico atualizar(Long id, DadosAtualizacaoTopico dados) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();

        Optional<Topico> topicoOptional = Optional.ofNullable(topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado.")));

        Topico topico = topicoOptional.get();

        if(!topico.getAutor().getId().equals(usuarioAutenticado.getId())){
            throw new RuntimeException("Usuário não possui permissão para atualizar este tópico.");
        }

        if (dados.curso() != null) {
            Optional<Curso> cursoOptional = cursoRepository.findByNome(dados.curso());
            Curso cursoExistente = cursoOptional.orElseThrow(() -> new RuntimeException("Curso não encontrado"));
            topico.setCurso(cursoExistente);
        }
        if (dados.titulo() != null) {
            topico.setTitulo(dados.titulo());
        }
        if (dados.mensagem() != null) {
            topico.setMensagem(dados.mensagem());
        }
        if (dados.status() != null) {
            topico.setStatus(dados.status());
        }
        return topico;
    }
}

