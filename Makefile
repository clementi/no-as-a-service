MAIN = naas.core
TARGET = target/${MAIN}-standalone.jar

build: ${TARGET}

${TARGET}:
	clj -T:build uber

clean:
	clj -T:build clean

run: ${TARGET}
	java -jar ${TARGET}
