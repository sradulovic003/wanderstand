(defproject wanderstand "0.1.0-SNAPSHOT"
  :description "A platform connecting vendors, market organizers, and visitors"
  :url "https://github.com/sradulovic003/wanderstand"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.12.2"]]
  :profiles {:dev {:dependencies [[midje "1.10.10"]]
                   :plugins [[lein-midje "3.2.1"]]}}
  :repl-options {:init-ns wanderstand.core})