(ns nrepl-json.core
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]
            [nrepl.transport :as transport])
  (:import java.io.EOFException
           [java.net Socket SocketException]))

;; NOTE: Copy from nrepl.transport
;;       https://github.com/nrepl/nrepl/blob/96eda167a8bd6b2ad7f10dd71efd3e57984831e2/src/clojure/nrepl/transport.clj#L75-L84
(defmacro ^{:private true} rethrow-on-disconnection
  [^Socket s & body]
  `(try
     ~@body
     (catch EOFException e#
       (throw (SocketException. "The transport's socket appears to have lost its connection to the nREPL server")))
     (catch Throwable e#
       (if (and ~s (not (.isConnected ~s)))
         (throw (SocketException. "The transport's socket appears to have lost its connection to the nREPL server"))
         (throw e#)))))

(defn json
  ([^Socket s] (json s s s))
  ([in out & [^Socket s]]
   (let [in (io/reader in)
         out (io/writer out)]
     (transport/fn-transport
      #(rethrow-on-disconnection s (json/read in))
      #(rethrow-on-disconnection s (locking out
                                     (json/write % out)
                                     (.flush out)))
      (fn []
        (if s
          (.close s)
          (do (.close in)
              (.close out))))))))
