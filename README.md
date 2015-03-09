# A simple web presence tracker

With this app, you can easily keep track of web users presence. The client (javascript party) sends heartbeat signals periodically to the server. The client can also (optionally) subscribe to a channel to receive updates on users count.

PS: To handle large scale (and HA) you can run the app as multiple instances and/or in multiple servers (pointing to the same Redis data store).


## Redis Operations

List active sessions:
```
KEYS session_*
```


On user heartbeat:
```
ZADD session_XPTO <CURRENT_TIMESTAMP> <USER_ID>
```


On users count report:

```
ZCOUNT session_XPTO <MIN_TIMESTAMP> <CURRENT_TIMESTAMP>
```


On session old users cleanup:

```
ZREMRANGEBYSCORE session_XPTO -inf <MIN_TIMESTAMP>
```


On empty sessions cleanup:

```
DEL session_XPTO
```