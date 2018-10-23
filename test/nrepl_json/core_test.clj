(ns nrepl-json.core-test
  (:require [clojure.test :as t]
            [nrepl-json.core :as sut]
            [nrepl.core :as nrepl]
            [nrepl.server :as server]))

(def ^:dynamic *server* nil)

(defn repl-server-fixture
  [f]
  (with-open [server (server/start-server :transport-fn sut/json)]
    (binding [*server* server]
      (f))))

(t/use-fixtures :each repl-server-fixture)

(defn- ensure-map [x]
  (cond
    (sequential? x) (apply merge x)
    :else x))

(t/deftest fixme-test
  (with-open [transport (nrepl/connect :port (:port *server*) :transport-fn sut/json)]
    (let [client (nrepl/client transport Long/MAX_VALUE)
          response (ensure-map (nrepl/message client {:op "clone"}))]
      (t/is (= (:status response) ["done"]))
      (t/is (string? (:new-session response))))))




