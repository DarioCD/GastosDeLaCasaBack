/* Crear casa */
INSERT INTO gastoscasa.casa (id_casa, nombre, codigo, disponible) VALUES (1, "Casa", "123456", 1221);

/* Crear usuarios */
INSERT INTO gastoscasa.usuario (id_usuario, casa_id, nombre, apellidos, email, password, sueldo) VALUES (1, 1, "Dario", "Chinea Delgado","dariocd0808@gmail.com","1234", 1221);
INSERT INTO gastoscasa.usuario (id_usuario, casa_id, nombre, apellidos, email, password, sueldo) VALUES (2, 1, "Eridania", "Diaz Garcia","erinadinadiazgarcia33@gmail.com","1234", 1400);

/* Crear estadística */
INSERT INTO gastoscasa.estadistica (fecha, id_casa, id_estadistica) VALUES ("2023-10-08", 1, 1);

/* Crear categorias */
INSERT INTO gastoscasa.categoria (id_categoria, nombre) VALUES (1, "Fijo");
INSERT INTO gastoscasa.categoria (id_categoria, nombre) VALUES (2, 'Variable');
INSERT INTO gastoscasa.categoria (id_categoria, nombre) VALUES (3, 'Puntual');
INSERT INTO gastoscasa.categoria (id_categoria, nombre) VALUES (4, 'Salud');
INSERT INTO gastoscasa.categoria (id_categoria, nombre) VALUES (5, 'Transporte');
INSERT INTO gastoscasa.categoria (id_categoria, nombre) VALUES (6, 'Casa');
INSERT INTO gastoscasa.categoria (id_categoria, nombre) VALUES (7, 'Comida');
INSERT INTO gastoscasa.categoria (id_categoria, nombre) VALUES (8, 'Ocio');
INSERT INTO gastoscasa.categoria (id_categoria, nombre) VALUES (9, 'Ropa');

/* Crear gastos */
INSERT INTO gastoscasa.gasto (fecha, precio, id_gasto, nombre) VALUES (CURRENT_DATE, 600, 1, "Alquiler");
INSERT INTO gastoscasa.gasto (fecha, precio, id_gasto, nombre) VALUES (CURRENT_DATE, 40, 2, "Gym");
INSERT INTO gastoscasa.gasto (fecha, precio, id_gasto, nombre) VALUES (CURRENT_DATE, 23.5, 3, "Comida en el Burger King");

/* Añadir gasto a categoria" */
INSERT INTO gastoscasa.gasto_categoria (id_categoria, id_gasto) VALUES (1,1);
INSERT INTO gastoscasa.gasto_categoria (id_categoria, id_gasto) VALUES (6,1);
INSERT INTO gastoscasa.gasto_categoria (id_categoria, id_gasto) VALUES (1,2);
INSERT INTO gastoscasa.gasto_categoria (id_categoria, id_gasto) VALUES (4,2);
INSERT INTO gastoscasa.gasto_categoria (id_categoria, id_gasto) VALUES (3,3);
INSERT INTO gastoscasa.gasto_categoria (id_categoria, id_gasto) VALUES (7,3);

/* Añadir gasto a estadística */
INSERT INTO gastoscasa.gasto_estadistica (id_estadistica, id_gasto) VALUES (1,1);
INSERT INTO gastoscasa.gasto_estadistica (id_estadistica, id_gasto) VALUES (1,2);
INSERT INTO gastoscasa.gasto_estadistica (id_estadistica, id_gasto) VALUES (1,3);



