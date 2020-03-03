# SignAPI
This is a simple API for creating and managing Bukkit signs (refresh, interact etc.).

## Usage
SignAPI can easily be included in maven/ gradle using my repo.

Maven
```xml
<repositories>
    <repository>
        <id>andrei1058-repo</id>
        <url>https://repo.andrei1058.com/releases/</url>
    </repository>
</repositories>
```
```xml
<dependencies>
    <dependency>
        <groupId>com.andrei1058.spigot</groupId>
        <artifactId>signs-api</artifactId>
        <version>0.1</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

Gradle
```gradle
repositories {
	maven {
        url "https://repo.andrei1058.com"
    }
}
dependencies {
    implementation 'com.andrei1058.spigot:signs-api:0.1'
}
```

In order to use this API you simply have to initialize it and then enjoy.
```java
import com.andrei1058.spigot.signapi.SignAPI;import jdk.nashorn.internal.ir.Block;import net.md_5.bungee.api.ChatColor;public class MyPlugin extends JavaPlugin{

    private static SignAPI signAPI;
        
    public void onEnable(){
    // It is important to use this in on-enable because it will register
    // a event listener for sign interactions.
    signAPI = new SignAPI(this);
    
    creatingSigns();
    }
    
    // Creating signs
    private void creatingSigns(){
    Block b;
    ASign as = signAPI.createSign(b).setLines(ChatColor.RED+"Hello", "", ChatColor.BLACK+"andrei1058");
        // on interact event
        as.setEvent((p)-> {
            p.sendMessage("You clicked my sign!");
            as.refresh("Sign clocked!");
        });
    }

}
```