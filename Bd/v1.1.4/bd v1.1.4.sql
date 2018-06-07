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
        u.descripcion,
        u.precio
    from usuarios u
    where u.id = id
    limit 1;
end//



drop procedure eliminar_solicitud_proyecto;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminar_solicitud_proyecto`(in id2 int)
begin
	delete
    from solicitudes_proyecto
    where id2 = id;
end$$
DELIMITER ;



drop procedure if exists get_proyectos;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_proyectos`()
begin
	select 
		p.id,
		p.nombre,
		p.descripcion,
        p.categoria,
		p.precio,
		p.fecha,
		p.inicio,
		p.fin,
		p.contratista_id,
		p.freelancer_id
    from proyectos p
    order by id desc
    ;
end$$
DELIMITER ;


drop procedure if exists get_solicitudes_proyecto;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_solicitudes_proyecto`(in id int)
begin
	select 
		sp.id,
		sp.proyecto_id,
		sp.freelancer_id,
        sp.estado,
        oferta
    from solicitudes_proyecto sp
    where sp.proyecto_id = id;
end$$
DELIMITER ;
