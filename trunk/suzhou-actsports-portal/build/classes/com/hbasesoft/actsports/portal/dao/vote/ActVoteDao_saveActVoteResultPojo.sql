INSERT INTO
	t_act_vote_result(
	id,
	user_id,
	param_key,
	param_value,
	create_time,
	vote_id
	)
SELECT
	upper(REPLACE(uuid(), '-', '')),
	:pojo.userId,
	:pojo.paramKey,
	:pojo.paramValue,
	:pojo.createTime,
	:pojo.voteId
FROM DUAL WHERE
	NOT EXISTS (
		SELECT
			1
		FROM
			t_act_vote_result t
		WHERE
			t.user_id = :pojo.userId
		AND
			t.param_key = :pojo.paramKey
		AND
			t.param_value = :pojo.paramValue
	)



	
