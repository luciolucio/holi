## How to release

Describe your change in `CHANGELOG.md`

```
* <version number> - <date>
  * Fixed such and such
```

Copy the change description to `tag.txt`

Change the version number in the following places:

* build.clj
```
  (def version "0.1.1")                  <-- change here
```

* new-holi-project.sh

```
  echo "Downloading template..."
  curl -LO https://github.com/luciolucio/holi/raw/0.1.1/resources/holi-template.zip     <-- change here
```

* resources/holi-template/resources/deps.edn

```
io.github.luciolucio/holi {:mvn/version "0.1.1"}}  <-- change here
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
rm holi-template.zip
zip holi-template.zip holi-template/**/*
zip holi-template.zip holi-template/**/.*
```

Tag the new version

```
git tag 0.1.1 -F tag.txt        <-- Use the new version here and in the line below
git push origin 0.1.1
```

and change CUSTOM.md:

```
curl -LO https://raw.githubusercontent.com/luciolucio/holi/v0.1.1/new-holi-project.sh   <-- change here
```

Push everything
