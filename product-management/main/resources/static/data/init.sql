-- Création de la base de données
CREATE DATABASE product_management_db;

-- Création de l'utilisateur
CREATE USER ingredient_manager_user WITH PASSWORD '123456';

-- Donner accès à la base
GRANT ALL PRIVILEGES ON DATABASE product_management_db TO ingredient_manager_user;
-- Se connecter à la base
\c product_management_db;

-- Donner les droits sur le schéma public
GRANT ALL ON SCHEMA public TO ingredient_manager_user;

-- Autoriser création de tables
ALTER SCHEMA public OWNER TO ingredient_manager_user;

-- Donner tous les droits sur les futures tables
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON TABLES TO ingredient_manager_user;

-- Donner tous les droits sur les séquences (important pour SERIAL)
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON SEQUENCES TO ingredient_manager_user;