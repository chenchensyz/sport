SELECT
	s1.user_id,
	s1.collect_id  as id,
  s2.collect_title,
	s1.create_time,
	s1.info,
        s2.idea
FROM
	(
		(
			SELECT
				user_id,
				collect_id,
				create_time,
				GROUP_CONCAT(
					param_key,
					":",
					param_value SEPARATOR '|'
				) AS info
			FROM
				t_act_collect_result e1 where collect_id ='${collectId}' 
			GROUP BY
				user_id 
		) s1,
		(
			 SELECT
						c2.collect_title,
						c1.collect_id,
						c1.user_id, 
   				                c1.idea,
						c2.create_date
					FROM
						t_act_collect_result_idea c1,
            t_act_collect c2
					WHERE
						c1.collect_id = c2.id and c1.collect_id = '${collectId}'
		) s2
	)
WHERE
	s1.user_id = s2.user_id and s1.collect_id=s2.collect_id

order by s2.create_date
 
