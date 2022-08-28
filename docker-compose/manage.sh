#!/usr/bin/env bash

# getting script path
SCRIPT_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

# export environment variables from .env
export $(grep -v '^#' $SCRIPT_HOME/.env | xargs)

# =================================================================================================================
# Usage:
# -----------------------------------------------------------------------------------------------------------------
usage() {
  cat <<-EOF

      Usage: $0 [command]

      Commands:

      start - Starts the system based on the docker-compose files
              and configuration supplied in the .env.

      restart - First, "down" is executed. Then, "start" is run.

      down - Stops all containers and removes all volumes.

EOF
  exit 1
}

function start() {
  if [ "${ACAPY_LEDGER_NAME}" = lissi-test ]; then
    echo "Generating random DID seed and registering DID to ledger..."
    ACAPY_DID_SEED=$(cat /dev/urandom | base64 | tr -dc 'a-zA-Z0-9' | fold -w 32 | head -n 1)
    export ACAPY_DID_SEED_SETTING="--seed ${ACAPY_DID_SEED}"

    curl -d "{\"role\": \"ENDORSER\", \"seed\":\"$ACAPY_DID_SEED\"}" -H "Content-Type: application/json" -X POST http://test-ledger.lissi.id/register
  fi

  echo "Starting SSI-Verifier System ..."
  docker-compose -f $SCRIPT_HOME/docker-compose.yml up -d
}


function down() {
  echo "Stopping all containers and removing all volumes ..."
  docker-compose -f $SCRIPT_HOME/docker-compose.yml down -v --remove-orphans
}

if [ $# -lt 1 ]; then
  echo 1>&2 "Error: not enough arguments"
  usage
  exit 2
elif [ $# -gt 1 ]; then
  echo 1>&2 "Error: too many arguments"
  usage
  exit 2
fi

case "${1}" in
start)
  start
  ;;
restart)
  down
  start
  ;;
down)
  down
  ;;
*)
  echo 1>&2 "Error: unknown command"
  usage
  ;;
esac
