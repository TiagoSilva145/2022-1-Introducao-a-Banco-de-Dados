DELIMITER //

CREATE PROCEDURE inc_budget ()

BEGIN

	DECLARE media float;
    DECLARE contagem int;
    DECLARE abaixo int;
    
    SELECT avg(d.budget) INTO media
    FROM department as d;
    
    SELECT count(*) INTO contagem
    FROM department as d;
    
    SELECT count(*) INTO abaixo
    FROM deparment as d
    WHERE d.budget < media;
    
    IF abaixo > contagem * 0.1 THEN
    
		UPDATE department
		SET budget = budget * 1.10
		WHERE budget <= media;
	END IF;

END; //

DELIMITER ;
