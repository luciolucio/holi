## Performance

### Performance test run - 2023-03-02

Actually taking advantage of the binary search by making the datelists be vectors

luciolucio.holi/add :business-days with no calendar
3.834684 ms -> 1.293036 µs

luciolucio.holi/holiday?
96.906469 µs -> 116.248213 ns

luciolucio.holi/add days without calendars
73.313833 ns -> 74.302447 ns

luciolucio.holi/add :business-days with a calendar
6.243130 ms -> 2.245936 µs

```
[(Running benchmark for luciolucio.holi/add days without calendars:
Evaluation count : 748000740 in 60 samples of 12466679 calls.
             Execution time mean : 74.302447 ns
    Execution time std-deviation : 0.281434 ns
   Execution time lower quantile : 73.820293 ns ( 2.5%)
   Execution time upper quantile : 74.834634 ns (97.5%)
                   Overhead used : 6.861412 ns
Is the mean less than 100ns? true
.Running benchmark for luciolucio.holi/holiday?:
Evaluation count : 494160360 in 60 samples of 8236006 calls.
             Execution time mean : 116.248213 ns
    Execution time std-deviation : 1.026821 ns
   Execution time lower quantile : 114.701839 ns ( 2.5%)
   Execution time upper quantile : 118.062655 ns (97.5%)
                   Overhead used : 6.861412 ns

Found 2 outliers in 60 samples (3.3333 %)
	low-severe	 1 (1.6667 %)
	low-mild	 1 (1.6667 %)
 Variance from outliers : 1.6389 % Variance is slightly inflated by outliers
Is the mean less than 200µs? true
.Running benchmark for luciolucio.holi/add :business-days with no calendar:
Evaluation count : 47205540 in 60 samples of 786759 calls.
             Execution time mean : 1.293036 µs
    Execution time std-deviation : 4.919744 ns
   Execution time lower quantile : 1.286463 µs ( 2.5%)
   Execution time upper quantile : 1.303342 µs (97.5%)
                   Overhead used : 6.861412 ns

Found 2 outliers in 60 samples (3.3333 %)
	low-severe	 2 (3.3333 %)
 Variance from outliers : 1.6389 % Variance is slightly inflated by outliers
Is the mean less than 10ms? true
.Running benchmark for luciolucio.holi/add :business-days with a calendar:
Evaluation count : 26540400 in 60 samples of 442340 calls.
             Execution time mean : 2.245936 µs
    Execution time std-deviation : 12.513776 ns
   Execution time lower quantile : 2.229526 µs ( 2.5%)
   Execution time upper quantile : 2.273790 µs (97.5%)
                   Overhead used : 6.861412 ns

Found 3 outliers in 60 samples (5.0000 %)
	low-severe	 2 (3.3333 %)
	low-mild	 1 (1.6667 %)
 Variance from outliers : 1.6389 % Variance is slightly inflated by outliers
Is the mean less than 10ms? true
.)]
```

### Performance test run - 2023-03-01.2

Memoized `read-dates-multi` and `read-dates-single`

luciolucio.holi.file/generate-datelist!
663.092213 ms -> 686.140175 ms

luciolucio.holi/add :business-days with no calendar
22.556061 ms -> 3.834684 ms

luciolucio.holi/holiday?
2.674918 ms -> 96.906469 µs

luciolucio.holi/add days without calendars
73.415091 ns -> 73.313833 ns

luciolucio.holi/add :business-days with a calendar
29.196367 ms -> 6.243130 ms

