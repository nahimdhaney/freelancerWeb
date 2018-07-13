-- drop database if exists freelancerTest;
create database if not exists freelancerTest;
use freelancerTest;

create table if not exists usuarios(
	id int auto_increment primary key,
    nombre_completo text not null,
    usuario varchar(20) not null,
    contraseña text not null,
    correo varchar(50) not null,
    tipo int not null, 
    constraint uk_usuarios_usario unique (usuario),
    constraint uk_usuarios_correo unique (correo)
);

create table if not exists codigos_recuperacion(
	id int primary key auto_increment,
    codigo text not null,
    fecha_expiracion datetime not null,
    utilizado boolean not null,
    usuario_id int not null
);

delimiter //
drop procedure if exists mk_usuario//
create procedure mk_usuario(
	in id int, 
    in nombre_completo text,
    in usuario text,
    in contraseña text,
    in correo text,
    in tipo int
)
begin
	if (id = 0) then
		insert into usuarios (
				nombre_completo, 
                usuario, 
                contraseña, 
                correo, 
                tipo
			) values (
				nombre_completo, 
                usuario, 
                sha(contraseña), 
                correo, 
                tipo
			);
    else
		update usuarios u
        set
			u.nombre_completo = nombre_completo,
            u.usuario = usuario,
            u.contraseña = sha(contraseña),
            u.correo = correo,
            u.tipo = tipo
        where u.id = id;
	end if;
end//

delimiter //
drop procedure if exists login_usuario//
create procedure login_usuario(in usuario text, in contraseña text)
begin
	select * 
    from usuarios u
    where u.usuario = usuario
    and u.contraseña = sha(contraseña)
    limit 1;
end//

delimiter //
drop procedure if exists get_user_by_username//
create procedure get_user_by_username(in usuario text)
begin
	select 
		id, 
        nombre_completo, 
        usuario, 
        contraseña, 
        correo, 
        tipo
    from usuarios u
    where u.usuario = usuario
    limit 1;
end//

delimiter //
drop procedure if exists mk_codigo_recuperacion//
create procedure mk_codigo_recuperacion(
	in id int, 
    in codigo text,
    in fecha_expiracion text,
    in utilizado boolean,
    in usuario_id int
)
begin
	if (id = 0) then
		insert into codigos_recuperacion (
				codigo,
                fecha_expiracion,
                utilizado,
                usuario_id
			) values (
				codigo,
                fecha_expiracion,
                utilizado,
                usuario_id
            );
    else 
		update codigos_recuperacion c
        set
			c.codigo = codigo,
            c.fecha_expiracion = fecha_expiracion,
            c.utilizado = utilizado,
            c.usuario_id = usuario_id
		where c.id = id;
    end if;
end//

delimiter //
drop procedure if exists get_codigo_recuperacion_by_id//
create procedure get_codigo_recuperacion_by_id(in id int)
begin
	select c.id, c.codigo, c.fecha_expiracion, c.utilizado, c.usuario_id
    from codigos_recuperacion c
    where c.id = id
    limit 1;
end//

delimiter //
drop procedure if exists verificar_codigo_recuperacion//
create procedure verificar_codigo_recuperacion(in id_usuario int, in codigo text)
begin
	select *
	from codigos_recuperacion c
	where c.usuario_id = id_usuario
    and c.codigo = codigo
	and c.utilizado is false
	and now() < fecha_expiracion;
end//
