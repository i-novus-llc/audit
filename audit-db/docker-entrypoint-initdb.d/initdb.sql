SELECT 'CREATE DATABASE audit' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'audit')\gexec
\connect audit
CREATE EXTENSION IF NOT EXISTS pg_pathman;