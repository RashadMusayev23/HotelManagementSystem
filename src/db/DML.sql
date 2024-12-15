-- Hotel already has 3 rows
INSERT INTO Hotel (hotel_id, hotel_name, location, phone_number, room_count, chain_name, star_rating)
VALUES
    (1, 'Grand Hotel', 'New York', '+1 555 55 55 5', 100, 'Luxury Stay Group', 5),
    (2, 'Sea Breeze Hotel', 'Miami', '+1 022 55 55 5', 110, 'Oceanic Hotels', 4),
    (3, 'Mountain View Hotel', 'Denver', '+1 233 55 55 5', 120, 'Nature Retreats', 3);

-- User currently has 4, we add more to accommodate multiple admins/receptionists/etc.
INSERT INTO User (user_id, username, password)
VALUES
    (1, 'admin1', 'adminpass'),
    (2, 'reception1', 'receptionpass'),
    (3, 'housekeeper1', 'housekeeperpass'),
    (4, 'guest1', 'guestpass'),
    (5, 'admin2', 'admin2pass'),
    (6, 'admin3', 'admin3pass'),
    (7, 'reception2', 'receptionpass2'),
    (8, 'reception3', 'receptionpass3'),
    (9, 'housekeeper2', 'housekeeperpass2'),
    (10, 'housekeeper3', 'housekeeperpass3'),
    (11, 'guest2', 'guest2pass'),
    (12, 'guest3', 'guest3pass');

-- Administrator now at least 3 entries
INSERT INTO Administrator (user_id)
VALUES (1), (5), (6);

-- Receptionist now at least 3 entries
INSERT INTO Receptionist (user_id)
VALUES (2), (7), (8);

-- Housekeeper now at least 3 entries
INSERT INTO Housekeeper (user_id)
VALUES (3), (9), (10);

-- Guest now at least 3 entries
INSERT INTO Guest (user_id)
VALUES (4), (11), (12);

-- Room already has 3 rows - sufficient
INSERT INTO Room (room_id, room_name, room_type, max_capacity, status, hotel_id, rate, discount)
VALUES
    (1, 'Room 101', 'Single', 2, 'Available', 1, 100.00, 0.00),
    (2, 'Room 102', 'Double', 4, 'Booked', 1, 150.00, 0.00),
    (3, 'Suite 201', 'Suite', 6, 'Available', 2, 300.00, 10.00);

-- Booking initially had 1, add 2 more for total of 3
INSERT INTO Booking (booking_id, guest_id, room_id, start_date, end_date, payment_status, status)
VALUES
    (1, 4, 2, '2024-12-10', '2024-12-15', 'Paid', 'Confirmed'),
    (2, 11, 1, '2024-12-20', '2024-12-22', 'Pending', 'Requested'),
    (3, 12, 3, '2025-01-05', '2025-01-10', 'Paid', 'Confirmed');

-- Payment had 1, add 2 more
INSERT INTO Payment (payment_id, booking_id, amount, payment_date, payment_method)
VALUES
    (1, 1, 500.00, '2024-12-03', 'Credit Card'),
    (2, 2, 300.00, '2024-12-19', 'Cash'),
    (3, 3, 700.00, '2024-12-01', 'Credit Card');

-- Housekeeping had 1, add 2 more
INSERT INTO Housekeeping (housekeeping_id, room_id, cleaned_status, schedule_date, housekeeper_id)
VALUES
    (1, 1, 'Pending', '2024-12-04', 3),
    (2, 2, 'Completed', '2024-12-05', 9),
    (3, 3, 'Pending', '2024-12-06', 10);

-- ResidesAt had 1, add 2 more
INSERT INTO ResidesAt (resides_id, guest_id, hotel_id, check_in_date, check_out_date)
VALUES
    (1, 4, 1, '2024-12-10', '2024-12-15'),
    (2, 11, 2, '2024-12-20', '2024-12-22'),
    (3, 12, 3, '2025-01-05', '2025-01-10');

-- WorksAt had 3 rows already, which is sufficient, but we can leave as is
INSERT INTO WorksAt (works_at_id, user_id, hotel_id)
VALUES
    (1, 1, 1),
    (2, 2, 1),
    (3, 3, 1);

SELECT b.booking_id, u.username AS guest_username, r.room_name, b.start_date, b.end_date, b.status
FROM Booking b
         JOIN Guest g ON b.guest_id = g.user_id
         JOIN User u ON g.user_id = u.user_id
         JOIN Room r ON b.room_id = r.room_id
WHERE u.username = 'guest1';

SELECT h.hotel_name, COUNT(r.room_id) AS available_rooms
FROM Hotel h
         JOIN Room r ON h.hotel_id = r.hotel_id
WHERE r.status = 'Available'
GROUP BY h.hotel_name
HAVING COUNT(r.room_id) > 0;

SELECT h.hotel_name, SUM(p.amount) AS total_revenue
FROM Payment p
         JOIN Booking b ON p.booking_id = b.booking_id
         JOIN Room r ON b.room_id = r.room_id
         JOIN Hotel h ON r.hotel_id = h.hotel_id
GROUP BY h.hotel_name;

UPDATE Booking
SET status = 'Completed'
WHERE booking_id = 1;

UPDATE Room
SET status   = 'Maintenance',
    discount = 10.00
WHERE room_id = 3;

UPDATE Room
SET status = 'Maintenance'
WHERE room_id = 3;