```
[(Running benchmark for luciolucio.holi/add :business-days with no calendar:
Evaluation count : 144 in 6 samples of 24 calls.
             Execution time mean : 3.834684 ms
    Execution time std-deviation : 69.377836 µs
   Execution time lower quantile : 3.795662 ms ( 2.5%)
   Execution time upper quantile : 3.948472 ms (97.5%)
                   Overhead used : 6.824377 ns

Found 1 outliers in 6 samples (16.6667 %)
	low-severe	 1 (16.6667 %)
 Variance from outliers : 13.8889 % Variance is moderately inflated by outliers
Is the mean less than 50ms? true
.Running benchmark for luciolucio.holi/add :business-days with a calendar:
Evaluation count : 102 in 6 samples of 17 calls.
             Execution time mean : 6.243130 ms
    Execution time std-deviation : 145.193179 µs
   Execution time lower quantile : 6.118988 ms ( 2.5%)
   Execution time upper quantile : 6.439452 ms (97.5%)
                   Overhead used : 6.824377 ns
Is the mean less than 50ms? true
.Running benchmark for luciolucio.holi.file/generate-datelist!:
Evaluation count : 6 in 6 samples of 1 calls.
             Execution time mean : 686.140175 ms
    Execution time std-deviation : 39.073101 ms
   Execution time lower quantile : 659.329411 ms ( 2.5%)
   Execution time upper quantile : 740.938395 ms (97.5%)
                   Overhead used : 6.824377 ns
Is the mean less than 1 second? true
.Running benchmark for luciolucio.holi/holiday?:
Evaluation count : 6138 in 6 samples of 1023 calls.
             Execution time mean : 96.906469 µs
    Execution time std-deviation : 1.584701 µs
   Execution time lower quantile : 95.720082 µs ( 2.5%)
   Execution time upper quantile : 99.143902 µs (97.5%)
                   Overhead used : 6.824377 ns
Is the mean less than 10ms? true
.Running benchmark for luciolucio.holi/add days without calendars:
Evaluation count : 7771452 in 6 samples of 1295242 calls.
             Execution time mean : 73.313833 ns
    Execution time std-deviation : 2.671164 ns
   Execution time lower quantile : 70.700549 ns ( 2.5%)
   Execution time upper quantile : 77.386017 ns (97.5%)
                   Overhead used : 6.824377 ns
Is the mean less than 100ns? true
.)]
```

### Performance test run - 2023-03-01.1

Replaced flatten+sort with merge-sorted-lists

luciolucio.holi.file/generate-datelist!
675.161286 ms -> 663.092213 ms

luciolucio.holi/add :business-days with no calendar
29.434686 ms -> 22.556061 ms

luciolucio.holi/holiday?
2.669468 ms -> 2.674918 ms

luciolucio.holi/add days without calendars
81.681958 ns -> 73.415091 ns

luciolucio.holi/add :business-days with a calendar
36.883344 ms -> 29.196367 ms

```
[(Running benchmark for luciolucio.holi/holiday?:
Evaluation count : 22860 in 60 samples of 381 calls.
             Execution time mean : 2.674918 ms
    Execution time std-deviation : 23.202102 µs
   Execution time lower quantile : 2.643000 ms ( 2.5%)
   Execution time upper quantile : 2.727866 ms (97.5%)
                   Overhead used : 6.823000 ns

Found 3 outliers in 60 samples (5.0000 %)
	low-severe	 3 (5.0000 %)
 Variance from outliers : 1.6389 % Variance is slightly inflated by outliers
Is the mean less than 10ms? true
.Running benchmark for luciolucio.holi/add :business-days with a calendar:
Evaluation count : 2100 in 60 samples of 35 calls.
             Execution time mean : 29.196367 ms
    Execution time std-deviation : 385.035501 µs
   Execution time lower quantile : 28.781546 ms ( 2.5%)
   Execution time upper quantile : 30.035409 ms (97.5%)
                   Overhead used : 6.823000 ns

Found 4 outliers in 60 samples (6.6667 %)
	low-severe	 1 (1.6667 %)
	low-mild	 3 (5.0000 %)
 Variance from outliers : 1.6389 % Variance is slightly inflated by outliers
Is the mean less than 50ms? true
.Running benchmark for luciolucio.holi/add :business-days with no calendar:
Evaluation count : 2700 in 60 samples of 45 calls.
             Execution time mean : 22.556061 ms
    Execution time std-deviation : 183.084753 µs
   Execution time lower quantile : 22.372606 ms ( 2.5%)
   Execution time upper quantile : 22.830975 ms (97.5%)
                   Overhead used : 6.823000 ns

Found 4 outliers in 60 samples (6.6667 %)
	low-severe	 3 (5.0000 %)
	low-mild	 1 (1.6667 %)
 Variance from outliers : 1.6389 % Variance is slightly inflated by outliers
Is the mean less than 50ms? true
.Running benchmark for luciolucio.holi/add days without calendars:
Evaluation count : 753182640 in 60 samples of 12553044 calls.
             Execution time mean : 73.415091 ns
    Execution time std-deviation : 0.551088 ns
   Execution time lower quantile : 72.900226 ns ( 2.5%)
   Execution time upper quantile : 74.719250 ns (97.5%)
                   Overhead used : 6.823000 ns
Is the mean less than 100ns? true
.Running benchmark for luciolucio.holi.file/generate-datelist!:
Evaluation count : 120 in 60 samples of 2 calls.
             Execution time mean : 663.092213 ms
    Execution time std-deviation : 8.688449 ms
   Execution time lower quantile : 655.448910 ms ( 2.5%)
   Execution time upper quantile : 671.856556 ms (97.5%)
                   Overhead used : 6.823000 ns

Found 2 outliers in 60 samples (3.3333 %)
	low-severe	 1 (1.6667 %)
	low-mild	 1 (1.6667 %)
 Variance from outliers : 1.6389 % Variance is slightly inflated by outliers
Is the mean less than 1 second? true
.)]
```

