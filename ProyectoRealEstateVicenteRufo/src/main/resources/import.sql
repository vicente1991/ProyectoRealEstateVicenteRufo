INSERT INTO USUARIOS (id,nombre,apellidos,direccion,email,telefono,avatar,password,rol) VALUES ('9c9555344e3011ec81d30242ac130003','bla','bla bla','calle bla','blabla@email','123456789','ninguno','$2a$12$j/UNoSkwOacnSjs1.aGqXef98imRu78qW.BmzrEhHRZ70lNkqcFKm','ADMIN')

insert into INMOBILIARIA (id, nombre, email, telefono) values (NEXTVAL('hibernate_sequence'), 'Senger, Satterfield and Krajcik', 'pdangl0@wsj.com', '617-940-1364');


INSERT INTO USUARIOS (id,nombre,apellidos,direccion,email,telefono,avatar,password,rol, inmobiliaria_id) VALUES ('c93b734c4e9511ec81d30242ac130003','Jorge Miguel','Tenorio Rodriguez','Calle Virgen de lujan','Jorge@gmail.com','675843562','jorge.png','$2a$12$ueo14EU.MRYXgEhwruoDYeP7/iNtbqAh5lrLHd1Sg/9XPS4wu1yMu','GESTOR','1')

INSERT INTO USUARIOS (id,nombre,apellidos,direccion,email,telefono,avatar,password,rol) VALUES ('f5f521584e8b11ec81d30242ac130003','Jaime','Jimenez Rios','Calle Ronda','Jaime@gmail.com','634254678','jaime.jpg','$2a$12$3iFG6qb0eHAatuzOzTkzdO9ShXyo3N/IfByOAEEthKBiW1yARjhS6','PROPIETARIO')
INSERT INTO USUARIOS (id,nombre,apellidos,direccion,email,telefono,avatar,password,rol) VALUES ('f5f5227a4e8b11ec81d30242ac130003','Vicente','Rufo Bru','Calle Evergreen terrace 356','Vicente@gmail.com','657849312','vicente.jpg','$2a$12$wfb2kjVSKxj17iScU3HMceevUwGo8A1ehlXgqsHyCY1tApKg6y7Lq','PROPIETARIO')
