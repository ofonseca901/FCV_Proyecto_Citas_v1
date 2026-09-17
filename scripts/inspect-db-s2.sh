#!/bin/sh
set -eu
# Run inside the existing MySQL container. Never echo passwords.
export MYSQL_PWD="$MYSQL_PASSWORD"
mysql -u"$MYSQL_USER" --batch --skip-column-names -e 'SHOW DATABASES; SHOW GRANTS FOR CURRENT_USER; SELECT TABLE_SCHEMA,TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA = "citas_fcv_training";'
exit 0
