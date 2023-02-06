# holi-showcase

A simple application to showcase holi's holiday calendars

### Installation

Make sure you have these prerequisites

* [Clojure](https://clojure.org/guides/getting_started)
* [nvm](https://github.com/nvm-sh/nvm)
* **Node** - Once you have nvm, run `nvm use stable`
* **Yarn** - Once you have node, run `npm install -g yarn`

Run the following command:

```
yarn install
```

Then to run, execute this:

```
yarn watch-all
```

or individually

```
yarn watch:app
yarn watch:workspaces
```

Wait for the build to complete, then navigate to:

```
http://localhost:8080 - for the app
http://localhost:3689 - for workspaces
``` 

### REPL
* For IntelliJ, create a remote nREPL connecting to localhost to the port under `shadow-cljs.edn` (11011 as of this writing)  

...then execute the code below to connect to your app

```
(require '[shadow.cljs.devtools.api :as shadow.api])
(shadow.api/nrepl-select :app)
```

# Lint

```
bin/lint
```

Autofix with

```
bin/lint fix
```

### Running tests

```
bin/run-tests
```

### Building

To use `stag.edn`
```
bin/build stag <version-name>
```

To use `prod.edn`
```
bin/build prod <version-name>
```

This will output a package to `stag-build` or `prod-build`

### Image

To build a docker image, after having built

```
bin/build-image <env> <version-name>
```

Image will use the package under `<env>-build`, and get tagged as `[project-name]-[version-name]` 
