create function create_partition_and_insert() returns trigger
  language plpgsql
as
$$
DECLARE
  partition_date TEXT;
  partition_name TEXT;
  sql TEXT;
BEGIN
  partition_date := to_char(NEW.event_date, 'YYYY_MM');
  partition_name := tg_table_name || '_' || partition_date;
  IF NOT EXISTS(SELECT relname FROM pg_class WHERE relname = partition_name) THEN
    RAISE NOTICE 'A partition has been created %', partition_name;
    EXECUTE 'CREATE TABLE ' ||tg_relname||'.'|| partition_name || ' (check (event_date>= ''' || date(date_trunc('month', new.event_date)) ||
            ''' AND event_date< ''' ||
            date(date_trunc('month',
                            new.event_date) + '1month') || ''')) INHERITS (' || tg_table_schema|| '.' || tg_table_name ||
            ');';
  END IF;
  select format('INSERT INTO %s values ($1.*)',tg_relname||'.'||partition_name) into sql;
  -- Propagate insert
  EXECUTE sql USING NEW;
  RETURN new;
END;
$$;


CREATE OR REPLACE FUNCTION delete_parent_row()
  RETURNS TRIGGER AS
$BODY$
DECLARE
BEGIN
  delete from only audit.audit where id = NEW.ID;
  RETURN null;
END;
$BODY$
  LANGUAGE plpgsql;