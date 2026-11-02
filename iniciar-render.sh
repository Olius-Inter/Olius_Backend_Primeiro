#!/bin/sh
set -eu

# O Render informa a porta usada pelo servico.
porta="${PORT:-10000}"
case "$porta" in
    ''|*[!0-9]*) echo "PORT deve ser um numero." >&2; exit 1 ;;
esac
sed -i "s/port=\"8080\"/port=\"$porta\"/" "$CATALINA_HOME/conf/server.xml"
exec catalina.sh run
