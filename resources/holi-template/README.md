# Holi custom jars

How to generate a custom jar with your own holidays

## Usage
1. Create `.hol` files in `calendars/`
    * See file format below, and examples in `examples/`
    * The name `WEEKEND.hol` is reserved and cannot be used
1. Run `gen-lib.sh`
1. Your jar file will be in `generated/{{lib-name}}.jar`

## .hol file format
Files with the `.hol` extension specify *holidays* and have the `.hol` extension. Each holiday occupies one line, and is formatted like so:

```
Holiday name|Holiday calculation rule
```

You can use any of the calculation rules demonstrated below


### Yearly holiday
To add a holiday that repeats every year on the same date, use the format `DDMMM`

```
Juneteenth|19Jun
```

This will repeat every year on June 19th

> NOTE: If you include a holiday on the 29th of February,
> it's only going to appear on leap years

#### Modifiers
Accepted modifiers for yearly holidays are:

|        Modifier         |                                    Meaning                                     |                       Example                        |
|:-----------------------:|:------------------------------------------------------------------------------:|:----------------------------------------------------:|
|         YYYY->          |                  Generate holiday only on and after year YYYY                  |          `New Year&#124;01Jan&#124;2014->`           |
|         ->YYYY          |                 Generate holiday only before and on year YYYY                  |          `New Year&#124;01Jan&#124;->2014`           |
|        observed         | Holiday will be Friday/Monday if date falls on a Saturday/Sunday respectively  |      `Independence Day&#124;4Jul&#124;observed`      |
|     observed-monday     |          Holiday will be Monday if date falls on a Saturday or Sunday          |   `New Year's Day&#124;1Jan&#124;observed-monday`    |
| observed-monday-tuesday | Holiday will be Monday/Tuesday if date falls on a Saturday/Sunday respectively | `Boxing Day&#124;26Dec&#124;observed-monday-tuesday` |


### Specific date holiday
To include as a holiday a day that does not repeat every year, use the format `DDMMMYYYY`

```
Queen Elizabeth's Funeral Day|19Sep2022
```

Will occur on the 19th of September of the year 2022 and no other year or date

#### Modifiers
No modifiers are accepted for specific date holidays


### Specific date exception
To exclude a date that otherwise would have been generated, use a minus sign (`-`) at the end

```
Spring Bank Holiday Moved For Jubilee Long Weekend|30May2022-
```

This date will be excluded

#### Modifiers
No modifiers are accepted for specific date exceptions


### N-th day of the week
To specify a holiday based on an 'n-th day of the week' rule, use the format `<N><Day of Week><Month>`

```
President's Day|3MonFeb
```

That is, the third (`3`) Monday (`Mon`) of February (`Feb`). Use common three-letter abbreviations for days of the week and months.
Note that you may also specify "next to last" with `-1`, like this:

```
Memorial Day|-1MonMay
```

#### Modifiers
Accepted modifiers for nth day of the week holidays are:

| Modifier |                    Meaning                    |                 Example                  |
|:--------:|:---------------------------------------------:|:----------------------------------------:|
|  YYYY->  | Generate holiday only on and after year YYYY  | `Memorial Day&#124;-1MonMay&#124;2018->` |
|  ->YYYY  | Generate holiday only before and on year YYYY | `Memorial Day&#124;-1MonMay&#124;->2018` |


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

#### Modifiers
Accepted modifiers for expression holidays are:

|        Modifier         |                                    Meaning                                     |                          Example                          |
|:-----------------------:|:------------------------------------------------------------------------------:|:---------------------------------------------------------:|
|         YYYY->          |                  Generate holiday only on and after year YYYY                  |               `Easter&#124;E+0&#124;2014->`               |
|         ->YYYY          |                 Generate holiday only before and on year YYYY                  |               `Easter&#124;E+0&#124;->2016`               |
|        observed         | Holiday will be Friday/Monday if date falls on a Saturday/Sunday respectively  |         `Easter On Monday&#124;E+0&#124;observed`         |
|     observed-monday     |          Holiday will be Monday if date falls on a Saturday or Sunday          |     `Easter On Monday&#124;E-1&#124;observed-monday`      |
| observed-monday-tuesday | Holiday will be Monday/Tuesday if date falls on a Saturday/Sunday respectively | `Easter On Tuesday&#124;E+0&#124;observed-monday-tuesday` |


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
* The directive must be on the first line (only a comment may come before it)
* The path of the included file is always relative to the root calendar path, i.e. `#include foo/bar` includes the same file regardless of where it's found
* Only one include per file is allowed, but you may nest includes
