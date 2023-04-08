ALTER TABLE users DROP COLUMN step_code RESTRICT;
ALTER TABLE users DROP COLUMN text RESTRICT;
ALTER TABLE users DROP COLUMN accept RESTRICT;
ALTER TABLE users DROP COLUMN register_at RESTRICT;

ALTER TABLE users
  ADD COLUMN user_name varchar(100),
  ADD COLUMN first_name varchar(100),
  ADD COLUMN last_name varchar(100),
  ADD COLUMN register_at timestamp with time zone,
  ADD COLUMN step_code varchar(100),
  ADD COLUMN text varchar(100),
  ADD COLUMN accept varchar(100);

comment on column users.user_name is 'User name пользователя';
comment on column users.first_name is 'Имя пользователя';
comment on column users.last_name is 'Фамилия пользователя';
comment on column users.register_at is 'Дата/время регистрации пользователя';
comment on column users.step_code is 'Код этапа';
comment on column users.text is 'Произвольный текст';
comment on column users.accept is 'Данные из кнопок';
