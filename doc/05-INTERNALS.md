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
and after. The default bracket is 80 years and is defined as a variable in the Makefile.

Note that `.datelist` files are all that holi needs to respond to its public API functions. The `.hol` files
become dispensable once the jar gets built.

### .datelist file format

A single date is first encoded in what is known as a Microsoft Timestamp, and is then converted to
hexadecimal and padded to 5 characters with zeros. For example:

```
> ISO date: 2025-04-20
> MS Timestamp (number of days since 1899-12-31): 45767
> Hexadecimal representation: B2C7
> Padded to 5 characters with zeros: 0B2C7
```

Then two characters are appended to indicate what holiday it is, i.e., a hex number from `00` to `FF`.
This entails a record of size 7, and the next one is simply appended to the first one, and so on.

All dates are in the first line, and the remaining lines contain holiday names (originally from the `.hol`
file) encoded like so:

> `XXHoliday Name`

For example: `04Veteran's Day`. So a typical file would look something like this (`[...]` is representing
the fact that there are many more records on each side):

```
[...]0AB9C010AC09000AD09000AD7600[...]
00My first holiday
01My second holiday
```

### Goals for the date encoding and alternatives considered

The goal was to allow for a decent amount of calendars (around 100), with a reasonable amount of
holidays each (about 10), while still allowing the date lists to be (somewhat) human-readable, or
at least simple enough to reason about, and maintaining a browser/internet-friendly bundle size.

| Alternative    | Description                               | Pros                         | Cons                                |
|----------------|-------------------------------------------|------------------------------|-------------------------------------|
| `YYYY-MM-DD??` | ISO date with dashes + holiday indicator  | High readability             | Large file size                     |
| `YYYYMMDD??`   | Remove dashes                             | Same as above                | Still large                         |
| `MS----??`     | MS timestamp + holiday indicator          | Dates with only 6 characters | Not immediately readable            |
| `XXXXX??`      | Hex MS timestamp + holiday indicator      | One fewer character          | Less readable, but not much worse   |
| `XXXXX!`       | Hex MS timestamp + char holiday indicator | Shave off one more byte      | Might render badly in a text editor |

The chosen one was `XXXXX??` to strike a balance between readability and space efficiency. Note that removing
one more character was not an option, as it would leave the max date at `2079-06-04` and that's too close.
The date list string is definitely not "readable" but it's simple to reason about, so you can quickly write
something to parse it.

Another decision was to remove line breaks between items. Space-wise it mostly doesn't matter
since most dates have to be padded anyway, but the good thing is that a generated js file
won't have an enormous section of dates in their own lines when looked into.

### Limits

| Description                | Limit      | Comments                                         |
|----------------------------|------------|--------------------------------------------------|
| Maximum unique holidays    | 256        | Holidays are keyed with two hex digits, max 0xFF |
| Max date on .datelist file | 4700-11-24 | 0xFFFFF converted to MS timestamp                |

Note that 256 is not necessarily the maximum number of lines in a `.hol` file. One may do other things
like exclude dates, add a comment or include other files.

## How holi checks whether a date is a weekend

It checks the date against the special `WEEKEND.datelist` file. If it's there, it's a weekend. This file
gets generated at the time holi builds.

## How holi checks whether a date is a holiday

Same as above, but it checks the date against the `.datelist` file that corresponds to the given holiday
calendar, like `US.datelist` or `brazil/sao-paulo.datelist`.
