## Performance

### Performance test run - 2023-02-24
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
