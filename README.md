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

## Technical Debt

- AcaPy Webhook Endpoint muss geschützt werden, da ein Angreifer ansonsten AcaPy
  imitieren kann und uns falsche Daten schicken kann - nur der AcaPy sollte die
  Webhooks aufrufen können!!
- WebSockets mit authorization Header absichern, damit nicht jeder subscriben
  kann
- Socket stream html element sollte nur eine Connection aufbauen und dann zu
  vielen topics subscriben anstatt jedes Mal eine neue Connection aufzubauen
- store proof results in-memory and offer a reload button in case the WebSocket
  message got lost

## Next Steps

- only sent the verification result
- also sent the proof claims
- replace the QR Code with the result
- make everything look nice and be responsive using a UI Library

- introduce a proof template overview page where the client can choose the proof
  template from he wants to request connectionless
- show images to each proof template
