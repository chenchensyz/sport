SELECT
	s1.user_id,
	s1.prize_id,
  s3.prize_title,
	s1.create_time,
	s1.info,
	s2.choose
FROM
	(
		(
			SELECT
				user_id,
				prize_id,
				create_time,
				GROUP_CONCAT(
					param_key,
					":",
					param_value SEPARATOR '|'
				) AS info
			FROM
				t_act_prize_result e1 where prize_id = '${prizeId}'
			GROUP BY
				user_id 
		) s1,
		(
			SELECT
				t1.user_id,
				prize_id,
				GROUP_CONCAT(t1.title) AS choose
			FROM
				(
					SELECT
						ent_c.title,
						res_c.prize_id,
						res_c.user_id
					FROM
						t_act_prize_result_choose res_c,
						t_act_prize_choose ent_c
					WHERE
						res_c.choose = ent_c.id and res_c.prize_id ='${prizeId}'
				) t1
			GROUP BY
				user_id  
				
		) s2,t_act_prize s3 
	)
WHERE
	s1.user_id = s2.user_id and s3.id=s1.prize_id
order by s1.create_time