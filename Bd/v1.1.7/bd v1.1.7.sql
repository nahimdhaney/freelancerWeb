use freelancer;

delete from version;

insert into version(
		version_mayor,
        version_menor,
        parche
	) values(
		1,
        1,
        7
	);

create table if not exists comentarios(
	id int primary key auto_increment,
    mensaje text not null,
    fecha datetime not null,
    usuario_id int not null,
    solicitud_proyecto_id int not null
);

alter table proyectos drop column categorias;

delimiter //
drop procedure if exists mk_comentario//
create procedure mk_comentario (
	in mensaje text,
	in fecha datetime,
	in usuario_id int,
	in solicitud_proyecto_id int
)
begin
	insert into comentarios (
        mensaje,
        fecha,
        usuario_id,
        solicitud_proyecto_id
	) values (
		mensaje,
        fecha,
        usuario_id,
        solicitud_proyecto_id
    );
end//

drop procedure if exists get_comentarios_de_solicitud//
create procedure get_comentarios_de_solicitud(in id int)
begin
	select 
		c.id,
		c.mensaje,
		c.fecha,
		c.usuario_id,
		c.solicitud_proyecto_id
	from comentarios c
	where c.solicitud_proyecto_id = id
	order by c.fecha asc;
end//

-- crear el sp entre proyecto y freelancer, devueleve sp
drop procedure if exists get_chats_desde_freelancer//
create procedure get_chats_desde_freelancer(
	in freelancer_id int, 
    in proyecto_id int
)
begin
	select 
		sp.id,
		sp.proyecto_id,
		sp.freelancer_id,
		sp.estado,
		sp.oferta
	from solicitudes_proyecto sp
	where sp.proyecto_id = proyecto_id
	and	freelancer_id = freelancer_id;
end//

drop procedure if exists mk_comentario2//
create procedure mk_comentario2 (
	in mensaje text,
	in usuario_id int,
	in solicitud_proyecto_id int
)
begin
	insert into comentarios (
        mensaje,
        fecha,
        usuario_id,
        solicitud_proyecto_id
	) values (
		mensaje,
        now(),
        usuario_id,
        solicitud_proyecto_id
    );
end//
