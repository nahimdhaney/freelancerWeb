use freelancerTest;

delimiter //
drop procedure if exists get_proyectos_de_freelancer5//
create procedure get_proyectos_de_freelancer5(in freelancer_id int)
begin
	 select 
		sp.id as id_solicitud,
        sp.proyecto_id as id_proyecto,
        sp.estado,
        p.nombre,
        p.descripcion
     from solicitudes_proyecto sp 
     inner join proyectos p 
     on (sp.proyecto_id = p.id) 
     where sp.freelancer_id = freelancer_id;
end//

drop procedure if exists confirmar5//
create procedure confirmar5(in id_solicitud int)
begin
	update solicitudes_proyecto sp
    set sp.estado = "aceptado"
    where sp.id = id_solicitud;
    
    update proyectos p
    set freelancer_id = 
		(select freelancer_id
        from solicitudes_proyecto sp
        where sp.id = id_solicitud)
    where p.id = 
		(select proyecto_id
        from solicitudes_proyecto sp
        where sp.id = id_solicitud);
end//
