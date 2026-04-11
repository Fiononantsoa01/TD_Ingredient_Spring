alter table dish add column price numeric;
alter table ingredient drop column id_dish;
create type unit_type as enum('PCS','KG','L');
create table DishIngredient(
                         id int primary key,
 id_dish int references dish(id),
id_ingredient int references ingredient (id),
 quantity_required numeric,
 unit unit_type not null);

insert into DishIngredient(id_dish,id_ingredient,quantity_required,unit)
values (1,1,0.20,'KG'),
       (1,2,0.15,'KG'),
       (2,3,1.00,'KG'),
       (4,4,0.30,'KG'),
       (4,5,0.20,'KG');
-- Riz nature / Riz simple / Riz blanc
UPDATE dish
SET price = 12000
WHERE name ILIKE '%riz nature%'
   OR name ILIKE '%riz simple%'
   OR name ILIKE '%riz blanc%'
   OR name ILIKE '%riznature%';

-- Riz aux légumes
UPDATE dish SET price = 15000 WHERE name ILIKE '%riz aux légumes%';

-- Poulet grillé / Riz poulet
UPDATE dish SET price = 20000
WHERE name ILIKE '%poulet grill%'
   OR name ILIKE '%riz poulet%';

-- Pizza (toutes)
UPDATE dish SET price = 18000 WHERE name ILIKE '%pizza%';

-- Pâtes au poulet / fromage
UPDATE dish SET price = 22000
WHERE name ILIKE '%pôtes au poulet%'
   OR name ILIKE '%pates au poulet%';

-- Soupe (starter)
UPDATE dish SET price = 8000 WHERE name ILIKE '%soupe%';

-- Gâteau au chocolat
UPDATE dish SET price = 12000 WHERE name ILIKE '%gâteau au chocolat%';

-- Salade de fruits
UPDATE dish SET price = 10000 WHERE name ILIKE '%salade de fruits%';

-- Les "RI" et "RIz" (variantes courtes de riz)
UPDATE dish SET price = 13000 WHERE name ILIKE 'ri%';