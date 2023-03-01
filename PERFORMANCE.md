## Performance

### Performance test run - 2023-03-01

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
.Running benchmark for luciolucio.holi.file/generate-datelist!:
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
```
