INSERT INTO 
	t_act_prize_result(
	id,
	prize_id,
	user_id,
	param_key,
	param_value,
	create_time
	)
SELECT
	upper(REPLACE(uuid(), '-', '')),
	:pojo.prizeId,
	:pojo.userId,
	:pojo.paramKey,
	:pojo.paramValue,
	:pojo.createTime
	
FROM DUAL WHERE
	NOT EXISTS (
		SELECT
			1
		FROM
			t_act_prize_result t
		WHERE
			t.prize_id = :pojo.prizeId
		AND
			t.user_id = :pojo.userId
		AND
			t.param_key = :pojo.paramKey
		AND
			t.param_value = :pojo.paramValue
	)
