DROP TABLE IF EXISTS tb_template CASCADE;
CREATE TABLE tb_template
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(250) NOT NULL,
    date_created  TIMESTAMP             DEFAULT CURRENT_TIMESTAMP NOT NULL,
    date_modified TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO tb_template (id, name)
VALUES (1, 'Template1'),
       (2, 'Template2'),
       (3, 'Template3'),
       (4, 'Template4');


DROP TABLE IF EXISTS tb_users CASCADE;
CREATE TABLE tb_users
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(250) NOT NULL,
    date_created  TIMESTAMP             DEFAULT CURRENT_TIMESTAMP NOT NULL,
    date_modified TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO tb_users (id, name)
VALUES (1, 'Fulano'),
       (2, 'Ciclano'),
       (3, 'Beltrano');