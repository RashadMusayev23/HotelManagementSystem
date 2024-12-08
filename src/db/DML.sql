INSERT INTO Hotel (hotel_id, hotel_name, location, phone_number, room_count)
VALUES (1, 'Grand Hotel', 'New York', '+1 555 55 55 5', 100),
       (2, 'Sea Breeze Hotel', 'Miami', '+1 022 55 55 5', 110),
       (3, 'Mountain View Hotel', 'Denver', '+1 233 55 55 5', 120);

INSERT INTO User (user_id, username, password)
VALUES (1, 'admin1', 'adminpass'),
       (2, 'reception1', 'receptionpass'),
       (3, 'housekeeper1', 'housekeeperpass'),
       (4, 'guest1', 'guestpass');

INSERT INTO Administrator (user_id)
VALUES (1);
INSERT INTO Receptionist (user_id)
VALUES (2);
INSERT INTO Housekeeper (user_id)
VALUES (3);
INSERT INTO Guest (user_id)
VALUES (4);

INSERT INTO Room (room_id, room_name, room_type, max_capacity, status, hotel_id)
VALUES (1, 'Room 101', 'Single', 2, 'Available', 1),
       (2, 'Room 102', 'Double', 4, 'Booked', 1),
       (3, 'Suite 201', 'Suite', 6, 'Available', 2);

INSERT INTO Booking (booking_id, guest_id, room_id, start_date, end_date, payment_status, status)
VALUES (1, 4, 2, '2024-12-10', '2024-12-15', 'Paid', 'Confirmed');

INSERT INTO Payment (payment_id, booking_id, amount, payment_date, payment_method)
VALUES (1, 1, 500.00, '2024-12-03', 'Credit Card');

INSERT INTO Housekeeping (housekeeping_id, room_id, cleaned_status, schedule_date, housekeeper_id)
VALUES (1, 1, 'Pending', '2024-12-04', 3);

INSERT INTO ResidesAt (resides_id, guest_id, hotel_id, check_in_date, check_out_date)
VALUES (1, 4, 1, '2024-12-10', '2024-12-15');

INSERT INTO WorksAt (works_at_id, user_id, hotel_id)
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 3, 1);