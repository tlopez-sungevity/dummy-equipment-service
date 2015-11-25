Equipment Service Client Library (Scala)
========================================

This library is designed to from within Play Framework projects to access the Equipment microservice. Requires JDK 8.
In Play 2.4, simply add the following entry to application.conf to include the Guice module:

```
play.modules.enabled += "service.equipment.client.EquipmentApiClientPlayModule"
```

This module also requires the URL of the Equipment Service to call to also be configured in application.conf of your Play application; e.g.:

```
service.equipment.client.apiUrl = "http://localhost:9000/equipment"
```


