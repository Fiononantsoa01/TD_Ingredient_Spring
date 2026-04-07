create table product(
    id int primary key ,
    name varchar,
    price numeric,
    creation_datetime timestamp
);
create table product_category(
    id int primary key ,
    name varchar,
    product_id int references product(id)
)
    insert into product values
    (1,'Laptop Dell XPS',4500.00,'2024-01-15 09:30:00'),
    (2,'iPhone 13',5200.00,'2024-02-02 14:10:00'),
    (3,'Casque Sony WH 1000',890.50,'2024-02-10 16:20:00'),
    (4,'Clavier Logitech',180.00,'2022-10-01 14:10:00'),
    (5,'Ecran Samsung 27',1200.00,'2024-10-20 03:10:00');
insert into product_category values
                                 (1,'Informatique',1),
                                 (2,'Telephonie',2),
                                 (3,'Audio',3),
                                 (4,'Accessoires',4),
                                 (5,'Informatique',5),
                                 (6,'Bureau',5),
                                 (7,'Mobile',2)