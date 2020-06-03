CREATE OR REPLACE FUNCTION callback_for_audit(params jsonb)
RETURNS VOID AS
$$
DECLARE
 range_min timestamp;
 new_relname text;
 field_id text;
BEGIN
 range_min := (params->>'range_min')::timestamp;
 field_id := 'audit_type_id';
 -- generate new name for partition based
 -- on its parent name and lower bound
 new_relname := format('%s_%s', params->>'parent', to_char(range_min, 'YYYY_MM'));
 -- rename partition
 IF EXISTS(SELECT FROM pg_catalog.pg_tables WHERE schemaname = params->>'partition_schema' AND tablename = new_relname) THEN
    new_relname := format('%s_%s_%s', params->>'parent', to_char(range_min, 'YYYY'), lpad((to_char(range_min, 'MM')::integer + 1)::text, 2, '0'));
 END IF;
 EXECUTE format('ALTER TABLE %s.%s RENAME TO %s', params->>'partition_schema', params->>'partition', new_relname);
 --add multilevel partitioning
 PERFORM create_range_partitions(concat(params->>'partition_schema', '.', new_relname), field_id, 1, 1, 10);
 PERFORM add_range_partition(concat(params->>'partition_schema', '.', new_relname), 11, NULL, concat(params->>'partition_schema', '.', new_relname, '_default'));
END
$$ LANGUAGE plpgsql;