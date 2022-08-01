# How to build a custom lib with holidays of your own

Create a new clojure deps project:

```
curl -LO https://raw.githubusercontent.com/piposaude/calenjars/0.5.0/new-calenjars-project.sh
bash new-calenjars-project.sh
```

Next, follow the instructions in the README of the generated project to create
your holiday calendar definition files and generate your jar

Import the generated jar as a dependency into your project
