use freelancerTest;

create table if not exists version (
	version_mayor int not null,
    version_menor int not null,
    parche int not null
);

delimiter //
drop procedure if exists get_version//
create procedure get_version()
begin
	select 
		version_mayor,
        version_menor,
        parche
	from version
	limit 1;
end//

delimiter ;
delete from version;

insert into version(
		version_mayor,
        version_menor,
        parche
	) values(
		1,
        1,
        0
	);
    
create table if not exists proyectos(
	id int auto_increment primary key,
    nombre text not null,
    descripcion text not null,
    precio double not null,
    fecha datetime not null,
    inicio datetime,
    fin datetime,
    contratista_id int not null,
    freelancer_id int,
    foreign key (contratista_id)
    references usuarios(id)
    on delete cascade,
    foreign key (freelancer_id)
    references usuarios(id)
    on delete set null
);

create table if not exists solicitudes_proyecto(
	id int auto_increment primary key,
    proyecto_id int not null,
    freelancer_id int not null,
    estado text,
    foreign key (proyecto_id)
    references proyectos(id)
    on delete cascade,
    foreign key (freelancer_id)
    references usuarios(id)
    on delete cascade
);

-- PROCEDIMIENTOS PARA LOS PROYECTOS
delimiter //
drop procedure if exists mk_proyecto//
create procedure mk_proyecto(
	in id int,
    in nombre text,
    in descripcion text,
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
				precio,
				fecha,
				inicio,
				fin,
				contratista_id,
				freelancer_id
			) values (
				nombre,
				descripcion,
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
			p.precio = precio,
			p.fecha = fecha,
			p.inicio = inicio,
			p.fin = fin,
			p.contratista_id = contratista_id,
			p.freelancer_id = freelancer_id
        where p.id = id;
	end if;
end//

drop procedure if exists eliminar_proyecto//
create procedure eliminar_proyecto(in id int)
begin
	delete
    from proyectos
    where id = id;
end//

drop procedure if exists get_proyecto//
create procedure get_proyecto(in id int)
begin
	select 
		p.id,
		p.nombre,
		p.descripcion,
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
		p.precio,
		p.fecha,
		p.inicio,
		p.fin,
		p.contratista_id,
        p.freelancer_id
    from proyectos p
    where lower(p.nombre) like lower(concat(valor, "%"));
end//

-- PROCEDIMIENTOS PARA LAS SOLICITUDES DE PROYECTOS

drop procedure if exists mk_solicitud_proyecto//
create procedure mk_solicitud_proyecto(
	in id int,
    in proyecto_id int,
    in freelancer_id int,
    in estado text
)
begin
	if (id = 0) then
		insert into solicitudes_proyecto (
				proyecto_id,
				freelancer_id,
                estado
			) values (
				proyecto_id,
				freelancer_id,
                estado
			);
    else
		update solicitudes_proyecto sp
        set
			sp.proyecto_id = proyecto_id,
			sp.freelancer_id = freelancer_id,
            sp.estado = estado
        where sp.id = id;
	end if;
end//

drop procedure if exists eliminar_solicitud_proyecto//
create procedure eliminar_solicitud_proyecto(in id int)
begin
	delete
    from solicitudes_proyecto
    where id = id;
end//

drop procedure if exists get_solicitud_proyecto//
create procedure get_solicitud_proyecto(in id int)
begin
	select 
		sp.id,
		sp.proyecto_id,
		sp.freelancer_id,
        sp.estado
    from solicitudes_proyecto sp
    where sp.id = id;
end//

drop procedure if exists get_solicitudes_proyectos//
create procedure get_solicitudes_proyectos()
begin
	select 
		sp.id,
		sp.proyecto_id,
		sp.freelancer_id,
        sp.estado
    from solicitudes_proyecto sp;
end//

-- PROBAR

drop procedure if exists get_solicitudes_proyectos_de_contratista//
create procedure get_solicitudes_proyectos_de_contratista(in id_contratista int)
begin
	select 
		p.id,
		p.nombre,
		p.descripcion,
		p.precio,
		p.fecha,
		p.inicio,
		p.fin,
		p.contratista_id,
        sp.id,
		sp.freelancer_id
    from proyectos p
    inner join usuarios u on (p.contratista_id = p.id_contratista)
    inner join solicitudes_proyecto sp on (p.id = sp.proyecto_id);
end//
