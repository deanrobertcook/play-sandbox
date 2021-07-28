## MySQL 5.6 with Percona Xtrabackup

## Pull the mysql:5.6 image
FROM mysql:5.6

VOLUME ["/etc/mysql", "/var/lib/mysql"]
ADD employees.sql /tmp/employees.sql

# Install requirement (wget)
RUN apt-get update && apt-get install -y wget

RUN /bin/bash -c "/usr/bin/mysqld_safe --skip-grant-tables &" && \
  sleep 5 && \
  mysql -u root -e "CREATE DATABASE mydb" && \
  mysql -u root mydb < /tmp/employees.sql