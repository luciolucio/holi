# Contributing

Thanks for your interest in contributing to holi! If you have any questions, ping me on [Slack](http://clojurians.slack.com): `@Lucio Assis`

## Guidelines

* No pull requests without tests
* No calendars without external references to back it up
* Opening an [issue](https://github.com/luciolucio/holi/issues) is also a form of contribution!

That's it! See below for some practical stuff.

## General development

### Requirements

* Clojure 1.11
* Make

### Running tests

To run clj tests, run `make test` (or `make watch` if you want the test runner to run tests as you write code).

To run cljs tests, run `make test-cljs`.

To run tests against the candidate jar, run `make test-all-libs`.

#### Performance tests

Run `QUICK_PERF_TESTS=true make perftest` for a quicker but higher uncertainty performance test.

Use `make perftest` for a longer, more precise measurement.

### Code formatting

Run `make fmt-check` to see if code formatting is ok, and `make fix` to fix formatting automatically.

### Linting

Your code should have no `clj-kondo` warnings, which you can run with `make lint`.

### Starting a ClojureScript REPL
To start a cljs REPL:

1. Run `make repl-cljs` and wait for the `Build completed` message
2. Visit http://localhost:8080 to give it a JS runtime
3. Connect to the REPL (nREPL) on port 11011

## Adding a new calendar

To contribute a new calendar:

* Add your `.hol` file(s) under `resources/calendars-source`
  * For **countries**, name your file after its two-letter code according to the [timeanddate.com holidays API](https://dev.timeanddate.com/docs/available-countries) list of available countries, or [ISO-3166](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2) if not available in the API
  * For **cities**, create folders and files to match the [timeanddate.com holidays API](https://dev.timeanddate.com/docs/available-locations) list of available locations (e.g. `usa/anchorage`), or something along those lines if not avalable
* Write your file according to the `.hol file format` section at [resources/holi-template/README.md](resources/holi-template/README.md#hol-file-format)
* Add it to `luciolucio.holi.core/holiday-datelists`
* Add tests for a few years' worth of holidays in the `luciolucio.holi.generate-test.holidays-locations-test` namespace
* Add one holiday for every added file in both `test-lib/clj/src/holi_test.clj` and `test-lib/cljs/src/holi_test.cljs`
* Add it to the `Available holiday calendars` section of the [README](README.md#available-holiday-calendars)
* Add an entry to the calendar showcase menu at `luciolucio.holi.showcase.pages.home.core/sidebar`
* Send a pull request

> NOTE: If you'd like to contribute a new calendar or a correction to an existing one, please
include any references that can be used to verify and document your change.
