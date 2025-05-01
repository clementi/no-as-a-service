(ns naas
  (:require
   [muuntaja.core :as m]
   [reitit.coercion.spec]
   [reitit.ring :as ring]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring.middleware.parameters :as parameters]
   [reitit.ring.coercion :as rrc]
   [ring.adapter.jetty :as jetty]
   [clojure.data.json :as json]))

(def reasons (json/read-str (slurp "./reasons.json")))

(def app
  (ring/ring-handler
   (ring/router
    ["/api"
     ["/no" {:get {:responses {200 {:body {:reason string?}}}
                   :handler (fn [_]
                              {:status 200
                               :body {:reason (rand-nth reasons)}})}}]]
    {:data {:coercion reitit.coercion.spec/coercion
            :muuntaja m/instance
            :middleware [parameters/parameters-middleware
                         rrc/coerce-request-middleware
                         muuntaja/format-response-middleware
                         rrc/coerce-response-middleware]}})))

(defn -main []
  (jetty/run-jetty app {:port 3000 :join? false}))