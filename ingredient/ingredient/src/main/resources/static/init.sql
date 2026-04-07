CREATE DATABASE mini_dish_db_spring;
       CREATE USER mini_dish_db_spring_manager WITH PASSWORD '123456';
 GRANT ALL PRIVILEGES ON DATABASE mini_dish_db_spring TO mini_dish_db_spring_manager;
-- Donner les droits sur le schéma public
GRANT ALL ON SCHEMA public TO mini_dish_db_spring_manager;

-- Autoriser création de tables
ALTER SCHEMA public OWNER TO mini_dish_db_spring_manager;

-- Donner tous les droits sur les futures tables
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON TABLES TO mini_dish_db_spring_manager;

-- Donner tous les droits sur les séquences (important pour SERIAL)
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON SEQUENCES TO mini_dish_db_spring_manager;
