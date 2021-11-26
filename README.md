# PaperLib
PaperLib is a plugin library for interfacing with Paper specific APIs (such as async chunk loading), with graceful fallbacks maintaining compatibility with both the Bukkit and Spigot API's.

## API
All API calls can be found as static util methods in the `PaperLib` class.

### getChunkAtAsync
```java
public class PaperLib {
  public static CompletableFuture<Chunk> getChunkAtAsync(Location loc);
  public static CompletableFuture<Chunk> getChunkAtAsync(Location loc, boolean gen);
  public static CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z);
  public static CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen); 
}
```
On Paper, loads (or generates on 1.13.1+) the chunk asynchronously if it is not loaded yet, then completes the returned future.
Chunk will load synchronous on Spigot.

### teleportAsync
```java
public class PaperLib {
  public static CompletableFuture<Boolean> teleportAsync(Entity entity, Location location);
  public static CompletableFuture<Boolean> teleportAsync(Entity entity, Location location, TeleportCause cause);
}
```
Uses the Async Chunk Load API, and if possible, loads/generates the chunk asynchronously before teleporting.
Will load synchronous on Spigot.

On 1.15+, this has an added improvement of also loading neighbor chunks to avoid collision checks loading chunks too.

### isChunkGenerated
```java
public class PaperLib {
  public static boolean isChunkGenerated(Location loc);
  public static boolean isChunkGenerated(World world, int x, int z);
}
```
Returns whether or not the chunk is generated. Only Supported in Paper 1.12+ and Spigot 1.13.1+

### getBlockState
```java
public class PaperLib {
  public static BlockStateSnapshotResult getBlockState(Block block, boolean useSnapshot);
}
```

Allows you to optionally avoid taking a snapshot of a TileEntity in a BlockState. Versions prior to 1.12 will always be
false for the snapshot. In versions 1.12+ on Spigot, the snapshot will always be true. In Paper 1.12+, the snapshot will
be whether or not you requested one in the API call.

### suggestPaper
```java
public class PaperLib {
  public static void suggestPaper(Plugin plugin);
}
```
Helps inform users who run your plugin on Spigot that your plugin will behave better on Paper! Calling this method
will print out an informational message in the logs that they should switch to Paper, and will help users discover
our software. We would appreciate it if you call this method, but it is optional.

## Example Plugin
```java
public class MyPlugin extends JavaPlugin {
    public void onEnable() {
        PaperLib.suggestPaper(this);
    }
    
    public void doSomething(Entity entity, Location location) {
        PaperLib.teleportAsync(entity, location).thenAccept(result -> {
            if (result) {
                player.sendMessage("Teleported!");
            } else {
                player.sendMessage("Something went wrong!");
            }
        });
    }
}
```

## Build Script Setup
Add the Paper repository and the PaperLib dependency, then shade and relocate it to your own package.
Relocation helps avoid version conflicts with other plugins using PaperLib. 

### Gradle

Repo:
```groovy
repositories {
    maven {
        name "papermc"
        url "https://papermc.io/repo/repository/maven-public/"
    }
}
```

Dependency:
```groovy
dependencies {
    implementation "io.papermc:paperlib:1.0.7"
}
```

Shadow Jar and Relocate (Groovy Syntax):
```groovy
plugins {
  id "com.github.johnrengelman.shadow" version "7.1.0"
  // Make sure to always use the latest version (https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)
}
shadowJar {
   relocate "io.papermc.lib", "[YOUR PLUGIN PACKAGE].paperlib"
}
```

### Maven
Repo:
```xml
<repositories>
    <repository>
        <id>papermc</id>
        <url>https://papermc.io/repo/repository/maven-public/</url>
    </repository>
</repositories>
```
Dependency:
```xml
<dependencies>
    <dependency>
        <groupId>io.papermc</groupId>
        <artifactId>paperlib</artifactId>
        <version>1.0.7</version>
        <scope>compile</scope>
     </dependency>
 </dependencies>
 ```
 
Shade & Relocate:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>3.2.4</version> <!-- Make sure to always use the latest version (https://maven.apache.org/plugins/maven-shade-plugin/) -->
            <configuration>
                <dependencyReducedPomLocation>${project.build.directory}/dependency-reduced-pom.xml</dependencyReducedPomLocation>
                <relocations>
                    <relocation>
                        <pattern>io.papermc.lib</pattern>
                        <shadedPattern>[YOUR PLUGIN PACKAGE].paperlib</shadedPattern> <!-- Replace this -->
                    </relocation>
                </relocations>
            </configuration>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

## Compiling
PaperLib is compiled using Gradle:
```
./gradlew build
```

## License
PaperLib is licensed under the [MIT license](LICENSE)
