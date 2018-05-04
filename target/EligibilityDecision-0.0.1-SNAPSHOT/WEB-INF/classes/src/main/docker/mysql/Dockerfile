FROM fedora
MAINTAINER http://fedoraproject.org/wiki/Cloud

RUN dnf -y update && dnf clean all
RUN dnf -y install community-mysql-server community-mysql pwgen supervisor bash-completion psmisc net-tools && dnf clean all

ADD ./start.sh /start.sh
ADD ./config_mysql.sh /config_mysql.sh
ADD ./supervisord.conf /etc/supervisord.conf

# RUN echo %sudo	ALL=NOPASSWD: ALL >> /etc/sudoers

RUN chmod 755 /start.sh
RUN chmod 755 /config_mysql.sh
RUN /config_mysql.sh

CMD ["/bin/bash", "/start.sh"]ENV MYSQL_ROOT_PASSWORD="bigsecret"

ADD createDb.sql /tmp/createDb.sql

RUN "mysql -u root --password="bigsecret" < /tmp/createDb.sql"

EXPOSE 3306