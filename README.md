# EasyAnnotation
Annotations for Android. Based on AspectJ.

This library help you with routine operations. Sach as Syncronous and Asynchronous call, check incomming variables and safe code execution.

Support annotations:
* `@Async` - call method at seporate thread
* `@Sync` - call method at main thread ( support parameter `delay` - delay before call - default value 0 )
* `@Safe` - surround method call by try/catch block.
* `@NotNull` - check all parameters to not null value (if same value is `null` then method will not execut)
* `@LogMethod` - log incomming parameters and method result with TAG *easyannotation*


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
    
    // same async code here

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
    
            // same async code here
            
    }catch(Throwable e){
      e.printStackTrace();
    }
}
</pre></td>
    
</tr></tbody></table>

## Implementing lib

