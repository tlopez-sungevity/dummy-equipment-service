

Building
========

You will need to have docker installed and running for your environment.

Run "make" from the repo root directory. This will run "activator dist" and build distributable play app into a docker container. Make will be replaced with something cross platform shortly.


Environment variables
=====================

The following envrionment variables must be created for the service to run correctly.

EQUIPMENT_DB_URL=&lt;jdbc-style-URL-to-DB&gt;

EQUIPMENT_DB_USER=&lt;user&gt;

EQUIPMENT_DB_PASSWORD=&lt;password&gt;
