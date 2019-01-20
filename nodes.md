## MVCC in Postgresql

1. Create some table and insert few rows
```sql
CREATE TABLE data(id int, value int);
INSERT INTO data VALUES(1, 100);
INSERT INTO data VALUES(2, 200);
```

2. View current transaction ID
```sql
SELECT txid_current();
```

3. View xmin and xmax of rows
```sql
SELECT xmin, xmax, * FROM data;
```

4. READ COMMITTED isolation level

```sql
BEGIN;
UPDATE data SET value=300 WHERE id=2;
COMMIT;
```

```sql
BEGIN;
SELECT xmin, xmax, * FROM data;
SELECT xmin, xmax, * FROM data;
COMMIT;
```

```sql
BEGIN;
UPDATE data SET value=400 WHERE id=2;
SELECT xmin, xmax, * FROM data;
COMMIT;
```

```sql
BEGIN;
INSERT INTO data VALUES(3, 1000);
UPDATE data SET value=500 WHERE id=2;
SELECT xmin, xmax, * FROM data;
COMMIT;
```

5. REPEATABLE READ isolation level
```sql
BEGIN TRANSACTION ISOLATION LEVEL REPEATABLE READ;
UPDATE data SET value=300 WHERE id=2;
COMMIT;
```

```sql
BEGIN TRANSACTION ISOLATION LEVEL REPEATABLE READ;
SELECT xmin, xmax, * FROM data;
SELECT xmin, xmax, * FROM data;
COMMIT;
```

6. SERIALIZABLE isolation level
```sql
BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
UPDATE data SET value=400 WHERE id=2;
SELECT xmin, xmax, * FROM data;
COMMIT;
```

```sql
BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
INSERT INTO data VALUES(3, 1000);
UPDATE data SET value=500 WHERE id=2;
SELECT xmin, xmax, * FROM data;
COMMIT;
```

7. VACUUM

```sql
VACUUM FULL ANALYZE VERBOSE data;
```