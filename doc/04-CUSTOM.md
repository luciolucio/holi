# How to build a custom lib with holidays of your own

Generate a new clojure deps project:

```
curl -LO https://raw.githubusercontent.com/luciolucio/holi/1.1.0/new-holi-project.sh
bash new-holi-project.sh
```

Follow the instructions in its README to create holiday calendar files of your own and generate
a jar. It will expose the [same API as holi](https://cljdoc.org/d/io.github.luciolucio/holi/1.1.0/api/luciolucio.holi)
but your holidays will be available instead.
