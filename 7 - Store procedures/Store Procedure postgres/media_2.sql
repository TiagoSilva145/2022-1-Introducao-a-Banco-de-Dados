CREATE PROCEDURE media_2 (x1 int, x2 int, OUT media FLOAT)
LANGUAGE SQL

BEGIN ATOMIC

   SET media = (x1+x2)/2;

END;

