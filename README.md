# Calendars

Creates calendars in S3 to be used with `date-add-cal`

[[_TOC_]]

## Usage

1. **Create** or **update** a calendar in `resources/calendars`
1. Commit, push and create a **merge request**
1. Once you **merge** your MR, the CI job will upsert any calendars in S3

> NOTE: If your holiday file has any syntax errors, the job will fail

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

## Including other calendars
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

## Comments
You may add a single line of comment at the top of your holiday definition file. It must start with `;`

```
; My awesome holiday file
#include BOOGALOO
Holiday Part 1|E+0
```

This line gets ignored by the holiday generator

## Next steps
* CI script
