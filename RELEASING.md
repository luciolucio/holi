## How to release

Describe your change in `CHANGELOG.md`

```
* <version number> - <date>
  * Fixed such and such
```

Change the version number in the following places:

* build.clj
```
  (def version "0.1.1")                  <-- change here
```

* resources/holi-template/resources/deps.edn

```
io.github.luciolucio/holi {:mvn/version "0.1.1"}}  <-- change here
```

* resources/holi-template/resources/pom.xml

```
    <dependency>
      <groupId>io.github.luciolucio</groupId>
      <artifactId>holi</artifactId>
      <version>0.1.1</version>              <-- change here
    </dependency>
```

Make sure the clojars token is in the `servers` section of your `~/.m2/settings.xml`:

```
    <server>
        <id>clojars</id>
        <username>luciolucio</username>
        <password>CLOJARS-TOKEN-HERE</password>
    </server>
```

Run this:

```
bin/release.sh
```

Update `template.zip`

```
cd resources
zip holi-template.zip holi-template/**/*
zip holi-template.zip holi-template/**/.*
```

Tag the new version in github, and change CUSTOM.md:

```
curl -LO https://raw.githubusercontent.com/luciolucio/holi/v0.1.0/new-holi-project.sh   <-- change here
```

Push everything
