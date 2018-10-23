(ns user
  (:require [nrepl-json.core :as core]
            [nrepl.server :as server]))

(defonce ^:private serv (atom nil))

(defn start []
  (when-not @serv
    (println "Starting server")
    (reset! serv (server/start-server :port 12345
                                      :transport-fn core/json))))

(defn stop []
  (when @serv
    (println "Stopping server")
    (server/stop-server @serv)
    (reset! serv nil)))

(defn go []
  (stop)
  (start))

(defn reset []
  (println "Resetting server")
  (require '[nrepl-json.core :as core] :reload-all)
  (go))
