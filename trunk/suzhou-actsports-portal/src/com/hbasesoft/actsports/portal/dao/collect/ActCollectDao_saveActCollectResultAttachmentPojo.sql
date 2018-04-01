INSERT INTO 
	t_act_collect_result_attachment(
	id,
	create_time,
	collect_id,
	user_id,
	image_path
	)
SELECT
	upper(REPLACE(uuid(), '-', '')),
	:pojo.createTime,
	:pojo.collectId,
	:pojo.userId,
	:pojo.imagePath
	
FROM DUAL WHERE
	NOT EXISTS (
		SELECT
			1
		FROM
			t_act_collect_result_attachment t
		WHERE
			t.collect_id = :pojo.collectId
		AND
			t.user_id = :pojo.userId
		AND
			t.image_path = :pojo.imagePath
	)

	
