SELECT
	u.id,
	u.browser,
	u.count,
	u.create_time,
	u.default_org_id,
	u.ip,
	u.last_login_time,
	u.state
FROM
	t_act_user u,
	t_act_user_account ua
WHERE
	u.ID = ua.user_id
AND ua.state = 'A'
AND ua.type = :type
AND ua.account = :account