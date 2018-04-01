SELECT
	*
FROM
	weixin_release
WHERE
	position != '1'
	AND code = :templateCode
GROUP BY
	father_id
UNION
	SELECT
		*
	FROM
		weixin_release
	WHERE
		position = '1'
	AND code = :templateCode
