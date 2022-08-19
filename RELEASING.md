## How to release

1. Describe your change in `CHANGELOG.md`

```
* <version number> - <date>
  * Fixed such and such
```

2. Run `VERSION_NUMBER=x.x.x make prepare`

3. Merge the proposed changes to main

4. Run `make tag`
