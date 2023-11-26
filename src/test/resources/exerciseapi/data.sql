DELETE FROM user_table;
DELETE FROM muscle_group_exercise;
DELETE FROM exercise;
DELETE FROM muscle_sub_group;
DELETE FROM muscle_sup_group_muscle_groups;
DELETE FROM muscle_group;
DELETE FROM muscle_sup_group;
DELETE FROM load_type;

INSERT INTO user_table (id, username, password) VALUES
('7b3d35e2-c24c-448d-bd5c-761adf3be31d', 'IT_TEST_USER_OK', 'user_password'),
('5f4b2d8a-2e3c-4b1d-9c2c-8daa1f33ed7d', 'IT_TEST_USER_KO', 'user_password_two');

-- _________________TESTS EXERCISE_________________

INSERT INTO load_type (id,description,name) VALUES
('4bd37b6e-08eb-484f-a562-8bbe56ae0fa5',NULL,'BAR'),
('d563d9f5-cfd4-48b1-9b4e-810e940a7120',NULL,'DUMBBELL'),
('f589b357-a06d-4afa-8be6-f0a95ee59285',NULL,'CABLE'),
('d3125176-18ff-4599-8558-d9e794039d3a',NULL,'BODYWEIGHT'),
('80d40af1-b213-431f-a3be-81823cecfc56',NULL,'MACHINE'),
('070db121-b0dd-49a7-97e1-fd1c62811b88',NULL,'MULTIPOWER');

INSERT INTO muscle_sup_group (id,description,name) VALUES
('6bdb6f1b-2302-4fd7-a5de-636c6cf8e752',NULL,'CHEST'),
('7b601780-5cf2-42e0-819b-81c289a3faf4',NULL,'BACK'),
('bc480290-d63b-4d05-a1d5-132ae57daf08',NULL,'SHOULDERS'),
('6ec3fdd6-f5c2-446a-96ba-ab2ad9bccc88',NULL,'ARMS'),
('a00beabd-dce3-47f3-ac24-b47b7b0fe782',NULL,'CORE'),
('e926b903-6250-46f6-8b30-807c7c0e6bd9',NULL,'LEGS');

INSERT INTO muscle_group (id,description,name) VALUES
('f9336a09-5af1-4c3b-b045-1940a56e0816',NULL,'CHEST'),
('1d8386f9-3c2a-478f-94c5-900dffed3f35',NULL,'SHOULDER ANTERIOR'),
('d443a8c5-2239-4725-8765-c58038ce927e',NULL,'SHOULDER LATERAL'),
('747bdc26-db99-4831-9a4d-7d6911dc109f',NULL,'SHOULDER POSTERIOR'),
('35122fb2-a6bd-4856-83f2-0d23d9d3fd5b',NULL,'LATS'),
('b025ec0c-3700-482b-b8a6-0e318a36a43b',NULL,'TRAPS'),
('cd0aa5c5-1159-45d9-9daa-b0d5b28a4f99',NULL,'LOWER BACK'),
('82db5332-b990-479f-8f45-e616221ad8c8',NULL,'BICEPS'),
('332cc20f-47e9-4ed4-815c-cc4bb10ce26d',NULL,'TRICEPS'),
('ed241eb2-bb48-4733-9713-c3f698e671ea',NULL,'FOREARMS'),
('bc965075-6244-43f5-b348-e6a72fe19fea',NULL,'ABS'),
('5daccb21-9667-4fdc-8ea1-95743cbfe0ab',NULL,'QUADS'),
('cbf879dc-aa9e-4cfc-a958-7522a3d288bf',NULL,'HAMSTRINGS'),
('51f681f2-7790-47d5-b7ee-73db82ee64e0',NULL,'GLUTES'),
('5dc861bf-c64f-49ec-bf37-cccbd92245d3',NULL,'CALVES'),
('542df6d4-27a5-41d7-aa1a-267364fd16fe',NULL,'TIBIALES ANTERIOR');

