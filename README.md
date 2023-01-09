# Flowable-RPA

## Instructions

### Creating Power Automate bots
If you don't have Microsoft Power Automate Desktop click [here](https://powerautomate.microsoft.com/en-us/robotic-process-automation/).
Once installed, click on '+ New flow' in the upper left corner. A new window pop-up will show up requesting a name to your flow.

Power Automate doesn't have an import option, so the only to save the bot is as .txt fileYou will find four .txt files in `/resources/powerautomate`. These are the bots. 
Copy and paste each of them in different flows.

Be sure you have the Power Automate extensions installed and enabled for Google Chrome and Microsoft Edge. Click on 'Tools -> Browser extensions', and install them. 

In order to start the bots is necessary to initialize Flowable work first. 
Power Automate allows to run one bot at a time hence enter to edit mode and run the bots from there. 
This will allow to have the four bots running simultaneously.
### Building the project artifacts
Java and Maven are needed as a prerequisite to build this code.
The Maven configuration of the project tries to get the Flowable dependencies form the Flowable Enterprise Repository.
That Repository needs a Flowable enterprise account to be accessible. Get in contact with Flowable to get your Flowable subscription.

As soon as you got your username and password you can add it to the `settings-flowable.xml` file in the root of the project.
(Keep in mind, that using an unencrypted password in this file is indeed a fast but also not a suggested approach.
Check https://maven.apache.org/guides/mini/guide-encryption.html on how to encrypt your password.)

You can then create the Flowable artifacts by executing `mvn -s settings-flowable.xml clean package`.

## Starting the project
Afterwards you can start the Spring Boot application defined in `com.flowable.rpa.work.FlowableRpaWorkApplication`. In order to achieve this,
you can build the executable war file with maven and execute `java -jar` pointing to the built jar or create a new IDE Run Configuration. 
Then open `http://localhost:8090` in the browser and use one of the users specified below in this document.

You can start the Flowable Design and Flowable Control applications accordingly.

## Helpful links
After both docker-compose and the application are started, these are the links for the different applications:

- http://localhost:8090 - Flowable Work
- http://localhost:8091 - Flowable Design
- http://localhost:8092 - Flowable Control

## Setting up Flowable vanilla apps with Docker
The docker-compose.yml file also contains (commented out) preconfigured service definitions to run all Flowable apps as 
vanilla docker images. Just uncomment the appropriate parts to start the apps together with the other infrastructure.
These vanilla services are preconfigured to run as a replacement of any of the customized apps within this project. 

To be able to use them, log in to docker `docker login artifacts.flowable.com` by using your Flowable login.
You will also need an appropriate Flowable license stored in `~/.flowable/flowable.license`.
Also, a decent amount of memory is needed for your docker VM. At least 4 GB are suggested.
Navigate to `/docker`, then execute `docker-compose up`.

## Infrastructure Containers
If you need to recreate the containers, perform the following actions:
- Make sure the application and the docker containers are stopped.
- Execute `docker-compose down` inside the `/docker` directory. This will remove the created containers.

## Data
Data created by the database and Elasticsearch are stored in docker volumes `data_db` and `data_es`.
This allows you to purge and recreate the containers without loosing any data.

If you need a clean state for the database and elasticsearch just execute `docker-compose down -v`inside the `/docker` directory.
The volumes will be recreated as soon as you restart the containers.  
BE CAREFUL AS WITH THIS YOU WILL LOSE ALL YOUR DATA STORED IN THE DATABASE AND ELASTICSEARCH!

## Sample users
| User | User Definition Key | Login | Password |
| -------------| ------------- | ------------- | ------------- |
| Flowable Administrator | admin-flowable | admin | test |