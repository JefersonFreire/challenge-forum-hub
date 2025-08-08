package br.com.jeferson.ForumHub.domain.usuario;

public record DadosDetalhesUsuario(
        Long id,
        String nome,
        String email,
        Perfil perfis
) {
    public DadosDetalhesUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(),
               usuario.getPerfil());
    }
}
