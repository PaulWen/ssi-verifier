# SSI Verifier

A simple SSI verifier app that integrates with the Lissi Agent.

## Run Locally

- activate the spring profile `local` to ensure that Thymeleaf templates are not
  cached
- ensure that your application is rebuild after saving (e.g. using Save Actions
  for IntelliJ) to ensure that changes take effect immediately without requiring
  a manual restart of the application
- add the webhook URL when starting the
  AcaPy `--webhook-url 'http://host.docker.internal:8888/api/acapy-webhook' \`
