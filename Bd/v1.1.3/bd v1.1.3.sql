use freelancer;


delete from version;

insert into version(
		version_mayor,
        version_menor,
        parche
	) values(
		1,
        1,
        3
	);

drop procedure if exists get_freelancers
CREATE PROCEDURE get_freelancers()
begin
	select 
		u.id, 
        u.nombre_completo, 
        u.usuario, 
        u.contraseña, 
        u.correo, 
        u.tipo,
        u.descripcion
    from usuarios u
    where u.tipo = 2
    ;
end

drop procedure if exists get_contratistas
CREATE PROCEDURE get_contratistas()
begin
	select 
		u.id, 
        u.nombre_completo, 
        u.usuario, 
        u.contraseña, 
        u.correo, 
        u.tipo,
        u.descripcion
    from usuarios u
    where u.tipo = 2
    ;
end
	
	
	
	
alter table usuarios add column descripcion text;

delimiter //
drop procedure if exists mk_usuario//
create procedure mk_usuario(
	in id int, 
    in nombre_completo text,
    in usuario text,
    in contraseña text,
    in correo text,
    in tipo int,
    in descripcion text
)
begin
	if (id = 0) then
		insert into usuarios (
				nombre_completo, 
                usuario, 
                contraseña, 
                correo, 
                tipo,
                descripcion
			) values (
				nombre_completo, 
                usuario, 
                sha(contraseña), 
                correo, 
                tipo,
                descripcion
			);
    else
		update usuarios u
        set
			u.nombre_completo = nombre_completo,
            u.usuario = usuario,
            u.contraseña = sha(contraseña),
            u.correo = correo,
            u.tipo = tipo,
            u.descripcion = descripcion
        where u.id = id;
	end if;
end//

drop procedure if exists get_user_by_username//
create procedure get_user_by_username(in usuario text)
begin
	select 
		id, 
        nombre_completo, 
        usuario, 
        contraseña, 
        correo, 
        tipo,
        descripcion
    from usuarios u
    where u.usuario = usuario
    limit 1;
end//

drop procedure if exists get_user//
create procedure get_user(in id int)
begin
	select 
		u.id, 
        u.nombre_completo, 
        u.usuario, 
        u.contraseña, 
        u.correo, 
        u.tipo,
        u.descripcion
    from usuarios u
    where u.id = id
    limit 1;
end//

drop procedure if exists get_users//
create procedure get_users()
begin
	select 
		u.id, 
        u.nombre_completo, 
        u.usuario, 
        u.contraseña, 
        u.correo, 
        u.tipo,
        u.descripcion
    from usuarios u;
end//
