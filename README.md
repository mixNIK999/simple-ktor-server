# simple-ktor-server

## Build & Run
`./gradlew run --args="--port INT"`

## Server api

* `GET /gen_string?length={len}` generates and returns random string of length `len > 0`
* `GET /gen_int?left={l}&right={r}` generates and returns random int in range `[l, r)`. `l` mast be less then `r`
* `GET /requests.log` returns log file with timestamp and generated strings/ints