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
    where u.tipo = 1
    ;
end
	
alter table solicitudes_proyecto add column oferta double;

	
	
