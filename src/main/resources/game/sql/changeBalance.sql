UPDATE USERS
SET balance = balance + ?
WHERE username IS ?
AND balance >= ?;
