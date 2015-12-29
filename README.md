# Petrol

Petrol is a library to help you build ClojureScript webapps. It
concentrates on being Functional, and if that leads to being Reactive,
well, that's just fine too.

[![Clojars Project](http://clojars.org/petrol/latest-version.svg)](http://clojars.org/petrol)

[![Build
Status](https://travis-ci.org/krisajenkins/petrol.svg?branch=0.1.0)](https://travis-ci.org/krisajenkins/petrol)

## About

Documentation in on its way. In the meantime, my [talk at ClojureExchange 2015](https://skillsmatter.com/skillscasts/7227-clojurescript-architecting-for-scale)
will tell you all you need to know.

## Running the examples

``` sh
cd examples
lein figwheel counter counter2 multicounter spotify hydra pages
```

Then open http://localhost:3449

## Developing

Developing a standalone library is a bit of a pain. The solution I like is to use the library as an editable-dependency of the examples, like so:

``` sh
cd examples
mkdir checkouts
ln -s .. checkouts/
lein figwheel
```

Now you should be able to edit the library, and the examples that demostrate its use, as one live project.

## Troubleshooting

If you get this exception when running `lein`:

``` sh
clojure.lang.Compiler$CompilerException: java.io.FileNotFoundException: Could not locate cljs/analyzer__init.class or cljs/analyzer.clj on classpath: , compiling:(figwheel_sidecar/utils.clj:1:1)
```

Try upgrading leiningen to version 2.5.3+

``` sh
lein upgrade
```

** Contributors

With thanks to...

[Chris Howe-Jones](https://github.com/chrishowejones)
[Munk](https://github.com/munk)
[Jelle Akkerman](https://github.com/jellea)

## License

Copyright © 2015 Kris Jenkins

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
