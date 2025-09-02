build:
    clj -T:build uber

clean:
    clj -T:build clean

run:
    java -jar ./target/naas.core-0.1.0-standalone.jar
