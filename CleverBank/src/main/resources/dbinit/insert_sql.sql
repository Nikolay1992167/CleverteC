INSERT INTO banks (title, bic)
VALUES ('CleverBank', 'CLEVER'),
       ('VTB', 'VTBRRUMM'),
       ('GazPromBank', 'GAZPRUMM'),
       ('RosSelBank', 'RUAGRUMM'),
       ('BankWindow', 'RUDLRUMM');

INSERT INTO users (name, email, password)
VALUES ('Anna Petrova', 'petrova@mail.ru', 'qwerty'),
       ('Sergej Smirnov', 'smirnov@mail.ru', 'asdfgh'),
       ('Maria Sidorova', 'sidorova@mail.ru', 'zxcvbn'),
       ('Alex Kuznetsov', 'kuznetsov@mail.ru', 'iloveyou'),
       ('Helen Popova', 'popova@mail.ru', 'letmein'),
       ('Den Socolov', 'sokolov@mail.ru', 'password'),
       ('Olga Novikova', 'novikova@mail.ru', 'trustno1'),
       ('Andrej Morozov', 'morozov@mail.ru', 'dragon'),
       ('Irina Volkova', 'volkova@mail.ru', 'baseball'),
       ('Nikolaj Pavlov', 'pavlov@mail.ru', 'football'),
       ('Jes Zaytseva', 'zaytseva@mail.ru', 'monkey'),
       ('Mihail Fedorov', 'fedorov@mail.ru', 'shadow'),
       ('Vera Belova', 'belova@mail.ru', 'master'),
       ('Kiril Egorov', 'egorov@mail.ru', 'superman'),
       ('Ludmila Orlova', 'orlova@mail.ru', 'qazwsx'),
       ('Vladimir Gusev', 'gusev@mail.ru', 'michael'),
       ('Nat Makarova', 'makarova@mail.ru', 'jennifer'),
       ('Artiem Lebedev', 'lebedev@mail.ru', 'hunter'),
       ('Galina Romanova', 'romanova@mail.ru', 'harley'),
       ('Pavel Kozlov', 'kozlov@mail.ru', 'ranger');

INSERT INTO accounts (currency, date_open, number, balance, bank_id, user_id)
VALUES ('BYN', ' 2023-04-15 12:00:00+03', '00001', '100', '1', '1'),
       ('BYN', ' 2023-04-16 11:00:00+03', '00002', '200', '2', '2'),
       ('BYN', ' 2023-04-17 10:11:00+03', '00003', '300', '3', '3'),
       ('BYN', ' 2023-04-18 14:00:00+03', '00004', '400', '4', '4'),
       ('BYN', ' 2023-04-19 12:00:00+03', '00005', '500', '5', '5'),
       ('BYN', ' 2023-04-20 15:00:00+03', '00006', '600', '1', '6'),
       ('BYN', ' 2023-04-21 13:00:00+03', '00007', '700', '2', '7'),
       ('BYN', ' 2023-04-22 12:00:00+03', '00008', '800', '3', '8'),
       ('BYN', ' 2023-04-23 12:00:00+03', '00009', '900', '4', '9'),
       ('BYN', ' 2023-04-24 18:00:00+03', '00010', '1000', '5', '10'),
       ('BYN', ' 2023-04-25 13:00:00+03', '00011', '1100', '1', '11'),
       ('BYN', ' 2023-04-26 17:00:00+03', '00012', '1200', '2', '12'),
       ('BYN', ' 2023-04-27 14:00:00+03', '00013', '1300', '3', '13'),
       ('BYN', ' 2023-04-28 11:00:00+03', '00014', '1400', '4', '14'),
       ('BYN', ' 2023-05-01 12:00:00+03', '00015', '1500', '5', '15'),
       ('BYN', ' 2023-05-02 12:00:00+03', '00016', '1600', '1', '16'),
       ('BYN', ' 2023-05-03 15:00:00+03', '00017', '1700', '2', '17'),
       ('BYN', ' 2023-05-15 12:00:00+03', '00018', '1800', '3', '18'),
       ('BYN', ' 2023-05-16 13:30:00+03', '00019', '1900', '4', '19'),
       ('BYN', ' 2023-05-17 12:00:00+03', '00020', '2000', '5', '20'),
       ('BYN', ' 2023-05-18 10:00:00+03', '00021', '2100', '3', '1'),
       ('BYN', ' 2023-05-19 13:00:00+03', '00022', '2200', '5', '2'),
       ('BYN', ' 2023-05-20 19:00:00+03', '00023', '2300', '2', '3'),
       ('BYN', ' 2023-05-21 14:00:00+03', '00024', '2400', '1', '4'),
       ('BYN', ' 2023-05-22 17:00:00+03', '00025', '2500', '4', '5'),
       ('BYN', ' 2023-05-24 18:00:00+03', '00026', '2600', '3', '6'),
       ('BYN', ' 2023-06-11 10:00:00+03', '00027', '2700', '5', '7'),
       ('BYN', ' 2023-06-12 11:00:00+03', '00028', '2800', '2', '8'),
       ('BYN', ' 2023-06-13 12:00:00+03', '00029', '2900', '1', '9'),
       ('BYN', ' 2023-06-14 13:00:00+03', '00030', '3000', '4', '10'),
       ('BYN', ' 2023-06-15 14:00:00+03', '00031', '3100', '3', '11'),
       ('BYN', ' 2023-06-16 15:00:00+03', '00032', '3200', '5', '12'),
       ('BYN', ' 2023-06-17 16:00:00+03', '00033', '3300', '2', '13'),
       ('BYN', ' 2023-06-18 17:00:00+03', '00034', '3400', '1', '14'),
       ('BYN', ' 2023-06-19 18:00:00+03', '00035', '3500', '4', '15'),
       ('BYN', ' 2023-06-20 19:00:00+03', '00036', '3600', '3', '16'),
       ('BYN', ' 2023-06-21 14:00:00+03', '00037', '3700', '5', '17'),
       ('BYN', ' 2023-07-15 14:00:00+03', '00038', '3800', '2', '18'),
       ('BYN', ' 2023-07-16 11:00:00+03', '00039', '3900', '1', '19'),
       ('BYN', ' 2023-07-18 15:00:00+03', '00040', '4000', '4', '20');

INSERT INTO transactions (type_transaction, from_account, to_account, amount, date)
VALUES ('TRANSFER', '00002', '00001', '50.00', '2023-07-15 14:00:00+03'),
       ('TRANSFER', '00006', '00004', '150.00', '2023-08-18 14:00:00+03');