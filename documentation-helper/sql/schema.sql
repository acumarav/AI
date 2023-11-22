-- podman run  -d  -p 0.0.0.0:5434:5432 -e POSTGRES_PASSWORD=password  --name pgvector ankane/pgvector
--psql -p 5434 -d vector -U admin -W
--initialize vector type by adding this extension:
--CREATE EXTENSION IF NOT EXISTS vector;

create table public.document(
  id serial primary key,
  file_path varchar(512) not null,
  content text not null,
  embedded vector(1536),
  created timestamp without time zone default now()
);


