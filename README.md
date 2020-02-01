# Image Space 

### Description

Image-space is simply telling what's going on around you.
We encourage you to update your friends and relatives to find out what's going on and find other interesting accounts.
Join the photo post and share all the moments of the day, from the most important to the fullest.

## Preparation
#### Postgres
Services expect installed postgres service on default port with default credentials. 
If you want to change any of those properties, you can just update it in `config-serivce` resource folder.

Creating user and database in Postgres
```
> sudo -u postgres psql
postgres> create user root with encrypted password 'root';
postgres> create database imagespace_account_db;
postgres> create database imagespace_post_db;
postgres> grant all privileges on database imagespace_account_db to root;
postgres> grant all privileges on database imagespace_post_db to root;
``` 

#### Kafka
For kafka initialization we use docker images of zookeeper and kafka. 
For start kafka just start docker-compose file in root project.
``` 
> docker-compose up
```

#### MongoDB
`source-service` includes reactive stack to integrate with mongoDB. 
For enable all features we need to setup mongoDB before start the service. 

This methods will allow you to work with the sessions in mongoDB.
For now it's working only from 4.0 and collections with replicas
Next, we'll start mongod service using the command line:
mongod --replSet rs0
```
mongod --replSet rs0 --config /usr/local/etc/mongod.conf --fork
```

Finally, initiate replica set – if not already:
```
mongo --eval "rs.initiate()"
```
Note that MongoDB currently supports transactions over a replica set.

For saving documents, before you'll need to create a collection in MongoDB, becasue
operations that affect the database catalog, such as creating or dropping a collection or an index, 
are not allowed in multi-document transactions. For example, a multi-document transaction cannot include 
an insert operation that would result in the creation of a new collection. See Restricted Operations.

So before start service you will need to create collection in our table:
```
> mongo "mongodb://localhost:27017/image-space-source-storage"
rs0:PRIMARY> db.createCollection("source");
rs0:PRIMARY> db.getCollectionNames();
[ "source" ]
```                    