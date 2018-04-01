INSERT INTO 
	t_act_collect_result(
	id,
	collect_id,
	user_id,
	param_key,
	param_value,
	create_time
	)
SELECT
	upper(REPLACE(uuid(), '-', '')),
	:pojo.collectId,
	:pojo.userId,
	:pojo.paramKey,
	:pojo.paramValue,
	:pojo.createTime
	
FROM DUAL WHERE
	NOT EXISTS (
		SELECT
			1
		FROM
			t_act_collect_result t
		WHERE
			t.collect_id = :pojo.collectId
		AND
			t.user_id = :pojo.userId
		AND
			t.param_key = :pojo.paramKey
		AND
			t.param_value = :pojo.paramValue
	)

	
