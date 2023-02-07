# holi-showcase

A simple application to showcase holi's holiday calendars

When releasing, showcase gets built and moved to the `docs/` folder. Do not mix with the `doc/` folder, where the rest
of the documentation lives. The name `docs/` is required by GitHub Pages.

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
yarn watch
```

Wait for the build to complete, then navigate to:

```
http://localhost:8080
``` 

Changes will auto-load

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
bin/build
```

This will output a package to the `build` folder
