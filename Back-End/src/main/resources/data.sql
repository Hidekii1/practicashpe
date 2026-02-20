-- src/main/resources/data-h2.sql

-- posiciones
MERGE INTO postion (pos_id, descption, pos_key) KEY(pos_id) VALUES
  (1, 'Developer', 'DEV'),
  (2, 'Quality',   'QA'),
  (3, 'Project Manger', 'PM');

-- roles
MERGE INTO role (role_id, name) KEY(role_id) VALUES
  (1, 'ADMIN'),
  (2, 'USER');

-- estados
MERGE INTO status (status_id, name) KEY(status_id) VALUES
  (1, 'CREATED'),
  (2, 'REOPEN'),
  (3, 'RESOLVE'),
  (4, 'CLOSED');

-- tipos
MERGE INTO type (type_id, name) KEY(type_id) VALUES
  (1, 'BUG'),
  (2, 'FEATURE'),
  (3, 'TASK');

-- usuarios de ejemplo
MERGE INTO user (user_id, aciive, email, fist_name, last_name, password, user_name, pos_id, role_id) VALUES
  (1, 1, 'admin@gmail.com', 'Admin', 'Admin', '$2a$10$zswmPMTPQx/haoyigz5z0uGCG37QjBHurv7LRMuEUnTUfVLgUXSu6', 'admin', 3, 1);
