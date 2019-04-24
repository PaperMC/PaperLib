# Configuring the maven shade plugin

The Maven shade plugin will shade in all dependencies that have `compile` scope, which is the default for a dependency that doesn't explicitly define a
different scope. So, if your project omits scope definitions, all dependencies will be shaded in, which is not what you want.

## The correct solution

When adding the maven shade plugin for PaperLib, make sure you have a 
```xml
<scope>provided</scope>
```
definition for all dependencies *except* PaperLib.

## The band-aid

If, for some reasons, you don't want to change a scope to `provided`, you can make the maven shade plugin include only PaperLib by explicitly mentioning
it in an `includes` section:

```xml
<configuration>
    <artifactSet>
        <includes>
            <include>io.papermc:paperlib:jar:*</include>
        </includes>
    </artifactSet>
</configuration>
```

However, this forces you to update the `includes` list every time you add a dependency you want shaded in.

## The ugly hack

Instead of including PaperLib, you can also exclude everything you don't want:

```xml
<configuration>
    <artifactSet>
        <excludes>
            <exclude>org.spigotmc:spigot-api</exclude>
            <exclude>org.bukkit:bukkit</exclude>
            ...
        </excludes>
    </artifactSet>                    
</configuration>
```

This is a bad idea though - this will break every time one of your dependencies updates its own dependencies. Don't use except for quick tests.
