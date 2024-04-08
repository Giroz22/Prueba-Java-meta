DROP DATABASE IF EXISTS 	;
CREATE DATABASE IF NOT EXISTS db_centro_comercial;
USE db_centro_comercial;

CREATE TABLE Tienda(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255),
    ubicacion VARCHAR(255)
);

CREATE TABLE Cliente(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255),
    apellido VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE Producto(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255),
    precio DECIMAL(10,2),
    id_tienda INT,
    CONSTRAINT fk_producto_tienda FOREIGN KEY (id_tienda) 
    REFERENCES Tienda(id) ON DELETE	CASCADE
);

CREATE TABLE Compra(
	id INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente INT,
    id_producto INT,
    fecha_compra TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    cantidad INT,
    CONSTRAINT fk_compra_cliente FOREIGN KEY (id_cliente) 
    REFERENCES Cliente(id) ON DELETE CASCADE,
    CONSTRAINT fk_compra_producto FOREIGN KEY (id_producto) 
    REFERENCES Producto(id) ON DELETE CASCADE
);

ALTER TABLE Producto
ADD stock INT;

INSERT INTO Tienda (nombre, ubicacion) VALUES
('Branchos',  'Local 101'),
('Mineso',  'Local 103'),
('DollarCity',  'Local 205'),
('Agua bendita',  'Local 104');

SELECT *  FROM Tienda;