INSERT INTO 
	t_act_collect_result_idea(
	id,
	collect_id,
	user_id,
	idea,
	create_time
	)
SELECT
	upper(REPLACE(uuid(), '-', '')),
	:pojo.collectId,
	:pojo.userId,
	:pojo.idea,
	:pojo.createTime
	
FROM DUAL WHERE
	NOT EXISTS (
		SELECT
			1
		FROM
			t_act_collect_result_idea t
		WHERE
			t.collect_id = :pojo.collectId
		AND
			t.user_id = :pojo.userId
		AND
			t.idea = :pojo.idea
	)

	
