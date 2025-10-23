FROM liquibase/liquibase:latest

COPY build/your-entities-lib-1.0.0.jar /liquibase/lib/your-lib.jar
COPY drivers/postgresql-42.7.4.jar    /liquibase/lib/postgresql.jar

ENV LIQUIBASE_CLASSPATH=/liquibase/lib/*

ENV LIQUIBASE_CHANGELOG_FILE=db/changelog/db.changelog-master.yaml

CMD ["update"]