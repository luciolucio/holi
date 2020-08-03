# Calenjars
[![Build Status](https://travis-ci.com/luciolucio/calenjars.svg?branch=master)](https://travis-ci.com/luciolucio/calenjars)

Calenjars is a clojure jar generator, designed to create libraries for date
arithmetics around non-business days, that you define yourself with a simple DSL.

## Usage

Generate a new library using whether `curl` or `wget`:

```
curl -o- https://raw.githubusercontent.com/luciolucio/calenjars/v0.1.0/gen.sh | bash
```

```
wget -qO- https://raw.githubusercontent.com/luciolucio/calenjars/v0.1.0/gen.sh | bash
```

> Note: the script will prompt you for the namespace to use


Next, **create** your `.hol` files in the generated project's `calendars/` dir
according to the calendar file format, then run:

```
clojure -A:depstar:calenjars -m hf.depstar.jar <jar-name.jar>
```

Import the generated jas as a dependency in your project and you'll be able to do things like:

```
(ns my-awesome-app
  (:require [my.generated.lib :refer [relative-date-add]]))

(relative-date-add (LocalDate/of 2020 10 9) 3 :business-days) ; -> 14Oct2020. Skips weekends.

(relative-date-add (LocalDate/of 2020 10 9) 3 :business-days "YOUR-CAL") ; Will skip weekends and any holidays defined by YOUR-CAL
```

It goes backwards too (this time with [juxt/tick](https://www.juxt.land/tick/docs/index.html)):

```
(ns my-awesome-app
  (:require [my.generated.lib :refer [relative-date-add]]
            [tick.core :as t]))

(relative-date-add (t/date "2020-10-12") -3 :business-days) ; -> 7Oct2020
```

## Calendar file format
Calendar files actually specify *holidays* and have the `.hol` extension. Each holiday occupies one line, and is formatted like so:

```
Holiday name|Holiday calculation rule
```

You can use any of the calculation rules demonstrated below

### Yearly holiday
To add a holiday that repeats every year on the same date, use the format `DDMMM`

```
Nossa Senhora Aparecida|12Oct
```

This will repeat every year on October 12th

> NOTE: If you include a holiday on the 29th of February,
> it's only going to appear on leap years

### Specific date holiday
To include as a holiday a day that does not repeat every year, use the format `DDMMMYYYY`

```
Antecipação Revolução Constitucionalista|25May2020
```

Will occur on the 25th of May of the year 2020 and no other year or date

### Specific date exception
To exclude a date that otherwise would have been generated, use a minus sign (`-`) at the end

```
Antecipação Revolução Constitucionalista|09Jul2020-
```

This date will be excluded

### Expression
To generate a holiday based on a calculation, use the format `<EXPRESSION><OPERATOR><AMOUNT>`

Where `EXPRESSION` is one of the supported expressions below. `OPERATOR` is whether `+` or `-`, and `AMOUNT` is a positive decimal integer.

#### Examples

```
Corpus Christi|E+60
```

Will calculate a date based on expression `E`, and add 60 calendar days to it. If you want `EXPRESSION` with no changes, use `+0`.

#### Supported Expressions
| Expression | Meaning                      |
| :---:      |  :------:                    |
| **E**      | Calculate the date of easter |

### Comments
You may add a single line of comment at the top of your holiday definition file. It must start with `;`

```
; My awesome holiday file
#include BOOGALOO
Holiday Part 1|E+0
```

This line gets ignored by the holiday generator

### Including other calendars
You may include another calendar file in your holiday file. This is useful for example for national vs municipal holidays.

```
#include INCLUDED-FILE
A holiday in addition to the ones included|5May
```

* Remember not to write the `.hol` extension in the `#include` directive
* The directive must be on the first line, except when a comment is present (see below)
* The included file must be in the same directory
* Only one include per file is allowed
* You may include a file that includes another file, and so on

## Roadmap

* `is-holiday?` fn
* Expressions like 'n-th monday in December' or 'last monday in May'
* Observed holidays (e.g. if holiday falls on a weekend, observe on Monday or Friday)
* Non-saturday/sunday weekends
