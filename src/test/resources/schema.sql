CREATE TABLE IF NOT EXISTS writer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    bio TEXT
);

CREATE TABLE IF NOT EXISTS magazine (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    publicationDate VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS writer_magazine (
    writerId INT,
    magazineId INT,
    PRIMARY KEY(writerId, magazineId),
    FOREIGN KEY(writerId) REFERENCES writer(id),
    FOREIGN KEY(magazineId) REFERENCES magazine(id)
);