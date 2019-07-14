CREATE OR REPLACE FUNCTION create_database_for_existing_data() RETURNS integer AS $$
declare
  date record;
  sql TEXT;
BEGIN
  FOR date IN
    select DISTINCT to_char(event_date,'YYYY_MM') as partition_name,
                    to_char((select date_trunc('month', event_date)), 'YYYY-MM-dd') as start_date,
                    to_char((select date_trunc('month', event_date) + interval '1 month'), 'YYYY-MM-dd') as end_date from audit.audit
    LOOP
      raise notice 'create table';
      select format('CREATE TABLE %s PARTITION OF %s FOR VALUES FROM (''' ||'%s'||''') TO (''' ||'%s'||''')', 'audit.audit_' || date.partition_name, 'audit.audit_test', date.start_date, date.end_date) into sql;
      EXECUTE sql;
    END LOOP;
  return 1;
END;
$$ LANGUAGE plpgsql;