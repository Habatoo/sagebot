create table if not exists users(
    id            int8 primary key not null,
    step_code     varchar(100),
    text          varchar(100),
    accept        varchar(3),
    register_at   timestamp with time zone
);

comment on column users.id is 'Идентификатор пользователя';
comment on column users.step_code is 'Код этапа';
comment on column users.text is 'Произвольный текст';
comment on column users.accept is 'Данные из кнопок';
comment on column users.register_at is 'Дата/время регистрации пользователя';
