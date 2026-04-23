CREATE TABLE users (
    id UUID NOT NULL,
    email VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255),
    is_active BOOLEAN NOT NULL,
    user_type VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_users_email UNIQUE (email)
);

CREATE TABLE customer (
    id UUID NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE service_provider (
    id UUID NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE contractor_available_hours (
    available_hour INTEGER,
    contractor_id UUID NOT NULL,
    FOREIGN KEY (contractor_id) REFERENCES service_provider(id)
);
