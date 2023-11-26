DELETE FROM user_table;
DELETE FROM set_table;
DELETE FROM set_group_entity;
DELETE FROM workout_entity;

INSERT INTO user_table (id, username, password) VALUES
('e0e5af19-7c1e-4d1c-96a8-fedc1e1ccc65', 'IT_TEST_USER_OK', '8a76bbdb-625d-4a99-a191-6aed4a0842f6'),
('21bbc752-dea7-465d-90d4-d8a457692177', 'IT_TEST_USER_KO', 'e5658ba4-6cac-40a3-8a16-91f1d43f3c39');

INSERT INTO workout_entity (id, workout_date, description, user_id) VALUES
('f48ecca5-a840-4c2f-8788-f82dd9103239', '2023-10-23', NULL, 'e0e5af19-7c1e-4d1c-96a8-fedc1e1ccc65'),
('27a24eec-65f7-49aa-b7c5-313d9836cbcb', '2023-11-03', NULL, 'e0e5af19-7c1e-4d1c-96a8-fedc1e1ccc65'),
('c45d0f49-0cce-4c23-8ac3-7cd0de47b26a', '2023-11-04', NULL, 'e0e5af19-7c1e-4d1c-96a8-fedc1e1ccc65'),
('6a4fc3a7-c476-4490-874b-b2313bee0098', '2023-11-05', NULL, 'e0e5af19-7c1e-4d1c-96a8-fedc1e1ccc65');

INSERT INTO set_group_entity (id, list_order, description, exercise_id, workout_id, rest, eccentric, first_pause, concentric, second_pause, super_set_with_next) VALUES
('7a8f6de6-7c6d-466e-bacb-9dcb26bd78ad', 0, NULL, 'e54fb752-8d3a-4dc6-92ee-5bcf22555e58', 'f48ecca5-a840-4c2f-8788-f82dd9103239', 60, 3, 0, 1, 0, false),
('431d00a4-485b-47b4-bad3-2033bcfe6170', 1, NULL, 'b744c8c9-ef76-4aa1-b3e9-4b2b1be50540', 'f48ecca5-a840-4c2f-8788-f82dd9103239', 60, 3, 0, 1, 0, false),
('5a9503e7-cdb9-4824-b5d5-174c9a5835ce', 2, NULL, 'b744c8c9-ef76-4aa1-b3e9-4b2b1be50540', 'f48ecca5-a840-4c2f-8788-f82dd9103239', 60, 3, 0, 1, 0, false),
('6e3acfc2-d8a4-4dd4-b561-3ac4439feecd', 3, NULL, 'b744c8c9-ef76-4aa1-b3e9-4b2b1be50540', 'f48ecca5-a840-4c2f-8788-f82dd9103239', 60, 3, 0, 1, 0, false),
('1e59a819-8e46-4a1b-8a32-3fa2918b98d7', 4, NULL, 'b744c8c9-ef76-4aa1-b3e9-4b2b1be50540', 'f48ecca5-a840-4c2f-8788-f82dd9103239', 60, 3, 0, 1, 0, false);

INSERT INTO set_table (id, list_order, description, reps, rir, weight, completed_at, set_group_id) VALUES
('b254efa7-7aea-45dd-bbd3-d46db28737bd', 0, NULL, 10, 1,41, NOW(), '7a8f6de6-7c6d-466e-bacb-9dcb26bd78ad'),
('967f6805-152b-4e92-a3ba-cd420c133b59', 1, NULL, 14, 1,24, NULL, '7a8f6de6-7c6d-466e-bacb-9dcb26bd78ad'),
('c282a196-69f4-4de2-94fa-a60ec5201686', 2, 'tvshrmlqqaqalyrdusdeoaopdgunhwpkzznujgxwmyjgzvmbekegxsxfungkljo', 11, 2,60, NULL, '7a8f6de6-7c6d-466e-bacb-9dcb26bd78ad'),
('e4e404b8-36ab-452a-9274-ea03983ed66d', 3, NULL, 8, 2,01, NULL, '7a8f6de6-7c6d-466e-bacb-9dcb26bd78ad');