.PHONY: clean publish

clean:
	rm -rf target

#CI 服务器太垃圾, 本地编译后上传
publish:
	mvn package
	cp target/Elune-*.jar ./Elune.jar
