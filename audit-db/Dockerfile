ARG DOCKER_REGISTRY
FROM ${DOCKER_REGISTRY}/postgres:12

COPY docker-entrypoint-initdb.d /docker-entrypoint-initdb.d

EXPOSE 5432