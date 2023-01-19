## Roadmap

* Add docs that showcase the actual calendars
  * Add a new public API to list holidays as data
  * Like `(holi/list 2023 "US")`
  * Returns a collection like `[{:date X1 :description Y1} {:date X2 :description Y2} ...]`
  * Hardcode list of existing countries/cities
* Test cljs with advanced compilation
* Improve docs with js date types
* Improve docs to show how to use the holi api with strings instead of LocalDate instances (except for `add`)
* Make the .datelist format more compact
* Change `is-date-in-list?` and related data structures so that a binary search is executed
* Make it so performance tests are more useful and actually checked during builds
* Non-saturday/sunday weekends
