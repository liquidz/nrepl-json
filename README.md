# nrepl-json

![ALPHA VERSION](https://img.shields.io/badge/version-ALPHA-red.svg)

nREPL JSON Transport

## Usage

```clj
(require '[nrepl-json.core :as njson]
         '[nrepl.server :as server])

(server/start-server :port 12345 :transport-fn njson/json)
```

## License

Copyright © 2018 [Masashi Iizuka](https://twitter.com/uochan)

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
