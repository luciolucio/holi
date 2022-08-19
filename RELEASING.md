## How to release

1. Describe your change in `CHANGELOG.md`

```
* <version number> - <date>
  * Fixed such and such
```

2. Run bin/prepare.sh VERSION_NUMBER

3. Check and merge the proposed changes

4. Run bin/tag.sh VERSION_NUMBER


Update `template.zip`

```
cd resources
rm holi-template.zip
zip holi-template.zip holi-template/**/*
zip holi-template.zip holi-template/**/.*
```
