INSERT INTO users (name, email)
VALUES
    ('Андрей', 'an@gmail.com'),
    ('Владимир', 'vlad@gmail.com'),
    ('Виктор', 'victor@gmail.com');

INSERT INTO requests (description, created, requestor_id)
VALUES
    ('Заявка 1', '2024-01-14 12:34:56', 1),
    ('Заявка 2', '2024-01-14 12:34:56', 2),
    ('Заявка 3', '2024-01-14 12:34:56', 3);

INSERT INTO items (name, description, available, owner_id, request_id)
VALUES
    ('Компьютер1', 'Компьютер1', TRUE, 3, 1),
    ('Компьютер2', 'Компьютер2', TRUE, 1, 2),
    ('Компьютер3', 'Компьютер3', TRUE, 2, 3);

INSERT INTO bookings (start_date, end_date, item_id, booker_id, status)
VALUES
    ('2024-01-14 12:34:56', '2024-01-14 12:34:57', 1, 1, 'APPROVED'),
    ('2024-01-14 12:34:56', '2024-01-14 12:34:57', 2, 2, 'APPROVED'),
    ('2024-01-14 12:34:56', '2024-01-14 12:34:57', 3, 3, 'APPROVED');