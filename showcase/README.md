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
yarn watch:app
```

Wait for the build to complete, then navigate to:

```
http://localhost:8080
``` 

### REPL
* For IntelliJ, create a remote nREPL connecting to localhost to the port under `shadow-cljs.edn` (11011 as of this writing)  

...then execute the code below to connect to your app

```
(require '[shadow.cljs.devtools.api :as shadow.api])
(shadow.api/nrepl-select :app)
```

# Code formatting

```
bin/fmt
```

Autofix with

```
bin/fmt fix
```

### Building

```
bin/build <version-number>
```

This will output a package to `build`