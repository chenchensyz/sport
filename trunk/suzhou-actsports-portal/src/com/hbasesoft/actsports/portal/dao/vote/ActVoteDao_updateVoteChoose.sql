UPDATE 
	t_act_vote_choose c
SET
	vote_num = :pojo.voteNum
WHERE
	c.id = :pojo.id
	AND
	c.vote_id = :pojo.voteId
	AND
	NOT EXISTS (
		SELECT
			1
		FROM
			t_act_vote_result_choose t
		WHERE
			t.user_id = :userId
		AND
			t.vote_id = :pojo.voteId
		AND
			t.choose = :pojo.id
	)



	
