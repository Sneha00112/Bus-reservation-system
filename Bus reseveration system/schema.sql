CREATE DATABASE IF NOT EXISTS busbookingsystem;
USE busbookingsystem;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    route VARCHAR(255) NOT NULL,
    travel_date DATE NOT NULL,
    time VARCHAR(10) NOT NULL,
    contact VARCHAR(100) NOT NULL,
    passenger_count INT NOT NULL,
    passenger_names TEXT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS bookseats (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(50) NOT NULL,
    booked_seats TEXT NOT NULL,
    travel_date DATE NOT NULL,
    time VARCHAR(10) NOT NULL,
    route VARCHAR(50) NOT NULL,
    total_price DOUBLE NOT NULL,
    UNIQUE (travel_date, time, route)
);

CREATE TABLE IF NOT EXISTS meals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    meal_name VARCHAR(100) NOT NULL,
    image_path VARCHAR(255) NOT NULL
);

INSERT IGNORE INTO meals (id, meal_name, image_path) VALUES (1, 'Sambar rice (Rs. 100)', '/sambar/sambar.jpg');
INSERT IGNORE INTO meals (id, meal_name, image_path) VALUES (2, 'Curd rice (Rs. 70)', '/curdrice/curdrice.jpg');
INSERT IGNORE INTO meals (id, meal_name, image_path) VALUES (3, 'Pongal (Rs. 100)', '/pongal/pongal.jpg');
INSERT IGNORE INTO meals (id, meal_name, image_path) VALUES (4, 'Idli (Rs. 80)', '/idly/idly.jpg');
INSERT IGNORE INTO meals (id, meal_name, image_path) VALUES (5, 'Dosa (Rs. 60)', '/dosa/dosa.jpg');
