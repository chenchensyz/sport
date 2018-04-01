SELECT 
	COUNT(distinct(user_id))
FROM
	t_act_entry_result_choose ec
WHERE
	ec.entry_id = :entryId

	
