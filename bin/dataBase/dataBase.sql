CREATE DATABASE IF NOT EXISTS entreprise;


CREATE TABLE IF NOT EXISTS User(
    id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(200) NOT NULL,
    prenom VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE,
    phone VARCHAR(200),
    password VARCHAR(200) NOT NULL,
    type VARCHAR(200) NOT NULL,
    salaire FLOAT(20) NOT NULL
);
CREATE TABLE IF NOT EXISTS project(
    id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(200) NOT NULL,
    stat VARCHAR(200) NOT NULL,
    chef_id INT(10) NOT NULL,
    FOREIGN KEY(chef_id) REFERENCES User(id)
);
                        
                        
CREATE TABLE IF NOT EXISTS Proj_user(
    user_id INT(10) NOT NULL,
    FOREIGN KEY(user_id) REFERENCES user(id),
    project_id INT(10) NOT NULL,
    FOREIGN KEY(project_id) REFERENCES project(id)
);
                        
                     