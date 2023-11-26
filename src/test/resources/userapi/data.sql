DELETE FROM user_table;
DELETE FROM settings_entity_selected_plates;
DELETE FROM settings_entity;

INSERT INTO user_table (id, username, password) VALUES
('e0e5af19-7c1e-4d1c-96a8-fedc1e1ccc65', 'IT_TEST_USER_WITH_SETTINGS_OK', '8a76bbdb-625d-4a99-a191-6aed4a0842f6'),
('a672f95d-a2a9-46af-b82d-3d542ce27fc3', 'IT_TEST_USER_WITHOUT_SETTINGS_OK', '949c9ae1-c396-4336-aea7-4ee23ae58439'),
('21bbc752-dea7-465d-90d4-d8a457692177', 'IT_TEST_USER_KO', 'e5658ba4-6cac-40a3-8a16-91f1d43f3c39');

INSERT INTO settings_entity (id, user_id, international_system, selected_increment, selected_bar) VALUES
('c47a0661-6e02-4c76-8dc6-03a72c09607b', 'e0e5af19-7c1e-4d1c-96a8-fedc1e1ccc65', TRUE, 1.25, 20);

INSERT INTO settings_entity_selected_plates (selected_plates, settings_entity_id) VALUES
(0.5, 'c47a0661-6e02-4c76-8dc6-03a72c09607b'),
(1, 'c47a0661-6e02-4c76-8dc6-03a72c09607b'),
(1.25, 'c47a0661-6e02-4c76-8dc6-03a72c09607b'),
(2.5, 'c47a0661-6e02-4c76-8dc6-03a72c09607b'),
(5, 'c47a0661-6e02-4c76-8dc6-03a72c09607b');