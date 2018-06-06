use freelancer;


delete from version;

insert into version(
		version_mayor,
        version_menor,
        parche
	) values(
		1,
        1,
        4
	);
	
alter table solicitudes_proyecto add column oferta double;
alter table usuarios add column precio double;

	
	drop procedure if exists get_user_by_username;
delimiter //
create procedure get_user_by_username(in usuario text)
begin
	select 
		id, 
        nombre_completo, 
        usuario, 
        contraseña, 
        correo, 
        tipo,
		precio
    from usuarios u
    where u.usuario = usuario
    limit 1;
end
//


drop procedure if exists get_freelancers;
delimiter //
CREATE PROCEDURE get_freelancers()
begin
	select 
		u.id, 
        u.nombre_completo, 
        u.usuario, 
        u.contraseña, 
        u.correo, 
        u.tipo,
        u.descripcion,
		u.precio
    from usuarios u
    where u.tipo = 2
    ;
end
//
