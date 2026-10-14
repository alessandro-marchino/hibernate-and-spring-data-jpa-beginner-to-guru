# Hibernate and Spring Data JPA: Beginner to Guru

Companion repository for the
[Hibernate and Spring Data JPA: Beginner to Guru](https://www.udemy.com/course/hibernate-and-spring-data-jpa-beginner-to-guru)
Udemy course.

## Liquibase

Command to generate changelog:

```bash
mvn liquibase:generateChangeLog \
  -Dliquibase.url="$JDBC_URL" \
  -Dliquibase.username="$JDBC_USER" \
  -Dliquibase.password="$JDBC_PASS" \
  -Dliquibase.outputChangeLogFile=wawa.yml
```
