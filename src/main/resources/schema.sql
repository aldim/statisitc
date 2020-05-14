DROP PROCEDURE IF EXISTS regular_users_for_period ^;

CREATE PROCEDURE regular_users_for_period (startDate date, endDate date)
BEGIN
	SELECT customer FROM
	(SELECT user_id as customer
	    FROM webstatistics.visit as vst
	    WHERE (vst.date >= startDate AND vst.date <= endDate)
	    GROUP BY user_id, page_id) as customers
	GROUP BY customer
	HAVING count(*) >= 10;
END^;

