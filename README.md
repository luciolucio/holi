# Calenjars
![build](https://github.com/piposaude/calenjars/actions/workflows/pull-request-checks.yml/badge.svg)
[![Clojars Project](https://img.shields.io/clojars/v/piposaude/calenjars.svg)](https://clojars.org/piposaude/calenjars)

Calenjars is a clojure jar generator, designed to create libraries for date
arithmetics around non-business days, that you define yourself with a simple DSL.

## Usage

Create a new clojure deps project:

```
curl -LO https://raw.githubusercontent.com/piposaude/calenjars/0.5.0/new-calenjars-project.sh
bash new-calenjars-project.sh
```

Next, follow the instructions in the README of the generated project to create
your holiday calendar definition files and generate your jar

When you import the generated jar as a dependency in your project, you'll be able to do things like:

```clj
(ns my-awesome-app
  (:require [my.generated.lib.calendar :refer [relative-date-add]]))

(relative-date-add (LocalDate/of 2020 10 9) 3 :business-days) ; -> 14Oct2020. Skips weekends.

(relative-date-add (LocalDate/of 2020 10 9) 3 :business-days "YOUR-CAL") ; Will skip weekends and any holidays defined by YOUR-CAL
```

It goes backwards too (this time with [juxt/tick](https://www.juxt.land/tick/docs/index.html)):

```clj
(ns my-awesome-app
  (:require [my.generated.lib.calendar :refer [relative-date-add]]
            [tick.core :as t]))

(relative-date-add (t/date "2020-10-12") -3 :business-days) ; -> 7Oct2020
```

Use "zero" business days for getting the next business day:

```clj
(ns my-awesome-app
  (:require [my.generated.lib.calendar :refer [relative-date-add]]))

(relative-date-add (LocalDate/of 2020 8 1) 0 :business-days) ; -> 3Aug20, because 1Aug20 is a Saturday
(relative-date-add (LocalDate/of 2020 8 2) 0 :business-days) ; -> 3Aug20, because 2Aug20 is a Sunday
(relative-date-add (LocalDate/of 2020 8 3) 0 :business-days) ; -> 3Aug20, as it's a regular Monday
```

Also check individual dates:

```clj
(ns my-awesome-app
  (:require [my.generated.lib.calendar :refer [weekend? holiday? non-business-day? business-day?]]
            [tick.core :as t]))

(holiday? (t/date "2020-10-12") "BR") ; -> true
(holiday? (t/date "2020-10-11") "BR") ; -> false
(weekend? (t/date "2020-10-12")) ; -> false
(weekend? (t/date "2020-10-11")) ; -> true
(business-day? (t/date "2020-10-12")) ; -> true
(business-day? (t/date "2020-10-12") "BR") ; -> false
(business-day? (t/date "2020-10-11")) ; -> false
(business-day? (t/date "2020-10-11") "BR") ; -> false
(non-business-day? (t/date "2020-10-12")) ; -> false
(non-business-day? (t/date "2020-10-12") "BR") ; -> true
(non-business-day? (t/date "2020-10-11")) ; -> true
(non-business-day? (t/date "2020-10-11") "BR") ; -> true
```
