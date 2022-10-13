create table posts (
    id bigserial primary key,
    user_id bigint not null references users(id) on delete cascade,
    text varchar(256) not null
);
