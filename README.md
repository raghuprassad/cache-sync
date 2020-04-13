# cache-sync

The way I understand, It is required to JVM data sync , because if services are running on different instance, the JVM is different. So with just one request we can not make update the cache data in both JVM.

 

So, I have used Hazelcast in memory caching to make the system distributed cached.

 

I have , used rabbitmq to update the cache in both the instances. However this is not successful , because we can not share data between JVM, I tried to change the server port on runtime by the Topic and two queues , but it is not possible to make change the server port updated at run time.
