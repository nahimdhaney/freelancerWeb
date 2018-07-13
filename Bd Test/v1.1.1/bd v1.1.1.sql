use freelancerTest;

delete from version;

insert into version(
		version_mayor,
        version_menor,
        parche
	) values(
		1,
        1,
        1
	);
    
alter table proyectos add column categoria text;

delimiter //
drop procedure if exists mk_proyecto//
create procedure mk_proyecto(
	in id int,
    in nombre text,
    in descripcion text,
    in categoria text,
    in precio double,
    in fecha datetime,
    in inicio datetime,
    in fin datetime,
    in contratista_id int,
    in freelancer_id int
)
begin
	if (id = 0) then
		insert into proyectos (
				nombre,
				descripcion,
                categoria,
				precio,
				fecha,
				inicio,
				fin,
				contratista_id,
				freelancer_id
			) values (
				nombre,
				descripcion,
                categoria,
				precio,
				fecha,
				inicio,
				fin,
				contratista_id,
				null
			);
    else
		update proyectos p
        set
			p.nombre = nombre,
			p.descripcion = descripcion,
            p.categoria = categoria,
			p.precio = precio,
			p.fecha = fecha,
			p.inicio = inicio,
			p.fin = fin,
			p.contratista_id = contratista_id,
			p.freelancer_id = freelancer_id
        where p.id = id;
	end if;
end//

drop procedure if exists get_proyecto//
create procedure get_proyecto(in id int)
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
    where p.id = id;
end//

drop procedure if exists get_proyectos//
create procedure get_proyectos()
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
    from proyectos p;
end//

drop procedure if exists get_proyectos_de_contratista//
create procedure get_proyectos_de_contratista(in id_contratista int)
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
    where p.contratista_id = id_contratista;
end//

drop procedure if exists get_proyectos_de_freelancer//
create procedure get_proyectos_de_freelancer(in id_freelancer int)
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
    where p.freelancer_id = id_freelancer;
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
    or lower(p.categoria) like lower(concat(valor, "%"));
end//
