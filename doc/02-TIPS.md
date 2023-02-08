# Tips and tricks

## "Same or next" business day

Use `n = 0` to get _same or next_ semantics, that is:

- If the date is a business day already, just return the same date
- Otherwise it's as if you had used `n = 1`

```clojure
(ns my-app
  (:require [luciolucio.holi :as holi]
    [tick.core :as t]))

(holi/add (t/date "2022-07-06") 0 :business-days) ; -> 2022-07-08, because 2022-07-06 is a Saturday
(holi/add (t/date "2022-07-07") 0 :business-days) ; -> 2022-07-08, because 2022-07-07 is a Sunday
(holi/add (t/date "2022-07-08") 0 :business-days) ; -> 2022-07-08, as it's a regular Monday
```

This is useful in cases where you have something that relies on the day of the month, but want to avoid weekends. For
example: an invoice due date is every 10th of the month. If it's a weekend, then it's due on the next business day.

```clojure
(-> (t/date "2022-06-06")
    (holi/add 1 :months)         ; 2022-07-06, Sat
    (holi/add 0 :business-days)) ; 2022-07-08, Mon
```
