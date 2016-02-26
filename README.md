# EasyAnnotation
Annotations for Android. Based on AspectJ.

This library help you with routine operations. Sach as Syncronous and Asynchronous call, check incomming variables and safe code execution.

Support annotations:
* **`@Async`** - call method at seporate thread
* **`@Sync`** - call method at main thread ( support parameter `delay` - delay before call - default value 0 )
* **`@Safe`** - surround method call by try/catch block.
* **`@NotNull`** - check all parameters to not null value (if same value is `null` then method will not execut)
* **`@LogMethod`** - log incomming parameters and method result with TAG *easyannotation*


Fro example call method async with annotation **`@Async`** :

<table><tbody><tr><th align="center">EasyAnnotation</th><th align="center">Standart</th></tr><tr>

<td><pre>
<b><i>@Async</i></b>
void myMethod(String param){
   
    // same async code here
   
}
</pre></td>

<td><pre>
void myMethod(final String param){
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
        
            // same async code here
        
        }
    };
    new Thread(runnable).start();        
}
</pre></td>
    
</tr></tbody></table>


 **`@NotNull`** :

<table><tbody><tr><th align="center">EasyAnnotation</th><th align="center">Standart</th></tr><tr>

<td><pre>
<b><i>@NotNull</i></b>
void myMethod(String param1, String param2){
   
    // same code here
   
}
</pre></td>

<td><pre>
void myMethod(String param1, String param2){
    if ( param1==null || param2==null){
    Log.w(TAG,"Null params unsupported");
    return;
    }
    
    // same code here

}
</pre></td>
    
</tr></tbody></table>


**`@Safe`** :

<table><tbody><tr><th align="center">EasyAnnotation</th><th align="center">Standart</th></tr><tr>

<td><pre>
<b><i>@Safe</i></b>
public void myMethod(String param){
   
    // same code here
   
}
</pre></td>

<td><pre>
public void myMethod( String param){
    try{
    
            // same code here
            
    }catch(Throwable e){
      e.printStackTrace();
    }
}
</pre></td>
    
</tr></tbody></table>


**`@LogMethod`** :

<table><tbody><tr><th align="center">EasyAnnotation</th><th align="center">Standart</th></tr><tr>

<td><pre>
<b><i>@LogMethod</i></b>
public Object myMethod(String param){
   
    // same code here
   
}
</pre></td>

<td><pre>
public Object myMethod( String param){
    Log.w(TAG, "--> ClassName.myMethod() Args: " + param);
    
    // same code here
    
    Log.w(TAG, "--> ClassName.myMethod() Result: " + result);
    return result;
}
</pre></td>

</tr></tbody></table>

#### EasyAnnotation have not any perf impact

EasyAnnotation has a little compilation overhead, but the generated classes are good old classic Android code. **No reflection. No startup time, and no runtime impact**.


## Implementing lib
**Step 1.** Add in your root build.gradle file (__*"project root dir"*/build.gradle__):

Add `maven { url "https://jitpack.io" }` in your root build.gradle at the end of repositories.
At section: allprojects->repositories-> add `maven { url "https://jitpack.io" }` 

Add `classpath 'com.uphyca.gradle:gradle-android-aspectj-plugin:0.9.+'` in your root build.gradle at the end of dependencies.
At section: buildscript->dependencies-> add `classpath 'com.uphyca.gradle:gradle-android-aspectj-plugin:0.9.+'` 

```gradle
buildscript {
    dependencies {
        ... 
       classpath 'com.uphyca.gradle:gradle-android-aspectj-plugin:0.9.+'**    // <<<--- add this
    }
}

allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }       // <<<--- add this
	}
}
```

**Step 2.** Add in your app build.gradle file (__*"app dir"*/build.gradle__):

Add `compile 'com.github.tepikin:EasyAnnotation:0.11'` in your app build.gradle at the end of dependencies.
At section: dependencies-> add `compile 'com.github.tepikin:EasyAnnotation:0.11'`

At start of file add `apply plugin: "android-aspectj"` 

```gradle

   apply plugin: "android-aspectj"  // <<<--- add this
   
	dependencies {
	        ...
	        compile 'com.github.tepikin:EasyAnnotation:0.11'     // <<<--- add this
	}
```