### Performance test run - 2023-02-24

This is the baseline

luciolucio.holi.file/generate-datelist!
675.161286 ms

luciolucio.holi/add :business-days with no calendar
29.434686 ms

luciolucio.holi/holiday?
2.669468 ms

luciolucio.holi/add days without calendars
81.681958 ns

luciolucio.holi/add :business-days with a calendar
36.883344 ms

```
[(Running benchmark for luciolucio.holi.file/generate-datelist!:
Evaluation count : 6 in 6 samples of 1 calls.
             Execution time mean : 675.161286 ms
    Execution time std-deviation : 36.466377 ms
   Execution time lower quantile : 645.071577 ms ( 2.5%)
   Execution time upper quantile : 727.065176 ms (97.5%)
                   Overhead used : 6.886692 ns
Is the mean less than 1 second? true
.Running benchmark for luciolucio.holi/add :business-days with no calendar:
Evaluation count : 24 in 6 samples of 4 calls.
             Execution time mean : 29.434686 ms
    Execution time std-deviation : 1.993675 ms
   Execution time lower quantile : 27.590400 ms ( 2.5%)
   Execution time upper quantile : 32.214661 ms (97.5%)
                   Overhead used : 6.886692 ns
Is the mean less than 50ms? true
.Running benchmark for luciolucio.holi/holiday?:
Evaluation count : 234 in 6 samples of 39 calls.
             Execution time mean : 2.669468 ms
    Execution time std-deviation : 39.863599 µs
   Execution time lower quantile : 2.623930 ms ( 2.5%)
   Execution time upper quantile : 2.725022 ms (97.5%)
                   Overhead used : 6.886692 ns
Is the mean less than 10ms? true
.Running benchmark for luciolucio.holi/add days without calendars:
Evaluation count : 6665604 in 6 samples of 1110934 calls.
             Execution time mean : 81.681958 ns
    Execution time std-deviation : 1.427749 ns
   Execution time lower quantile : 78.838549 ns ( 2.5%)
   Execution time upper quantile : 82.549254 ns (97.5%)
                   Overhead used : 6.886692 ns

Found 1 outliers in 6 samples (16.6667 %)
	low-severe	 1 (16.6667 %)
 Variance from outliers : 13.8889 % Variance is moderately inflated by outliers
Is the mean less than 100ns? true
.Running benchmark for luciolucio.holi/add :business-days with a calendar:
Evaluation count : 24 in 6 samples of 4 calls.
             Execution time mean : 36.883344 ms
    Execution time std-deviation : 1.384224 ms
   Execution time lower quantile : 35.327993 ms ( 2.5%)
   Execution time upper quantile : 38.252743 ms (97.5%)
                   Overhead used : 6.886692 ns
Is the mean less than 50ms? true
.)]
```
