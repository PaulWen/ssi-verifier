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

## Next Steps

- dynamically connect to any Lissi Agent instance by providing the domain of the
  Lissi Agent
- implement the Logout button
- integrate an URL shortener

- store proof request results to an in-memory database
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

