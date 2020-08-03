## How to release

Change the version number in these places:

* resources/calenjars-template/resources/deps.edn

```
luciolucio/calenjars {:mvn/version "0.1.1"}}  <-- change here
```

* resources/calenjars-template/resources/pom.xml

```
    <dependency>
      <groupId>luciolucio</groupId>
      <artifactId>calenjars</artifactId>
      <version>0.1.1</version>              <-- change here
    </dependency>
```

* main pom.xml
```
  <groupId>luciolucio</groupId>
  <artifactId>calenjars</artifactId>
  <version>0.1.1</version>                  <-- change here
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
zip calenjars-template.zip calenjars-template/**/*
zip calenjars-template.zip calenjars-template/**/.*
```

Tag the new version in github, and change the README:

```
curl -o- https://raw.githubusercontent.com/luciolucio/calenjars/v0.1.1/new-project.sh | bash   <-- change here
```

```
wget -qO- https://raw.githubusercontent.com/luciolucio/calenjars/v0.1.1/new-project.sh | bash   <-- change here
```

Push everything
