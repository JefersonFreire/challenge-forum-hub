package br.com.jeferson.ForumHub.domain.curso;

public record DadosDetalhesCurso(
        String nome,
        Categoria categoria
) {
    public DadosDetalhesCurso(Curso curso) {
        this(curso.getNome(), curso.getCategoria());
    }
}
