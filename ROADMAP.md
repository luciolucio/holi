## Roadmap

* Add boundary checks against the .datelist limits (incl. showcase)
* Add a cljs test with advanced compilation
* Improve docs to show how to use the holi api with strings instead of LocalDate instances (except for `add`)
* Change `is-date-in-list?` and related data structures so that a binary search is executed
* Make it so performance tests are more useful and actually checked during builds
* Non-saturday/sunday weekends
