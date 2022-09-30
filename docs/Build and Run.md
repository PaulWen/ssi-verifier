# Build and Run Docker Container

## Build and Run Docker Container Locally

```
docker build -t ssi-verifier .
 
docker run -p 8888:8080 --name ssi-verifier ssi-verifier
```

## Run Pre-Build Docker Container

```
docker run -p 8888:8080 --name ssi-verifier ghcr.io/paulwen/ssi-verifier:latest
```

# Run Locally for Development

- Activate the spring profile `local` to ensure that Thymeleaf templates are not
  cached
- Ensure that your application is rebuild after saving (e.g. using Save Actions
  for IntelliJ) to ensure that changes take effect immediately without requiring
  a manual restart of the application
- Add the webhook URL when starting the
  AcaPy `--webhook-url 'http://host.docker.internal:8888/api/acapy-webhook#secret-key' \`

# Docker Compose Deployment

The [docker-compose deployment](../docker-compose) can be used to deploy the SSI-Verifier app.

## Configuration

The [.env.example](../docker-compose/.env.example) lists all the configuration options available.
Before starting the docker-compose deployment a custom `.env` file needs to be created.

## Start and Stop

```
cd ./docker-compose
./manage.sh start
./manage.sh stop
./manage.sh down
```

