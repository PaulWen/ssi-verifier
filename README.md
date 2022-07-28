# SSI Verifier

A simple SSI verifier app that integrates with the Lissi Agent.

## Run Locally

- activate the spring profile `local` to ensure that Thymeleaf templates are not
  cached
- ensure that your application is rebuild after saving (e.g. using Save Actions
  for IntelliJ) to ensure that changes take effect immediately without requiring
  a manual restart of the application
- add the webhook URL when starting the
  AcaPy `--webhook-url 'http://host.docker.internal:8888/api/acapy-webhook#secret-key' \`

## Build and Run Docker Container

### Build and Run Docker Container Locally

```
docker build -t ssi-verifier .
 
docker run -p 8888:8080 --name ssi-verifier ssi-verifier
```

### Run Pre-Build Docker Container

```
docker run -p 8888:8080 --name ssi-verifier ghcr.io/paulwen/ssi-verifier:latest
```

## Helm Deployment

The [Helm chart](./k8s) can be used to deploy the SSI-Verifier app to a K8s
cluster.

### Configuration

As outlined in the [values.yaml](./k8s/values.yaml) the following configuration
is required:

- `ssiVerifier.keycloakUrl`: URL to the Keycloak to authenticate the user and
  get access to the API of the Lissi Agent instance (e.g.
  "https://<DOMAIN>/auth")
- `ssiVerifier.lissiAgentApiUrl`: URL to the API of the Lissi Agent instance
  (e.g.
  "https:/<DOMAIN>/ctrl/api/v1.0")
- `ssiVerifier.tenantId`: The ID of the tenant of the Lissi Agent instance
  (e.g. "default_tenant")
- `ssiVerifier.k8sSecretRef`: The name of the K8s secret - see below  (e.g.
  "ssi-verifier")

### Secret

- `webhookApiKey`: The API key used to authenticate the webhook calls from the
  AcaPy instance

## Release Process

1. Update version in [pom.xml](./pom.xml)
1. Update app version in [Chart.yaml](./k8s/Chart.yaml)
2. Add a tag `X.X.X`
3. The [GitHub Action](./.github/workflows/docker-publish.yml) will
   automatically build and publish a new docker image

## Technical Debt

- WebSockets mit authorization Header absichern, damit nicht jeder subscriben
  kann -> not super critical as clients need to subscribe to changes for
  particular proof exchange IDs and those are hard to guess
- Socket stream html element sollte nur eine Connection aufbauen und dann zu
  vielen topics subscriben anstatt jedes Mal eine neue Connection aufzubauen

## Next Steps

- introduce a default error page
- integrate an URL shortener
- investigate why a user needs to log-in twice after logging out
- WebSocket connection is being lost sometimes and no proof request results are being delivered - maybe the WebSocket HTTP connection times out after one minute or less

- store proof request results to an in-memory database to not only share the result via a WebSocket event but also after refreshing the page
- clean up the database every 15 minutes and all database entries expire after
  15 minutes
- selecting a proof request template generates a new connectionless proof
  request and redirects the user to that (proof exchange ID instead of proof
  request template ID is shown in the URL bar)
    - allows to reload the page to see if an result already arrived (e.g. in
      case the Websocket connection failed)
    - ensures that no green tick is rendered temporarily when the same proof
      template is requested twice and the green tick is still cached
- show a refresh button below the QR Code

- render data URL claim values as links

## Out of Scope

- dynamically connect to any Lissi Agent instance by providing the domain of the
  Lissi Agent
    - comes along with various security concerns as the same verifier instance
      suddenly handels data from many SSI agents
    - to ensure proper data isolation a new SSI verifier instance should be
      deployed for each Lissi Agent instance
