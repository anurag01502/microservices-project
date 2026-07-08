CREATE EVENT delete_unverified_users
ON SCHEDULE EVERY 1 HOUR
DO
DELETE FROM users
WHERE is_verified = FALSE
  AND created_at <= DATE_SUB(NOW(), INTERVAL 24 HOUR);