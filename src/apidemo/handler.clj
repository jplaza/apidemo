(ns apidemo.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :as route]
            [ring.util.response :refer [response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-response
                                          wrap-json-body]]
            [clojure.pprint :as pp]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (POST "/hello" req
    (pp/pprint (:body req))
    (response {:hello "JSON"}))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
      (wrap-json-body {:keywords? true :bigdecimals? true})
      (wrap-json-response)))
