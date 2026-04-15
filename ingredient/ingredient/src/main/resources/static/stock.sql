CREATE TYPE mouvement_type AS ENUM ('IN', 'OUT');
CREATE TABLE stock_movement (
                                id SERIAL PRIMARY KEY,
                                id_ingredient INT NOT NULL,
                                quantity NUMERIC NOT NULL,
                                type mouvement_type NOT NULL,
                                unit unit_type NOT NULL,
                                creation_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                CONSTRAINT fk_ingredient
                                    FOREIGN KEY (id_ingredient)
                                        REFERENCES ingredient(id)
                                        ON DELETE CASCADE
);INSERT INTO stock_movement (id_ingredient, quantity, type, unit, creation_datetime) VALUES
                                                                                          (1, 5.0, 'IN', 'KG', '2024-01-05 08:00'),
                                                                                          (1, 0.2, 'OUT', 'KG', '2024-01-06 12:00'),
                                                                                          (2, 4.0, 'IN', 'KG', '2024-01-05 08:00'),
                                                                                          (2, 0.15, 'OUT', 'KG', '2024-01-06 12:00'),
                                                                                          (3, 10.0, 'IN', 'KG', '2024-01-04 09:00'),
                                                                                          (3, 1.0, 'OUT', 'KG', '2024-01-06 13:00'),
                                                                                          (4, 3.0, 'IN', 'KG', '2024-01-05 10:00'),
                                                                                          (4, 0.3, 'OUT', 'KG', '2024-01-06 14:00'),
                                                                                          (5, 2.5, 'IN', 'KG', '2024-01-05 10:00'),
                                                                                          (5, 0.2, 'OUT', 'KG', '2024-01-06 14:00');