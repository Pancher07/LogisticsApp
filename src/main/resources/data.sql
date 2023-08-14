INSERT INTO users(name, surname, email, login, password, phone, role, user_active)
VALUES ('User1', 'Userenko1', 'user1@user.com', 'user1', 'password1', '+380501111111', 1, true);

INSERT INTO users(name, surname, email, login, password, phone, role, user_active)
VALUES ('User2', 'Userenko2', 'user2@user.com', 'user2', 'password2', '+380502222222', 2, true);

INSERT INTO managers(user_id)
VALUES (1);

INSERT INTO logisticians(user_id)
VALUES (2);

INSERT INTO truck_tractors(plate_number, model, pump)
VALUES ('BH1234BH', 'DAF', 1);

INSERT INTO truck_tractors(plate_number, model, pump)
VALUES ('BH2233BH', 'DAF', 1);

INSERT INTO truck_tractors(plate_number, model, pump)
VALUES ('BH3344BH', 'DAF', 1);

INSERT INTO trailers(plate_number, model, volume, calibration, petroleum_type)
VALUES ('BH1111BH', 'Kassbohrer', 31000, 0, 0);

INSERT INTO trailers(plate_number, model, volume, calibration, petroleum_type)
VALUES ('BH2222BH', 'Kassbohrer', 31000, 0, 0);

INSERT INTO trailers(plate_number, model, volume, calibration, petroleum_type)
VALUES ('BH3333BH', 'Kassbohrer', 31000, 0, 1);

INSERT INTO drivers(name, middle_name, surname, phone, last_time_worked)
VALUES ('Іван', 'Іванович', 'Іванов', '+380111111111', '2023-06-22 14:00:00');

INSERT INTO drivers(name, middle_name, surname, phone, last_time_worked)
VALUES ('Джон', 'Джонович', 'Джонсонюк', '+3802222222', '2023-06-20 14:00:00');

INSERT INTO drivers(name, middle_name, surname, phone, last_time_worked)
VALUES ('Максим', 'Максимович', 'Максименко', '+380333333333', '2023-06-15 14:00:00');

INSERT INTO projects(petroleum_type, project_country)
VALUES (0, 0);

INSERT INTO contractors(manager_id, name)
VALUES (1, 'ТОВ ЗНГК');

INSERT INTO task_lists(status, created_at, logistician_id)
VALUES (0, '2023-06-21 14:00:00', 1);

INSERT INTO hitches (vehicle_status, driver_id, logistician_id, project_id, trailer_id, truck_tractor_id, comment,
                     location, loaded_with_product)
VALUES (0, 1, 1, 1, 1, 1, 'no comment', 'Измаил', 0);

INSERT INTO hitches (vehicle_status, driver_id, logistician_id, project_id, trailer_id, truck_tractor_id, comment,
                     location, loaded_with_product)
VALUES (0, 2, 1, 1, 2, 2, 'no comment', 'Измаил', 0);

INSERT INTO hitches (vehicle_status, driver_id, logistician_id, project_id, trailer_id, truck_tractor_id, comment,
                     location, loaded_with_product)
VALUES (0, 3, 1, 1, 3, 3, 'no comment', 'Измаил', 0);

INSERT INTO loading_orders (count_of_vehicle, petroleum_type, status, created_at, loading_date_time, loading_point)
VALUES (100, 0, 0, '2023-06-27 14:00:00', '2023-06-30 08:00:00', 'Измаил');

INSERT INTO loading_orders (count_of_vehicle, petroleum_type, status, created_at, loading_date_time, loading_point)
VALUES (2, 0, 0, '2023-08-14 14:00:00', '2023-08-30 08:00:00', 'Одесса');

INSERT INTO manager_orders (calibration, pump, status, type_of_product, volume, contractor_id, created_at, hitch_id,
                            manager_id, uploading_date_time, contact)
VALUES (1, 0, 0, 0, 31000, 1, '2023-07-13 15:00:00', null, 1, '2023-07-14 14:00:00', 'contact +38050000000');

INSERT INTO manager_orders (calibration, pump, status, type_of_product, volume, contractor_id, created_at, hitch_id,
                            manager_id, uploading_date_time, contact)
VALUES (1, 0, 0, 0, 31000, 1, '2023-06-14 11:00:00', null, 1, '2023-07-16 14:00:00', 'contact +380511111111')