INSERT INTO muscle_sub_group (id,muscle_group_id,description,name) VALUES
('5401760d-76b4-465b-a1af-1d10a550e614','f9336a09-5af1-4c3b-b045-1940a56e0816',NULL,'CHEST UPPER'),
('7cba5b20-45c3-4c9b-ad83-690118a5c2bd','f9336a09-5af1-4c3b-b045-1940a56e0816',NULL,'CHEST LOWER'),
('b42e84d9-50b9-42d6-bc2e-a0717c60c300','f9336a09-5af1-4c3b-b045-1940a56e0816',NULL,'CHEST MIDDLE'),
('8eedef82-00d5-4d09-861d-5c9788890df9','35122fb2-a6bd-4856-83f2-0d23d9d3fd5b',NULL,'LATS UPPER'),
('441d24dd-9f74-4c6a-8d99-0b5e1733da9d','35122fb2-a6bd-4856-83f2-0d23d9d3fd5b',NULL,'LATS LOWER'),
('6b933ec5-56f9-42a2-b80f-b9d668aa1d08','332cc20f-47e9-4ed4-815c-cc4bb10ce26d',NULL,'TRICEPS LONG'),
('b6f0099e-8a50-4a6d-bd6f-9483efb7811f','332cc20f-47e9-4ed4-815c-cc4bb10ce26d',NULL,'TRICEPS SHORT'),
('ad507f19-9b89-41a9-a4d8-1589a33f990b','332cc20f-47e9-4ed4-815c-cc4bb10ce26d',NULL,'TRICEPS MIDDLE'),
('ed25ba61-b8fb-4f62-bd85-6cd77d99db4f','ed241eb2-bb48-4733-9713-c3f698e671ea',NULL,'FOREARMS FLEXORS'),
('9c6ef2d5-c01d-4b76-9285-99e111f97861','ed241eb2-bb48-4733-9713-c3f698e671ea',NULL,'FOREARMS EXTENSORS'),
('e5949d2e-4bf5-49a0-9a0e-6bba5b198bdf','ed241eb2-bb48-4733-9713-c3f698e671ea',NULL,'FOREARMS BRACHIORADIALIS');

INSERT INTO muscle_sup_group_muscle_groups (muscle_groups_id,muscle_sup_group_id) VALUES
('6ec3fdd6-f5c2-446a-96ba-ab2ad9bccc88','82db5332-b990-479f-8f45-e616221ad8c8'),
('6ec3fdd6-f5c2-446a-96ba-ab2ad9bccc88','332cc20f-47e9-4ed4-815c-cc4bb10ce26d'),
('6ec3fdd6-f5c2-446a-96ba-ab2ad9bccc88','ed241eb2-bb48-4733-9713-c3f698e671ea'),
('a00beabd-dce3-47f3-ac24-b47b7b0fe782','bc965075-6244-43f5-b348-e6a72fe19fea'),
('e926b903-6250-46f6-8b30-807c7c0e6bd9','5daccb21-9667-4fdc-8ea1-95743cbfe0ab'),
('e926b903-6250-46f6-8b30-807c7c0e6bd9','cbf879dc-aa9e-4cfc-a958-7522a3d288bf'),
('e926b903-6250-46f6-8b30-807c7c0e6bd9','51f681f2-7790-47d5-b7ee-73db82ee64e0'),
('e926b903-6250-46f6-8b30-807c7c0e6bd9','5dc861bf-c64f-49ec-bf37-cccbd92245d3'),
('e926b903-6250-46f6-8b30-807c7c0e6bd9','542df6d4-27a5-41d7-aa1a-267364fd16fe');


INSERT INTO exercise (access_type,unilateral,id,load_type_id,owner,description,name) VALUES
(0,false,'84b3f6f6-4a16-4908-84b3-7774e277b351','4bd37b6e-08eb-484f-a562-8bbe56ae0fa5','7b3d35e2-c24c-448d-bd5c-761adf3be31d',NULL,'PRESS BANCA'),
(0,false,'edb6cf72-0bf1-4b80-b9ee-65d585f3d114','4bd37b6e-08eb-484f-a562-8bbe56ae0fa5','7b3d35e2-c24c-448d-bd5c-761adf3be31d',NULL,'PRESS BANCA INCLINADO'),
(0,false,'1403a9c1-da71-41df-9fb2-2ca956b4e2c4','4bd37b6e-08eb-484f-a562-8bbe56ae0fa5','7b3d35e2-c24c-448d-bd5c-761adf3be31d',NULL,'PRESS BANCA DECLINADO'),
(0,false,'79e2e7f5-4ec4-4690-a6dd-b929e36f06b1','d563d9f5-cfd4-48b1-9b4e-810e940a7120','7b3d35e2-c24c-448d-bd5c-761adf3be31d',NULL,'PRESS CON MANCUERNAS'),
(0,false,'2ead7b16-b99f-4a40-bf55-924e04ab42dc','d563d9f5-cfd4-48b1-9b4e-810e940a7120','7b3d35e2-c24c-448d-bd5c-761adf3be31d',NULL,'PRESS CON MANCUERNAS INCLINADO'),
(0,false,'05ce8e1a-6dc5-4f8b-a305-24f669c973f3','d563d9f5-cfd4-48b1-9b4e-810e940a7120','7b3d35e2-c24c-448d-bd5c-761adf3be31d',NULL,'PRESS CON MANCUERNAS DECLINADO'),
(0,false,'8b77e9b3-55d0-49c9-93f8-ce63f7a66678','070db121-b0dd-49a7-97e1-fd1c62811b88','7b3d35e2-c24c-448d-bd5c-761adf3be31d',NULL,'PRESS EN MULTIPOWER'),
(0,false,'53c995e8-f2ba-47d2-9d63-41f511f64d98','070db121-b0dd-49a7-97e1-fd1c62811b88','7b3d35e2-c24c-448d-bd5c-761adf3be31d',NULL,'PRESS EN MULTIPOWER INCLINADO'),
(0,false,'8b4cedee-67da-4896-88a9-5b20061b1e79','070db121-b0dd-49a7-97e1-fd1c62811b88','7b3d35e2-c24c-448d-bd5c-761adf3be31d',NULL,'PRESS EN MULTIPOWER DECLINADO'),
(0,false,'a097ba6e-087d-4b1c-a93f-028ff416e18c','d563d9f5-cfd4-48b1-9b4e-810e940a7120','7b3d35e2-c24c-448d-bd5c-761adf3be31d',NULL,'PRESS CON MANCUERNAS'),
(0,false,'bfc259dc-07ea-4f15-9dee-50abc8652fca','d563d9f5-cfd4-48b1-9b4e-810e940a7120','7b3d35e2-c24c-448d-bd5c-761adf3be31d',NULL,'PRESS CON MANCUERNAS INCLINADO'),
(0,false,'14094ca8-aa0e-4f75-8987-e4841fe5ce71','d563d9f5-cfd4-48b1-9b4e-810e940a7120','7b3d35e2-c24c-448d-bd5c-761adf3be31d',NULL,'PRESS CON MANCUERNAS DECLINADO');

