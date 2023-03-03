# Changes

* UNRELEASED
  * API now throws if holi does not recognize a calendar name

* 0.16.0 - 3Mar2023
  * API now throws when holi has no record of holidays for given year
  * Improved performance of API fns even more, up to 100x faster!

* 0.15.0 - 2Mar2023
  * Improved performance of main api functions to be 5 to 10 times faster

* 0.14.0 - 6Feb2023
  * Date lists are now about 55% smaller - great for js bundles. They also link dates and holiday names now
  * Added a new API function to list holidays

* 0.13.3 - 19Jan2023
  * No functionality change again, more documentation improvements

* 0.13.2 - 12Jan2023
  * No functionality change, but major documentation improvements
    * Must release a minor version since namespace metadata for cljdoc is changing.

* 0.13.1 - 10Jan2023
  * Set things up so that cljdoc can reach the source

* 0.13.0 - 10Jan2023
  * Shadow-cljs is no longer a runtime dependency, since the use of `shadow.resource/inline` is now gone

* 0.12.0 - 13Dec2022
  * Now supports cljs!
  * Upgraded to tick 0.5.0

* 0.11.0 - 27Nov2022
  * Added US and GB calendars
  * Added brazil/sao-paulo calendar
  * Now supports a 'forward-only' observance rule where a weekend holiday always gets pushed to Monday
  * Fixed a bug that caused nth-day-of-week holidays in August or September to fail holiday generation

* 0.10.0 - 19Aug2022
  * Now supports holiday files in subdirectories
  * Improved file generation performance greatly

* 0.9.0 - 9Aug2022
  * Generated files now have extensions to avoid collisions with actual namespaces when generating custom jars

* 0.8.0 - 7Aug2022
  * Custom build now uses `tools.build`

* 0.7.0 - 7Aug2022
  * Now supports singular versions of :days :weeks :months :years and :business-days
  * Cleaned up the namespace, moving internal fns somewhere else to avoid confusion

* 0.6.0 - 7Aug2022
  * Goodbye `calenjars`, hello `holi`!
  * The main API function has been simplified greatly: `relative-date-add` is now just `add`
  * Library now ships with the BR calendar, no need to generate anything
  * New possibilities in calendar DSL:
    * Added support for nth-day-of-the-week holidays
    * Now supports observed holidays, including UK Christmas/Boxing Day Monday/Tuesday rule
    * Can now specify a holiday to begin or end only after a certain year

* 0.5.0 - 7Jan2022
  * Upgraded to tick 0.4.32

* 0.4.0 - 22Oct2020
  * Added a way to find the next business day using "zero" business days

* 0.3.0 - 5Sep2020
  * Fixed a bug when generating lib with no calendars

* 0.2.0 - 4Aug2020
  * Added new functions to check individual dates
    * `weekend?`
    * `holiday?`
    * `business-day?`
    * `non-business-day?`

* 0.1.3 - 3Aug2020
  * Fixed an issue with gen-lib that prevented the holidays from entering the jar

* 0.1.2 -- 3Aug2020
  * First working version of new project script

* 0.1.0 -- 3Aug2020
  * Initial version
