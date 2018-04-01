INSERT INTO 
	t_act_entry_result_choose(
	id,
	entry_id,
	user_id,
	choose,
	create_time,
	entry_rank
	)
SELECT
	upper(REPLACE(uuid(), '-', '')),
	:pojo.entryId,
	:pojo.userId,
	:pojo.choose,
	:pojo.createTime,
	:pojo.entryRank
	
FROM DUAL WHERE
	NOT EXISTS (
		SELECT
			user_id
		FROM
			t_act_entry_result_choose t
		WHERE
			t.user_id = :pojo.userId
		AND
			t.entry_id = :pojo.entryId
		AND
			t.choose= :pojo.choose
	)

	
