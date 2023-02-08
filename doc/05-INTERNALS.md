# How holi works

Holi uses regular calendar operations, such as `add 5 days`, but it's aware of weekends and holidays
to be able to skip those when requested.

## .hol files

It all starts with the `.hol` files. They contain the definition of what is called a "_holiday calendar_",
or: **a set of rules that allow holi to figure out holidays on a specific year**. Here are a couple
example rules and their application to a few years.

| Holiday name     | Rule                                                                                      | Date on 2019 | Date on 2020 | Date on 2021 |
|------------------|-------------------------------------------------------------------------------------------|--------------|--------------|--------------|
| New Year's Day   | Every 1st of January                                                                      | 2019-01-01   | 2020-01-01   | 2021-01-01   |
| Independence Day | 4th of July, but if it falls Sat or Sun, holiday will be observed Fri or Mon respectively | 2019-07-04   | 2020-07-03   | 2021-07-05   |

This collection of rules is known as "_the holiday calendar_", and is written in a specific
format (see [Custom Holidays](https://cljdoc.org/d/io.github.luciolucio/holi/0.13.3/doc/custom-holidays)
if you want the details).

## .datelist files

When holi gets built, files in the `.hol` format are interpreted and each individual rule gets applied
to the year at hand (i.e., the year in which holi is getting built), and for a bracket of years before
and after. The bracket size is 80 years and is defined as a variable in the Makefile.

Note that `.datelist` files are all that holi needs to respond to its public API functions. The `.hol` files
become dispensable once the jar gets built.

### .datelist file format

A single date is first encoded in a timestamp that is the number of days since 1939-12-31 (see note below), and then
converted to hexadecimal and padded to 4 characters with zeros if needed. For example:

```
ISO date: 1940-04-20
Timestamp (number of days since 1939-12-31): 70
Hexadecimal representation: 46
Padded to 4 characters: 0046
```

Then two characters are appended to indicate what holiday it is, i.e., a hex number from `00` to `FF`.
This entails a record of size 6, and the next record is simply appended to the first one, and so on.

The first line is all date records, and the remaining lines contain holiday names (originally from the `.hol`
file) encoded like so:

> `XXHoliday Name`

For example: `04Veteran's Day`. So a typical file would look something like this (`[...]` is representing
the fact that there could be many more records on each side):

```
[...]728B0172F80073F800746500[...]
00My first holiday
01My second holiday
```

#### Notes

> The reference date 1939-12-31 is chosen to be just over 80 years in the past because that's the bracket size used to
> build holi

> This timestamp format is inspired in what is known as the Microsoft Timestamp, the number of days since 1899-12-31

### Goals for the date encoding and alternatives considered

The goal was to allow for a decent amount of calendars (around 100), with a reasonable amount of
holidays each (about 10), while still allowing the date lists to be (somewhat) human-readable, or
at least simple enough to reason about, and having a browser/internet-friendly bundle size.

| Alternative    | Description                               | Pros                         | Cons                                |
|----------------|-------------------------------------------|------------------------------|-------------------------------------|
| `YYYY-MM-DD??` | ISO date with dashes + holiday indicator  | High readability             | Large file size                     |
| `YYYYMMDD??`   | Remove dashes                             | Same as above                | Still large                         |
| `TS----??`     | Timestamp + holiday indicator             | Dates with only 6 characters | Not immediately readable            |
| `XXXXX??`      | Hex timestamp + holiday indicator         | One character fewer          | Less readable, but not much worse   |
| `XXXX??`       | Smaller hex timestamp + holiday indicator | One character fewer          | Smaller high date limit             |
| `XXXXX!`       | Hex timestamp + char holiday indicator    | Shave off one more byte      | Might render badly in a text editor |

The chosen one was the next to last one, `XXXX??` to strike a balance between readability and space efficiency. The high
date limit is controlled by the choice of timestamp reference date. The date list string is definitely not "readable"
but it's simple to reason about, so you can quickly write something to parse it (you might as well
use `luciolucio.holi.core/parse-date`).

Another decision was to use a fixed record size instead of a separator - only about 7% of the dates will require
padding, so that saves a lot of space.

### Limits

| Description                | Limit      | Comments                                         |
|----------------------------|------------|--------------------------------------------------|
| Maximum unique holidays    | 256        | Holidays are keyed with two hex digits, max 0xFF |
| Max date on .datelist file | 2119-06-04 | 0xFFFF converted to timestamp                    |

Note that 256 is not necessarily the maximum number of lines in a `.hol` file. One may do other things
like exclude dates, add a comment or include other files.

## How holi checks whether a date is a weekend

It checks the date against the special `WEEKEND.datelist` file. If it's there, it's a weekend. This file
gets generated at the time holi builds. The weekend datelist is special in that it does not have holiday indicators.

## How holi checks whether a date is a holiday

Same as above, but it checks the date against the `.datelist` file that corresponds to the given holiday
calendar, like `US.datelist` or `brazil/sao-paulo.datelist`.
