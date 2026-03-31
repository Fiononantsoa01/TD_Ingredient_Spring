CREATE TYPE dish_type_enum AS ENUM ('START', 'MAIN', 'DESSERT');
CREATE TABLE Dish (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      dish_type dish_type_enum NOT NULL
);
CREATE TYPE ingredient_category_enum AS ENUM ('VEGETABLE', 'ANIMAL', 'MARINE', 'DAIRY', 'OTHER');
CREATE TABLE Ingredient (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            price NUMERIC(10,2) NOT NULL,
                            category ingredient_category_enum NOT NULL,
                            id_dish INT NOT NULL REFERENCES Dish(id) ON DELETE CASCADE
);
INSERT INTO Dish (id, name, dish_type) VALUES
                                           (1, 'Salade fraîche', 'START'),
                                           (2, 'Poulet grillé', 'MAIN'),
                                           (3, 'Riz aux légumes', 'MAIN'),
                                           (4, 'Gâteau au chocolat', 'DESSERT'),
                                           (5, 'Salade de fruits', 'DESSERT');
INSERT INTO Ingredient (id, name, price, category, id_dish) VALUES
                                                                (1, 'Laitue', 800.00, 'VEGETABLE', 1),
                                                                (2, 'Tomate', 600.00, 'VEGETABLE', 1),
                                                                (3, 'Poulet', 4500.00, 'ANIMAL', 2),
                                                                (4, 'Chocolat', 3000.00, 'OTHER', 4),
                                                                (5, 'Beurre', 2500.00, 'DAIRY', 4);
CREATE TABLE DishIngredient (
                                id SERIAL PRIMARY KEY,
                                id_dish INT NOT NULL REFERENCES Dish(id) ON DELETE CASCADE,
                                id_ingredient INT NOT NULL REFERENCES Ingredient(id) ON DELETE CASCADE,
                                quantity_required NUMERIC(10,2) DEFAULT 1,
                                unit VARCHAR(50) DEFAULT 'unit',
                                UNIQUE (id_dish, id_ingredient)
);