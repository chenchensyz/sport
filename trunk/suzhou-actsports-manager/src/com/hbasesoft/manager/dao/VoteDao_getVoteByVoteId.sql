SELECT
	vot_c.vote_id,
	GROUP_CONCAT(
		title,
		":",
		vote_num SEPARATOR '|'
	) AS choose
FROM
	t_act_vote_choose vot_c
WHERE
	vot_c.vote_id = '${voteId}'