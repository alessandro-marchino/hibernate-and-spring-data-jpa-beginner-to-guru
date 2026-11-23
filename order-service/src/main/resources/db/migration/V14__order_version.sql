ALTER TABLE order_header ADD COLUMN version INTEGER;
ALTER TABLE order_line ADD COLUMN version INTEGER;

UPDATE order_header SET version = 0 WHERE version IS NULL;
UPDATE order_line SET version = 0 WHERE version IS NULL;
