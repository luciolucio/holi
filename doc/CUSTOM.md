# How to build a custom lib with holidays of your own

Create a new clojure deps project:

```
curl -LO https://raw.githubusercontent.com/luciolucio/holi/0.13.2/new-holi-project.sh
bash new-holi-project.sh
```

Open the generated project and follow the instructions in its README to create holiday definition files
of your own and generate a jar. It will expose the same API as holi.
