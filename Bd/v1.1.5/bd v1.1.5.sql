use freelancer;

delimiter //
drop procedure if exists get_user_by_username//
create procedure get_user_by_username(in username text)
begin
	select 
		u.id, 
        u.nombre_completo, 
        u.usuario, 
        u.contraseña, 
        u.correo, 
        u.tipo,
        u.precio,
        u.descripcion
    from usuarios u
    where u.usuario = username;
end//

-- MODIF MK SOLIC

-- CREAR 
drop procedure if exists ver_solicitudes_5//
create procedure ver_solicitudes_5(in proyecto_id int)
begin
	select 
		sp.id as id_solicitud,
        u.id as id_freelancer,
        u.nombre_completo,
        sp.oferta
    from solicitudes_proyecto sp
    inner join usuarios u
    on (sp.freelancer_id = u.id)
    where sp.proyecto_id = proyecto_id;
end//

drop procedure if exists aceptar5//
create procedure aceptar5(in id_solicitud int)
begin
	update solicitudes_proyecto sp
    set sp.estado = "pendiente"
    where sp.id = id_solicitud;
end//
