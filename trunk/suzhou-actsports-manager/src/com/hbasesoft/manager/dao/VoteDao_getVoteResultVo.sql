SELECT
	s1.user_id,
	s1.vote_id,
  s3.vote_title,
	s1.create_time,
	s1.info,
	s2.choose
FROM
	(
		(
			SELECT
				user_id,
				vote_id,
				create_time,
				GROUP_CONCAT(
					param_key,
					":",
					param_value SEPARATOR '|'
				) AS info
			FROM
				t_act_vote_result e1 where vote_id ='${voteId}'
			GROUP BY
				user_id 
		) s1,
		(
			SELECT
				t1.user_id,
				vote_id,
				GROUP_CONCAT(t1.title,":",user_vote_num SEPARATOR '|') AS choose
			FROM
				(
					SELECT
						vot_c.title,
						res_c.vote_id,
						res_c.user_id, 
            COUNT(res_c.choose) user_vote_num
					FROM
						t_act_vote_result_choose res_c,
						t_act_vote_choose vot_c
					WHERE
						res_c.choose = vot_c.id and res_c.vote_id = '${voteId}' group by choose,user_id
				) t1
			GROUP BY
				user_id  
				
		) s2,t_act_vote s3 
	)
WHERE
	s1.user_id = s2.user_id and s3.id=s1.vote_id

 order by s1.create_time
