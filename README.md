# Calenjars
[![Build Status](https://travis-ci.com/luciolucio/calenjars.svg?branch=master)](https://travis-ci.com/luciolucio/calenjars)
[![Clojars Project](https://img.shields.io/clojars/v/luciolucio/calenjars.svg)](https://clojars.org/luciolucio/calenjars)

Calenjars is a clojure jar generator, designed to create libraries for date
arithmetics around non-business days, that you define yourself with a simple DSL.

## Usage

Create a new clojure deps project:

```
curl -LO https://raw.githubusercontent.com/luciolucio/calenjars/v0.1.0/new-calenjars-project.sh
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
