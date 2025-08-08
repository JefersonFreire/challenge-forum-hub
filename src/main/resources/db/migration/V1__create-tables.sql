create table perfis(
    id bigint not null auto_increment,
    nome varchar(100) not null,

    primary key(id)
);

create table usuarios(
    id bigint not null auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    senha varchar(255) not null,
    perfil_id bigint,

    primary key(id),
    constraint fk_usuario_perfil foreign key(perfil_id) references perfis(id)
);

create table cursos(
    id bigint not null auto_increment,
    nome varchar(100) not null,
    categoria varchar(100) not null,

    primary key(id)
);

create table topicos(
    id bigint not null auto_increment,
    titulo varchar(255) not null,
    mensagem text not null,
    data_criacao datetime not null,
    status varchar(100) not null,
    autor_id bigint,
    curso_id bigint,

    primary key(id),
    constraint fk_topico_autor foreign key(autor_id) references usuarios(id),
    constraint fk_topico_curso foreign key(curso_id) references cursos(id)
);

create table respostas(
    id bigint not null auto_increment,
    mensagem text not null,
    data_criacao datetime not null,
    topico_id bigint,
    autor_id bigint,
    solucao boolean default false,

    primary key(id),
    constraint fk_resposta_topico foreign key(topico_id) references topicos(id),
    constraint fk_resposta_autor foreign key(autor_id) references usuarios(id)
);

