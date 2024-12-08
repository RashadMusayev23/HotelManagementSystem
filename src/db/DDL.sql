CREATE TABLE Hotel
(
    hotel_id     INT PRIMARY KEY,
    hotel_name   VARCHAR(100) NOT NULL,
    location     VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20)  NOT NULL,
    room_count   INT          NOT NULL,
);

CREATE TABLE User
(
    user_id  INT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE Administrator
(
    user_id INT PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES User (user_id)
);

CREATE TABLE Receptionist
(
    user_id INT PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES User (user_id)
);

CREATE TABLE Housekeeper
(
    user_id INT PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES User (user_id)
);

CREATE TABLE Guest
(
    user_id INT PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES User (user_id)
);

CREATE TABLE Room
(
    room_id      INT PRIMARY KEY,
    room_name    VARCHAR(100) NOT NULL UNIQUE,
    room_type    VARCHAR(50)  NOT NULL,
    max_capacity INT          NOT NULL,
    status       VARCHAR(20)  NOT NULL,
    hotel_id     INT          NOT NULL,
    FOREIGN KEY (hotel_id) REFERENCES Hotel (hotel_id)
);

CREATE TABLE Booking
(
    booking_id     INT PRIMARY KEY,
    guest_id       INT         NOT NULL,
    room_id        INT         NOT NULL,
    start_date     DATE        NOT NULL,
    end_date       DATE        NOT NULL,
    payment_status VARCHAR(20) NOT NULL,
    status         VARCHAR(50) NOT NULL,
    FOREIGN KEY (guest_id) REFERENCES Guest (user_id),
    FOREIGN KEY (room_id) REFERENCES Room (room_id)
);

CREATE TABLE Payment
(
    payment_id     INT PRIMARY KEY,
    booking_id     INT            NOT NULL,
    amount         DECIMAL(10, 2) NOT NULL,
    payment_date   DATE           NOT NULL,
    payment_method VARCHAR(50)    NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES Booking (booking_id)
);

CREATE TABLE Housekeeping
(
    housekeeping_id INT PRIMARY KEY,
    room_id         INT         NOT NULL,
    cleaned_status  VARCHAR(50) NOT NULL,
    schedule_date   DATE        NOT NULL,
    housekeeper_id  INT         NOT NULL,
    FOREIGN KEY (room_id) REFERENCES Room (room_id),
    FOREIGN KEY (housekeeper_id) REFERENCES Housekeeper (user_id)
);

CREATE TABLE ResidesAt
(
    resides_id     INT PRIMARY KEY,
    guest_id       INT  NOT NULL,
    hotel_id       INT  NOT NULL,
    check_in_date  DATE NOT NULL,
    check_out_date DATE NOT NULL,
    FOREIGN KEY (guest_id) REFERENCES Guest (user_id),
    FOREIGN KEY (hotel_id) REFERENCES Hotel (hotel_id)
);

CREATE TABLE WorksAt
(
    works_at_id INT PRIMARY KEY,
    user_id     INT NOT NULL,
    hotel_id    INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User (user_id),
    FOREIGN KEY (hotel_id) REFERENCES Hotel (hotel_id)
);