# Reproducer for CDI error in Payara during `@Asynchronous` processes

A simple application demonstrating an NPE in an `@Asynchronous` process using CDI.

## Bug description

We have the following situation:

- An `@Asynchronous` process, which manipulates an entity in a database using JPA
- An `EntityListener` on said entity which performs some processing on post-persist
- The `EntityListener` requires some dependencies provided by CDI

What happens:

1. The async process is run in a separate thread using `EjbAsyncTask`
2. The database manipulation is prepared
3. In the `postInvoke` of the `EjbAsyncTask`, the transaction is being committed, and the `EntityListener` triggered

Because the EntityListener does not exist yet, it is lazily created during transaction commit.

Since Payara 6.2025.6, this construction results in an NPE when getting the dependencies using `CDI.current().select(...)`:

```
java.lang.NullPointerException: Cannot invoke "org.glassfish.api.invocation.ComponentInvocation.getJNDIEnvironment()" because the return value of "org.glassfish.api.invocation.InvocationManager.getCurrentInvocation()" is null
	at org.glassfish.weld.GlassFishWeldProvider.getBundleDescriptor(GlassFishWeldProvider.java:166)
	at org.glassfish.weld.GlassFishWeldProvider.getCDI(GlassFishWeldProvider.java:149)
	at jakarta.enterprise.inject.spi.CDI.getCDIProvider(CDI.java:78)
	at jakarta.enterprise.inject.spi.CDI.current(CDI.java:65)
	at com.github.bvschaik.reproducer.cdi.SampleEntityListener.<init>(SampleEntityListener.java:15)
    ... more
```

Digging deeper into `EjbAsyncTask`, `BaseContainer` and `InvocationManager`, it appears that the current invocation is cleared in `postInvoke()`, before the transaction is committed. So when the EntityListener is created, there no longer is a current invocation, causing the NPE.

Note: this sample contains a duplicate `@Asynchronous` call since the first one also contains a servlet invocation. On our production project only one `@Asynchronous` call is present.

## Steps to reproduce

1. Build a .war and deploy it on Payara 6.2025.6
2. Do a GET to `/api/create` a few times
3. Do a call to `/api/count` and note that the number of database entities is still 0
4. Note in the logs that we have a NullPointerException which caused the transaction to be rolled back

Deploy on Payara 6.2024.5 or earlier and note that the `/api/count` result does increase and the logs don't show any exceptions.

## Versions

Affected Payara server versions:

- 6.2025.6

Not affected:

- 6.2024.5 and earlier
