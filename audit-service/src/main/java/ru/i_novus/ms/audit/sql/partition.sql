CREATE OR REPLACE FUNCTION create_partition_and_insert() RETURNS trigger AS
$body$
DECLARE
  partition_date TEXT;
  partition      TEXT;
BEGIN
  partition_date := to_char(NEW.event_date, 'YYYY_MM');
  partition := tg_table_name || '_' || partition_date;
  IF NOT EXISTS(SELECT relname FROM pg_class WHERE relname = partition) THEN
    RAISE NOTICE 'A partition has been created %', partition;
    EXECUTE 'CREATE TABLE ' || partition || ' (check (date>= ''' || date(date_trunc('month', new.event_date)) ||
            ''' AND date< ''' ||
            date(date_trunc('month',
                            new.event_date) + '1month') || ''')) INHERITS (' || tg_table_name ||
            ');';
  END IF;
  EXECUTE format('INSERT INTO %I VALUES ('|| New.* || ')', partition);
  raise notice 'in func';
  return null;
END;
$body$
  LANGUAGE plpgsql;

CREATE TRIGGER test_trigger
  BEFORE INSERT
  on audit
  FOR EACH ROW
EXECUTE PROCEDURE create_partition_and_insert();