INSERT INTO muscle_group_exercise (weight,exercise_id,id,muscle_group_id) VALUES
(1.0,'84b3f6f6-4a16-4908-84b3-7774e277b351','6a910837-ad9a-4073-b5a0-e5608fd83e62','f9336a09-5af1-4c3b-b045-1940a56e0816'),
(1.0,'edb6cf72-0bf1-4b80-b9ee-65d585f3d114','f65fb5ec-d525-4c4c-b727-1cfecbed9698','f9336a09-5af1-4c3b-b045-1940a56e0816'),
(1.0,'1403a9c1-da71-41df-9fb2-2ca956b4e2c4','354c7137-1e0b-4a05-ab11-01da5ebcce17','f9336a09-5af1-4c3b-b045-1940a56e0816'),
(1.0,'79e2e7f5-4ec4-4690-a6dd-b929e36f06b1','192250a1-32cf-475c-8ed3-95188395f665','f9336a09-5af1-4c3b-b045-1940a56e0816'),
(1.0,'2ead7b16-b99f-4a40-bf55-924e04ab42dc','b7cdc04d-f700-4c4b-8069-e923d2a743ce','f9336a09-5af1-4c3b-b045-1940a56e0816'),
(1.0,'05ce8e1a-6dc5-4f8b-a305-24f669c973f3','dd1b3cf9-b3c4-4b09-9549-9910d129dfba','f9336a09-5af1-4c3b-b045-1940a56e0816'),
(1.0,'8b77e9b3-55d0-49c9-93f8-ce63f7a66678','760ffb2d-17c3-4bb6-bb65-07c5bab5a6c3','f9336a09-5af1-4c3b-b045-1940a56e0816'),
(1.0,'53c995e8-f2ba-47d2-9d63-41f511f64d98','f31e5e01-f6ec-4982-a8b6-e90ba5953db7','f9336a09-5af1-4c3b-b045-1940a56e0816'),
(1.0,'8b4cedee-67da-4896-88a9-5b20061b1e79','1523f2ba-a170-4aa3-a68a-bc815a7c1d0a','f9336a09-5af1-4c3b-b045-1940a56e0816'),
(1.0,'a097ba6e-087d-4b1c-a93f-028ff416e18c','f6866d99-9c5f-4d9e-80b6-b958df6fff09','f9336a09-5af1-4c3b-b045-1940a56e0816');

INSERT INTO muscle_group_exercise (weight,exercise_id,id,muscle_group_id) VALUES
(1.0,'bfc259dc-07ea-4f15-9dee-50abc8652fca','d546dc32-c436-4d22-a2cf-29bf2218a2eb','f9336a09-5af1-4c3b-b045-1940a56e0816'),
(1.0,'14094ca8-aa0e-4f75-8987-e4841fe5ce71','ecd90c84-a58a-4124-b30d-1a149755033d','f9336a09-5af1-4c3b-b045-1940a56e0816');
