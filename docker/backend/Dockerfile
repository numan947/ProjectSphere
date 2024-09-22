# Build the backend application
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests


# Runtime stage
FROM amazoncorretto:22.0.2-alpine3.20

## Set the default values for the environment variables so it's easier to debug
## THESE CAN BE REGULAR ENV VARIABLES
ARG APP_NAME=missing_app_name
ARG APP_VERSION=missing_version
ARG PORT=missing_port

ENV JAR_NAME=${APP_NAME}
ENV VERSION=${APP_VERSION}

ENV PROFILE=missing_profile
ENV APP_PORT=${PORT}

## THESE SHOULD BE SECRET ENV VARIABLES
# Database
ENV DB_URL=missing_db_url
ENV DB_USERNAME=missing_db_username
ENV DB_PASSWORD=missing_db_password
# Email
ENV EMAIL_HOSTNAME=missing_host_name
ENV EMAIL_USERNAME=missing_host_name
ENV EMAIL_PASSWORD=missing_host_name
ENV EMAIL_PORT=missing_host_name
ENV MAIL_SENDER_EMAIL=missing_mail_sender_email
# JWT
ENV JWT_SECRET=missing_jwt_secret
ENV JWT_EXPIRATION=missing_jwt_expiration
# Account activation and password reset
ENV ACTIVATION_URL=missing_activation_url
ENV ACTIVATION_CODE_LENGTH=missing_activation_code_length
ENV ACTIVATION_CODE_EXPIRATION=missing_activation_code_expiration
ENV ACTIVATION_CODE_CHARACTERS=missing_activation_code_characters
ENV RESET_URL=missing_reset_url
# Cors - comma separated list of origins
ENV CORS_ORIGINS=missing_cors_origins


WORKDIR /app
COPY --from=build /build/target/${JAR_NAME}-${VERSION}.jar /app/

EXPOSE ${APP_PORT}
CMD java -jar -Dspring.profiles.active=${PROFILE} -Dspring.datasource.url=${DATABASE_URL}  ${JAR_NAME}-${VERSION}.jar