# msas
A project for an IoT platform for securing the access control to the room of an establishment.
  * MSAS_Web is a maven based project for the web services.
* MSAS_Arduino is the client's algorithm to inject into an arduino micro-controller which is supposed to control the door.

-----
Note: 
-----

* You have to change username / password for mysql connection inside the com.msas.MSAS.Configuration.PersistenceJpaConfig class so that the web app can connect and persist data.

* You have to install an MQTT Broker at port 1883 (or use mosquitto broker which by default open a tcp connection at port 1883) so that the application can subscribe and receive requests from micro-controllers.

![1](https://github.com/mssm199996/msas/blob/master/Capture%20d%E2%80%99%C3%A9cran%20de%202019-07-26%2018-50-51.png)
![2](https://github.com/mssm199996/msas/blob/master/Capture%20d%E2%80%99%C3%A9cran%20de%202019-07-26%2018-51-07.png)
![3](https://github.com/mssm199996/msas/blob/master/Capture%20d%E2%80%99%C3%A9cran%20de%202019-07-26%2018-51-12.png)
![4](https://github.com/mssm199996/msas/blob/master/Capture%20d%E2%80%99%C3%A9cran%20de%202019-07-26%2018-52-17.png)
