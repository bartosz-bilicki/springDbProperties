create table properties (
  prop_key VARCHAR(255)  NOT NULL PRIMARY KEY,
  prop_value VARCHAR(255),
  env VARCHAR(255) NULL
);

INSERT INTO properties (prop_key, prop_value) VALUES ('db.key1', 'db.val1');
INSERT INTO properties (prop_key, prop_value) VALUES ('db.key2', 'db.val2');