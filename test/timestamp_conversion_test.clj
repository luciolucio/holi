(ns timestamp-conversion-test
  (:require [clojure.test :as ct]
            [luciolucio.holi.core :as core]
            [luciolucio.holi.file :as file]
            [tick.core :as t]))

(ct/deftest should-convert-to-and-back-from-timestamp-consistently
  (ct/is (= (t/date "2020-10-10")
            (core/timestamp->date (file/date->timestamp (t/date "2020-10-10")))))

  (ct/is (= 29505
            (file/date->timestamp (core/timestamp->date 29505)))))

(ct/deftest should-encode-and-decode-consistenly
  (ct/is (= (t/date "2020-10-10")
            (core/parse-date (file/encode-holiday {:date (t/date "2020-10-10") :name "a"}
                                                  {"a" "0A"}))))

  (ct/is (= {:date (t/date "2020-10-10") :name "a"}
            (core/parse-date-with-holiday (file/encode-holiday {:date (t/date "2020-10-10") :name "a"}
                                                               {"a" "0A"})
                                          {"0A" "a"}))))
