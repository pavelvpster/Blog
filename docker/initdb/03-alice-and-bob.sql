insert into users(name) values ('alice');

insert into posts(user_id, text) (
    select id, 'Hello, world!'
    from users
    where name = 'alice'
);

insert into users(name) values ('bob');

insert into posts(user_id, text) (
    select id, 'Hello, world!'
    from users
    where name = 'bob'
);

insert into posts(user_id, text) (
    select id, 'Hello, Alice!'
    from users
    where name = 'bob'
);
