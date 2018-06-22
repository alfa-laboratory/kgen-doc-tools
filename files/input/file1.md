:::title [file1]

# header 1123  

[some image](static/js/gumshoe.js)
[some image2](/static/js/gumshoe.js)

[external1](https://github.com/evgzakharov/md_parser)

[relativeToFile](./file2.html#file_2)

file 1 some text line 1
file 1 some text line 2

some text VALUE_WITH_UNDERSCORE
some text VALUE<span>_</span>WITH<span>_</span>UNDERSCORE<span>_</span>ESCAPED

## sub header 1

::include "file2.md" 

## sub header 2

file 1 some text line 3
file 1 some text line 4

## sub header 3

```sh
$ node app
```

```java
public class Main {
	public static void main() {
    }
}
```

```kotlin
fun main(args: Array<String>) {
    val workDir = if (args.isEmpty())
        File("files/")
    else
        File(args[0])

    if (!workDir.exists())
        workDir.mkdirs()

    if (workDir.list().isEmpty())
        unpackFiles(workDir)

    val mode = if (args.size >= 2 && args[1] == "test")
        ApplicationMode.TEST
    else
        ApplicationMode.PROD

    Application(workDir, mode = mode).apply {
        start(true)
    }
}
```