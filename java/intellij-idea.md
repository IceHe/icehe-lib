# JetBrains Java IDE - IntelliJ IDEA

Usage

---

## Generate JavaDoc

References

- Intellij IDEA 如何生成 JavaDoc : https://www.jianshu.com/p/0ddb0864e499

## Generate Serial Version UID in IDEA

References

- java generate serialVersionUID - Google Search : https://www.google.com/search?newwindow=1&ei=siS3XIKAGMqT0gL-x4ioDA&q=java+generate+serialVersionUID&oq=java+generate+serialVersionUID&gs_l=psy-ab.3..0i7i30l5j0i8i7i30l4.2820.298466..299489...1.0..0.2351.7704.3-3j0j3j3j9-1......0....1..gws-wiz.......0i71j35i39.Hgb0WkftIFI
- SerialVersionUID in Java - GeeksforGeeks : https://www.geeksforgeeks.org/serialversionuid-in-java/
- Java serialVersionUID - How to generate serialVersionUID : https://howtodoinjava.com/java/serialization/serialversionuid/
- java - What is a serialVersionUID and why should I use it? - Stack Overflow : https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
- java - How to generate serial version UID in Intellij - Stack Overflow : https://stackoverflow.com/questions/24573643/how-to-generate-serial-version-uid-in-intellij/24573768

### GsonFormat

References

- GitHub - zzz40500/GsonFormat: 根据Gson库使用的要求,将JSONObject格式的String  解析成实体 : https://github.com/zzz40500/GsonFormat

### Maven Dependencies Red Underlines

References

- Maven Dependencies Red underlines - IDEs Support (IntelliJ Platform) | JetBrains : https://intellij-support.jetbrains.com/hc/en-us/community/posts/207369215-Maven-Dependencies-Red-underlines

> **todd prickett** : _Created November 16, 2018 03:33_
>
> I agree with all the suggestions to delete the .idea directory.
>
> I tried a maven clean/compile from both inside and outside the IDE.  Both worked, but red squiggles remained.
>
> I tried clean, reimport from the Maven directory.  No joy.
>
> I tried restarting IntelliJ.  No joy.
>
> I tried Invalidate Caches/Restart.  No joy.
>
> Finally, I exited IntelliJ.  Deleted the .idea directory.  Imported a new project and chose the .pom file to import from.  Success.