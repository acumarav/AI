--initialize vector type by adding this extension:
--create extension vector;

create table public.document(
  id serial primary key,
  file_path varchar(512),
  content text,
  embeded vector(1536)
);


