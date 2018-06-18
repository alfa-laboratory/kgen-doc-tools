:::title [file1]

# header 1123  

[some image](static/js/gumshoe.js)
[some image2](/static/js/gumshoe.js)

file 1 some text line 1
file 1 some text line 2

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