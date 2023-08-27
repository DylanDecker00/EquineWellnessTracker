-- Owners Table
CREATE TABLE IF NOT EXISTS owners (
    owner_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(255),
    ownership VARCHAR(255)
);

-- Services Table
CREATE TABLE IF NOT EXISTS services (
    service_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(255) NOT NULL,
    hourly_rate DOUBLE NOT NULL
);

-- Horses Table
CREATE TABLE IF NOT EXISTS horses (
    horse_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    kind VARCHAR(255),
    color VARCHAR(255),
    gender VARCHAR(255),
    work_specialty VARCHAR(255),
    owner_id BIGINT,
    FOREIGN KEY (owner_id) REFERENCES owners(owner_id)
);

-- Join Table for ManyToMany Relationship between Horses and Services
CREATE TABLE IF NOT EXISTS horse_service (
    horse_id BIGINT,
    service_id BIGINT,
    PRIMARY KEY (horse_id, service_id),
    FOREIGN KEY (horse_id) REFERENCES horses(horse_id),
    FOREIGN KEY (service_id) REFERENCES services(service_id)
);
ALTER TABLE horses MODIFY COLUMN age INT NULL
);