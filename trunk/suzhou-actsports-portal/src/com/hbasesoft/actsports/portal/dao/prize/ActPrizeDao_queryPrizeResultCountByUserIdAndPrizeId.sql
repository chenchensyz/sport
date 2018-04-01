SELECT 
	id,
	prize_id,
	user_id,
	count
FROM
	t_act_prize_result_count
WHERE
	user_id = :userId	
    AND
	prize_id = :prizeId



	
