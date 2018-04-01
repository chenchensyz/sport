SELECT
	s1.user_id,
	s1.entry_id,
  s3.entry_title,
	s1.create_time,
	s1.info,
  s2.entry_rank,
	s2.choose
FROM
	(
		(
			SELECT
                                id,
				user_id,
				entry_id,
				create_time,
				GROUP_CONCAT(
					param_key,
					":",
					param_value SEPARATOR '|'
				) AS info
			FROM
				t_act_entry_result e1 where entry_id ='${entryId}'
			GROUP BY
				user_id 
		) s1,
		(
			SELECT
				t1.user_id,
				entry_id,
				entry_rank,
				GROUP_CONCAT(t1.title) AS choose
			FROM
				(
					SELECT
						ent_c.title,
						res_c.entry_id,
						res_c.user_id,
            res_c.entry_rank
					FROM
						t_act_entry_result_choose res_c,
						t_act_entry_choose ent_c
					WHERE
						res_c.choose = ent_c.id and res_c.entry_id = '${entryId}'
				) t1
			GROUP BY
				user_id  
				
		) s2,t_act_entry s3 
	)
WHERE
	s1.user_id = s2.user_id and s3.id=s1.entry_id
order by entry_rank