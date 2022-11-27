# How to build a custom lib with holidays of your own

Create a new clojure deps project:

```
curl -LO https://raw.githubusercontent.com/luciolucio/holi/0.11.0/new-holi-project.sh
bash new-holi-project.sh
```

Next, follow the instructions in the README of the generated project to create
your holiday calendar definition files and generate your jar

Import the generated jar as a dependency into your project, it will expose the same API as the main project
