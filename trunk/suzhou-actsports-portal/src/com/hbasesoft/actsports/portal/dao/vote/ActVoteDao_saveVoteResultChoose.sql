INSERT INTO
	t_act_vote_result_choose(
	id,
	user_id,
	choose,
	create_time,
	vote_id
	)
SELECT
	upper(REPLACE(uuid(), '-', '')),
	:pojo.userId,
	:pojo.choose,
	:pojo.createTime,
	:pojo.voteId
FROM DUAL WHERE
	NOT EXISTS (
		SELECT
			user_id
		FROM
			t_act_vote_result_choose t
		WHERE
			t.user_id = :pojo.userId
		AND
			t.vote_id = :pojo.voteId
		AND
			t.choose = :pojo.choose
	)



	
