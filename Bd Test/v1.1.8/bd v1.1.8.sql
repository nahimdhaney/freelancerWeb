use freelancerTest;

delete from version;

insert into version(
		version_mayor,
        version_menor,
        parche
	) values(
		1,
        1,
        8
	);

delimiter //

drop procedure if exists eliminar_proyecto//
create procedure eliminar_proyecto(in id int)
begin
	delete p
    from proyectos p
    where p.id = id;
end//

drop procedure if exists buscar_proyectos//
create procedure buscar_proyectos(in valor text)
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
    where lower(p.nombre) like lower(concat(valor, "%"))
    or lower(p.categoria) like lower(concat(valor, "%"))
    or lower(p.descripcion) like lower(concat(valor, "%"));
end//
