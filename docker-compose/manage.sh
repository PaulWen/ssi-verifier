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
