create table manufacturers
(
    id      bigserial    not null
        constraint manufacturers_pk
            primary key,
    name    varchar(225) not null,
    country varchar(225) not null,
    deleted boolean default false
);

alter table manufacturers
    owner to postgres;
