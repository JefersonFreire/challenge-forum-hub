package br.com.jeferson.ForumHub.controller;

import br.com.jeferson.ForumHub.domain.usuario.DadosCadastroUsuario;
import br.com.jeferson.ForumHub.domain.usuario.Perfil;
import br.com.jeferson.ForumHub.domain.usuario.Usuario;
import br.com.jeferson.ForumHub.domain.usuario.UsuarioRepository;
import br.com.jeferson.ForumHub.domain.usuario.DadosDetalhesUsuario;
import br.com.jeferson.ForumHub.domain.usuario.PerfilRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder) {

        var perfil = new Perfil();
        perfil.setNome(dados.nome());
        perfilRepository.save(perfil);

        var usuario = new Usuario(dados);
        usuario.setNome(dados.nome());
        usuario.setEmail(dados.email());
        usuario.setSenha(dados.senha());
        usuario.setPerfil(perfil);

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        repository.save(usuario);

        var uri = uriBuilder.path("/cadastrar/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhesUsuario(usuario));

    }
}
