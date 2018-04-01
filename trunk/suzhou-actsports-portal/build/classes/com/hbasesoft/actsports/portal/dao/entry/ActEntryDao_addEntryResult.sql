INSERT INTO 
	t_act_entry_result(
	id,
	entry_id,
	user_id,
	param_key,
	param_value,
	create_time
	)
SELECT
	upper(REPLACE(uuid(), '-', '')),
	:pojo.entryId,
	:pojo.userId,
	:pojo.paramKey,
	:pojo.paramValue,
	:pojo.createTime
	
FROM DUAL WHERE
	NOT EXISTS (
		SELECT
			*
		FROM
			t_act_entry_result t
		WHERE
			t.param_key = :pojo.paramKey
		AND
			t.param_value = :pojo.paramValue
		AND
			t.user_id = :pojo.userId
	)

	
